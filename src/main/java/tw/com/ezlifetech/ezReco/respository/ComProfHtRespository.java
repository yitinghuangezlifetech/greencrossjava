package tw.com.ezlifetech.ezReco.respository;

import java.util.List;
import java.util.Map;

import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.dto.ComProfHtDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.model.ComProfHt;

public interface ComProfHtRespository extends GenericRepository<ComProfHt, String>{

	List<Map<String, Object>> getAllCompIdAndNameListMap();
	
	List<Map<String, Object>> getAllCompCodeAndNameListMap();

	List<Map<String, Object>> getCompIdAndNameListMapByUserId(String userId);

	boolean isComCodeRepeat(ComProfHtDto dto);

	ComProfHt getEntityByComCode(String comCode);

	List<Map<String, Object>> getAllCompCodeAndNameListMapByWhId(String whId,UserDto loginUser);

	List<Map<String, Object>> getCompCodeAndNameListMapByUserId(String userId);

}
