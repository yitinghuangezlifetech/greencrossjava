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

import tw.com.ezlifetech.ezReco.bean.ComProfHtBean;
import tw.com.ezlifetech.ezReco.bean.ContactUsQuestMgrBean;
import tw.com.ezlifetech.ezReco.bean.ProductManagerBean;
import tw.com.ezlifetech.ezReco.bean.StoreBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.ContactUsQuestMgrService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;

@Controller
@RequestMapping("/admin/contactUsQuestMgr")
public class ContactUsQuestMgrController extends GenericController{

	private static final String JAVA_NAME="ContactUsQuestMgrController";
	private static Logger logger = LoggerFactory.getLogger(ContactUsQuestMgrController.class);
	
	@Autowired
	private ContactUsQuestMgrService contactUsQuestMgrService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		
		return "admin/contactUsQuestMgr/list";
	}
	
	
	@RequestMapping(value = "/ajaxGetGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetGridList(ContactUsQuestMgrBean formBean, HttpServletRequest request) {

		return contactUsQuestMgrService.ajaxGetGridList(formBean);
	}
	
	@RequestMapping(value = "/ajaxGetQuest", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetQuest(ContactUsQuestMgrBean bean, HttpServletRequest request) {
		AjaxReturnBean result = null;
		try {
			
			
			
			result = contactUsQuestMgrService.ajaxGetQuest(bean);
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SEARCH_FAIL.getDoc());
		}

		return AjaxUtil.success(result);
	}
	
	
	@RequestMapping(value = "/ajaxChgQuestStatus", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxChgQuestStatus(ContactUsQuestMgrBean bean, HttpServletRequest request) {
		AjaxReturnBean result = null;
		try {
			
			
			
			result = contactUsQuestMgrService.ajaxChgQuestStatus(bean);
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SEARCH_FAIL.getDoc());
		}

		return AjaxUtil.success(result);
	}
	
}
