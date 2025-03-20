package tw.com.ezlifetech.ezReco.respository;

import java.util.List;
import java.util.Map;

import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.model.ItemOrder;


public interface ItemOrderRespository  extends GenericRepository<ItemOrder, String> {

	public List<Map<String, Object>> getItemOrderByUserid(UserDto dto);

	public Long getNextItemOrderSeq();

}
