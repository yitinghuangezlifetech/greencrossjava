package tw.com.ezlifetech.ezReco.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;

import tw.com.ezlifetech.ezReco.bean.GuardianBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.dto.GuardianDto;
import tw.com.ezlifetech.ezReco.dto.GuardianPicDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;

public interface GuardianService {
	
	String ajaxHtGridList(GuardianBean bean, UserDto loginUser);
	
	void paper(Model model,GuardianBean bean,String language,String classType);
	
	AjaxReturnBean ajaxSaveHt(GuardianDto dto, UserDto loginUser);
	
	AjaxReturnBean ajaxRemoveHt(GuardianDto dto);
	
	void paperEdit(Model model, GuardianBean dto,UserDto loginUser) throws Exception;
	
	AjaxReturnBean saveGuardianPic(GuardianPicDto guardianPicDto, UserDto loginUser);
	
	void uploadFile(MultipartFile asyncFiles, GuardianPicDto dto) throws Exception;
	
	void getGuardianPic(HttpServletResponse response, String value) throws Exception;
	
	JsonArray ajaxGetAllGuardianPic(GuardianDto dto);
	
	AjaxReturnBean ajaxRemovePic(GuardianPicDto dto) throws Exception;
	
	AjaxReturnBean ajaxSetMainPic(GuardianPicDto dto ,UserDto userDto);
}
