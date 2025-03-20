package tw.com.ezlifetech.ezReco.bean;

import tw.com.ezlifetech.ezReco.common.bean.CommonBean;

public class FileUploadBean extends CommonBean{
	public static final String beanFormName = "fileUploadBean";

	
	private String fileName;
	private String fileId;
	private boolean isRemoveSuccessful = false;


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getFileId() {
		return fileId;
	}


	public void setFileId(String fileId) {
		this.fileId = fileId;
	}


	public boolean isRemoveSuccessful() {
		return isRemoveSuccessful;
	}


	public void setRemoveSuccessful(boolean isRemoveSuccessful) {
		this.isRemoveSuccessful = isRemoveSuccessful;
	}
}
