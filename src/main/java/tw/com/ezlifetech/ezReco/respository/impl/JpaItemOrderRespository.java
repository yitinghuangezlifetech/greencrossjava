package tw.com.ezlifetech.ezReco.respository.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.model.ItemOrder;
import tw.com.ezlifetech.ezReco.respository.ItemOrderRespository;

@Repository
public class JpaItemOrderRespository extends JpaGenericRepository<ItemOrder, String> implements ItemOrderRespository {

	@Override
	public List<Map<String, Object>> getItemOrderByUserid(UserDto dto) {
		List<Map<String, Object>> ppList = findListMapBySQL("SELECT * FROM item_order WHERE user_Id=?", dto.getId());
		System.out.println("ppList="+ppList);
		return ppList;
	}

	@Override
	public Long getNextItemOrderSeq() {
		return convert2Long(getBySQL("select nextval('item_order_seq')"));
	}

}
