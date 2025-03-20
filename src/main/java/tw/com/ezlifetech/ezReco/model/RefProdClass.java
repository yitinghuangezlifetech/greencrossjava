package tw.com.ezlifetech.ezReco.model;
// Generated 2018/8/5 �W�� 11:30:55 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import tw.com.ezlifetech.ezReco.common.model.GenericEntity;
import tw.com.ezlifetech.ezReco.util.StringUtil;

/**
 * RefProdClass generated by hbm2java
 */
@Entity
@Table(name = "ref_prod_class", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = { "pro_no",
		"class_serno" }))
public class RefProdClass extends GenericEntity<String> {

	private String id;
	private String proNo;
	private String classSerno;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String systemMemo;

	public RefProdClass() {
	}

	public RefProdClass(String id, String proNo, String classSerno, String createUser, Date createTime) {
		this.id = id;
		this.proNo = proNo;
		this.classSerno = classSerno;
		this.createUser = createUser;
		this.createTime = createTime;
	}

	

	@Id

	@Column(name = "id", unique = true, nullable = false, length = 20)
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	
	public void setId(String id) {
		this.id = StringUtil.getParameter(id);
		
	}

	@Column(name = "pro_no", nullable = false, length = 20)
	public String getProNo() {
		return this.proNo;
	}

	public void setProNo(String proNo) {
		this.proNo = proNo;
	}

	@Column(name = "class_serno", nullable = false, length = 20)
	public String getClassSerno() {
		return this.classSerno;
	}

	public void setClassSerno(String classSerno) {
		this.classSerno = classSerno;
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
