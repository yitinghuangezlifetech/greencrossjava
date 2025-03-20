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

import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.RoleManagerBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.RefFunctionRoleDto;
import tw.com.ezlifetech.ezReco.dto.RoleDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.RoleManagerService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;

@Controller
@RequestMapping("/admin/roleManager")
public class RoleManagerController extends GenericController {
	private static final String JAVA_NAME="RoleManagerController";
	private static Logger logger = LoggerFactory.getLogger(RoleManagerController.class);
	
	@Autowired
	private RoleManagerService roleManagerService;
	
	@RequestMapping("/roleList")
	public String roleList(Model model, @ModelAttribute(RoleManagerBean.beanFormName) RoleManagerBean formBean) {

		return "admin/roleManager/list";
	}
	
	@RequestMapping("/funcRoleList")
	public String funcRoleList(Model model, @ModelAttribute(RoleDto.dtoName) RoleDto dto) {
		roleManagerService.paperView(model,dto);
		
		return "admin/roleManager/funcList";
	}

	@RequestMapping(value = "/ajaxRoleGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRoleGridList(RoleManagerBean formBean, HttpServletRequest request) {

		return roleManagerService.roleGridList(formBean,getLoginUser());
	}
	
	@RequestMapping(value = "/ajaxRoleFunGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRoleFunGridList(RoleDto dto, HttpServletRequest request) {

		return roleManagerService.roleFunGridList(dto,getLoginUser());
	}

	@RequestMapping(value = "/ajaxSaveRole", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveRole(RoleDto dto, HttpServletRequest request) {
		String result = "";
		try {
			validateInternalSave(dto);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			result = roleManagerService.ajaxSaveRole(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(result);
	}

	@RequestMapping(value = "/ajaxGetRoleById", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetRoleById(RoleDto dto, HttpServletRequest request) {

		JsonObject result = null;
		try {
			result = roleManagerService.ajaxGetRoleById(dto);
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(result);
	}
	
	@RequestMapping(value = "/ajaxRemoveRoleById", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemoveRoleById(RoleDto dto, HttpServletRequest request) {

		
		try {
			validateInternalRemove(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			roleManagerService.ajaxRemoveRoleById(dto);
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
	}

	
	
	@RequestMapping(value = "/ajaxApplyFunc", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxApplyFunc(RefFunctionRoleDto dto, HttpServletRequest request) {

		
		try {
			validateInternalApplyFunc(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			roleManagerService.ajaxApplyFunc(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(AjaxMesgs.APPLY_SUCCESSFUL.getDoc());
	}
	
	@RequestMapping(value = "/ajaxCancelFunc", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxCancelFunc(RefFunctionRoleDto dto, HttpServletRequest request) {

		
		try {
			validateInternalCancelFunc(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			roleManagerService.ajaxCancelFunc(dto);
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(AjaxMesgs.APPLY_SUCCESSFUL.getDoc());
	}
	
	
	private void validateInternalSave(RoleDto dto) {
		setErrorBeans(roleManagerService.validateInternalSave(dto));
	}
	
	private void validateInternalRemove(RoleDto dto) {
		setErrorBeans(roleManagerService.validateInternalRemove(dto));
	}

	private void validateInternalApplyFunc(RefFunctionRoleDto dto) {
		setErrorBeans(roleManagerService.validateInternalApplyFunc(dto));
	}
	
	private void validateInternalCancelFunc(RefFunctionRoleDto dto) {
		setErrorBeans(roleManagerService.validateInternalCancelFunc(dto));
	}
}
