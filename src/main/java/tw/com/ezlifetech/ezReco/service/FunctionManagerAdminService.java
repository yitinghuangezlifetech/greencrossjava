package tw.com.ezlifetech.ezReco.service;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.FunctionManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.ProfFunctionDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;

public interface FunctionManagerAdminService {
	List<Map<String,String>> funcSelMapList(FunctionManagerBean formBean);

	String funcGridList(FunctionManagerBean formBean);

	JsonArray funcListByParentId(FunctionManagerBean formBean);

	void sortFunc(FunctionManagerBean formBean,UserDto userDto);

	String saveFunc(ProfFunctionDto dto,UserDto userDto);

	List<ErrorBean> validateInternalSave(ProfFunctionDto dto);

	String removeFunc(ProfFunctionDto dto);

	List<ErrorBean> validateInternalRemove(ProfFunctionDto dto);

	JsonObject getFuncById(ProfFunctionDto dto) throws Exception;
}
