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

import tw.com.ezlifetech.ezReco.bean.FunctionManagerBean;
import tw.com.ezlifetech.ezReco.bean.ArClassManagerBean;
import tw.com.ezlifetech.ezReco.bean.RoleManagerBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.ArClassDto;
import tw.com.ezlifetech.ezReco.dto.RoleDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.ArClassManagerService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;

@Controller
@RequestMapping("/admin/arClassManager")
public class ArClassManagerController extends GenericController{
	private static final String JAVA_NAME="ArClassManagerController";
	private static Logger logger = LoggerFactory.getLogger(ArClassManagerController.class);
	
	@Autowired
	private ArClassManagerService arClassManagerService;
	
	@RequestMapping("/arClassList")
	public String arClassList(Model model, @ModelAttribute(ArClassManagerBean.beanFormName) ArClassManagerBean formBean) {

		return "admin/arClassManager/list";
	}
	
	@RequestMapping(value = "/ajaxArClassGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxArClassGridList(ArClassManagerBean formBean, HttpServletRequest request) {

		return arClassManagerService.arClassGridList(formBean);
	}
	
	
	@RequestMapping(value = "/ajaxSaveArClass", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveArClass(ArClassDto dto, HttpServletRequest request) {
		String result = "";
		try {
			validateInternalSave(dto);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			result = arClassManagerService.ajaxSaveArClass(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(result);
	}
	
	@RequestMapping(value = "/ajaxRemoveArClass", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemoveArClass(ArClassDto dto, HttpServletRequest request) {
		
		try {
			validateInternalRemove(dto);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			arClassManagerService.ajaxRemoveArClass(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.REMOVE_FAIL.getDoc());
		}
		return AjaxUtil.success(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
	}
	
	@RequestMapping(value = "/ajaxGetArClassById", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetArClassById(ArClassDto dto, HttpServletRequest request) {

		JsonObject result = null;
		try {
			result = arClassManagerService.ajaxGetArClassById(dto);
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SEARCH_FAIL.getDoc());
		}

		return AjaxUtil.success(result);
	}
	
	@RequestMapping(value = "/ajaxGetAllArClass", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetAllArClass(ArClassDto dto, HttpServletRequest request) {
		return AjaxUtil.success(arClassManagerService.ajaxGetAllArClass(dto,"-->"));
	}
	
	
	
	@RequestMapping(value = "/ajaxArClassList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxArClassList(ArClassManagerBean formBean, HttpServletRequest request) {

		return AjaxUtil.success(arClassManagerService.arClassList(formBean));
	}
	

	private void validateInternalSave(ArClassDto dto) {
		setErrorBeans(arClassManagerService.validateInternalSave(dto));
		
	}
	
	private void validateInternalRemove(ArClassDto dto) {
		setErrorBeans(arClassManagerService.validateInternalRemove(dto));
		
	}
}
