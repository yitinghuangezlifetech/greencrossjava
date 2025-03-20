package tw.com.ezlifetech.ezReco.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.ezlifetech.ezReco.bean.OrderManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.model.Product;
import tw.com.ezlifetech.ezReco.model.RefOrderProd;
import tw.com.ezlifetech.ezReco.respository.ItemOrderRespository;
import tw.com.ezlifetech.ezReco.respository.RefOrderProdRespository;
import tw.com.ezlifetech.ezReco.service.OrderManagerService;
import tw.com.ezlifetech.ezReco.util.DAOUtill;
import tw.com.ezlifetech.ezReco.util.PageUtil;

@Service
public class OrderManagerServiceImpl implements OrderManagerService {

	@Autowired
	private ItemOrderRespository itemOrderRespository;
	@Autowired
	private RefOrderProdRespository refOrderProdRespository;

	@Override
	public String getOrderList(OrderManagerBean bean, UserDto dto) {
		String orderNo = "'" + bean.getOrderNo() + "'";
		// 如沒輸入預設所有資料
		if (bean.getOrderNo().trim() == null || bean.getOrderNo().trim() == "") {
			// 使用者商品管理
			List<Map<String, Object>> itemOrderList = itemOrderRespository.getItemOrderByUserid(dto);
			orderNo = DAOUtill.setSQLInList(itemOrderList, "order_no");
		}
		List<Map<String, Object>> prod = refOrderProdRespository.getRefOrderProdByOrderNo(orderNo);

		return PageUtil.transToGridDataSource(prod, bean);
	}

	@Override
	public AjaxReturnBean ajaxRemove(RefOrderProd refOrderProd, UserDto dto) {
		return refOrderProdRespository.ajaxRemove(refOrderProd, dto);
	}

	@Override
	public List<Product> getProductList(RefOrderProd refOrderProd) {

		String orderNo = "'" + refOrderProd.getOrderNo() + "'";

		List<Map<String, Object>> prod = refOrderProdRespository.getRefOrderProdByOrderNo(orderNo);

		String productString = DAOUtill.setSQLInList(prod, "Id");

		System.out.println("productString=" + productString);

		return null;
	}
}
