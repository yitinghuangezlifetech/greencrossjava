package tw.com.ezlifetech.ezReco.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.ShoppingCarBean;
import tw.com.ezlifetech.ezReco.bean.StoreBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.common.service.SeqGenService;
import tw.com.ezlifetech.ezReco.controller.admin.BulletinController;
import tw.com.ezlifetech.ezReco.dto.ItemOrderDto;
import tw.com.ezlifetech.ezReco.dto.RoleDto;
import tw.com.ezlifetech.ezReco.dto.ShoppingCarDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.ShoppingCarService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;

@Controller
@RequestMapping("/home/shoppingCar")
public class ShoppingCarController extends GenericController{
	private static final String JAVA_NAME="ShoppingCarController";
	private static Logger logger = LoggerFactory.getLogger(ShoppingCarController.class);
	
	@Autowired
	private ShoppingCarService shoppingCarService;
	
	
	
	@RequestMapping(value = "/ajaxAddShoppingCar", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxAddShoppingCar(ShoppingCarDto dto, HttpServletRequest request) {
		String result = "";
		try {
			validateInternalAdd(dto);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			result = shoppingCarService.ajaxSaveShoppingCar(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(result);
	}


	private void validateInternalAdd(ShoppingCarDto dto) {
		setErrorBeans(shoppingCarService.validateInternalAdd(dto));
		
	}
	
	
	
	@RequestMapping("/inCar")
	public String list(Model model) {
		
		notShowHomeBanner();
		if(!isLogin()) {
			return "redirect:/";
		}
		
		return "home/shoppingCar/shoppingCarPage";
	}
	
	@RequestMapping(value = "/ajaxGetAllShoppingCar", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetAllShoppingCar(ShoppingCarBean bean, HttpServletRequest request) {
		JsonObject result = null;
		try {
			result = shoppingCarService.ajaxGetAllShoppingCar(bean,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SEARCH_FAIL.getDoc());
		}

		return AjaxUtil.success(result);
	}
	
	@RequestMapping(value = "/ajaxUpdateShoppingCar", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxUpdateShoppingCar(ShoppingCarDto dto, HttpServletRequest request) {
		String result = "";
		try {
			validateInternalUpdate(dto);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			result = shoppingCarService.ajaxSaveShoppingCar(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(result);
	}


	private void validateInternalUpdate(ShoppingCarDto dto) {
		setErrorBeans(shoppingCarService.validateInternalUpdate(dto));
		
	}
	
	@RequestMapping(value = "/ajaxRemoveShoppingCar", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemoveShoppingCar(ShoppingCarDto dto, HttpServletRequest request) {
		String result = "";
		try {
			validateInternalRemove(dto);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			result = shoppingCarService.ajaxRemoveShoppingCar(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(result);
	}


	private void validateInternalRemove(ShoppingCarDto dto) {
		setErrorBeans(shoppingCarService.validateInternalRemove(dto));
		
	}
	
	@RequestMapping(value = "/ajaxCreateOrderItem", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxCreateOrderItem(ItemOrderDto dto, HttpServletRequest request) {
		String result = "";
		try {
			validateInternalCreateOrderItem(dto);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			result = shoppingCarService.ajaxCreateOrderItem(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(result);
	}


	private void validateInternalCreateOrderItem(ItemOrderDto dto) {
		setErrorBeans(shoppingCarService.validateInternalCreateOrderItem(dto));
		
	}
	
}
