package tw.com.ezlifetech.ezReco.respository;

import java.util.List;
import java.util.Map;

import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.dto.RefCompWarehouseDto;
import tw.com.ezlifetech.ezReco.model.RefCompWarehouse;

public interface RefCompWarehouseRespository extends GenericRepository<RefCompWarehouse, String>{

	List<Map<String, Object>> getLogistcProfGridList(RefCompWarehouseDto dto);

	boolean isLogistcRepaet(RefCompWarehouseDto dto);

	String getWcsId(String comId, String whId);

	

}
