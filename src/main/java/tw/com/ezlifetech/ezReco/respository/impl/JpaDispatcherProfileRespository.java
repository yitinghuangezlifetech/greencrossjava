package tw.com.ezlifetech.ezReco.respository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.DispatcherProfile;
import tw.com.ezlifetech.ezReco.respository.DispatcherProfileRespository;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Repository
public class JpaDispatcherProfileRespository extends JpaGenericRepository<DispatcherProfile, String> implements DispatcherProfileRespository {

	
	
	
	
	@Override
	public List<Map<String, Object>> getAllDisIdSelectListByWhIdCompId(String whId,String comId) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(StringUtil.isBlank(comId) || StringUtil.isBlank(whId)) {
			return new ArrayList<Map<String, Object>>() ;
		}
		params.put("comp_id", comId);
		params.put("wh_id", whId);
		
		StringBuffer sql = new StringBuffer(); 
		sql.append("	SELECT                          ");
		sql.append("		id as id,                   ");
//		sql.append("		id||'/'||name as name                ");
		sql.append("		name as name                ");
		sql.append("	                                ");
		sql.append("	FROM dispatcher_profile         ");
		sql.append("	WHERE 1=1                       ");
		sql.append("	AND comp_id = :comp_id          ");
		sql.append("	AND wh_id=:wh_id                ");
		
		
		
		return this.findListMapBySQL_map(sql.toString(), params);
	}

}
