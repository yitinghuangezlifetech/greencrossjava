package tw.com.ezlifetech.ezReco.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.ezlifetech.ezReco.bean.ProductQuestMgrBean;
import tw.com.ezlifetech.ezReco.bean.ProductQuestMgrBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.ContactUsQuestMgrService;
import tw.com.ezlifetech.ezReco.service.ProductQuestMgrService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;

@Controller
@RequestMapping("/admin/productQuestMgr")
public class ProductQuestMgrController extends GenericController{
	private static final String JAVA_NAME="ProductQuestMgrController";
	private static Logger logger = LoggerFactory.getLogger(ProductQuestMgrController.class);
	
	@Autowired
	private ProductQuestMgrService productQuestMgrService;
	
	
	@RequestMapping("/list")
	public String list(Model model) {
		
		return "admin/productQuestMgr/list";
	}
	
	@RequestMapping(value = "/ajaxGetGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetGridList(ProductQuestMgrBean formBean, HttpServletRequest request) {

		return productQuestMgrService.ajaxGetGridList(formBean);
	}
	
	@RequestMapping(value = "/ajaxGetQuest", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetQuest(ProductQuestMgrBean bean, HttpServletRequest request) {
		AjaxReturnBean result = null;
		try {
			
			
			
			result = productQuestMgrService.ajaxGetQuest(bean);
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SEARCH_FAIL.getDoc());
		}

		return AjaxUtil.success(result);
	}
	
	
	@RequestMapping(value = "/ajaxChgQuestStatus", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxChgQuestStatus(ProductQuestMgrBean bean, HttpServletRequest request) {
		AjaxReturnBean result = null;
		try {
			
			
			
			result = productQuestMgrService.ajaxChgQuestStatus(bean);
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SEARCH_FAIL.getDoc());
		}

		return AjaxUtil.success(result);
	}
}
