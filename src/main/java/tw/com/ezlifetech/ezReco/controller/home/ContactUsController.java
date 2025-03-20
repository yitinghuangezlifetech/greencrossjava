package tw.com.ezlifetech.ezReco.controller.home;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.ContactusBean;
import tw.com.ezlifetech.ezReco.bean.StoreBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.ContactUsService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;

@Controller
@RequestMapping("/home/contactus")
public class ContactUsController extends GenericController {
	private static final String JAVA_NAME="ContactUsController";
	private static Logger logger = LoggerFactory.getLogger(ContactUsController.class);
	
	@Autowired
	private ContactUsService contactUsService;
	
	@RequestMapping("/list")
	public String list(Model model) {

		notShowHeaderSectionIndex();
		
		
		return "home/contactus/list";
	}
	
	
	
	
	@RequestMapping(value = "/ajaxSendQuest", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSendQuest(ContactusBean bean, HttpServletRequest request) {
		AjaxReturnBean result = null;
		try {
			bean.setLanguage(getLanguage());
			validateInternalAjaxSendQuest(bean);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			
			result = contactUsService.ajaxSendQuest(bean);
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SEARCH_FAIL.getDoc());
		}

		return AjaxUtil.success(result);
	}




	private void validateInternalAjaxSendQuest(ContactusBean bean) {
		setErrorBeans(contactUsService.validateInternalAjaxSendQuest(bean));
		
	}
	

}
