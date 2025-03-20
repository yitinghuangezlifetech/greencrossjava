package tw.com.ezlifetech.ezReco.service;

import java.util.List;

import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.ContactusBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;

public interface ContactUsService {

	List<ErrorBean> validateInternalAjaxSendQuest(ContactusBean bean);

	AjaxReturnBean ajaxSendQuest(ContactusBean bean);

}
