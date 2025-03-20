package tw.com.ezlifetech.ezReco.common.service;

import java.util.List;
import java.util.Map;

import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.model.User;

public interface UserService {

	
	public List<Map<String,Object>> getUserFunctionList(UserDto userDto, String methodName, String roleType, String roleNo);
}
