package tw.com.ezlifetech.ezReco.bean;

import tw.com.ezlifetech.ezReco.common.bean.CommonBean;

public class ArItemUploadBean extends CommonBean{
	public static final String beanFormName = "arItemUploadBean";
	
	
	private String htId;
	private String dlId;
	
	private String itemName;
	private String itemContent;
	private String arfileId;
	private String action;
	private String fileId;
	private String var;
	
	private String proNo;
	
	private String ownerId;
	
	
	
	
	
	
	
	
	
	
	
	

	public String getProNo() {
		return proNo;
	}

	public void setProNo(String proNo) {
		this.proNo = proNo;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getHtId() {
		return htId;
	}

	public void setHtId(String htId) {
		this.htId = htId;
	}

	public String getDlId() {
		return dlId;
	}

	public void setDlId(String dlId) {
		this.dlId = dlId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemContent() {
		return itemContent;
	}

	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getArfileId() {
		return arfileId;
	}

	public void setArfileId(String arfileId) {
		this.arfileId = arfileId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	} 

}
