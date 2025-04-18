package tw.com.ezlifetech.ezReco.model;
// Generated 2018/10/17 �U�� 10:36:56 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import tw.com.ezlifetech.ezReco.common.model.GenericEntity;

/**
 * VrItemHt generated by hbm2java
 */
@Entity
@Table(name = "ar_item_ht", schema = "public")
public class ArItemHt extends GenericEntity<String> {

	private String id;

	private String status;
	private String itemName;
	private String itemContent;
	private String ownerId;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String systemMemo;

	public ArItemHt() {
	}

	public ArItemHt(String id, String status, String itemName, String itemContent, String createUser, Date createTime) {
		this.id = id;
		this.status = status;
		this.itemName = itemName;
		this.itemContent = itemContent;
		this.createUser = createUser;
		this.createTime = createTime;
	}

	

	@Id

	@Column(name = "id", unique = true, nullable = false, length = 30)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "item_name", nullable = false, length = 100)
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "item_content", nullable = false, length = 1000)
	public String getItemContent() {
		return this.itemContent;
	}

	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}

	@Column(name = "owner_id", length = 30)
	public String getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
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
