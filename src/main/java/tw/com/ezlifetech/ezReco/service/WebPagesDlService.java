package tw.com.ezlifetech.ezReco.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.WebPagesBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.WebPagesDlDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;

public interface WebPagesDlService {

	//取得編輯頁面明細資料
	public String webPagesDlGridList(WebPagesBean formBean);
	
	JsonObject ajaxGetWebPagesDlById(WebPagesDlDto dto); 
	
	//儲存頁面資料
	AjaxReturnBean ajaxSaveWebPagesDl(WebPagesDlDto dto, UserDto userDto)  throws Exception;

	//驗證儲存
	List<ErrorBean> validateInternalSaveWebPagesDl(WebPagesDlDto dto);
	
	//刪除頁面資料
	AjaxReturnBean ajaxRemoveWebPagesDl(WebPagesDlDto dto) throws Exception;
	
	public Long getMaxSortNo(WebPagesBean formBean);

	
}
