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

import tw.com.ezlifetech.ezReco.bean.ProductClassManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.BasicSysparamHtDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.dto.WareHouseProfDlDto;
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftDlDto;
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftHtDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.WorkClassShiftsConfigService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;

@Controller
@RequestMapping("/admin/workClassShiftsConfig")
public class WorkClassShiftsConfigController extends GenericController {
	private static final String JAVA_NAME="WorkClassShiftsConfigController";
	private static Logger logger = LoggerFactory.getLogger(WorkClassShiftsConfigController.class);
	
	
	@Autowired
	private WorkClassShiftsConfigService workClassShiftsConfigService;
	
	
	@RequestMapping("/list")
	public String list(Model model) {
		
		workClassShiftsConfigService.paper(model);
		
		return "admin/workClassShiftsConfig/list"; 
	}
	
	@RequestMapping("/edit")
	public String edit(Model model,@ModelAttribute(WorkClassShiftHtDto.dtoName) WorkClassShiftHtDto dto) throws Exception {
		
		workClassShiftsConfigService.paperEdit(model,dto,getLoginUser());
		
		return "admin/workClassShiftsConfig/edit"; 
	}

	
	@RequestMapping(value = "/ajaxSaveHt", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveHt(WorkClassShiftHtDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalSaveHt(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = workClassShiftsConfigService.ajaxSaveHt(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalSaveHt(WorkClassShiftHtDto dto) throws Exception {
		// TODO Auto-generated method stub
		setErrorBeans(workClassShiftsConfigService.validateInternalSaveHt(dto));
	}
	
	@RequestMapping(value = "/ajaxRemoveHt", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemoveHt(WorkClassShiftHtDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalRemoveHt(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = workClassShiftsConfigService.ajaxRemoveHt(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalRemoveHt(WorkClassShiftHtDto dto) {
		// TODO Auto-generated method stub
		setErrorBeans(workClassShiftsConfigService.validateInternalRemoveHt(dto));
	}
	
	@RequestMapping(value = "/ajaxHtGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxHtGridList(WorkClassShiftHtDto dto, HttpServletRequest request) {

		return workClassShiftsConfigService.ajaxHtGridList(dto,getLoginUser()); 
	}
	
	
	
	@RequestMapping(value = "/ajaxSaveDl", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveDl(WorkClassShiftDlDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalSaveDl(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = workClassShiftsConfigService.ajaxSaveDl(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalSaveDl(WorkClassShiftDlDto dto) {
		// TODO Auto-generated method stub
		setErrorBeans(workClassShiftsConfigService.validateInternalSaveDl(dto));
	}
	
	
	@RequestMapping(value = "/ajaxGetDl", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetDl(WorkClassShiftDlDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalGetDl(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = workClassShiftsConfigService.ajaxGetDl(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalGetDl(WorkClassShiftDlDto dto) {
		// TODO Auto-generated method stub
		setErrorBeans(workClassShiftsConfigService.validateInternalGetDl(dto));
	}
	
	
	@RequestMapping(value = "/ajaxTimes2PageCount", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxTimes2PageCount(WorkClassShiftDlDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
//			validateInternalGetDl(dto);
//			if(hasError()){
//				return AjaxUtil.validateFail(mesgToJSONString());
//			}
			ajaxReturnBean = workClassShiftsConfigService.ajaxTimes2PageCount(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}
	
	
	
	@RequestMapping(value = "/ajaxDlGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxDlGridList(WorkClassShiftHtDto dto, HttpServletRequest request) {

		return workClassShiftsConfigService.ajaxDlGridList(dto,getLoginUser()); 
	}
	
	@RequestMapping(value = "/ajaxRemoveDl", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemoveDl(WorkClassShiftDlDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalRemoveDl(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = workClassShiftsConfigService.ajaxRemoveDl(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalRemoveDl(WorkClassShiftDlDto dto) {
		// TODO Auto-generated method stub
		setErrorBeans(workClassShiftsConfigService.validateInternalRemoveDl(dto));
	}
	

	@RequestMapping(value = "/ajaxSetMainDl", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSetMainDl(WorkClassShiftDlDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalSetMainDl(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = workClassShiftsConfigService.ajaxSetMainDl(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalSetMainDl(WorkClassShiftDlDto dto) {
		// TODO Auto-generated method stub
		setErrorBeans(workClassShiftsConfigService.validateInternalSetMainDl(dto));
	}
	
	@RequestMapping(value = "/ajaxDisSetMainDl", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxDisSetMainDl(WorkClassShiftDlDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalDisSetMainDl(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = workClassShiftsConfigService.ajaxDisSetMainDl(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalDisSetMainDl(WorkClassShiftDlDto dto) {
		// TODO Auto-generated method stub
		setErrorBeans(workClassShiftsConfigService.validateInternalDisSetMainDl(dto));
	}
	
	
	@RequestMapping(value = "/ajaxDlSelectList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxDlSelectList(WareHouseProfDlDto dto, HttpServletRequest request) {

		return workClassShiftsConfigService.ajaxDlSelectList(dto,getLoginUser()); 
	}

}
