package tw.com.ezlifetech.ezReco.util;

import java.io.File;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;


/**
 * 字串格式轉換
 * <p>Title: 字串格式轉換</p>
 * <p>Description: 字串格式轉換</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: TradeVan</p>
 * @author TradeVan T40
 * @version 1.0
 */
/**
 * @author 6282
 *
 */
/**
 * @author 6282
 *
 */
public class StringUtil extends StringUtils {
	public final static int PADDING_TYPE_RIGHT = 0;
	public final static int PADDING_TYPE_LEFT = 1;

	/**
	 * 防止XSS攻擊，將其傳入的字串做過濾，確保資料安全
	 * @param obj Object Object資料
	 * <p>Object會轉成String</p>
	 * @return String 過濾XSS攻擊後的資料內容
	 */
	public static String getParameter(Object obj) {
		String value = "";
		
		if(obj instanceof StringBuffer) {
			try {
				value = ((StringBuffer)obj).toString();
			} catch (Exception e) {
				try {
					MethodUtils.invokeMethod(e, "printStackTrace");
				} catch (Exception ex) {
				}
			}
		} else {
			try {
				value = ((String) obj);
			} catch (Exception e) {
				try {
					MethodUtils.invokeMethod(e, "printStackTrace");
				} catch (Exception ex) {
				}
			}
		}
		
	    

	    return getParameter(value);
	}
	
	/**
	 * 防止XSS攻擊，將其傳入的字串做過濾，確保資料安全
	 * @param obj Object Object資料
	 * <p>Object會轉成String</p>
	 * @return String 過濾XSS攻擊後的資料內容
	 * <p>空值會固定回傳零</p>
	 */
	public static String getParameterIntegerValue(Object obj) {
		return String.valueOf(getParameterIntValue(obj));
	}
	
	/**
	 * 防止XSS攻擊，將其傳入的字串做過濾，確保資料安全
	 * @param obj Object Object資料
	 * <p>Object會轉成String</p>
	 * @return String 過濾XSS攻擊後的資料內容
	 * <p>空值會固定回傳零</p>
	 */
	public static int getParameterIntValue(Object obj) {
		int intValue = 0;
		
		if(notBlank(getParameter(obj))) {
			try {
				intValue = Integer.parseInt(getParameter(obj));
			} catch(NumberFormatException nfex) {
				try {
					MethodUtils.invokeMethod(nfex, "printStackTrace");
				} catch (Exception ex) {
				}
				intValue = 0;
			}
		}//if(notBlank(getParameter(obj))) end

		return intValue;
	}
	
	/**
	 * 防止XSS攻擊，將其傳入的字串做過濾，確保資料安全
	 * @param obj Object Object資料
	 * <p>Object會轉成String</p>
	 * @return String 過濾XSS攻擊後的資料內容
	 * <p>空值會固定回傳零點零</p>
	 */
	public static String getParameterDoubleValue(Object obj) {
		return String.valueOf(getParameterDubValue(obj));
	}
	
	/**
	 * 防止XSS攻擊，將其傳入的字串做過濾，確保資料安全
	 * @param obj Object Object資料
	 * <p>Object會轉成String</p>
	 * @return String 過濾XSS攻擊後的資料內容
	 * <p>空值會固定回傳零點零</p>
	 */
	public static double getParameterDubValue(Object obj) {
		double doubleValue = 0.0;
		
		if(notBlank(getParameter(obj))) {
			try {
				doubleValue = Double.parseDouble(getParameter(obj));
			} catch(NumberFormatException nfex) {
				doubleValue = 0.0;
			}
		}//if(notBlank(getParameter(obj))) end
		
		return doubleValue;
	}
	
	/**
	 * 防止XSS攻擊，將其傳入的字串做過濾，確保資料安全
	 * @param obj Object Object資料
	 * <p>Object會轉成String</p>
	 * @return String 過濾XSS攻擊後的資料內容
	 * <p>空值會固定回傳零</p>
	 */
	public static String getParameterLongValue(Object obj) {
		return String.valueOf(getParameterLngValue(obj));
	}
	
