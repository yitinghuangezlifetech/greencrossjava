package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.ProductSale;
import tw.com.ezlifetech.ezReco.respository.ProductSaleRespository;

@Repository
public class JpaProductSaleRespository extends JpaGenericRepository<ProductSale, String> implements ProductSaleRespository{

	@Override
	public Long getNextProductSaleSeq() {
		return convert2Long(getBySQL("select nextval('product_sale_seq')"));
	}

}
