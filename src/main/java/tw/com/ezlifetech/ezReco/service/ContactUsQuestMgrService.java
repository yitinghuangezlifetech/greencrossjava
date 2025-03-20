package tw.com.ezlifetech.ezReco.service;

import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.ContactUsQuestMgrBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;

public interface ContactUsQuestMgrService {

	String ajaxGetGridList(ContactUsQuestMgrBean formBean);

	AjaxReturnBean ajaxGetQuest(ContactUsQuestMgrBean bean);

	AjaxReturnBean ajaxChgQuestStatus(ContactUsQuestMgrBean bean);

}
