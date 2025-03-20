package tw.com.ezlifetech.ezReco.respository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.dto.RefCompWarehouseDto;
import tw.com.ezlifetech.ezReco.dto.WareHouseProfDlDto;
import tw.com.ezlifetech.ezReco.dto.WareHouseProfDto;
import tw.com.ezlifetech.ezReco.model.WareHouseProfDl;
import tw.com.ezlifetech.ezReco.respository.WareHouseProfDlRespository;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Repository
public class JpaWareHouseProfDlRespository extends JpaGenericRepository<WareHouseProfDl, String> implements WareHouseProfDlRespository {

	@Override
	public List<Map<String, Object>> ajaxDlGridList(WareHouseProfDlDto dto) {
		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtil.isBlank(dto.getTopId())) {
			return new ArrayList<Map<String, Object>>() ;
		}
		params.put("id", dto.getTopId());
		StringBuffer sql = new StringBuffer();
		sql.append("        SELECT                                                                ");
		sql.append("        	dl.id as id,                                                      ");
		sql.append("        	dl.wh_id as wh_id,                                    ");
		sql.append("        	ht.wh_name as wh_name,                                    ");
		sql.append("        	dl.top_id as top_id,                                       ");
		sql.append("        	dl.dl_id as dl_id,                                       ");
		sql.append("        	dl.dl_name as dl_name,                                       ");
		sql.append("        	dl.create_user as create_user,                                    ");
		sql.append("        	to_char(dl.create_time,'YYYY/MM/DD HH24:MI:SS') as create_time,   ");
		sql.append("        	dl.update_user as update_user,                                    ");
		sql.append("        	to_char(dl.update_time,'YYYY/MM/DD HH24:MI:SS') as update_time    ");
		sql.append("        FROM warehouse_profile_dl dl , warehouse_profile ht                ");
		
		sql.append("        WHERE 1=1                                                             ");
		sql.append("        AND ht.id = dl.top_id                                           ");
		sql.append("        AND dl.top_id = :id                                            ");
		
		return this.findListMapBySQL_map(sql.toString(), params);
	}

	@Override
	public List<Map<String, Object>> getLogistcDlSelectList(WareHouseProfDlDto dto) {
		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtil.isBlank(dto.getTopId())) {
			return new ArrayList<Map<String, Object>>() ;
		}
		params.put("id", dto.getTopId());
		StringBuffer sql = new StringBuffer();
		sql.append("        SELECT                                                                ");
		sql.append("        	dl.dl_id as dl_id,                                       ");
		sql.append("        	dl.dl_name as dl_name                                       ");
		sql.append("        FROM warehouse_profile_dl dl                 ");
		sql.append("        WHERE 1=1                                                             ");
		sql.append("        AND dl.top_id = :id                                            ");
		
		return this.findListMapBySQL_map(sql.toString(), params);
	}

}
