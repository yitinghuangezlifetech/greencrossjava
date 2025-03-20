package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.ProductPic;
import tw.com.ezlifetech.ezReco.respository.ProductPicRespository;

@Repository
public class JpaProductPicRespository extends JpaGenericRepository<ProductPic, String> implements ProductPicRespository{

	@Override
	public Long getNextProductPicSeq() {
		return convert2Long(getBySQL("select nextval('product_pic_seq')"));
	}

}
