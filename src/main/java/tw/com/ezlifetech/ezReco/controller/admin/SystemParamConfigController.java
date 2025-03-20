package tw.com.ezlifetech.ezReco.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.RoleManagerBean;
import tw.com.ezlifetech.ezReco.bean.SystemParamConfigBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.BasicSysparamDlDto;
import tw.com.ezlifetech.ezReco.dto.BasicSysparamHtDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.SystemParamConfigService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;


@Controller
@RequestMapping("/admin/systemParamConfig")
public class SystemParamConfigController extends GenericController {
	private static final String JAVA_NAME="SystemParamConfigController";
	private static Logger logger = LoggerFactory.getLogger(SystemParamConfigController.class);

	@Autowired
	private SystemParamConfigService systemParamConfigService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		
		return "admin/systemParamConfig/list"; 
		                             
	}
	
	@RequestMapping("/detailList")
	public String detailList(Model model,@ModelAttribute(BasicSysparamHtDto.dtoName)BasicSysparamHtDto dto) {
		systemParamConfigService.paperDetailPage(dto);
		return "admin/systemParamConfig/detailList"; 
		                             
	}
	
	@RequestMapping(value = "/ajaxSystemParamHtGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSystemParamHtGridList(SystemParamConfigBean formBean, HttpServletRequest request) {

		return systemParamConfigService.systemParamHtGridList(formBean); 
	}
	
	@RequestMapping(value = "/ajaxSystemParamDlGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSystemParamDlGridList(BasicSysparamHtDto dto, HttpServletRequest request) {

		return systemParamConfigService.systemParamDlGridList(dto); 
	}
	
	
	@RequestMapping(value = "/ajaxSaveSystemParamHt", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveSystemParamHt(BasicSysparamHtDto dto, HttpServletRequest request) {
		AjaxReturnBean ajaxReturnBean =null;
		try {
			// 先驗證輸入資訊
			validateInternalSaveSystemParamHt(dto);
			// 檢查有無驗證錯誤
			if(hasError()){
				// 有則回傳 檢覈的錯誤訊息
				return AjaxUtil.validateFail(mesgToJSONString());
			}	
			// 無檢核錯誤 則執行存檔動作
			ajaxReturnBean = systemParamConfigService.ajaxSaveSystemParamHt(dto,getLoginUser());
		}catch (Exception e) {
			// 例外狀況
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalSaveSystemParamHt(BasicSysparamHtDto dto) {
		setErrorBeans(systemParamConfigService.validateInternalSaveSystemParamHt(dto));
	}
	
	@RequestMapping(value = "/ajaxGetSystemParamHtById", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetSystemParamHtById(BasicSysparamHtDto dto, HttpServletRequest request) {

		JsonObject result = null;
		try {
			result = systemParamConfigService.ajaxGetSystemParamHtById(dto);
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(result);
	}
	
	@RequestMapping(value = "/ajaxRemoveSystemParamHt", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemoveSystemParamHt(BasicSysparamHtDto dto, HttpServletRequest request) {
		AjaxReturnBean ajaxReturnBean =null;
		try {
			// 先驗證輸入資訊
			validateInternalRemoveSystemParamHt(dto);
			// 檢查有無驗證錯誤
			if(hasError()){
				// 有則回傳 檢覈的錯誤訊息
				return AjaxUtil.validateFail(mesgToJSONString());
			}	
			// 無檢核錯誤 則執行存檔動作
			ajaxReturnBean = systemParamConfigService.ajaxRemoveSystemParamHt(dto,getLoginUser());
		}catch (Exception e) {
			// 例外狀況
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalRemoveSystemParamHt(BasicSysparamHtDto dto) {
		setErrorBeans(systemParamConfigService.validateInternalRemoveSystemParamHt(dto));
	}
	
	
	
	@RequestMapping(value = "/ajaxSaveSystemParamDl", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveSystemParamDl(BasicSysparamDlDto dto, HttpServletRequest request) {
		AjaxReturnBean ajaxReturnBean =null;
		try {
			// 先驗證輸入資訊
			validateInternalSaveSystemParamDl(dto);
			// 檢查有無驗證錯誤
			if(hasError()){
				// 有則回傳 檢覈的錯誤訊息
				return AjaxUtil.validateFail(mesgToJSONString());
			}	
			// 無檢核錯誤 則執行存檔動作
			ajaxReturnBean = systemParamConfigService.ajaxSaveSystemParamDl(dto,getLoginUser());
		}catch (Exception e) {
			// 例外狀況
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalSaveSystemParamDl(BasicSysparamDlDto dto) {
		setErrorBeans(systemParamConfigService.validateInternalSaveSystemParamDl(dto));
	}
	
	
	@RequestMapping(value = "/ajaxGetSystemParamDlById", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetSystemParamDlById(BasicSysparamDlDto dto, HttpServletRequest request) {

		JsonObject result = null;
		try {
			result = systemParamConfigService.ajaxGetSystemParamDlById(dto);
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(result);
	}
	
	@RequestMapping(value = "/ajaxRemoveSystemParamDl", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemoveSystemParamDl(BasicSysparamDlDto dto, HttpServletRequest request) {
		AjaxReturnBean ajaxReturnBean =null;
		try {
			// 先驗證輸入資訊
			validateInternalRemoveSystemParamDl(dto);
			// 檢查有無驗證錯誤
			if(hasError()){
				// 有則回傳 檢覈的錯誤訊息
				return AjaxUtil.validateFail(mesgToJSONString());
			}	
			// 無檢核錯誤 則執行存檔動作
			ajaxReturnBean = systemParamConfigService.ajaxRemoveSystemParamDl(dto,getLoginUser());
		}catch (Exception e) {
			// 例外狀況
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalRemoveSystemParamDl(BasicSysparamDlDto dto) {
		setErrorBeans(systemParamConfigService.validateInternalRemoveSystemParamDl(dto));
	}
	
}
