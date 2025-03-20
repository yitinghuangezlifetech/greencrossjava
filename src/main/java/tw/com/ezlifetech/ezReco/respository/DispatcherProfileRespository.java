package tw.com.ezlifetech.ezReco.respository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.model.DispatcherProfile;

public interface DispatcherProfileRespository extends GenericRepository<DispatcherProfile, String>{

	List<Map<String, Object>> getAllDisIdSelectListByWhIdCompId(String whId,String comId);

}
