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
import tw.com.ezlifetech.ezReco.bean.HomeBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.ProfFunctionDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.FunctionManagerAdminService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;


@Controller
@RequestMapping("/admin/functionManagerAdmin")
public class FunctionManagerAdminController extends GenericController{
	private static final String JAVA_NAME="FunctionManagerAdminController";
	private static Logger logger = LoggerFactory.getLogger(FunctionManagerAdminController.class);
	
	@Autowired
	private FunctionManagerAdminService functionManagerAdminService;
	
	
	@RequestMapping("/functionList")
	public String functionList(Model model, @ModelAttribute(FunctionManagerBean.beanFormName) FunctionManagerBean formBean) {

		model.addAttribute("funcSelMapList", functionManagerAdminService.funcSelMapList(formBean));
		
		
		return "admin/functionManagerAdmin/list";
	}
	
	@RequestMapping(value = "/ajaxFuncGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRoleGridList(FunctionManagerBean formBean, HttpServletRequest request) {

		return functionManagerAdminService.funcGridList(formBean);
	}
	
	@RequestMapping(value = "/ajaxFuncListByParentId", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxFuncListByParentId(FunctionManagerBean formBean, HttpServletRequest request) {

		return AjaxUtil.success(functionManagerAdminService.funcListByParentId(formBean));
	}
	
	@RequestMapping(value = "/ajaxSaveSort", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveSort(FunctionManagerBean formBean, HttpServletRequest request) {
		functionManagerAdminService.sortFunc(formBean,getLoginUser());
		return AjaxUtil.success(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
	}
	
	@RequestMapping(value = "/ajaxGetMainFuncList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetMainFuncList(FunctionManagerBean formBean, HttpServletRequest request) {
		formBean.setParentId("000000");
		return AjaxUtil.success(functionManagerAdminService.funcListByParentId(formBean));
	}
	
	@RequestMapping(value = "/ajaxSaveFunc", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveFunc(ProfFunctionDto dto, HttpServletRequest request) {
		
		String result = "";
		try {
			validateInternalSave(dto);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			
			result = functionManagerAdminService.saveFunc(dto,getLoginUser());
			
		}catch(Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		
		
		
		return AjaxUtil.success(result);
	}
	
	@RequestMapping(value = "/ajaxRemoveFunc", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemoveFunc(ProfFunctionDto dto, HttpServletRequest request) {
		
		String result = "";
		try {
			validateInternalRemove(dto);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			
			result = functionManagerAdminService.removeFunc(dto);
			
		}catch(Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.REMOVE_FAIL.getDoc());
		}
		
		
		
		return AjaxUtil.success(result);
	}
	
	@RequestMapping(value = "/ajaxGetFuncById", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetFuncById(ProfFunctionDto dto, HttpServletRequest request) {

		JsonObject result = null;
		try {
			result = functionManagerAdminService.getFuncById(dto);
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SEARCH_FAIL.getDoc());
		}

		return AjaxUtil.success(result);
	}
	
	
	private void validateInternalRemove(ProfFunctionDto dto) {
		setErrorBeans(functionManagerAdminService.validateInternalRemove(dto));
		
	}

	private void validateInternalSave(ProfFunctionDto dto) {
		setErrorBeans(functionManagerAdminService.validateInternalSave(dto));
	}
	
	
	
}
