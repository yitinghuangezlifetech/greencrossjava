package tw.com.ezlifetech.ezReco.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.com.ezlifetech.ezReco.bean.HomeBean;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.model.User;



public interface LoginService {
	
	void genSecurityImage(HttpServletRequest request, HttpServletResponse response);

	User getUserProfNewByUserId(String uId);

	void updateUserLoginError(User user);

	void setUserLoginInfo(UserDto user, HttpServletRequest request);
	
	void updateUserLoginSuccess(User user);

	String getBreadcrumbPath(String funcId);

	void ajaxChangeLocal(HttpServletRequest request,HttpServletResponse response, HomeBean formBean);
}
