package tw.com.ezlifetech.ezReco.bean;

import java.math.BigDecimal;
import java.util.List;

import tw.com.ezlifetech.ezReco.common.bean.CommonBean;
import tw.com.ezlifetech.ezReco.dto.ProductSaleDto;
import tw.com.ezlifetech.ezReco.dto.RefProdGiftDto;
import tw.com.ezlifetech.ezReco.model.ProductSale;

public class CarSaleBean extends CommonBean {
	public static final String beanFormName = "carSaleBean";

	
	private List<RefProdGiftDto> giftList ;
	private BigDecimal bigResult ;
	
	private ProductSaleDto DISps;
	public List<RefProdGiftDto> getGiftList() {
		return giftList;
	}
	public void setGiftList(List<RefProdGiftDto> giftList) {
		this.giftList = giftList;
	}
	public BigDecimal getBigResult() {
		return bigResult;
	}
	public void setBigResult(BigDecimal bigResult) {
		this.bigResult = bigResult;
	}
	public ProductSaleDto getDISPs() {
		return DISps;
	}
	public void setDISPs(ProductSaleDto DISps) {
		this.DISps = DISps;
	}  
	
	
	
}
