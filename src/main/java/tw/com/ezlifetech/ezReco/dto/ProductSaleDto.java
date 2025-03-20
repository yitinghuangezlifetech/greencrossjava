package tw.com.ezlifetech.ezReco.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import tw.com.ezlifetech.ezReco.common.dto.CommonDto;
import tw.com.ezlifetech.ezReco.model.ProductSale;

public class ProductSaleDto extends CommonDto{
	public static final String dtoName = "productSaleDto";

	
	private String id;
	private String saleName;
	private String saleMode;
	private Date saleSdate;
	private Date saleEdate;
	private String saleTargetType;
	private String parameter1;
	private String parameter2;
	private String parameter3;
	private String parameter4;
	private String parameter5;
	private String parameter6;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String systemMemo;
	
	
	private String saleSdateS;
	private String saleEdateS;
	
	private String[] productClassSelM;
	private String[] productSelM;
	
	private String productClassSel;
	private String productSel;
	
	private String disNumber;
	
	private String buyN;
	private String freeM;
	
	private String freeB;
	
	private String fullNumber;
	private String gift;
	
	private BigDecimal fullNumberCount = new BigDecimal(0);
	
	
	private List<RefSaleDetailDto> detail = new ArrayList<RefSaleDetailDto>();
	
	public ProductSaleDto() {
		
	}
	
	public ProductSaleDto(ProductSale productSale) {
		BeanUtils.copyProperties(productSale, this);
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public Date getSaleSdate() {
		return saleSdate;
	}
	public void setSaleSdate(Date saleSdate) {
		this.saleSdate = saleSdate;
	}
	public Date getSaleEdate() {
		return saleEdate;
	}
	public void setSaleEdate(Date saleEdate) {
		this.saleEdate = saleEdate;
	}
	public String getSaleTargetType() {
		return saleTargetType;
	}
	public void setSaleTargetType(String saleTargetType) {
		this.saleTargetType = saleTargetType;
	}
	public String getParameter1() {
		return parameter1;
	}
	public void setParameter1(String parameter1) {
		this.parameter1 = parameter1;
	}
	public String getParameter2() {
		return parameter2;
	}
	public void setParameter2(String parameter2) {
		this.parameter2 = parameter2;
	}
	public String getParameter3() {
		return parameter3;
	}
	public void setParameter3(String parameter3) {
		this.parameter3 = parameter3;
	}
	public String getParameter4() {
		return parameter4;
	}
	public void setParameter4(String parameter4) {
		this.parameter4 = parameter4;
	}
	public String getParameter5() {
		return parameter5;
	}
	public void setParameter5(String parameter5) {
		this.parameter5 = parameter5;
	}
	public String getParameter6() {
		return parameter6;
	}
	public void setParameter6(String parameter6) {
		this.parameter6 = parameter6;
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
	public String[] getProductClassSelM() {
		return productClassSelM;
	}
	public void setProductClassSelM(String[] productClassSelM) {
		this.productClassSelM = productClassSelM;
	}
	public String[] getProductSelM() {
		return productSelM;
	}
	public void setProductSelM(String[] productSelM) {
		this.productSelM = productSelM;
	}
	public String getProductClassSel() {
		return productClassSel;
	}
	public void setProductClassSel(String productClassSel) {
		this.productClassSel = productClassSel;
	}
	public String getProductSel() {
		return productSel;
	}
	public void setProductSel(String productSel) {
		this.productSel = productSel;
	}
	public String getDisNumber() {
		return disNumber;
	}
	public void setDisNumber(String disNumber) {
		this.disNumber = disNumber;
	}
	public String getBuyN() {
		return buyN;
	}
	public void setBuyN(String buyN) {
		this.buyN = buyN;
	}
	public String getFreeM() {
		return freeM;
	}
	public void setFreeM(String freeM) {
		this.freeM = freeM;
	}
	public String getFreeB() {
		return freeB;
	}
	public void setFreeB(String freeB) {
		this.freeB = freeB;
	}
	public String getFullNumber() {
		return fullNumber;
	}
	public void setFullNumber(String fullNumber) {
		this.fullNumber = fullNumber;
	}
	public String getGift() {
		return gift;
	}
	public void setGift(String gift) {
		this.gift = gift;
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

	public List<RefSaleDetailDto> getDetail() {
		return detail;
	}

	public void setDetail(List<RefSaleDetailDto> detail) {
		this.detail = detail;
	}

	public BigDecimal getFullNumberCount() {
		return fullNumberCount;
	}

	public void setFullNumberCount(BigDecimal fullNumberCount) {
		this.fullNumberCount = fullNumberCount;
	}
	
	
}
