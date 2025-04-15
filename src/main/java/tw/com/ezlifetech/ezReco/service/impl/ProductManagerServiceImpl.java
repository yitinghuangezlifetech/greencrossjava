package tw.com.ezlifetech.ezReco.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.ProductManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.common.service.SeqGenService;
import tw.com.ezlifetech.ezReco.dto.ProductDto;
import tw.com.ezlifetech.ezReco.dto.ProductPicDto;
import tw.com.ezlifetech.ezReco.dto.RefProdArDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.ArItemDlActions;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.enums.ProductStatus;
import tw.com.ezlifetech.ezReco.model.ArItemDl;
import tw.com.ezlifetech.ezReco.model.ArItemHt;
import tw.com.ezlifetech.ezReco.model.Product;
import tw.com.ezlifetech.ezReco.model.ProductPic;
import tw.com.ezlifetech.ezReco.model.RefProdAr;
import tw.com.ezlifetech.ezReco.model.RefProdClass;
import tw.com.ezlifetech.ezReco.respository.ArItemDlRespository;
import tw.com.ezlifetech.ezReco.respository.ArItemHtRespository;
import tw.com.ezlifetech.ezReco.respository.ProductPicRespository;
import tw.com.ezlifetech.ezReco.respository.ProductRespository;
import tw.com.ezlifetech.ezReco.respository.RefProdArRepository;
import tw.com.ezlifetech.ezReco.respository.RefProdClassRespository;
import tw.com.ezlifetech.ezReco.service.ArItemUploadService;
import tw.com.ezlifetech.ezReco.service.ProductManagerService;
import tw.com.ezlifetech.ezReco.util.ConfigUtil;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;
import tw.com.ezlifetech.ezReco.util.ValidatorUtil;

@Service
public class ProductManagerServiceImpl  implements ProductManagerService{
	@Autowired
	private ProductRespository productRespository;
	
	@Autowired
	private ProductPicRespository productPicRespository;
	
	@Autowired
	private RefProdClassRespository refProdClassRespository;
	
	@Autowired
	private SeqGenService seqGenService;

	@Autowired
	private RefProdArRepository refProdArRepository; 
	
	@Autowired
	private ArItemHtRespository arItemHtRespository;
	
	@Autowired
	private ArItemDlRespository arItemDlRespository;
	
	@Override
	public void paperPage(Model model, ProductDto dto) {
		if(!StringUtil.isBlank(dto.getId())) {
			Product product = productRespository.getEntityById(dto.getId());
			BeanUtils.copyProperties(product, dto , "id");
			
			List<RefProdClass> refList = refProdClassRespository.findEntityListByJPQL("SELECT r FROM RefProdClass r WHERE r.proNo=?", dto.getId());
			JsonArray ja = new JsonArray();
			for(RefProdClass r :refList) {
				ja.add(r.getClassSerno());
			}
			model.addAttribute("classSel", ja.toString());
			
			// 取出AR資料
			RefProdAr refProdAr = refProdArRepository.getEntityByProperty("proNo", dto.getId());
			if(refProdAr != null) {
				RefProdArDto refProdArDto = new RefProdArDto();
				refProdArDto.setProNo(refProdAr.getProNo());
				refProdArDto.setArId(refProdAr.getArId());
				ArItemHt arItemHt = arItemHtRespository.getEntityById(refProdAr.getArId());
				refProdArDto.setItemName(arItemHt.getItemName());
				refProdArDto.setItemContent(arItemHt.getItemContent());
				List<ArItemDl> dlList = arItemDlRespository.findEntityListByJPQL("SELECT d FROM ArItemDl d WHERE d.htId=?", refProdAr.getArId());
				refProdArDto.setDlId(dlList.get(0).getId());
				refProdArDto.setAction(dlList.get(0).getAction());
				refProdArDto.setVar(dlList.get(0).getVar());
				model.addAttribute("refProdArDto", refProdArDto);
			}
		}else {
			model.addAttribute("classSel", "[]");
		}
		model.addAttribute("actionList", actionSelect());
	}
	
	private List<Map<String,Object>> actionSelect() {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(ArItemDlActions a : ArItemDlActions.values()) {
			
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("text", a.getValue());
			m.put("code", a.getCode());
			list.add(m);
		}		
		return list;
	}
	
