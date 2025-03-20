package tw.com.ezlifetech.ezReco.respository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.dto.RefCompWarehouseDto;
import tw.com.ezlifetech.ezReco.model.ComProfHt;
import tw.com.ezlifetech.ezReco.model.RefCompWarehouse;
import tw.com.ezlifetech.ezReco.model.WareHouseProf;
import tw.com.ezlifetech.ezReco.respository.ComProfHtRespository;
import tw.com.ezlifetech.ezReco.respository.RefCompWarehouseRespository;
import tw.com.ezlifetech.ezReco.respository.WareHouseProfDlRespository;
import tw.com.ezlifetech.ezReco.respository.WareHouseProfRespository;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Repository
public class JpaRefCompWarehouseRespository extends JpaGenericRepository<RefCompWarehouse, String> implements RefCompWarehouseRespository {

	@Autowired
	private WareHouseProfDlRespository wareHouseProfDlRespository;
	
	@Autowired
	private WareHouseProfRespository wareHouseProfRespository;
	
	@Autowired
	private ComProfHtRespository comProfHtRespository;
	
	@Override
	public List<Map<String, Object>> getLogistcProfGridList(RefCompWarehouseDto dto) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("one", 1);
		StringBuffer sql = new StringBuffer();
		
		sql.append("	SELECT                                                                                                                       ");
		sql.append("	                                                                                                                             ");
		sql.append("		r.id as id,                                                                                                              ");
		sql.append("		r.com_id as com_id,                                                                                                      ");
		sql.append("		cp.com_name as com_name,                                                                                                 ");
		sql.append("		r.wh_id as wh_id,                                                                                                        ");
		sql.append("		wh.wh_name as wh_name,                                                                                                   ");
		sql.append("		r.wh_dl_id as wh_dl_id,                                                                                                  ");
		sql.append("		whdl.dl_name as dl_name,                                                                                                 ");
		sql.append("		r.wcs_id as wcs_id,                                                                                                      ");
		sql.append("		wcsh.config_name as config_name,                                                                                         ");
		sql.append("	    r.strength as strength,                                                                                                   ");
		sql.append("		r.create_user as create_user,                                                                                            ");
		sql.append("		r.create_time as create_time,                                                                                            ");
		sql.append("		r.update_user as update_user,                                                                                            ");
		sql.append("		r.update_time as update_time                                                                                             ");
		sql.append("	                                                                                                                             ");
		sql.append("	                                                                                                                             ");
		sql.append("	                                                                                                                             ");
		sql.append("	                                                                                                                             ");
		sql.append("	FROM ref_comp_warehouse r , com_prof_ht cp , warehouse_profile wh ,warehouse_profile_dl whdl ,work_class_shift_ht wcsh       ");
		sql.append("	WHERE 1=:one                                                                                                                    ");
		sql.append("	AND r.com_id = cp.com_code                                                                                                   ");
		sql.append("	AND r.wh_id = wh.wh_id                                                                                                       ");
		sql.append("	AND r.wh_dl_id = whdl.dl_id                                                                                                  ");
		sql.append("	AND r.wcs_id = wcsh.id                                                                                                       ");
		
		
		
		return this.findListMapBySQL_map(sql.toString(), params);
	}

	@Override
	public boolean isLogistcRepaet(RefCompWarehouseDto dto) {
		String compCode="";
		String whId="";
		
		
		ComProfHt com = comProfHtRespository.getEntityById(dto.getComId());
		if(com!=null) {
			if(!StringUtil.isBlank(com.getComCode())) {
				compCode = com.getComCode();
			}
		}
		
		WareHouseProf  wh =wareHouseProfRespository.getEntityById(dto.getWhId());
		if(wh!=null) {
			if(!StringUtil.isBlank(wh.getWhId())) {
				whId = wh.getWhId();
			}
		}
		RefCompWarehouse e = null;
		if(StringUtil.isBlank(dto.getId())) {
			e =this.getEntityByJPQL("SELECT r FROM RefCompWarehouse  r WHERE r.comId=? AND r.whId=? AND r.whDlId=? AND r.wcsId=? ", compCode,whId,dto.getWhDlId(),dto.getWcsId());
		}else {
			e =this.getEntityByJPQL("SELECT r FROM RefCompWarehouse  r WHERE r.comId=? AND r.whId=? AND r.whDlId=? AND r.wcsId=? AND r.id!=?", compCode,whId,dto.getWhDlId(),dto.getWcsId(),dto.getId());
		}
		
		return !(e==null);
	}

	@Override
	public String  getWcsId(String comId,String whId) {
		String[] nameArray = new String[] {"comId", "whId"};
		Object[] valueArray = new Object[] {comId,whId};
		RefCompWarehouse rcw = this.getEntityByProperties(nameArray, valueArray);
		if(rcw != null) { 
			return rcw.getWcsId();
		}else {
			return null;
		}
	}

}
