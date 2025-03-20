package tw.com.ezlifetech.ezReco.dto;

import java.util.Date;

import tw.com.ezlifetech.ezReco.common.dto.CommonDto;

public class RefCompWarehouseDto extends CommonDto{
	
	public static final String dtoName = "refCompWarehouseDto";
	
	private String id;
	private String comId;
	private String whId;
	private String whDlType;
	private String whDlId;
	private String wcsId;
	private String strength;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String systemMemo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getComId() {
		return comId;
	}
	public void setComId(String comId) {
		this.comId = comId;
	}
	public String getWhId() {
		return whId;
	}
	public void setWhId(String whId) {
		this.whId = whId;
	}
	public String getWhDlType() {
		return whDlType;
	}
	public void setWhDlType(String whDlType) {
		this.whDlType = whDlType;
	}
	public String getWhDlId() {
		return whDlId;
	}
	public void setWhDlId(String whDlId) {
		this.whDlId = whDlId;
	}
	public String getStrength() {
		return strength;
	}
	public void setStrength(String strength) {
		this.strength = strength;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getSystemMemo() {
		return systemMemo;
	}
	public void setSystemMemo(String systemMemo) {
		this.systemMemo = systemMemo;
	}
	public String getWcsId() {
		return wcsId;
	}
	public void setWcsId(String wcsId) {
		this.wcsId = wcsId;
	}
	
	
	

}
