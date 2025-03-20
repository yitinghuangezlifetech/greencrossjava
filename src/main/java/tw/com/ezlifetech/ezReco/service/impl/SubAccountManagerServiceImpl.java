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

import tw.com.ezlifetech.ezReco.bean.SubAccountManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.model.Role;
import tw.com.ezlifetech.ezReco.model.User;
import tw.com.ezlifetech.ezReco.respository.RoleRepository;
import tw.com.ezlifetech.ezReco.respository.UserRepository;
import tw.com.ezlifetech.ezReco.service.SubAccountManagerService;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SubAccountManagerServiceImpl implements SubAccountManagerService{
	private static final String JAVA_NAME="SubAccountManagerServiceImpl";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Override
	public String ajaxSubAccountGridList(SubAccountManagerBean formBean, UserDto userDto) {
		// 依查詢條件撈清單
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentUserId", !StringUtil.isBlank(userDto.getParentUserId())?userDto.getParentUserId():userDto.getId());
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT                                                ");
		sql.append("      bup.user_id,                                      ");
		sql.append("      bup.user_name,                                    ");
		sql.append("      bup.user_status,                                  ");
		sql.append("      bup.role_type,                                    ");
		sql.append("      bup.role_no,                                      ");
		sql.append("      bup.user_start_date,                              ");
		sql.append("      brp.role_name                                     ");
		sql.append("  FROM                                                  ");
		sql.append("      hf_user bup,hf_role brp     ");
		sql.append("  WHERE 1=1                                             ");
		sql.append("      AND PARENT_USER_ID = :parentUserId                ");
		sql.append("      AND bup.ROLE_TYPE = brp.ROLE_TYPE                 ");
		sql.append("      AND bup.ROLE_NO = brp.ROLE_NO                     ");
		
		List<Map<String, Object>> list=userRepository.findListMapBySQL_map(sql.toString(), params);
		
		
		return PageUtil.transToGridDataSource(list,formBean);
	}


	@Override
	public JsonArray ajaxSubAccountRoleList(SubAccountManagerBean formBean, UserDto userDto) {
		JsonArray ja = new JsonArray();
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentUserId", !StringUtil.isBlank(userDto.getParentUserId())?userDto.getParentUserId():userDto.getId());
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT role_id, role_name                              ");
		sql.append("    FROM hf_role                              ");
		sql.append("    WHERE ROLE_OWNER = :parentUserId AND USE_STATE = 'Y' ");
		
		List<Map<String, Object>> list=roleRepository.findListMapBySQL_map(sql.toString(), params);
		
		for(Map<String, Object> map : list) {
			JsonObject jo = new JsonObject();
			jo.addProperty("role_id", map.get("role_id").toString());
			jo.addProperty("role_name", map.get("role_name").toString());
			ja.add(jo);
		}
		
		return ja;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void ajaxApplyRoleSetting(UserDto dto, UserDto userDto) throws Exception {
		User targetUser = userRepository.getEntityById(dto.getId());
		if(targetUser==null) {
			throw new Exception("targetUser not found");
		}
		
		Role setRole =roleRepository.getEntityById(dto.getRoleId());
		if(setRole==null) {
			throw new Exception("setRole not found");
		}
		
		targetUser.setRoleType(setRole.getRoleType());
		targetUser.setRoleNo(setRole.getRoleNo());
		targetUser.setUpdateUser(userDto.getId());
		targetUser.setUpdateTime(DateUtil.getSystemDateTimeObject());
		
		userRepository.save(targetUser);
	}


	@Override
	public List<ErrorBean> validateInternalApplyRoleSetting(UserDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		if(StringUtil.isBlank(dto.getRoleId())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_roleId");
			erBean.setLabelName("");
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "角色"));
			list.add(erBean);
		}
		return list;
	}
}
