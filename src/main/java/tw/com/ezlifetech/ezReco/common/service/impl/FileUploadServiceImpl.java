package tw.com.ezlifetech.ezReco.common.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tw.com.ezlifetech.ezReco.bean.FileUploadBean;
import tw.com.ezlifetech.ezReco.common.service.FileUploadService;
import tw.com.ezlifetech.ezReco.util.StringUtil;
import tw.com.ezlifetech.ezReco.util.SysParamsUtils;

@Service
public class FileUploadServiceImpl implements FileUploadService{

	@Override
	public FileUploadBean saveUploadFile(MultipartFile file) throws Exception {
		FileUploadBean res = new FileUploadBean();
		
		if (!file.getOriginalFilename().isEmpty()) {
			
			
			String uploadPath = SysParamsUtils.getParam("uploadFilePath")+File.separator+"arImgUpload";
			File uploadPathD = new File(uploadPath);
			if(!uploadPathD.exists()) {
				uploadPathD.mkdirs();
			}
			
			String fileName = System.currentTimeMillis()+StringUtil.getRandomNum(3)+"_"+file.getOriginalFilename();
			res.setFileName(fileName);
			res.setFileId(fileName);
	        BufferedOutputStream outputStream = new BufferedOutputStream(
	               new FileOutputStream(
	                     new File(uploadPath+File.separator+fileName)));
	         outputStream.write(file.getBytes());
	         outputStream.flush();
	         outputStream.close();
	      }
		
		
		
		return res;
	}

	@Override
	public FileUploadBean removeUploadFileByFileId(String fileId) throws Exception {
		FileUploadBean res = new FileUploadBean();
		
		
		if(!StringUtil.isBlank(fileId)) {
			res.setFileId(fileId);
			String uploadPath = SysParamsUtils.getParam("uploadFilePath")+File.separator+"arImgUpload";
			File uploadPathD = new File(uploadPath);
			if(uploadPathD.exists()) {
				
				File removeFile = new File(uploadPath+File.separator+fileId);
				if(removeFile.exists() && removeFile.isFile()) {
					res.setRemoveSuccessful(removeFile.delete());
				}
				
				
			}
		}
		
		return res;
	}

}
