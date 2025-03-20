package tw.com.ezlifetech.ezReco.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.time.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.common.service.SeqGenService;
import tw.com.ezlifetech.ezReco.dto.ComProfHtDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.dto.WareHouseProfDlDto;
import tw.com.ezlifetech.ezReco.dto.WareHouseProfDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.model.WareHouseProf;
import tw.com.ezlifetech.ezReco.model.WareHouseProfDl;
import tw.com.ezlifetech.ezReco.respository.ComProfHtRespository;
import tw.com.ezlifetech.ezReco.respository.WareHouseProfDlRespository;
import tw.com.ezlifetech.ezReco.respository.WareHouseProfRespository;
import tw.com.ezlifetech.ezReco.service.ComProfHtService;
import tw.com.ezlifetech.ezReco.service.WareHouseProfService;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;
import tw.com.ezlifetech.ezReco.util.ValidatorUtil;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WareHouseProfServiceImpl implements WareHouseProfService{

	
	@Autowired
	private WareHouseProfRespository WareHouseProfRespository;
	
	@Autowired
	private WareHouseProfDlRespository WareHouseProfDlRespository;
	
	@Autowired
	private ComProfHtRespository comProfHtRespository;
	
	@Autowired
	private SeqGenService seqGenService;
	
	@Autowired
	private ComProfHtService comProfHtService;
	
	@Override
	public void paper(Model model,UserDto userDto) throws Exception {
		// TODO Auto-generated method stub
		ComProfHtDto comHtDto = new ComProfHtDto();
		comHtDto = comProfHtService.getComProfHt(userDto);
		if(null!=comHtDto) {
			model.addAttribute("comHtDto",comHtDto);
		}
	}

	@Override
	public void paperEdit(Model model, WareHouseProfDto dto) throws Exception {
		
		
		if(StringUtil.isBlank(dto.getId())) {
			
		}else {
			WareHouseProf ht = WareHouseProfRespository.getEntityById(dto.getId());
			BeanUtils.copyProperties(dto,ht);
			
		}
		
		
		model.addAttribute("compIdSelectList", getCompIdSelectList());
		
	}

	private List<Map<String,Object>> getCompIdSelectList() {
		List<Map<String,Object>> compList = new  ArrayList<>();
		compList = comProfHtRespository.getAllCompIdAndNameListMap();
		return compList;
	}

	@Override
	
	public List<ErrorBean> validateInternalSaveHt(WareHouseProfDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
//		if(StringUtil.isBlank(dto.getConfigName())) {
//			ErrorBean erBean = new ErrorBean();
//			erBean.setErrSpanId("err_configName"); // 對應前端錯誤提示 的 Id 
//			erBean.setLabelName("班別設定名稱"); // 欄位名稱
//			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
//			list.add(erBean);
//		}else {
//			if(dto.getConfigName().length()>100) {
//				ErrorBean erBean = new ErrorBean();
//				erBean.setErrSpanId("err_configName"); // 對應前端錯誤提示 的 Id 
//				erBean.setLabelName("班別設定名稱"); // 欄位名稱
//				erBean.setMesg(ErrorMesgs.OUT_OF_RANGE.getDoc().replace("{!range}", "100"));
//				list.add(erBean);
//			}
//		}
		
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxSaveHt(WareHouseProfDto dto, UserDto loginUser) throws Exception {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		JsonObject jo = new JsonObject();
		WareHouseProf ht = null;
		
		if(StringUtil.isBlank(dto.getId())) {
			ht = new WareHouseProf();
			BeanUtils.copyProperties(ht, dto);
			ht.setId(seqGenService.getSystemTimeRandomNumber());
			ht.setCreateUser(loginUser.getId());
			ht.setCreateTime(DateUtil.getSystemDateTimeObject());

		}else {
			
			ht = WareHouseProfRespository.getEntityById(dto.getId());
			BeanUtils.copyProperties(ht, dto);
			ht.setUpdateUser(loginUser.getId());
			ht.setUpdateTime(DateUtil.getSystemDateTimeObject());
		}
		
		
		WareHouseProfRespository.save(ht);
		
		
		jo.addProperty("htId", ht.getId());
		
		ajaxReturnBean.setValue(jo);
		ajaxReturnBean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}

	@Override
	public String ajaxDlGridList(WareHouseProfDlDto dto, UserDto loginUser) {
	
		
		return PageUtil.transToGridDataSource(WareHouseProfDlRespository.ajaxDlGridList(dto),dto); 
	}

	@Override
	public String ajaxHtGridList(WareHouseProfDto dto, UserDto loginUser) {
		dto.setWhComId("20190623131341951");
		return PageUtil.transToGridDataSource(WareHouseProfRespository.ajaxGridList(dto),dto); 
	}

	@Override
	public List<ErrorBean> validateInternalRemoveHt(WareHouseProfDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxRemoveHt(WareHouseProfDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		WareHouseProf ht = WareHouseProfRespository.getEntityById(dto.getId());
		
		if(ht!=null) {
			List<WareHouseProfDl>  dlList = WareHouseProfDlRespository.findEntityListByJPQL("SELECT d FROM WareHouseProfDl d WHERE topId=?", dto.getId());
			for(WareHouseProfDl d : dlList) {
				WareHouseProfDlRespository.delete(d);
			}
			WareHouseProfRespository.delete(ht);
		}
		
		ajaxReturnBean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxSaveDl(WareHouseProfDlDto dto, UserDto loginUser) throws Exception {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		WareHouseProfDl dl = null;
		if(StringUtil.isBlank(dto.getId())) {
			dl = new WareHouseProfDl();
			dl.setTopId(dto.getId());
			BeanUtils.copyProperties(dl, dto);
			dl.setId(seqGenService.getSystemTimeRandomNumber());
			dl.setCreateUser(loginUser.getId());
			dl.setCreateTime(DateUtil.getSystemDateTimeObject());
		}else {
			dl = WareHouseProfDlRespository.getEntityById(dto.getId());
			BeanUtils.copyProperties(dl, dto);
			dl.setUpdateUser(loginUser.getId());
			dl.setUpdateTime(DateUtil.getSystemDateTimeObject());
		}
//		dl.setClassName(dto.getClassName());
//		dl.setSdateTime(dto.getSdateTime());
//		dl.setEdateTime(dto.getEdateTime());
//		dl.setSalary(dto.getSalary());
//		dl.setUnit(dto.getUnit());
		
		WareHouseProfDlRespository.save(dl);
		ajaxReturnBean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}

	@Override
	public List<ErrorBean> validateInternalSaveDl(WareHouseProfDlDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
//		if(StringUtil.isBlank(dto.getClassName())) {
//			ErrorBean erBean = new ErrorBean();
//			erBean.setErrSpanId("err_className"); // 對應前端錯誤提示 的 Id 
//			erBean.setLabelName("班別名稱"); // 欄位名稱
//			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
//			list.add(erBean);
//		}else {
//			if(dto.getClassName().length()>100) {
//				ErrorBean erBean = new ErrorBean();
//				erBean.setErrSpanId("err_className"); // 對應前端錯誤提示 的 Id 
//				erBean.setLabelName("班別名稱"); // 欄位名稱
//				erBean.setMesg(ErrorMesgs.OUT_OF_RANGE.getDoc().replace("{!range}", "100"));
//				list.add(erBean);
//			}
//		}
//		
//		
//		if(StringUtil.isBlank(dto.getSdateTime())) {
//			ErrorBean erBean = new ErrorBean();
//			erBean.setErrSpanId("err_sdateTime"); // 對應前端錯誤提示 的 Id 
//			erBean.setLabelName(""); // 欄位名稱
//			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "班別開始時間"));
//			list.add(erBean);
//		}else {
//			if(!ValidatorUtil.isTimeHHMM(dto.getSdateTime())) {
//				ErrorBean erBean = new ErrorBean();
//				erBean.setErrSpanId("err_sdateTime"); // 對應前端錯誤提示 的 Id 
//				erBean.setLabelName("班別開始時間"); // 欄位名稱
//				erBean.setMesg(ErrorMesgs.DATA_FORMAT_NOT_MATCH.getDoc());
//				list.add(erBean);
//			}
//		}
//		
//		if(StringUtil.isBlank(dto.getEdateTime())) {
//			ErrorBean erBean = new ErrorBean();
//			erBean.setErrSpanId("err_edateTime"); // 對應前端錯誤提示 的 Id 
//			erBean.setLabelName(""); // 欄位名稱
//			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "班別結束時間"));
//			list.add(erBean);
//		}else {
//			if(!ValidatorUtil.isTimeHHMM(dto.getEdateTime())) {
//				ErrorBean erBean = new ErrorBean();
//				erBean.setErrSpanId("err_edateTime"); // 對應前端錯誤提示 的 Id 
//				erBean.setLabelName("班別結束時間"); // 欄位名稱
//				erBean.setMesg(ErrorMesgs.DATA_FORMAT_NOT_MATCH.getDoc());
//				list.add(erBean);
//			}
//		}
//		
//		
//		if(!(StringUtil.isBlank(dto.getSalary()) && StringUtil.isBlank(dto.getUnit()))) {
//			if(StringUtil.isBlank(dto.getSalary())) {
//				ErrorBean erBean = new ErrorBean();
//				erBean.setErrSpanId("err_salary"); // 對應前端錯誤提示 的 Id 
//				erBean.setLabelName("每單位薪水"); // 欄位名稱
//				erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
//				list.add(erBean);
//			}else {
//				if(!ValidatorUtil.isNumber(dto.getSalary())) {
//					ErrorBean erBean = new ErrorBean();
//					erBean.setErrSpanId("err_salary"); // 對應前端錯誤提示 的 Id 
//					erBean.setLabelName("每單位薪水"); // 欄位名稱
//					erBean.setMesg(ErrorMesgs.PLZ_INPUT_NUMBER.getDoc());
//					list.add(erBean);
//				} else if(Double.parseDouble(dto.getSalary())<0) {
//					ErrorBean erBean = new ErrorBean();
//					erBean.setErrSpanId("err_salary"); // 對應前端錯誤提示 的 Id 
//					erBean.setLabelName("每單位薪水"); // 欄位名稱
//					erBean.setMesg(ErrorMesgs.MUST_BIGGER_THEN.getDoc().replace("{!label}", "0"));
//					list.add(erBean);
//				} else if(dto.getSalary().length()>30) {
//					ErrorBean erBean = new ErrorBean();
//					erBean.setErrSpanId("err_salary"); // 對應前端錯誤提示 的 Id 
//					erBean.setLabelName("每單位薪水"); // 欄位名稱
//					erBean.setMesg(ErrorMesgs.OUT_OF_RANGE.getDoc().replace("{!range}", "30"));
//					list.add(erBean);
//				}
//				
//			}
//			
//			if(StringUtil.isBlank(dto.getUnit())) {
//				ErrorBean erBean = new ErrorBean();
//				erBean.setErrSpanId("err_unit"); // 對應前端錯誤提示 的 Id 
//				erBean.setLabelName(""); // 欄位名稱
//				erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "單位"));
//				list.add(erBean);
//			}
//		}
		
		
		return list;
	}

	@Override
	public AjaxReturnBean ajaxGetDl(WareHouseProfDlDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		JsonObject jo = new JsonObject();
		WareHouseProfDl dl =WareHouseProfDlRespository.getEntityById(dto.getId());
		
		jo.addProperty("id", dl.getId());
		
		ajaxReturnBean.setValue(jo);
		return ajaxReturnBean;
	}

	@Override
	public List<ErrorBean> validateInternalGetDl(WareHouseProfDlDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		WareHouseProfDl dl =WareHouseProfDlRespository.getEntityById(dto.getId());
		if(dl==null) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("nano"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName(""); // 欄位名稱
			erBean.setMesg(ErrorMesgs.DATA_NOT_EXIST.getDoc());
			list.add(erBean);
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxRemoveDl(WareHouseProfDlDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		WareHouseProfDl dl =WareHouseProfDlRespository.getEntityById(dto.getId());
		if(dl!=null) {
			WareHouseProfDlRespository.delete(dl);
		}
		
		ajaxReturnBean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}

	@Override
	public List<ErrorBean> validateInternalRemoveDl(WareHouseProfDlDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		return list;
	}
	
}
