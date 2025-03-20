package tw.com.ezlifetech.ezReco.respository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.bean.WebPagesBean;
import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.WebPagesDl;
import tw.com.ezlifetech.ezReco.respository.WebPagesDlRespository;
import tw.com.ezlifetech.ezReco.util.StringUtil;


@Repository
public class JpaWebPagesDlRespository extends JpaGenericRepository<WebPagesDl, String> implements WebPagesDlRespository{

	@Override
	public Long getNextWebPagesDlSeq() {
		return convert2Long(getBySQL("select nextval('web_pages_dl_seq')"));
	}
	
	@Override
	public Long getMaxSortNo(WebPagesBean formBean) {
		Map<String,Object> params = new HashMap<String,Object>();
		long SortNo = 0;
		if(!StringUtil.isBlank(formBean.getPageId())) {
			params.put("page_id",formBean.getPageId());
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select max(page_Sort_No) ");
		sql.append(" from web_pages_dl where 1 = 1 ");
		if(params.get("page_id")!=null)
			sql.append("  and page_id = '"+formBean.getPageId()+"'");
		System.out.println(getBySQL(sql.toString()));
		if(null!=getBySQL(sql.toString())) {
			SortNo = Long.parseLong(getBySQL(sql.toString()).toString());
		}
		return SortNo;
	}
}	
