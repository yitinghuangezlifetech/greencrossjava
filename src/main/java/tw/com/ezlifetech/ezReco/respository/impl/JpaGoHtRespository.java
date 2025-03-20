package tw.com.ezlifetech.ezReco.respository.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.bean.GoBean;
import tw.com.ezlifetech.ezReco.bean.GoReportBean;
import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.dto.GoHtDto;
import tw.com.ezlifetech.ezReco.model.GoHt;
import tw.com.ezlifetech.ezReco.respository.GoHtRespository;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Repository
public class JpaGoHtRespository  extends JpaGenericRepository<GoHt, String> implements GoHtRespository{

	@Override
	public List<Map<String, Object>> ajaxHtGridList(GoBean bean) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(StringUtil.isBlank(bean.getEmpId()) || StringUtil.isBlank(bean.getWhId()) || StringUtil.isBlank(bean.getComId())) {
			return new ArrayList<Map<String, Object>>() ;
		}
		params.put("empId", bean.getEmpId());
		params.put("whId", bean.getWhId());
		params.put("comId", bean.getComId());
		StringBuffer sql = new StringBuffer();
		sql.append("        SELECT                                                                ");
		sql.append("        	ht.go_id as id,                                                      ");
		sql.append("        	ht.user_id as user_id,                                    ");
		sql.append("        	ht.user_name as user_name,                                       ");
		sql.append("            ht.s_go_systime as s_go_systime,                                              ");
		sql.append("        	ht.e_go_systime as e_go_systime,                                    ");
		sql.append("            ht.s_go_time as s_go_time,                                              ");
		sql.append("        	ht.e_go_time as e_go_time,                                    ");
		sql.append("            ht.go_general_hr as go_general_hr,                                              ");
		sql.append("        	ht.go_ovt_hr as go_ovt_hr,                                    ");
		sql.append("        	ht.go_time as go_time,                                    ");
		sql.append("        	ht.contract_type  as contract_type   ");
		sql.append("        FROM go_ht ht,dispatcher_profile dp                ");
		sql.append("        WHERE 1=1                                                             ");
		sql.append("        AND ht.user_id = dp.id                                            ");

