package tw.com.ezlifetech.ezReco.respository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.bean.NewsHtBean;
import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.NewsHt;
import tw.com.ezlifetech.ezReco.respository.NewsHtRepository;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Repository
public class JpaNewsHtRepository extends JpaGenericRepository<NewsHt, String> implements NewsHtRepository {

	@Override
	public List<Map<String, Object>> ajaxHtGridList(NewsHtBean bean) {
		Map<String,Object> params = new HashMap<String,Object>();

		StringBuffer sql = new StringBuffer();
		sql.append("        SELECT                                     ");
		sql.append("        	ht.id as id,                           ");
		sql.append("        	ht.news_type as news_type,             ");
		sql.append("        	ht.title as title,                     ");
		sql.append("            ht.content as content,                 ");
		sql.append("        	ht.title_en as title_en,                     ");
		sql.append("            ht.content_en as content_en,                 ");
		sql.append("        	ht.status as status,                   ");
		sql.append("        	ht.show_time_s as show_time_s,         ");
		sql.append("        	ht.show_time_e as show_time_e,         ");
		sql.append("        	ht.active_time_s as active_time_s,     ");
		sql.append("        	ht.active_time_e as active_time_e,     ");
		sql.append("        	ht.group_type as group_type,     	   ");
		sql.append("        	ht.html as html,                       ");
		sql.append("        	ht.contact as contact,                 ");
		sql.append("        	ht.email as email,                     ");
		sql.append("        	ht.tel as tel                          ");
		sql.append("        FROM news_ht ht        					   ");
		sql.append("        WHERE 1=1                                  ");


		if(StringUtil.isNotBlank(bean.getTitle())) {
			params.put("title", bean.getTitle());
			sql.append("        AND ht.title = :title                  ");
		}
		if(StringUtil.isNotBlank(bean.getNewsType())) {
			params.put("newsType", bean.getNewsType());
			sql.append("        AND ht.news_type = :newsType           ");
		}
		if(StringUtil.isNotBlank(bean.getActiveTimeS())) {
			params.put("activeTimeS", bean.getActiveTimeS());
			sql.append("        AND ht.active_time_s >= :activeTimeS   ");
		}
		if(StringUtil.isNotBlank(bean.getActiveTimeE())) {
			params.put("activeTimeE", bean.getActiveTimeE());
			sql.append("        AND ht.active_time_e <= :activeTimeE   ");
		}
		
		sql.append("        Order by ht.create_time desc                 ");
		
		return this.findListMapBySQL_map(sql.toString(), params);
	}
}