	/**
	 * 防止XSS攻擊，將其傳入的字串做過濾，確保資料安全
	 * @param obj Object Object資料
	 * <p>Object會轉成String</p>
	 * @return String 過濾XSS攻擊後的資料內容
	 * <p>空值會固定回傳零</p>
	 */
	public static long getParameterLngValue(Object obj) {
		long longValue = 0;
		
		if(notBlank(getParameter(obj))) {
			try {
				longValue = Long.parseLong(getParameter(obj));
			} catch(NumberFormatException nfex) {
				longValue = 0;
			}
		}//if(notBlank(getParameter(obj))) end
		
		return longValue;
	}
	
	/**
	 * 防止XSS攻擊，將其傳入的字串做過濾，確保資料安全
	 * @param value String 字串資料
	 * @return String 過濾XSS攻擊後的資料內容
	 */
	public static String getParameter(String value) {
		String data = "";

		if(StringUtils.isNotBlank(value)) {
			value = value.replaceAll("<", "＜");
			value = value.replaceAll(">", "＞");
			value = value.replaceAll("'", "’");
			value = value.replaceAll("eval\\((.*)\\)", "");
			value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
			value = value.replaceAll("script", "");
			value = value.replaceAll("alert", "");
			value = value.replaceAll("%2E", "");
			data = value.trim();
		}
		
		return data;
	}

	/**
	 * 如果是null物件回傳空字串
	 * @param value
	 * @return string
	 */
	public static String getString(Object MapO){
		return MapO!=null?MapO.toString():"";
	}
	
	/**
	 * 防止XSS攻擊，將其傳入的字串做過濾，確保資料安全
	 * @param values String 字串陣列資料
	 * @return String[] 過濾XSS攻擊後的資料內容
	 */
	public static String[] getParameterValues(String[] values) {
		ArrayList<String> arrayData = getParameterArrayValues(values);
		String[] data = null;
		
		if(arrayData!=null && arrayData.size()>0) {
			data = new String[arrayData.size()];
			for(int idx=0; idx<data.length; idx++) {
				data[idx] = String.valueOf(arrayData.get(idx)).trim();
			}//for() end
		} else {
			data = new String[0];
		}//if(arrayData!=null && arrayData.size()>0) end
		
		return data;
	}
	
	/**
	 * 防止XSS攻擊，將其傳入的字串做過濾，確保資料安全
	 * @param values String 字串陣列資料
	 * @return ArrayList 過濾XSS攻擊後的資料內容
	 */
	public static ArrayList<String> getParameterArrayValues(String[] values) {
		ArrayList<String> arrayData = new ArrayList<String>();
		
		if(values!=null && values.length>0) {
			for(int idx=0; idx<values.length; idx++) {
				if(StringUtils.isNotBlank(values[idx])) {
					arrayData.add(getParameter(values[idx]).trim());
				} else {
					arrayData.add("");
				}
			}//for() end			
		}//if(values!=null && values.length>0) end
		
		return arrayData;
	}
	
	
	
	/**
	 * 判斷是否有空白或NULL的資料內容
	 * @param object Object 物件
	 * @return boolean 有空白或NULL的資料
	 */
	public static boolean blank(Object object) {
		return (!notBlank(object));
	}
	
