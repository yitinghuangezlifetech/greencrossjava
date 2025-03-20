package tw.com.ezlifetech.ezReco.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.CarSaleBean;
import tw.com.ezlifetech.ezReco.bean.ShoppingCarBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.common.service.ProductPriceCountService;
import tw.com.ezlifetech.ezReco.common.service.SeqGenService;
import tw.com.ezlifetech.ezReco.dto.ItemOrderDto;
import tw.com.ezlifetech.ezReco.dto.RefProdGiftDto;
import tw.com.ezlifetech.ezReco.dto.ShoppingCarDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.enums.OrderItemStatus;
import tw.com.ezlifetech.ezReco.model.ItemOrder;
import tw.com.ezlifetech.ezReco.model.Product;
import tw.com.ezlifetech.ezReco.model.RefOrderProd;
import tw.com.ezlifetech.ezReco.model.RefProdGift;
import tw.com.ezlifetech.ezReco.model.ShoppingCar;
import tw.com.ezlifetech.ezReco.respository.ItemOrderRespository;
import tw.com.ezlifetech.ezReco.respository.ProductRespository;
import tw.com.ezlifetech.ezReco.respository.RefOrderProdRespository;
import tw.com.ezlifetech.ezReco.respository.RefProdGiftRespository;
import tw.com.ezlifetech.ezReco.respository.ShoppingCarRespository;
import tw.com.ezlifetech.ezReco.service.ShoppingCarService;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ShoppingCarServiceImpl implements ShoppingCarService{

	@Autowired
	private ShoppingCarRespository shoppingCarRespository;
	
	@Autowired
	private SeqGenService seqGenService;
	
	@Autowired
	private ProductRespository productRespository;
	
	@Autowired
	private ItemOrderRespository itemOrderRespository;
	
	@Autowired
	private RefOrderProdRespository refOrderProdRespository;
	
	@Autowired
	private ProductPriceCountService productPriceCountService;
	
	@Autowired
	private RefProdGiftRespository refProdGiftRespository;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String ajaxSaveShoppingCar(ShoppingCarDto dto, UserDto loginUser) throws Exception {
		ShoppingCar shoppingCar =null;
		
		if(StringUtil.isBlank(dto.getId())) {
			shoppingCar = new ShoppingCar();
			shoppingCar.setId(seqGenService.getShoppingCarNumber());
			shoppingCar.setCreateTime(DateUtil.getSystemDateTimeObject());
			shoppingCar.setCreateUser(loginUser.getId());
		}else {
			shoppingCar = shoppingCarRespository.getEntityById(dto.getId());
			if(shoppingCar==null) {
				 throw new Exception("shoppingCar not found");
			}
			dto.setProNo(shoppingCar.getProNo());
			dto.setUserId(shoppingCar.getUserId());
			shoppingCar.setUpdateTime(DateUtil.getSystemDateTimeObject());
			shoppingCar.setUpdateUser(loginUser.getId());
		}
		shoppingCar.setProNo(dto.getProNo());
		shoppingCar.setUserId(dto.getUserId());
		shoppingCar.setOrderNumber(dto.getOrderNumber());
		
		shoppingCarRespository.save(shoppingCar);
		if(StringUtil.isBlank(dto.getId())) {
			loginUser.setInShoppingCarNum(loginUser.getInShoppingCarNum()+1);
		}
			
		return StringUtil.isBlank(dto.getId())?AjaxMesgs.ADD_SUCCESSFUL.getDoc():AjaxMesgs.CHANGE_SUCCESSFUL.getDoc();
	}

	@Override
	public List<ErrorBean> validateInternalAdd(ShoppingCarDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		System.out.println(" dto.getUserId()"+ dto.getUserId());
		System.out.println(" dto.getProNo()"+ dto.getProNo());
		List<ShoppingCar>shopCarList = shoppingCarRespository.findByJPQL("SELECT s FROM ShoppingCar s WHERE s.userId=? AND s.proNo=?", dto.getUserId(),dto.getProNo());
		if(shopCarList!=null) {
			if(shopCarList.size()>0) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("none");
				erBean.setLabelName("該商品");
				erBean.setMesg(ErrorMesgs.DATA_EXIST_IN_SHOPPING_CAR.getDoc());
				list.add(erBean);
			}
		}
		
		
		
		
		if(StringUtil.isBlank(dto.getOrderNumber())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("none");
			erBean.setLabelName("商品數量");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
			
		}else {
			if(!StringUtil.isInteger(dto.getOrderNumber())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("none");
				erBean.setLabelName("商品數量");
				erBean.setMesg(ErrorMesgs.PLZ_INPUT_NUMBER.getDoc());
				list.add(erBean);
			}else {
				if(Integer.parseInt(dto.getOrderNumber())<0) {
					ErrorBean erBean = new ErrorBean();
					erBean.setErrSpanId("none");
					erBean.setLabelName("商品數量");
					erBean.setMesg(ErrorMesgs.DATA_CAN_NOT_UNDER_ZERO.getDoc());
					list.add(erBean);
				}
			}
			
			
		}
		
		
		return list;
	}

	@Override
	public int getInShoppingCarNumber(UserDto loginUser) {
		List<ShoppingCar>shopCarList = shoppingCarRespository.findByJPQL("SELECT s FROM ShoppingCar s WHERE s.userId=? ", loginUser.getId());
		return shopCarList.size();
	}

	@Override
	public JsonObject ajaxGetAllShoppingCar(ShoppingCarBean bean,UserDto loginUser) {
		JsonObject rjo= new JsonObject();
		JsonArray ja = new JsonArray();
		JsonArray giftJa = new JsonArray();
		Map<String,Object> params = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer();
		
		params.put("userId", loginUser.getId());
		
		sql.append("    SELECT                                   ");
		sql.append("    	sc.id as shopid ,                    ");
		sql.append("    	sc.user_id as onwerid,                ");
		sql.append("    	sc.order_number as ordernumber,      ");
		sql.append("    	prd.pro_no as prono,                 ");
		sql.append("    	prd.pro_name as proname,             ");
		sql.append("    	prd.pro_spec as prospec,             ");
		sql.append("    	prd.pro_code as procode,             ");
		sql.append("    	prd.pro_sell_price as proprice       ");
		sql.append("    FROM shopping_car sc , product prd       ");
		sql.append("    WHERE  sc.pro_no = prd.pro_no            ");
		sql.append("    AND    sc.user_id = :userId               ");
		sql.append("    ORDER BY  sc.create_time                 ");
		
		List<Map<String, Object>> list=shoppingCarRespository.findListMapBySQL_map(sql.toString(), params);
		NumberFormat numberFormat1 = NumberFormat.getNumberInstance();  
		 
		for(Map<String, Object> s : list) {
			JsonObject jo = new JsonObject();
			jo.addProperty("shopid", s.get("shopid").toString());
			jo.addProperty("onwerid", s.get("onwerid").toString());
			jo.addProperty("prono", s.get("prono").toString());
			jo.addProperty("proname", s.get("proname").toString());
			jo.addProperty("prospec", s.get("prospec").toString());
			jo.addProperty("procode", s.get("procode").toString());
			String proprice =numberFormat1.format(Double.parseDouble(s.get("proprice").toString()));
			jo.addProperty("proprice", proprice);
			jo.addProperty("ordernumber", s.get("ordernumber").toString());
			
			CarSaleBean  cBean = productPriceCountService.totalPrice(s.get("prono").toString(), s.get("proprice").toString(), s.get("ordernumber").toString());
			if(cBean.getDISPs()!=null) {
				jo.addProperty("DISPs", cBean.getDISPs().getParameter1());
			}else {
				jo.addProperty("DISPs", "");
			}
			String totalPrice = cBean.getBigResult().toString();
			
		
			jo.addProperty("totalPrice", numberFormat1.format(Double.parseDouble(totalPrice.toString())));
			jo.addProperty("pict", productRespository.getProdMainPictByProdId(s.get("prono").toString()));
			ja.add(jo);
			
			
		}
		CarSaleBean  cBeanAll = productPriceCountService.allShoppingCarPrice(loginUser);
		//giftJa
		
		for(RefProdGiftDto gift :cBeanAll.getGiftList()) {
			JsonObject gJo = new JsonObject();
			
			Product gProd = productRespository.getEntityById(gift.getProNo());
			
			gJo.addProperty("giftProNo", gift.getProNo());
			gJo.addProperty("giftName", gProd.getProName());
			gJo.addProperty("giftSpec", gProd.getProSpec());
			gJo.addProperty("giftCode", gProd.getProCode());
			gJo.addProperty("giftNumber", gift.getGiftNumber());
			gJo.addProperty("giftContent", gift.getGiftContent());
			gJo.addProperty("giftpict", productRespository.getProdMainPictByProdId(gift.getProNo()));
			giftJa.add(gJo);
		}
		
		rjo.add("giftList", giftJa);
		rjo.add("shopCarList", ja);
		rjo.addProperty("allPrice", numberFormat1.format(Double.parseDouble(cBeanAll.getBigResult().toString())));
		
		return rjo;
	}

	@Override
	public List<ErrorBean> validateInternalUpdate(ShoppingCarDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		ShoppingCar e =shoppingCarRespository.getEntityById(dto.getId());
		if(e==null) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("none");
			erBean.setLabelName("該商品");
			erBean.setMesg(ErrorMesgs.DATA_NOT_EXIST.getDoc());
			list.add(erBean);
		}
		
		if(StringUtil.isBlank(dto.getOrderNumber())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("none");
			erBean.setLabelName("商品數量");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
			
		}else {
			if(!StringUtil.isInteger(dto.getOrderNumber())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("none");
				erBean.setLabelName("商品數量");
				erBean.setMesg(ErrorMesgs.PLZ_INPUT_NUMBER.getDoc());
				list.add(erBean);
			}else {
				if(Integer.parseInt(dto.getOrderNumber())<0) {
					ErrorBean erBean = new ErrorBean();
					erBean.setErrSpanId("none");
					erBean.setLabelName("商品數量");
					erBean.setMesg(ErrorMesgs.DATA_CAN_NOT_UNDER_ZERO.getDoc());
					list.add(erBean);
				}
			}
			
			
		}
		
		return list;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String ajaxRemoveShoppingCar(ShoppingCarDto dto, UserDto loginUser) {
		ShoppingCar e =shoppingCarRespository.getEntityById(dto.getId());
		shoppingCarRespository.delete(e);
		loginUser.setInShoppingCarNum(loginUser.getInShoppingCarNum()-1);
		return AjaxMesgs.REMOVE_SUCCESSFUL.getDoc();
	}

	@Override
	public List<ErrorBean> validateInternalRemove(ShoppingCarDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		ShoppingCar e =shoppingCarRespository.getEntityById(dto.getId());
		if(e==null) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("none");
			erBean.setLabelName("該商品");
			erBean.setMesg(ErrorMesgs.DATA_NOT_EXIST.getDoc());
			list.add(erBean);
		}
		
		
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String ajaxCreateOrderItem(ItemOrderDto dto, UserDto loginUser) {
		
		String seq = seqGenService.getItemOrderNumber();
		
		ItemOrder order = new ItemOrder();
		order.setId(seq);
		order.setStatus(OrderItemStatus.EFFECTIVE.getCode());
		order.setUserId(loginUser.getId());
		order.setOrderTime(DateUtil.getSystemDateTimeObject());
		CarSaleBean  cBean = productPriceCountService.allShoppingCarPrice(loginUser);
		
		
		order.setTotalAmount(cBean.getBigResult().toString());
		order.setMemo(dto.getMemo());
		order.setSendAddress(dto.getSendAddress());
		order.setRecipient(dto.getRecipient());
		order.setCreateTime(DateUtil.getSystemDateTimeObject());
		order.setCreateUser(loginUser.getId());
		
		
		itemOrderRespository.save(order);
		
		List<ShoppingCar>shopCarList = shoppingCarRespository.findByJPQL("SELECT s FROM ShoppingCar s WHERE s.userId=? ", loginUser.getId());
		
		for(ShoppingCar s : shopCarList) {
			RefOrderProd r  = new RefOrderProd();
			r.setId(System.currentTimeMillis()+StringUtil.getRandomNum(3));
			r.setOrderNo(seq);
			r.setProNo(s.getProNo());
			r.setOrderNumber(s.getOrderNumber());
			r.setCreateTime(DateUtil.getSystemDateTimeObject());
			r.setCreateUser(loginUser.getId());
			refOrderProdRespository.save(r);
		}
		
		for(ShoppingCar s : shopCarList) {
			shoppingCarRespository.delete(s);
		}
		
		for(RefProdGiftDto gift :cBean.getGiftList()) {
			RefProdGift g = new RefProdGift();
			g.setId(seqGenService.getProductGiftNumber());
			g.setOrderNo(order.getId());
			g.setProNo(gift.getProNo());
			g.setGiftNumber(gift.getGiftNumber());
			g.setGiftContent(gift.getGiftContent());
			g.setCreateUser(loginUser.getId());
			g.setCreateTime(DateUtil.getSystemDateTimeObject());
			refProdGiftRespository.save(g);
		}
		
		
		
		
		loginUser.setInShoppingCarNum(0);
		return AjaxMesgs.ADD_SUCCESSFUL.getDoc();
	}

	@Override
	public List<ErrorBean> validateInternalCreateOrderItem(ItemOrderDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		if(StringUtil.isBlank(dto.getRecipient())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_recipient");
			erBean.setLabelName("收件人");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else {
			if(dto.getRecipient().length()>30) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_recipient");
				erBean.setLabelName("收件人");
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "30"));
				list.add(erBean);
			}
		}
		
		if(StringUtil.isBlank(dto.getSendAddress())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_sendAddress");
			erBean.setLabelName("寄件地址");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else {
			if(dto.getSendAddress().length()>300) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_sendAddress");
				erBean.setLabelName("寄件地址");
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "300"));
				list.add(erBean);
			}
		}
		
		
		if(dto.getMemo().length()>300) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_memo");
			erBean.setLabelName("備註");
			erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "300"));
			list.add(erBean);
		}
		
		
		return list;
	}

	

}
