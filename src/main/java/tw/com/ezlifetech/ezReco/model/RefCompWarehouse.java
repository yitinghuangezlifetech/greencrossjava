package tw.com.ezlifetech.ezReco.model;
// Generated 2019/6/22 �U�� 10:03:36 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import tw.com.ezlifetech.ezReco.common.model.GenericEntity;

/**
 * RefCompWarehouse generated by hbm2java
 */
@Entity
@Table(name = "ref_comp_warehouse", schema = "public")
public class RefCompWarehouse extends GenericEntity<String> {

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

	public RefCompWarehouse() {
	}

	public RefCompWarehouse(String id, String createUser, Date createTime) {
		this.id = id;
		this.createUser = createUser;
		this.createTime = createTime;
	}

	public RefCompWarehouse(String id, String comId, String whId, String whDlType, String whDlId, String strength,
			String createUser, Date createTime, String updateUser, Date updateTime, String systemMemo) {
		this.id = id;
		this.comId = comId;
		this.whId = whId;
		this.whDlType = whDlType;
		this.whDlId = whDlId;
		this.strength = strength;
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

	@Column(name = "com_id", length = 30)
	public String getComId() {
		return this.comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}

	@Column(name = "wh_id", length = 30)
	public String getWhId() {
		return this.whId;
	}

	public void setWhId(String whId) {
		this.whId = whId;
	}

	@Column(name = "wh_dl_type", length = 30)
	public String getWhDlType() {
		return this.whDlType;
	}

	public void setWhDlType(String whDlType) {
		this.whDlType = whDlType;
	}

	@Column(name = "wh_dl_id", length = 30)
	public String getWhDlId() {
		return this.whDlId;
	}

	public void setWhDlId(String whDlId) {
		this.whDlId = whDlId;
	}

	public void setSystemMemo(String systemMemo) {
		this.systemMemo = systemMemo;
	}

	@Column(name = "wcs_id", length = 30)
	public String getWcsId() {
		return wcsId;
	}

	public void setWcsId(String wcsId) {
		this.wcsId = wcsId;
	}
	
	@Column(name = "strength", length = 30)
	public String getStrength() {
		return this.strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
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

	

}
