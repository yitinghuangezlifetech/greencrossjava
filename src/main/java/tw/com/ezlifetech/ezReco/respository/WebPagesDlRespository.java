package tw.com.ezlifetech.ezReco.respository;

import tw.com.ezlifetech.ezReco.bean.WebPagesBean;
import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.model.WebPagesDl;


public interface WebPagesDlRespository  extends GenericRepository<WebPagesDl, String>{
	
	public Long getNextWebPagesDlSeq();

	public Long getMaxSortNo(WebPagesBean formBean);
}
