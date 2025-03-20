package tw.com.ezlifetech.ezReco.service.impl;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



import tw.com.ezlifetech.ezReco.bean.ContactusBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.common.service.SeqGenService;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.CommonStatus;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.enums.QuestTypes;
import tw.com.ezlifetech.ezReco.model.QuestionAnswerList;
import tw.com.ezlifetech.ezReco.respository.QuestionAnswerListRespository;
import tw.com.ezlifetech.ezReco.service.ContactUsService;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;
import tw.com.ezlifetech.ezReco.util.ValidatorUtil;

@Service
public class ContactUsServiceImpl implements ContactUsService{

	@Autowired
	private QuestionAnswerListRespository questionAnswerListRespository;
	
	@Autowired
	private SeqGenService seqGenService;
	
	@Override
	public List<ErrorBean> validateInternalAjaxSendQuest(ContactusBean bean) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		if(StringUtil.isBlank(bean.getQuestName())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_name");
			erBean.setLabelName("en".equals(bean.getLanguage())?"Name":"姓名");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc(bean.getLanguage()));
			list.add(erBean);
		}
		
		if(StringUtil.isBlank(bean.getQuestMail())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_mail");
			erBean.setLabelName("en".equals(bean.getLanguage())?"E-mail":"E-mail");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc(bean.getLanguage()));
			list.add(erBean);
		}else {
			if(!ValidatorUtil.isValidEmail(bean.getQuestMail())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_mail");
				erBean.setLabelName("en".equals(bean.getLanguage())?"E-mail":"E-mail");
				erBean.setMesg(ErrorMesgs.DATA_FORMAT_NOT_MATCH.getDoc(bean.getLanguage()));
				list.add(erBean);
			}
		}
		
		if(StringUtil.isBlank(bean.getQuestTitle())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_title");
			erBean.setLabelName("en".equals(bean.getLanguage())?"Subject":"主旨");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc(bean.getLanguage()));
			list.add(erBean);
		}
		
		if(StringUtil.isBlank(bean.getQuestContent())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_content");
			erBean.setLabelName("en".equals(bean.getLanguage())?"Message":"內容");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc(bean.getLanguage()));
			list.add(erBean);
		}
		
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxSendQuest(ContactusBean bean) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		
		QuestionAnswerList q = new QuestionAnswerList();
		q.setId(seqGenService.getSystemTimeRandomNumber());
		q.setQuestType(QuestTypes.CONTACT_US.getCode());
		q.setStatus(CommonStatus.WAIT_TO_BE_PROCESSED.getStatusCode());
		q.setQuestName(bean.getQuestName());
		q.setQuestMail(bean.getQuestMail());
		q.setQuestTitle(bean.getQuestTitle());
		q.setQuestContent(bean.getQuestContent());
		q.setIsAns("N");
		q.setCreateTime(DateUtil.getSystemDateTimeObject());
		q.setCreateUser("notLoginUser");
		
		questionAnswerListRespository.save(q);
		
		//questionAnswerListRespository
		ajaxReturnBean.setMessage(AjaxMesgs.SUBMIT_SUCCESSFUL.getDoc(bean.getLanguage()));
		return ajaxReturnBean;
	}

}
