package tw.com.ezlifetech.ezReco.common.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.model.User;
import tw.com.ezlifetech.ezReco.util.Constant;
public class GenericController {
	private static final String REQ_MSG_MAP = "REQ_MSG_MAP";
	
	@Autowired(required = false)
	protected HttpServletRequest request;
	
	private static Logger logger = LoggerFactory.getLogger(GenericController.class);
	
	/**
	 * 取session
	 * @return  session
	 */
	public static HttpSession getSession() {
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    return attr.getRequest().getSession(true); // true == allow create
	}
	
	/**
	 * 取得登入者資訊
	 * @param request
	 * @return
	 */
	public UserDto getLoginUser(){
		return (UserDto)getSession().getAttribute(Constant.SESSION_USER);
	}
	
	public boolean isLogin() {
		return ((String)getSession().getAttribute("isLogin")).equals("Y");
	}
	
	@SuppressWarnings("unchecked")
	protected Map<String, String> getMsgMap(boolean newIfNotFound) {
		Object msgMap = getReqAttr(REQ_MSG_MAP);
		return msgMap != null ? (Map<String, String>) msgMap : (newIfNotFound ? new HashMap<String, String>() : Collections.EMPTY_MAP);
	}
	
	protected boolean hasError() {
		return getMsgMap(false).size() > 0 ? true : false; 
	}
	
	protected void addToMsgMap(String code, String msg) {
		Map<String, String> msgMap = getMsgMap(true);
		msgMap.put(code, msg);
		setReqAttr(REQ_MSG_MAP, msgMap);
	}
	
	
	protected Object getReqAttr(String name) {
		return request.getAttribute(name);
	}
	
	protected void setReqAttr(String name, Object attr) {
		request.setAttribute(name, attr);
	}
	
	protected void setErrorBeans(List<ErrorBean> list) {
		for(ErrorBean reBean : list) {
			addToMsgMap(reBean.getErrSpanId(),reBean.getLabelName()+reBean.getMesg());
		}
	}
	
	protected JsonArray mesgToJSONString() {
		Map<String, String> msgMap = getMsgMap(true);
		JsonArray ja = new JsonArray();
		for(String key :msgMap.keySet()) {
			JsonObject jo = new JsonObject();
			jo.addProperty("errSpanId", key);
			jo.addProperty("mesg", msgMap.get(key));
			ja.add(jo);
		}
		return ja;
	}
	
	protected void notShowHomeBanner() {
		request.getSession().setAttribute("isHomeBannerShow", "N");	
	}
	
	protected void showHomeBanner() {
		request.getSession().setAttribute("isHomeBannerShow", "Y");	
	}
	
	
	protected void notShowHomefooter() {
		request.getSession().setAttribute("isHomefooterShow", "N");	
	}
	
	protected void showHomefooter() {
		request.getSession().setAttribute("isHomefooterShow", "Y");	
	}
	
	
	protected void handleException(Exception e, Logger logger,String message) {
		logger.error(message);
		e.printStackTrace();
	}
	
	
	protected String getLanguage() {
		return (getSession().getAttribute("local")==null?"tw":(String)getSession().getAttribute("local"));
	}
	
	
	protected void notShowHeaderSectionIndex() {
		request.getSession().setAttribute("isShowHeaderSectionIndex", "N");	
	}
	
	protected void showHeaderSectionIndex() {
		request.getSession().setAttribute("isShowHeaderSectionIndex", "Y");	
	}
}
