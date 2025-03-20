package tw.com.ezlifetech.ezReco.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.ContactUsQuestMgrBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.QuestTypes;
import tw.com.ezlifetech.ezReco.model.QuestionAnswerList;
import tw.com.ezlifetech.ezReco.respository.QuestionAnswerListRespository;
import tw.com.ezlifetech.ezReco.service.ContactUsQuestMgrService;
import tw.com.ezlifetech.ezReco.util.PageUtil;

@Service
public class ContactUsQuestMgrServiceImpl implements ContactUsQuestMgrService{

	@Autowired
	private QuestionAnswerListRespository questionAnswerListRespository;
	
	
	@Override
	public String ajaxGetGridList(ContactUsQuestMgrBean formBean) {
		formBean.setQuestType(QuestTypes.CONTACT_US.getCode());
		List<Map<String, Object>> list = questionAnswerListRespository.ajaxGetGridList(formBean);
		
		return PageUtil.transToGridDataSource(list,formBean);
	}


	@Override
	public AjaxReturnBean ajaxGetQuest(ContactUsQuestMgrBean bean) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		JsonObject jo = new JsonObject();
		
		QuestionAnswerList  qa = questionAnswerListRespository.getEntityById(bean.getId());
		jo.addProperty("questName", qa.getQuestName());
		jo.addProperty("questMail", qa.getQuestMail());
		jo.addProperty("questTitle", qa.getQuestTitle());
		jo.addProperty("questContent", qa.getQuestContent());
		jo.addProperty("isAns", qa.getIsAns());
		ajaxReturnBean.setValue(jo);
		return ajaxReturnBean;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxChgQuestStatus(ContactUsQuestMgrBean bean) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		QuestionAnswerList  qa = questionAnswerListRespository.getEntityById(bean.getId());
		
		qa.setIsAns(bean.getStatus());
		
		
		questionAnswerListRespository.save(qa);
		ajaxReturnBean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}

	
}
