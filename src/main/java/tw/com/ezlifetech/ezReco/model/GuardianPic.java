package tw.com.ezlifetech.ezReco.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import tw.com.ezlifetech.ezReco.common.model.GenericEntity;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Entity
@Table(name = "guardian_pic", schema = "public")
public class GuardianPic extends GenericEntity<String> {

	private String id;
	private String proNo;
	private String picPatch;
	private String isMainPic;
	private String picIndex;
	private String contentType;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String systemMemo;

	public GuardianPic() {
	}

	public GuardianPic(String id, String proNo, String picPatch) {
		this.id = id;
		this.proNo = proNo;
		this.picPatch = picPatch;
		
	}


	@Id

	@Column(name = "pro_pic_no", unique = true, nullable = false, length = 40)
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

	@Column(name = "pic_patch", nullable = false, length = 200)
	public String getPicPatch() {
		return this.picPatch;
	}

	public void setPicPatch(String picPatch) {
		this.picPatch = picPatch;
	}

	@Column(name = "is_main_pic", length = 5)
	public String getIsMainPic() {
		return this.isMainPic;
	}

	public void setIsMainPic(String isMainPic) {
		this.isMainPic = isMainPic;
	}

	@Column(name = "pic_index", length = 5)
	public String getPicIndex() {
		return this.picIndex;
	}

	public void setPicIndex(String picIndex) {
		this.picIndex = picIndex;
	}

	@Column(name = "content_type", length = 100)
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Column(name = "create_user", length = 30)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", length = 29)
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
