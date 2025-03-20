package tw.com.ezlifetech.ezReco.util;

import java.util.List;
import java.util.Map;

public class DAOUtill {

	public static String setSQLInList(List<Map<String, Object>> bean, String key) {
		String val = "";
		for (int i = 0; i < bean.size(); i++) {
			if (i == 0) {
				val = "'" + bean.get(i).get(key) + "'";
			} else {
				val += ", '" + bean.get(i).get(key) + "'";
			}
		}
		return val;
	}
}
