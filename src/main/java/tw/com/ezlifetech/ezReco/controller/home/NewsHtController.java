package tw.com.ezlifetech.ezReco.controller.home;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.ezlifetech.ezReco.bean.NewsHtBean;

import tw.com.ezlifetech.ezReco.common.controller.GenericController;


import tw.com.ezlifetech.ezReco.dto.UserDto;

import tw.com.ezlifetech.ezReco.service.NewsHtService;

import org.apache.commons.lang.StringEscapeUtils;

@Controller
@RequestMapping("/home/news")
public class NewsHtController extends GenericController {
	private static final String JAVA_NAME="NewsHtController";
	private static Logger logger = LoggerFactory.getLogger(NewsHtController.class);
	
	@Autowired
	private NewsHtService newsHtService;
	
	@RequestMapping("/newsList")
	public String newsList(Model model,NewsHtBean bean) {
		notShowHeaderSectionIndex();
		bean.setNewsType("N");
		
		newsHtService.paper(model, bean);
		
		return "home/news/newsList"; 
	}
	

	@RequestMapping("/activeList")
	public String activeList(Model model,NewsHtBean bean) {
		
		bean.setNewsType("A");
		
		newsHtService.paper(model, bean);
		
		return "home/news/activeList"; 
	}
	
	@RequestMapping(value = "/ajaxHtGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxHtGridList(NewsHtBean bean, HttpServletRequest request) {

		return newsHtService.ajaxHtGridList(bean,getLoginUser()); 
	}
	

	@RequestMapping("/editNews")
	public String editNews(Model model, NewsHtBean dto, UserDto loginUser, String id) throws Exception {
		
		newsHtService.paperEdit(model,dto,loginUser);
		String html = "";

		html = StringEscapeUtils.unescapeXml(dto.getHtml());
		dto.setHtml(html);
		if("N".equals(dto.getNewsType())) {
			return "home/news/newsContent"; 
		}
		else {
			return "home/news/activeContent"; 
		}
	}

	

}
