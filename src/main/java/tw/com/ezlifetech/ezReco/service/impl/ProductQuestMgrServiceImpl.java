package tw.com.ezlifetech.ezReco.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;


import tw.com.ezlifetech.ezReco.bean.ProductQuestMgrBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.QuestTypes;
import tw.com.ezlifetech.ezReco.model.Product;
import tw.com.ezlifetech.ezReco.model.QuestionAnswerList;
import tw.com.ezlifetech.ezReco.respository.ProductRespository;
import tw.com.ezlifetech.ezReco.respository.QuestionAnswerListRespository;
import tw.com.ezlifetech.ezReco.service.ProductQuestMgrService;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Service
public class ProductQuestMgrServiceImpl implements ProductQuestMgrService{

	
	@Autowired
	private QuestionAnswerListRespository questionAnswerListRespository;
	
	@Autowired
	private ProductRespository productRespository;
	
	@Override
	public String ajaxGetGridList(ProductQuestMgrBean formBean) {
		formBean.setQuestType(QuestTypes.PROUD_QUEST.getCode());
		List<Map<String, Object>> list = questionAnswerListRespository.ajaxGetGridList(formBean);
		for(Map<String, Object> m : list) {
			m.put("product_name","");
			m.put("product_code","");
			if(m.get("ref_id")!=null && !"".equals(m.get("ref_id").toString())) {
				Product p = productRespository.getEntityById(m.get("ref_id").toString());
				if(p!=null) {
					m.put("product_name",p.getProName());
					m.put("product_code",p.getProCode());
				}
			}
		}
		return PageUtil.transToGridDataSource(list,formBean);
	}

	@Override
	public AjaxReturnBean ajaxGetQuest(ProductQuestMgrBean bean) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		JsonObject jo = new JsonObject();
		
		QuestionAnswerList  qa = questionAnswerListRespository.getEntityById(bean.getId());
		jo.addProperty("questName", qa.getQuestName());
		jo.addProperty("questMail", qa.getQuestMail());
		jo.addProperty("questTitle", qa.getQuestTitle());
		jo.addProperty("questContent", qa.getQuestContent());
		jo.addProperty("questTel", qa.getQuestPhone());
		jo.addProperty("isAns", qa.getIsAns());
		
		jo.addProperty("isAns", qa.getIsAns());
		jo.addProperty("prodName","");
		jo.addProperty("prodCode","");
		if(!StringUtil.isBlank(qa.getRefId())) {
			Product p = productRespository.getEntityById(qa.getRefId());
			if(p!=null) {
				jo.addProperty("prodName",p.getProName());
				jo.addProperty("prodCode",p.getProCode());
			}
		}
		
		ajaxReturnBean.setValue(jo);
		return ajaxReturnBean;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxChgQuestStatus(ProductQuestMgrBean bean) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		QuestionAnswerList  qa = questionAnswerListRespository.getEntityById(bean.getId());
		
		qa.setIsAns(bean.getStatus());
		
		
		questionAnswerListRespository.save(qa);
		ajaxReturnBean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}

}
