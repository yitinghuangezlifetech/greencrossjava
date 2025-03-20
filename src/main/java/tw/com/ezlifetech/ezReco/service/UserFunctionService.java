package tw.com.ezlifetech.ezReco.service;

import java.util.List;

import org.springframework.ui.Model;

import tw.com.ezlifetech.ezReco.bean.UserFunctionBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.RefFunctionUserDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;



public interface UserFunctionService {

	void paperPage(Model model, UserDto dto);

	String userFunGridList(UserFunctionBean bean,boolean isAdmin, UserDto loginUser);

	void ajaxApplyFunc(RefFunctionUserDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalApplyFunc(RefFunctionUserDto dto);

	void ajaxCancelFunc(RefFunctionUserDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalCancelFunc(RefFunctionUserDto dto);

	
}
