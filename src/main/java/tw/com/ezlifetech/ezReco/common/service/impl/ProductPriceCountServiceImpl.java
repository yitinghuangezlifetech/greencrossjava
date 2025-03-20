package tw.com.ezlifetech.ezReco.common.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.ezlifetech.ezReco.bean.CarSaleBean;
import tw.com.ezlifetech.ezReco.common.service.ProductPriceCountService;
import tw.com.ezlifetech.ezReco.dto.ProductDto;
import tw.com.ezlifetech.ezReco.dto.ProductSaleDto;
import tw.com.ezlifetech.ezReco.dto.RefProdGiftDto;
import tw.com.ezlifetech.ezReco.dto.RefSaleDetailDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.SaleTargetTypes;
import tw.com.ezlifetech.ezReco.model.Product;
import tw.com.ezlifetech.ezReco.model.ProductSale;
import tw.com.ezlifetech.ezReco.model.RefProdClass;
import tw.com.ezlifetech.ezReco.model.RefSaleDetail;
import tw.com.ezlifetech.ezReco.model.ShoppingCar;
import tw.com.ezlifetech.ezReco.respository.ProductRespository;
import tw.com.ezlifetech.ezReco.respository.ProductSaleRespository;
import tw.com.ezlifetech.ezReco.respository.RefProdClassRespository;
import tw.com.ezlifetech.ezReco.respository.RefSaleDetailRespository;
import tw.com.ezlifetech.ezReco.respository.ShoppingCarRespository;

@Service
public class ProductPriceCountServiceImpl implements ProductPriceCountService{
	
	@Autowired
	private ShoppingCarRespository shoppingCarRespository;
	
	@Autowired
	private ProductRespository productRespository;
	
	@Autowired
	private ProductSaleRespository productSaleRespository;
	
	@Autowired
	private RefSaleDetailRespository refSaleDetailRespository;
	
	@Autowired
	private RefProdClassRespository refProdClassRespository;

