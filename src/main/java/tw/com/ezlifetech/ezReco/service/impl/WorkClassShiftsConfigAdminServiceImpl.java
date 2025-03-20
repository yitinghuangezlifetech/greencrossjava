package tw.com.ezlifetech.ezReco.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
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
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftDlDto;
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftHtDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.CommonStatus;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
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
import tw.com.ezlifetech.ezReco.service.WorkClassShiftsConfigAdminService;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WorkClassShiftsConfigAdminServiceImpl implements WorkClassShiftsConfigAdminService{

	
	
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
		model.addAttribute("compIdSelectList", getCompIdSelectList());
		model.addAttribute("whIdSelectList", getWhIdSelectList());
		
		
		
		RefCompWarehouse  r = null;
		r = refCompWarehouseRespository.getEntityByJPQL("SELECT r FROM RefCompWarehouse r WHERE r.wcsId=?", dto.getId());
		if(r!=null) {
			
			
			
			WareHouseProf  w = wareHouseProfRespository.getEntityByWhId(r.getWhId());
			dto.setWhId(w.getId());
			
			dto.setWhDlId(r.getWhDlId());
			
			
		}
	}

	private List<Map<String,Object>> getCompIdSelectList() {
		List<Map<String,Object>> compList = new  ArrayList<>();
		compList = comProfHtRespository.getAllCompIdAndNameListMap();
		return compList;
	}
	private Object getWhIdSelectList() {
		List<Map<String,Object>> whList = new  ArrayList<>();
		whList = wareHouseProfRespository.getAllwhIdAndNameListMap();
		
		return whList;
	}

	@Override
	public String ajaxHtGridList(WorkClassShiftHtDto dto, UserDto loginUser) {
		List<Map<String, Object>>  res = workClassShiftHtRespository.ajaxHtGridListAdmin(dto);
		for(Map<String, Object> m:res) {
			m.put("status_text", CommonStatus.getStstusTextByCode(m.get("status").toString()));
		}
		return PageUtil.transToGridDataSource(res,dto); 
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
	public List<ErrorBean> validateInternalApply(WorkClassShiftHtDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		return list;
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxAgree(WorkClassShiftHtDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		WorkClassShiftHt ht = workClassShiftHtRespository.getEntityById(dto.getId());
		ht.setStatus(CommonStatus.AGREE.getStatusCode());
		workClassShiftHtRespository.save(ht);
		ajaxReturnBean.setMessage(AjaxMesgs.REVIEW_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxReturn(WorkClassShiftHtDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		WorkClassShiftHt ht = workClassShiftHtRespository.getEntityById(dto.getId());
		ht.setStatus(CommonStatus.RETURN.getStatusCode());
		workClassShiftHtRespository.save(ht);
		ajaxReturnBean.setMessage(AjaxMesgs.REVIEW_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxRef(WorkClassShiftHtDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		WorkClassShiftHt ht = workClassShiftHtRespository.getEntityById(dto.getId());
		ht.setStatus(CommonStatus.REFUSE.getStatusCode());
		workClassShiftHtRespository.save(ht);
		ajaxReturnBean.setMessage(AjaxMesgs.REVIEW_SUCCESSFUL.getDoc());
		return null;
	}
}
