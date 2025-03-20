package tw.com.ezlifetech.ezReco.respository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.dto.RefCompWarehouseDto;
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftHtDto;
import tw.com.ezlifetech.ezReco.model.WorkClassShiftHt;

public interface WorkClassShiftHtRespository extends GenericRepository<WorkClassShiftHt, String>{

	List<Map<String, Object>> ajaxHtGridList(WorkClassShiftHtDto dto);

	List<Map<String, Object>> ajaxHtGridListAdmin(WorkClassShiftHtDto dto);

	List<Map<String, Object>> getLogistcWcsList(RefCompWarehouseDto dto);

}
