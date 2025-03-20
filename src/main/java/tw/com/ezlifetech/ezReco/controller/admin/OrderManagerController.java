package tw.com.ezlifetech.ezReco.controller.admin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.ezlifetech.ezReco.bean.OrderManagerBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.model.Product;
import tw.com.ezlifetech.ezReco.model.RefOrderProd;
import tw.com.ezlifetech.ezReco.service.OrderManagerService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;

@Controller
@RequestMapping("/admin/orderManager")
public class OrderManagerController extends GenericController {
	private static final String JAVA_NAME = "orderManagerController";
	private static Logger logger = LoggerFactory.getLogger(OrderManagerController.class);

	@Autowired
	private OrderManagerService orderManagerService;

	@RequestMapping("")
	public String orderManager(Model model) {
		return "admin/orderManager/list";
	}

	@RequestMapping("/administrator")
	public String orderManagerAdminstrator(Model model) {
		return "admin/orderManager/administrator";
	}


	@RequestMapping(value = "/editProductList", produces = "application/json;charset=utf-8")
	public String ajaxProduct(RefOrderProd refOrderProd) {
		
		List<Product> prdList = orderManagerService.getProductList(refOrderProd);
		
		return "admin/orderManager/editProduct";
	}
	
	@RequestMapping(value = "/ajaxItemList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxItemList(OrderManagerBean bean) {
		UserDto dto = getLoginUser();
		return orderManagerService.getOrderList(bean, dto);
	}

	@RequestMapping(value = "/ajaxRemove", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemove(RefOrderProd refOrderProd  , UserDto dto) {
		try {
			return AjaxUtil.success(orderManagerService.ajaxRemove(refOrderProd,dto));
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.REMOVE_FAIL.getDoc());
		}
	}
}
