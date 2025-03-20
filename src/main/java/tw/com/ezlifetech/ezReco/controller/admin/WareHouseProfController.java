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
import tw.com.ezlifetech.ezReco.dto.WareHouseProfDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.WareHouseProfService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;

@Controller
@RequestMapping("/admin/wareHouseProf")
public class WareHouseProfController extends GenericController {
	private static final String JAVA_NAME="WareHouseProfController";
	private static Logger logger = LoggerFactory.getLogger(WareHouseProfController.class);
	
	
	@Autowired
	private WareHouseProfService wareHouseProfService;
	
	
	@RequestMapping("/list")
	public String list(Model model) throws Exception {
		wareHouseProfService.paper(model,getLoginUser());
		
		return "admin/wareHouseProf/list"; 
	}
	
	@RequestMapping("/edit")
	public String edit(Model model,@ModelAttribute(WareHouseProfDto.dtoName) WareHouseProfDto dto) throws Exception {
		getLoginUser();
		wareHouseProfService.paperEdit(model,dto);
		
		return "admin/wareHouseProf/list"; 
	}

	
	@RequestMapping(value = "/ajaxSaveHt", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveHt(WareHouseProfDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalSaveHt(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = wareHouseProfService.ajaxSaveHt(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalSaveHt(WareHouseProfDto dto) {
		// TODO Auto-generated method stub
		setErrorBeans(wareHouseProfService.validateInternalSaveHt(dto));
	}
	
	@RequestMapping(value = "/ajaxRemoveHt", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemoveHt(WareHouseProfDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalRemoveHt(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = wareHouseProfService.ajaxRemoveHt(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalRemoveHt(WareHouseProfDto dto) {
		// TODO Auto-generated method stub
		setErrorBeans(wareHouseProfService.validateInternalRemoveHt(dto));
	}
	
	@RequestMapping(value = "/ajaxHtGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxHtGridList(WareHouseProfDto dto, HttpServletRequest request) {

		return wareHouseProfService.ajaxHtGridList(dto,getLoginUser()); 
	}
	
	
	
	@RequestMapping(value = "/ajaxSaveDl", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveDl(WareHouseProfDlDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalSaveDl(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = wareHouseProfService.ajaxSaveDl(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalSaveDl(WareHouseProfDlDto dto) {
		// TODO Auto-generated method stub
		setErrorBeans(wareHouseProfService.validateInternalSaveDl(dto));
	}
	
	
	@RequestMapping(value = "/ajaxGetDl", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetDl(WareHouseProfDlDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalGetDl(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = wareHouseProfService.ajaxGetDl(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalGetDl(WareHouseProfDlDto dto) {
		// TODO Auto-generated method stub
		setErrorBeans(wareHouseProfService.validateInternalGetDl(dto));
	}
	
	
	
	@RequestMapping(value = "/ajaxDlGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxDlGridList(WareHouseProfDlDto dto, HttpServletRequest request) {

		return wareHouseProfService.ajaxDlGridList(dto,getLoginUser()); 
	}
	
	@RequestMapping(value = "/ajaxRemoveDl", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemoveDl(WareHouseProfDlDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalRemoveDl(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = wareHouseProfService.ajaxRemoveDl(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalRemoveDl(WareHouseProfDlDto dto) {
		// TODO Auto-generated method stub
		setErrorBeans(wareHouseProfService.validateInternalRemoveDl(dto));
	}
	

}
