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
import tw.com.ezlifetech.ezReco.dto.WebPagesDto;
import tw.com.ezlifetech.ezReco.dto.ProductDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;

public interface WebPagesService {

	//取得欲編輯的頁面資料
	void detailPage(Model model,WebPagesDto dto);

	//取得編輯頁面明細資料

	String webPagesDlGridList(WebPagesBean formBean);
	
	//儲存頁面資料
	AjaxReturnBean ajaxSaveWebPages(WebPagesDto dto, UserDto userDto)  throws Exception;

	//驗證儲存
	List<ErrorBean> validateInternalSave(WebPagesDto dto);
	
	//刪除頁面資料
	AjaxReturnBean ajaxRemove(WebPagesDto dto) throws Exception;
}
