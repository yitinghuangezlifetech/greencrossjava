package tw.com.ezlifetech.ezReco.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.RoleManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.RefFunctionRoleDto;
import tw.com.ezlifetech.ezReco.dto.RoleDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.model.RefFunctionRole;
import tw.com.ezlifetech.ezReco.model.Role;
import tw.com.ezlifetech.ezReco.model.User;
import tw.com.ezlifetech.ezReco.respository.ProfFunctionRepository;
import tw.com.ezlifetech.ezReco.respository.RefFunctionRoleRepository;
import tw.com.ezlifetech.ezReco.respository.RoleRepository;
import tw.com.ezlifetech.ezReco.respository.UserRepository;
import tw.com.ezlifetech.ezReco.service.RoleManagerAdminService;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleManagerAdminServiceImpl implements RoleManagerAdminService{

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RefFunctionRoleRepository refFunctionRoleRepository;
	
	@Autowired
	private UserRepository userRepository;
	

	
	
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String ajaxSaveRole(RoleDto dto,UserDto userDto) {
		Role role = null;
		if(dto.getId() == null || dto.getId().equals("")) {
			role = new Role();
			role.setId(dto.getRoleType()+dto.getRoleNo());
			role.setRoleType(dto.getRoleType());
			role.setRoleNo(dto.getRoleNo());
			role.setCreateUser(userDto.getId());
			role.setCreateTime(DateUtil.getSystemDateTimeObject());
		}else {
			role = roleRepository.getEntityById(dto.getId());	
			role.setUpdateUser(userDto.getId());
			role.setUpdateTime(DateUtil.getSystemDateTimeObject());
		}
		role.setRoleName(dto.getRoleName());
		role.setUseState((dto.getUseState()!=null && dto.getUseState().equals("on"))?"Y":"N");
		
		roleRepository.save(role);
		
		
		return AjaxMesgs.SAVE_SUCCESSFUL.getDoc();
	}


	@Override
	public JsonObject ajaxGetRoleById(RoleDto dto) throws Exception {
		JsonObject jo = new JsonObject();
		Role role = roleRepository.getEntityById(dto.getId());
		if(role != null) {
			dto = new RoleDto(role);
		}else {
			 throw new Exception("role not found");
		}
		jo.addProperty("id", dto.getId());
		jo.addProperty("roleType", dto.getRoleType());
		jo.addProperty("roleNo", dto.getRoleNo());
		jo.addProperty("roleName", dto.getRoleName());
		jo.addProperty("useState", dto.getUseState());
		return jo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void ajaxRemoveRoleById(RoleDto dto) {
		Role role = roleRepository.getEntityById(dto.getId());
		List<RefFunctionRole> roleFunList = refFunctionRoleRepository.findEntityListByJPQL("SELECT r FROM RefFunctionRole r WHERE roleType=? and roleNo=?",dto.getRoleType(),dto.getRoleNo());
		
		for(RefFunctionRole rfr :roleFunList) {
			refFunctionRoleRepository.delete(rfr);
		}
		
		roleRepository.delete(role);
	}

	@Override
	public RoleDto getRoleDtoById(String id) {
		return new RoleDto(roleRepository.getEntityById(id));
	}

	@Override
	public void paperView(Model model, RoleDto dto) {
		RoleDto pdto = getRoleDtoById(dto.getId());
		dto.setRoleName(pdto.getRoleName());
		dto.setRoleType(pdto.getRoleType());
		dto.setRoleNo(pdto.getRoleNo());
		dto.setUseState(pdto.getUseState());
		
	}

	@Override
	public String roleGridList(RoleManagerBean formBean) {
		// 依查詢條件撈角色清單
		Map<String,Object> params = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT role_id  ");
		sql.append(" , role_type     ");
		sql.append(" , role_no       ");
		sql.append(" , role_name     ");
		sql.append(" , use_state     ");
		sql.append(" , role_owner    ");
		sql.append(" , create_user   ");
		sql.append(" , create_time   ");
		sql.append(" , update_user   ");
		sql.append(" , update_time   ");

		sql.append(" FROM hf_role ");
		sql.append(" WHERE 1=1 ");
		List<Map<String, Object>> list=roleRepository.findListMapBySQL_map(sql.toString(), params);
		
		
		
		return PageUtil.transToGridDataSource(list,formBean);
	}
	
	@Override
	public String roleFunGridList(RoleDto dto) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("roleType", dto.getRoleType());
		params.put("roleNo", dto.getRoleNo());
		params.put("parentId", "000000");
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT                      ");
		sql.append("   pf.func_id                ");
		sql.append("  ,pf.func_name              ");
		sql.append("  ,pf.parent_id              ");
		sql.append("  ,pf.use_state              ");
		sql.append("  ,rf.id as rfId             ");
		//sql.append("  ,pf.func_id as id          ");
		//sql.append("  ,pf.parent_id as parentId  ");
		sql.append(" FROM                        ");
		sql.append("  prof_function pf           ");
		sql.append(" LEFT JOIN                   ");
		sql.append("   ref_function_role rf      ");
		sql.append("  ON                         ");
		sql.append("   pf.func_id = rf.func_no   ");
		sql.append("   AND                       ");
		sql.append("   rf.role_type = :roleType  ");
		sql.append("   AND                       ");
		sql.append("   rf.role_no= :roleNo       ");
		sql.append(" WHERE pf.parent_id = :parentId ");
		sql.append(" ORDER BY                    ");
		sql.append("  pf.sort_index              ");
		List<Map<String, Object>> list=roleRepository.findListMapBySQL_map(sql.toString(), params);
		List<Map<String, Object>> reslist = new ArrayList<Map<String, Object>>();
		int id=1;
		for(Map<String, Object> map :list) {
			
			map.put("id", String.format("%06d", id));
			map.put("parentId", null);
			reslist.add(map);
			params.put("parentId", map.get("func_id").toString());
			List<Map<String, Object>> sublist=roleRepository.findListMapBySQL_map(sql.toString(), params);
			int subId=1;
			for(Map<String, Object> submap :sublist) {
				submap.put("id", String.format("%03d", id)+String.format("%03d", subId));
				submap.put("parentId", String.format("%03d", id));
				reslist.add(submap);
				subId++;
			}
			id++;
		}
		
		
		
		/*for(Map<String, Object> map :reslist) {
			if(map.get("parentid").toString().equals("000000")) {
				map.put("parentId", null);
			}else {
				map.put("parentId", map.get("parentid").toString());
			}
			map.remove("parentid");
		}*/
		
		
		return PageUtil.transToGridDataSource(reslist,dto);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void ajaxApplyFunc(RefFunctionRoleDto dto,UserDto userDto) {
		RefFunctionRole refFunctionRole = new RefFunctionRole();
		refFunctionRole.setId(""+System.currentTimeMillis());
		refFunctionRole.setAuthority("W");
		refFunctionRole.setRoleType(dto.getRoleType());
		refFunctionRole.setRoleNo(dto.getRoleNo());
		refFunctionRole.setFuncNo(dto.getFuncNo());
		refFunctionRole.setCreateUser(userDto.getId());
		refFunctionRole.setCreateTime(DateUtil.getSystemDateTimeObject());
		refFunctionRoleRepository.save(refFunctionRole);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void ajaxCancelFunc(RefFunctionRoleDto dto) {
		RefFunctionRole refFunctionRole = refFunctionRoleRepository.getEntityByJPQL("SELECT r FROM RefFunctionRole r WHERE roleNo=? AND roleType=? AND funcNo=?", dto.getRoleNo(),dto.getRoleType(),dto.getFuncNo());
		refFunctionRoleRepository.delete(refFunctionRole);
	}
	
	@Override
	public List<ErrorBean> validateInternalSave(RoleDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		// 必填欄位是否有填
		if(StringUtils.isBlank(dto.getRoleType())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_roleType");
			erBean.setLabelName("角色類型");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}
		
		if(StringUtils.isBlank(dto.getRoleNo())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_roleNo");
			erBean.setLabelName("角色編號");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}
		
		if(StringUtils.isBlank(dto.getRoleName())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_roleName");
			erBean.setLabelName("角色名稱");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}
		
		// 1. 檢查ID有無重複
		if(StringUtils.isNotBlank(dto.getRoleType()) && StringUtils.isNotBlank(dto.getRoleNo()) && StringUtils.isBlank(dto.getId()))
			if(roleRepository.getEntityById(dto.getRoleType()+dto.getRoleNo())!=null) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_roleType");
				erBean.setLabelName("角色類型+角色編號");
				erBean.setMesg(ErrorMesgs.REPEAT_ADD.getDoc());
				list.add(erBean);
			}	
		
		
		
		return list;
	}
	
	@Override
	public List<ErrorBean> validateInternalRemove(RoleDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		Role role = roleRepository.getEntityById(dto.getId());
		dto = new RoleDto(role);
		List<User> roleFList = userRepository.findEntityListByJPQL("SELECT u FROM User u WHERE roleType=? and roleNo=?",dto.getRoleType(),dto.getRoleNo());
		if(roleFList.size()>0) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("none");
			erBean.setLabelName("該角色帳號");
			erBean.setMesg(ErrorMesgs.DATA_EXIST_CAN_NOT_REMOVE.getDoc());
			list.add(erBean);
		}
		
		return list;
	}


	@Override
	public List<ErrorBean> validateInternalApplyFunc(RefFunctionRoleDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		RefFunctionRole refFunctionRole =refFunctionRoleRepository.getEntityByJPQL("SELECT r FROM RefFunctionRole r WHERE roleNo=? AND roleType=? AND funcNo=?", dto.getRoleNo(),dto.getRoleType(),dto.getFuncNo());
		if(refFunctionRole!=null) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("none");
			erBean.setLabelName("該角色功能");
			erBean.setMesg(ErrorMesgs.DATA_EXIST.getDoc());
			list.add(erBean);
		}
		
		return list;
	}

	@Override
	public List<ErrorBean> validateInternalCancelFunc(RefFunctionRoleDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		RefFunctionRole refFunctionRole =refFunctionRoleRepository.getEntityByJPQL("SELECT r FROM RefFunctionRole r WHERE roleNo=? AND roleType=? AND funcNo=?", dto.getRoleNo(),dto.getRoleType(),dto.getFuncNo());
		if(refFunctionRole==null) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("none");
			erBean.setLabelName("該角色功能");
			erBean.setMesg(ErrorMesgs.DATA_NOT_EXIST_CAN_NOT_REMOVE.getDoc());
			list.add(erBean);
		}
		return list;
	}

}