	@Override
	public CarSaleBean totalPrice(String prodNo, String proSellPrice, String orderNumber) {
		System.out.println("prodNo:"+prodNo+"  proSellPrice:"+proSellPrice+"  orderNumber:"+orderNumber+"");
		CarSaleBean resBean = new CarSaleBean();
		BigDecimal bigResult = new BigDecimal(0);  
		Map<String,ProductSaleDto> pSaleMap = new HashMap<String,ProductSaleDto>();
		List<RefProdGiftDto> giftList = new ArrayList<RefProdGiftDto>();
		
		List<ProductSaleDto> psDtoList = getNowAllSale();
		boolean isOrgCount=true;
		Product p =productRespository.getEntityById(prodNo);
		ProductDto pDto = new ProductDto(p);
		for(ProductSaleDto psd:psDtoList) {
			if(isProductInSale(pDto,psd)) {
				pDto.getHasProductSale().add(psd);
			}
		}
		if(pDto.hasProductSale()) {
			if(pDto.hasDIS()) {
				
				isOrgCount = false;
				ProductSaleDto mostDIS =pDto.getMostDIS();
				BigDecimal disNumber = new BigDecimal(mostDIS.getParameter1()); 
				BigDecimal bigProprice = new BigDecimal(p.getProSellPrice()); 
				BigDecimal bigOrdernumber = new BigDecimal(orderNumber); 
				
				bigResult = bigProprice.multiply(bigOrdernumber);
				bigResult = bigResult.multiply(disNumber).divide(new BigDecimal("100"));
				resBean.setDISPs(mostDIS);
			}
			if(pDto.hasBNGMF()) {
				ProductSaleDto mostBNGMF =pDto.getMostBNGMF();
				int gifMode = Integer.parseInt(orderNumber)/Integer.parseInt(mostBNGMF.getParameter1());
				BigDecimal gifModeBD = new BigDecimal(gifMode);
				BigDecimal numberM = new BigDecimal(mostBNGMF.getParameter2());
				BigDecimal gifNumber = gifModeBD.multiply(numberM);
				
				if(gifMode>0) {
					RefProdGiftDto g = new RefProdGiftDto();
					g.setProNo(pDto.getId());
					g.setGiftNumber(gifNumber.toString());
					g.setGiftContent(getBNGMFContent(mostBNGMF.getSaleName(),pDto.getProName()+"("+pDto.getProCode()+")",mostBNGMF.getParameter1(),mostBNGMF.getParameter2()));
					giftList.add(g);
				}
				
				
			}
			if(pDto.hasBAGBF()) {
				List<ProductSaleDto> listBAGBF = pDto.getBAGBF();
				for(ProductSaleDto psd:listBAGBF) {
					Product pGift =productRespository.getEntityById(psd.getParameter1());
					RefProdGiftDto g = new RefProdGiftDto();
					g.setProNo(psd.getParameter1());
					g.setGiftNumber(orderNumber);
					g.setGiftContent(getBAGBFContent(psd.getSaleName(),pDto.getProName()+"("+pDto.getProCode()+")",pGift.getProName()+"("+pGift.getProCode()+")"));
					giftList.add(g);
				}
				
			}
			if(pDto.hasFG()) {
				List<ProductSaleDto> listFG = pDto.getFG();
				for(ProductSaleDto psd:listFG) {
					if(pSaleMap.get(psd.getId())==null) {
						pSaleMap.put(psd.getId(), psd);
					}else {
						psd = pSaleMap.get(psd.getId());
					}
					
					BigDecimal fullNumberCount = psd.getFullNumberCount();
					if(!isOrgCount) {
						fullNumberCount = fullNumberCount.add(bigResult);
						psd.setFullNumberCount(fullNumberCount);
						
					}else {
						BigDecimal bigProprice = new BigDecimal(p.getProSellPrice()); 
						BigDecimal bigOrdernumber = new BigDecimal(orderNumber); 
						BigDecimal bigResult2 = new BigDecimal(0);  
						bigResult2 = bigProprice.multiply(bigOrdernumber);
						
						fullNumberCount = fullNumberCount.add(bigResult2);
						psd.setFullNumberCount(fullNumberCount);
						
					}
					pSaleMap.put(psd.getId(), psd);
					
				}				
			}
		}
		
		if(isOrgCount) {
			BigDecimal bigOrdernumber = new BigDecimal(orderNumber); 
			BigDecimal bigProprice = new BigDecimal(proSellPrice); 
			bigResult = bigOrdernumber.multiply(bigProprice);
		}
		
		for(String key:pSaleMap.keySet()) {
			ProductSaleDto psd =pSaleMap.get(key);
			BigDecimal targetPrice = new BigDecimal(psd.getParameter1());
			int comValue = psd.getFullNumberCount().compareTo(targetPrice);
			if(comValue==0 || comValue==1) {
				Product pGift =productRespository.getEntityById(psd.getParameter2());
				RefProdGiftDto g = new RefProdGiftDto();
				g.setProNo(psd.getParameter2());
				g.setGiftNumber("1");
				g.setGiftContent(getBAGBFContent(psd.getSaleName(),pDto.getProName()+"("+pDto.getProCode()+")",pGift.getProName()+"("+pGift.getProCode()+")"));
				giftList.add(g);
			}
		}
		resBean.setBigResult(bigResult);
		resBean.setGiftList(giftList);
		return resBean;
	}

