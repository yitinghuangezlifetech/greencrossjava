package tw.com.ezlifetech.ezReco.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SysParamsUtils {

	
	
	public static String getParam(String key) {
		Properties properties = new Properties();
		
		InputStream in = SysParamsUtils.class.getClassLoader().getResourceAsStream("apSysParams.properties");
		
		String result = null;
		try {
			properties.load(in);
			result = properties.getProperty(key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	} 
	
}
