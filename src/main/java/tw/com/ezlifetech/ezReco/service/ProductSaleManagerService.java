package tw.com.ezlifetech.ezReco.service;

import java.util.List;

import org.springframework.ui.Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import tw.com.ezlifetech.ezReco.bean.ProductSaleBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.ProductSaleDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;

public interface ProductSaleManagerService {

	void paperAddPage(Model model, ProductSaleDto dto);

	AjaxReturnBean ajaxSaleSave(ProductSaleDto dto, UserDto loginUser) throws Exception;

	List<ErrorBean> validateInternalSaleSave(ProductSaleDto dto);

	String roductSaleGridList(ProductSaleBean formBean);

	List<ErrorBean> validateInternalProductSaleGridList(ProductSaleBean formBean);

	void paperPage(Model model);

	AjaxReturnBean ajaxSaleRemove(ProductSaleDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalSaleRemove(ProductSaleDto dto);

	

}
