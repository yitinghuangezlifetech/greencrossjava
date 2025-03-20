package tw.com.ezlifetech.ezReco.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.CarSaleBean;
import tw.com.ezlifetech.ezReco.bean.StoreBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.common.service.ProductPriceCountService;
import tw.com.ezlifetech.ezReco.common.service.SeqGenService;
import tw.com.ezlifetech.ezReco.dto.ItemOrderDto;
import tw.com.ezlifetech.ezReco.dto.ProductClassDto;
import tw.com.ezlifetech.ezReco.dto.ProductDto;
import tw.com.ezlifetech.ezReco.dto.ProductPicDto;
import tw.com.ezlifetech.ezReco.dto.RefProdGiftDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.CommonStatus;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.enums.OrderItemStatus;
import tw.com.ezlifetech.ezReco.enums.QuestTypes;
import tw.com.ezlifetech.ezReco.model.ItemOrder;
import tw.com.ezlifetech.ezReco.model.Product;
import tw.com.ezlifetech.ezReco.model.ProductClass;
import tw.com.ezlifetech.ezReco.model.ProductPic;
import tw.com.ezlifetech.ezReco.model.QuestionAnswerList;
import tw.com.ezlifetech.ezReco.model.RefOrderProd;
import tw.com.ezlifetech.ezReco.model.RefProdClass;
import tw.com.ezlifetech.ezReco.model.RefProdGift;
import tw.com.ezlifetech.ezReco.respository.ItemOrderRespository;
import tw.com.ezlifetech.ezReco.respository.ProductClassRespository;
import tw.com.ezlifetech.ezReco.respository.ProductPicRespository;
import tw.com.ezlifetech.ezReco.respository.ProductRespository;
import tw.com.ezlifetech.ezReco.respository.QuestionAnswerListRespository;
import tw.com.ezlifetech.ezReco.respository.RefOrderProdRespository;
import tw.com.ezlifetech.ezReco.respository.RefProdClassRespository;
import tw.com.ezlifetech.ezReco.respository.RefProdGiftRespository;
import tw.com.ezlifetech.ezReco.service.ProductClassManagerService;
import tw.com.ezlifetech.ezReco.service.StoreService;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;
import tw.com.ezlifetech.ezReco.util.ValidatorUtil;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StoreServiceImpl implements StoreService{
	private static final String JAVA_NAME="StoreServiceImpl";

	
	@Autowired
	private ProductClassRespository productClassRespository;
	
	@Autowired
	private ProductClassManagerService productClassManagerService;
	
	@Autowired
	private ProductRespository productRespository;
	
	@Autowired
	private ProductPicRespository productPicRespository;
	
	@Autowired
	private RefProdClassRespository refProdClassRespository;
	
	@Autowired
	private ProductPriceCountService productPriceCountService;
	
	@Autowired
	private SeqGenService seqGenService;
	
	@Autowired
	private ItemOrderRespository itemOrderRespository;
	
	@Autowired
	private RefOrderProdRespository refOrderProdRespository;
	
	@Autowired
	private RefProdGiftRespository refProdGiftRespository;
	
	@Autowired
	private QuestionAnswerListRespository questionAnswerListRespository;
	
	
	@Override
	public void paperPage(Model model,String language,String classType,String productClassId) {
		model.addAttribute("prodClassDropdowntree", ajaxGetProdClassList());
		//model.addAttribute("prodClassTreeMap",productClassManagerService.ajaxGetAllProdClass("　"));
		model.addAttribute("productClassId",productClassId);
		model.addAttribute("prodClassTreeMap",ajaxGetAllProdClass("　",language,classType));
		
		
		model.addAttribute("onePageDataSize", "12");

		
	}
	
	private List<Map<String,Object>> ajaxGetAllProdClass(String preStr,String language,String classType) {
		List<ProductClass> pList = new ArrayList<ProductClass>();
		
		pList = productClassRespository.findEntityListByJPQL("SELECT p FROM ProductClass p WHERE p.classType=? AND p.classParentId=? order by sort_index",classType, "000000");
		
		
		List<Map<String,Object>>list = new ArrayList<Map<String,Object>>();
		
		
		
		
		
		if(pList==null || pList.size()==0) {
			
		}else {
			for(ProductClass p : pList) {
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("classId", p.getId().toString());
				String className ="";
				if("en".equals(language)) {
					className = StringUtil.repeatStr(0,preStr)+"‧"+(StringUtil.isBlank(p.getClassNameEn())?"No classification":p.getClassNameEn());
				}else {
					className = StringUtil.repeatStr(0,preStr)+"‧"+p.getClassName();
					
				}
				
				
				map.put("className",className );
				map.put("isRoot", "Y");
				
				
				List<Map<String,String>>childlist = new ArrayList<Map<String,String>>();
				productClassManagerService.deepProdClass(childlist, 1, "　", p.getId(),language);
				map.put("cList", childlist);
				list.add(map);
			}
		}
		
		
		
		return list;
	}

	@Override
	public String ajaxGetProdClassList() {
		JsonArray ja = new JsonArray();
		JsonObject jo = new JsonObject();
		jo.addProperty("text", "全部商品");
		jo.addProperty("value", "  ");
		ja.add(jo);
		deepClassGridList(ja,null,"000000");
		return ja.toString();
	}
	
	
	private JsonElement deepClassGridList(JsonArray pja,JsonObject pjo,String pId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pid", pId);
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT              ");
		sql.append("     class_serno,     ");
		sql.append("     class_name,      ");
		sql.append("     status,          ");
		sql.append("     sort_index,      ");
		sql.append("     create_user,     ");
		sql.append("     create_time,     ");
		sql.append("     update_user,     ");
		sql.append("     update_time,     ");
		sql.append("     class_parent_id  ");
		sql.append("  FROM                ");
		sql.append("     product_class    ");
		sql.append("  WHERE               ");
		sql.append("     class_parent_id=:pid  ");
//		sql.append("  ORDER BY sort_index,class_serno    ");
		sql.append("  ORDER BY sort_index    ");
		List<Map<String, Object>> inList = productClassRespository.findListMapBySQL_map(sql.toString(), params);
		JsonArray ja = new JsonArray();
		if(inList==null ||inList.size()==0) {
			if(!pId.equals("000000")) {
				pja.add(pjo);
			}
			return pjo;
		}else {
			if(!pId.equals("000000")) {
				pja.add(pjo);
				pjo.addProperty("expanded", true);
				pjo.add("items", ja);
			}
		}
		
		for(Map<String, Object> map:inList) {
			JsonObject jo = new JsonObject();
			jo.addProperty("text", map.get("class_name").toString());
			jo.addProperty("value", map.get("class_serno").toString());
			
			
			JsonElement je = deepClassGridList(ja,jo,map.get("class_serno").toString());
			if(pId.equals("000000"))
				pja.add(je);
		}
		return pjo;
	}

	@Override
	public JsonObject ajaxGetProduct(StoreBean bean) {
		StringBuffer sql = new StringBuffer();
		Map<String,Object> params = new HashMap<String,Object>();
		JsonObject jo = new JsonObject();
		JsonArray ja = new JsonArray();
	
		if(!StringUtil.isBlank(bean.getProductClassId())) {
			List<Map<String,Object>> classList = new ArrayList<Map<String,Object>>();
			
			deepClassGridList(classList,bean.getProductClassId());
			List<String> classListP = new ArrayList<String>();
			classListP.add(bean.getProductClassId());
			for(Map<String,Object> m : classList) {
				classListP.add(m.get("class_serno").toString());
				
			}
			
			params.put("productClassId",classListP);
		}
		if(!StringUtil.isBlank(bean.getSearchProductText())) {
			params.put("searchProductText","%"+bean.getSearchProductText()+"%");
		}
		if(StringUtil.isBlank(bean.getOnePageDataSize())) {
			bean.setOnePageDataSize("20");
		}
		params.put("onePageDataSize",Integer.parseInt(bean.getOnePageDataSize()));
		
		if(StringUtil.isBlank(bean.getNowPage())) {
			bean.setNowPage("1");
		}
		int nowPage=Integer.parseInt(bean.getNowPage());
		int onePageDataSize = Integer.parseInt(bean.getOnePageDataSize());
		int offset = ((nowPage-1)*onePageDataSize);
		params.put("offset", offset);
		
		sql.append("   WITH cte AS ( "  );
		sql.append( "   	SELECT distinct pd.*                                       "  );
		sql.append( "   	FROM product pd ,ref_prod_class rpc                        "  );
		sql.append( "   	WHERE 1=1                                                  "  );
		sql.append( "   	AND pd.pro_no = rpc.pro_no                                 "  );
		if(params.get("searchProductText")!=null)
			sql.append( "   	AND pd.pro_name like :searchProductText                "  );
		if(params.get("productClassId")!=null)
			sql.append( "   	AND rpc.class_serno in :productClassId                  "  );
		sql.append( "       AND pd.status = 'A'                                        "  );
		if("tw".equals(bean.getLanguage())) {
			sql.append( "       AND pd.prod_lang != 'en'                                        "  );
		}else {
			sql.append( "       AND pd.prod_lang != 'tw'                                        "  );
			}
		sql.append( "   )SELECT *                                                      "  );
		sql.append( "   FROM (                                                         "  );
		sql.append( "   	TABLE cte                                                  "  );
		sql.append( "   	ORDER BY pro_no                                            "  );
		sql.append( "   	LIMIT :onePageDataSize                                     "  );
		sql.append( "   	OFFSET :offset                                             "  );
		sql.append( "   ) sub                                                          "  );
		sql.append( "   RIGHT  JOIN (SELECT count(*) FROM cte) c(full_count) ON true   "  );
		
		
		List<Map<String, Object>> list=productRespository.findListMapBySQL_map(sql.toString(), params);
		int dataSize=0;
		int totalPage=0;
		if(list.size()>0) {
			dataSize = Integer.parseInt(list.get(0).get("full_count").toString());
			totalPage = dataSize/onePageDataSize+(dataSize%onePageDataSize>0?1:0);
			
		}
		jo.addProperty("dataSize", dataSize);
		jo.addProperty("totalPage", totalPage);
		
		
		ProductClass  pclass =productClassRespository.getEntityById(bean.getProductClassId());
		
		String classDesc = "";
		if(pclass!=null) {
			if("en".equals(bean.getLanguage())) {
				classDesc = StringUtil.isBlank(pclass.getClassDescEn())?"":pclass.getClassDescEn();
			}else {
				classDesc = StringUtil.isBlank(pclass.getClassDesc())?"":pclass.getClassDesc();
			}
		}
		
		jo.addProperty("classDesc", classDesc);
		System.out.println("listlist:"+list);
		if(dataSize>0)
			for(Map<String, Object> map : list) {
				JsonObject joo = new JsonObject();
				for(String k :map.keySet()) {
					String input =map.get(k)==null?"": map.get(k).toString();
					
					if(k.equals("pro_sell_price")) {
						NumberFormat numberFormat1 = NumberFormat.getNumberInstance();  
						input =numberFormat1.format(Double.parseDouble(input));
					}
					
					if(k.equals("pro_name")) {
						if("en".equals(bean.getLanguage())) {
							input = map.get("pro_name_en")==null?"": map.get("pro_name_en").toString();
						}
					}
					
					if(k.equals("pro_spec")) {
						if("en".equals(bean.getLanguage())) {
							input = map.get("pro_spec_en")==null?"": map.get("pro_spec_en").toString();
						}
					}
					
					
					joo.addProperty(k, input);
					
					
					
				}
				joo.addProperty("pro_main_pict",productRespository.getProdMainPictByProdId(map.get("pro_no").toString()) );
				ja.add(joo);
			}
		jo.add("data", ja);
		
		return jo;
	}
	
	private void deepClassGridList(List<Map<String, Object>> list,String pId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pid", pId);
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT              ");
		sql.append("     class_serno     ");
		sql.append("  FROM                ");
		sql.append("     product_class    ");
		sql.append("  WHERE               ");
		sql.append("     class_parent_id=:pid  ");
		sql.append("  ORDER BY sort_index    ");
//		sql.append("  ORDER BY sort_index,class_serno    ");
		List<Map<String, Object>> inList = productClassRespository.findListMapBySQL_map(sql.toString(), params);
		
		if(inList==null ||inList.size()==0) {
			return;
		}
		
		for(Map<String, Object> map:inList) {
			
			list.add(map);
			map.put("id", map.get("class_serno").toString());
			map.put("parentId", pId.equals("000000")?null:pId);
			deepClassGridList(list,map.get("class_serno").toString());
		}
		
	}

	@Override
	public void paperProductPage(ProductDto dto,Model model, String value) {
		Product product =productRespository.getEntityById(value);
		BeanUtils.copyProperties(product, dto);
		
		if("en".equals(dto.getLanguage())) {
			dto.setProName(dto.getProNameEn());
			dto.setProSpec(dto.getProSpecEn());
			dto.setProHtml(dto.getProHtmlEn());
			dto.setProParamHtml(dto.getProParamHtmlEn());
		}
		
		
		NumberFormat numberFormat1 = NumberFormat.getNumberInstance();  
		String input =numberFormat1.format(Double.parseDouble(dto.getProSellPrice()));
		dto.setProSellPrice(input);
		dto.setProHtml(StringEscapeUtils.unescapeHtml(dto.getProHtml()));
		dto.setProParamHtml(StringEscapeUtils.unescapeHtml(dto.getProParamHtml()));
		List<RefProdClass> refList =refProdClassRespository.findByJPQL("SELECT r FROM RefProdClass r WHERE r.proNo=?", dto.getId());
		List<ProductClassDto> pcDtoList = new ArrayList<ProductClassDto>();
		
		for(RefProdClass r : refList) {
			ProductClass pc =productClassRespository.getEntityById(r.getClassSerno());
			ProductClassDto pcDto = new ProductClassDto(pc);
			if("en".equals(dto.getLanguage())) {
				pcDto.setClassName(pcDto.getClassNameEn()==null?"":pcDto.getClassNameEn());
			}
			pcDtoList.add(pcDto);
		}
		
		dto.setProductClassDtoList(pcDtoList);
		
		
		List<ProductPicDto> picDtoList = new ArrayList<ProductPicDto>();
		List<ProductPic> picList =productPicRespository.findByJPQL("SELECT p FROM ProductPic p WHERE p.proNo=? ORDER BY p.isMainPic , p.id", dto.getId());
		
		
		int i = 0;
		for(ProductPic p : picList) {
			ProductPicDto pDto = new ProductPicDto(p);
			if(i==0) {
				pDto.setFirst(true);
			}else {
				pDto.setFirst(false);
			}
			pDto.setPicIndex(i+"");
			picDtoList.add(pDto);
			i++;
		}
		
		if(picDtoList.size()==0) {
			model.addAttribute("havePict", "N");
		}else{
			model.addAttribute("havePict", "Y");
		}
		
		dto.setProductPicDtoList(picDtoList);
		
		
	}

	@Override
	public void paperPageDirect(Model model, StoreBean storeBean) {
		Product product = productRespository.getEntityById(storeBean.getProNo());
		List<Map<String,String>> giftMap = new ArrayList<Map<String,String>>();
		model.addAttribute("prodNo", product.getId());
		model.addAttribute("prodPict", productRespository.getProdMainPictByProdId(storeBean.getProNo()));
		
		model.addAttribute("prodName", product.getProName());
		model.addAttribute("prodSpec", product.getProSpec());
		
		model.addAttribute("prodPrice", product.getProSellPrice());
		model.addAttribute("prodOrderNumber", storeBean.getOrderNumber());
		CarSaleBean  cBeanTotalPrice = productPriceCountService.totalPrice(product.getId(),product.getProSellPrice(),storeBean.getOrderNumber());
		CarSaleBean  cBeanAllPrice =  productPriceCountService.allPrice(product.getId(),product.getProSellPrice(),storeBean.getOrderNumber());

		if(cBeanTotalPrice.getDISPs()!=null) {
			model.addAttribute("DISPs", cBeanTotalPrice.getDISPs().getParameter1());
		}else {
			model.addAttribute("DISPs", "");
		}
		
		NumberFormat numberFormat1 = NumberFormat.getNumberInstance();
		
		String prodTotalPrice = cBeanTotalPrice.getBigResult().toString();
		String allPrice =cBeanAllPrice.getBigResult().toString();
		model.addAttribute("prodTotalPrice", numberFormat1.format(Double.parseDouble(prodTotalPrice.toString())));
		model.addAttribute("allPrice",numberFormat1.format(Double.parseDouble(allPrice.toString())));
		
		
		
		for(RefProdGiftDto gift :cBeanAllPrice.getGiftList()) {
			Map<String,String> map = new HashMap<String,String>();
			
			Product gProd = productRespository.getEntityById(gift.getProNo());
			
			map.put("giftProNo", gift.getProNo());
			map.put("giftName", gProd.getProName());
			map.put("giftSpec", gProd.getProSpec());
			map.put("giftCode", gProd.getProCode());
			map.put("giftNumber", gift.getGiftNumber());
			map.put("giftContent", gift.getGiftContent());
			map.put("giftpict", productRespository.getProdMainPictByProdId(gift.getProNo()));
			giftMap.add(map);
		}
		
		model.addAttribute("giftList",giftMap);
		model.addAttribute("isZero", giftMap.size()==0?"Y":"N");
	}

	

	@Override
	public AjaxReturnBean ajaxGetProductPrice(StoreBean bean) {
		AjaxReturnBean returnBean = new AjaxReturnBean();
		JsonArray giftJa = new JsonArray();
		JsonObject jo = new JsonObject();
		Product product = productRespository.getEntityById(bean.getProNo());
		
		NumberFormat numberFormat1 = NumberFormat.getNumberInstance();
		
		CarSaleBean  cBeanTotalPrice = productPriceCountService.totalPrice(product.getId(),product.getProSellPrice(),bean.getOrderNumber());
		CarSaleBean  cBeanAllPrice =  productPriceCountService.allPrice(product.getId(),product.getProSellPrice(),bean.getOrderNumber());
		
		
		String prodTotalPrice = cBeanTotalPrice.getBigResult().toString();
		String allPrice = cBeanAllPrice.getBigResult().toString();
		
		if(cBeanTotalPrice.getDISPs()!=null) {
			jo.addProperty("DISPs", cBeanTotalPrice.getDISPs().getParameter1());
		}else {
			jo.addProperty("DISPs", "");
		}
		
		jo.addProperty("prodTotalPrice", numberFormat1.format(Double.parseDouble(prodTotalPrice.toString())));
		jo.addProperty("allPrice",numberFormat1.format(Double.parseDouble(allPrice.toString())));
		
		
		
		for(RefProdGiftDto gift :cBeanAllPrice.getGiftList()) {
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
		
		jo.add("giftList", giftJa);
		
		returnBean.setMessage(AjaxMesgs.CHANGE_SUCCESSFUL.getDoc());
		returnBean.setValue(jo);
		return returnBean;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String ajaxCreateOrderItem(ItemOrderDto dto, UserDto loginUser) {
		String seq = seqGenService.getItemOrderNumber();
		
		Product product = productRespository.getEntityById(dto.getProNo());
				
		ItemOrder order = new ItemOrder();
		order.setId(seq);
		order.setStatus(OrderItemStatus.EFFECTIVE.getCode());
		order.setUserId(loginUser.getId());
		order.setOrderTime(DateUtil.getSystemDateTimeObject());
		CarSaleBean  cBeanTotalPrice =productPriceCountService.allPrice(product.getId(),product.getProSellPrice(),dto.getOrderNumber());
		
		order.setTotalAmount(cBeanTotalPrice.getBigResult().toString());
		order.setMemo(dto.getMemo());
		order.setSendAddress(dto.getSendAddress());
		order.setRecipient(dto.getRecipient());
		order.setCreateTime(DateUtil.getSystemDateTimeObject());
		order.setCreateUser(loginUser.getId());
		
		itemOrderRespository.save(order);
		
		RefOrderProd r  = new RefOrderProd();
		r.setId(System.currentTimeMillis()+StringUtil.getRandomNum(3));
		r.setOrderNo(seq);
		r.setProNo(dto.getProNo());
		r.setOrderNumber(dto.getOrderNumber());
		r.setCreateTime(DateUtil.getSystemDateTimeObject());
		r.setCreateUser(loginUser.getId());
		refOrderProdRespository.save(r);
		
		for(RefProdGiftDto gift :cBeanTotalPrice.getGiftList()) {
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

	@Override
	public List<ErrorBean> validateInternalAjaxSendQuest(StoreBean bean) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		if(StringUtil.isBlank(bean.getQuestName())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_name");
			erBean.setLabelName("en".equals(bean.getLanguage())?"Name":"姓名");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc(bean.getLanguage()));
			list.add(erBean);
		}
		
		if(StringUtil.isBlank(bean.getQuestMail())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_mail");
			erBean.setLabelName("en".equals(bean.getLanguage())?"E-mail":"E-mail");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc(bean.getLanguage()));
			list.add(erBean);
		}else {
			if(!ValidatorUtil.isValidEmail(bean.getQuestMail())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_mail");
				erBean.setLabelName("en".equals(bean.getLanguage())?"E-mail":"E-mail");
				erBean.setMesg(ErrorMesgs.DATA_FORMAT_NOT_MATCH.getDoc(bean.getLanguage()));
				list.add(erBean);
			}
		}
		
		if(StringUtil.isBlank(bean.getQuestTel())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_tel");
			erBean.setLabelName("en".equals(bean.getLanguage())?"Contact number":"連絡電話");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc(bean.getLanguage()));
			list.add(erBean);
		}
		
		if(StringUtil.isBlank(bean.getQuestTitle())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_title");
			erBean.setLabelName("en".equals(bean.getLanguage())?"Subject":"主旨");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc(bean.getLanguage()));
			list.add(erBean);
		}
		
		if(StringUtil.isBlank(bean.getQuestContent())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_content");
			erBean.setLabelName("en".equals(bean.getLanguage())?"Message":"內容");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc(bean.getLanguage()));
			list.add(erBean);
		}
		
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxSendQuest(StoreBean bean) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		QuestionAnswerList q = new QuestionAnswerList();
		q.setId(seqGenService.getSystemTimeRandomNumber());
		q.setQuestType(QuestTypes.PROUD_QUEST.getCode());
		q.setStatus(CommonStatus.WAIT_TO_BE_PROCESSED.getStatusCode());
		q.setRefId(bean.getProdId());
		q.setQuestName(bean.getQuestName());
		q.setQuestMail(bean.getQuestMail());
		q.setQuestTitle(bean.getQuestTitle());
		q.setQuestContent(bean.getQuestContent());
		q.setQuestPhone(bean.getQuestTel());
		q.setIsAns("N");
		q.setCreateTime(DateUtil.getSystemDateTimeObject());
		q.setCreateUser("notLoginUser");
		
		questionAnswerListRespository.save(q);
		
		
		
		
		ajaxReturnBean.setMessage(AjaxMesgs.SUBMIT_SUCCESSFUL.getDoc(bean.getLanguage()));
		return ajaxReturnBean;
	}


	
}
