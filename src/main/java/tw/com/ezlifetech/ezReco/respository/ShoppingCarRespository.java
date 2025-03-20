package tw.com.ezlifetech.ezReco.respository;

import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.model.ShoppingCar;

public interface ShoppingCarRespository extends GenericRepository<ShoppingCar, String>{

	Long getNextShoppingCarSeq();

}
