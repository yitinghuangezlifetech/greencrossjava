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

import tw.com.ezlifetech.ezReco.bean.ProductClassManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.ProductClassDto;
import tw.com.ezlifetech.ezReco.dto.RoleDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.model.ProductClass;
import tw.com.ezlifetech.ezReco.model.RefProdClass;
import tw.com.ezlifetech.ezReco.respository.ProductClassRespository;
import tw.com.ezlifetech.ezReco.respository.RefProdClassRespository;
import tw.com.ezlifetech.ezReco.service.ProductClassManagerService;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Service
public class ProductClassManagerServiceImpl implements ProductClassManagerService{

	@Autowired
	private ProductClassRespository productClassRespository;
	
	@Autowired
	private RefProdClassRespository refProdClassRespository;
	
	@Override
	public String prodClassGridList(ProductClassManagerBean formBean) {
		
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
		sql.append("     product_class    ");
		sql.append("  WHERE               ");
		sql.append("     class_parent_id=:pid  ");
		sql.append("  ORDER BY sort_index,class_serno    ");
		List<Map<String, Object>> inList = productClassRespository.findListMapBySQL_map(sql.toString(), params);
		
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
	public String ajaxSaveProdClass(ProductClassDto dto, UserDto userDto) {
		ProductClass productClass = null;
		
		if(StringUtil.isBlank(dto.getId())) {
			productClass = new ProductClass();
			productClass.setId(DateUtil.getSystemDateTime()+StringUtil.getRandomNum(2));
			productClass.setCreateUser(userDto.getId());
			productClass.setCreateTime(DateUtil.getSystemDateTimeObject());
		}else{
			productClass = productClassRespository.getEntityById(dto.getId());
			productClass.setUpdateUser(userDto.getId());
			productClass.setUpdateTime(DateUtil.getSystemDateTimeObject());
		}
		productClass.setClassName(dto.getClassName());
		productClass.setClassNameEn(dto.getClassNameEn());
		productClass.setClassDesc(dto.getClassDesc());
		productClass.setClassDescEn(dto.getClassDescEn());
		productClass.setClassParentId(dto.getClassParentId());
		productClass.setStatus((dto.getStatus()!=null && dto.getStatus().equals("on"))?"Y":"N");
		productClassRespository.save(productClass);
		
		return AjaxMesgs.SAVE_SUCCESSFUL.getDoc();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void ajaxRemoveProdClass(ProductClassDto dto, UserDto userDto) {
		ProductClass productClass = productClassRespository.getEntityById(dto.getId());
		List<RefProdClass> refProdClassList =refProdClassRespository.findEntityListByJPQL("SELECT rpc FROM RefProdClass rpc WHERE rpc.classSerno = ?", dto.getId());
		
		for(RefProdClass r :refProdClassList) {
			refProdClassRespository.delete(r);
		}
		
		productClassRespository.delete(productClass);
		
		
	}

	
	@Override
	public JsonObject ajaxGetProdClassById(ProductClassDto dto) throws Exception {
		JsonObject jo = new JsonObject();
		ProductClass productClass = productClassRespository.getEntityById(dto.getId());
		if(productClass != null) {
			dto = new ProductClassDto(productClass);
		}else {
			 throw new Exception("role not found");
		}
		jo.addProperty("id", dto.getId());
		jo.addProperty("className", dto.getClassName());
		jo.addProperty("classNameEn", dto.getClassNameEn());
		jo.addProperty("classDesc", dto.getClassDesc());
		jo.addProperty("classDescEn", dto.getClassDescEn());
		jo.addProperty("status", dto.getStatus());
		jo.addProperty("sortIndex", dto.getSortIndex());
		jo.addProperty("classParentId", dto.getClassParentId());
		
		return jo;
	}
	
	@Override
	public JsonArray prodClassList(ProductClassManagerBean formBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ErrorBean> validateInternalSave(ProductClassDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		if(StringUtil.isBlank(dto.getClassName())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_className");
			erBean.setLabelName("商品分類名稱");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else if(dto.getClassName().length()>1000) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_className");
			erBean.setLabelName("商品分類名稱");
			erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "1000"));
			list.add(erBean);
		}
		
		
		if(!StringUtil.isBlank(dto.getClassNameEn())) {
			
			 if(dto.getClassNameEn().length()>1000) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_classNameEn");
				erBean.setLabelName("商品分類名稱(英文)");
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "1000"));
				list.add(erBean);
			 }
		}
		
		if(StringUtil.isBlank(dto.getClassParentId())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_classParentId");
			erBean.setLabelName("");
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "上層商品分類"));
			list.add(erBean);
		}
		
		
		if(StringUtil.isBlank(dto.getId()))
			if((productClassRespository.getEntityByJPQL("SELECT p FROM ProductClass p WHERE p.className=?", dto.getClassName()))!=null) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_className");
				erBean.setLabelName("商品分類名稱");
				erBean.setMesg(ErrorMesgs.REPEAT_ADD.getDoc());
				list.add(erBean);
			}
		return list;
	}

	@Override
	public List<ErrorBean> validateInternalRemove(ProductClassDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		return list;
	}

	@Override
	public JsonArray ajaxGetAllProdClass(ProductClassDto dto,String preStr) {
		// TODO Auto-generated method stub
		JsonArray ja = new JsonArray();
		deepProdClass(ja,0,preStr,"000000");
		
		
		
		return ja;
	}

	
	private void deepProdClass(JsonArray ja,int index,String preStr,String pId) {
		
		List<ProductClass> pList = productClassRespository.findEntityListByJPQL("SELECT p FROM ProductClass p WHERE p.classParentId=?", pId);
		
		if(pList==null || pList.size()==0) {
			return;
		}else {
			for(ProductClass p : pList) {
				JsonObject jo = new JsonObject();
				jo.addProperty("classId", p.getId());
				jo.addProperty("className",StringUtil.repeatStr(index,preStr)+p.getClassName());
				ja.add(jo);
				deepProdClass(ja,index+1,preStr,p.getId());
			}
		}
		
		
	}
	
	
	@Override
	public List<Map<String,String>> ajaxGetAllProdClass(String preStr) {
		// TODO Auto-generated method stub
		List<Map<String,String>>list = new ArrayList<Map<String,String>>();
		
		deepProdClass(list,0,preStr,"000000");
		
		
		
		return list;
	}

	@Override
	public void deepProdClass(List<Map<String,String>> list,int index,String preStr,String pId) {
		
		List<ProductClass> pList = productClassRespository.findEntityListByJPQL("SELECT p FROM ProductClass p WHERE p.classParentId=? order by p.sortIndex", pId);
		
		if(pList==null || pList.size()==0) {
			return;
		}else {
			for(ProductClass p : pList) {
				
				Map<String,String> map = new HashMap<String,String>();
				map.put("classId", p.getId());
				map.put("className", StringUtil.repeatStr(index,preStr)+"‧"+p.getClassName());
				map.put("isRoot", "000000".equals(pId)?"Y":"N");
				list.add(map);
				deepProdClass(list,index+1,preStr,p.getId());
			}
		}
		
		
	}


	@Override
	public void deepProdClass(List<Map<String, String>> list, int index, String preStr, String pId, String language) {
		List<ProductClass> pList = productClassRespository.findEntityListByJPQL("SELECT p FROM ProductClass p WHERE p.classParentId=? order by sort_index", pId);
		
		if(pList==null || pList.size()==0) {
			return;
		}else {
			for(ProductClass p : pList) {
				
				Map<String,String> map = new HashMap<String,String>();
				map.put("classId", p.getId());
				
				String className ="";
				if("en".equals(language)) {
					className = StringUtil.repeatStr(0,preStr)+"‧"+(StringUtil.isBlank(p.getClassNameEn())?"No classification":p.getClassNameEn());
				}else {
					className = StringUtil.repeatStr(0,preStr)+"‧"+p.getClassName();
					
				}
				map.put("className", className);
				map.put("isRoot", "000000".equals(pId)?"Y":"N");
				list.add(map);
				deepProdClass(list,index+1,preStr,p.getId());
			}
		}
		
	}
	
	

}
