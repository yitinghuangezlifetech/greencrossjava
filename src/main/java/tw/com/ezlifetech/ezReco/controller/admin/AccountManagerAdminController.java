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

import tw.com.ezlifetech.ezReco.bean.AccountManagerAdminBean;
import tw.com.ezlifetech.ezReco.bean.UserFunctionBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.RefFunctionUserDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.AccountManagerAdminService;
import tw.com.ezlifetech.ezReco.service.UserFunctionService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;

@Controller
@RequestMapping("/admin/accountManagerAdmin")
public class AccountManagerAdminController extends GenericController {
	private static final String JAVA_NAME="AccountManagerAdminController";
	private static Logger logger = LoggerFactory.getLogger(AccountManagerAdminController.class);
	
	
	@Autowired
	private AccountManagerAdminService accountManagerAdminService;
	
	@Autowired
	private UserFunctionService userFunctionService;
	
	@RequestMapping("/list")
	public String list(Model model) {

		
		
		return "admin/accountManagerAdmin/list";
	}
	
	
	
	
	@RequestMapping("/accountManager")
	public String accountManager(Model model, @ModelAttribute(UserDto.dtoName) UserDto dto) {

		accountManagerAdminService.paperPage(model,dto);
		
		return "admin/accountManagerAdmin/accountManager";
	}
	
	
	
	
	
	@RequestMapping(value = "/ajaxAccountGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxAccountGridList(AccountManagerAdminBean formBean, HttpServletRequest request) {
		
		return accountManagerAdminService.ajaxAccountGridList(formBean,getLoginUser());
	}
	
	@RequestMapping(value = "/ajaxSaveAccount", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveAccount(UserDto dto, HttpServletRequest request) {
		try {
			validateInternalSaveAccount(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			accountManagerAdminService.ajaxSaveAccount(dto,getLoginUser());
		}catch (Exception e) {
			
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
	}

	private void validateInternalSaveAccount(UserDto dto) throws Exception {
		setErrorBeans(accountManagerAdminService.validateInternalSaveAccount(dto));
	}
	
	
	
	
	@RequestMapping("/userFuncList")
	public String userFuncList(Model model, @ModelAttribute(UserDto.dtoName) UserDto dto) {

		userFunctionService.paperPage(model,dto);
		
		return "admin/accountManagerAdmin/userFuncList";
	}
	
	@RequestMapping(value = "/ajaxUserFunGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxUserFunGridList(UserFunctionBean bean, HttpServletRequest request) {

		return userFunctionService.userFunGridList(bean,true,getLoginUser());
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
