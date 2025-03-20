package tw.com.ezlifetech.ezReco.service;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.ProductClassManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.ProductClassDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;

public interface ProductClassManagerService {

	String prodClassGridList(ProductClassManagerBean formBean);

	String ajaxSaveProdClass(ProductClassDto dto, UserDto userDto);

	List<ErrorBean> validateInternalSave(ProductClassDto dto);

	JsonObject ajaxGetProdClassById(ProductClassDto dto)  throws Exception;

	void ajaxRemoveProdClass(ProductClassDto dto, UserDto userDto);

	List<ErrorBean> validateInternalRemove(ProductClassDto dto);

	JsonArray prodClassList(ProductClassManagerBean formBean);

	JsonArray ajaxGetAllProdClass(ProductClassDto dto,String preStr);
	
	List<Map<String,String>> ajaxGetAllProdClass(String preStr);

	void deepProdClass(List<Map<String, String>> list, int index, String preStr, String pId);
	void deepProdClass(List<Map<String, String>> list, int index, String preStr, String pId,String language);

}
