package tw.com.ezlifetech.ezReco.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {
	private static final Pattern ASCII_PATTERN = Pattern.compile("[\\x00-\\x7F]*");

	public static boolean isPureASCII(String msg) {
		boolean result = false;
		if (ASCII_PATTERN.matcher(msg).matches()) {
			result = true;
		}
		return result;
	}

	/**
	 * IP Address 檢查程式
	 * 
	 * @since 2006/07/19
	 **/
	public static boolean isValidIPAddr(String msg) {
		boolean result = true;
		String[] tmp = msg.split(".");
		if (tmp.length <= 4) {
			for (int i = 0; i < tmp.length; i++) {
				if (Integer.parseInt(tmp[i]) > 255) {
					result = false;
				}
			}
		}
		return result;
	}

	/**
	 * 手機門號檢查程式
	 * 
	 * @since 2006/07/19
	 **/
	public static final Pattern MSISDN_PATTERN = Pattern.compile("[+-]?\\d{10,12}");

	public static boolean isValidMSISDN(String msisdn) {
		boolean result = false;
		if (MSISDN_PATTERN.matcher(msisdn).matches()) {
			result = true;
		}
		return result;
	}

	//public static final Pattern EMAIL_PATTERN = Pattern.compile("^\\w+\\.*\\w+@(\\w+\\.){1,5}[a-zA-Z]{2,3}$");
	public static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)$");

	/**
	 * Email 格式檢查程式
	 * 
	 * @since 2006/07/19
	 **/
	public static boolean isValidEmail(String email) {
		boolean result = false;
		if (EMAIL_PATTERN.matcher(email).matches()) {
			result = true;
		}
		return result;
	}

	public static final Pattern TWPID_PATTERN = Pattern.compile("[ABCDEFGHJKLMNPQRSTUVXYWZIO][12]\\d{8}");

	/**
	 * 身分證字號檢查程式，身分證字號規則： 字母(ABCDEFGHJKLMNPQRSTUVXYWZIO)對應一組數(10~35)，
	 * 令其十位數為X1，個位數為X2；( 如Ａ：X1=1 , X2=0 )；D表示2~9數字 Y = X1 + 9*X2 + 8*D1 + 7*D2 +
	 * 6*D3 + 5*D4 + 4*D5 + 3*D6 + 2*D7+ 1*D8 + D9 如Y能被10整除，則表示該身分證號碼為正確，否則為錯誤。
	 * 臺北市(A)、臺中市(B)、基隆市(C)、臺南市(D)、高雄市(E)、臺北縣(F)、
	 * 宜蘭縣(G)、桃園縣(H)、嘉義市(I)、新竹縣(J)、苗栗縣(K)、臺中縣(L)、
	 * 南投縣(M)、彰化縣(N)、新竹市(O)、雲林縣(P)、嘉義縣(Q)、臺南縣(R)、
	 * 高雄縣(S)、屏東縣(T)、花蓮縣(U)、臺東縣(V)、金門縣(W)、澎湖縣(X)、 陽明山(Y)、連江縣(Z)
	 * 
	 * @since 2006/07/19
	 */
	public static boolean isValidTWPID(String twpid) {
		boolean result = false;
		String pattern = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
		if (TWPID_PATTERN.matcher(twpid.toUpperCase()).matches()) {
			int code = pattern.indexOf(twpid.toUpperCase().charAt(0)) + 10;
			int sum = 0;
			sum = (int) (code / 10) + 9 * (code % 10) + 8 * (twpid.charAt(1) - '0') + 7 * (twpid.charAt(2) - '0')
					+ 6 * (twpid.charAt(3) - '0') + 5 * (twpid.charAt(4) - '0') + 4 * (twpid.charAt(5) - '0')
					+ 3 * (twpid.charAt(6) - '0') + 2 * (twpid.charAt(7) - '0') + 1 * (twpid.charAt(8) - '0')
					+ (twpid.charAt(9) - '0');
			if ((sum % 10) == 0) {
				result = true;
			}
		}
		return result;
	}

	public static final Pattern TWBID_PATTERN = Pattern.compile("^[0-9]{8}$");

	/**
	 * 營利事業統一編號檢查程式 可至 http://www.etax.nat.gov.tw/ 查詢營業登記資料
	 * 
	 * @since 2006/07/19
	 */
	public static boolean isValidTWBID(String twbid) {
		boolean result = false;
		String weight = "12121241";
		boolean type2 = false; // 第七個數是否為七
		if (TWBID_PATTERN.matcher(twbid).matches()) {
			int tmp = 0, sum = 0;
			for (int i = 0; i < 8; i++) {
				tmp = (twbid.charAt(i) - '0') * (weight.charAt(i) - '0');
				sum += (int) (tmp / 10) + (tmp % 10); // 取出十位數和個位數相加
				if (i == 6 && twbid.charAt(i) == '7') {
					type2 = true;
				}
			}
			if (type2) {
				if ((sum % 10) == 0 || ((sum + 1) % 10) == 0) { // 如果第七位數為7
					result = true;
				}
			} else {
				if ((sum % 10) == 0) {
					result = true;
				}
			}
		}
		return result;
	}

	public static boolean isValidQueryString(String sqlStr) {
		if (sqlStr == null || sqlStr.length() <= 0 || sqlStr.indexOf("@") >= 0 || sqlStr.indexOf("'") >= 0
				|| sqlStr.indexOf("_") >= 0 || sqlStr.indexOf("\"") >= 0 || sqlStr.indexOf("%") >= 0
				|| sqlStr.indexOf("=") >= 0) {
			// "關鍵字不可包含 ( @ ' % \ _ = ) 等字元\n";
			return false;
		}
		return true;
	}

	
	public static boolean isValidPwd(String pwd) {
		if (pwd.length() < 6) {
			return false;
		} else {
			String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,30}$";	
			return pwd.matches(regex);
		}

		
	}
	
	public static boolean isNumber(String input) {
		Pattern ptn = Pattern.compile("^-?[0-9]+(\\.[0-9]+)?$");
		Matcher mth = null;
		mth = ptn.matcher(input);
		return mth.find();
	}

	public static boolean isOnlyEnglishNumber(String paramCode) {
		 Pattern pattern = Pattern.compile("^.[A-Za-z0-9]+$"); 
	        Matcher m = pattern.matcher(paramCode);
	        if( !m.matches() ){ //匹配不到,說明輸入的不符合條件
	           return false; 
	        } 
	        return true; 
	}
	
	public static boolean isTimeHHMM(String input) {
		Pattern ptn = Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");
		Matcher mth = null;
		mth = ptn.matcher(input);
		return mth.find();
	}
}
