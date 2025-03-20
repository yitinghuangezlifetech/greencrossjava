package tw.com.ezlifetech.ezReco.respository;

import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.model.Product;


public interface ProductRespository  extends GenericRepository<Product, String>{
	public Long getNextProductSeq();

	public String getProdMainPictByProdId(String id);
}
