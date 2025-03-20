package tw.com.ezlifetech.ezReco.dto;

public class RefProdArDto {

	private String id;
	
	private String proNo;
	
	private String arId;
	private String dlId;
	
	private String itemName;
	private String itemContent;
	
	private String action;
	private String var;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProNo() {
		return proNo;
	}
	public void setProNo(String proNo) {
		this.proNo = proNo;
	}
	public String getArId() {
		return arId;
	}
	public void setArId(String arId) {
		this.arId = arId;
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
	public String getDlId() {
		return dlId;
	}
	public void setDlId(String dlId) {
		this.dlId = dlId;
	}
	
	
}
