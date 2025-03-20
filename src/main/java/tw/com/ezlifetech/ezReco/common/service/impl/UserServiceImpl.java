package tw.com.ezlifetech.ezReco.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.ezlifetech.ezReco.common.service.UserService;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.model.User;
import tw.com.ezlifetech.ezReco.respository.ProfFunctionRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private ProfFunctionRepository profFunctionRepository;
	
	
	@Override
	public List<Map<String, Object>> getUserFunctionList(UserDto userDto, String methodName, String roleType, String roleNo) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, StringType> scalarMap = new HashMap<String, StringType>();
		StringBuffer sql = new StringBuffer();
		/*sql.append("select pf.func_id, pf.func_uri, pf.func_name, pf.parent_id, '' authority, '' role_type, restriction,");
		sql.append("to_char(level - 1) as level_, ");
		sql.append("decode(pf.sort_index, null, pf.func_id, lpad(pf.sort_index, 2, '0')) sort_index ");
		sql.append("from prof_function pf ");
		sql.append("where pf.use_state = 'Y' and pf.func_id <> '000000' ");
		sql.append("start with pf.func_id = '000000' ");
		sql.append("connect by prior pf.func_id = pf.parent_id ");
		sql.append("order by pf.func_id ");*/ // Oracle 
		sql.append("(                                                          ");
		sql.append("	WITH RECURSIVE cte AS (                                ");
		sql.append("	   SELECT                                              ");
		sql.append("		func_id,                                           ");
		sql.append("		func_uri,                                          ");
		sql.append("		func_name,                                         ");
		sql.append("		func_pict,                                         ");
		sql.append("		parent_id,                                         ");
		sql.append("		'' authority,                                      ");
		sql.append("		'' role_type,'' restriction,                          ");
		sql.append("		1 AS level_ ,                                       ");
		sql.append("		(                                                  ");
		sql.append("			CASE WHEN                                      ");
		sql.append("				sort_index IS NULL THEN func_id            ");
		sql.append("			ELSE                                           ");
		sql.append("				lpad(sort_index,2,'0')                     ");
		sql.append("			END                                            ");
		sql.append("		) sort_index                                       ");
		sql.append("	   FROM   prof_function                                ");
		sql.append("	   WHERE parent_id = '000000'                          ");
		sql.append("             and use_state = 'Y'                           ");
		sql.append("	   UNION  ALL                                          ");
		sql.append("                                                           ");
		sql.append("	   SELECT                                              ");
		sql.append("		e.func_id,                                         ");
		sql.append("		e.func_uri,                                        ");
		sql.append("		e.func_name,                                       ");
		sql.append("		e.func_pict,                                       ");
		sql.append("		e.parent_id,                                       ");
		sql.append("		'' authority,                                      ");
		sql.append("		'' role_type,                                      ");
		sql.append("		'' restriction,                                     ");
		sql.append("		c.level_ + 1,                                       ");
		sql.append("		(                                                  ");
		sql.append("			CASE WHEN                                      ");
		sql.append("				e.sort_index IS NULL THEN e.func_id        ");
		sql.append("			ELSE                                           ");
		sql.append("				lpad(e.sort_index,2,'0')                   ");
		sql.append("			END                                            ");
		sql.append("		) sort_index                                       ");
		sql.append("	   FROM   cte c                                        ");
		sql.append("	   JOIN   prof_function e ON e.parent_id = c.func_id   ");
		sql.append("       Where e.use_state = 'Y'                           ");
		sql.append("	   )                                                   ");
		sql.append("	SELECT *                                               ");
		sql.append("	FROM   cte                                             ");
		sql.append(")                                                          ");
		sql.append("ORDER BY func_id                                          ");
		
		List<Map<String, Object>> funcList = profFunctionRepository.findBySQLForPlaceholder(sql.toString(), scalarMap, params);
		String index1 = "";
		String index2 = "";
		for (Map<String, Object> map : funcList) {
			if (map.get("level_").toString().equals("1")) {
				index1 = map.get("sort_index").toString();
			}
			if (map.get("level_").toString().equals("2")) {
				index2 = map.get("sort_index").toString();
				map.put("sort_index", index1 + index2);
			}
			if (map.get("level_").toString().equals("3")) {
				map.put("sort_index", index1 + index2 +  map.get("sort_index").toString());
			}
		}
		Map<String, Map<String, Object>> mmap = new HashMap<String, Map<String, Object>>();
		for (Map<String, Object> map : funcList) {
			mmap.put(map.get("sort_index").toString(), map);
		}
		funcList = new ArrayList<Map<String, Object>>();
		TreeMap<String, Map<String, Object>> treeMap = new TreeMap<String, Map<String, Object>>(mmap);
		for (Map.Entry<String, Map<String, Object>> entry : treeMap.entrySet()) {
			System.out.println(">>>>>>KEY:"+entry.getKey());
			funcList.add(entry.getValue());
		}
		
		sql.setLength(0);
		
		sql.append(" (                                                        ");
		sql.append(" 	WITH RECURSIVE cte AS (                               ");
		sql.append(" 	   SELECT                                             ");
		sql.append(" 		func_id,                                          ");
		sql.append(" 		func_uri,                                         ");
		sql.append(" 		func_name,                                        ");
		sql.append("		func_pict,                                         ");
		sql.append(" 		parent_id,                                        ");
		sql.append(" 		fr.authority,                                     ");
		sql.append(" 		'' restriction                                       ");
		sql.append(" 	   FROM   prof_function pf                            ");
		sql.append(" 		left join                                         ");
		sql.append("         ref_function_role fr                             ");
		sql.append("             on fr.func_no = pf.func_no                   ");
		sql.append(" 	   WHERE parent_id = '000000' and pf.func_no in(      ");
		sql.append(" 	   	select                                            ");
		sql.append("                 r.func_no                                ");
		sql.append("             from                                         ");
		sql.append("                 ref_function_role r                      ");
		sql.append("             where                                        ");
		sql.append("                 r.role_type = :roleType                  ");
		sql.append("                 and r.role_no = :roleNo                  ");
		sql.append(" 	   )                                                  ");
		sql.append("             and pf.use_state = 'Y'                       ");
		sql.append(" 	   UNION  ALL                                         ");
		sql.append("                                                          ");
		sql.append(" 	   SELECT                                             ");
		sql.append(" 		e.func_id,                                        ");
		sql.append(" 		e.func_uri,                                       ");
		sql.append(" 		e.func_name,                                      ");
		sql.append("		e.func_pict,                                       ");
		sql.append(" 		e.parent_id,                                      ");
		sql.append(" 		fr2.authority,                                    ");
		sql.append(" 		'' restriction                                     ");
		sql.append(" 	   FROM   cte c                                       ");
		sql.append(" 	   JOIN   prof_function e ON e.parent_id = c.func_id  ");
		sql.append("       left join                                          ");
		sql.append("         ref_function_role fr2                            ");
		sql.append("             on fr2.func_no = e.func_no                   ");    
		sql.append("       Where e.use_state = 'Y'   and e.func_no in(        ");
		sql.append(" 	   	select                                            ");
		sql.append("                 r.func_no                                ");
		sql.append("             from                                         ");
		sql.append("                 ref_function_role r                      ");
		sql.append("             where                                        ");
		sql.append("                 r.role_type = :roleType                  ");
		sql.append("                 and r.role_no = :roleNo                  ");
		sql.append(" 	   )                                                  ");
		sql.append(" 	   )                                                  ");
		sql.append(" 	SELECT distinct *                                     ");
		sql.append(" 	FROM   cte pf                                         ");
		sql.append(" 	                                                      ");
		sql.append(" )                                                        ");
		sql.append(" ORDER BY func_id                                         ");
		
		/*sql.append("select distinct pf.func_id, pf.func_uri, pf.func_name, pf.parent_id, fr.authority, restriction ");
		sql.append("from prof_function pf ");
		sql.append("left join ref_function_role fr on fr.func_no = pf.func_no ");
		sql.append("where pf.use_state = 'Y' and pf.func_id <> '000000' ");
		sql.append("start with pf.func_no in ");
		sql.append("(select r.func_no ");
		sql.append("from ref_function_role r ");
		sql.append("where r.role_type = :roleType and r.role_no = :roleNo) ");
		sql.append("connect by prior pf.parent_id = pf.func_id ");
		sql.append("order by pf.func_id ");*/ // Oracle
		if (methodName.equals("getUserMenuTree") || methodName.equals("roleSetEdit")) {
			params.put("roleType", userDto.getRoleType());
			params.put("roleNo", userDto.getRoleNo());
		}
		if (methodName.equals("roleFunSetQuery")) {
			params.put("roleType", roleType);
			params.put("roleNo", roleNo);
		}
		List<Map<String, Object>> roleList = profFunctionRepository.findBySQLForPlaceholder(sql.toString(), scalarMap, params);
		
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
		if (methodName.equals("getUserMenuTree") || methodName.equals("roleSetEdit")) {
			
			/*
			sql.append("select distinct pf.func_id, pf.func_uri, pf.func_name, pf.parent_id, fu.authority ,restriction ");
			sql.append("from prof_function pf ");
			sql.append("left join ref_function_user fu on fu.func_no = pf.func_no ");
			sql.append("where pf.use_state = 'Y' and pf.func_id <> '000000' ");
			sql.append("start with pf.func_no in ");
			sql.append("(select u.func_no ");
			sql.append("from ref_function_user u ");
			sql.append("where u.user_id = :userId) ");
			sql.append("connect by prior pf.parent_id = pf.func_id ");
			sql.append("order by pf.func_id ");*/
			sql.setLength(0);
			sql.append(" (                                                       ");
			sql.append(" 	WITH RECURSIVE cte AS (                              ");
			sql.append(" 	   SELECT                                            ");
			sql.append(" 		func_id,                                         ");
			sql.append(" 		func_uri,                                        ");
			sql.append(" 		func_name,                                       ");
			sql.append("		func_pict,                                         ");
			sql.append(" 		parent_id,                                       ");
			sql.append(" 		fu.authority,                                    ");
			sql.append(" 		'' restriction                                      ");
			sql.append(" 	   FROM   prof_function pf                           ");
			sql.append(" 		 left join                                       ");
			sql.append("         ref_function_user fu                            ");
			sql.append("             on fu.func_no = pf.func_no                  ");
			sql.append(" 	   WHERE parent_id = '000000' and pf.func_no in(     ");
			sql.append(" 	   	select                                           ");
			sql.append("                 u.func_no                               ");
			sql.append("             from                                        ");
			sql.append("                 ref_function_user u                     ");
			sql.append("             where                                       ");
			sql.append("                 u.user_id = :userId                     ");
			sql.append(" 	   )  and  pf.use_state = 'Y'                        ");
			sql.append("                                                         ");
			sql.append(" 	   UNION  ALL                                        ");
			sql.append("                                                         ");
			sql.append(" 	   SELECT                                            ");
			sql.append(" 		e.func_id,                                       ");
			sql.append(" 		e.func_uri,                                      ");
			sql.append(" 		e.func_name,                                     ");
			sql.append("		e.func_pict,                                       ");
			sql.append(" 		e.parent_id,                                     ");
			sql.append(" 		fu2.authority,                                   ");
			sql.append(" 		'' restriction                                    ");
			sql.append(" 	   FROM   cte c                                      ");
			sql.append(" 	   JOIN   prof_function e ON e.parent_id = c.func_id ");
			sql.append(" 		left join                                        ");
			sql.append(" 				ref_function_user fu2                    ");
			sql.append(" 					on fu2.func_no = e.func_no           ");
			sql.append("       Where e.use_state = 'Y'                           ");
			sql.append(" 	   )                                                 ");
			sql.append(" 	SELECT distinct *                                    ");
			sql.append(" 	FROM   cte pf                                        ");
			sql.append(" 	                                                     ");
			sql.append(" )                                                       ");
			sql.append(" ORDER BY func_id;                                       ");
			params.clear();
			params.put("userId", userDto.getId());
			userList = profFunctionRepository.findBySQLForPlaceholder(sql.toString(), scalarMap, params);
		}
		
		for (Map<String, Object> map : funcList) {
			for (Map<String, Object> m : roleList) {
				if (map.get("func_id").equals(m.get("func_id"))) {
					map.put("authority", m.get("authority"));
					map.put("restriction",map.get("restriction")==null?"":map.get("restriction"));
					map.put("role_type", "C");
				}
			}
			if (methodName.equals("getUserMenuTree") || methodName.equals("roleSetEdit")) {
				for (Map<String, Object> m : userList) {
					if (map.get("func_id").equals(m.get("func_id")) && (map.get("role_type") == null || map.get("role_type").equals(""))) {
						map.put("authority", m.get("authority"));
						map.put("restriction",map.get("restriction")==null?"":map.get("restriction"));
						map.put("role_type", "U");
					}
				}
			}
			list.add(map);
		}
		return list;
	}

}
