package tw.com.ezlifetech.ezReco.respository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.dto.RefCompWarehouseDto;
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftHtDto;
import tw.com.ezlifetech.ezReco.model.WorkClassShiftHt;
import tw.com.ezlifetech.ezReco.respository.WorkClassShiftHtRespository;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Repository
public class JpaWorkClassShiftHtRespository  extends JpaGenericRepository<WorkClassShiftHt, String> implements WorkClassShiftHtRespository {

	@Override
	public List<Map<String, Object>> ajaxHtGridList(WorkClassShiftHtDto dto) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(StringUtil.isBlank(dto.getOwnerId())) {
			return new ArrayList<Map<String, Object>>() ;
		}
		params.put("ownerId", dto.getOwnerId());
		StringBuffer sql = new StringBuffer();
		sql.append("        SELECT                                                                ");
		sql.append("        	ht.id as id,                                                      ");
		sql.append("        	ht.config_name as config_name,                                    ");
		sql.append("        	comp.com_name as comp_name,                                       ");
		sql.append("            ht.status as status,                                              ");
		sql.append("        	ht.create_user as create_user,                                    ");
		sql.append("        	to_char(ht.create_time,'YYYY/MM/DD HH24:MI:SS') as create_time,   ");
		sql.append("        	ht.update_user as update_user,                                    ");
		sql.append("        	to_char(ht.update_time,'YYYY/MM/DD HH24:MI:SS') as update_time    ");
		sql.append("        FROM work_class_shift_ht ht left join com_prof_ht comp                ");
		sql.append("        ON ht.comp_id = comp.com_id                                           ");
		sql.append("        WHERE 1=1                                                             ");
		sql.append("        AND ht.owner_id = :ownerId                                            ");
		sql.append("        AND ht.status != 'D'                                                  ");
		return this.findListMapBySQL_map(sql.toString(), params);
	}
	
	@Override
	public List<Map<String, Object>> ajaxHtGridListAdmin(WorkClassShiftHtDto dto) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(!StringUtil.isBlank(dto.getOwnerId())) {
			params.put("ownerId", dto.getOwnerId());
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("        SELECT                                                                ");
		sql.append("        	ht.id as id,                                                      ");
		sql.append("        	ht.config_name as config_name,                                    ");
		sql.append("        	comp.com_name as comp_name,                                       ");
		sql.append("            ht.status as status,                                              ");
		sql.append("        	ht.create_user as create_user,                                    ");
		sql.append("        	to_char(ht.create_time,'YYYY/MM/DD HH24:MI:SS') as create_time,   ");
		sql.append("        	ht.update_user as update_user,                                    ");
		sql.append("        	to_char(ht.update_time,'YYYY/MM/DD HH24:MI:SS') as update_time    ");
		sql.append("        FROM work_class_shift_ht ht left join com_prof_ht comp                ");
		sql.append("        ON ht.comp_id = comp.com_id                                           ");
		sql.append("        WHERE 1=1                                                             ");
		if(params.get("ownerId")!=null)
			sql.append("        AND ht.owner_id = :ownerId                                            ");
		sql.append("        AND ht.status != 'D'                                                  ");
		return this.findListMapBySQL_map(sql.toString(), params);
	}

	@Override
	public List<Map<String, Object>> getLogistcWcsList(RefCompWarehouseDto dto) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(StringUtil.isBlank(dto.getComId())) {
			return new ArrayList<Map<String, Object>>() ;
		}
		params.put("comp_id", dto.getComId());
		StringBuffer sql = new StringBuffer();
		sql.append("	SELECT                                  ");
		sql.append("		id as id,                           ");
		sql.append("		config_name as config_name          ");
		sql.append("		                                    ");
		sql.append("	                                        ");
		sql.append("	                                        ");
		sql.append("	FROM work_class_shift_ht                ");
		sql.append("	WHERE 1=1                               ");
		sql.append("    AND status = 'A'                        ");
		sql.append("	AND comp_id = :comp_id                  ");
		
		
		return this.findListMapBySQL_map(sql.toString(), params);
	}

}
