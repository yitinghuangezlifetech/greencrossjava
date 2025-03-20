package tw.com.ezlifetech.ezReco.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.ProductSaleBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.common.service.SeqGenService;
import tw.com.ezlifetech.ezReco.dto.ProductSaleDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.enums.ProductStatus;
import tw.com.ezlifetech.ezReco.enums.SaleMode;
import tw.com.ezlifetech.ezReco.enums.SaleTargetTypes;
import tw.com.ezlifetech.ezReco.model.Product;
import tw.com.ezlifetech.ezReco.model.ProductSale;
import tw.com.ezlifetech.ezReco.model.RefSaleDetail;
import tw.com.ezlifetech.ezReco.respository.ProductRespository;
import tw.com.ezlifetech.ezReco.respository.ProductSaleRespository;
import tw.com.ezlifetech.ezReco.respository.RefSaleDetailRespository;
import tw.com.ezlifetech.ezReco.service.ProductSaleManagerService;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;
import tw.com.ezlifetech.ezReco.util.ValidatorUtil;

@Service
public class ProductSaleManagerServiceImpl implements ProductSaleManagerService{

	@Autowired
	private ProductRespository productRespository;
	
	@Autowired
	private ProductSaleRespository productSaleRespository;
	
	@Autowired
	private RefSaleDetailRespository refSaleDetailRespository;
	
	@Autowired
	private SeqGenService seqGenService;
	
