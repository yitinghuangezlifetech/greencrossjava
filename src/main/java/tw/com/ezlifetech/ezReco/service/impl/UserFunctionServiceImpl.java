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
import org.springframework.ui.Model;

import tw.com.ezlifetech.ezReco.bean.UserFunctionBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.RefFunctionUserDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.model.RefFunctionUser;
import tw.com.ezlifetech.ezReco.model.Role;
import tw.com.ezlifetech.ezReco.model.User;
import tw.com.ezlifetech.ezReco.respository.ProfFunctionRepository;
import tw.com.ezlifetech.ezReco.respository.RefFunctionUserRepository;
import tw.com.ezlifetech.ezReco.respository.RoleRepository;
import tw.com.ezlifetech.ezReco.respository.UserRepository;
import tw.com.ezlifetech.ezReco.service.UserFunctionService;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;





@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserFunctionServiceImpl implements UserFunctionService{
	private static final String JAVA_NAME="UserFunctionServiceImpl";

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfFunctionRepository profFunctionRepository;
	
	@Autowired
	private RefFunctionUserRepository refFunctionUserRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public void paperPage(Model model, UserDto dto) {
		User targetUser = null;
		if (!StringUtil.isBlank(dto.getId())) {
			targetUser = userRepository.getEntityById(dto.getId());
		}
		BeanUtils.copyProperties(targetUser, dto);
		dto.setRoleId(dto.getRoleType() + dto.getRoleNo());
		
		Role role = roleRepository.getEntityByJPQL("SELECT r FROM Role r WHERE r.roleType=? AND r.roleNo=?", dto.getRoleType(),dto.getRoleNo());
		dto.setRoleName(role.getRoleName());	
	
	}


	@Override
	public String userFunGridList(UserFunctionBean bean,boolean isAdmin, UserDto loginUser) {
		List<Map<String, Object>> reslist = new ArrayList<Map<String, Object>>();
		User user = userRepository.getEntityById(bean.getUserId());
		UserDto userDto = new UserDto(user);
		
		if(isAdmin) {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("roleType", userDto.getRoleType());
			params.put("roleNo", userDto.getRoleNo());
			params.put("parentId", "000000");
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT                      ");
			sql.append("   pf.func_id      ");
			sql.append("  ,pf.func_name  ");
			sql.append("  ,pf.parent_id  ");
			sql.append("  ,pf.use_state  ");
			sql.append("  ,rf.id as rfid             ");
			sql.append(" FROM                        ");
			sql.append("  prof_function pf  ");
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
			List<Map<String, Object>> list=profFunctionRepository.findListMapBySQL_map(sql.toString(), params);
			int id=1;
			for(Map<String, Object> map :list) {
				
				map.put("id", String.format("%06d", id));
				map.put("parentId", null);
				reslist.add(map);
				params.put("parentId", map.get("func_id").toString());
				List<Map<String, Object>> sublist=profFunctionRepository.findListMapBySQL_map(sql.toString(), params);
				int subId=1;
				for(Map<String, Object> submap :sublist) {
					submap.put("id", String.format("%03d", id)+String.format("%03d", subId));
					submap.put("parentId", String.format("%03d", id));
					reslist.add(submap);
					subId++;
				}
				id++;
			}
		}else {
			
			
			Map<String,Object> params = new HashMap<String,Object>();
			User pUser = userRepository.getEntityById(!StringUtil.isBlank(userDto.getParentUserId())?userDto.getParentUserId():userDto.getId());
			UserDto pUserDto = new UserDto(pUser);
			
			params.put("parentUser",pUserDto.getId());
			params.put("parentUserRoleType",pUserDto.getRoleType());
			params.put("parentUserRoleNo",pUserDto.getRoleNo());
			params.put("roleType", userDto.getRoleType());
			params.put("roleNo", userDto.getRoleNo());
			params.put("parentId", "000000");
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT                                                                                                                                         ");
			sql.append("   pf.func_id                                                                                                                         ");
			sql.append("  ,pf.func_name                                                                                                                     ");
			sql.append("  ,pf.parent_id                                                                                                                     ");
			sql.append("  ,pf.use_state                                                                                                                    ");
			sql.append("  ,rf.id as rfid                                                                                                                                ");
			sql.append(" FROM                                                                                                                                           ");
			sql.append("  prof_function pf                                                                                                                     ");
			sql.append(" LEFT JOIN                                                                                                                                      ");
			sql.append("   ref_function_role rf                                                                                                                         ");
			sql.append("  ON                                                                                                                                            ");
			sql.append("   pf.func_id = rf.func_no                                                                                                                      ");
			sql.append("   AND                                                                                                                                          ");
			sql.append("   rf.role_type = :roleType                                                                                                                     ");
			sql.append("   AND                                                                                                                                          ");
			sql.append("   rf.role_no= :roleNo                                                                                                                          ");
			sql.append(" WHERE pf.parent_id = :parentId                                                                                                                 ");
			sql.append("   AND pf.func_id in (SELECT rfr.func_no FROM ref_function_role rfr WHERE rfr.ROLE_TYPE=:parentUserRoleType AND rfr.role_no=:parentUserRoleNo   ");
			sql.append(" 					 UNION ALL                                                                                                                  ");
			sql.append(" 					 SELECT rfu.func_no FROM ref_function_user rfu WHERE rfu.USER_ID = :parentUser)                                             ");
			sql.append("                                                                                                                                                ");
			sql.append(" ORDER BY                                                                                                                                       ");
			sql.append("  pf.sort_index                                                                                                                                 ");
			List<Map<String, Object>> list=profFunctionRepository.findListMapBySQL_map(sql.toString(), params);
			
			int id=1;
			for(Map<String, Object> map :list) {
				
				map.put("id", String.format("%06d", id));
				map.put("parentId", null);
				reslist.add(map);
				params.put("parentId", map.get("func_id").toString());
				List<Map<String, Object>> sublist=profFunctionRepository.findListMapBySQL_map(sql.toString(), params);
				int subId=1;
				for(Map<String, Object> submap :sublist) {
					submap.put("id", String.format("%03d", id)+String.format("%03d", subId));
					submap.put("parentId", String.format("%03d", id));
					reslist.add(submap);
					subId++;
				}
				id++;
			}
		}
		
		for(Map<String, Object> map:reslist) {
			RefFunctionUser rfu =refFunctionUserRepository.getEntityByJPQL("SELECT r FROM RefFunctionUser r WHERE r.userId = ? AND r.funcNo=?", userDto.getId(),map.get("func_id").toString());
			if(rfu!=null) {
				map.put("rfu_id", rfu.getId());
				
			}else {
				map.put("rfu_id", "");
			}
		}
		
		
		
		return PageUtil.transToGridDataSource(reslist,bean);
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void ajaxApplyFunc(RefFunctionUserDto dto, UserDto loginUserDto) {
		RefFunctionUser rfu = new RefFunctionUser();
		rfu.setId(""+System.currentTimeMillis()+StringUtil.getRandomNum(3));
		rfu.setUserId(dto.getUserId());
		rfu.setFuncNo(dto.getFuncNo());
		rfu.setAuthority("W");
		rfu.setCreateUser(loginUserDto.getId());
		rfu.setCreateTime(DateUtil.getSystemDateTimeObject());
		refFunctionUserRepository.save(rfu);
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void ajaxCancelFunc(RefFunctionUserDto dto, UserDto loginUser) {
		RefFunctionUser rfu = refFunctionUserRepository.getEntityById(dto.getId());
		refFunctionUserRepository.delete(rfu);
		
	}


	@Override
	public List<ErrorBean> validateInternalApplyFunc(RefFunctionUserDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		RefFunctionUser rfu = refFunctionUserRepository.getEntityByJPQL("SELECT r FROM RefFunctionUser r WHERE r.userId=? AND r.funcNo=?", dto.getUserId(),dto.getFuncNo());
		if(rfu!=null) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("none");
			erBean.setLabelName("該帳號功能");
			erBean.setMesg(ErrorMesgs.DATA_EXIST.getDoc());
			list.add(erBean);
		}
		
		return list;
	}


	

	@Override
	public List<ErrorBean> validateInternalCancelFunc(RefFunctionUserDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		RefFunctionUser rfu = refFunctionUserRepository.getEntityById(dto.getId());
		if(rfu==null) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("none");
			erBean.setLabelName("該帳號功能");
			erBean.setMesg(ErrorMesgs.DATA_NOT_EXIST_CAN_NOT_REMOVE.getDoc());
			list.add(erBean);
		}
		
		return list;
	}
	
	
	
	
	
	
}
