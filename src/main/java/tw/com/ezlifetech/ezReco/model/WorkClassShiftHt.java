package tw.com.ezlifetech.ezReco.model;
// Generated 2019/6/22 �U�� 10:57:18 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import tw.com.ezlifetech.ezReco.common.model.GenericEntity;

/**
 * WorkClassShiftHt generated by hbm2java
 */
@Entity
@Table(name = "work_class_shift_ht", schema = "public")
public class WorkClassShiftHt extends GenericEntity<String> {

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

	public WorkClassShiftHt() {
	}

	public WorkClassShiftHt(String id, String ownerId, String compId, String createUser, Date createTime) {
		this.id = id;
		this.ownerId = ownerId;
		this.compId = compId;
		this.createUser = createUser;
		this.createTime = createTime;
	}

	public WorkClassShiftHt(String id, String ownerId, String compId, String principalId, String siteSuperId,
			String configName, String status, String createUser, Date createTime, String updateUser, Date updateTime,
			String systemMemo) {
		this.id = id;
		this.ownerId = ownerId;
		this.compId = compId;
		this.principalId = principalId;
		this.siteSuperId = siteSuperId;
		this.configName = configName;
		this.status = status;
		this.createUser = createUser;
		this.createTime = createTime;
		this.updateUser = updateUser;
		this.updateTime = updateTime;
		this.systemMemo = systemMemo;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false, length = 30)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "owner_id", nullable = false, length = 30)
	public String getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	@Column(name = "comp_id", nullable = false, length = 30)
	public String getCompId() {
		return this.compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}

	@Column(name = "principal_id", length = 30)
	public String getPrincipalId() {
		return this.principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	@Column(name = "site_super_id", length = 30)
	public String getSiteSuperId() {
		return this.siteSuperId;
	}

	public void setSiteSuperId(String siteSuperId) {
		this.siteSuperId = siteSuperId;
	}

	@Column(name = "config_name", length = 200)
	public String getConfigName() {
		return this.configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	@Column(name = "status", length = 30)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "create_user", nullable = false, length = 30)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false, length = 29)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_user", length = 30)
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", length = 29)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "system_memo", length = 100)
	public String getSystemMemo() {
		return this.systemMemo;
	}

	public void setSystemMemo(String systemMemo) {
		this.systemMemo = systemMemo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sdate", length = 29)
	public Date getSdate() {
		return sdate;
	}

	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "edate", length = 29)
	public Date getEdate() {
		return edate;
	}

	public void setEdate(Date edate) {
		this.edate = edate;
	}

}