	@Override
	public List<ErrorBean> validateInternalSave(ProductDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		if(dto.getProName().length()>200) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_proName");
			erBean.setLabelName("商品名稱");
			erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "200"));
			list.add(erBean);
		}
		
		if(!StringUtil.isBlank(dto.getProNameEn())) {
			if(dto.getProNameEn().length()>200) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_proNameEn");
				erBean.setLabelName("商品名稱(英文)");
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "200"));
				list.add(erBean);
			}
		}
		
		
		if(StringUtil.isBlank(dto.getProCode())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_proCode");
			erBean.setLabelName("商品代號");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else if(dto.getProCode().length()>50) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_proCode");
			erBean.setLabelName("商品代號");
			erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "50"));
			list.add(erBean);
		}
		
		if(dto.getProSpec().length()>300) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_proSpec");
			erBean.setLabelName("商品規格");
			erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "300"));
			list.add(erBean);
		}
		
		
		
		if(!StringUtil.isBlank(dto.getProSpecEn())) {
			if(dto.getProSpecEn().length()>300) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_proSpecEn");
				erBean.setLabelName("商品規格(英文)");
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "300"));
				list.add(erBean);
			}
		}
		
		if(StringUtil.isBlank(dto.getProSellPrice())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_proSellPrice");
			erBean.setLabelName("商品售價");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else if(!ValidatorUtil.isNumber(dto.getProSellPrice())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_proSellPrice");
			erBean.setLabelName("商品售價");
			erBean.setMesg(ErrorMesgs.PLZ_INPUT_NUMBER.getDoc());
			list.add(erBean);
		}else if(dto.getProSellPrice().length()>20) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_proSellPrice");
			erBean.setLabelName("商品售價");
			erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "20")+"(含小數)");
			list.add(erBean);
		}
		
		if(!StringUtil.isBlank(dto.getProInPrice())) {
			if(!ValidatorUtil.isNumber(dto.getProInPrice())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_proInPrice");
				erBean.setLabelName("商品進價");
				erBean.setMesg(ErrorMesgs.PLZ_INPUT_NUMBER.getDoc());
				list.add(erBean);
			}else if(dto.getProInPrice().length()>20) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_proInPrice");
				erBean.setLabelName("商品進價");
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "20")+"(含小數)");
				list.add(erBean);
			}
		}
		
		if(StringUtil.isBlank(dto.getStatus())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_status");
			erBean.setLabelName("");
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "商品狀態"));
			list.add(erBean);
		}
		
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxSaveProduct(ProductDto dto, UserDto userDto) throws Exception {
		Product product=null;
		if(StringUtil.isBlank(dto.getId())) {
			product = new Product();
			product.setId(seqGenService.getProductNumber());
			dto.setId(product.getId());
			product.setCreateUser(userDto.getId());
			product.setCreateTime(DateUtil.getSystemDateTimeObject());
			
		}else {
			product = productRespository.getEntityById(dto.getId());
			if(product==null) {
				 throw new Exception("product not found");
			}
			product.setUpdateUser(userDto.getId());
			product.setUpdateTime(DateUtil.getSystemDateTimeObject());
		}
		product.setProdLang(dto.getProdLang());
		product.setProName(dto.getProName());
		product.setProNameEn(dto.getProNameEn());
		product.setProCode(dto.getProCode());
		product.setProSpec(dto.getProSpec());
		product.setProSpecEn(dto.getProSpecEn());
		product.setProSellPrice(dto.getProSellPrice());
		product.setProInPrice(dto.getProInPrice());
		product.setStatus(dto.getStatus());
		product.setProHtml(dto.getProHtml());
		product.setProHtmlEn(dto.getProHtmlEn());
		product.setProParamHtml(dto.getProParamHtml());
		product.setProParamHtmlEn(dto.getProParamHtmlEn());
		productRespository.save(product);
		
		
		
		refProdClassRespository.deleteByProperty("proNo", dto.getId());
		
		for(String classNo : dto.getProdClass()) {
			RefProdClass rpc = new RefProdClass();
			rpc.setId(""+System.currentTimeMillis()+StringUtil.getRandomNum(3));
			rpc.setProNo(dto.getId());
			rpc.setClassSerno(classNo);
			rpc.setCreateUser(userDto.getId());
			rpc.setCreateTime(DateUtil.getSystemDateTimeObject());
			refProdClassRespository.save(rpc);
		}
		
		JsonObject jo = new JsonObject();
		jo.addProperty("prodId", dto.getId());
		AjaxReturnBean bean = new AjaxReturnBean();
		bean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		bean.setValue(jo);
		
		return bean;
	}

	@Override
	public String productGridList(ProductManagerBean formBean) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(!StringUtil.isBlank(formBean.getProName())) {
			params.put("proName", "%"+formBean.getProName()+"%");
		}
		
		if(!StringUtil.isBlank(formBean.getProCode())) {
			params.put("proCode", formBean.getProCode());
		}
		
		if(!StringUtil.isBlank(formBean.getStatus())) {
			params.put("status", formBean.getStatus());
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT                            ");
		sql.append("  	pro_no,                       ");
		sql.append("  	pro_name,                     ");
		sql.append("  	pro_code,                     ");
		sql.append("  	pro_spec,                     ");
		sql.append("  	CASE WHEN status='A' THEN '啟用' WHEN status='S' THEN '停用' END as status,  ");
		sql.append("  	create_user,                  ");
		sql.append("  	create_time,                  ");
		sql.append("  	update_user,                  ");
		sql.append("  	update_time                   ");
		sql.append("  FROM                              ");
		sql.append("  	product                       ");
		sql.append("  WHERE                             ");
		sql.append("  	1=1                           ");
		if(params.get("proName")!=null)
			sql.append("  AND pro_name like :proName        ");
		if(params.get("proCode")!=null)
			sql.append("  AND pro_code = :proCode           ");
		if(params.get("status")!=null)
			sql.append("  AND status = :status              ");
		sql.append(" ORDER BY pro_code ");

		
		List<Map<String, Object>> list = productRespository.findListMapBySQL_map(sql.toString(), params);
		
		
		
		return PageUtil.transToGridDataSource(list,formBean);
	}

	@Override
	public void uploadFile(MultipartFile asyncFiles, ProductPicDto dto) throws Exception {
		dto.setTmpId(seqGenService.getProductPicNumber());
		dto.setContentType(asyncFiles.getContentType());
		String filePath = ConfigUtil.getValue("uploadpath")+File.separator+ConfigUtil.PRODUCT_PIC_PATH;
		File dir = new File(filePath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		String origFileName = asyncFiles.getOriginalFilename();
		String fileName = dto.getTmpId()+StringUtil.getSubFileName(origFileName);
		File file = new File(filePath, fileName);
		FileUtils.writeByteArrayToFile(file, asyncFiles.getBytes());
		//File.separator會依不同OS判斷要用"/"or"\"去串接
		dto.setPicPatch(ConfigUtil.PRODUCT_PIC_PATH+File.separator+fileName);
		
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean saveProductPic(ProductPicDto productPicDto, UserDto loginUser) {
		AjaxReturnBean bean = new AjaxReturnBean();
		ProductPic pic = new ProductPic();
		pic.setId(productPicDto.getTmpId());
		pic.setProNo(productPicDto.getProNo());
		pic.setPicPatch(productPicDto.getPicPatch());
		pic.setCreateUser(loginUser.getId());
		pic.setCreateTime(DateUtil.getSystemDateTimeObject());
		pic.setContentType(productPicDto.getContentType());
		productPicRespository.save(pic);
		
		JsonObject jo = new JsonObject();
		jo.addProperty("prodId", pic.getId());
		bean.setMessage(AjaxMesgs.UPLAOD_SUCCESSFUL.getDoc());
		bean.setValue(jo);
		return bean;
	}

	@Override
	public void getProductPic(HttpServletResponse response, String value) throws Exception {
	    
	    String filePath = ConfigUtil.getValue("uploadpath");
	    File picFile = null;
	    ProductPic pic = productPicRespository.getEntityById(value);
	    if(pic != null) {
	        String picPatch = pic.getPicPatch().replace("\\", "/");
	        picFile = Paths.get(filePath, picPatch).toFile();
	        response.setContentType(pic.getContentType());
	    } else {
	        try (InputStream is = getClass().getResourceAsStream("/shopping-bag.png")) {
	            if (is == null) {
	                throw new FileNotFoundException("shopping-bag.png資源不存在");
	            }
	            response.setContentType("image/png");
	            response.setContentLength(is.available());
	            FileCopyUtils.copy(is, response.getOutputStream());
	            return;
	        }
	    }

	    response.setContentLength((int) picFile.length());
	    FileCopyUtils.copy(Files.readAllBytes(picFile.toPath()), response.getOutputStream());
	}


	@Override
	public JsonArray ajaxGetAllProdPic(ProductDto dto) {
		JsonArray ja = new JsonArray();
		
		if(!StringUtil.isBlank(dto.getId())) {
			List<ProductPic> list =productPicRespository.findByJPQL("SELECT p FROM ProductPic p WHERE p.proNo = ? ORDER BY p.createTime ", dto.getId());
			for(ProductPic p : list) {
				String isMain="";
				JsonObject jo = new JsonObject();
				jo.addProperty("name", p.getId());
				
				if(p.getIsMainPic()==null) {
					isMain = "N";
				}else {
					isMain = p.getIsMainPic().equals("Y")?"Y":"N";
				}
				
				jo.addProperty("isMain", isMain);
				ja.add(jo);
			}
		}
		
		
		return ja;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxRemovePic(ProductPicDto dto) throws Exception {
		AjaxReturnBean bean = new AjaxReturnBean();
		ProductPic pp = productPicRespository.getEntityById(dto.getId());
		
		File pict = new File(ConfigUtil.getValue("uploadpath")+File.separator+pp.getPicPatch());
		if(pict.exists()) {
			pict.delete();
		}
		
		productPicRespository.delete(pp);
		bean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		
		return bean;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxSetMainPic(ProductPicDto dto ,UserDto userDto) {
		AjaxReturnBean bean = new AjaxReturnBean();
		productPicRespository.updateBySQL("UPDATE product_pic SET is_main_pic = null WHERE pro_no = ?",dto.getProNo());
		
		ProductPic pp = productPicRespository.getEntityById(dto.getId());
		pp.setIsMainPic("Y");
		pp.setUpdateUser(userDto.getId());
		pp.setUpdateTime(DateUtil.getSystemDateTimeObject());
		
		productPicRespository.save(pp);
		
		bean.setMessage(AjaxMesgs.APPLY_SUCCESSFUL.getDoc());
		return bean;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxRemove(ProductDto dto) throws Exception {
		AjaxReturnBean bean = new AjaxReturnBean();
		Product p = productRespository.getEntityById(dto.getId());
		
		refProdClassRespository.deleteByProperty("proNo", dto.getId());
		List<ProductPic>ppList = productPicRespository.findByJPQL("SELECT p FROM ProductPic p WHERE p.proNo=?", dto.getId());
		for(ProductPic pp : ppList) {
			
			ajaxRemovePic(new ProductPicDto(pp));
		}
		
		productRespository.delete(p);
		
		// 將AR關聯一併刪除
		RefProdAr refProdAr = refProdArRepository.getEntityByProperty("proNo", dto.getId()); 
		if(refProdAr != null) {
			ArItemHt arItemHt = arItemHtRespository.getEntityById(refProdAr.getArId());
			if(arItemHt != null) {
				List<ArItemDl> arItemDlList = arItemDlRespository.findEntityListByProperty("htId", arItemHt.getId(), null);
				for(ArItemDl arItemDl : arItemDlList) {
					arItemDlRespository.delete(arItemDl);
				}
				arItemHtRespository.delete(arItemHt);
			}
			refProdArRepository.delete(refProdAr);
		}
		
		bean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		return bean;
	}

	@Override
	public JsonArray ajaxGetAllProduct() {
		JsonArray ja = new JsonArray();
		
		List<Product> pList = productRespository.findByJPQL("SELECT p FROM Product p WHERE p.status = ?", ProductStatus.ALIVE.getCode());
		for(Product p :pList) {
			JsonObject jo = new JsonObject();
			jo.addProperty("id", p.getId());
			jo.addProperty("proCode", p.getProCode());
			jo.addProperty("proName", p.getProName());
			ja.add(jo);
		}
		
		
		return ja;
		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveRefProdAr(String proNo, String arId) throws Exception {
		RefProdAr refProdAr = new RefProdAr();
		refProdAr.setId(DateUtil.getSystemDateTime()+seqGenService.getProductNumber());
		refProdAr.setProNo(proNo);
		refProdAr.setArId(arId);
		refProdArRepository.save(refProdAr);
	}

	
	
	
	
}