	/**
	 * 判斷是否有空白或NULL的資料內容
	 * @param object Object 物件
	 * @return boolean 沒有空白或NULL的資料
	 */
	public static boolean notBlank(Object object) {
		boolean isNotBlank = false;

		if(object!=null) {
			if(object instanceof String) {
				String data = ((String)object);
				if(StringUtils.isNotBlank(data)) {
					isNotBlank = true;
				}
			} else if(object instanceof Integer) {
				try {
					int data = Integer.valueOf(String.valueOf(object)).intValue();
					isNotBlank = true;
				} catch(NumberFormatException nfex) {
					isNotBlank = false;
				}
			} else if(object instanceof Float) {
				try {
					float data = Float.valueOf(String.valueOf(object)).floatValue();
					isNotBlank = true;
				} catch(NumberFormatException nfex) {
					isNotBlank = false;
				}
			} else if(object instanceof Double) {
				try {
					double data = Double.valueOf(String.valueOf(object)).doubleValue();
					isNotBlank = true;
				} catch(NumberFormatException nfex) {
					isNotBlank = false;
				}
			} else if(object instanceof Long) {
				try {
					long data = Long.valueOf(String.valueOf(object)).longValue();
					isNotBlank = true;
				} catch(NumberFormatException nfex) {
					isNotBlank = false;
				}
			} else if(object instanceof ArrayList) {
				ArrayList data = ((ArrayList)object);
				if(data!=null && data.size()>0) {
					isNotBlank = true;
				}
			} else if(object instanceof File[]) {
				File[] data = ((File[])object);
				if(data!=null && data.length>0) {
					isNotBlank = true;
				}
			} else if(object instanceof File) {
				File data = ((File)object);
				if(data!=null && data.length()>0) {
					isNotBlank = true;
				}
			} else if(object instanceof List) {
				List data = ((List)object);
				if(data!=null && data.size()>0) {
					isNotBlank = true;
				}
			} else if(object instanceof Map) {
				Map data = ((Map)object);
				if(data!=null && data.size()>0) {
					isNotBlank = true;
				}
			} else if(object instanceof Collection) {
				Collection data = ((Collection)object);
				if(data!=null && data.size()>0) {
					isNotBlank = true;
				}
			} else if(object instanceof Object[]) {
				Object[] data = ((Object[])object);
				if(data!=null && data.length>0) {
					isNotBlank = true;
				}
			} else if(object instanceof StringBuffer) {
				StringBuffer data = ((StringBuffer)object);
				if(data!=null && data.length()>0) {
					isNotBlank = true;
				}
			} else if(object instanceof String[]) {
				String[] data = ((String[])object);
				if(data!=null && data.length>0) {
					isNotBlank = true;
				}
			} else if(object instanceof Locale) {
				String data = ((Locale)object).toString();
				if(data!=null && data.length()>0) {
					isNotBlank = true;
				}
			} else {				
				isNotBlank = false;
			}//if(object instanceof String) end
		} else {
			isNotBlank = false;
		}//if(object!=null) end

		return isNotBlank;
	}
	
	/**
	 * 取出最大長度資料內容的資料
	 * <p>資料內容有可能超過欄位長度，故只取出最大長度的資料；</p>
	 * <p>超過的部份則去除</p>
	 * @param value String 資料內容
	 * @param length int 資料最大長度
	 * @return String 最大長度的資料內容
	 */
	public static String getValue(String value, int length) {
		String strValue = "";
		
		if(StringUtils.isNotBlank(value)) {
			byte[] byteValue = getParameter(value).getBytes();
			
			//判斷資料內容是否有超過最大的長度
			if(byteValue.length < length) {
				strValue = getParameter(value);
			} else {
				byte[] tmpData = new byte[length];
		        System.arraycopy(byteValue, 0, tmpData, 0, length);

		        int idx = 0;
		        for (; idx<tmpData.length; idx++) {
		          if (tmpData[idx] < 0) {
		            if (idx == tmpData.length - 1) {
		              break;
		            }
		            idx++;
		          }
		        }
		        strValue = new String(tmpData, 0, idx);
			}//if(byteValue.length < length) end
		}//if(StringUtils.isNotBlank(value)) end
		
		return strValue;
	}
	
	/**
	 * 填補空白
	 * @param value String 字串資料
	 * @param length int 填補的長度
	 * @param paddingType int 填補右邊空白或填補左邊空白
	 * <p>使用StringUtil.PADDING_TYPE_RIGHT為右補空白</p>
	 * <p>使用StringUtil.PADDING_TYPE_LEFT為左補空白</p>
	 * @return String 填補空白後的資料內容
	 */
	public static String fillWithBlank(String value, int length, int paddingType) {
		String strValue = "";
		
		if(StringUtils.isNotBlank(value)) {
			String tmp = getValue(value, length);
			int valueLength = tmp.getBytes().length;
			int differentLength = 0;
			
			//判斷資料內容長度是否有超過最大資料長度
			if(valueLength>0) {
				if(valueLength>length) {
					differentLength = length;
				} else {
					differentLength = (length-valueLength);
				}//if(valueLength>maxLength) end
			} else {
				//若沒有資料內容時,也要補齊資料最大長度
				differentLength = length;
			}//if(valueLength>0) end
			
			//補齊空白處或補齊數字處
			byte[] fillWord = new byte[differentLength];
			Arrays.fill(fillWord, (byte) 0x20);
			
			if(PADDING_TYPE_RIGHT==paddingType) {
				strValue = (tmp+(new String(fillWord)));
			} else {
				strValue = ((new String(fillWord))+tmp);
			}//if(PADDING_RIGHT==padding) end
		} else {
			//補齊空白處或補齊數字處
			byte[] fillWord = new byte[length];
			Arrays.fill(fillWord, (byte) 0x20);
			strValue = (new String(fillWord));
		}//if(StringUtils.isNotBlank(value)) end
		
		return strValue;
	}
	
