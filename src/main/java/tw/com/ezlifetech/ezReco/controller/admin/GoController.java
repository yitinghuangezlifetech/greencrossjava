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
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.ezlifetech.ezReco.bean.DispatcherProfileBean;
import tw.com.ezlifetech.ezReco.bean.GoBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.BasicSysparamHtDto;
import tw.com.ezlifetech.ezReco.dto.GoHtDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.GoService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;

@Controller
@RequestMapping("/admin/go")
public class GoController extends GenericController {
	private static final String JAVA_NAME="GoController";
	private static Logger logger = LoggerFactory.getLogger(GoController.class);
	
	
	@Autowired
	private GoService GoService;
	
	
	@RequestMapping("/list")
	public String list(Model model,GoBean bean) {
		
		GoService.paper(model, bean);
		
		return "admin/go/list"; 
	}
	
	
	@RequestMapping(value = "/ajaxHtGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxHtGridList(GoBean bean, HttpServletRequest request) {

		return GoService.ajaxHtGridList(bean,getLoginUser()); 
	}
	
	
	@RequestMapping("/edit")
	public String edit(Model model,@ModelAttribute(GoHtDto.dtoName) GoHtDto dto) throws Exception {
		
		//GoService.paperEdit(model,dto);
		
		return "admin/Go/edit"; 
	}

	
	@RequestMapping(value = "/ajaxSaveHt", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveHt(GoHtDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalSaveHt(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = GoService.ajaxSaveHt(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalSaveHt(GoHtDto dto) {
		// TODO Auto-generated method stub
		setErrorBeans(GoService.validateInternalSaveHt(dto));
	}
	
	@RequestMapping(value = "/ajaxRemoveHt", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemoveHt(GoHtDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalRemoveHt(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = GoService.ajaxRemoveHt(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}

	private void validateInternalRemoveHt(GoHtDto dto) {
		// TODO Auto-generated method stub
		setErrorBeans(GoService.validateInternalRemoveHt(dto));
	}

	
	@RequestMapping(value = "/ajaxGetComIdSelectListByWhId", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetComIdSelectListByWhId(GoBean bean, HttpServletRequest request) {

		return GoService.ajaxGetComIdSelectListByWhId(bean,getLoginUser()); 
	}
	
	
	@RequestMapping(value = "/ajaxGetWhIdSelectList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetWhIdSelectList(GoBean bean, HttpServletRequest request) {

		return GoService.ajaxGetWhIdSelectList(bean,getLoginUser()); 
	}
	
	@RequestMapping(value = "/ajaxGetDisIdSelectListByWhIdCompId", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetDisIdSelectListByWhIdCompId(GoBean bean, HttpServletRequest request) {

		return GoService.ajaxGetDisIdSelectListByWhIdCompId(bean,getLoginUser()); 
	}
	
	
	@RequestMapping(value = "/ajaxPunch", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxPunch(GoBean bean, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalPunch(bean);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = GoService.ajaxPunch(bean,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}
	
	private void validateInternalPunch(GoBean bean) {
		// TODO Auto-generated method stub
		setErrorBeans(GoService.validateInternalPunch(bean));
	}
	
	
	@RequestMapping(value = "/ajaxGetDisId" , method = RequestMethod.GET)
	public void ajaxGetDisId(HttpServletResponse response,@PathVariable String value){
		try {
			GoService.ajaxGetDispatcherPic(response,value);
	    } catch (Exception e) {
	    	handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
	    }
	}
	
	
	@RequestMapping(value = "/ajaxGetDispatcherPic/{value}", method = RequestMethod.GET)
	public void ajaxGetDispatcherPic(HttpServletResponse response,@PathVariable String value){
		try {
			GoService.ajaxGetDispatcherPic(response,value);
	    } catch (Exception e) {
	    	handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
	    }
	}
	
	
	
	@RequestMapping(value = "/ajaxBtnControl", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxBtnControl(GoHtDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
			validateInternalBtnControl(dto);
			if(hasError()){
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			ajaxReturnBean = GoService.ajaxBtnControl(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}
	
	private void validateInternalBtnControl(GoHtDto dto) {
		// TODO Auto-generated method stub
		setErrorBeans(GoService.validateInternalBtnControl(dto));
	}
	
}
