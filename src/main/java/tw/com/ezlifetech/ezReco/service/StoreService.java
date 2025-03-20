package tw.com.ezlifetech.ezReco.service;

import java.util.List;

import org.springframework.ui.Model;

import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.StoreBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.ItemOrderDto;
import tw.com.ezlifetech.ezReco.dto.ProductDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;

public interface StoreService {

	String ajaxGetProdClassList();

	void paperPage(Model model,String language,String classType,String productClassId);

	JsonObject ajaxGetProduct(StoreBean bean);

	void paperProductPage(ProductDto dto,Model model, String value);

	void paperPageDirect(Model model, StoreBean storeBean);

	AjaxReturnBean ajaxGetProductPrice(StoreBean bean);

	String ajaxCreateOrderItem(ItemOrderDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalCreateOrderItem(ItemOrderDto dto);

	List<ErrorBean> validateInternalAjaxSendQuest(StoreBean bean);

	AjaxReturnBean ajaxSendQuest(StoreBean bean);

}
