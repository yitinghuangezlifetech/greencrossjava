package tw.com.ezlifetech.ezReco.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tw.com.ezlifetech.ezReco.common.controller.GenericController;

@Controller
public class BulletinController extends GenericController{
	private static final String JAVA_NAME="BulletinController";
	private static Logger logger = LoggerFactory.getLogger(BulletinController.class);
	
	
	@RequestMapping("/bulletinPage")
	public ModelAndView bulletinPage() {
		String methodName="bulletinPage";
		ModelAndView mv = new ModelAndView("admin/bulletin/bulletinPage");

		return mv;
	}
}
