package tw.com.ezlifetech.ezReco.respository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.WebPages;
import tw.com.ezlifetech.ezReco.respository.WebPagesRespository;


@Repository
public class JpaWebPagesRespository extends JpaGenericRepository<WebPages, String> implements WebPagesRespository{

	@Override
	public Long getNextWebPagesSeq() {
		return convert2Long(getBySQL("select nextval('web_pages_seq')"));
	}
	
}	
