package tw.com.ezlifetech.ezReco.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tw.com.ezlifetech.ezReco.bean.HomeBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.service.HomeService;
import tw.com.ezlifetech.ezReco.service.LoginService;
import tw.com.ezlifetech.ezReco.service.ProductManagerService;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Controller
public class HomeController extends GenericController{
	private static final String JAVA_NAME="HomeController";
	private static Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private ProductManagerService productManagerService;
	
	@Autowired
	private HomeService homeService;
	
	@RequestMapping("/admin/home")
	public ModelAndView homeAdmin(HttpServletRequest request) {
		
 
		ModelAndView mv = new ModelAndView("home");
		request.getSession().setAttribute("funcId","");
		request.getSession().setAttribute("breadcrumb", "<li>首頁</li>");
		request.getSession().setAttribute("funcName", "首頁");
		return mv;
	}
	
	@RequestMapping("/")
	public String homePage(HttpServletRequest request,Model model) {
		
		
		
		//showHeaderSectionIndex();
		notShowHeaderSectionIndex();
		homeService.paperPage(model,getLanguage());
		return "helloworld";
	}
	
	@RequestMapping(value = "/home/ajaxChangeLocal", method = RequestMethod.POST)
	  public void ajaxChangeLocal(HttpServletRequest request,HttpServletResponse response,HomeBean formBean){
	        try {
	        	
	        	loginService.ajaxChangeLocal(request,response,formBean);
	           
	 
	        } catch (Exception e) {
	        	handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
	        }
	  }
	
	
	/**
	 * 跳轉功能列，為了取得功能麵包屑
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/redirectAction")
	public String redirectAction(HttpServletRequest request, @ModelAttribute(HomeBean.beanFormName) HomeBean formBean,@RequestParam String act,
			@RequestParam String funcId, @RequestParam(required=false) String funcName) {
		String methodName="redirectAction";
		logger.info("The Java "+JAVA_NAME+"'s method "+ methodName + " is entry");
		String breadcrumb=loginService.getBreadcrumbPath(StringUtil.getParameter(funcId));
		try {
			request.getSession().setAttribute("breadcrumb", breadcrumb);
			request.getSession().setAttribute("funcId", StringUtil.getParameter(funcId));
			//request.getSession().setAttribute("parentFuncId", funcId);
			request.getSession().setAttribute("funcName", java.net.URLDecoder.decode(StringUtil.getParameter(funcName), "UTF-8"));
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			try {
				MethodUtils.invokeMethod(e, "printStackTrace");
			} catch (Exception ex) {
				handleException(ex,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+ex.getMessage());
			}
		}
		
		return "redirect:"+act;
	}
	
	@RequestMapping(value = "/ajaxGetProductPic/{value}", method = RequestMethod.GET)
	  public void ajaxGetProductPic(HttpServletResponse response,@PathVariable String value){
	        try {
	        	
	        	productManagerService.getProductPic(response,value);
	           
	 
	        } catch (Exception e) {
	        	handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
	        }
	  }
	
	@RequestMapping(value = "/ajaxGetProductPic", method = RequestMethod.GET)
	  public void ajaxGetProductPic(HttpServletResponse response){
	        try {
	        	
	        	productManagerService.getProductPic(response,"");
	           
	 
	        } catch (Exception e) {
	        	handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
	        }
	  }
}
