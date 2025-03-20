package tw.com.ezlifetech.ezReco.common.service;

import java.util.List;

import tw.com.ezlifetech.ezReco.bean.CarSaleBean;
import tw.com.ezlifetech.ezReco.dto.ProductDto;
import tw.com.ezlifetech.ezReco.dto.ProductSaleDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;

public interface ProductPriceCountService {

	public CarSaleBean totalPrice(String prodNo, String proSellPrice, String orderNumber);
	
	public CarSaleBean allPrice(String prodNo, String proSellPrice, String orderNumber);
	
	public CarSaleBean allShoppingCarPrice(UserDto userId);
	
	public List<ProductSaleDto> getNowAllSale();
	
	public boolean isProductInSale(ProductDto pDto,ProductSaleDto psDto);
	
}
