package tw.com.ezlifetech.ezReco.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import tw.com.ezlifetech.ezReco.common.service.ReCAPTCHAService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;



@Service
public class ReCAPTCHAServiceImpl implements ReCAPTCHAService{
	
	private static Logger log = LoggerFactory.getLogger(ReCAPTCHAServiceImpl.class);
	
	private static final String SITE_KEY   ="6LdIxx0TAAAAALZ82-vyBCslYScl0gIaO6f6OS8i";
	private static final String SECRET_KEY ="6LdIxx0TAAAAAB5iKN8kDkoSy6tNk36zk7EL_Q1Z";
	private static final String CHECK_URL  ="https://www.google.com/recaptcha/api/siteverify";
	private static final String GOOGLE_DOMAIN  ="www.google.com";
	private final static String USER_AGENT = "Mozilla/5.0";
	
	
	
	@Override
	public boolean verification(String gRecaptchaResponse,HttpSession session) {
		return verification(gRecaptchaResponse,null,session);
	}
	@Override
	public boolean verification(String gRecaptchaResponse, String remoteip,HttpSession session) {
		if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
			return false;
		}
		
		try{
		URL obj = new URL(CHECK_URL);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String postParams = "secret=" + SECRET_KEY + "&response="
				+ gRecaptchaResponse + (remoteip==null?"":"&remoteip="+remoteip);

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(postParams);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + CHECK_URL);
		System.out.println("Post parameters : " + postParams);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		
		log.info("[Google response] : "+response.toString());
		
		
		JsonElement jelement = new JsonParser().parse(response.toString());
		JsonObject  jobject = jelement.getAsJsonObject();
		session.setAttribute("useReCAPTCHA","N");
		return jobject.get("success").getAsBoolean();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	@Override
	public void useReCAPTCHA(HttpSession session,Model model) {
		if(!pingURL()) {
			session.setAttribute("useReCAPTCHA","N");
			return;
		}
		session.setAttribute("useReCAPTCHA","Y");
		model.addAttribute("reCAPTCHACode", "<div class=\"g-recaptcha\" data-sitekey=\""+SITE_KEY+"\"></div>");
	}
	private boolean pingURL() {
		// TODO Auto-generated method stub
		InetAddress address;
		boolean reachable= false;
		try {
			address = InetAddress.getByName(GOOGLE_DOMAIN);
			reachable = address.isReachable(10000);
		} catch (Exception e) {
			log.error(e.toString());
		}
        
		return reachable;
	}
	@Override
	public void cancelUseReCAPTCHA(HttpSession session) {
		session.setAttribute("useReCAPTCHA","N");
	}
	@Override
	public boolean isUseReCAPTCHA(HttpSession session) {
		return ((String)session.getAttribute("useReCAPTCHA")).equals("Y");
	}
	
	
	
	
	
}
