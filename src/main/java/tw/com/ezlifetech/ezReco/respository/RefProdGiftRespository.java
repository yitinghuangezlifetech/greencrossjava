package tw.com.ezlifetech.ezReco.respository;

import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.model.RefProdGift;

public interface RefProdGiftRespository extends GenericRepository<RefProdGift, String>{

	Long getNextProductGiftSeq();

}
