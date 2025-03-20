package tw.com.ezlifetech.ezReco.respository;

import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.model.ProductPic;

public interface ProductPicRespository  extends GenericRepository<ProductPic, String>{

	Long getNextProductPicSeq();

}
