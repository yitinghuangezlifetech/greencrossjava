package tw.com.ezlifetech.ezReco.model;
// Generated 2018/7/30 �U�� 10:25:21 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import tw.com.ezlifetech.ezReco.common.model.GenericEntity;
import tw.com.ezlifetech.ezReco.util.StringUtil;

/**
 * ProfFunction generated by hbm2java
 */
@Entity
@Table(name = "prof_function", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "func_no"))
public class ProfFunction extends GenericEntity<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String funcName;
	private String funcNo;
	private String funcUri;
	private String funcPict;
	private String parentId;
	private String sortIndex;
	private String useState;
	private String systemMemo;
	private Date updateTime;
	private String updateUser;
	private Date createTime;
	private String createUser;
	
	
	private List<ProfFunction> children;  

	public ProfFunction() {
	}

	public ProfFunction(String id, String funcName, String funcNo, String parentId, String useState) {
		this.id = id;
		this.funcName = funcName;
		this.funcNo = funcNo;
		this.parentId = parentId;
		this.useState = useState;
	}

	

	@Id
	@Column(name = "func_id", unique = true, nullable = false, length = 20)
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	
	public void setId(String id) {
		this.id = StringUtil.getParameter(id);
		
	}

	@Column(name = "func_name", nullable = false, length = 20)
	public String getFuncName() {
		return this.funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	@Column(name = "func_no", unique = true, nullable = false, length = 20)
	public String getFuncNo() {
		return this.funcNo;
	}

	public void setFuncNo(String funcNo) {
		this.funcNo = funcNo;
	}

	@Column(name = "func_uri", length = 200)
	public String getFuncUri() {
		return this.funcUri;
	}

	public void setFuncUri(String funcUri) {
		this.funcUri = funcUri;
	}
	
	@Column(name = "func_pict", length = 100)
	public String getFuncPict() {
		return this.funcPict;
	}

	public void setFuncPict(String funcPict) {
		this.funcPict = funcPict;
	}

	@Column(name = "parent_id", nullable = false, length = 20)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "sort_index", length = 5)
	public String getSortIndex() {
		return this.sortIndex;
	}

	public void setSortIndex(String sortIndex) {
		this.sortIndex = sortIndex;
	}

	@Column(name = "use_state", nullable = false, length = 5)
	public String getUseState() {
		return this.useState;
	}

	public void setUseState(String useState) {
		this.useState = useState;
	}

	@Column(name = "system_memo", length = 100)
	public String getSystemMemo() {
		return this.systemMemo;
	}

	public void setSystemMemo(String systemMemo) {
		this.systemMemo = systemMemo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", length = 29)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "update_user", length = 30)
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", length = 29)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "create_user", length = 30)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	@Transient
	public List<ProfFunction> getChildren() {
		return children;
	}

	public void setChildren(List<ProfFunction> children) {
		this.children = children;
	}

	

}
