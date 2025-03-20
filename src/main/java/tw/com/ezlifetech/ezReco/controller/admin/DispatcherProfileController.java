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

import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.DispatcherProfileBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.DispatcherProfileDto;
import tw.com.ezlifetech.ezReco.dto.ProductDto;
import tw.com.ezlifetech.ezReco.dto.ProductPicDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.DispatcherProfileService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Controller
@RequestMapping("/admin/dispatcherProfile")
public class DispatcherProfileController extends GenericController{
	
	@Autowired
	private DispatcherProfileService dispatcherProfileService;
	private static final String JAVA_NAME="DispatcherProfileController";
	private static Logger logger = LoggerFactory.getLogger(DispatcherProfileController.class);
	
	@RequestMapping("/list")
	public String dispatcherProfileList(Model model, @ModelAttribute(DispatcherProfileBean.beanFormName) DispatcherProfileBean formBean) {
		
		return "admin/dispatcherProfile/list";
	}
	
	@RequestMapping(value="/addDispatcherProfile" , method = RequestMethod.POST)
	public String addDispatcherProfile(Model model, @ModelAttribute(DispatcherProfileDto.dtoName) DispatcherProfileDto dispatcherProfileDto) {
		
		dispatcherProfileService.paperPage(model,dispatcherProfileDto);
		
		return "admin/dispatcherProfile/addDispatcherProfile";
	}
	
	
	@RequestMapping(value = "/ajaxDispatcherProfileGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxDispatcherProfileGridList(DispatcherProfileBean formBean, HttpServletRequest request) {

		return dispatcherProfileService.dispatcherProfileGridList(formBean);
	}
	
	@RequestMapping(value = "/ajaxSaveDispatcherProfile", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveDispatcherProfile(DispatcherProfileDto dto, HttpServletRequest request) {
		AjaxReturnBean result =null;
		try {
			validateInternalSave(dto);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			result = dispatcherProfileService.ajaxSaveDispatcherProfile(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(result);
	}

	private void validateInternalSave(DispatcherProfileDto dto) {
		setErrorBeans(dispatcherProfileService.validateInternalSave(dto));
		
	}

	@RequestMapping(value = "/ajaxRemove", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemove(DispatcherProfileDto dto, HttpServletRequest request) {
		try {
			return AjaxUtil.success(dispatcherProfileService.ajaxRemove(dto));
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.REMOVE_FAIL.getDoc());
		}
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST , produces = "application/json;charset=utf-8")
	public @ResponseBody String upload(@RequestParam(value = "files", required = false) MultipartFile files,DispatcherProfileDto dispatcherProfileDto) {
		AjaxReturnBean bean = new AjaxReturnBean();
		try {
			DispatcherProfileDto dto = uploadFile(files, dispatcherProfileDto);
			DispatcherProfileDto newDispatcherProfileDto  = dispatcherProfileService.updateDispatcherPhotoPath(dto,getLoginUser());
			JsonObject jo = new JsonObject();
			jo.addProperty("photoPath", newDispatcherProfileDto.getPhotoPath());
			bean.setMessage(AjaxMesgs.UPLAOD_SUCCESSFUL.getDoc());
			bean.setValue(jo);
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.UPLAOD_FAIL.getDoc());
		}
		return AjaxUtil.success(bean);
	}
	
	private DispatcherProfileDto uploadFile(MultipartFile files, DispatcherProfileDto dispatcherProfileDto) throws Exception {
		DispatcherProfileDto dto = null;
		if(files != null) {
//			if(StringUtil.isBlank(dto.getProNo())) {
//				throw new Exception("ProNo is Blank");
//			}
			dto = dispatcherProfileService.uploadFile(files,dispatcherProfileDto);
		}		
		return dto;
	}
	
	@RequestMapping(value = "/ajaxGetDisPatcherPic/{id}", method = RequestMethod.GET)
	public void ajaxGetProductPic(HttpServletResponse response,@PathVariable String id){
		try {
			dispatcherProfileService.getDispatcherPic(response,id);
	    } catch (Exception e) {
	    	handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
	    }
	}
//	@RequestMapping(value = "/ajaxGetProductPic/{value}", method = RequestMethod.GET)
//	public void ajaxGetProductPic(HttpServletResponse response,@PathVariable String value){
//		try {
//	        productManagerService.getProductPic(response,value);
//	    } catch (Exception e) {
//	    	handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
//	    }
//	}
	
	
//	@RequestMapping(value = "/ajaxRemovePic", produces = "application/json;charset=utf-8")
//	public @ResponseBody String ajaxRemovePic(ProductPicDto dto, HttpServletRequest request) {
//		try {
//			return AjaxUtil.success(productManagerService.ajaxRemovePic(dto));
//		} catch (Exception e) {
//			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
//			return AjaxUtil.fail(AjaxMesgs.REMOVE_FAIL.getDoc());
//		}
//	}
	
//	@RequestMapping(value = "/ajaxUploadFile", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
//	public @ResponseBody String uploadFileHandler( @RequestParam("files") MultipartFile file) {
//		AjaxReturnBean ajaxReturnBean =null;
//		
//		try {
//			ajaxReturnBean = arItemUploadService.ajaxUploadFile(file);
//			
//		} catch (Exception e) {
//			
//			// 例外狀況
//			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
//			return AjaxUtil.fail(AjaxMesgs.UPLAOD_FAIL.getDoc());
//		}
//		
//		return AjaxUtil.success(ajaxReturnBean);
//	}
}

