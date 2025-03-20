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
import tw.com.ezlifetech.ezReco.bean.ProductClassManagerBean;
import tw.com.ezlifetech.ezReco.bean.RoleManagerBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.ProductClassDto;
import tw.com.ezlifetech.ezReco.dto.RoleDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.ProductClassManagerService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;

@Controller
@RequestMapping("/admin/productClassManager")
public class ProductClassManagerController extends GenericController{
	private static final String JAVA_NAME="ProductClassManagerController";
	private static Logger logger = LoggerFactory.getLogger(ProductClassManagerController.class);
	
	@Autowired
	private ProductClassManagerService productClassManagerService;
	
	@RequestMapping("/prodClassList")
	public String prodClassList(Model model, @ModelAttribute(ProductClassManagerBean.beanFormName) ProductClassManagerBean formBean) {

		return "admin/productClassManager/list";
	}
	
	@RequestMapping(value = "/ajaxProdClassGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxProdClassGridList(ProductClassManagerBean formBean, HttpServletRequest request) {

		return productClassManagerService.prodClassGridList(formBean);
	}
	
	
	@RequestMapping(value = "/ajaxSaveProdClass", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveProdClass(ProductClassDto dto, HttpServletRequest request) {
		String result = "";
		try {
			validateInternalSave(dto);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			result = productClassManagerService.ajaxSaveProdClass(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(result);
	}
	
	@RequestMapping(value = "/ajaxRemoveProdClass", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemoveProdClass(ProductClassDto dto, HttpServletRequest request) {
		
		try {
			validateInternalRemove(dto);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			productClassManagerService.ajaxRemoveProdClass(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.REMOVE_FAIL.getDoc());
		}
		return AjaxUtil.success(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
	}
	
	@RequestMapping(value = "/ajaxGetProdClassById", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetProdClassById(ProductClassDto dto, HttpServletRequest request) {

		JsonObject result = null;
		try {
			result = productClassManagerService.ajaxGetProdClassById(dto);
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SEARCH_FAIL.getDoc());
		}

		return AjaxUtil.success(result);
	}
	
	@RequestMapping(value = "/ajaxGetAllProdClass", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetAllProdClass(ProductClassDto dto, HttpServletRequest request) {
		return AjaxUtil.success(productClassManagerService.ajaxGetAllProdClass(dto,"-->"));
	}
	
	
	
	@RequestMapping(value = "/ajaxProdClassList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxProdClassList(ProductClassManagerBean formBean, HttpServletRequest request) {

		return AjaxUtil.success(productClassManagerService.prodClassList(formBean));
	}
	

	private void validateInternalSave(ProductClassDto dto) {
		setErrorBeans(productClassManagerService.validateInternalSave(dto));
		
	}
	
	private void validateInternalRemove(ProductClassDto dto) {
		setErrorBeans(productClassManagerService.validateInternalRemove(dto));
		
	}
}
