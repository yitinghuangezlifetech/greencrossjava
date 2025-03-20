package tw.com.ezlifetech.ezReco.service;

import java.util.List;

import com.google.gson.JsonArray;

import tw.com.ezlifetech.ezReco.bean.SubAccountManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.UserDto;

public interface SubAccountManagerService {
	String ajaxSubAccountGridList(SubAccountManagerBean formBean, UserDto userDto);

	JsonArray ajaxSubAccountRoleList(SubAccountManagerBean formBean, UserDto loginUser);

	void ajaxApplyRoleSetting(UserDto dto, UserDto userDto) throws Exception;

	List<ErrorBean> validateInternalApplyRoleSetting(UserDto dto);
}
