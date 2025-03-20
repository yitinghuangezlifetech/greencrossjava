package tw.com.ezlifetech.ezReco.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.FunctionManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.ProfFunctionDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.model.ProfFunction;
import tw.com.ezlifetech.ezReco.respository.ProfFunctionRepository;
import tw.com.ezlifetech.ezReco.service.FunctionManagerAdminService;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;

import org.apache.commons.lang3.StringUtils;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class FunctionManagerAdminServiceImpl implements FunctionManagerAdminService{
	@Autowired
	private ProfFunctionRepository profFunctionRepository;
	
	
	@Override
	public List<Map<String, String>> funcSelMapList(FunctionManagerBean formBean) {
		List<Map<String, String>> resList = new ArrayList<Map<String, String>>();
		List<ProfFunction> profFunctionList = profFunctionRepository.findEntityListBySQL("SELECT f.* FROM prof_function f WHERE f.parent_id = ? ORDER BY cast(NULLIF(regexp_replace(f.sort_index, E'\\\\D', '', 'g'), '') AS integer) , f.func_id",formBean.getParentId()==null?"000000":formBean.getParentId());
		
		for(ProfFunction f:profFunctionList) {
			Map<String, String> map = new HashMap<String,String>();
			map.put("funcName",f.getFuncName());
			map.put("funcId",f.getId());
			resList.add(map);
		}
		
		
		return resList;
	}

	
	@Override
	public String funcGridList(FunctionManagerBean formBean) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentId", formBean.getParentId());
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT                       ");
		sql.append(" 	func_id,                  ");
		sql.append(" 	func_name,                ");
		sql.append(" 	(                         ");
		sql.append(" 		SELECT                ");
		sql.append(" 			func_Name         ");
		sql.append(" 		FROM                  ");
		sql.append(" 			prof_function     ");
		sql.append(" 		WHERE                 ");
		sql.append(" 		    func_id=:parentId ");
		sql.append(" 	) as func_parent_Name,    ");
		sql.append(" 	func_uri,                 ");
		sql.append(" 	use_state,                ");
		sql.append(" 	create_user,              ");
		sql.append(" 	create_time,              ");
		sql.append(" 	update_user,              ");
		sql.append(" 	update_time               ");
		sql.append(" FROM                         ");
		sql.append(" 	prof_function             ");
		sql.append(" WHERE                        ");
		sql.append(" 	parent_id=:parentId       ");
		sql.append(" order by                     ");
		sql.append(" 	cast(NULLIF(regexp_replace(sort_index, E'\\\\D', '', 'g'), '') AS integer),               ");
		sql.append(" 	func_id                   ");
		
		List<Map<String, Object>> list=profFunctionRepository.findListMapBySQL_map(sql.toString(), params);
		
		return PageUtil.transToGridDataSource(list,formBean);
	}


	@Override
	public JsonArray funcListByParentId(FunctionManagerBean formBean) {
		JsonArray ja = new JsonArray();
		List<ProfFunction> profFunctionList = profFunctionRepository.findEntityListBySQL("SELECT f.* FROM prof_function f WHERE f.parent_id = ? ORDER BY cast(NULLIF(regexp_replace(f.sort_index, E'\\\\D', '', 'g'), '') AS integer) , f.func_id",formBean.getParentId()==null?"000000":formBean.getParentId());
		
		for(ProfFunction f:profFunctionList) {
			JsonObject jo = new JsonObject();
			jo.addProperty("funcName", f.getFuncName());
			jo.addProperty("funcId", f.getId());
			ja.add(jo);
		
		}
		
		return ja;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void sortFunc(FunctionManagerBean formBean,UserDto userDto) {
		int index = 0;
		for(String funcId:formBean.getSortFuncId()) {
			index++;
			ProfFunction pf =profFunctionRepository.getEntityById(funcId);
			pf.setSortIndex(index+"");
			pf.setUpdateTime(DateUtil.getSystemDateTimeObject());
			pf.setUpdateUser(userDto.getId());
			profFunctionRepository.save(pf);
		}
		
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String saveFunc(ProfFunctionDto dto,UserDto userDto) {
		ProfFunction pf = null;
		
		if(dto.getId()==null|| dto.getId().equals("")) {
			pf = new ProfFunction();
			pf.setId(dto.getFuncNo());
			pf.setFuncNo(dto.getFuncNo());
			pf.setCreateTime(DateUtil.getSystemDateTimeObject());
			pf.setCreateUser(userDto.getId());
		}else {
			pf =profFunctionRepository.getEntityById(dto.getId());
			pf.setUpdateTime(DateUtil.getSystemDateTimeObject());
			pf.setUpdateUser(userDto.getId());
		}
		pf.setFuncName(dto.getFuncName());
		pf.setFuncUri(StringUtils.isBlank(dto.getFuncUri())?null:dto.getFuncUri());
		pf.setParentId(dto.getParentId());
		pf.setFuncPict(dto.getFuncPict());
		pf.setUseState((dto.getUseState()!=null && dto.getUseState().equals("on"))?"Y":"N");
		
		profFunctionRepository.save(pf);
		
		
		return AjaxMesgs.SAVE_SUCCESSFUL.getDoc();
	}


	@Override
	public List<ErrorBean> validateInternalSave(ProfFunctionDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		// 必填欄位是否有填
		if(StringUtils.isBlank(dto.getId())) {
			if(StringUtils.isBlank(dto.getFuncNo())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_funcNo");
				erBean.setLabelName("功能ID");
				erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
				list.add(erBean);
			}
		}
		if(StringUtils.isBlank(dto.getFuncName())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_funcName");
			erBean.setLabelName("功能名稱");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}
		if(StringUtils.isBlank(dto.getParentId())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_parentId");
			erBean.setLabelName("");
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "上層功能"));
			list.add(erBean);
		}
		if(!dto.getParentId().equals("000000")) {
			if(StringUtils.isBlank(dto.getFuncUri())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_funcUri");
				erBean.setLabelName("功能URI");
				erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
				list.add(erBean);
			}
		}
		
		
		//檢核重複
		if(StringUtils.isBlank(dto.getId())) {
			List<ProfFunction> profFunctionList = profFunctionRepository.findEntityListBySQL("SELECT f.* FROM prof_function f WHERE f.func_id = ? OR f.func_no = ? ORDER BY cast(NULLIF(regexp_replace(f.sort_index, E'\\\\D', '', 'g'), '') AS integer) , f.func_id",dto.getFuncNo(),dto.getFuncNo());
			if(profFunctionList.size()>0) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_funcNo");
				erBean.setLabelName("功能ID");
				erBean.setMesg(ErrorMesgs.DATA_EXIST.getDoc());
				list.add(erBean);
			}
		}
		
		
		
		return list;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String removeFunc(ProfFunctionDto dto) {
		ProfFunction pf = profFunctionRepository.getEntityById(dto.getId());
		profFunctionRepository.delete(pf);
		
		
		return AjaxMesgs.REMOVE_SUCCESSFUL.getDoc();
	}


	@Override
	public List<ErrorBean> validateInternalRemove(ProfFunctionDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		//檢核是否有子功能
		if(!StringUtils.isBlank(dto.getId())) {
			List<ProfFunction> profFunctionList = profFunctionRepository.findEntityListByJPQL("SELECT f FROM ProfFunction f WHERE f.parentId = ?",dto.getId());
			if(profFunctionList.size()>0) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("none");
				erBean.setLabelName("該功能");
				erBean.setMesg(ErrorMesgs.DATA_EXIST_SUBFUNC_CAN_NOT_REMOVE.getDoc());
				list.add(erBean);
			}
		}else{
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("none");
			erBean.setLabelName("該功能");
			erBean.setMesg(ErrorMesgs.DATA_NOT_EXIST_CAN_NOT_REMOVE.getDoc());
			list.add(erBean);
		}
		
		return list;
	}


	@Override
	public JsonObject getFuncById(ProfFunctionDto dto) throws Exception {
		JsonObject jo = new JsonObject();
		
		ProfFunction func = profFunctionRepository.getEntityById(dto.getId());
		if(func != null) {
			dto = new ProfFunctionDto(func);
		}else {
			 throw new Exception("func not found");
		}
		jo.addProperty("id", dto.getId());
		jo.addProperty("funcName", dto.getFuncName());
		jo.addProperty("parentId", dto.getParentId());
		jo.addProperty("funcUri", dto.getFuncUri());
		jo.addProperty("funcPict", dto.getFuncPict());
		jo.addProperty("useState", dto.getUseState());
		return jo;
	}

}
