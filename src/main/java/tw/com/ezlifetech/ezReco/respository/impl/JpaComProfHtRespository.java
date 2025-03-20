package tw.com.ezlifetech.ezReco.respository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.dto.ComProfHtDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.model.ComProfHt;
import tw.com.ezlifetech.ezReco.respository.ComProfHtRespository;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Repository
public class JpaComProfHtRespository  extends JpaGenericRepository<ComProfHt, String> implements ComProfHtRespository{

	@Override
	public List<Map<String, Object>> getAllCompIdAndNameListMap() {
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		
		List<ComProfHt> allList = this.findAllEntityList();
		if(allList!=null) {
			for(ComProfHt c : allList) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", c.getId());
				m.put("name", c.getComName());
				res.add(m);
			}
		}
		return res;
	}
	
	@Override
	public List<Map<String, Object>> getAllCompCodeAndNameListMap() {
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		
		List<ComProfHt> allList = this.findAllEntityList();
		if(allList!=null) {
			for(ComProfHt c : allList) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", c.getComCode());
				m.put("name", c.getComName());
				res.add(m);
			}
		}
		return res;
	}
	
	@Override
	public List<Map<String, Object>> getCompCodeAndNameListMapByUserId(String userId) {
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		
		List<ComProfHt> allList = this.findEntityListByJPQL("SELECT c FROM ComProfHt c , User u WHERE u.comId=c.id AND u.id = ? ", userId);
		if(allList!=null) {
			for(ComProfHt c : allList) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", c.getComCode());
				m.put("name", c.getComName());
				res.add(m);
			}
		}
		return res;
	}
	
	@Override
	public List<Map<String, Object>> getCompIdAndNameListMapByUserId(String userId) {
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		
		List<ComProfHt> allList = this.findEntityListByJPQL("SELECT c FROM ComProfHt c , User u WHERE u.comId=c.id AND u.id = ? ", userId);
		if(allList!=null) {
			for(ComProfHt c : allList) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", c.getId());
				m.put("name", c.getComName());
				res.add(m);
			}
		}
		return res;
	}


	@Override
	public boolean isComCodeRepeat(ComProfHtDto dto) {
		List<ComProfHt>  list = this.findEntityListByJPQL("SELECT c FROM ComProfHt c WHERE c.id != ? AND c.comCode=?", dto.getId(),dto.getComCode());
		return !(list==null || list.size()==0);
	}


	@Override
	public ComProfHt getEntityByComCode(String comCode) {
		ComProfHt c = this.getEntityByJPQL("SELECT c FROM ComProfHt c WHERE c.comCode = ? ", comCode);
		return c;
	}

	@Override
	public List<Map<String, Object>> getAllCompCodeAndNameListMapByWhId(String whId,UserDto loginUser) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(StringUtil.isBlank(whId)) {
			return new ArrayList<Map<String, Object>>() ;
		}
		params.put("wh_Id", whId);
		
		StringBuffer sql = new StringBuffer();
		sql.append("	SELECT                                                  ");
		sql.append("		c.com_code as id,                                   ");
		sql.append("		c.com_name as name                               ");
		sql.append("	                                                        ");
		sql.append("	                                                        ");
		sql.append("	FROM com_prof_ht c   , ref_comp_warehouse r  ,work_class_shift_ht ht   ");
		sql.append("	WHERE 1=1                                               ");
		sql.append("    AND ht.id = r.wcs_id ");
		sql.append("	AND c.com_code=r.com_id                                    ");
		sql.append("	AND r.wh_Id = :wh_Id                                  ");
		sql.append("    AND ht.status = 'A'   ");
		// 登入者 角色不為 ADMIN || OWNER 則只能看到自己所屬協力商
		if(!("ADMIN".equals(loginUser.getRoleType()) || "OWNER".equals(loginUser.getRoleType()))) {
			params.put("com_id", loginUser.getComId());
			sql.append("    AND c.com_id = :com_id   ");
		}
		sql.append("	GROUP BY c.com_code,c.com_name;                            ");
		
		return this.findListMapBySQL_map(sql.toString(), params);
	}


	

}