	@Override
	public CarSaleBean allPrice(String prodNo, String proSellPrice, String orderNumber) {
		CarSaleBean resBean = new CarSaleBean();
		BigDecimal bigResult = new BigDecimal(0);  
		Map<String,ProductSaleDto> pSaleMap = new HashMap<String,ProductSaleDto>();
		List<RefProdGiftDto> giftList = new ArrayList<RefProdGiftDto>();
		/*BigDecimal bigOrdernumber = new BigDecimal(orderNumber); 
		BigDecimal bigProprice = new BigDecimal(proSellPrice); 
		bigResult = bigOrdernumber.multiply(bigProprice);*/
		List<ProductSaleDto> psDtoList = getNowAllSale();
		boolean isOrgCount=true;
		Product p =productRespository.getEntityById(prodNo);
		ProductDto pDto = new ProductDto(p);
		for(ProductSaleDto psd:psDtoList) {
			if(isProductInSale(pDto,psd)) {
				pDto.getHasProductSale().add(psd);
			}
		}
		if(pDto.hasProductSale()) {
			if(pDto.hasDIS()) {
				
				isOrgCount = false;
				ProductSaleDto mostDIS =pDto.getMostDIS();
				BigDecimal disNumber = new BigDecimal(mostDIS.getParameter1()); 
				BigDecimal bigProprice = new BigDecimal(p.getProSellPrice()); 
				BigDecimal bigOrdernumber = new BigDecimal(orderNumber); 
				
				bigResult = bigProprice.multiply(bigOrdernumber);
				bigResult = bigResult.multiply(disNumber).divide(new BigDecimal("100"));
				resBean.setDISPs(mostDIS);
			}
			if(pDto.hasBNGMF()) {
				ProductSaleDto mostBNGMF =pDto.getMostBNGMF();
				int gifMode = Integer.parseInt(orderNumber)/Integer.parseInt(mostBNGMF.getParameter1());
				BigDecimal gifModeBD = new BigDecimal(gifMode);
				BigDecimal numberM = new BigDecimal(mostBNGMF.getParameter2());
				BigDecimal gifNumber = gifModeBD.multiply(numberM);
				
				if(gifMode>0) {
					RefProdGiftDto g = new RefProdGiftDto();
					g.setProNo(pDto.getId());
					g.setGiftNumber(gifNumber.toString());
					g.setGiftContent(getBNGMFContent(mostBNGMF.getSaleName(),pDto.getProName()+"("+pDto.getProCode()+")",mostBNGMF.getParameter1(),mostBNGMF.getParameter2()));
					giftList.add(g);
				}
				
				
			}
			if(pDto.hasBAGBF()) {
				List<ProductSaleDto> listBAGBF = pDto.getBAGBF();
				for(ProductSaleDto psd:listBAGBF) {
					Product pGift =productRespository.getEntityById(psd.getParameter1());
					RefProdGiftDto g = new RefProdGiftDto();
					g.setProNo(psd.getParameter1());
					g.setGiftNumber(orderNumber);
					g.setGiftContent(getBAGBFContent(psd.getSaleName(),pDto.getProName()+"("+pDto.getProCode()+")",pGift.getProName()+"("+pGift.getProCode()+")"));
					giftList.add(g);
				}
				
			}
			if(pDto.hasFG()) {
				List<ProductSaleDto> listFG = pDto.getFG();
				for(ProductSaleDto psd:listFG) {
					if(pSaleMap.get(psd.getId())==null) {
						pSaleMap.put(psd.getId(), psd);
					}else {
						psd = pSaleMap.get(psd.getId());
					}
					
					BigDecimal fullNumberCount = psd.getFullNumberCount();
					if(!isOrgCount) {
						fullNumberCount = fullNumberCount.add(bigResult);
						psd.setFullNumberCount(fullNumberCount);
						
					}else {
						BigDecimal bigProprice = new BigDecimal(p.getProSellPrice()); 
						BigDecimal bigOrdernumber = new BigDecimal(orderNumber); 
						BigDecimal bigResult2 = new BigDecimal(0);  
						bigResult2 = bigProprice.multiply(bigOrdernumber);
						
						fullNumberCount = fullNumberCount.add(bigResult2);
						psd.setFullNumberCount(fullNumberCount);
						
					}
					pSaleMap.put(psd.getId(), psd);
					
				}				
			}
		}
		if(isOrgCount) {
			BigDecimal bigOrdernumber = new BigDecimal(orderNumber); 
			BigDecimal bigProprice = new BigDecimal(proSellPrice); 
			bigResult = bigOrdernumber.multiply(bigProprice);
		}
		
		for(String key:pSaleMap.keySet()) {
			ProductSaleDto psd =pSaleMap.get(key);
			BigDecimal targetPrice = new BigDecimal(psd.getParameter1());
			int comValue = psd.getFullNumberCount().compareTo(targetPrice);
			if(comValue==0 || comValue==1) {
				Product pGift =productRespository.getEntityById(psd.getParameter2());
				RefProdGiftDto g = new RefProdGiftDto();
				g.setProNo(psd.getParameter2());
				g.setGiftNumber("1");
				g.setGiftContent(getFGContent(psd.getSaleName(),psd.getParameter1(),pGift.getProName()));
				giftList.add(g);
			}
		}
		resBean.setBigResult(bigResult);
		resBean.setGiftList(giftList);
		return resBean;
	}