		sql.append("        AND ht.user_id = :empId                                            ");
		sql.append("        AND ht.com_id = :comId                                            ");
		sql.append("        AND ht.wh_id = :whId                                            ");
		return this.findListMapBySQL_map(sql.toString(), params);
	}
	
	@Override
	public List<Map<String, Object>> ajaxReportHtGridList(GoReportBean bean) throws ParseException{
		Map<String,Object> params = new HashMap<String,Object>();
		
//		if(StringUtil.isBlank(dto.getOwnerId())) {
//			return new ArrayList<Map<String, Object>>() ;
//		}
//		params.put("ownerId", dto.getOwnerId());
		if(!StringUtil.isBlank(bean.getUserId())) {
			params.put("userId", bean.getUserId());
		}
		if(!StringUtil.isBlank(bean.getComId())) {
			params.put("comId", bean.getComId());
		}
		if(!StringUtil.isBlank(bean.getWhId())) {
			params.put("whId", bean.getWhId());
		}
		if(!StringUtil.isBlank(bean.getsCreateTime()) && !StringUtil.isBlank(bean.geteGoSystime())) {		
			String[] start = bean.getsCreateTime().split("/");
			String dateString = start[0]+"-"+start[1]+"-"+start[2]+" 00:00:00";
			
			String[] end = bean.geteGoSystime().split("/");
			String dateString2 = end[0]+"-"+end[1]+"-"+end[2]+" 00:00:00";
			//設定日期格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//進行轉換
			Date dateS = sdf.parse(dateString);
			Date dateE = sdf.parse(dateString2);
			params.put("sTime", dateS);
			params.put("eTime", dateE);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("        SELECT                                                                ");
		//sql.append("        	ht.user_name as user_name,                                                    ");
		sql.append("        	ht.user_id as user_id,                                    ");
		sql.append("        	ht.user_name as user_name,                                       ");
		sql.append("        	wp.wh_name as wh_name,                                      ");
		sql.append("            SUM(CAST(ht.go_general_hr AS float)) as total_go_general_hr,                                              ");
		sql.append("        	SUM(CAST(ht.go_ovt_hr AS float)) as total_go_ovt_hr,                                    ");
		sql.append("            SUM(CAST(ht.go_general_sal AS float)) as total_go_general_sal,                                              ");
		sql.append("        	SUM(CAST(ht.go_ovt_sal AS float)) as total_go_ovt_sal                                   ");
		//sql.append("    CAST( EXTRACT(EPOCH FROM  SUM ( ht.e_go_systime - ht.s_go_systime ))/60/60 AS integer) AS total_time_hour, "); 
		//sql.append("    CAST(EXTRACT(MINUTE  FROM  SUM ( ht.e_go_systime - ht.s_go_systime ))AS integer) AS total_time_min                         ");

		sql.append("       FROM go_ht ht left join dispatcher_profile dp  on ht.user_id = dp.id              ");
		sql.append("        			 left join warehouse_profile wp  on ht.wh_id = wp.wh_id                                                           ");
		
		sql.append(" 		where 1 = 1   ");
		sql.append("    AND ht.go_status='E'                                                                                       ");
		if(params.get("userId")!=null)
			sql.append("  AND ht.user_id = :userId           ");	
		if(params.get("whId")!=null)
			sql.append("  AND ht.wh_id = :whId           ");	
		if(params.get("comId")!=null)
			sql.append("  AND ht.com_id = :comId           ");	
		if(params.get("userId")!=null)
			sql.append("  AND ht.user_Id = :userId           ");	
		if(params.get("sTime")!=null && params.get("eTime")!=null)
			sql.append("  AND ht.s_create_time <= :sTime      AND     ht.e_go_systime <= :eTime  ");			
//		sql.append("        AND ht.owner_id = :ownerId                                            ");
//		sql.append("        AND ht.status != 'D'                                                  ");
		sql.append("        group by ht.user_id,ht.user_name,wp.wh_name                                      ");
		return this.findListMapBySQL_map(sql.toString(), params);
	}

	@Override
	public List<Map<String, Object>> ajaxExpReportHtGridList(GoReportBean bean) throws ParseException {
		Map<String,Object> params = new HashMap<String,Object>();
		
//		if(!StringUtil.isBlank(bean.getUserId())) {
//			params.put("userId", bean.getUserId());
//		}
		if(!StringUtil.isBlank(bean.getComId())) {
			params.put("comId", bean.getComId());
		}
		if(!StringUtil.isBlank(bean.getWhId())) {
			params.put("whId", bean.getWhId());
		}
		if(!StringUtil.isBlank(bean.getsCreateTime()) && !StringUtil.isBlank(bean.geteGoSystime())) {		
			String[] start = bean.getsCreateTime().split("/");
			String dateString = start[0]+"-"+start[1]+"-"+start[2]+" 00:00:00";
			
			String[] end = bean.geteGoSystime().split("/");
			String dateString2 = end[0]+"-"+end[1]+"-"+end[2]+" 00:00:00";
			//設定日期格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//進行轉換
			Date dateS = sdf.parse(dateString);
			Date dateE = sdf.parse(dateString2);
			params.put("sTime", dateS);
			params.put("eTime", dateE);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("        SELECT                                                                ");
		sql.append("        	SUM(CAST(ht.go_salary AS INT)) as totalSalary,                                                    ");
		sql.append("        	ht.user_id as user_id,                                    ");
		sql.append("        	ht.user_name as user_name,                                       ");
		sql.append("        	wp.wh_name as wh_name,                                      ");
		sql.append("        	cph.com_name as com_name,                                      ");
		sql.append("        	wcsd.salary as salary,                                      ");
		sql.append("    CAST( EXTRACT(EPOCH FROM  SUM ( ht.e_go_systime - ht.s_go_systime ))/60/60 AS integer) AS total_time_hour, "); 
		sql.append("    CAST(EXTRACT(MINUTE  FROM  SUM ( ht.e_go_systime - ht.s_go_systime ))AS integer) AS total_time_min                         ");

		sql.append("       FROM go_ht ht left join dispatcher_profile dp  on ht.user_id = dp.id              ");
		sql.append("        			 left join warehouse_profile wp  on ht.wh_id = wp.wh_id                                                           ");
		sql.append("        			 left join com_prof_ht cph  on dp.comp_id = cph.com_code    ");
		sql.append("        			 left join ref_comp_warehouse rcw  on ht.com_id = rcw.com_id and ht.wh_id = rcw.wh_id   ");
		sql.append("        			 left join work_class_shift_dl wcsd  on rcw.wcs_id = wcsd.ht_id    ");
		sql.append(" 		where 1 = 1   ");
		sql.append("    AND ht.go_status='E'  AND  wcsd.is_main = 'Y'                                                                                   ");
		if(params.get("userId")!=null)
			sql.append("  AND ht.user_id = :userId           ");	
		if(params.get("whId")!=null)
			sql.append("  AND ht.wh_id = :whId           ");	
		if(params.get("comId")!=null)
			sql.append("  AND ht.com_id = :comId           ");	
		if(params.get("userId")!=null)
			sql.append("  AND ht.user_Id = :userId           ");	
		if(params.get("sTime")!=null && params.get("eTime")!=null)
			sql.append("  AND ht.s_create_time >= :sTime      AND     ht.e_go_systime <= :eTime  ");			
//		sql.append("        AND ht.owner_id = :ownerId                                            ");
//		sql.append("        AND ht.status != 'D'                                                  ");
		sql.append("        group by ht.user_id,ht.user_name,wp.wh_name,cph.com_name ,salary                                    ");
		return this.findListMapBySQL_map(sql.toString(), params);
	}	

	@Override
	public Map<String, Object> ajaxExpReportHt(GoReportBean bean) throws ParseException {
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(!StringUtil.isBlank(bean.getComId())) {
			params.put("comId", bean.getComId());
		}
		if(!StringUtil.isBlank(bean.getWhId())) {
			params.put("whId", bean.getWhId());
		}
		if(!StringUtil.isBlank(bean.getsCreateTime()) && !StringUtil.isBlank(bean.geteGoSystime())) {		
			String[] start = bean.getsCreateTime().split("/");
			String dateString = start[0]+"-"+start[1]+"-"+start[2]+" 00:00:00";
			
			String[] end = bean.geteGoSystime().split("/");
			String dateString2 = end[0]+"-"+end[1]+"-"+end[2]+" 00:00:00";
			//設定日期格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//進行轉換
			Date dateS = sdf.parse(dateString);
			Date dateE = sdf.parse(dateString2);
			params.put("sTime", dateS);
			params.put("eTime", dateE);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("        SELECT                                                                ");
		sql.append("        	SUM(CAST(ht.go_general_sal AS iNT)) as generalSal,                                                    ");
		sql.append("        	SUM(CAST(ht.go_general_hr AS float)) as generalHr,        ");
		sql.append("        	SUM(CAST(ht.go_ovt_hr AS float)) as ovtHr,        ");
		sql.append("        	SUM(CAST(ht.go_ovt_sal AS float)) as ovtSal,        ");
		//sql.append("        	ht.user_id as user_id,                                    ");
		//sql.append("        	ht.user_name as user_name,                                       ");
		sql.append("        	wp.wh_name as wh_name,                                      ");
		sql.append("        	cph.com_name as com_name,                                      ");
		sql.append("        	wcsd.salary as salary                                      ");
		//sql.append("    CAST( EXTRACT(EPOCH FROM  SUM ( ht.e_go_systime - ht.s_go_systime ))/60/60 AS integer) AS total_time_hour, "); 
		//sql.append("    CAST(EXTRACT(MINUTE  FROM  SUM ( ht.e_go_systime - ht.s_go_systime ))AS integer) AS total_time_min                         ");

		sql.append("       FROM go_ht ht left join dispatcher_profile dp  on ht.user_id = dp.id              ");
		sql.append("        			 left join warehouse_profile wp  on ht.wh_id = wp.wh_id                                                           ");
		sql.append("        			 left join com_prof_ht cph  on dp.comp_id = cph.com_code    ");
		sql.append("        			 left join ref_comp_warehouse rcw  on ht.com_id = rcw.com_id and ht.wh_id = rcw.wh_id   ");
		sql.append("        			 left join work_class_shift_dl wcsd  on rcw.wcs_id = wcsd.ht_id    ");
		sql.append(" 		where 1 = 1   ");
		sql.append("    AND ht.go_status='E'  AND  wcsd.is_main = 'Y'                                                                                   ");
		if(params.get("userId")!=null)
			sql.append("  AND ht.user_id = :userId           ");	
		if(params.get("whId")!=null)
			sql.append("  AND ht.wh_id = :whId           ");	
		if(params.get("comId")!=null)
			sql.append("  AND ht.com_id = :comId           ");	
		if(params.get("userId")!=null)
			sql.append("  AND ht.user_Id = :userId           ");	
		if(params.get("sTime")!=null && params.get("eTime")!=null)
			sql.append("  AND ht.s_create_time >= :sTime      AND     ht.e_go_systime <= :eTime  ");			
//		sql.append("        AND ht.owner_id = :ownerId                                            ");
//		sql.append("        AND ht.status != 'D'                                                  ");
		sql.append("        group by wp.wh_name,cph.com_name ,salary                                    ");
		return this.getMapBySQL_map(sql.toString(), params);	
	}
	
	@Override
	public GoHt getUserGo(GoHtDto dto) {

		
		if(StringUtil.isBlank(dto.getUserId())) {
			return null ;
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("        SELECT                                                        ");
		sql.append("        	*                                                         ");
		sql.append("        FROM go_ht ht                                                 ");
		sql.append("        WHERE 1=1                                                     ");
		sql.append("        AND ht.user_id = ?                                            ");
		sql.append("        AND date(ht.update_time) = current_date                       ");
		sql.append("        AND ht.go_status not in('E','A')      ");

		return this.getEntityBySQL(sql.toString(),dto.getUserId());
		
	}

}