	/**
	 * 填補零字元
	 * @param value String 字串資料
	 * @param length int 填補的長度
	 * @param paddingType int 填補右邊為零或填補左邊為零	 * 
	 * <p>使用StringUtil.PADDING_TYPE_RIGHT為右補零</p>
	 * <p>使用StringUtil.PADDING_TYPE_LEFT為左補零</p>
	 * @return String 填補為零後的資料內容
	 */
	public static String fillWithZero(String value, int length, int paddingType) {
		String strValue = "";
		
		if(StringUtils.isNotBlank(value)) {
			String tmp = getValue(value, length);
			int valueLength = tmp.getBytes().length;
			int differentLength = 0;
			
			//判斷資料內容長度是否有超過最大資料長度
			if(valueLength>0) {
				if(valueLength>length) {
					differentLength = length;
				} else {
					differentLength = (length-valueLength);
				}//if(valueLength>maxLength) end
			} else {
				//若沒有資料內容時,也要補齊資料最大長度
				differentLength = length;
			}//if(valueLength>0) end
			
			//補齊空白處或補齊數字處
			byte[] fillWord = new byte[differentLength];
			Arrays.fill(fillWord, (byte) 0x30);
			
			if(PADDING_TYPE_RIGHT==paddingType) {
				strValue = (tmp+(new String(fillWord)));
			} else {
				strValue = ((new String(fillWord))+tmp);
			}//if(PADDING_RIGHT==padding) end
		} else {
			//補齊空白處或補齊數字處
			byte[] fillWord = new byte[length];
			Arrays.fill(fillWord, (byte) 0x30);
			strValue = (new String(fillWord));
		}//if(StringUtils.isNotBlank(value)) end
		
		return strValue;
	}
	
	/**
	 * 字元集轉換
	 * @param value String 字串資料
	 * @param inputEncode String 原始字串字元集編碼
	 * <p>轉換後字元集編碼預設為UTF-8字元集</p>
	 * @return String 字元集轉換後的資料內容
	 */
	public static String convertData(String value, String inputEncode) {
		String data = "";
		
		try {
			data = convertData(value, inputEncode, "UTF-8");
		} catch (UnsupportedEncodingException ueex) { }
		
		return data;
	}
	
	/**
	 * 字元集轉換
	 * @param value String 字串資料
	 * @param inputEncode String 原始字串字元集編碼
	 * @param outputEncode String 轉換後字元集編碼
	 * @return String 字元集轉換後的資料內容
	 * @throws UnsupportedEncodingException
	 */
	public static String convertData(String value, String inputEncode, String outputEncode) throws UnsupportedEncodingException {
		if(StringUtils.isNotBlank(value)) {
			try {
				return (new String(getParameter(value).getBytes(inputEncode), outputEncode));
			} catch (UnsupportedEncodingException ueex) {
				throw ueex;
			}
		} else {
			return "";
		}
	}
	
	/**
	 * 16進位轉成10進位
	 * @param value String 16進位字串
	 * @return byte[] 10進位資料內容
	 */
	private static byte[] convertHexToInt(String value) {
		byte[] key_value = new byte[value.length() / 2];
		byte[] temp = value.getBytes();

		for (int i = 0; i < temp.length; i++) {
			if ((temp[i] >= 48) && temp[i] <= 57) { // 0 ~ 9
				temp[i] = (byte) (temp[i] - 48);
			} else if (temp[i] >= 65 && temp[i] <= 70) { // A ~ F
				temp[i] = (byte) (temp[i] - 65 + 10);
			} else if (temp[i] >= 97 && temp[i] <= 102) { // a ~ f
				temp[i] = (byte) (temp[i] - 97 + 10);
			}
		}

		for (int i = 0; i < key_value.length; i++) {
			key_value[i] = (byte) (temp[i * 2] * 16 + temp[i * 2 + 1]);
		}
		return key_value;
	}
	
