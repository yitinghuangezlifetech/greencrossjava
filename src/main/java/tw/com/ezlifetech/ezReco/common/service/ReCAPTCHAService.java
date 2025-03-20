package tw.com.ezlifetech.ezReco.common.service;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

public interface ReCAPTCHAService {

	void    useReCAPTCHA(HttpSession session,Model model);
	void    cancelUseReCAPTCHA(HttpSession session);
	boolean isUseReCAPTCHA(HttpSession session);
	
	boolean verification(String gRecaptchaResponse,HttpSession session);
	boolean verification(String gRecaptchaResponse,String remoteip,HttpSession session);
}
