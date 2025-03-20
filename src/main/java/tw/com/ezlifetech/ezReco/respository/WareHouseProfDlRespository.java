package tw.com.ezlifetech.ezReco.respository;

import java.util.List;
import java.util.Map;

import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.dto.RefCompWarehouseDto;
import tw.com.ezlifetech.ezReco.dto.WareHouseProfDlDto;
import tw.com.ezlifetech.ezReco.model.WareHouseProfDl;

public interface WareHouseProfDlRespository extends GenericRepository<WareHouseProfDl, String>{

	List<Map<String, Object>> ajaxDlGridList(WareHouseProfDlDto dto);

	List<Map<String, Object>> getLogistcDlSelectList(WareHouseProfDlDto dto);
	
}
