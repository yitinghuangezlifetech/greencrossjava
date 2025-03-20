package tw.com.ezlifetech.ezReco.service;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import tw.com.ezlifetech.ezReco.bean.ArItemUploadBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.ArItemHtDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;

public interface ArItemUploadService {

	void paper(Model model);

	AjaxReturnBean ajaxUploadFile(MultipartFile file)  throws Exception;

	AjaxReturnBean removeFileHandler(ArItemUploadBean formBean) throws Exception;

	List<ErrorBean> validateInternalFastArHtDlSave(ArItemUploadBean bean);

	AjaxReturnBean ajaxFastArHtDlSave(ArItemUploadBean bean, UserDto loginUser) throws Exception;

	String arGridList(ArItemUploadBean formBean);

	AjaxReturnBean ajaxRemoveHt(ArItemUploadBean formBean)  throws Exception;

	AjaxReturnBean ajaxGetArHtDlsInfo(ArItemHtDto dto, UserDto loginUser) throws Exception;
	
	AjaxReturnBean ajaxFastArHtDlUpdate(ArItemUploadBean bean, UserDto loginUser) throws Exception;

}
