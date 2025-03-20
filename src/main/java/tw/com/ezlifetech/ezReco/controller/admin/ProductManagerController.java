package tw.com.ezlifetech.ezReco.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import tw.com.ezlifetech.ezReco.bean.ProductManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.ProductClassDto;
import tw.com.ezlifetech.ezReco.dto.ProductDto;
import tw.com.ezlifetech.ezReco.dto.ProductPicDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.ArItemUploadService;
import tw.com.ezlifetech.ezReco.service.ProductClassManagerService;
import tw.com.ezlifetech.ezReco.service.ProductManagerService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Controller
@RequestMapping("/admin/productManager")
public class ProductManagerController extends GenericController{
	private static final String JAVA_NAME="ProductManagerController";
	private static Logger logger = LoggerFactory.getLogger(ProductManagerController.class);
	
	@Autowired
	private ProductManagerService productManagerService;
	
	@Autowired
	private ProductClassManagerService productClassManagerService;
	
	@RequestMapping("/prodList")
	public String prodList(Model model, @ModelAttribute(ProductManagerBean.beanFormName) ProductManagerBean formBean) {
		
		return "admin/productManager/list";
	}
	
	@RequestMapping(value="/addProd" , method = RequestMethod.POST)
	public String addProd(Model model, @ModelAttribute(ProductDto.dtoName) ProductDto dto) {
		
		productManagerService.paperPage(model,dto);
		
		return "admin/productManager/addProd";
	}
	
	
	@RequestMapping(value = "/ajaxProductGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxProductGridList(ProductManagerBean formBean, HttpServletRequest request) {

		return productManagerService.productGridList(formBean);
	}
	
	
	@RequestMapping(value = "/ajaxGetAllProdClass", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetAllProdClass(ProductClassDto dto, HttpServletRequest request) {
		return AjaxUtil.success(productClassManagerService.ajaxGetAllProdClass(dto,"-->"));
	}
	
	@RequestMapping(value = "/ajaxRemove", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemove(ProductDto dto, HttpServletRequest request) {
		try {
			return AjaxUtil.success(productManagerService.ajaxRemove(dto));
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.REMOVE_FAIL.getDoc());
		}
	}
	
	
	@RequestMapping(value = "/ajaxSaveProduct", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveProduct(ProductDto dto, HttpServletRequest request) {
		AjaxReturnBean result =null;
		try {
			validateInternalSave(dto);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			result = productManagerService.ajaxSaveProduct(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(result);
	}

	private void validateInternalSave(ProductDto dto) {
		
		setErrorBeans(productManagerService.validateInternalSave(dto));
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST , produces = "application/json;charset=utf-8")
	public @ResponseBody String upload(@RequestParam(value = "asyncFiles", required = false) MultipartFile asyncFiles,ProductPicDto productPicDto) {
		AjaxReturnBean result =null;
		try {
			uploadFile(asyncFiles, productPicDto);
			result = productManagerService.saveProductPic(productPicDto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.UPLAOD_FAIL.getDoc());
		}
		return AjaxUtil.success(result);
	}
	

	private void uploadFile(MultipartFile asyncFiles, ProductPicDto dto) throws Exception {
		if(asyncFiles != null) {
			if(StringUtil.isBlank(dto.getProNo())) {
				throw new Exception("ProNo is Blank");
			}
			productManagerService.uploadFile(asyncFiles,dto);
		}		
	}
	
	@RequestMapping(value = "/ajaxGetProductPic/{value}", method = RequestMethod.GET)
	public void ajaxGetProductPic(HttpServletResponse response,@PathVariable String value){
		try {
	        productManagerService.getProductPic(response,value);
	    } catch (Exception e) {
	    	handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
	    }
	}
	
	@RequestMapping(value = "/ajaxGetAllProdPic", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetAllProdPic(ProductDto dto, HttpServletRequest request) {
		return AjaxUtil.success(productManagerService.ajaxGetAllProdPic(dto));
	}
	
	@RequestMapping(value = "/ajaxRemovePic", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemovePic(ProductPicDto dto, HttpServletRequest request) {
		try {
			return AjaxUtil.success(productManagerService.ajaxRemovePic(dto));
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.REMOVE_FAIL.getDoc());
		}
	}
	
	
	@RequestMapping(value = "/ajaxSetMainPic", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSetMainPic(ProductPicDto dto, HttpServletRequest request) {
		try {
			return AjaxUtil.success(productManagerService.ajaxSetMainPic(dto,getLoginUser()));
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.APPLY_FAIL.getDoc());
		}
	}
	
	
}
