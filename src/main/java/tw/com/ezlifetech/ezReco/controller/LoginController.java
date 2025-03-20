package tw.com.ezlifetech.ezReco.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import tw.com.ezlifetech.ezReco.bean.LoginBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.common.service.ReCAPTCHAService;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.model.User;
import tw.com.ezlifetech.ezReco.service.LoginService;
import tw.com.ezlifetech.ezReco.util.Constant;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;



@Controller
public class LoginController extends GenericController{
	private static final String JAVA_NAME="LoginController";
	
	
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private ReCAPTCHAService reCAPTCHAService;
	
	/**
	 * 登入頁
	 * @param mv
	 * @return
	 */
	@RequestMapping(value="/admin/login")
	public String loingPage (Model model, @ModelAttribute(LoginBean.beanFormName) LoginBean formBean, HttpServletRequest request) {
		String methodName="loingPage";
		logger.info("The Java " + JAVA_NAME + "'method " + methodName + " is entry");
		try {
			reCAPTCHAService.useReCAPTCHA(request.getSession(), model);
			//reCAPTCHAService.cancelUseReCAPTCHA(request.getSession());
			//model.addAttribute("bulletinList",bullService.getBulletinByLogin());
		} catch (Exception e) {
			try {
				MethodUtils.invokeMethod(e, "printStackTrace");
				handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			} catch (Exception ex) {
				handleException(ex,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+ex.getMessage());
			}
			model.addAttribute("errorMsg",Constant.MSG_ERROR);
			
			
		}
		return "login";
	}
	
	
	/**
	 * 登出
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/admin/loginOut")
	public String loginOut (Model model, HttpServletRequest request) {
		String methodName = "loginCheck";
		try {
			logger.info("Entry method " + methodName);
			request.getSession().invalidate(); // 銷燬Sessionv
			reCAPTCHAService.useReCAPTCHA(request.getSession(), model);
			//reCAPTCHAService.cancelUseReCAPTCHA(request.getSession());
			//model.addAttribute("bulletinList",bullService.getBulletinByLogin());
		} catch (Exception e) {
			try {
				handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
				MethodUtils.invokeMethod(e, "printStackTrace");
			} catch (Exception ex) {
				handleException(ex,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+ex.getMessage());
			}
		}
		
		return "login";
	}
	
	/**
	 * 登出
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/home/loginOut")
	public String homeloginOut (Model model, HttpServletRequest request) {
		String methodName = "loginCheck";
		try {
			logger.info("Entry method " + methodName);
			request.getSession().invalidate(); // 銷燬Sessionv
			//reCAPTCHAService.useReCAPTCHA(request.getSession(), model);
			//reCAPTCHAService.cancelUseReCAPTCHA(request.getSession());
			//model.addAttribute("bulletinList",bullService.getBulletinByLogin());
		} catch (Exception e) {
			try {
				handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
				MethodUtils.invokeMethod(e, "printStackTrace");
			} catch (Exception ex) {
				handleException(ex,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+ex.getMessage());
			}
		}
		
		return "redirect:/";
	}
	
	/**
	 * 傳統登入驗證碼
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/securityImage")
	public void getSecurityImage(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String methodName = "securityImage";
		logger.info("Entry method " + methodName);
		loginService.genSecurityImage(request,response);
	}
	
	
	/**
	 * 登入檢查
	 * @param model
	 * @param userId
	 * @param userPwd
	 * @return
	 */
	@RequestMapping(value="/admin/loginCheck")
	public String loginCheck (Model model, @ModelAttribute(LoginBean.beanFormName) LoginBean formBean, HttpServletRequest request) {
		String methodName = "loginCheck";
		logger.info("The Java " + JAVA_NAME + "'method " + methodName + " is entry");
		String errorMsg=Constant.MSG_LOGIN_ERROR;
		String uId = formBean.getLoginUserId();
		char[] userPp = formBean.getLoginUserPp();
		String pp = new String(userPp != null ? userPp : new char[0]);
		String securityRandom = (String)getSession().getAttribute("randomNumber");//驗證碼
		String lang = formBean.getLang();
		String errorRwd = "3";
		
		
		try {
			if(reCAPTCHAService.isUseReCAPTCHA(getSession())) {
				if(!reCAPTCHAService.verification(request.getParameter("g-recaptcha-response"), getSession())) {
					model.addAttribute("errorMsg",Constant.MSG_SECURITY_ERROR);
					return loingPage( model, formBean, request);
				}
			}else {
				//if(StringUtil.isBlank(formBean.getSecurityId()) || !formBean.getSecurityId().equals(securityRandom)){
				//暫先忽略驗證碼驗證				
				if(false){
					model.addAttribute("errorMsg",Constant.MSG_SECURITY_ERROR);
					return loingPage( model, formBean, request);
				}
			}
			
			User user = loginService.getUserProfNewByUserId(uId);
			
			logger.info("userId=" + uId + ",securityRandom=" + securityRandom);
			
			if(user != null){
				
				UserDto userDto = new UserDto(user);
				
				//密碼超過xx天自動導到密碼修改
				/*if (user.getChangePwdTime() == null)
					user.setChangePwdTime(DateUtil.getSystemDateTime());
				int days = Integer.parseInt(DateUtil.timeDifferenceDays(user.getChangePwdTime().substring(0, 8), 
						DateUtil.getSystemDateTime().substring(0, 8)));
				if (days > Integer.parseInt(changePwdDays)) {
					return "redirect:/system/account"; 
				}*/
				
				//比對狀態必是是使用中
				if(userDto.getUserStatus().equals(Constant.USER_STATUS_ACTIVITY)){
					//判斷是否密碼錯誤超過3次，需鎖定15分鐘不能登入
					boolean check = true;
					int loginErrTimes = 0;
					if(StringUtil.isBlank(userDto.getLoginErrTimes()))
						loginErrTimes = 0;
					else
						loginErrTimes = Integer.parseInt(userDto.getLoginErrTimes());
					if(loginErrTimes >= Integer.parseInt(errorRwd)){
//						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
//						Date beginDate = sdf.parse(user.getLoginErrTime());
//						Date endDate = sdf.parse(DateUtil.getSystemDateTime());
//						long minutes=TimeUnit.MILLISECONDS.toMinutes(endDate.getTime() -beginDate.getTime());
//                      錯誤3次且間隔時間小於15分鐘
//						if(minutes < 3){
//							check=false;
//							errorMsg="密碼錯誤超過3次，請15分鐘後再試";
//						}
//                      錯誤3次將狀態改為鎖定
						check = false;
						user.setUserStatus(Constant.USER_STATUS_LOCK);
						loginService.updateUserLoginError(user);
					}
					if(check){
						//比對密碼是否一致
						//if(TvEncrypt.decode(user.getPswd()).equals(pp)) {
						logger.info("pp:"+pp);
						logger.info("DigestUtils.sha1Hex pp:"+DigestUtils.sha1Hex(pp));
						logger.info("user.getPswd():"+userDto.getUserPwd());
						if(DigestUtils.sha1Hex(pp).equals(userDto.getUserPwd())) {
							logger.info("ININININ");
							//使用代理人記錄原登入者
							MethodUtils.invokeMethod(request.getSession(), "setAttribute", "originalUserId", userDto.getId());
							
							loginService.setUserLoginInfo(userDto, request);//設定使用者登入資訊				
							//清除密碼 
							Arrays.fill(userPp, '\u0000');
							
							//如果帳號與密碼一樣的話或MAIL欄位是空白時需要導到密碼修改頁
							/*
							if(uId.equals(user.getUserText()) || StringUtil.isBlank(user.getUserEmail()))
								return "redirect:/showLoginAccountChange";
							
							
							*/
							return "redirect:/admin/home";//導頁進去首頁
						} else {
							//更新錯誤資訊
							loginService.updateUserLoginError(user);
						}
					}
					
				}
			}
			//model.addAttribute("bulletinList",bullService.getBulletinByLogin());
			model.addAttribute("errorMsg",errorMsg);	
		} catch (Exception e) {
			try {
				handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
				MethodUtils.invokeMethod(e, "printStackTrace");
			} catch (Exception ex) {
				handleException(ex,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+ex.getMessage());
			}
			model.addAttribute("errorMsg",Constant.MSG_ERROR);
		}
		
		
		return  loingPage(model, formBean, request);
	}
	
}