	@Override
	public CarSaleBean allShoppingCarPrice(UserDto userId) {
		CarSaleBean resBean = new CarSaleBean();
		List<ShoppingCar>shopCarList = shoppingCarRespository.findByJPQL("SELECT s FROM ShoppingCar s WHERE s.userId=? ", userId.getId());
		BigDecimal bigAllResult = new BigDecimal(0);  
		List<ProductSaleDto> psDtoList = getNowAllSale();
		
		Map<String,ProductSaleDto> pSaleMap = new HashMap<String,ProductSaleDto>();
		List<RefProdGiftDto> giftList = new ArrayList<RefProdGiftDto>();
		
		for(ShoppingCar s : shopCarList) {
			boolean isOrgCount=true;
			Product p =productRespository.getEntityById(s.getProNo());
			ProductDto pDto = new ProductDto(p);
			for(ProductSaleDto psd:psDtoList) {
				if(isProductInSale(pDto,psd)) {
					pDto.getHasProductSale().add(psd);
				}
			}
			
			if(pDto.hasProductSale()) {
				BigDecimal bigResult = new BigDecimal(0);
				if(pDto.hasDIS()) {
					
					isOrgCount = false;
					ProductSaleDto mostDIS =pDto.getMostDIS();
					BigDecimal disNumber = new BigDecimal(mostDIS.getParameter1()); 
					BigDecimal bigProprice = new BigDecimal(p.getProSellPrice()); 
					BigDecimal bigOrdernumber = new BigDecimal(s.getOrderNumber()); 
					
					bigResult = bigProprice.multiply(bigOrdernumber);
					bigResult = bigResult.multiply(disNumber).divide(new BigDecimal("100"));
					bigAllResult = bigAllResult.add(bigResult);
					
				}
				if(pDto.hasBNGMF()) {
					ProductSaleDto mostBNGMF =pDto.getMostBNGMF();
					int gifMode = Integer.parseInt(s.getOrderNumber())/Integer.parseInt(mostBNGMF.getParameter1());
					System.out.println("gifMode:"+gifMode +"|| "+mostBNGMF.getParameter1()+"||"+s.getOrderNumber());
					BigDecimal gifModeBD = new BigDecimal(gifMode);
					BigDecimal numberM = new BigDecimal(mostBNGMF.getParameter2());
					BigDecimal gifNumber = gifModeBD.multiply(numberM);
					if(gifMode>0) {
						RefProdGiftDto g = new RefProdGiftDto();
						g.setProNo(pDto.getId());
						g.setGiftNumber(gifNumber.toString());
						g.setGiftContent(getBNGMFContent(mostBNGMF.getSaleName(),pDto.getProName()+"("+pDto.getProCode()+")",mostBNGMF.getParameter1(),mostBNGMF.getParameter2()));
						giftList.add(g);
					}
					
					
				}
				if(pDto.hasBAGBF()) {
					List<ProductSaleDto> listBAGBF = pDto.getBAGBF();
					for(ProductSaleDto psd:listBAGBF) {
						Product pGift =productRespository.getEntityById(psd.getParameter1());
						RefProdGiftDto g = new RefProdGiftDto();
						g.setProNo(psd.getParameter1());
						g.setGiftNumber(s.getOrderNumber());
						g.setGiftContent(getBAGBFContent(psd.getSaleName(),pDto.getProName()+"("+pDto.getProCode()+")",pGift.getProName()+"("+pGift.getProCode()+")"));
						giftList.add(g);
					}
					
				}
				if(pDto.hasFG()) {
					List<ProductSaleDto> listFG = pDto.getFG();
					for(ProductSaleDto psd:listFG) {
						if(pSaleMap.get(psd.getId())==null) {
							pSaleMap.put(psd.getId(), psd);
						}else {
							psd = pSaleMap.get(psd.getId());
						}
						
						BigDecimal fullNumberCount = psd.getFullNumberCount();
						if(!isOrgCount) {
							fullNumberCount = fullNumberCount.add(bigResult);
							psd.setFullNumberCount(fullNumberCount);
							
						}else {
							BigDecimal bigProprice = new BigDecimal(p.getProSellPrice()); 
							BigDecimal bigOrdernumber = new BigDecimal(s.getOrderNumber()); 
							BigDecimal bigResult2 = new BigDecimal(0);  
							bigResult2 = bigProprice.multiply(bigOrdernumber);
							
							fullNumberCount = fullNumberCount.add(bigResult2);
							psd.setFullNumberCount(fullNumberCount);
							
						}
						pSaleMap.put(psd.getId(), psd);
						
					}				
				}
			}
			
			if(isOrgCount) {
				BigDecimal bigProprice = new BigDecimal(p.getProSellPrice()); 
				BigDecimal bigOrdernumber = new BigDecimal(s.getOrderNumber()); 
				BigDecimal bigResult = new BigDecimal(0);  
				bigResult = bigProprice.multiply(bigOrdernumber);
				bigAllResult = bigAllResult.add(bigResult);
			}
		}
		
		for(String key:pSaleMap.keySet()) {
			ProductSaleDto psd =pSaleMap.get(key);
			BigDecimal targetPrice = new BigDecimal(psd.getParameter1());
			int comValue = psd.getFullNumberCount().compareTo(targetPrice);
			if(comValue==0 || comValue==1) {
				Product pGift =productRespository.getEntityById(psd.getParameter2());
				RefProdGiftDto g = new RefProdGiftDto();
				g.setProNo(psd.getParameter2());
				g.setGiftNumber("1");
				g.setGiftContent(getFGContent(psd.getSaleName(),psd.getParameter1(),pGift.getProName()));
				
				giftList.add(g);
			}
		}
		
		
		
		resBean.setBigResult(bigAllResult);
		resBean.setGiftList(giftList);
		return resBean;
	}


