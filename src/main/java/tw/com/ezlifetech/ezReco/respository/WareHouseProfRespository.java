package tw.com.ezlifetech.ezReco.respository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.dto.WareHouseProfDto;
import tw.com.ezlifetech.ezReco.model.WareHouseProf;

public interface WareHouseProfRespository extends GenericRepository<WareHouseProf, String>{

	List<Map<String, Object>> ajaxGridList(WareHouseProfDto dto);

	List<Map<String, Object>> getAllwhIdAndNameListMap();
	
	List<Map<String, Object>> getAllwhCodeAndNameListMap();

	WareHouseProf getEntityByWhId(String whId);

	Collection<? extends Map<String, Object>> getAllWhIdList();
	
}
