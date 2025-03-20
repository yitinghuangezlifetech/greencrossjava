package tw.com.ezlifetech.ezReco.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.ArItemUploadBean;
import tw.com.ezlifetech.ezReco.bean.ArMetaDataBean;
import tw.com.ezlifetech.ezReco.bean.ArReturnBean;
import tw.com.ezlifetech.ezReco.bean.FileUploadBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.common.service.FileUploadService;
import tw.com.ezlifetech.ezReco.common.service.SeqGenService;
import tw.com.ezlifetech.ezReco.dto.ArItemHtDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.ArItemDlActions;
import tw.com.ezlifetech.ezReco.enums.AritemStatus;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.model.ArItemDl;
import tw.com.ezlifetech.ezReco.model.ArItemHt;
import tw.com.ezlifetech.ezReco.respository.ArItemDlRespository;
import tw.com.ezlifetech.ezReco.respository.ArItemHtRespository;
import tw.com.ezlifetech.ezReco.service.ArItemUploadService;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;
import tw.com.ezlifetech.ezReco.util.SysParamsUtils;
import tw.com.ezlifetech.ezReco.util.ar.VuforiaProxyService;
import tw.com.ezlifetech.ezReco.util.ar.conf.Settings;
import tw.com.ezlifetech.ezReco.util.ar.vo.TargetInfoVO;
import tw.com.ezlifetech.ezReco.util.ar.vo.VuforiaInfoVO;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ArItemUploadServiceImpl implements ArItemUploadService{

	
	@Autowired
	private FileUploadService fileUploadService;
	
	
	@Autowired
	private ArItemHtRespository arItemHtRespository;
	
	@Autowired
	private ArItemDlRespository arItemDlRespository;
	
	@Autowired
	private SeqGenService seqGenService;
	
	@Override
	public void paper(Model model) {
		model.addAttribute("actionList", actionSelect());
		
	}
	
	
	
	private List<Map<String,Object>> actionSelect() {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(ArItemDlActions a : ArItemDlActions.values()) {
			
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("text", a.getValue());
			m.put("code", a.getCode());
			list.add(m);
		}
		
		
		return list;
	}



	@Override
	public AjaxReturnBean ajaxUploadFile(MultipartFile file) throws Exception {
		
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean(); 
		FileUploadBean fileUploadBean = fileUploadService.saveUploadFile(file);
		
		
		
		
		
		
		JsonObject jo = new JsonObject();
		jo.addProperty("fileId", fileUploadBean.getFileName());
		jo.addProperty("fileName", fileUploadBean.getFileName());
		
		ajaxReturnBean.setValue(jo);
		
		ajaxReturnBean.setMessage(AjaxMesgs.UPLAOD_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}



	@Override
	public AjaxReturnBean removeFileHandler(ArItemUploadBean formBean) throws Exception {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean(); 
		
		fileUploadService.removeUploadFileByFileId(formBean.getFileId());
		
		
		
		ajaxReturnBean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}



	@Override
	public List<ErrorBean> validateInternalFastArHtDlSave(ArItemUploadBean bean) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		if(StringUtil.isBlank(bean.getItemName())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_itemName");
			erBean.setLabelName("物件名稱");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}
		
		if(StringUtil.isBlank(bean.getItemContent())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_itemContent");
			erBean.setLabelName("物件描述");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}
		
		if(StringUtil.isBlank(bean.getArfileId()) && StringUtil.isBlank(bean.getHtId())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_arfileId");
			erBean.setLabelName("");
			erBean.setMesg(ErrorMesgs.PLZ_UPLOAD_FILE.getDoc());
			list.add(erBean);
		}
		
		if(StringUtil.isBlank(bean.getAction())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_action");
			erBean.setLabelName("");
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "動作"));
			list.add(erBean);
		}else {
			
			if(ArItemDlActions.IMAGE_UPLOAD.getCode().equals(bean.getAction())) {
				if(StringUtil.isBlank(bean.getFileId())) {
					ErrorBean erBean = new ErrorBean();
					erBean.setErrSpanId("err_fileId");
					erBean.setLabelName("");
					erBean.setMesg(ErrorMesgs.PLZ_UPLOAD_FILE.getDoc());
					list.add(erBean);
				}
			}else {
				if(StringUtil.isBlank(bean.getVar())) {
					ErrorBean erBean = new ErrorBean();
					erBean.setErrSpanId("err_var");
					erBean.setLabelName("參數");
					erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
					list.add(erBean);
				}
				
			}
			
			
			
		}
		
		
		
		return list;
	}



	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxFastArHtDlSave(ArItemUploadBean bean, UserDto loginUser) throws Exception {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean(); 
		VuforiaProxyService vrforiaProxyService = new VuforiaProxyService();
		String metaData = vrforiaProxyService.genMetaData(genMetaData(bean,vrforiaProxyService));
		String filePath = SysParamsUtils.getParam("uploadFilePath")+File.separator+"arImgUpload"+File.separator+bean.getArfileId();
		
		VuforiaInfoVO vInfoVO = new VuforiaInfoVO();
		vInfoVO.setAccessKey(Settings.SERVER_ACCESS_KEY);
		vInfoVO.setSecretKey(Settings.SERVER_SECRET_KEY);
		vInfoVO.setImagePath(filePath);
		vInfoVO.setImageNm(loginUser.getId()+DateUtil.getSystemTime("yyyyMMddHHmmss")+StringUtil.getRandomNum(3));
		vInfoVO.setMetaData(metaData);
		
		
		ArItemHt ht = null;
		
		if(StringUtil.isBlank(bean.getHtId())) {
			ht = new ArItemHt();
			ht.setId(seqGenService.getSystemTimeRandomNumber());
			ht.setCreateTime(DateUtil.getSystemDateTimeObject());
			ht.setCreateUser(loginUser.getId());
			ht.setStatus(AritemStatus.ALIVE.getStatusCode());
			ht.setOwnerId(loginUser.getOnwerId());
		}else {
			ht = arItemHtRespository.getEntityById(bean.getHtId());
			if(ht==null) {
				 throw new Exception("ArItemHt not found");
			}
			ht.setUpdateUser(loginUser.getId());
			ht.setUpdateTime(DateUtil.getSystemDateTimeObject());
		}
		
		ht.setItemName(bean.getItemName());
		ht.setItemContent(bean.getItemContent());
		
		
		
		ArItemDl dl = null;
		
		if(StringUtil.isBlank(bean.getDlId())) {
			dl = new ArItemDl();
			dl.setId(seqGenService.getSystemTimeRandomNumber());
			dl.setHtId(ht.getId());
			dl.setCreateTime(DateUtil.getSystemDateTimeObject());
			dl.setCreateUser(loginUser.getId());
			
			
			ArReturnBean arRes = vrforiaProxyService.createImageTarget(vInfoVO);
			if(!arRes.isSuccessful()) {
				throw new Exception("createTarget Fail!");
			}
			
			dl.setTargetId(arRes.getResValues().get("target_id").toString());
			dl.setIsMainImg("N");
			dl.setStatus(AritemStatus.ALIVE.getStatusCode());
		}else {
			dl = arItemDlRespository.getEntityById(bean.getDlId());
			if(dl==null) {
				 throw new Exception("ArItemDl not found");
			}
			dl.setUpdateUser(loginUser.getId());
			dl.setUpdateTime(DateUtil.getSystemDateTimeObject());
			
		}
		if(ArItemDlActions.IMAGE_UPLOAD.getCode().equals(bean.getAction())) {
			dl.setUploadPath(SysParamsUtils.getParam("uploadFilePath")+File.separator+"arImgUpload"+File.separator+bean.getFileId());
		}else {
			dl.setUploadPath("");
		}
		
		
		dl.setImgPath(filePath);
		dl.setAction(bean.getAction());
		dl.setVar(bean.getVar());
		dl.setMetaData(metaData);
		
		
		arItemHtRespository.save(ht);
		arItemDlRespository.save(dl);
		ajaxReturnBean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		ajaxReturnBean.setHtId(ht.getId());
		return ajaxReturnBean;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxFastArHtDlUpdate(ArItemUploadBean bean, UserDto loginUser) throws Exception {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean(); 
		VuforiaProxyService vrforiaProxyService = new VuforiaProxyService();
		// 檢查該物件是否存在雲端
		if(!existTarget(bean)) {
			throw new Exception("該物件已被刪除,無法進行修改");
		}
		
		String metaData = vrforiaProxyService.genMetaData(genMetaData(bean,vrforiaProxyService));
		String filePath = null;
		VuforiaInfoVO vInfoVO = null;
		if(StringUtils.isNotBlank(bean.getArfileId())) {
			filePath = SysParamsUtils.getParam("uploadFilePath")+File.separator+"arImgUpload"+File.separator+bean.getArfileId();	
		}
		
		if(StringUtils.isNotBlank(filePath)) {
			vInfoVO = new VuforiaInfoVO();
			vInfoVO.setAccessKey(Settings.SERVER_ACCESS_KEY);
			vInfoVO.setSecretKey(Settings.SERVER_SECRET_KEY);
			vInfoVO.setImageNm(loginUser.getId()+DateUtil.getSystemTime("yyyyMMddHHmmss")+StringUtil.getRandomNum(3));
			vInfoVO.setMetaData(metaData);		
			vInfoVO.setImagePath(filePath);
		}
		
		ArItemHt ht = null;
		
		if(StringUtil.isNotBlank(bean.getHtId())) {
			ht = arItemHtRespository.getEntityById(bean.getHtId());
			if(ht != null) {
				ht.setItemName(bean.getItemName());
				ht.setItemContent(bean.getItemContent());
				ht.setUpdateUser(loginUser.getId());
				ht.setUpdateTime(DateUtil.getSystemDateTimeObject());	
			}
			else {
				throw new Exception("ArItemHt not found");
			}
		}else {
			throw new Exception("ArItemHt not found");
		}
				
		ArItemDl dl = null;
		
		if(StringUtil.isNotBlank(bean.getDlId())) {
			dl = arItemDlRespository.getEntityById(bean.getDlId());
			dl.setUpdateUser(loginUser.getId());
			dl.setUpdateTime(DateUtil.getSystemDateTimeObject());
			if(filePath != null) {
				dl.setImgPath(filePath);	
			}
			if(vInfoVO != null) {
				vInfoVO.setTargetId(dl.getTargetId());
				if(!vrforiaProxyService.updateImageTarget(vInfoVO)) {
					throw new Exception("updateTarget Fail!");
				}
			}			
		}else {
			 throw new Exception("ArItemDl not found");
		}
		if(ArItemDlActions.IMAGE_UPLOAD.getCode().equals(bean.getAction())) {
			if(bean.getFileId() != null) {
				dl.setUploadPath(SysParamsUtils.getParam("uploadFilePath")+File.separator+"arImgUpload"+File.separator+bean.getFileId());	
			}
		}
		
		dl.setAction(bean.getAction());
		dl.setVar(bean.getVar());
		dl.setMetaData(metaData);
				
		arItemHtRespository.save(ht);
		arItemDlRespository.save(dl);
		ajaxReturnBean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}

	private ArMetaDataBean genMetaData(ArItemUploadBean bean,VuforiaProxyService vrforiaProxyService) {
		ArMetaDataBean arMetaDataBean = new ArMetaDataBean();
		if(ArItemDlActions.IMAGE_UPLOAD.getCode().equals(bean.getAction())) {
			arMetaDataBean.setUrlPath(Settings.WEB_SITE_URL+"/arimage/"+bean.getFileId());
		}else {
			arMetaDataBean.setUrlPath(bean.getVar());
		}
		arMetaDataBean.setApplicateCate(vrforiaProxyService.getActionByCode(bean.getAction()));
		arMetaDataBean.setTopic(bean.getItemName());
		arMetaDataBean.setDesc(bean.getItemContent());
		arMetaDataBean.setAction("");
		arMetaDataBean.setApplicateID("");
		arMetaDataBean.setRecognID("");
		
		return arMetaDataBean;
	}



	@Override
	public String arGridList(ArItemUploadBean formBean) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer();
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(!StringUtil.isBlank(formBean.getOwnerId())) {
			params.put("ownerId", formBean.getOwnerId());
		}
		
		sql.append("  SELECT                ");
		sql.append("  id                    ");
		sql.append("  ,status               ");
		sql.append("  ,item_name            ");
		sql.append("  ,item_content         ");
		sql.append("  ,owner_id             ");
		sql.append("  ,create_user          ");
		sql.append("  ,create_time          ");
		sql.append("  ,update_user          ");
		sql.append("  ,update_time          ");
		sql.append("  FROM ar_item_ht       ");
		sql.append("  WHERE 1=1             ");
		if(params.get("ownerId")!=null)
			sql.append("  AND  owner_id = :ownerId          ");
		
		list = arItemHtRespository.findListMapBySQL_map(sql.toString(), params);
		
		return PageUtil.transToGridDataSource(list,formBean);
	}



	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxRemoveHt(ArItemUploadBean formBean) throws Exception {
		VuforiaProxyService vrforiaProxyService = new VuforiaProxyService();
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean(); 
		ArItemHt ht = arItemHtRespository.getEntityById(formBean.getHtId());
		
		List<ArItemDl> dlList = arItemDlRespository.findEntityListByJPQL("SELECT d FROM ArItemDl d WHERE d.htId=?", ht.getId());
		
		for(ArItemDl dl:dlList) {
			VuforiaInfoVO vInfoVO = new VuforiaInfoVO();
			vInfoVO.setAccessKey(Settings.SERVER_ACCESS_KEY);
			vInfoVO.setSecretKey(Settings.SERVER_SECRET_KEY);
			vInfoVO.setTargetId(dl.getTargetId());
			//確認雲端上資料是否存在
			TargetInfoVO targetInfoVO = vrforiaProxyService.getImageTargetByTargetId(vInfoVO);
			//若存在,先刪除雲端,後DB
			if(targetInfoVO.isSuccess()) {
				ArReturnBean arRes = vrforiaProxyService.delImageTarget(vInfoVO);
				if(!arRes.isSuccessful()) {
					throw new Exception("Delete Target Fail!");
				}				
			}			
			arItemDlRespository.delete(dl);
		}
		arItemHtRespository.delete(ht);
		
		
		ajaxReturnBean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}



	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	
	public AjaxReturnBean ajaxGetArHtDlsInfo(ArItemHtDto dto, UserDto loginUser) throws Exception {
		JsonObject jo = new JsonObject();
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		
		if(StringUtil.isBlank(dto.getId())) {
			throw new Exception("dto ArHt id is null");
		}
		ArItemHt ht =arItemHtRespository.getEntityById(dto.getId());
		if(ht==null) {
			throw new Exception("Get ArItemHt Fail!");
		}
		
		jo.addProperty("id", ht.getId());
		jo.addProperty("itemName", ht.getItemName());
		jo.addProperty("itemContent", ht.getItemContent());
		jo.addProperty("ownerId", ht.getOwnerId());
		
		JsonArray ja = new JsonArray();
		List<ArItemDl> dlList = arItemDlRespository.findEntityListByJPQL("SELECT d FROM ArItemDl d WHERE d.htId=?", ht.getId());
		for(ArItemDl dl :dlList) {
			JsonObject dlJo = new JsonObject();
			dlJo.addProperty("id", dl.getId());
			dlJo.addProperty("htId", dl.getHtId());
			dlJo.addProperty("targetId", dl.getTargetId());
			dlJo.addProperty("imgPath", dl.getImgPath());
			dlJo.addProperty("isMainImg", dl.getIsMainImg());
			dlJo.addProperty("status", dl.getStatus());
			dlJo.addProperty("action", dl.getAction());
			dlJo.addProperty("var", dl.getVar());
			dlJo.addProperty("metaData", dl.getMetaData());
			dlJo.addProperty("rating", dl.getRating());
			dlJo.addProperty("uploadPath", dl.getUploadPath());
			ja.add(dlJo);
		}
		jo.add("dlList", ja);
		ajaxReturnBean.setValue(jo);
		ajaxReturnBean.setMessage("");
		
		return ajaxReturnBean;
	}

	public boolean existTarget(ArItemUploadBean bean) {
		boolean exist = false;
		VuforiaProxyService vrforiaProxyService = new VuforiaProxyService();
		ArItemDl dl = arItemDlRespository.getEntityById(bean.getDlId());
		VuforiaInfoVO vInfoVO = new VuforiaInfoVO();
		vInfoVO.setAccessKey(Settings.SERVER_ACCESS_KEY);
		vInfoVO.setSecretKey(Settings.SERVER_SECRET_KEY);
		vInfoVO.setTargetId(dl.getTargetId());
		TargetInfoVO targetInfoVO = vrforiaProxyService.getImageTargetByTargetId(vInfoVO);
		if(targetInfoVO.isSuccess()) {
			exist = true;
		}
		return exist;
	}
}