	/* (non-Javadoc)
	 * @see tw.com.website.sample.common.service.ProductPriceCountService#getNowAllSale()
	 * 取得當下所有折扣
	 * 
	 */
	@Override
	public List<ProductSaleDto> getNowAllSale() {
		List<ProductSaleDto> resList = new ArrayList<ProductSaleDto>();
		List<ProductSale>psList= productSaleRespository.findEntityListBySQL("SELECT * FROM product_sale WHERE now() BETWEEN sale_sdate AND sale_edate ");
		
		if(psList!=null) {
			for(ProductSale ps : psList) {
				ProductSaleDto psDto = new ProductSaleDto(ps);
				List<RefSaleDetail> rsDetailList  =refSaleDetailRespository.findByJPQL("SELECT r FROM RefSaleDetail r WHERE r.saleId=?", psDto.getId());
				for(RefSaleDetail rsd :rsDetailList) {
					RefSaleDetailDto dto = new RefSaleDetailDto(rsd);
					psDto.getDetail().add(dto);
				}
				resList.add(psDto);
			}
		}
			
		return resList;
	}

	/* (non-Javadoc)
	 * @see tw.com.website.sample.common.service.ProductPriceCountService#isProductInSale(tw.com.website.sample.dto.ProductDto, tw.com.website.sample.dto.ProductSaleDto)
	 * 判斷該商品是否有在該折扣中
	 */
	@Override
	public boolean isProductInSale(ProductDto pDto, ProductSaleDto psDto) {
		if(psDto.getSaleTargetType().equals(SaleTargetTypes.PRODUCT_CLASS.getCode())) {
			List<RefProdClass>rpcList = refProdClassRespository.findByJPQL("SELECT r FROM RefProdClass r Where r.proNo=?", pDto.getId());
			for(RefSaleDetailDto rsd:psDto.getDetail()) {
				for(RefProdClass rpc:rpcList) {
					if(rsd.getTargetId().equals(rpc.getClassSerno())) {
						return true;
					}
				}
			}
		}else if(psDto.getSaleTargetType().equals(SaleTargetTypes.PRODUCT.getCode())){
			for(RefSaleDetailDto rsd:psDto.getDetail()) {
				if(rsd.getTargetId().equals(pDto.getId())) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	
	public String getFGContent(String saleName,String full,String gift) {
		return "活動『"+saleName+"』<br/>指定商品或分類滿新台幣"+full+"元，送『"+gift+"』";
	}
	
	public String getBAGBFContent(String saleName,String product,String gift) {
		return "活動『"+saleName+"』<br/>『"+product+"』滿足以下條件：購買指定商品或分類，送『"+gift+"』";
	}
	
	public String getBNGMFContent(String saleName,String product,String N,String M) {
		return "活動『"+saleName+"』<br/>購買指定商品，『"+product+"』買"+N+"送"+M+"";
	}
}
