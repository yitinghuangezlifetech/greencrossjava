package tw.com.ezlifetech.ezReco.respository;

import java.util.List;
import java.util.Map;

import tw.com.ezlifetech.ezReco.bean.NewsHtBean;
import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.model.NewsHt;

public interface NewsHtRepository extends GenericRepository<NewsHt, String>{

	/**
	 * 查詢最新消息
	 * @param bean
	 * @return
	 */
	List<Map<String, Object>> ajaxHtGridList(NewsHtBean bean);
}
