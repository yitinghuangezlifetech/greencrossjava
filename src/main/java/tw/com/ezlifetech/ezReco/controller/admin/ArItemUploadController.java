package tw.com.ezlifetech.ezReco.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.ArItemUploadBean;
import tw.com.ezlifetech.ezReco.bean.FunctionManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.ArItemHtDto;
import tw.com.ezlifetech.ezReco.dto.ProductSaleDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.ArItemUploadService;
import tw.com.ezlifetech.ezReco.service.ProductManagerService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Controller
@RequestMapping("/admin/arItemUpload")
public class ArItemUploadController extends GenericController {
	private static final String JAVA_NAME="VrItemUploadController";
	private static Logger logger = LoggerFactory.getLogger(ArItemUploadController.class);
	
	
	
	
	@Autowired
	private ArItemUploadService arItemUploadService;
	
	@Autowired
	private ProductManagerService productManagerService;
	
	
	@RequestMapping("/list")
	public String list(Model model) {
		
		arItemUploadService.paper(model);
		
		return "admin/arItemUpload/list"; 
		                             
	}
	
	@RequestMapping(value = "/ajaxArGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxArGridList(ArItemUploadBean formBean, HttpServletRequest request) {

		formBean.setOwnerId(StringUtil.isBlank(getLoginUser().getParentUserId())?getLoginUser().getId():getLoginUser().getParentUserId());
		
		return arItemUploadService.arGridList(formBean);
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/ajaxFastArHtDlSave", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxFastArHtDlSave(ArItemUploadBean bean, HttpServletRequest request) {
		AjaxReturnBean result = null;
		try {
			validateInternalFastArHtDlSave(bean);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			// 新增
			if(StringUtils.isBlank(bean.getHtId())) {
				result = arItemUploadService.ajaxFastArHtDlSave(bean,getLoginUser());
				// 如果有商品ID 則作商品與AR關聯
				if(!StringUtils.isBlank(bean.getProNo())) {
					productManagerService.saveRefProdAr(bean.getProNo(), result.getHtId());
				}
			}
			// 修改
			else {
				result = arItemUploadService.ajaxFastArHtDlUpdate(bean,getLoginUser());
			}
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc() + e.getMessage());
		}
		return AjaxUtil.success(result);
	}

	@RequestMapping(value = "/ajaxGetArHtDlsInfo", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetArHtDlsInfo(ArItemHtDto dto, HttpServletRequest request) {
		AjaxReturnBean result = null;
		try {
			
				
			result = arItemUploadService.ajaxGetArHtDlsInfo(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(result);
	}

	
	
	
	
	
	
	
	
	private void validateInternalFastArHtDlSave(ArItemUploadBean bean) {
		// TODO Auto-generated method stub
		setErrorBeans(arItemUploadService.validateInternalFastArHtDlSave(bean));
	}





	
	@RequestMapping(value = "/ajaxUploadFile", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler( @RequestParam("files") MultipartFile file) {
		AjaxReturnBean ajaxReturnBean =null;
		
		try {
			ajaxReturnBean = arItemUploadService.ajaxUploadFile(file);
			
		} catch (Exception e) {
			
			// 例外狀況
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.UPLAOD_FAIL.getDoc());
		}
		
		return AjaxUtil.success(ajaxReturnBean);
	}
	
	@RequestMapping(value = "/ajaxArUploadFile", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public @ResponseBody String uploadArFileHandler( @RequestParam("arfiles") MultipartFile file) {
		AjaxReturnBean ajaxReturnBean =null;
		
		try {
			ajaxReturnBean = arItemUploadService.ajaxUploadFile(file);
			
		} catch (Exception e) {
			
			// 例外狀況
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.UPLAOD_FAIL.getDoc());
		}
		
		return AjaxUtil.success(ajaxReturnBean);
	}
	
	
	
	
	@RequestMapping(value = "/ajaxRemoveFile", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public @ResponseBody String removeFileHandler(ArItemUploadBean formBean ) {
		AjaxReturnBean ajaxReturnBean =null;
		System.out.println("formBean:"+formBean.getFileId());
		try {
			ajaxReturnBean = arItemUploadService.removeFileHandler(formBean);
		} catch (Exception e) {
			
			// 例外狀況
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.REMOVE_FAIL.getDoc());
		}
		
		return AjaxUtil.success(ajaxReturnBean);
	}
	
	
	@RequestMapping(value = "/ajaxRemoveHt", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public @ResponseBody String ajaxRemoveHt(ArItemUploadBean formBean ) {
		AjaxReturnBean ajaxReturnBean =null;
		System.out.println("formBean:"+formBean.getFileId());
		try {
			ajaxReturnBean = arItemUploadService.ajaxRemoveHt(formBean);
		} catch (Exception e) {
			
			// 例外狀況
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.REMOVE_FAIL_PLZ_WAIT.getDoc());
		}
		
		return AjaxUtil.success(ajaxReturnBean);
	}

}
