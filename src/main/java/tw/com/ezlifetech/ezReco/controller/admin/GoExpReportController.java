package tw.com.ezlifetech.ezReco.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import tw.com.ezlifetech.ezReco.bean.GoExpReportBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;

/**
 * 結帳報表查詢
 * @author 87Leon
 */
@Controller
@RequestMapping("/admin/goExpReport")
public class GoExpReportController extends GenericController{

	private static final String JAVA_NAME="GoExpReportController";
	private static Logger logger = LoggerFactory.getLogger(GoExpReportController.class);
	
	@RequestMapping("/list")
	public String goExpReportList(Model model, @ModelAttribute(GoExpReportBean.beanFormName) GoExpReportBean formBean) {
		
		return "admin/goExpReport/list";
	}
}

