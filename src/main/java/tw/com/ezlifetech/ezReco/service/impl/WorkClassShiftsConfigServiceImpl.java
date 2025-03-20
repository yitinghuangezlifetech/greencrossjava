package tw.com.ezlifetech.ezReco.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.dto.WareHouseProfDlDto;
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftDlDto;
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftHtDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.CommonStatus;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.model.ComProfHt;
import tw.com.ezlifetech.ezReco.model.RefCompWarehouse;
import tw.com.ezlifetech.ezReco.model.WareHouseProf;
import tw.com.ezlifetech.ezReco.model.WorkClassShiftDl;
import tw.com.ezlifetech.ezReco.model.WorkClassShiftHt;
import tw.com.ezlifetech.ezReco.respository.ComProfHtRespository;
import tw.com.ezlifetech.ezReco.respository.RefCompWarehouseRespository;
import tw.com.ezlifetech.ezReco.respository.WareHouseProfDlRespository;
import tw.com.ezlifetech.ezReco.respository.WareHouseProfRespository;
import tw.com.ezlifetech.ezReco.respository.WorkClassShiftDlRespository;
import tw.com.ezlifetech.ezReco.respository.WorkClassShiftHtRespository;
import tw.com.ezlifetech.ezReco.service.WorkClassShiftsConfigService;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;
import tw.com.ezlifetech.ezReco.util.ValidatorUtil;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WorkClassShiftsConfigServiceImpl implements WorkClassShiftsConfigService{

	
	@Autowired
	private WorkClassShiftHtRespository workClassShiftHtRespository;
	
	@Autowired
	private WorkClassShiftDlRespository workClassShiftDlRespository;
	
	@Autowired
	private ComProfHtRespository comProfHtRespository;
	
	@Autowired
	private SeqGenService seqGenService;
	
	@Autowired
	private WareHouseProfRespository wareHouseProfRespository;
	
	@Autowired
	private WareHouseProfDlRespository wareHouseProfDlRespository;
	
	@Autowired
	private RefCompWarehouseRespository refCompWarehouseRespository;
	
	
	@Override
	public void paper(Model model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paperEdit(Model model, WorkClassShiftHtDto dto,UserDto loginUser) throws Exception {
		
		
		
		if(StringUtil.isBlank(dto.getId())) {
			
		}else {
			WorkClassShiftHt ht = workClassShiftHtRespository.getEntityById(dto.getId());
			BeanUtils.copyProperties(dto,ht);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			if(dto.getSdate()!=null) {
				dto.setSdateText(sdf.format(dto.getSdate()));
			}
			if(dto.getEdate()!=null) {
				dto.setEdateText(sdf.format(dto.getEdate()));
			}
		}
		
		
		model.addAttribute("whIdSelectList", getWhIdSelectList());
		model.addAttribute("compIdSelectList", getCompIdSelectList(loginUser.getId()));
		
		
		
		RefCompWarehouse  r = null;
		r = refCompWarehouseRespository.getEntityByJPQL("SELECT r FROM RefCompWarehouse r WHERE r.wcsId=?", dto.getId());
		if(r!=null) {
			
			
			
			WareHouseProf  w = wareHouseProfRespository.getEntityByWhId(r.getWhId());
			dto.setWhId(w.getId());
			
			dto.setWhDlId(r.getWhDlId());
			
			
		}
		
	}

	private Object getWhIdSelectList() {
		List<Map<String,Object>> whList = new  ArrayList<>();
		whList = wareHouseProfRespository.getAllwhIdAndNameListMap();
		
		return whList;
	}
	private List<Map<String,Object>> getCompIdSelectList(String userId) {
		List<Map<String,Object>> compList = new  ArrayList<>();
		compList = comProfHtRespository.getCompIdAndNameListMapByUserId(userId);
		return compList;
	}

	@Override
	
	public List<ErrorBean> validateInternalSaveHt(WorkClassShiftHtDto dto) throws Exception {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		
		if(!StringUtil.isBlank(dto.getId()))
			if(!isHtStatusCanSave(dto.getId())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("nona"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName(""); // 欄位名稱
				erBean.setMesg("合約目前狀態無法修改資料");
				list.add(erBean);
				return list;
			}
		
		
		if(StringUtil.isBlank(dto.getConfigName())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_configName"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName("合約名稱"); // 欄位名稱
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else {
			if(dto.getConfigName().length()>100) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_configName"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("合約名稱"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.OUT_OF_RANGE.getDoc().replace("{!range}", "100"));
				list.add(erBean);
			}
		}
		
		if(StringUtil.isBlank(dto.getCompId())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_compId"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName(""); // 欄位名稱
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "所屬公司"));
			list.add(erBean);
		}
		
		
		if(StringUtil.isBlank(dto.getSdateText())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_sdateText"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName("合約起日"); // 欄位名稱
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}
		
		
		if(StringUtil.isBlank(dto.getEdateText())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_edateText"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName("合約迄日"); // 欄位名稱
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}
		
		if(!StringUtil.isBlank(dto.getSdateText())&& !StringUtil.isBlank(dto.getEdateText())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date sdate = sdf.parse(dto.getSdateText());
			Date edate = sdf.parse(dto.getEdateText());
			
			if(edate.compareTo(sdate)<=0) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_edateText"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("合約迄日"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.MUST_BIGGER_THEN.getDoc().replace("{!label}", "合約起日"));
				list.add(erBean);
			}
			
		}
		
		
		
		if(StringUtil.isBlank(dto.getWhId())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_whId"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName(""); // 欄位名稱
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "倉庫"));
			list.add(erBean);
		}
		
		if(StringUtil.isBlank(dto.getWhDlId())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_whDlId"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName(""); // 欄位名稱
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "棚別"));
			list.add(erBean);
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxSaveHt(WorkClassShiftHtDto dto, UserDto loginUser) throws Exception {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		JsonObject jo = new JsonObject();
		WorkClassShiftHt ht = null;
		
		if(StringUtil.isBlank(dto.getId())) {
			ht = new WorkClassShiftHt();
			ht.setId(seqGenService.getSystemTimeRandomNumber());
			ht.setOwnerId(loginUser.getOnwerId());
			ht.setCreateUser(loginUser.getId());
			ht.setCreateTime(DateUtil.getSystemDateTimeObject());
			ht.setStatus(CommonStatus.WAIT_TO_BE_PROCESSED.getStatusCode());
		}else {
			ht = workClassShiftHtRespository.getEntityById(dto.getId());
			ht.setUpdateUser(loginUser.getId());
			ht.setUpdateTime(DateUtil.getSystemDateTimeObject());
		}
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date sdate = sdf.parse(dto.getSdateText());
		Date edate = sdf.parse(dto.getEdateText());
		ht.setSdate(sdate);
		ht.setEdate(edate);
		ht.setPrincipalId(dto.getPrincipalId());
		ht.setSiteSuperId(dto.getSiteSuperId());
		ht.setConfigName(dto.getConfigName());
		ht.setCompId(dto.getCompId());
		
		workClassShiftHtRespository.save(ht);
		
		
		RefCompWarehouse  r = null;
		r = refCompWarehouseRespository.getEntityByJPQL("SELECT r FROM RefCompWarehouse r WHERE r.wcsId=?", ht.getId());
		if(r==null) {
			r = new RefCompWarehouse();
			r.setId(seqGenService.getSystemTimeRandomNumber());
			r.setCreateTime(DateUtil.getSystemDateTimeObject());
			r.setCreateUser(loginUser.getId());
		}else {
			r.setUpdateTime(DateUtil.getSystemDateTimeObject());
			r.setUpdateUser(loginUser.getId());
		}
		
		String compCode = "";
		ComProfHt com = comProfHtRespository.getEntityById(dto.getCompId());
		if(com!=null) {
			if(!StringUtil.isBlank(com.getComCode())) {
				compCode = com.getComCode();
			}
		}
		
		r.setComId(compCode);
		r.setWcsId(ht.getId());
		
		String whId="";
		WareHouseProf  wh =wareHouseProfRespository.getEntityById(dto.getWhId());
		if(wh!=null) {
			if(!StringUtil.isBlank(wh.getWhId())) {
				whId = wh.getWhId();
			}
		}
		r.setWhId(whId);
		r.setWhDlId(dto.getWhDlId());
		//r.setStrength(dto.getStrength());
		refCompWarehouseRespository.save(r);
		
		jo.addProperty("htId", ht.getId());
		
		ajaxReturnBean.setValue(jo);
		ajaxReturnBean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}

	@Override
	public String ajaxDlGridList(WorkClassShiftHtDto dto, UserDto loginUser) {
	
		List<Map<String, Object>>  list = workClassShiftDlRespository.ajaxDlGridList(dto);
		
		for(Map<String, Object> m : list) {
			if("time".equals(m.get("contract_type").toString())) {
				m.put("contract_type_text", "計時");
			}else {
				m.put("contract_type_text", "計件");
			}
		}
		
		return PageUtil.transToGridDataSource(list,dto); 
	}

	@Override
	public String ajaxHtGridList(WorkClassShiftHtDto dto, UserDto loginUser) {
		dto.setOwnerId(loginUser.getOnwerId());
		List<Map<String, Object>>  res = workClassShiftHtRespository.ajaxHtGridList(dto);
		for(Map<String, Object> m:res) {
			m.put("status_text", CommonStatus.getStstusTextByCode(m.get("status").toString()));
		}
		return PageUtil.transToGridDataSource(res,dto); 
	}

	@Override
	public List<ErrorBean> validateInternalRemoveHt(WorkClassShiftHtDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxRemoveHt(WorkClassShiftHtDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		WorkClassShiftHt ht = workClassShiftHtRespository.getEntityById(dto.getId());
		
		if(ht!=null) {
			//List<WorkClassShiftDl>  dlList = workClassShiftDlRespository.findEntityListByJPQL("SELECT d FROM WorkClassShiftDl d WHERE htId=?", dto.getId());
			//for(WorkClassShiftDl d : dlList) {
				//workClassShiftDlRespository.delete(d);
			//}
			//workClassShiftHtRespository.delete(ht);
			
			ht.setStatus(CommonStatus.DELETE.getStatusCode());
		}
		
		
		
		ajaxReturnBean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxSaveDl(WorkClassShiftDlDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		WorkClassShiftDl dl = null;
		if(StringUtil.isBlank(dto.getId())) {
			dl = new WorkClassShiftDl();
			dl.setHtId(dto.getHtId());
			dl.setId(seqGenService.getSystemTimeRandomNumber());
			dl.setCreateUser(loginUser.getId());
			dl.setCreateTime(DateUtil.getSystemDateTimeObject());
			
			List<WorkClassShiftDl> list = workClassShiftDlRespository.findEntityByHtId(dto);
			if(list==null || list.size()==0) {
				dl.setIsMain("Y");
			}else {
				dl.setIsMain("N");
			}
			
		}else {
			dl = workClassShiftDlRespository.getEntityById(dto.getId());
			dl.setUpdateUser(loginUser.getId());
			dl.setUpdateTime(DateUtil.getSystemDateTimeObject());
		}
		dl.setClassName(dto.getClassName());
		dl.setSdateTime(dto.getSdateTime());
		dl.setEdateTime(dto.getEdateTime());
		
		
		dl.setContractType(dto.getContractType());
		
		if("time".equals(dto.getContractType())||"time2".equals(dto.getContractType())) {
			
			dl.setSalary(dto.getSalary());
			if("time2".equals(dto.getContractType())) {
				
				BigDecimal s = new BigDecimal (dto.getSalary());
				BigDecimal s133 = new BigDecimal ("1.33");
				BigDecimal s166 = new BigDecimal ("1.66");
				
				dl.setExtendIn(s.multiply(s133).toString());
				dl.setExtendOut( s.multiply(s166).toString());
			}else {
				dl.setExtendIn(dto.getExtendIn());
				dl.setExtendOut(dto.getExtendOut());
			}
			
			dl.setNightAdd(dto.getNightAdd());
			dl.setUnit(dto.getUnit());
			dl.setRemuneration(null);
		}else if("package".equals(dto.getContractType())) {
			dl.setSalary(null);
			dl.setExtendIn(null);
			dl.setExtendOut(null);
			dl.setNightAdd(null);
			dl.setUnit(null);
			dl.setRemuneration(dto.getRemuneration());
		}
		
		
		workClassShiftDlRespository.save(dl);
		ajaxReturnBean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}

	@Override
	public List<ErrorBean> validateInternalSaveDl(WorkClassShiftDlDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		if(!StringUtil.isBlank(dto.getHtId()))
			if(!isHtStatusCanSave(dto.getHtId())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("nona"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName(""); // 欄位名稱
				erBean.setMesg("合約目前狀態無法修改資料");
				list.add(erBean);
				return list;
			}
		
		
		if(StringUtil.isBlank(dto.getClassName())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_className"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName("班別名稱"); // 欄位名稱
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else {
			if(dto.getClassName().length()>100) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_className"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("班別名稱"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.OUT_OF_RANGE.getDoc().replace("{!range}", "100"));
				list.add(erBean);
			}
		}
		
		
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
		
		
		
		if(StringUtil.isBlank(dto.getContractType())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_contractType"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName(""); // 欄位名稱
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "計時/計件"));
			list.add(erBean);
		}else {
			if("time".equals(dto.getContractType()) || "time2".equals(dto.getContractType())) {
				if(StringUtil.isBlank(dto.getSalary())) {
					ErrorBean erBean = new ErrorBean();
					erBean.setErrSpanId("err_salary"); // 對應前端錯誤提示 的 Id 
					erBean.setLabelName("每單位薪水"); // 欄位名稱
					erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
					list.add(erBean);
				}else {
					if(!ValidatorUtil.isNumber(dto.getSalary())) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_salary"); // 對應前端錯誤提示 的 Id 
						erBean.setLabelName("每單位薪水"); // 欄位名稱
						erBean.setMesg(ErrorMesgs.PLZ_INPUT_NUMBER.getDoc());
						list.add(erBean);
					} else if(Double.parseDouble(dto.getSalary())<0) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_salary"); // 對應前端錯誤提示 的 Id 
						erBean.setLabelName("每單位薪水"); // 欄位名稱
						erBean.setMesg(ErrorMesgs.MUST_BIGGER_THEN.getDoc().replace("{!label}", "0"));
						list.add(erBean);
					} else if(dto.getSalary().length()>30) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_salary"); // 對應前端錯誤提示 的 Id 
						erBean.setLabelName("每單位薪水"); // 欄位名稱
						erBean.setMesg(ErrorMesgs.OUT_OF_RANGE.getDoc().replace("{!range}", "30"));
						list.add(erBean);
					}
					
				}
				
				if(!"time2".equals(dto.getContractType())) {
					if(StringUtil.isBlank(dto.getExtendIn())) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_extendIn"); // 對應前端錯誤提示 的 Id 
						erBean.setLabelName("延長工時2小時以內"); // 欄位名稱
						erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
						list.add(erBean);
					}else {
						if(!ValidatorUtil.isNumber(dto.getExtendIn())) {
							ErrorBean erBean = new ErrorBean();
							erBean.setErrSpanId("err_extendIn"); // 對應前端錯誤提示 的 Id 
							erBean.setLabelName("延長工時2小時以內"); // 欄位名稱
							erBean.setMesg(ErrorMesgs.PLZ_INPUT_NUMBER.getDoc());
							list.add(erBean);
						} else if(Double.parseDouble(dto.getExtendIn())<0) {
							ErrorBean erBean = new ErrorBean();
							erBean.setErrSpanId("err_extendIn"); // 對應前端錯誤提示 的 Id 
							erBean.setLabelName("延長工時2小時以內"); // 欄位名稱
							erBean.setMesg(ErrorMesgs.MUST_BIGGER_THEN.getDoc().replace("{!label}", "0"));
							list.add(erBean);
						} else if(dto.getExtendIn().length()>30) {
							ErrorBean erBean = new ErrorBean();
							erBean.setErrSpanId("err_extendIn"); // 對應前端錯誤提示 的 Id 
							erBean.setLabelName("延長工時2小時以內"); // 欄位名稱
							erBean.setMesg(ErrorMesgs.OUT_OF_RANGE.getDoc().replace("{!range}", "30"));
							list.add(erBean);
						}
						
					}
				}
					
				
				if(!"time2".equals(dto.getContractType())) {
					if(StringUtil.isBlank(dto.getExtendOut())) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_extendOut"); // 對應前端錯誤提示 的 Id 
						erBean.setLabelName("延長工時2小時以上"); // 欄位名稱
						erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
						list.add(erBean);
					}else {
						if(!ValidatorUtil.isNumber(dto.getExtendOut())) {
							ErrorBean erBean = new ErrorBean();
							erBean.setErrSpanId("err_extendOut"); // 對應前端錯誤提示 的 Id 
							erBean.setLabelName("延長工時2小時以上"); // 欄位名稱
							erBean.setMesg(ErrorMesgs.PLZ_INPUT_NUMBER.getDoc());
							list.add(erBean);
						} else if(Double.parseDouble(dto.getExtendOut())<0) {
							ErrorBean erBean = new ErrorBean();
							erBean.setErrSpanId("err_extendOut"); // 對應前端錯誤提示 的 Id 
							erBean.setLabelName("延長工時2小時以上"); // 欄位名稱
							erBean.setMesg(ErrorMesgs.MUST_BIGGER_THEN.getDoc().replace("{!label}", "0"));
							list.add(erBean);
						} else if(dto.getExtendOut().length()>30) {
							ErrorBean erBean = new ErrorBean();
							erBean.setErrSpanId("err_extendOut"); // 對應前端錯誤提示 的 Id 
							erBean.setLabelName("延長工時2小時以上"); // 欄位名稱
							erBean.setMesg(ErrorMesgs.OUT_OF_RANGE.getDoc().replace("{!range}", "30"));
							list.add(erBean);
						}
						
					}
				}
					
				
				if(StringUtil.isBlank(dto.getNightAdd())) {
					ErrorBean erBean = new ErrorBean();
					erBean.setErrSpanId("err_nightAdd"); // 對應前端錯誤提示 的 Id 
					erBean.setLabelName("夜間加計"); // 欄位名稱
					erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
					list.add(erBean);
				}else {
					if(!ValidatorUtil.isNumber(dto.getNightAdd())) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_nightAdd"); // 對應前端錯誤提示 的 Id 
						erBean.setLabelName("夜間加計"); // 欄位名稱
						erBean.setMesg(ErrorMesgs.PLZ_INPUT_NUMBER.getDoc());
						list.add(erBean);
					} else if(Double.parseDouble(dto.getNightAdd())<0) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_nightAdd"); // 對應前端錯誤提示 的 Id 
						erBean.setLabelName("夜間加計"); // 欄位名稱
						erBean.setMesg(ErrorMesgs.MUST_BIGGER_THEN.getDoc().replace("{!label}", "0"));
						list.add(erBean);
					} else if(dto.getNightAdd().length()>30) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_nightAdd"); // 對應前端錯誤提示 的 Id 
						erBean.setLabelName("夜間加計"); // 欄位名稱
						erBean.setMesg(ErrorMesgs.OUT_OF_RANGE.getDoc().replace("{!range}", "30"));
						list.add(erBean);
					}
					
				}
				
				
				if(StringUtil.isBlank(dto.getUnit())) {
					ErrorBean erBean = new ErrorBean();
					erBean.setErrSpanId("err_unit"); // 對應前端錯誤提示 的 Id 
					erBean.setLabelName(""); // 欄位名稱
					erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "單位"));
					list.add(erBean);
				}
			}else if("package".equals(dto.getContractType())) {
				if(StringUtil.isBlank(dto.getRemuneration())) {
					ErrorBean erBean = new ErrorBean();
					erBean.setErrSpanId("err_remuneration"); // 對應前端錯誤提示 的 Id 
					erBean.setLabelName("勞務報酬單價"); // 欄位名稱
					erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
					list.add(erBean);
				}else {
					if(!ValidatorUtil.isNumber(dto.getRemuneration())) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_remuneration"); // 對應前端錯誤提示 的 Id 
						erBean.setLabelName("勞務報酬單價"); // 欄位名稱
						erBean.setMesg(ErrorMesgs.PLZ_INPUT_NUMBER.getDoc());
						list.add(erBean);
					} else if(Double.parseDouble(dto.getRemuneration())<0) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_remuneration"); // 對應前端錯誤提示 的 Id 
						erBean.setLabelName("勞務報酬單價"); // 欄位名稱
						erBean.setMesg(ErrorMesgs.MUST_BIGGER_THEN.getDoc().replace("{!label}", "0"));
						list.add(erBean);
					} else if(dto.getRemuneration().length()>30) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_remuneration"); // 對應前端錯誤提示 的 Id 
						erBean.setLabelName("勞務報酬單價"); // 欄位名稱
						erBean.setMesg(ErrorMesgs.OUT_OF_RANGE.getDoc().replace("{!range}", "30"));
						list.add(erBean);
					}
					
				}
			}
		}
		
		
		
		
		
		return list;
	}

	private boolean isHtStatusCanSave(String htId) {
		WorkClassShiftHt ht =workClassShiftHtRespository.getEntityById(htId);
		if(CommonStatus.AGREE.getStatusCode().equals(ht.getStatus())
				|| CommonStatus.REFUSE.getStatusCode().equals(ht.getStatus())
				) {
			return false;
		}
		return true;
	}

	@Override
	public AjaxReturnBean ajaxGetDl(WorkClassShiftDlDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		JsonObject jo = new JsonObject();
		WorkClassShiftDl dl =workClassShiftDlRespository.getEntityById(dto.getId());
		
		jo.addProperty("id", dl.getId());
		jo.addProperty("className", dl.getClassName());
		jo.addProperty("sdateTime", dl.getSdateTime());
		jo.addProperty("edateTime", dl.getEdateTime());
		jo.addProperty("salary", dl.getSalary());
		jo.addProperty("unit", dl.getUnit());
		jo.addProperty("contractType", dl.getContractType());
		jo.addProperty("extendIn", dl.getExtendIn());
		jo.addProperty("extendOut", dl.getExtendOut());
		jo.addProperty("nightAdd", dl.getNightAdd());
		jo.addProperty("remuneration", dl.getRemuneration());
		
		ajaxReturnBean.setValue(jo);
		return ajaxReturnBean;
	}

	@Override
	public List<ErrorBean> validateInternalGetDl(WorkClassShiftDlDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		WorkClassShiftDl dl =workClassShiftDlRespository.getEntityById(dto.getId());
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
	public AjaxReturnBean ajaxRemoveDl(WorkClassShiftDlDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		WorkClassShiftDl dl =workClassShiftDlRespository.getEntityById(dto.getId());
		if(dl!=null) {
			workClassShiftDlRespository.delete(dl);
		}
		
		ajaxReturnBean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}

	@Override
	public List<ErrorBean> validateInternalRemoveDl(WorkClassShiftDlDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		if(!StringUtil.isBlank(dto.getHtId()))
			if(!isHtStatusCanSave(dto.getHtId())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("nona"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName(""); // 欄位名稱
				erBean.setMesg("合約目前狀態無法修改資料");
				list.add(erBean);
				return list;
			}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxSetMainDl(WorkClassShiftDlDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		
		List<WorkClassShiftDl> dlList = workClassShiftDlRespository.findEntityByHtId(dto);
		if(dlList!=null) {
			for(WorkClassShiftDl d : dlList) {
				d.setIsMain("N");
				workClassShiftDlRespository.save(d);
			}
			workClassShiftDlRespository.flush();
		}
		WorkClassShiftDl d  = workClassShiftDlRespository.getEntityById(dto.getId());
		d.setIsMain("Y");
		workClassShiftDlRespository.save(d);
		ajaxReturnBean.setMessage(AjaxMesgs.APPLY_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}

	@Override
	public List<ErrorBean> validateInternalSetMainDl(WorkClassShiftDlDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		if(!StringUtil.isBlank(dto.getHtId()))
			if(!isHtStatusCanSave(dto.getHtId())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("nona"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName(""); // 欄位名稱
				erBean.setMesg("合約目前狀態無法修改資料");
				list.add(erBean);
				return list;
			}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxDisSetMainDl(WorkClassShiftDlDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		
		WorkClassShiftDl d  = workClassShiftDlRespository.getEntityById(dto.getId());
		d.setIsMain("N");
		workClassShiftDlRespository.save(d);
		ajaxReturnBean.setMessage(AjaxMesgs.APPLY_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}

	@Override
	public List<ErrorBean> validateInternalDisSetMainDl(WorkClassShiftDlDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		if(!StringUtil.isBlank(dto.getHtId()))
			if(!isHtStatusCanSave(dto.getHtId())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("nona"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName(""); // 欄位名稱
				erBean.setMesg("合約目前狀態無法修改資料");
				list.add(erBean);
				return list;
			}
		return list;
	}

	@Override
	public String ajaxDlSelectList(WareHouseProfDlDto dto, UserDto loginUser) {
		// TODO Auto-generated method stub
		List<Map<String, Object>>  res = new ArrayList<Map<String, Object>>();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("dl_id", "");
		m.put("dl_name", "請選擇");
		res.add(m);
		res.addAll(wareHouseProfDlRespository.getLogistcDlSelectList(dto));
		return PageUtil.transToGridDataSource(res,dto); 
	}

	@Override
	public AjaxReturnBean ajaxTimes2PageCount(WorkClassShiftDlDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		JsonObject jo = new JsonObject();
		boolean isOk=false;
		if(!StringUtil.isBlank(dto.getSalary())) {
			if(ValidatorUtil.isNumber(dto.getSalary())) {
				isOk=true;
				BigDecimal s = new BigDecimal (dto.getSalary());
				BigDecimal s133 = new BigDecimal ("1.33");
				BigDecimal s166 = new BigDecimal ("1.66");
				
				jo.addProperty("extendIn", s.multiply(s133).toString());
				jo.addProperty("extendOut", s.multiply(s166).toString());
				
			}
		}
		
		jo.addProperty("isOk", isOk?"Y":"N");
		ajaxReturnBean.setValue(jo);
		return ajaxReturnBean;
	}
	
}