	/**
	 * 轉換成NCR編碼
	 * @param value String 字串資料
	 * @return String NCR資料內容
	 * @throws UnsupportedEncodingException
	 */
	private static String convertNCR(String value) throws UnsupportedEncodingException {
		String data = "";

		try {
			byte[] unicodeBytes = value.getBytes("UTF-16");
			StringBuffer sbHexData = new StringBuffer();
			String hexString = "";
			for (int idx = 0; idx < unicodeBytes.length; idx++) {
				if (idx > 1) {
					int intValue = Integer.parseInt(String.valueOf(unicodeBytes[idx]));
					if (intValue < 0) {
						intValue += 256;
					}
					
					hexString = Integer.toHexString(intValue);
					if (hexString.length() < 2) {
						hexString = "0" + hexString;
					}
					sbHexData.append(hexString);
				}
			} // for() end

			if (sbHexData.length() > 0) {
				String hexData = "0x" + sbHexData.toString();
				data = "&#" + Integer.decode(hexData) + ";";
			}
		} catch (UnsupportedEncodingException ueex) {
			throw ueex;
		}
		
		return data;
	}
	
	/**
	 * split的陣列資料內容
	 * @param value String 字串
	 * @return ArrayList 陣列資料內容
	 */
	public static ArrayList<String> splitValues(String value) {
		ArrayList<String> arrayData = new ArrayList<String>();
		
		if(StringUtils.isNotBlank(value)) {
			String[] datas = StringUtils.split(value);
			for(int idx=0; idx<datas.length; idx++) {
				arrayData.add(getParameter(datas[idx]));
			}//for() end
		}//if(StringUtils.isNotBlank(value)) end
		
		return arrayData;
	}
	
	/**
	 * NCR編碼轉換
	 * @param value String NCR碼資料
	 * <p>NCR碼為&#開頭且;結尾</p>
	 * <p>例如:&#30849;對照成中文則為碁</p>
	 * <p>可以在google搜尋&#30849;也可以看到google會將它轉成碁</p>
	 * @return String unicode資料內容
	 */
	public static String ncrDecode(String value) {
		if (StringUtils.isNotBlank(value)) {
			try {
				StringBuffer sbData = new StringBuffer(value);
				sbData.indexOf("&#");// 字串開頭為&#字串結尾為;
				Pattern pattern = Pattern.compile("\\d*");
				int startIdx = 0;
				while (true) {
					int sidx = sbData.indexOf("&#", startIdx);
					if (sidx == -1) {
						break;
					}

					int eidx = sbData.indexOf(";", sidx);
					if (eidx == -1) {
						break;
					}
					String data = sbData.substring(sidx + 2, eidx);
					if (Integer.parseInt(data) > -1 && Integer.parseInt(data) < 128) {
						sbData.replace(sidx, eidx + 1, ("" + ((char) Integer.parseInt(data))));
						startIdx = sidx + 1;
					} else {
						Matcher m = pattern.matcher(data);
						if (m.matches()) {
							Integer intValue = Integer.decode(data);
							String unicode = Integer.toHexString(intValue.intValue());
							byte[] unicodeBytes = convertHexToInt(unicode);
							String unicodeString = new String(unicodeBytes, "UTF-16");
							sbData.replace(sidx, eidx + 1, unicodeString);
							startIdx = sidx + 1;
						} else {
							startIdx = eidx + 1;
						}
					}// if (Integer.parseInt(data) > -1 && Integer.parseInt(value) < 128) end
				}// while() end
				
				return sbData.toString();
			} catch (UnsupportedEncodingException ueex) {
				return value;
			}
		} else {
			return "";
		}//if(StringUtils.isNotBlank(value)) end
	}
	
