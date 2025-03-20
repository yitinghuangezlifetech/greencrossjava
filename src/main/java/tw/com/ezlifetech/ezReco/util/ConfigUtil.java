package tw.com.ezlifetech.ezReco.util;


import java.util.Properties;

public class ConfigUtil {

	public final static String PRODUCT_PIC_PATH = "productpic";
	
	public final static String DISPATCHER_PROFILE_PIC_PATH = "dispatcherProfilePic";
	
	public static String getValue(String name) throws Exception {
		Properties properties = new Properties();
		String configFile = "proj-setting.properties";
		
		properties.load(ConfigUtil.class.getClassLoader().getResourceAsStream(configFile));
		
			
		return properties.getProperty(name,null);
	} 
}