	@Override
	public void paperAddPage(Model model, ProductSaleDto dto) {
		
		model.addAttribute("classSelM", "[]");
		model.addAttribute("productSelM", "[]");
		
		
		if(!StringUtil.isBlank(dto.getId()))  {
			ProductSale ps =productSaleRespository.getEntityById(dto.getId());
			dto.setSaleName(ps.getSaleName());
			dto.setSaleMode(ps.getSaleMode());
			dto.setSaleSdateS(DateUtil.parseDateToString(ps.getSaleSdate(), "yyyy/MM/dd"));
			dto.setSaleEdateS(DateUtil.parseDateToString(ps.getSaleEdate(), "yyyy/MM/dd"));
			dto.setSaleTargetType(ps.getSaleTargetType());
			if(dto.getSaleMode().equals(SaleMode.DISCOUNT.getCode())) {
				dto.setDisNumber(ps.getParameter1());
				
			}else if(dto.getSaleMode().equals(SaleMode.BUY_N_GET_M_FREE.getCode())) {
				dto.setBuyN(ps.getParameter1());
				dto.setFreeM(ps.getParameter2());
				
			}else if(dto.getSaleMode().equals(SaleMode.BUY_A_GET_B_FREE.getCode())) {
				dto.setFreeB(ps.getParameter1());
				
			}else if(dto.getSaleMode().equals(SaleMode.FULL_GIFT.getCode())) {
				dto.setFullNumber(ps.getParameter1());
				dto.setGift(ps.getParameter2());
			}
			
			List<RefSaleDetail> rsdList = refSaleDetailRespository.findByJPQL("SELECT r FROM RefSaleDetail r WHERE r.saleId=?", dto.getId());
			
			if(dto.getSaleTargetType().equals(SaleTargetTypes.PRODUCT.getCode())) {
				if(dto.getSaleMode().equals(SaleMode.DISCOUNT.getCode()) || dto.getSaleMode().equals(SaleMode.BUY_A_GET_B_FREE.getCode())) {
					JsonArray ja = new JsonArray();
					for(RefSaleDetail rsd : rsdList) {
						ja.add(rsd.getTargetId());
					}
					model.addAttribute("productSelM", ja.toString());
					
				}else if(dto.getSaleMode().equals(SaleMode.BUY_N_GET_M_FREE.getCode())||dto.getSaleMode().equals(SaleMode.FULL_GIFT.getCode())) {
					dto.setProductSel(rsdList.get(0).getTargetId());
				}
			}else if(dto.getSaleTargetType().equals(SaleTargetTypes.PRODUCT_CLASS.getCode()) || dto.getSaleMode().equals(SaleMode.BUY_A_GET_B_FREE.getCode())) {
				if(dto.getSaleMode().equals(SaleMode.DISCOUNT.getCode())) {
					
					JsonArray ja = new JsonArray();
					for(RefSaleDetail rsd : rsdList) {
						ja.add(rsd.getTargetId());
					}
					model.addAttribute("classSelM", ja.toString());
					
				}else if(dto.getSaleMode().equals(SaleMode.FULL_GIFT.getCode())) {
					dto.setProductClassSel(rsdList.get(0).getTargetId());
				}
			}
		}	
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxSaleSave(ProductSaleDto dto, UserDto loginUser) throws Exception {
		AjaxReturnBean bean = new AjaxReturnBean();
		JsonObject jo = new JsonObject();
		ProductSale ps = null;
		if(StringUtil.isBlank(dto.getId())) {
			ps = new ProductSale();
			dto.setId(seqGenService.getProductSaleNumber());
			ps.setId(dto.getId());
			ps.setCreateTime(DateUtil.getSystemDateTimeObject());
			ps.setCreateUser(loginUser.getId());
		}else {
			ps = productSaleRespository.getEntityById(dto.getId());
			
			if(ps==null) {
				throw new Exception("productSale not found");
			}
			ps.setParameter1("");
			ps.setParameter2("");
			ps.setParameter3("");
			ps.setParameter4("");
			ps.setParameter5("");
			ps.setParameter6("");
			
			
			
			ps.setUpdateTime(DateUtil.getSystemDateTimeObject());
			ps.setUpdateUser(loginUser.getId());
			
		}
		
		ps.setSaleName(dto.getSaleName());
		ps.setSaleMode(dto.getSaleMode());
		jo.addProperty("saleModeValue", dto.getSaleMode());
		
		Date s = DateUtil.parseDay(dto.getSaleSdateS(), "yyyy/MM/dd");
		ps.setSaleSdate(s);
		
		Date e = DateUtil.parseDay(dto.getSaleEdateS(), "yyyy/MM/dd");
		ps.setSaleEdate(e);
		
		ps.setSaleTargetType(dto.getSaleTargetType());
		jo.addProperty("saleTargetTypeValue", dto.getSaleTargetType());
		
		if(dto.getSaleMode().equals(SaleMode.DISCOUNT.getCode())) {
			ps.setParameter1(dto.getDisNumber());
			
		}else if(dto.getSaleMode().equals(SaleMode.BUY_N_GET_M_FREE.getCode())) {
			ps.setParameter1(dto.getBuyN());
			ps.setParameter2(dto.getFreeM());
		}else if(dto.getSaleMode().equals(SaleMode.BUY_A_GET_B_FREE.getCode())) {
			ps.setParameter1(dto.getFreeB());
			jo.addProperty("freeBValue", dto.getFreeB());
		}else if(dto.getSaleMode().equals(SaleMode.FULL_GIFT.getCode())) {
			ps.setParameter1(dto.getFullNumber());
			ps.setParameter2(dto.getGift());
			jo.addProperty("giftValue", dto.getGift());
		}
		
		refSaleDetailRespository.deleteByProperty("saleId", dto.getId());
		
		if(dto.getSaleTargetType().equals(SaleTargetTypes.PRODUCT.getCode())) {
			if(dto.getSaleMode().equals(SaleMode.DISCOUNT.getCode())) {
				for(String t : dto.getProductSelM()) {
					RefSaleDetail rs = new RefSaleDetail();
					rs.setId(System.currentTimeMillis()+StringUtil.getRandomNum(3));
					rs.setSaleId(dto.getId());
					rs.setTargetId(t);
					rs.setCreateUser(loginUser.getId());
					rs.setCreateTime(DateUtil.getSystemDateTimeObject());
					refSaleDetailRespository.save(rs);
				}
			}else if(dto.getSaleMode().equals(SaleMode.BUY_N_GET_M_FREE.getCode())) {
				RefSaleDetail rs = new RefSaleDetail();
				rs.setId(System.currentTimeMillis()+StringUtil.getRandomNum(3));
				rs.setSaleId(dto.getId());
				rs.setTargetId(dto.getProductSel());
				rs.setCreateUser(loginUser.getId());
				rs.setCreateTime(DateUtil.getSystemDateTimeObject());
				refSaleDetailRespository.save(rs);
				jo.addProperty("productSelValue", dto.getProductSel());
				
			}else if(dto.getSaleMode().equals(SaleMode.BUY_A_GET_B_FREE.getCode())) {
				for(String t : dto.getProductSelM()) {
					RefSaleDetail rs = new RefSaleDetail();
					rs.setId(System.currentTimeMillis()+StringUtil.getRandomNum(3));
					rs.setSaleId(dto.getId());
					rs.setTargetId(t);
					rs.setCreateUser(loginUser.getId());
					rs.setCreateTime(DateUtil.getSystemDateTimeObject());
					refSaleDetailRespository.save(rs);
				}
			}else if(dto.getSaleMode().equals(SaleMode.FULL_GIFT.getCode())) {
				RefSaleDetail rs = new RefSaleDetail();
				rs.setId(System.currentTimeMillis()+StringUtil.getRandomNum(3));
				rs.setSaleId(dto.getId());
				rs.setTargetId(dto.getProductSel());
				rs.setCreateUser(loginUser.getId());
				rs.setCreateTime(DateUtil.getSystemDateTimeObject());
				refSaleDetailRespository.save(rs);
				jo.addProperty("productSelValue", dto.getProductSel());
			}
		}else if (dto.getSaleTargetType().equals(SaleTargetTypes.PRODUCT_CLASS.getCode())) {
			if(dto.getSaleMode().equals(SaleMode.DISCOUNT.getCode())) {
				for(String t : dto.getProductClassSelM()) {
					RefSaleDetail rs = new RefSaleDetail();
					rs.setId(System.currentTimeMillis()+StringUtil.getRandomNum(3));
					rs.setSaleId(dto.getId());
					rs.setTargetId(t);
					rs.setCreateUser(loginUser.getId());
					rs.setCreateTime(DateUtil.getSystemDateTimeObject());
					refSaleDetailRespository.save(rs);
				}
			}else if(dto.getSaleMode().equals(SaleMode.BUY_N_GET_M_FREE.getCode())) {
				
			}else if(dto.getSaleMode().equals(SaleMode.BUY_A_GET_B_FREE.getCode())) {
				for(String t : dto.getProductClassSelM()) {
					RefSaleDetail rs = new RefSaleDetail();
					rs.setId(System.currentTimeMillis()+StringUtil.getRandomNum(3));
					rs.setSaleId(dto.getId());
					rs.setTargetId(t);
					rs.setCreateUser(loginUser.getId());
					rs.setCreateTime(DateUtil.getSystemDateTimeObject());
					refSaleDetailRespository.save(rs);
				}
			}else if(dto.getSaleMode().equals(SaleMode.FULL_GIFT.getCode())) {
				RefSaleDetail rs = new RefSaleDetail();
				rs.setId(System.currentTimeMillis()+StringUtil.getRandomNum(3));
				rs.setSaleId(dto.getId());
				rs.setTargetId(dto.getProductClassSel());
				rs.setCreateUser(loginUser.getId());
				rs.setCreateTime(DateUtil.getSystemDateTimeObject());
				refSaleDetailRespository.save(rs);
				jo.addProperty("productClassSelValue", dto.getProductClassSel());
			}
		}
		
		productSaleRespository.save(ps);
		
		jo.addProperty("saleId", dto.getId());
		bean.setValue(jo);
		bean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		return bean;
	}

	@Override
	public List<ErrorBean> validateInternalSaleSave(ProductSaleDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		if(StringUtil.isBlank(dto.getSaleMode())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_saleMode");
			erBean.setLabelName("");
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "特賣模式"));
			list.add(erBean);
		}else {
			if(StringUtil.isBlank(dto.getSaleTargetType())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_saleTargetType");
				erBean.setLabelName("");
				erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "特賣對象類型"));
				list.add(erBean);
			}else {
				if(dto.getSaleMode().equals(SaleMode.DISCOUNT.getCode())) {
					if(StringUtil.isBlank(dto.getDisNumber())) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_disNumber");
						erBean.setLabelName("折扣數百分比(%)");
						erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
						list.add(erBean);
					}else if(dto.getDisNumber().length()>5) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_disNumber");
						erBean.setLabelName("折扣數百分比(%)");
						erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "5"));
						list.add(erBean);
					}else if(!ValidatorUtil.isNumber(dto.getDisNumber())) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_disNumber");
						erBean.setLabelName("折扣數百分比(%)");
						erBean.setMesg(ErrorMesgs.PLZ_INPUT_NUMBER.getDoc());
						list.add(erBean);
					}
				}else if(dto.getSaleMode().equals(SaleMode.BUY_N_GET_M_FREE.getCode())) {
					if(StringUtil.isBlank(dto.getBuyN())) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_buyN");
						erBean.setLabelName("買N");
						erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
						list.add(erBean);
					}else if(dto.getBuyN().length()>5) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_buyN");
						erBean.setLabelName("買N");
						erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "5"));
						list.add(erBean);
					}else if(!ValidatorUtil.isNumber(dto.getBuyN())) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_buyN");
						erBean.setLabelName("買N");
						erBean.setMesg(ErrorMesgs.PLZ_INPUT_NUMBER.getDoc());
						list.add(erBean);
					}
					
					if(StringUtil.isBlank(dto.getFreeM())) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_freeM");
						erBean.setLabelName("送M");
						erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
						list.add(erBean);
					}else if(dto.getFreeM().length()>5) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_freeM");
						erBean.setLabelName("送M");
						erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "5"));
						list.add(erBean);
					}else if(!ValidatorUtil.isNumber(dto.getFreeM())) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_freeM");
						erBean.setLabelName("送M");
						erBean.setMesg(ErrorMesgs.PLZ_INPUT_NUMBER.getDoc());
						list.add(erBean);
					}
					
				}else if(dto.getSaleMode().equals(SaleMode.BUY_A_GET_B_FREE.getCode())) {
					if(StringUtil.isBlank(dto.getFreeB())) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_freeB");
						erBean.setLabelName("");
						erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "送B"));
						list.add(erBean);
					}
				}else if(dto.getSaleMode().equals(SaleMode.FULL_GIFT.getCode())) {
					if(StringUtil.isBlank(dto.getFullNumber())) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_fullNumber");
						erBean.setLabelName("滿額金額");
						erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
						list.add(erBean);
					}else if(dto.getFullNumber().length()>10) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_fullNumber");
						erBean.setLabelName("滿額金額");
						erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "10"));
						list.add(erBean);
					}else if(!ValidatorUtil.isNumber(dto.getFullNumber())) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_fullNumber");
						erBean.setLabelName("滿額金額");
						erBean.setMesg(ErrorMesgs.PLZ_INPUT_NUMBER.getDoc());
						list.add(erBean);
					}
					
					if(StringUtil.isBlank(dto.getGift())) {
						ErrorBean erBean = new ErrorBean();
						erBean.setErrSpanId("err_gift");
						erBean.setLabelName("");
						erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "贈送"));
						list.add(erBean);
					}
				}
				
				
				if(dto.getSaleTargetType().equals(SaleTargetTypes.PRODUCT.getCode())) {
					if(dto.getSaleMode().equals(SaleMode.DISCOUNT.getCode())) {
						if(dto.getProductSelM()==null || dto.getProductSelM().length==0) {
							ErrorBean erBean = new ErrorBean();
							erBean.setErrSpanId("err_productSelM");
							erBean.setLabelName("");
							erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "單一商品"));
							list.add(erBean);
						}
					}else if(dto.getSaleMode().equals(SaleMode.BUY_N_GET_M_FREE.getCode())) {
						if(StringUtil.isBlank(dto.getProductSel())) {
							ErrorBean erBean = new ErrorBean();
							erBean.setErrSpanId("err_productSel");
							erBean.setLabelName("");
							erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "單一商品"));
							list.add(erBean);
						}
					}else if(dto.getSaleMode().equals(SaleMode.BUY_A_GET_B_FREE.getCode())) {
						if(dto.getProductSelM()==null || dto.getProductSelM().length==0) {
							ErrorBean erBean = new ErrorBean();
							erBean.setErrSpanId("err_productSelM");
							erBean.setLabelName("");
							erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "單一商品"));
							list.add(erBean);
						}
					}else if(dto.getSaleMode().equals(SaleMode.FULL_GIFT.getCode())) {
						if(StringUtil.isBlank(dto.getProductSel())) {
							ErrorBean erBean = new ErrorBean();
							erBean.setErrSpanId("err_productSel");
							erBean.setLabelName("");
							erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "單一商品"));
							list.add(erBean);
						}
					}
				}else if (dto.getSaleTargetType().equals(SaleTargetTypes.PRODUCT_CLASS.getCode())) {
					if(dto.getSaleMode().equals(SaleMode.DISCOUNT.getCode())) {
						if(dto.getProductClassSelM()==null || dto.getProductClassSelM().length==0) {
							ErrorBean erBean = new ErrorBean();
							erBean.setErrSpanId("err_productClassSelM");
							erBean.setLabelName("");
							erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "商品分類"));
							list.add(erBean);
						}
					}else if(dto.getSaleMode().equals(SaleMode.BUY_N_GET_M_FREE.getCode())) {
						
					}else if(dto.getSaleMode().equals(SaleMode.BUY_A_GET_B_FREE.getCode())) {
						if(dto.getProductClassSelM()==null || dto.getProductClassSelM().length==0) {
							ErrorBean erBean = new ErrorBean();
							erBean.setErrSpanId("err_productClassSelM");
							erBean.setLabelName("");
							erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "商品分類"));
							list.add(erBean);
						}
					}else if(dto.getSaleMode().equals(SaleMode.FULL_GIFT.getCode())) {
						if(StringUtil.isBlank(dto.getProductClassSel())) {
							ErrorBean erBean = new ErrorBean();
							erBean.setErrSpanId("err_productClassSel");
							erBean.setLabelName("");
							erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "單一商品"));
							list.add(erBean);
						}
					}
				}
				
			}
			
			
		}
		
		if(StringUtil.isBlank(dto.getSaleName())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_saleName");
			erBean.setLabelName("特賣名稱");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else if(dto.getSaleName().length()>100){
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_saleName");
			erBean.setLabelName("特賣名稱");
			erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "100"));
			list.add(erBean);
		}
		
		
		if(StringUtil.isBlank(dto.getSaleSdateS())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_saleSdate");
			erBean.setLabelName("特賣起日");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}
		
		if(StringUtil.isBlank(dto.getSaleEdateS())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_saleEdate");
			erBean.setLabelName("特賣迄日");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}
		
		
		return list;
	}

	@Override
	public String roductSaleGridList(ProductSaleBean formBean) {
		Map<String,Object> params = new HashMap<String,Object>();
		if(!StringUtil.isBlank(formBean.getSaleName())) {
			params.put("saleName", "%"+formBean.getSaleName()+"%");
		}
		
		if(!StringUtil.isBlank(formBean.getSaleMode())) {
			params.put("saleMode", formBean.getSaleMode());
		}
		
		if(!StringUtil.isBlank(formBean.getSaleSdateS())) {
			params.put("saleSdateS", formBean.getSaleSdateS());
		}
		
		if(!StringUtil.isBlank(formBean.getSaleEdateS())) {
			params.put("saleEdateS", formBean.getSaleEdateS());
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("   SELECT *                                         ");
		sql.append("   ,to_char(sale_sdate,'YYYY-MM-DD') as sale_sdates ");
		sql.append("   ,to_char(sale_edate,'YYYY-MM-DD') as sale_edates ");
		sql.append("   ,(CASE WHEN sale_mode ='DIS' THEN '折扣促銷'        ");
		sql.append("   	  WHEN sale_mode ='BNGMF' THEN '買N送M'           ");
		sql.append("   	  WHEN sale_mode ='BAGBF' THEN '買A送B'           ");
		sql.append("   	  WHEN sale_mode ='FG' THEN '滿額贈'               ");
		sql.append("    END )as sale_modes                               ");
		sql.append("   FROM product_sale                                ");
		sql.append("   WHERE 1=1                                        ");
		
		if(params.get("saleName")!=null)
			sql.append("   AND sale_name like :saleName                            ");
		
		if(params.get("saleMode")!=null)
			sql.append("   AND sale_mode = :saleMode                               ");
		
		if(params.get("saleSdateS")!=null)
			sql.append("   AND sale_sdate >= to_timestamp(:saleSdateS, 'YYYY-MM-DD')   ");
		
		if(params.get("saleEdateS")!=null)
			sql.append("   AND sale_edate <= to_timestamp(:saleEdateS, 'YYYY-MM-DD')   ");
		
		sql.append(" order by sale_sdate,sale_edate ");
		
		List<Map<String, Object>> list=productSaleRespository.findListMapBySQL_map(sql.toString(), params);
		for(Map<String, Object> m : list) {
			
		}
		
		
		return PageUtil.transToGridDataSource(list,formBean);
	}

	@Override
	public List<ErrorBean> validateInternalProductSaleGridList(ProductSaleBean formBean) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		if(StringUtil.isBlank(formBean.getSaleSdateS())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_saleSdateS");
			erBean.setLabelName("特賣起日");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}
		
		if(StringUtil.isBlank(formBean.getSaleEdateS())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_saleEdateS");
			erBean.setLabelName("特賣迄日");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}
		
		
		return list;
	}

	@Override
	public void paperPage(Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		Calendar scal = Calendar.getInstance(); 
		scal.add(Calendar.MONTH, -6);
		Calendar ecal = Calendar.getInstance(); 
		ecal.add(Calendar.MONTH, +6);
		
		
		model.addAttribute("sDate", sdf.format(scal.getTime()));
		model.addAttribute("eDate", sdf.format(ecal.getTime()));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxSaleRemove(ProductSaleDto dto, UserDto loginUser) {
		AjaxReturnBean bean = new AjaxReturnBean();
		refSaleDetailRespository.deleteByProperty("saleId", dto.getId());
		productSaleRespository.delete(dto.getId());
		bean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		return bean;
	}

	@Override
	public List<ErrorBean> validateInternalSaleRemove(ProductSaleDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		return list;
	}

	

}