	/**
	 * 轉換成NCR編碼
	 * @param value String 字串資料
	 * @return String NCR資料內容
	 */
	public static String ncrEncode(String value) {
		if(StringUtils.isNotBlank(value)) {
			StringBuffer sbData = new StringBuffer();
			char[] data = value.toCharArray();
			
			for(int idx=0; idx<data.length; idx++) {
				try {
					sbData.append(convertNCR(String.valueOf(data[idx])));
				} catch (UnsupportedEncodingException ueex) {
					sbData.append("");
				}
			}//for() end
			
			return sbData.toString();
		} else {
			return "";
		}//if(StringUtils.isNotBlank(value)) end
	}
	
	/**
	 * 轉換成NCR編碼
	 * @param value String 字串資料
	 * @return String[] NCR陣列資料內容
	 */
	public static String[] ncrEncodeValues(String value) {
		String[] data = null;
		
		if(StringUtils.isNotBlank(value)) {
			char[] datas = value.toCharArray();
			data = new String[datas.length];
			
			for(int idx=0; idx<datas.length; idx++) {
				try {
					data[idx] = (convertNCR(String.valueOf(datas[idx])));
				} catch (UnsupportedEncodingException ueex) {
					data[idx] = "";
				}
			}//for() end
			
			return data;
		} else {
			data = new String[0];
		}//if(StringUtils.isNotBlank(value)) end
		
		return data;
	}
	
	/**
	 * 轉換成NCR編碼
	 * @param value String 字串資料
	 * @return ArrayList NCR陣列資料內容
	 */
	public static ArrayList<String> ncrEncodeArrayValues(String value) {
		ArrayList<String> arrayData = new ArrayList<String>();
		
		if(StringUtils.isNotBlank(value)) {
			char[] datas = value.toCharArray();
			
			for(int idx=0; idx<datas.length; idx++) {
				try {
					arrayData.add(convertNCR(String.valueOf(datas[idx])));
				} catch (UnsupportedEncodingException ueex) {
					arrayData.add("");
				}
			}//for() end
			
			return arrayData;
		}//if(StringUtils.isNotBlank(value)) end
		
		return arrayData;
	}
	
	
	/**
	 * 將資料中的欄位做值的連結，例：'aaa','bbb','ccc'，用於傳入sql in的語法。
	 * @param list
	 * @param coloumnName
	 * @return String
	 */
	public static String genInString(List<Map<String,String>> list,String coloumnName){
		StringBuffer inString =new StringBuffer("");
		try {
			for(int i=0;i<list.size();++i){
				Map map =list.get(i);
				if(inString.length() >0) 
					inString.append(",");
				inString.append("'"+map.get(coloumnName)+"'"); 
			}
		} catch (Exception e) {
			
		}
		return inString.toString();
	}
	/**
	 * 將資料中的欄位做值的連結，例：'aaa','bbb','ccc'，用於傳入sql in的語法。
	 * @param ArrayList<String>
	 * @return String
	 */
	public static String genInString(ArrayList<String> list){
		StringBuffer inString =new StringBuffer("");
		try {
			for(int i=0;i<list.size();++i){
				if(inString.length() >0) 
					inString.append(",");
				inString.append("'"+list.get(i)+"'"); 
			}
		} catch (Exception e) {
			
		}
		return inString.toString();
	}
	/**
	 * 將資料中的欄位做值的連結，例：'aaa','bbb','ccc'，用於傳入sql in的語法。
	 * @param ArrayList<String>
	 * @return String
	 */
	public static String genInString(List<String> list){
		StringBuffer inString =new StringBuffer("");
		try {
			for(int i=0;i<list.size();++i){
				if(inString.length() >0) 
					inString.append(",");
				inString.append("'"+list.get(i)+"'"); 
			}
			if (isBlank(inString.toString())) {
				inString.append("''");
			}
		} catch (Exception e) {
			
		}
		return inString.toString();
	}
	/**
	 * 將資料中的欄位做值的連結，例：'aaa','bbb','ccc'，用於傳入sql in的語法。
	 * @param ArrayList<String>
	 * @return String
	 */
	public static String genInString(String[] ary){
		StringBuffer inString =new StringBuffer("");
		try {
			for(int i=0;i<ary.length;++i){
				if(inString.length() >0) 
					inString.append(",");
				inString.append("'"+ary[i]+"'"); 
			}
		} catch (Exception e) {
			
		}
		return inString.toString();
	}
	/**
	 * 將資料中的欄位轉成ArrayList，用於傳入sql in的語法。
	 * @param list
	 * @param coloumnName
	 * @return ArrayList
	 */
	public static ArrayList<String> genInAry(List<Map<String,String>> list,String coloumnName){
		ArrayList<String> ary=new ArrayList<String>();
		for(Map<String,String> map:list)
			ary.add(map.get(coloumnName));

		return ary;
	}
	/**
	 * 
	 * @param value 字串
	 * @param length 限制長度
	 * @return boolean
	 */
	public static boolean checkStringLength(String value,int limitLen){
		boolean result=true;
		byte[] byteValue = getParameter(value).getBytes();
		if(byteValue.length >limitLen)
			result=false;
		
		return result;
	}
	/**
	   * 轉全形
	   * @param values
	   * @return
	   */
	  public static String transFullShape(String values) {
		  String fullShape = "";
		  StringBuffer sb = null;
		  
		  if(isNotBlank((values))) {
			  sb = new StringBuffer();
			  char[] charArray = values.toCharArray();
			  for(int idx=0; idx<charArray.length; idx++) {
				  int tmp = ((int)charArray[idx]);
				  if(tmp<128) {
					  tmp = 65248+tmp;
				  }
				  sb.append(((char)tmp));
			  }//for(idx) end
			  
			  fullShape = sb.toString();
		  }//if() end
		  
		  return fullShape;
	  }
	  
