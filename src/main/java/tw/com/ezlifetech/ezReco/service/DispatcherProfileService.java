package tw.com.ezlifetech.ezReco.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import tw.com.ezlifetech.ezReco.bean.DispatcherProfileBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.DispatcherProfileDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;

public interface DispatcherProfileService {

	AjaxReturnBean ajaxSaveDispatcherProfile(DispatcherProfileDto dto, UserDto userDto)  throws Exception;

	void paperPage(Model model, DispatcherProfileDto DispatcherProfileDto);

	String dispatcherProfileGridList(DispatcherProfileBean formBean);
	
	List<Map<String,Object>> dispatcherProfileList(String userId,String comId);
	
	AjaxReturnBean ajaxRemove(DispatcherProfileDto dto) throws Exception;

	List<ErrorBean> validateInternalSave(DispatcherProfileDto dto);

	List<Map<String,Object>> getCompIdSelectList();
	
	DispatcherProfileDto uploadFile(MultipartFile asyncFiles, DispatcherProfileDto dto) throws Exception;
	
	DispatcherProfileDto updateDispatcherPhotoPath(DispatcherProfileDto dto, UserDto userDto) throws Exception;
	
	void getDispatcherPic(HttpServletResponse response, String id) throws Exception;
}
