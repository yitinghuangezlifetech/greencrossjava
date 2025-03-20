package tw.com.ezlifetech.ezReco.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import tw.com.ezlifetech.ezReco.bean.GoReportBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;


/**
 * 出勤報表查詢
 * @author 87Leon
 */
@Controller
@RequestMapping("/admin/goReport")
public class GoReportController extends GenericController{

	private static final String JAVA_NAME="GoReportController";
	private static Logger logger = LoggerFactory.getLogger(GoReportController.class);
	
	@RequestMapping("/list")
	public String goReportList(Model model, @ModelAttribute(GoReportBean.beanFormName) GoReportBean formBean) {
		
		return "admin/goReport/list";
	}
}
