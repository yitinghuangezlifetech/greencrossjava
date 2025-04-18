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
 * WarehouseProfileDl generated by hbm2java
 */
@Entity
@Table(name = "warehouse_profile_dl", schema = "public")
public class WareHouseProfDl extends GenericEntity<String> {

	private String id;
	private String whId;
	private String topId;
	private String type;
	private String dlId;
	private String dlName;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String systemMemo;

	public WareHouseProfDl() {
	}

	public WareHouseProfDl(String id, String whId, String type, String dlId, String dlName, String createUser,
			Date createTime) {
		this.id = id;
		this.whId = whId;
		this.type = type;
		this.dlId = dlId;
		this.dlName = dlName;
		this.createUser = createUser;
		this.createTime = createTime;
	}

	public WareHouseProfDl(String id, String whId, String topId, String type, String dlId, String dlName,
			String createUser, Date createTime, String updateUser, Date updateTime, String systemMemo) {
		this.id = id;
		this.whId = whId;
		this.topId = topId;
		this.type = type;
		this.dlId = dlId;
		this.dlName = dlName;
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

	@Column(name = "wh_id", nullable = false, length = 30)
	public String getWhId() {
		return this.whId;
	}

	public void setWhId(String whId) {
		this.whId = whId;
	}

	@Column(name = "top_id", length = 30)
	public String getTopId() {
		return this.topId;
	}

	public void setTopId(String topId) {
		this.topId = topId;
	}

	@Column(name = "type", nullable = false, length = 30)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "dl_id", nullable = false, length = 30)
	public String getDlId() {
		return this.dlId;
	}

	public void setDlId(String dlId) {
		this.dlId = dlId;
	}

	@Column(name = "dl_name", nullable = false, length = 200)
	public String getDlName() {
		return this.dlName;
	}

	public void setDlName(String dlName) {
		this.dlName = dlName;
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

}
