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

import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftDlDto;
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftHtDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.WorkClassShiftsConfigAdminService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;

@Controller
@RequestMapping("/admin/workClassShiftsConfigAdmin")
public class WorkClassShiftsConfigAdminController extends GenericController {
	private static final String JAVA_NAME="WorkClassShiftsConfigAdminController";
	private static Logger logger = LoggerFactory.getLogger(WorkClassShiftsConfigAdminController.class);
	

	@Autowired
	private WorkClassShiftsConfigAdminService workClassShiftsConfigAdminService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		
		workClassShiftsConfigAdminService.paper(model);
		
		return "admin/workClassShiftsConfigAdmin/list"; 
	}
	
	
	
	@RequestMapping("/edit")
	public String edit(Model model,@ModelAttribute(WorkClassShiftHtDto.dtoName) WorkClassShiftHtDto dto) throws Exception {
		
		workClassShiftsConfigAdminService.paperEdit(model,dto,getLoginUser());
		
		return "admin/workClassShiftsConfigAdmin/edit"; 
	}
	
	
	@RequestMapping(value = "/ajaxHtGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxHtGridList(WorkClassShiftHtDto dto, HttpServletRequest request) {

		return workClassShiftsConfigAdminService.ajaxHtGridList(dto,getLoginUser()); 
	}
	
	
	@RequestMapping(value = "/ajaxGetDl", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetDl(WorkClassShiftDlDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalGetDl(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = workClassShiftsConfigAdminService.ajaxGetDl(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalGetDl(WorkClassShiftDlDto dto) {
		// TODO Auto-generated method stub
		setErrorBeans(workClassShiftsConfigAdminService.validateInternalGetDl(dto));
	}
	
	
	
	@RequestMapping(value = "/ajaxDlGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxDlGridList(WorkClassShiftHtDto dto, HttpServletRequest request) {

		return workClassShiftsConfigAdminService.ajaxDlGridList(dto,getLoginUser()); 
	}
	
	
	@RequestMapping(value = "/ajaxAgree", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxAgree(WorkClassShiftHtDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalApply(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = workClassShiftsConfigAdminService.ajaxAgree(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.REVIEW_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}
	
	@RequestMapping(value = "/ajaxReturn", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxReturn(WorkClassShiftHtDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalApply(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = workClassShiftsConfigAdminService.ajaxReturn(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.REVIEW_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}

	@RequestMapping(value = "/ajaxRef", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRef(WorkClassShiftHtDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalApply(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = workClassShiftsConfigAdminService.ajaxRef(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.REVIEW_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}
	private void validateInternalApply(WorkClassShiftHtDto dto) {
		// TODO Auto-generated method stub
		setErrorBeans(workClassShiftsConfigAdminService.validateInternalApply(dto));
	}
}