	  /**
	   * 轉半形
	   * @param values
	   * @return
	   */
	  public static String transHalfShape(String values) {
		  String halfShape = "";
		  StringBuffer sb = null;
		  
		  if(isNotBlank(values)) {
			  sb = new StringBuffer();
			  char[] charArray = values.toCharArray();
			  for(int idx=0; idx<charArray.length; idx++) {
				  int tmp = ((int)charArray[idx]);
				  if(tmp>65280 && tmp<65376) {
					  tmp = tmp-65248;
				  }
				  sb.append(((char)tmp));
			  }//for(idx) end
			  
			  halfShape = sb.toString();
		  }//if() end
		  
		  return halfShape;
	  }
	  /**
	   * 轉換List到字串
	   * 例：List<String> to 111,222,333 
	   * @param List<String> list
	   * @param regex
	   * @return
	   */
	  public static String splitListToString(List<String> list,String regex){
		  StringBuffer result=new StringBuffer("");
		  if(list != null){
			  //將List裡的資料append符號
			  for(String str:list){
				  result.append(str).append(regex);
			  }
			  //去掉最後一個符號
			  if(result.length() > 0){
				  result.substring(0, result.length()-1);
			  }
		  }
		  return result.toString();
	  }
	  /**
	   * 轉換字串到List
	   * 例：111,222,333 to List<String>
	   * @param value
	   * @param regex
	   * @return
	   */
	  public static List<String> splitToList(String value,String regex){
		  List<String> list = new ArrayList<String>();
		  if(StringUtil.isNotBlank(value)){
			  String[] ary=value.split(regex);
			  for(String val:ary){
				  list.add(val);
			  }
		  }
		  return list;
	  }
	  
	  
	  
	/**
	 * 隨機取數字
	 * @param size 位數
	 * @return
	 */
	public static String getRandomNum(int size) {
		  int max = 1;
		  
		  for(int i = 0 ; i<size ; i++) {
			  max = max*10;
		  }
		  max=max-1;
		  Random ran = new Random();
		  int result = ran.nextInt(max);
		  return result+"";
	  }
	  
	  public static String specByCount(int count) {
		  String res= "";
		  for(int i = 0;i<count;i++) {
			  res = "　" +res;
		  }
		  return res;
	  }
	  
	  public static String repeatStr(int count,String str) {
		  String res= "";
		  for(int i = 0;i<count;i++) {
			  res = str +res;
		  }
		  return res;
	  }
	  
	  public static String getSubFileName(String fileName) {
	    	int idx = fileName.lastIndexOf(".");
	    	if (idx != -1) {
	    		return fileName.substring(idx);
	    	}
	    	return "";
	    }
	  
	  public static boolean isInteger(String value) {
		    Pattern pattern = Pattern.compile("^[-+]?\\d+$");
		    return pattern.matcher(value).matches();
		}
}
