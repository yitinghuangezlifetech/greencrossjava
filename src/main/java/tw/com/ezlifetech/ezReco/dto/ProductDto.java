package tw.com.ezlifetech.ezReco.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import tw.com.ezlifetech.ezReco.common.dto.CommonDto;
import tw.com.ezlifetech.ezReco.enums.SaleMode;
import tw.com.ezlifetech.ezReco.model.Product;
import tw.com.ezlifetech.ezReco.model.ProductPic;

public class ProductDto extends CommonDto{
	public static final String dtoName = "productDto";
	private String id;
	private String proCode;
	private String prodLang;
	private String proName;
	private String proNameEn;
	private String proSpec;
	private String proSpecEn;
	private String proSellPrice;
	private String proInPrice;
	private String status;
	private String proHtml;
	private String proParamHtml;
	private String proHtmlEn;
	private String proParamHtmlEn;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String systemMemo;
	
	private String [] prodClass;
	private String [] prodPicts;
	
	private List<ProductClassDto> productClassDtoList;
	private List<ProductPicDto> productPicDtoList;
	
	
	private List<ProductSaleDto> hasProductSale = new ArrayList<ProductSaleDto>();
	
	public ProductDto() {
		
	}
	public ProductDto(Product product) {
		BeanUtils.copyProperties(product, this);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProCode() {
		return proCode;
	}
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	public String getProdLang() {
		return prodLang;
	}
	public void setProdLang(String prodLang) {
		this.prodLang = prodLang;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getProSpec() {
		return proSpec;
	}
	public void setProSpec(String proSpec) {
		this.proSpec = proSpec;
	}
	public String getProSellPrice() {
		return proSellPrice;
	}
	public void setProSellPrice(String proSellPrice) {
		this.proSellPrice = proSellPrice;
	}
	public String getProInPrice() {
		return proInPrice;
	}
	public void setProInPrice(String proInPrice) {
		this.proInPrice = proInPrice;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProHtml() {
		return proHtml;
	}
	public void setProHtml(String proHtml) {
		this.proHtml = proHtml;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getSystemMemo() {
		return systemMemo;
	}
	public void setSystemMemo(String systemMemo) {
		this.systemMemo = systemMemo;
	}
	public String [] getProdClass() {
		return prodClass;
	}
	public void setProdClass(String [] prodClass) {
		this.prodClass = prodClass;
	}
	public String [] getProdPicts() {
		return prodPicts;
	}
	public void setProdPicts(String [] prodPicts) {
		this.prodPicts = prodPicts;
	}
	public List<ProductClassDto> getProductClassDtoList() {
		return productClassDtoList;
	}
	public void setProductClassDtoList(List<ProductClassDto> productClassDtoList) {
		this.productClassDtoList = productClassDtoList;
	}
	public List<ProductPicDto> getProductPicDtoList() {
		return productPicDtoList;
	}
	public void setProductPicDtoList(List<ProductPicDto> productPicDtoList) {
		this.productPicDtoList = productPicDtoList;
	}
	public List<ProductSaleDto> getHasProductSale() {
		return hasProductSale;
	}
	public void setHasProductSale(List<ProductSaleDto> hasProductSale) {
		this.hasProductSale = hasProductSale;
	}
	
	public boolean hasProductSale() {
		return getHasProductSale().size()>0;
	}
	
	public boolean hasDIS() {
		for(ProductSaleDto p : getHasProductSale()) {
			if(p.getSaleMode().equals(SaleMode.DISCOUNT.getCode())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasBNGMF() {
		for(ProductSaleDto p : getHasProductSale()) {
			if(p.getSaleMode().equals(SaleMode.BUY_N_GET_M_FREE.getCode())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasBAGBF() {
		for(ProductSaleDto p : getHasProductSale()) {
			if(p.getSaleMode().equals(SaleMode.BUY_A_GET_B_FREE.getCode())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasFG() {
		for(ProductSaleDto p : getHasProductSale()) {
			if(p.getSaleMode().equals(SaleMode.FULL_GIFT.getCode())) {
				return true;
			}
		}
		return false;
	}
	
	public ProductSaleDto getMostDIS() {
		ProductSaleDto res = null;
		for(ProductSaleDto p : getHasProductSale()) {
			if(p.getSaleMode().equals(SaleMode.DISCOUNT.getCode())) {
				if(res==null) {
					res = p;
				}else {
					if(Double.parseDouble(p.getParameter1())<Double.parseDouble(res.getParameter1())) {
						res=p;
					}
				}
			}
		}
		return res;
	}
	
	public ProductSaleDto getMostBNGMF() {
		ProductSaleDto res = null;
		for(ProductSaleDto p : getHasProductSale()) {
			if(p.getSaleMode().equals(SaleMode.BUY_N_GET_M_FREE.getCode())) {
				if(res==null) {
					res = p;
				}else {
					
					BigDecimal pN = new BigDecimal(p.getParameter1());
					BigDecimal pM = new BigDecimal(p.getParameter2());
					BigDecimal pValue = pM.divide(pN);
					
					
					BigDecimal rN = new BigDecimal(res.getParameter1());
					BigDecimal rM = new BigDecimal(res.getParameter2());
					BigDecimal rValue = rM.divide(rN);
					
					int i=pValue.compareTo(rValue);
					if(i==1) {
						res = p;
					}
				}
			}
		}
		return res;
	}
	
	public List<ProductSaleDto> getBAGBF(){
		List<ProductSaleDto> resList = new ArrayList<ProductSaleDto>();
		for(ProductSaleDto p : getHasProductSale()) {
			if(p.getSaleMode().equals(SaleMode.BUY_A_GET_B_FREE.getCode())) {
				resList.add(p);
			}
		}
		return resList;
	}
	
	public List<ProductSaleDto> getFG(){
		List<ProductSaleDto> resList = new ArrayList<ProductSaleDto>();
		for(ProductSaleDto p : getHasProductSale()) {
			if(p.getSaleMode().equals(SaleMode.FULL_GIFT.getCode())) {
				resList.add(p);
			}
		}
		return resList;
	}
	public String getProNameEn() {
		return proNameEn;
	}
	public void setProNameEn(String proNameEn) {
		this.proNameEn = proNameEn;
	}
	public String getProSpecEn() {
		return proSpecEn;
	}
	public void setProSpecEn(String proSpecEn) {
		this.proSpecEn = proSpecEn;
	}
	public String getProHtmlEn() {
		return proHtmlEn;
	}
	public void setProHtmlEn(String proHtmlEn) {
		this.proHtmlEn = proHtmlEn;
	}
	public String getProParamHtml() {
		return proParamHtml;
	}
	public void setProParamHtml(String proParamHtml) {
		this.proParamHtml = proParamHtml;
	}
	public String getProParamHtmlEn() {
		return proParamHtmlEn;
	}
	public void setProParamHtmlEn(String proParamHtmlEn) {
		this.proParamHtmlEn = proParamHtmlEn;
	}
}
