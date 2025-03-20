package tw.com.ezlifetech.ezReco.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.RefCompWarehouseDto;
import tw.com.ezlifetech.ezReco.dto.WareHouseProfDlDto;
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftDlDto;
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftHtDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.LogistcProfService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;

@Controller
@RequestMapping("/admin/logistcProf")
public class LogistcProfController  extends GenericController {
	private static final String JAVA_NAME="LogistcProfController";
	private static Logger logger = LoggerFactory.getLogger(LogistcProfController.class);

	@Autowired
	private LogistcProfService logistcProfService;
	
	
	@RequestMapping("/list")
	public String list(Model model) {
		
		
		logistcProfService.paperPage(model,getLoginUser());
		return "admin/logistcProf/list"; 
	}
	
	@RequestMapping(value = "/ajaxGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGridList(RefCompWarehouseDto dto, HttpServletRequest request) {

		return logistcProfService.ajaxGridList(dto,getLoginUser()); 
	}
	
	@RequestMapping(value = "/ajaxDlSelectList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxDlSelectList(WareHouseProfDlDto dto, HttpServletRequest request) {

		return logistcProfService.ajaxDlSelectList(dto,getLoginUser()); 
	}
	
	@RequestMapping(value = "/ajaxWcsSelectList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxWcsSelectList(RefCompWarehouseDto dto, HttpServletRequest request) {

		return logistcProfService.ajaxWcsSelectList(dto,getLoginUser()); 
	}
	
	
	@RequestMapping(value = "/ajaxSave", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSave(RefCompWarehouseDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalSave(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = logistcProfService.ajaxSave(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalSave(RefCompWarehouseDto dto) {
		setErrorBeans(logistcProfService.validateInternalSave(dto));
		
	}
	
	@RequestMapping(value = "/ajaxRemove", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemove(RefCompWarehouseDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalRemove(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = logistcProfService.ajaxRemove(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalRemove(RefCompWarehouseDto dto) {
		setErrorBeans(logistcProfService.validateInternalRemove(dto));
		
	}
	
	
	@RequestMapping(value = "/ajaxGetRefData", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetRefData(RefCompWarehouseDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalGetRefData(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = logistcProfService.ajaxGetRefData(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalGetRefData(RefCompWarehouseDto dto) {
		setErrorBeans(logistcProfService.validateInternalGetRefData(dto));
		
	}
	
	
}
