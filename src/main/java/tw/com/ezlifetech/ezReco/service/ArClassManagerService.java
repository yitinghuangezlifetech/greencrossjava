package tw.com.ezlifetech.ezReco.service;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.ArClassManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.ArClassDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;

public interface ArClassManagerService {

	String arClassGridList(ArClassManagerBean formBean);

	String ajaxSaveArClass(ArClassDto dto, UserDto userDto);

	List<ErrorBean> validateInternalSave(ArClassDto dto);

	JsonObject ajaxGetArClassById(ArClassDto dto)  throws Exception;

	void ajaxRemoveArClass(ArClassDto dto, UserDto userDto);

	List<ErrorBean> validateInternalRemove(ArClassDto dto);

	JsonArray arClassList(ArClassManagerBean formBean);

	JsonArray ajaxGetAllArClass(ArClassDto dto,String preStr);
	
	List<Map<String,String>> ajaxGetAllArClass(String preStr);

}
