package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.ShoppingCar;
import tw.com.ezlifetech.ezReco.respository.ShoppingCarRespository;

@Repository
public class JpaShoppingCarRespository  extends JpaGenericRepository<ShoppingCar, String> implements ShoppingCarRespository {

	@Override
	public Long getNextShoppingCarSeq() {
		return convert2Long(getBySQL("select nextval('shopping_car_seq')"));
	}

}
