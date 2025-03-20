package tw.com.ezlifetech.ezReco.controller.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.service.AboutUsService;

@Controller
@RequestMapping("/home/aboutus")
public class AboutUsController extends GenericController {
	private static final String JAVA_NAME="GoController";
	private static Logger logger = LoggerFactory.getLogger(AboutUsController.class);
	
	@Autowired
	private AboutUsService aboutUsService;
	
	@RequestMapping("/list")
	public String list(Model model) {

		notShowHeaderSectionIndex();
		
		aboutUsService.paperPage(model,getLanguage());
		
		return "home/aboutus/list";
	}
	

}
