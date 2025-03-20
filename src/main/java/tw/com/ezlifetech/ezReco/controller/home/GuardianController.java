package tw.com.ezlifetech.ezReco.controller.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.ezlifetech.ezReco.bean.GuardianBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.service.GuardianService;
import tw.com.ezlifetech.ezReco.service.ProductClassManagerService;


@Controller
@RequestMapping("/home/education")
public class GuardianController extends GenericController {
	private static final String JAVA_NAME="GuardianController";
	private static Logger logger = LoggerFactory.getLogger(GuardianController.class);
	
	@Autowired
	private GuardianService guardianService;
	
	@RequestMapping("/educationList")
	public String guardianList(Model model,GuardianBean bean) {
		notShowHeaderSectionIndex();
		bean.setNewsType("N");
		
		guardianService.paper(model, bean,getLanguage(),"2");
		
		return "home/education/educationList"; 
	}
	


	
	@RequestMapping(value = "/ajaxHtGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxHtGridList(GuardianBean bean, HttpServletRequest request) {

		return guardianService.ajaxHtGridList(bean,getLoginUser()); 
	}
	

	@RequestMapping("/editEducation")
	public String editEducation(Model model, GuardianBean dto, UserDto loginUser, String id) throws Exception {
		notShowHeaderSectionIndex();
		guardianService.paperEdit(model,dto,loginUser);
		String html = "";

		html = StringEscapeUtils.unescapeXml(dto.getHtml());
		dto.setHtml(html);
		if("N".equals(dto.getNewsType())) {
			return "home/education/educationContent"; 
		}
		else {
			return "home/education/educationContent"; 
		}
	}

	@RequestMapping(value = "/ajaxGetEducationPic/{value}", method = RequestMethod.GET)
	public void ajaxGetProductPic(HttpServletResponse response,@PathVariable String value){
		try {
	        guardianService.getGuardianPic(response,value);
	    } catch (Exception e) {
	    	handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
	    }
	}

}
