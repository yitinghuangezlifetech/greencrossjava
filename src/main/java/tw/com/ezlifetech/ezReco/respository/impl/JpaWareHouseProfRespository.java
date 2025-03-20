package tw.com.ezlifetech.ezReco.respository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.dto.WareHouseProfDto;
import tw.com.ezlifetech.ezReco.model.WareHouseProf;
import tw.com.ezlifetech.ezReco.respository.WareHouseProfRespository;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Repository
public class JpaWareHouseProfRespository  extends JpaGenericRepository<WareHouseProf, String> implements WareHouseProfRespository {

	@Override
	public List<Map<String, Object>> ajaxGridList(WareHouseProfDto dto) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(StringUtil.isBlank(dto.getWhComId())) {
			return new ArrayList<Map<String, Object>>() ;
		}

		params.put("WhComId", dto.getWhComId());

		StringBuffer sql = new StringBuffer();
		sql.append("        SELECT                                                                ");
		sql.append("        	ht.id as id,                                                      ");
		sql.append("        	comp.com_name as comp_name,                                       ");
		sql.append("        	comp.ban as ban,                                       ");
		sql.append("        	ht.wh_id as wh_id,                                    ");
		sql.append("        	ht.wh_name as wh_name,                                       ");
		sql.append("        	ht.wh_addr as wh_addr,                                       ");
		sql.append("        	ht.create_user as create_user,                                    ");
		sql.append("        	to_char(ht.create_time,'YYYY/MM/DD HH24:MI:SS') as create_time,   ");
		sql.append("        	ht.update_user as update_user,                                    ");
		sql.append("        	to_char(ht.update_time,'YYYY/MM/DD HH24:MI:SS') as update_time    ");
		sql.append("        FROM warehouse_profile ht left join com_prof_ht comp                 ");
		sql.append("        ON ht.wh_com_id = comp.com_id                                           ");
		sql.append("        WHERE 1=1                                                             ");
		sql.append("        AND ht.wh_com_id = :WhComId                                            ");

		if(!StringUtil.isBlank(dto.getWhId())) {
			params.put("whId", dto.getWhId());
			sql.append("        AND ht.wh_id = :whId                                            ");
		}
		
		if(!StringUtil.isBlank(dto.getDlId())) {
			params.put("dlId", dto.getDlId());
			sql.append("        AND ht.wh_id in (SELECT dl.wh_id FROM warehouse_profile_dl dl WHERE dl.dl_id = :dlId )                                            ");
			
		}
		
		return this.findListMapBySQL_map(sql.toString(), params);

	}

	@Override
	public List<Map<String, Object>> getAllwhIdAndNameListMap() {
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		
		List<WareHouseProf>  all =this.findAllEntityList();
		if(all!=null) {
			for(WareHouseProf wh : all) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", wh.getId());
				m.put("name", wh.getWhName());
				res.add(m);
			}
		}
		
		return res;
	}

	@Override
	public WareHouseProf getEntityByWhId(String whId) {
		WareHouseProf w =this.getEntityByJPQL("SELECT w FROM WareHouseProf w WHERE w.whId = ?", whId);
		return w;
	}

	@Override
	public List<Map<String, Object>> getAllwhCodeAndNameListMap() {
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		
		List<WareHouseProf>  all =this.findAllEntityList();
		if(all!=null) {
			for(WareHouseProf wh : all) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", wh.getWhId());
				m.put("name", wh.getWhName());
				res.add(m);
			}
		}
		
		return res;
	}
	
	@Override
	public List<Map<String, Object>> getAllWhIdList() {
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		
		
		StringBuffer sql = new StringBuffer();
		sql.append("	SELECT                                                  ");
		sql.append("		wh.wh_id as wh_id,                                   ");
		sql.append("		wh.wh_name as wh_name                               ");
		sql.append("	                                                        ");
		sql.append("	                                                        ");
		sql.append("	FROM warehouse_profile wh        ");
		sql.append("	WHERE 1=1                                               ");
		sql.append("	GROUP BY wh.wh_id,wh.wh_name;                            ");
		
		return this.findListMapBySQL_map(sql.toString(), params);
		
	}

}
