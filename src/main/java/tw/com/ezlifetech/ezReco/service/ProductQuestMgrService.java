package tw.com.ezlifetech.ezReco.service;

import tw.com.ezlifetech.ezReco.bean.ProductQuestMgrBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;

public interface ProductQuestMgrService {

	String ajaxGetGridList(ProductQuestMgrBean formBean);

	AjaxReturnBean ajaxGetQuest(ProductQuestMgrBean bean);

	AjaxReturnBean ajaxChgQuestStatus(ProductQuestMgrBean bean);

}
