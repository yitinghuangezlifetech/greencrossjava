package tw.com.ezlifetech.ezReco.service;

import java.util.List;

import org.springframework.ui.Model;

import tw.com.ezlifetech.ezReco.bean.AccountManagerAdminBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.UserDto;

public interface AccountManagerAdminService {
	String ajaxAccountGridList(AccountManagerAdminBean formBean, UserDto loginUser);

	void paperPage(Model model, UserDto dto);

	String ajaxSaveAccount(UserDto dto, UserDto loginUserDto)  throws Exception;

	List<ErrorBean> validateInternalSaveAccount(UserDto dto) throws Exception;
}
