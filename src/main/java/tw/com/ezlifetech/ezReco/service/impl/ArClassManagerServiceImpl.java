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

import tw.com.ezlifetech.ezReco.bean.ArClassManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.ArClassDto;
import tw.com.ezlifetech.ezReco.dto.RoleDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.model.ArClass;
import tw.com.ezlifetech.ezReco.model.RefArClass;
import tw.com.ezlifetech.ezReco.respository.ArClassRespository;
import tw.com.ezlifetech.ezReco.respository.RefArClassRespository;
import tw.com.ezlifetech.ezReco.service.ArClassManagerService;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Service
public class ArClassManagerServiceImpl implements ArClassManagerService{

	@Autowired
	private ArClassRespository arClassRespository;
	
	@Autowired
	private RefArClassRespository refArClassRespository;
	
	@Override
	public String arClassGridList(ArClassManagerBean formBean) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		deepClassGridList(list,"000000");
		
		return PageUtil.transToGridDataSource(list,formBean);
	}
	
	
	private void deepClassGridList(List<Map<String, Object>> list,String pId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pid", pId);
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT              ");
		sql.append("     class_serno,     ");
		sql.append("     class_name,      ");
		sql.append("     status,          ");
		sql.append("     sort_index,      ");
		sql.append("     create_user,     ");
		sql.append("     create_time,     ");
		sql.append("     update_user,     ");
		sql.append("     update_time,     ");
		sql.append("     class_parent_id  ");
		sql.append("  FROM                ");
		sql.append("     ar_class    ");
		sql.append("  WHERE               ");
		sql.append("     class_parent_id=:pid  ");
		sql.append("  ORDER BY sort_index,class_serno    ");
		List<Map<String, Object>> inList = arClassRespository.findListMapBySQL_map(sql.toString(), params);
		
		if(inList==null ||inList.size()==0) {
			return;
		}
		
		for(Map<String, Object> map:inList) {
			
			list.add(map);
			map.put("id", map.get("class_serno").toString());
			map.put("parentId", pId.equals("000000")?null:pId);
			deepClassGridList(list,map.get("class_serno").toString());
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String ajaxSaveArClass(ArClassDto dto, UserDto userDto) {
		ArClass arClass = null;
		
		if(StringUtil.isBlank(dto.getId())) {
			arClass = new ArClass();
			arClass.setId(DateUtil.getSystemDateTime()+StringUtil.getRandomNum(2));
			arClass.setCreateUser(userDto.getId());
			arClass.setCreateTime(DateUtil.getSystemDateTimeObject());
		}else{
			arClass = arClassRespository.getEntityById(dto.getId());
			arClass.setUpdateUser(userDto.getId());
			arClass.setUpdateTime(DateUtil.getSystemDateTimeObject());
		}
		arClass.setClassName(dto.getClassName());
		arClass.setClassParentId(dto.getClassParentId());
		arClass.setStatus((dto.getStatus()!=null && dto.getStatus().equals("on"))?"Y":"N");
		arClassRespository.save(arClass);
		
		return AjaxMesgs.SAVE_SUCCESSFUL.getDoc();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void ajaxRemoveArClass(ArClassDto dto, UserDto userDto) {
		ArClass arClass = arClassRespository.getEntityById(dto.getId());
		List<RefArClass> refArClassList =refArClassRespository.findEntityListByJPQL("SELECT rpc FROM RefArClass rpc WHERE rpc.classSerno = ?", dto.getId());
		
		for(RefArClass r :refArClassList) {
			refArClassRespository.delete(r);
		}
		
		arClassRespository.delete(arClass);
		
		
	}

	
	@Override
	public JsonObject ajaxGetArClassById(ArClassDto dto) throws Exception {
		JsonObject jo = new JsonObject();
		ArClass arClass = arClassRespository.getEntityById(dto.getId());
		if(arClass != null) {
			dto = new ArClassDto(arClass);
		}else {
			 throw new Exception("role not found");
		}
		jo.addProperty("id", dto.getId());
		jo.addProperty("className", dto.getClassName());
		jo.addProperty("status", dto.getStatus());
		jo.addProperty("sortIndex", dto.getSortIndex());
		jo.addProperty("classParentId", dto.getClassParentId());
		
		return jo;
	}
	
	@Override
	public JsonArray arClassList(ArClassManagerBean formBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ErrorBean> validateInternalSave(ArClassDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		if(StringUtil.isBlank(dto.getClassName())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_className");
			erBean.setLabelName("商品分類名稱");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else if(dto.getClassName().length()>20) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_className");
			erBean.setLabelName("商品分類名稱");
			erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "20"));
			list.add(erBean);
		}
		
		if(StringUtil.isBlank(dto.getClassParentId())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_classParentId");
			erBean.setLabelName("");
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "上層商品分類"));
			list.add(erBean);
		}
		
		
		if(StringUtil.isBlank(dto.getId()))
			if((arClassRespository.getEntityByJPQL("SELECT p FROM ArClass p WHERE p.className=?", dto.getClassName()))!=null) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_className");
				erBean.setLabelName("商品分類名稱");
				erBean.setMesg(ErrorMesgs.REPEAT_ADD.getDoc());
				list.add(erBean);
			}
		return list;
	}

	@Override
	public List<ErrorBean> validateInternalRemove(ArClassDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		return list;
	}

	@Override
	public JsonArray ajaxGetAllArClass(ArClassDto dto,String preStr) {
		// TODO Auto-generated method stub
		JsonArray ja = new JsonArray();
		deepArClass(ja,0,preStr,"000000");
		
		
		
		return ja;
	}

	
	private void deepArClass(JsonArray ja,int index,String preStr,String pId) {
		
		List<ArClass> pList = arClassRespository.findEntityListByJPQL("SELECT p FROM ArClass p WHERE p.classParentId=?", pId);
		
		if(pList==null || pList.size()==0) {
			return;
		}else {
			for(ArClass p : pList) {
				JsonObject jo = new JsonObject();
				jo.addProperty("classId", p.getId());
				jo.addProperty("className",StringUtil.repeatStr(index,preStr)+p.getClassName());
				ja.add(jo);
				deepArClass(ja,index+1,preStr,p.getId());
			}
		}
		
		
	}
	
	
	@Override
	public List<Map<String,String>> ajaxGetAllArClass(String preStr) {
		// TODO Auto-generated method stub
		List<Map<String,String>>list = new ArrayList<Map<String,String>>();
		
		deepArClass(list,0,preStr,"000000");
		
		
		
		return list;
	}

	
private void deepArClass(List<Map<String,String>> list,int index,String preStr,String pId) {
		
		List<ArClass> pList = arClassRespository.findEntityListByJPQL("SELECT p FROM ArClass p WHERE p.classParentId=?", pId);
		
		if(pList==null || pList.size()==0) {
			return;
		}else {
			for(ArClass p : pList) {
				
				Map<String,String> map = new HashMap<String,String>();
				map.put("classId", p.getId());
				map.put("className", StringUtil.repeatStr(index,preStr)+"‧"+p.getClassName());
				list.add(map);
				deepArClass(list,index+1,preStr,p.getId());
			}
		}
		
		
	}
	
	

}
