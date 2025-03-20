package tw.com.ezlifetech.ezReco.respository;

import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.model.ProductSale;

public interface ProductSaleRespository extends GenericRepository<ProductSale, String>{

	Long getNextProductSaleSeq();

}
