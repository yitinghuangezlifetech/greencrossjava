package tw.com.ezlifetech.ezReco.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import tw.com.ezlifetech.ezReco.common.dto.CommonDto;
import tw.com.ezlifetech.ezReco.model.ProductClass;

public class ProductClassDto extends CommonDto{
	public static final String dtoName = "productClassDto";
	private String id;
	private String className;
	private String classNameEn;
	private String classDesc;
	private String classDescEn;
	private String status;
	private String sortIndex;
	private String classParentId;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String systemMemo;
	
	public ProductClassDto() {
		
	}
	
	public ProductClassDto(ProductClass productClass) {
		BeanUtils.copyProperties(productClass, this);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(String sortIndex) {
		this.sortIndex = sortIndex;
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

	public String getClassParentId() {
		return classParentId;
	}

	public void setClassParentId(String classParentId) {
		this.classParentId = classParentId;
	}

	public String getClassNameEn() {
		return classNameEn;
	}

	public void setClassNameEn(String classNameEn) {
		this.classNameEn = classNameEn;
	}

	public String getClassDesc() {
		return classDesc;
	}

	public void setClassDesc(String classDesc) {
		this.classDesc = classDesc;
	}

	public String getClassDescEn() {
		return classDescEn;
	}

	public void setClassDescEn(String classDescEn) {
		this.classDescEn = classDescEn;
	}
	
	
	
	
}
