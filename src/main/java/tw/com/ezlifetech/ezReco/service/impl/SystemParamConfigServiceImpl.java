package tw.com.ezlifetech.ezReco.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.SystemParamConfigBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.common.service.SeqGenService;
import tw.com.ezlifetech.ezReco.dto.BasicSysparamDlDto;
import tw.com.ezlifetech.ezReco.dto.BasicSysparamHtDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.model.BasicSysparamDl;
import tw.com.ezlifetech.ezReco.model.BasicSysparamHt;
import tw.com.ezlifetech.ezReco.respository.BasicSysparamDlRespository;
import tw.com.ezlifetech.ezReco.respository.BasicSysparamHtRespository;
import tw.com.ezlifetech.ezReco.service.SystemParamConfigService;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;
import tw.com.ezlifetech.ezReco.util.ValidatorUtil;


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class) // Transactional Rollback 
public class SystemParamConfigServiceImpl implements SystemParamConfigService{
	
	@Autowired
	private BasicSysparamHtRespository basicSysparamHtRespository;
	
	@Autowired
	private BasicSysparamDlRespository basicSysparamDlRespository;
	
	@Autowired
	private SeqGenService seqGenService;
	
	@Override
	public String systemParamHtGridList(SystemParamConfigBean formBean) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(!StringUtil.isBlank(formBean.getSearchName())) {
			params.put("searchName","%"+formBean.getSearchName()+"%");
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT * FROM sysparam_ht WHERE 1=1  ");
		if(params.get("searchName")!=null)
			sql.append("  AND PARAM_NAME like :searchName ");
		
		List<Map<String, Object>> list=basicSysparamHtRespository.findListMapBySQL_map(sql.toString(), params);
		
		return PageUtil.transToGridDataSource(list,formBean); 
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxSaveSystemParamHt(BasicSysparamHtDto dto, UserDto loginUser) throws Exception {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		BasicSysparamHt h = null;
		if(StringUtil.isBlank(dto.getId())) {
			h=new BasicSysparamHt();
			h.setCreateTime(DateUtil.getSystemDateTimeObject());
			h.setCreateUser(loginUser.getId());
			h.setId(seqGenService.getSysparamHtNumber());
			h.setParamCode(dto.getParamCode());
			
		}else {
			h= basicSysparamHtRespository.getEntityById(dto.getId());
			if(h==null) {
				throw new Exception("BasicSysparamHt not found!");
			}
			h.setUpdateUser(loginUser.getId());
			h.setUpdateTime(DateUtil.getSystemDateTimeObject());
		}
		
		h.setParamName(dto.getParamName());
		h.setParamContent(dto.getParamContent());
		h.setName1(dto.getName1());
		h.setName2(dto.getName2());
		h.setName3(dto.getName3());
		h.setName4(dto.getName4());
		h.setName5(dto.getName5());
		
		basicSysparamHtRespository.save(h);
		
		ajaxReturnBean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}


	@Override
	public List<ErrorBean> validateInternalSaveSystemParamHt(BasicSysparamHtDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		if(StringUtil.isBlank(dto.getId())) {
			if(StringUtil.isBlank(dto.getParamCode())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_paramCode"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("參數代號"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
				list.add(erBean);
			}else {
				if(basicSysparamHtRespository.getEntityByCode(dto.getParamCode())!=null) {
					ErrorBean erBean = new ErrorBean();
					erBean.setErrSpanId("err_paramCode"); // 對應前端錯誤提示 的 Id 
					erBean.setLabelName("參數代號"); // 欄位名稱
					erBean.setMesg(ErrorMesgs.DATA_EXIST.getDoc());
					list.add(erBean);
				}
				if(dto.getParamCode().length()>50) {
					ErrorBean erBean = new ErrorBean();
					erBean.setErrSpanId("err_paramCode"); // 對應前端錯誤提示 的 Id 
					erBean.setLabelName("參數代號"); // 欄位名稱
					erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "50"));
					list.add(erBean);
				}else if(!ValidatorUtil.isOnlyEnglishNumber(dto.getParamCode())) {
					ErrorBean erBean = new ErrorBean();
					erBean.setErrSpanId("err_paramCode"); // 對應前端錯誤提示 的 Id 
					erBean.setLabelName("參數代號"); // 欄位名稱
					erBean.setMesg(ErrorMesgs.ONLY_CAN_INPUT_ENGLISH_NUMBER.getDoc());
					list.add(erBean);
				}
			}
		}
		
		
		if(StringUtil.isBlank(dto.getParamName())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_paramName"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName("參數名稱"); // 欄位名稱
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else {
			if(dto.getParamName().length()>50) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_paramName"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("參數名稱"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "50"));
				list.add(erBean);
			}
		}
		
