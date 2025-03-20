package tw.com.ezlifetech.ezReco.respository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;



import tw.com.ezlifetech.ezReco.bean.ContactUsQuestMgrBean;
import tw.com.ezlifetech.ezReco.bean.QuestMgrBean;
import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.enums.QuestTypes;
import tw.com.ezlifetech.ezReco.model.QuestionAnswerList;
import tw.com.ezlifetech.ezReco.respository.QuestionAnswerListRespository;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Repository
public class JpaQuestionAnswerListRespository extends JpaGenericRepository<QuestionAnswerList, String> implements QuestionAnswerListRespository {

	@Override
	public List<Map<String, Object>> ajaxGetGridList(QuestMgrBean formBean) {
		Map<String,Object> params = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer();
		
		if(!StringUtil.isBlank(formBean.getsDate())) {
			params.put("sDate", formBean.getsDate());
		}
		
		if(!StringUtil.isBlank(formBean.geteDate())) {
			params.put("eDate", formBean.geteDate());
		}
		
		ArrayList<String> status = new ArrayList<String>();
		
		if("Y".equals(formBean.getStatusY())) {
			status.add("Y");
		}
		
		if("Y".equals(formBean.getStatusN())) {
			status.add("N");
		}
		String inStatus = "";
		if(status.size()>0) {
			
			
			for(int i =0 ; i<status.size();i++) {
				
				inStatus = inStatus+"'"+status.get(i)+"'";
				
				if((i+1)<status.size()) {
					inStatus = inStatus+",";
				}
				
			}
			
		}
		
		
		
		params.put("questType", formBean.getQuestType());
		
		sql.append("	SELECT                                                     ");
		sql.append("		id,                                                    ");
		sql.append("		quest_name,                                            ");
		sql.append("		quest_mail,                                            ");
		sql.append("		quest_title,                                           ");
		sql.append("		quest_phone,                                           ");
		sql.append("		is_ans,                                                ");
		sql.append("		ans_time,                                              ");
		sql.append("		create_time,                                           ");
		sql.append("	    ref_id                                                 ");
		sql.append("	FROM question_answer_list                                  ");
		sql.append("	WHERE 1=1                                                  ");
		sql.append("	AND quest_type = :questType                                      ");
		if(params.get("sDate")!=null)
			sql.append("	AND to_char(create_time,'YYYY/MM/DD') >= :sDate      ");
		if(params.get("eDate")!=null)
			sql.append("	AND to_char(create_time,'YYYY/MM/DD') <= :eDate      ");
		if(!"".equals(inStatus))
			sql.append("	AND is_ans in ("+inStatus+")                                   ");
		 
		sql.append("    ORDER BY create_time DESC ");
		
		
		return this.findListMapBySQL_map(sql.toString(), params);
	}

}
