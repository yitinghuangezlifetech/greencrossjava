package tw.com.ezlifetech.ezReco.common.service;

import org.springframework.web.multipart.MultipartFile;

import tw.com.ezlifetech.ezReco.bean.FileUploadBean;

public interface FileUploadService {

	
	public FileUploadBean saveUploadFile(MultipartFile file) throws Exception;
	public FileUploadBean removeUploadFileByFileId(String fileId) throws Exception;
}
