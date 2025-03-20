package tw.com.ezlifetech.ezReco.respository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftDlDto;
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftHtDto;
import tw.com.ezlifetech.ezReco.model.WorkClassShiftDl;
import tw.com.ezlifetech.ezReco.respository.WorkClassShiftDlRespository;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Repository
public class JpaWorkClassShiftDlRespository  extends JpaGenericRepository<WorkClassShiftDl, String> implements WorkClassShiftDlRespository {

	@Override
	public List<Map<String, Object>> ajaxDlGridList(WorkClassShiftHtDto dto) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(StringUtil.isBlank(dto.getId())) {
			return new ArrayList<Map<String, Object>>() ;
		}
		
		params.put("htId",dto.getId());
		
		StringBuffer sql = new StringBuffer();
		sql.append("    SELECT                                                               ");
		sql.append("    	id as id,                                                        ");
		sql.append("    	ht_id as ht_id,                                                  ");
		sql.append("    	class_name as class_name,                                        ");
		sql.append("        contract_type as contract_type,                                  ");
		sql.append("    	sdate_time as sdate_time,                                        ");
		sql.append("    	edate_time as edate_time,                                        ");
		sql.append("        remuneration as remuneration,                                    ");
		sql.append("        extend_in as extend_in,                                          ");
		sql.append("        extend_out as extend_out,                                        ");
		sql.append("        night_add as night_add,                                          ");
		sql.append("        kpi as kpi,                                                      ");
		sql.append("    	salary as salary,                                                ");
		sql.append("    	unit as unit,                                                    ");
		sql.append("        is_main as is_main,                                               ");
		sql.append("    	create_user as create_user,                                      ");
		sql.append("    	to_char(create_time,'YYYY/MM/DD HH24:MI:SS') as create_time,     ");
		sql.append("    	update_user as update_user,                                      ");
		sql.append("    	to_char(update_time,'YYYY/MM/DD HH24:MI:SS') as update_time      ");
		sql.append("                                                                         ");
		sql.append("    FROM work_class_shift_dl                                             ");
		sql.append("    WHERE 1=1                                                            ");
		sql.append("    AND ht_id = :htId                                                   ");
		
		return this.findListMapBySQL_map(sql.toString(), params);
	}

	@Override
	public List<WorkClassShiftDl> findEntityByHtId(WorkClassShiftDlDto dto) {
		// TODO Auto-generated method stub
		return this.findEntityListByJPQL("SELECT d FROM WorkClassShiftDl d WHERE d.htId =? ", dto.getHtId());
	}

	
}
