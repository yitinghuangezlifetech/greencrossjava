package tw.com.ezlifetech.ezReco.respository;

import java.util.List;
import java.util.Map;

import tw.com.ezlifetech.ezReco.bean.GuardianBean;
import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.model.Guardian;

public interface GuardianRepository extends GenericRepository<Guardian, String>{

	/**
	 * 查詢衛教訊息
	 * @param bean
	 * @return
	 */
	List<Map<String, Object>> ajaxHtGridList(GuardianBean bean);
}
