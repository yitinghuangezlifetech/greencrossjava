package tw.com.ezlifetech.ezReco.interceptor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import tw.com.ezlifetech.ezReco.dto.ProfFunctionDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.model.LoggSystem;
import tw.com.ezlifetech.ezReco.model.ProductClass;
import tw.com.ezlifetech.ezReco.model.User;
import tw.com.ezlifetech.ezReco.model.WebPages;
import tw.com.ezlifetech.ezReco.respository.LoggSystemRepository;
import tw.com.ezlifetech.ezReco.respository.ProductClassRespository;
import tw.com.ezlifetech.ezReco.respository.WebPagesRespository;
import tw.com.ezlifetech.ezReco.service.WebPagesService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;

/**
 * Title: SecurityInterceptor<br>
 * Description: 檢查使用者是否有登入。<br>
 * Company: Tradevan Co.<br>
 */
public class SecurityInterceptor implements HandlerInterceptor {

	private static final long serialVersionUID = 1L;

	private static final String[] LOGIN_ESCAPE = {};
	private static final String PAGE_TIMEOUT = "timeout";

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private LoggSystemRepository loggSystemRepository;

	
	@Autowired
	private WebPagesRespository webPagesRespository;
	
	@Autowired
	private ProductClassRespository productClassRespository;
	
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String loginUrl = "/";

		if(request.getServletPath().startsWith("/home")) {
			String seoMeta = (String)(request.getSession().getAttribute("SEOmetaData")==null?"":request.getSession().getAttribute("SEOmetaData"));
			if("".equals(seoMeta)) {
				WebPages  webPages = webPagesRespository.getEntityByJPQL("SELECT w FROM WebPages w WHERE w.pageType = ?", "seo");
				request.getSession().setAttribute("SEOmetaData", StringEscapeUtils.unescapeXml(webPages.getPageContent()));
			}
		}
		// 取得官網 產品介紹 清單
		List<ProductClass> pList = new ArrayList<ProductClass>();
		pList = productClassRespository.findEntityListByJPQL("SELECT p FROM ProductClass p WHERE p.classType=? AND p.classParentId=?","1", "000000");
		List<Map<String,Object>> menuProdList = new ArrayList<Map<String,Object>>();
		
		for(ProductClass p :pList) {
			Map<String,Object> m =new  HashMap<String,Object>();
			m.put("pClassId", p.getId());
			m.put("pClassName", (request.getSession().getAttribute("local")==null?"tw":(String)(request.getSession().getAttribute("local"))).equals("tw")? p.getClassName():(p.getClassNameEn()==null?"No classification":p.getClassNameEn()));
			menuProdList.add(m);
		}
		request.getSession().setAttribute("menuProdList", menuProdList);
		
		// 登入的使用者
		UserDto userDto = (UserDto) request.getSession().getAttribute("userProf");
		String sessionId = request.getSession().getId();
		request.getSession().setAttribute("randomId", DateUtil.getSystemDateTime());// 每次畫面重新載入時取得最新的css與js
		request.getSession().setAttribute("isHomeBannerShow", "Y");
		request.getSession().setAttribute("isHomefooterShow", "Y");
		request.getSession().setAttribute("isLogin", userDto==null?"N":"Y");
		// 可以加上判斷哪些關鍵字是不需要做登入檢查的，直接return true
		if (checkEscapeUrl(request.getServletPath())) {

			return true;
		}
		// logger.info("servletPath = " + request.getServletPath() + " Session Id = " +
		// sessionId);
		MethodUtils.invokeMethod(logger, "info",
				"servletPath = " + request.getServletPath() + " Session Id = " + sessionId);

		// 判斷是否已登入
		if (userDto != null) {
			
			// 檢查是否有該功能權限
			System.out.println("request.getServletPath():" + request.getServletPath());
			if (!checkRoleUserFunction(request.getServletPath(), userDto)) {
				if (checkIsAjax(request.getServletPath())) {
					response.setContentType("application/json");
					// Get the printwriter object from response to write the required json object to
					// the output stream
					PrintWriter out = response.getWriter();
					// Assuming your json object is **jsonObject**, perform the following, it will
					// return your json object
					out.print(AjaxUtil.notLogin());
					out.flush();
				} else {
					logger.info("not login ,return to login page");
					response.sendRedirect(request.getContextPath() + loginUrl);
				}
			}

			String userSessionId = userDto.getSessionId();
			setUserLogger(request, userDto);// 使用者操作記錄
			if (StringUtils.equals(sessionId, userSessionId)) {
				// request.getSession().setAttribute("randomId",
				// userSessionId);//每次重新登入可以取得最新的css與js
				// 檢查檢查資料輸入時與存檔時的userID 是否一致
				
				String checkSessionUserId = (request.getParameter("checkSessionUserId")==null ?null:request.getParameter("checkSessionUserId"));
				if(!StringUtil.isBlank(checkSessionUserId)) {
					
					if(!userDto.getId().equals(checkSessionUserId)) {
						
						
						if(checkIsAjax(request.getServletPath())) {
							response.setContentType("application/json");
							// Get the printwriter object from response to write the required json object to the output stream      
							PrintWriter out = response.getWriter();
							// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
							out.print(AjaxUtil.notLogin());
							out.flush();
						}else {
							logger.info("not login ,return to login page");
							response.sendRedirect(request.getContextPath() + loginUrl);
						}
						
						return false;
					}
				}
				return true;
			}
		}

