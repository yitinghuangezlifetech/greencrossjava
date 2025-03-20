package tw.com.ezlifetech.ezReco.respository;

import java.util.List;
import java.util.Map;

import tw.com.ezlifetech.ezReco.bean.ContactUsQuestMgrBean;
import tw.com.ezlifetech.ezReco.bean.QuestMgrBean;
import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.model.QuestionAnswerList;

public interface QuestionAnswerListRespository extends GenericRepository<QuestionAnswerList, String>{

	List<Map<String, Object>> ajaxGetGridList(QuestMgrBean formBean);

}
