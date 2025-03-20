package tw.com.ezlifetech.ezReco.service;

import java.util.List;

import tw.com.ezlifetech.ezReco.bean.OrderManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.model.Product;
import tw.com.ezlifetech.ezReco.model.RefOrderProd;

public interface OrderManagerService {

	String getOrderList(OrderManagerBean bean, UserDto dto);

	AjaxReturnBean ajaxRemove(RefOrderProd refOrderProd, UserDto dto);

	List<Product> getProductList(RefOrderProd refOrderProd);

}
