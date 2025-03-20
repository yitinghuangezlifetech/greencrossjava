package tw.com.ezlifetech.ezReco.service;

import org.springframework.ui.Model;

import tw.com.ezlifetech.ezReco.bean.NewsHtBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.dto.GoHtDto;
import tw.com.ezlifetech.ezReco.dto.NewsHtDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;

public interface NewsHtService {

	String ajaxHtGridList(NewsHtBean bean, UserDto loginUser);
	
	void paper(Model model,NewsHtBean bean);
	
	AjaxReturnBean ajaxSaveHt(NewsHtDto dto, UserDto loginUser);
	
	AjaxReturnBean ajaxRemoveHt(NewsHtDto dto);
	
	void paperEdit(Model model, NewsHtBean dto,UserDto loginUser) throws Exception;
}
