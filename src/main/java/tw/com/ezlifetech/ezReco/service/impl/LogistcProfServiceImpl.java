package tw.com.ezlifetech.ezReco.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.common.service.SeqGenService;
import tw.com.ezlifetech.ezReco.dto.RefCompWarehouseDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.dto.WareHouseProfDlDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.model.ComProfHt;
import tw.com.ezlifetech.ezReco.model.RefCompWarehouse;
import tw.com.ezlifetech.ezReco.model.WareHouseProf;
import tw.com.ezlifetech.ezReco.respository.ComProfHtRespository;
import tw.com.ezlifetech.ezReco.respository.RefCompWarehouseRespository;
import tw.com.ezlifetech.ezReco.respository.WareHouseProfDlRespository;
import tw.com.ezlifetech.ezReco.respository.WareHouseProfRespository;
import tw.com.ezlifetech.ezReco.respository.WorkClassShiftHtRespository;
import tw.com.ezlifetech.ezReco.service.LogistcProfService;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;
import tw.com.ezlifetech.ezReco.util.ValidatorUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LogistcProfServiceImpl implements LogistcProfService{

	@Autowired
	private RefCompWarehouseRespository refCompWarehouseRespository;
	
	@Autowired
	private WareHouseProfDlRespository wareHouseProfDlRespository;
	
	@Autowired
	private WareHouseProfRespository wareHouseProfRespository;
	
	@Autowired
	private ComProfHtRespository comProfHtRespository;
	
	@Autowired
	private WorkClassShiftHtRespository workClassShiftHtRespository;
	
	@Autowired
	private SeqGenService seqGenService;
	
	
	@Override
	public String ajaxGridList(RefCompWarehouseDto dto, UserDto loginUser) {
		
		return PageUtil.transToGridDataSource(refCompWarehouseRespository.getLogistcProfGridList(dto),dto); 
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
	public String ajaxWcsSelectList(RefCompWarehouseDto dto, UserDto loginUser) {
		List<Map<String, Object>>  res = new ArrayList<Map<String, Object>>();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("id", "");
		m.put("config_name", "請選擇");
		res.add(m);
		res.addAll(workClassShiftHtRespository.getLogistcWcsList(dto));
		return PageUtil.transToGridDataSource(res,dto);
	}

	@Override
	public void paperPage(Model model, UserDto loginUser) {
		
		
		
		model.addAttribute("compIdSelectList", getCompIdSelectList());
		model.addAttribute("whIdSelectList", getWhIdSelectList());
	}

	private Object getWhIdSelectList() {
		List<Map<String,Object>> whList = new  ArrayList<>();
		whList = wareHouseProfRespository.getAllwhIdAndNameListMap();
		
		return whList;
	}


	private List<Map<String,Object>> getCompIdSelectList() {
		List<Map<String,Object>> compList = new  ArrayList<>();
		compList = comProfHtRespository.getAllCompIdAndNameListMap();
		return compList;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxSave(RefCompWarehouseDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		RefCompWarehouse  r = null;
		if(StringUtil.isBlank(dto.getId())) {
			r = new RefCompWarehouse();
			r.setId(seqGenService.getSystemTimeRandomNumber());
			r.setCreateTime(DateUtil.getSystemDateTimeObject());
			r.setCreateUser(loginUser.getId());
		}else {
			r = refCompWarehouseRespository.getEntityById(dto.getId());
			r.setUpdateTime(DateUtil.getSystemDateTimeObject());
			r.setUpdateUser(loginUser.getId());
		}
		String compCode = "";
		ComProfHt com = comProfHtRespository.getEntityById(dto.getComId());
		if(com!=null) {
			if(!StringUtil.isBlank(com.getComCode())) {
				compCode = com.getComCode();
			}
		}
		
		r.setComId(compCode);
		r.setWcsId(dto.getWcsId());
		
		String whId="";
		WareHouseProf  wh =wareHouseProfRespository.getEntityById(dto.getWhId());
		if(wh!=null) {
			if(!StringUtil.isBlank(wh.getWhId())) {
				whId = wh.getWhId();
			}
		}
		r.setWhId(whId);
		r.setWhDlId(dto.getWhDlId());
		r.setStrength(dto.getStrength());
		refCompWarehouseRespository.save(r);
		ajaxReturnBean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}


	@Override
	public List<ErrorBean> validateInternalSave(RefCompWarehouseDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		if(StringUtil.isBlank(dto.getComId())){
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_comId"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName(""); // 欄位名稱
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "廠商公司"));
			list.add(erBean);
		}		
		
		if(StringUtil.isBlank(dto.getWhId())){
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_whId"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName(""); // 欄位名稱
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "倉庫"));
			list.add(erBean);
		}	
		
		if(StringUtil.isBlank(dto.getWhDlId())){
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_whDlId"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName(""); // 欄位名稱
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "棚別"));
			list.add(erBean);
		}	
		
		if(StringUtil.isBlank(dto.getWcsId())){
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_wcsId"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName(""); // 欄位名稱
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "合約"));
			list.add(erBean);
		}	
		
		
		if(StringUtil.isBlank(dto.getStrength())){
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_strength"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName("編制"); // 欄位名稱
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		} else {
			if(dto.getStrength().length()>30) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_strength"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("編制"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "30"));
				list.add(erBean);
			}else if(!ValidatorUtil.isNumber(dto.getStrength())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_strength"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("編制"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.PLZ_INPUT_NUMBER.getDoc());
				list.add(erBean);
			}
		}
		
		
		if(refCompWarehouseRespository.isLogistcRepaet(dto)) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("nona"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName("協力廠商"); // 欄位名稱
			erBean.setMesg(ErrorMesgs.DATA_EXIST.getDoc());
			list.add(erBean);
		}
		
		
		
		
		return list;
	}


	@Override
	public AjaxReturnBean ajaxGetRefData(RefCompWarehouseDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		JsonObject jo = new JsonObject();
		RefCompWarehouse  r = refCompWarehouseRespository.getEntityById(dto.getId());
		ComProfHt c =comProfHtRespository.getEntityByComCode(r.getComId());
		jo.addProperty("comId", c.getId());
		jo.addProperty("wcsId", r.getWcsId());
		WareHouseProf  w = wareHouseProfRespository.getEntityByWhId(r.getWhId());
		jo.addProperty("whId", w.getId());
		
		
		jo.addProperty("whDlId", r.getWhDlId());
		
		jo.addProperty("strength", r.getStrength());
		
		
		ajaxReturnBean.setValue(jo);
		return ajaxReturnBean;
	}


	@Override
	public List<ErrorBean> validateInternalGetRefData(RefCompWarehouseDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		return list;
	}


	@Override
	public List<ErrorBean> validateInternalRemove(RefCompWarehouseDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		return list;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxRemove(RefCompWarehouseDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		RefCompWarehouse  r =refCompWarehouseRespository.getEntityById(dto.getId());
		
		refCompWarehouseRespository.delete(r);
		ajaxReturnBean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}


	

}
