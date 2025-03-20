package tw.com.ezlifetech.ezReco.controller.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.ContactusBean;
import tw.com.ezlifetech.ezReco.bean.StoreBean;
import tw.com.ezlifetech.ezReco.bean.SubAccountManagerBean;
import tw.com.ezlifetech.ezReco.bean.UserFunctionBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.ItemOrderDto;
import tw.com.ezlifetech.ezReco.dto.ProductDto;
import tw.com.ezlifetech.ezReco.dto.RoleDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.StoreService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;

@Controller
@RequestMapping("/home/product")
public class StoreController extends GenericController {
	private static final String JAVA_NAME="StoreController";
	private static Logger logger = LoggerFactory.getLogger(StoreController.class);
	
	@Autowired
	private StoreService storeService;
	
	
	@RequestMapping("/list")
	public String list(Model model,StoreBean storeBean) {

		notShowHeaderSectionIndex();
		storeService.paperPage(model,getLanguage(),"1",storeBean.getProductClassId());
		
		return "home/product/list";
	}

	
	
	@RequestMapping("/directPurchase")
	public String directPurchase(Model model,StoreBean storeBean) {

		if(!isLogin()) {
			return "redirect:/";
		}
		notShowHomeBanner();
		storeService.paperPageDirect(model,storeBean);
		
		return "home/store/directPurchase";
	}
	
	
	@RequestMapping(value = "/ajaxGetProdClassList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetProdClassList(StoreBean formBean, HttpServletRequest request) {
		
		return storeService.ajaxGetProdClassList();
	}
	
	
	@RequestMapping(value = "/ajaxGetProduct", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetProduct(StoreBean bean, HttpServletRequest request) {
		JsonObject result = null;
		try {
			
			bean.setLanguage(getLanguage());
			
			result = storeService.ajaxGetProduct(bean);
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SEARCH_FAIL.getDoc());
		}

		return AjaxUtil.success(result);
	}
	
	@RequestMapping(value = "/ajaxGetProductPrice", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetProductPrice(StoreBean bean, HttpServletRequest request) {
		AjaxReturnBean result = null;
		try {
			result = storeService.ajaxGetProductPrice(bean);
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SEARCH_FAIL.getDoc());
		}

		return AjaxUtil.success(result);
	}
	
	@RequestMapping(value = "/product/{value}", method = RequestMethod.GET)
	public String product(@ModelAttribute(ProductDto.dtoName) ProductDto dto,Model model,HttpServletResponse response,@PathVariable String value) {
		notShowHomeBanner();
		
		
		dto.setLanguage(getLanguage());
		storeService.paperProductPage(dto,model,value);
		
		return "home/product/product";
	}
	
	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public String productNoinput(Model model,StoreBean storeBean) {

		
		return list(model,storeBean);
	}
	
	@RequestMapping(value = "/ajaxCreateOrderItem", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxCreateOrderItem(ItemOrderDto dto, HttpServletRequest request) {
		String result = "";
		try {
			validateInternalCreateOrderItem(dto);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			result = storeService.ajaxCreateOrderItem(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(result);
	}


	private void validateInternalCreateOrderItem(ItemOrderDto dto) {
		setErrorBeans(storeService.validateInternalCreateOrderItem(dto));
		
	}
	
	
	@RequestMapping(value = "/ajaxSendQuest", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSendQuest(StoreBean bean, HttpServletRequest request) {
		AjaxReturnBean result = null;
		try {
			bean.setLanguage(getLanguage());
			validateInternalAjaxSendQuest(bean);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			
			result = storeService.ajaxSendQuest(bean);
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SEARCH_FAIL.getDoc());
		}

		return AjaxUtil.success(result);
	}




	private void validateInternalAjaxSendQuest(StoreBean bean) {
		setErrorBeans(storeService.validateInternalAjaxSendQuest(bean));
		
	}
}
