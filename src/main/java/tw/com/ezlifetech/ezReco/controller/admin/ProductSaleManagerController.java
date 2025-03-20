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

import tw.com.ezlifetech.ezReco.bean.ProductManagerBean;
import tw.com.ezlifetech.ezReco.bean.ProductSaleBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.ProductClassDto;
import tw.com.ezlifetech.ezReco.dto.ProductDto;
import tw.com.ezlifetech.ezReco.dto.ProductSaleDto;
import tw.com.ezlifetech.ezReco.dto.RoleDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.ProductClassManagerService;
import tw.com.ezlifetech.ezReco.service.ProductManagerService;
import tw.com.ezlifetech.ezReco.service.ProductSaleManagerService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;

@Controller
@RequestMapping("/admin/productSaleManager")
public class ProductSaleManagerController extends GenericController{
	private static final String JAVA_NAME="ProductSaleManagerController";
	private static Logger logger = LoggerFactory.getLogger(ProductSaleManagerController.class);
	
	@Autowired
	private ProductSaleManagerService productSaleManagerService;
	
	@Autowired
	private ProductClassManagerService productClassManagerService;
	
	@Autowired
	private ProductManagerService productManagerService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		productSaleManagerService.paperPage(model);
		return "admin/productSaleManager/list";
	}
	
	
	@RequestMapping("/addSale")
	public String addSale(Model model, @ModelAttribute(ProductSaleDto.dtoName) ProductSaleDto dto) {
		productSaleManagerService.paperAddPage(model,dto);
		return "admin/productSaleManager/addSale";
	}

	@RequestMapping(value = "/ajaxGetAllProdClass", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetAllProdClass(ProductClassDto dto, HttpServletRequest request) {
		return AjaxUtil.success(productClassManagerService.ajaxGetAllProdClass(dto,"-->"));
	}
	
	
	@RequestMapping(value = "/ajaxGetAllProduct", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetAllProduct(HttpServletRequest request) {
		return AjaxUtil.success(productManagerService.ajaxGetAllProduct());
	}
	
	
	@RequestMapping(value = "/ajaxSaleSave", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaleSave(ProductSaleDto dto, HttpServletRequest request) {
		AjaxReturnBean result = null;
		try {
			validateInternalSaleSave(dto);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			result = productSaleManagerService.ajaxSaleSave(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(result);
	}


	private void validateInternalSaleSave(ProductSaleDto dto) {
		setErrorBeans(productSaleManagerService.validateInternalSaleSave(dto));
	}
	
	@RequestMapping(value = "/ajaxProductSaleGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxProductSaleGridList(ProductSaleBean formBean, HttpServletRequest request) {
		validateInternalProductSaleGridList(formBean);
		if (hasError()) {
			return AjaxUtil.validateFail(mesgToJSONString());
		}
		
		return productSaleManagerService.roductSaleGridList(formBean);
	}


	private void validateInternalProductSaleGridList(ProductSaleBean formBean) {
		setErrorBeans(productSaleManagerService.validateInternalProductSaleGridList(formBean));
		
	}
	
	@RequestMapping(value = "/ajaxSaleRemove", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaleRemove(ProductSaleDto dto, HttpServletRequest request) {
		AjaxReturnBean result = null;
		try {
			validateInternalSaleRemove(dto);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			result = productSaleManagerService.ajaxSaleRemove(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.REMOVE_FAIL.getDoc());
		}
		return AjaxUtil.success(result);
	}


	private void validateInternalSaleRemove(ProductSaleDto dto) {
		setErrorBeans(productSaleManagerService.validateInternalSaleRemove(dto));
		
	}
	
}