		if(StringUtil.isBlank(dto.getParamContent())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_paramContent"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName("參數說明"); // 欄位名稱
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else {
			if(dto.getParamContent().length()>100) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_paramContent"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("參數說明"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "100"));
				list.add(erBean);
			}
		}
		
		
		if(!StringUtil.isBlank(dto.getName1())) {
			if(dto.getName1().length()>1000) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_name1"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("欄位1"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "1000"));
				list.add(erBean);
			}
			
		}
		
		if(!StringUtil.isBlank(dto.getName2())) {
			if(dto.getName2().length()>1000) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_name2"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("欄位2"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "1000"));
				list.add(erBean);
			}
					
		}
		
		if(!StringUtil.isBlank(dto.getName3())) {
			if(dto.getName3().length()>1000) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_name3"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("欄位3"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "1000"));
				list.add(erBean);	
			}
			
		}
		
		if(!StringUtil.isBlank(dto.getName4())) {
			if(dto.getName4().length()>1000) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_name4"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("欄位4"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "1000"));
				list.add(erBean);	
			}
			
		}
		
		if(!StringUtil.isBlank(dto.getName5())) {
			if(dto.getName5().length()>1000) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_name5"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("欄位5"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "1000"));
				list.add(erBean);	
			}
			
		}
		
		return list;
	}


	@Override
	public JsonObject ajaxGetSystemParamHtById(BasicSysparamHtDto dto) {
		JsonObject jo = new JsonObject();
		BasicSysparamHt h =basicSysparamHtRespository.getEntityById(dto.getId());
		jo.addProperty("id", h.getId());
		jo.addProperty("paramCode", h.getParamCode());
		jo.addProperty("paramName", h.getParamName());
		jo.addProperty("paramContent", h.getParamContent());
		jo.addProperty("name1", StringUtil.isBlank(h.getName1())?"":h.getName1());
		jo.addProperty("name2", StringUtil.isBlank(h.getName2())?"":h.getName2());
		jo.addProperty("name3", StringUtil.isBlank(h.getName3())?"":h.getName3());
		jo.addProperty("name4", StringUtil.isBlank(h.getName4())?"":h.getName4());
		jo.addProperty("name5", StringUtil.isBlank(h.getName5())?"":h.getName5());
		return jo;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxRemoveSystemParamHt(BasicSysparamHtDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		basicSysparamHtRespository.delete(dto.getId());
		basicSysparamDlRespository.deleteByProperty("htId", dto.getId());
		ajaxReturnBean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}


	@Override
	public List<ErrorBean> validateInternalRemoveSystemParamHt(BasicSysparamHtDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		List<BasicSysparamDl> dlList = basicSysparamDlRespository.findByJPQL("SELECT d FROM BasicSysparamDl d WHERE d.htId=? AND d.isDelete !='Y' ", dto.getId());
		if(dlList!=null && dlList.size()>0) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("nona"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName("該參數主檔"); // 欄位名稱
			erBean.setMesg(ErrorMesgs.DATA_EXIST_DL_CAN_NOT_REMOVE.getDoc());
			list.add(erBean);	
		}
		return list;
	}


	@Override
	public void paperDetailPage(BasicSysparamHtDto dto) {
		BasicSysparamHt h =basicSysparamHtRespository.getEntityById(dto.getId());
		BeanUtils.copyProperties(h,dto);
		
	}


	@Override
	public String systemParamDlGridList(BasicSysparamHtDto dto) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		
		params.put("htId",dto.getId());
		
		
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT * FROM sysparam_dl WHERE 1=1  ");
		sql.append("  AND ht_id = :htId ");
		sql.append("  ORDER BY is_delete ");
		List<Map<String, Object>> list=basicSysparamDlRespository.findListMapBySQL_map(sql.toString(), params);
		
		return PageUtil.transToGridDataSource(list,dto); 
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxSaveSystemParamDl(BasicSysparamDlDto dto, UserDto loginUser) throws Exception {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		
		BasicSysparamDl dl =null;
		if(StringUtil.isBlank(dto.getId())) {
			dl = new BasicSysparamDl();
			dl.setCreateUser(loginUser.getId());
			dl.setCreateTime(DateUtil.getSystemDateTimeObject());
			dl.setId(seqGenService.getSysparamDlNumber());
			dl.setParamCode(dto.getParamCode());
			dl.setHtId(dto.getHtId());
		}else {
			dl = basicSysparamDlRespository.getEntityById(dto.getId());
			if(dl==null) {
				throw new Exception("BasicSysparamDl not found!");
			}
			dl.setUpdateUser(loginUser.getId());
			dl.setUpdateTime(DateUtil.getSystemDateTimeObject());
			
		}
		dl.setParam1(dto.getParam1());
		dl.setParam2(dto.getParam2());
		dl.setParam3(dto.getParam3());
		dl.setParam4(dto.getParam4());
		dl.setParam5(dto.getParam5());
		dl.setIsDelete(dto.getIsDelete());
		
		basicSysparamDlRespository.save(dl);
		
		ajaxReturnBean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}


	@Override
	public List<ErrorBean> validateInternalSaveSystemParamDl(BasicSysparamDlDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		if(!StringUtil.isBlank(dto.getParam1())) {
			if(dto.getParam1().length()>1000) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_param1"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("欄位1"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "1000"));
				list.add(erBean);	
			}
			
		}
		
		if(!StringUtil.isBlank(dto.getParam2())) {
			if(dto.getParam2().length()>1000) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_param2"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("欄位2"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "1000"));
				list.add(erBean);	
			}
			
		}
		
		if(!StringUtil.isBlank(dto.getParam3())) {
			if(dto.getParam3().length()>1000) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_param3"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("欄位3"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "1000"));
				list.add(erBean);	
			}
			
		}
		
		if(!StringUtil.isBlank(dto.getParam4())) {
			if(dto.getParam4().length()>1000) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_param4"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("欄位4"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "1000"));
				list.add(erBean);	
			}
			
		}
		
		if(!StringUtil.isBlank(dto.getParam5())) {
			if(dto.getParam5().length()>1000) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_param5"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("欄位5"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "1000"));
				list.add(erBean);	
			}
			
		}
		
		return list;
	}


	@Override
	public JsonObject ajaxGetSystemParamDlById(BasicSysparamDlDto dto) {
		JsonObject jo = new JsonObject();
		BasicSysparamDl dl =basicSysparamDlRespository.getEntityById(dto.getId());
		jo.addProperty("id", dl.getId());
		jo.addProperty("htId", dl.getHtId());
		jo.addProperty("paramCode", dl.getParamCode());
		jo.addProperty("param1", dl.getParam1());
		jo.addProperty("param2", dl.getParam2());
		jo.addProperty("param3", dl.getParam3());
		jo.addProperty("param4", dl.getParam4());
		jo.addProperty("param5", dl.getParam5());
		jo.addProperty("isDelete", dl.getIsDelete());
		jo.addProperty("isDeleteText", dl.getIsDelete().equals("Y")?"已刪除":"啟用中");
		
		return jo;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxRemoveSystemParamDl(BasicSysparamDlDto dto, UserDto loginUser) throws Exception {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		BasicSysparamDl dl = basicSysparamDlRespository.getEntityById(dto.getId());
		if(dl==null) {
			throw new Exception("BasicSysparamDl not found!");
		}
		dl.setIsDelete(dto.getIsDelete());
		dl.setUpdateUser(loginUser.getId());
		dl.setUpdateTime(DateUtil.getSystemDateTimeObject());
		basicSysparamDlRespository.save(dl);
		ajaxReturnBean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}


	@Override
	public List<ErrorBean> validateInternalRemoveSystemParamDl(BasicSysparamDlDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		return list;
	}
	
	
	
	
	

}
