package tw.com.ezlifetech.ezReco.service;

import java.util.List;

import org.springframework.ui.Model;

import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.RoleManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.RefFunctionRoleDto;
import tw.com.ezlifetech.ezReco.dto.RoleDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;



public interface RoleManagerAdminService {

	String roleGridList(RoleManagerBean formBean);

	String roleFunGridList(RoleDto dto);
	
	String ajaxSaveRole(RoleDto dto,UserDto userDto);

	List<ErrorBean> validateInternalSave(RoleDto dto);

	JsonObject ajaxGetRoleById(RoleDto dto) throws Exception;

	void ajaxRemoveRoleById(RoleDto dto);

	List<ErrorBean> validateInternalRemove(RoleDto dto);

	RoleDto getRoleDtoById(String id);

	void paperView(Model model, RoleDto dto);

	void ajaxApplyFunc(RefFunctionRoleDto dto,UserDto userDto);

	void ajaxCancelFunc(RefFunctionRoleDto dto);

	List<ErrorBean> validateInternalApplyFunc(RefFunctionRoleDto dto);

	List<ErrorBean> validateInternalCancelFunc(RefFunctionRoleDto dto);

	

}
