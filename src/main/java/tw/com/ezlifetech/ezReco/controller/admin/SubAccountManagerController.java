package tw.com.ezlifetech.ezReco.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.ezlifetech.ezReco.bean.SubAccountManagerBean;
import tw.com.ezlifetech.ezReco.bean.UserFunctionBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.RefFunctionUserDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.SubAccountManagerService;
import tw.com.ezlifetech.ezReco.service.UserFunctionService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;

@Controller
@RequestMapping("/admin/subAccountManager")
public class SubAccountManagerController extends GenericController{
	private static final String JAVA_NAME="SubAccountManagerController";
	private static Logger logger = LoggerFactory.getLogger(SubAccountManagerController.class);
	
	@Autowired
	private SubAccountManagerService subAccountManagerService;
	
	@Autowired
	private UserFunctionService userFunctionService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		
		return "admin/subAccountManager/list";
	}
	
	@RequestMapping(value = "/ajaxSubAccountGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSubAccountGridList(SubAccountManagerBean formBean, HttpServletRequest request) {
		
		return subAccountManagerService.ajaxSubAccountGridList(formBean,getLoginUser());
	}
	
	@RequestMapping(value = "/ajaxSubAccountRoleList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSubAccountRoleList(SubAccountManagerBean formBean, HttpServletRequest request) {
		
		return AjaxUtil.success(subAccountManagerService.ajaxSubAccountRoleList(formBean,getLoginUser()));
	}
	
	@RequestMapping(value = "/ajaxApplyRoleSetting", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxApplyRoleSetting(UserDto dto, HttpServletRequest request) {

		
		try {
			validateInternalApplyRoleSetting(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			subAccountManagerService.ajaxApplyRoleSetting(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(AjaxMesgs.APPLY_SUCCESSFUL.getDoc());
	}

	private void validateInternalApplyRoleSetting(UserDto dto) {
		// TODO Auto-generated method stub
		setErrorBeans(subAccountManagerService.validateInternalApplyRoleSetting(dto));
	}
	
	
	@RequestMapping("/userFuncList")
	public String userFuncList(Model model, @ModelAttribute(UserDto.dtoName) UserDto dto) {

		userFunctionService.paperPage(model,dto);
		
		return "admin/subAccountManager/userFuncList";
	}
	
	@RequestMapping(value = "/ajaxUserFunGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxUserFunGridList(UserFunctionBean bean, HttpServletRequest request) {

		return userFunctionService.userFunGridList(bean,false,getLoginUser());
	}
	
	@RequestMapping(value = "/ajaxApplyFunc", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxApplyFunc(RefFunctionUserDto dto, HttpServletRequest request) {

		
		try {
			validateInternalApplyFunc(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			userFunctionService.ajaxApplyFunc(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(AjaxMesgs.APPLY_SUCCESSFUL.getDoc());
	}


	@RequestMapping(value = "/ajaxCancelFunc", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxCancelFunc(RefFunctionUserDto dto, HttpServletRequest request) {

		
		try {
			validateInternalCancelFunc(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			userFunctionService.ajaxCancelFunc(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(AjaxMesgs.APPLY_SUCCESSFUL.getDoc());
	}


	private void validateInternalCancelFunc(RefFunctionUserDto dto) {
		setErrorBeans(userFunctionService.validateInternalCancelFunc(dto));
		
	}

	private void validateInternalApplyFunc(RefFunctionUserDto dto) {
		setErrorBeans(userFunctionService.validateInternalApplyFunc(dto));
		
	}
	

}
