package tw.com.ezlifetech.ezReco.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import tw.com.ezlifetech.ezReco.bean.GuardianBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.GuardianDto;
import tw.com.ezlifetech.ezReco.dto.GuardianPicDto;
import tw.com.ezlifetech.ezReco.dto.ProductDto;
import tw.com.ezlifetech.ezReco.dto.ProductPicDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.GuardianService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Controller
@RequestMapping("/admin/guardian")
public class GuardianAdminController extends GenericController {
	private static final String JAVA_NAME="GuardianAdminController";
	private static Logger logger = LoggerFactory.getLogger(GuardianAdminController.class);
	
	@Autowired
	private GuardianService guardianService;
	
	@RequestMapping("/guardianList")
	public String guardianList(Model model,GuardianBean bean) {
		
		//newsHtService.paper(model, bean);
		
		return "admin/guardian/guardianList"; 
	}
	

//	@RequestMapping("/activeList")
//	public String activeList(Model model,NewsHtBean bean) {
//		
//		//newsHtService.paper(model, bean);
//		
//		return "admin/news/activeList"; 
//	}
	
	@RequestMapping(value = "/ajaxHtGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxHtGridList(GuardianBean bean, HttpServletRequest request) {

		return guardianService.ajaxHtGridList(bean,getLoginUser()); 
	}
	
	@RequestMapping(value="/addGuardian" , method = RequestMethod.POST)
	public String addGuardian(Model model) {
		
		return "admin/guardian/addGuardian";
	}
	
//	@RequestMapping(value="/addActive" , method = RequestMethod.POST)
//	public String addActive(Model model) {
//		
//		return "admin/news/addActive";
//	}	
	@RequestMapping("/editGuardian")
	public String editGuardian(Model model, GuardianBean dto, UserDto loginUser) throws Exception {
		
		guardianService.paperEdit(model,dto,loginUser);
		
		return "admin/guardian/addGuardian"; 
	}

	
	@RequestMapping(value = "/ajaxSaveHt", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveHt(GuardianDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
//			validateInternalSaveHt(dto);
//			if(hasError()){
//				return AjaxUtil.validateFail(mesgToJSONString());
//			}
			ajaxReturnBean = guardianService.ajaxSaveHt(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}
	
	@RequestMapping(value = "/ajaxRemove", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemove(GuardianDto dto, HttpServletRequest request) {
		try {
			return AjaxUtil.success(guardianService.ajaxRemoveHt(dto));
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.REMOVE_FAIL.getDoc());
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST , produces = "application/json;charset=utf-8")
	public @ResponseBody String upload(@RequestParam(value = "asyncFiles", required = false) MultipartFile asyncFiles,GuardianPicDto guardianPicDto) {
		AjaxReturnBean result =null;
		try {
			uploadFile(asyncFiles, guardianPicDto);
			result = guardianService.saveGuardianPic(guardianPicDto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.UPLAOD_FAIL.getDoc());
		}
		return AjaxUtil.success(result);
	}
	
	private void uploadFile(MultipartFile asyncFiles, GuardianPicDto dto) throws Exception {
		if(asyncFiles != null) {
			if(StringUtil.isBlank(dto.getProNo())) {
				throw new Exception("ProNo is Blank");
			}
			guardianService.uploadFile(asyncFiles,dto);
		}		
	}
	
	@RequestMapping(value = "/ajaxGetAllProdPic", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetAllProdPic(GuardianDto dto, HttpServletRequest request) {
		return AjaxUtil.success(guardianService.ajaxGetAllGuardianPic(dto));
	}
	
	@RequestMapping(value = "/ajaxRemovePic", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemovePic(GuardianPicDto dto, HttpServletRequest request) {
		try {
			return AjaxUtil.success(guardianService.ajaxRemovePic(dto));
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.REMOVE_FAIL.getDoc());
		}
	}
	
	
	@RequestMapping(value = "/ajaxSetMainPic", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSetMainPic(GuardianPicDto dto, HttpServletRequest request) {
		try {
			return AjaxUtil.success(guardianService.ajaxSetMainPic(dto,getLoginUser()));
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.APPLY_FAIL.getDoc());
		}
	}
	
	@RequestMapping(value = "/ajaxGetGuardianPic/{value}", method = RequestMethod.GET)
	public void ajaxGetProductPic(HttpServletResponse response,@PathVariable String value){
		try {
	        guardianService.getGuardianPic(response,value);
	    } catch (Exception e) {
	    	handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
	    }
	}
}
