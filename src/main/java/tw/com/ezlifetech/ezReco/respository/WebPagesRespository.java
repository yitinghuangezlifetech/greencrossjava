package tw.com.ezlifetech.ezReco.respository;

import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.model.WebPages;


public interface WebPagesRespository  extends GenericRepository<WebPages, String>{
	
	public Long getNextWebPagesSeq();

}
