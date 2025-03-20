package tw.com.ezlifetech.ezReco.respository;

import java.util.List;
import java.util.Map;

import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftDlDto;
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftHtDto;
import tw.com.ezlifetech.ezReco.model.WorkClassShiftDl;


public interface WorkClassShiftDlRespository extends GenericRepository<WorkClassShiftDl, String>{

	List<Map<String, Object>> ajaxDlGridList(WorkClassShiftHtDto dto);

	List<WorkClassShiftDl> findEntityByHtId(WorkClassShiftDlDto dto);

}
