package tw.com.ezlifetech.ezReco.dto;

import java.util.Date;

import tw.com.ezlifetech.ezReco.common.dto.CommonDto;

public class WorkClassShiftHtDto extends CommonDto{
	
	public static final String dtoName = "workClassShiftHtDto";

	private String id;
	private String ownerId;
	private String compId;
	private String principalId;
	private String siteSuperId;
	private String configName;
	private String status;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String systemMemo;
	
	private Date sdate;
	private Date edate;
	
	private String sdateText;
	private String edateText;
	
	
	

	private String whId;
	private String whDlId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getCompId() {
		return compId;
	}
	public void setCompId(String compId) {
		this.compId = compId;
	}
	public String getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	public String getSiteSuperId() {
		return siteSuperId;
	}
	public void setSiteSuperId(String siteSuperId) {
		this.siteSuperId = siteSuperId;
	}
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getSdateText() {
		return sdateText;
	}
	public void setSdateText(String sdateText) {
		this.sdateText = sdateText;
	}
	public String getEdateText() {
		return edateText;
	}
	public void setEdateText(String edateText) {
		this.edateText = edateText;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	public Date getEdate() {
		return edate;
	}
	public void setEdate(Date edate) {
		this.edate = edate;
	}
	public String getWhId() {
		return whId;
	}
	public void setWhId(String whId) {
		this.whId = whId;
	}
	public String getWhDlId() {
		return whDlId;
	}
	public void setWhDlId(String whDlId) {
		this.whDlId = whDlId;
	}
	
	
	
	
	
	
	
}
