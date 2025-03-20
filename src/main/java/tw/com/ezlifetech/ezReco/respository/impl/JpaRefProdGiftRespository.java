package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.RefProdGift;
import tw.com.ezlifetech.ezReco.respository.RefProdGiftRespository;

@Repository
public class JpaRefProdGiftRespository extends JpaGenericRepository<RefProdGift, String> implements RefProdGiftRespository{

	@Override
	public Long getNextProductGiftSeq() {
		return convert2Long(getBySQL("select nextval('prod_gift_seq')"));
	}

}
