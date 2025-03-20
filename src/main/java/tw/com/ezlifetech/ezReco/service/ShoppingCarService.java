package tw.com.ezlifetech.ezReco.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.ShoppingCarBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.ItemOrderDto;
import tw.com.ezlifetech.ezReco.dto.ShoppingCarDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;

public interface ShoppingCarService {

	String ajaxSaveShoppingCar(ShoppingCarDto dto, UserDto loginUser) throws Exception ;

	List<ErrorBean> validateInternalAdd(ShoppingCarDto dto);

	int getInShoppingCarNumber(UserDto loginUser);

	JsonObject ajaxGetAllShoppingCar(ShoppingCarBean bean,UserDto loginUser);

	List<ErrorBean> validateInternalUpdate(ShoppingCarDto dto);

	List<ErrorBean> validateInternalRemove(ShoppingCarDto dto);

	String ajaxRemoveShoppingCar(ShoppingCarDto dto, UserDto loginUser);

	String ajaxCreateOrderItem(ItemOrderDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalCreateOrderItem(ItemOrderDto dto);
	
}
