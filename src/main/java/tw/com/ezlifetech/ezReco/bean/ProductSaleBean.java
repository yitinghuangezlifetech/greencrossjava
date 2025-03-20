package tw.com.ezlifetech.ezReco.bean;

import java.util.Date;

import tw.com.ezlifetech.ezReco.common.bean.CommonBean;

public class ProductSaleBean extends CommonBean {
	public static final String beanFormName = "productSaleBean";
	
	private String saleName;
	private String saleMode;
	private String saleSdateS;
	private String saleEdateS;
	
	
	
	public String getSaleName() {
		return saleName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public String getSaleMode() {
		return saleMode;
	}
	public void setSaleMode(String saleMode) {
		this.saleMode = saleMode;
	}
	public String getSaleSdateS() {
		return saleSdateS;
	}
	public void setSaleSdateS(String saleSdateS) {
		this.saleSdateS = saleSdateS;
	}
	public String getSaleEdateS() {
		return saleEdateS;
	}
	public void setSaleEdateS(String saleEdateS) {
		this.saleEdateS = saleEdateS;
	}
	
	

}