		// 回登入頁
		if(checkIsAjax(request.getServletPath())) {
			response.setContentType("application/json");
			// Get the printwriter object from response to write the required json object to the output stream      
			PrintWriter out = response.getWriter();
			// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
			out.print(AjaxUtil.notLogin());
			out.flush();
		}else {
			logger.info("not login ,return to login page");
			response.sendRedirect(request.getContextPath() + loginUrl);
		}
		return false;

	}
	private boolean checkRoleUserFunction(String servletPath,UserDto userDto) {
		
    	System.out.println("checkRoleUserFunction-servletPath:"+servletPath);
    	
    	for(ProfFunctionDto pf : userDto.getFuncList()) {
    		System.out.println("checkRoleUserFunction-getCanUseUrl:"+pf.getCanUseUrl());
    		if(!StringUtil.isBlank(pf.getCanUseUrl())&&servletPath.startsWith(pf.getCanUseUrl())) {
    			return pf.isUserCanUse();
    		}
    	}
    	
    	
		return true;
	}
	private boolean checkIsAjax(String servletPath) {
    	boolean result=false;
    	if(servletPath.indexOf("ajax")>-1) {
    		result = true;
    	}
		return result;
	}
	/**
	 * 儲存使用者操作紀錄
	 * 
	 * @param request
	 * @param user
	 */
	private void setUserLogger(HttpServletRequest request, UserDto userDto) {
		String function = request.getRequestURI();
		if (!(function.startsWith("/websitesample/admin/home") || function.startsWith("/websitesample/admin/login")
				|| function.startsWith("/websitesample/securityImage")
				|| function.startsWith("/websitesample/admin/redirectAction")
				|| function.startsWith("/websitesample/css") || function.startsWith("/websitesample/js")
				|| function.startsWith("/websitesample/admin/ajax")
				|| function.startsWith("/websitesample/admin/uploadFiles"))) {
			Enumeration<String> e = request.getParameterNames();
			String argument = "";
			while (e.hasMoreElements()) {
				String param = e.nextElement();
				String value = request.getParameter(param);
				argument = argument + param + "=" + value + ",";
			}
			if (!argument.equals("")) {
				String now = DateUtil.getSystemDateTime();
				/*
				 * LoggSystem log = new LoggSystem(); log.setId(""+System.currentTimeMillis());
				 * log.setUserId(userDto.getUserId());
				 * log.setUserSessionId(userDto.getSessionId());
				 * log.setUserIp(userDto.getUserIp()); log.setFunctionCode(function);
				 * log.setArgumentValue(argument); log.setCreateUser(userDto.getUserId());
				 * log.setCreateTime(now); log.setUpdateUser(userDto.getUserId());
				 * log.setUpdateTime(now); loggSystemRepository.save(log);
				 */
			}
		}
	}

	/**
	 * 判斷是否有例外網址是不用做登入檢查的
	 * 
	 * @param servletPath
	 * @return
	 */
	private boolean checkEscapeUrl(String servletPath) {
		boolean result = false;
		if (servletPath.equals("/")) {
			result = true;
			return result;
		}
		String[] escapeUrl = { "/index.html", "/favicon.ico", "/admin/login", "/admin/loginCheck", "/admin/loginOut",
				"/createStoreJsonFile", "/createStoreEmployeeJsonFile", "/securityImage", "/img", "/js", "/css",
				"/system/showforget", "/system/forget/chkForget", "/home" ,"/ajaxGetProductPic"};

		for (String s : escapeUrl) {
			if (servletPath.startsWith(s))
				result = true;
		}
		return result;
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// logger.info("postHandle");

	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// logger.info("afterCompletion");
	}

}
