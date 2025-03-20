package tw.com.ezlifetech.ezReco.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.ezlifetech.ezReco.bean.GoBean;
import tw.com.ezlifetech.ezReco.bean.NewsHtBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.GoHtDto;
import tw.com.ezlifetech.ezReco.dto.NewsHtDto;
import tw.com.ezlifetech.ezReco.dto.ProductDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.NewsHtService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;

@Controller
@RequestMapping("/admin/news")
public class NewsHtAdminController extends GenericController {
	private static final String JAVA_NAME="NewsHtAdminController";
	private static Logger logger = LoggerFactory.getLogger(NewsHtAdminController.class);
	
	@Autowired
	private NewsHtService newsHtService;
	
	@RequestMapping("/newsList")
	public String newsList(Model model,NewsHtBean bean) {
		
		//newsHtService.paper(model, bean);
		
		return "admin/news/newsList"; 
	}
	

	@RequestMapping("/activeList")
	public String activeList(Model model,NewsHtBean bean) {
		
		//newsHtService.paper(model, bean);
		
		return "admin/news/activeList"; 
	}
	
	@RequestMapping(value = "/ajaxHtGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxHtGridList(NewsHtBean bean, HttpServletRequest request) {

		return newsHtService.ajaxHtGridList(bean,getLoginUser()); 
	}
	
	@RequestMapping(value="/addNews" , method = RequestMethod.POST)
	public String addNews(Model model) {
		
		return "admin/news/addNews";
	}
	
	@RequestMapping(value="/addActive" , method = RequestMethod.POST)
	public String addActive(Model model) {
		
		return "admin/news/addActive";
	}	
	@RequestMapping("/editNews")
	public String editNews(Model model, NewsHtBean dto, UserDto loginUser) throws Exception {
		
		newsHtService.paperEdit(model,dto,loginUser);
		
		return "admin/news/addNews"; 
	}

	
	@RequestMapping(value = "/ajaxSaveHt", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveHt(NewsHtDto dto, HttpServletRequest request) {

		AjaxReturnBean ajaxReturnBean =null;
		try {
//			validateInternalSaveHt(dto);
//			if(hasError()){
//				return AjaxUtil.validateFail(mesgToJSONString());
//			}
			ajaxReturnBean = newsHtService.ajaxSaveHt(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(ajaxReturnBean);
	}
	
	@RequestMapping(value = "/ajaxRemove", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemove(NewsHtDto dto, HttpServletRequest request) {
		try {
			return AjaxUtil.success(newsHtService.ajaxRemoveHt(dto));
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.REMOVE_FAIL.getDoc());
		}
	}

}
