package tw.com.ezlifetech.ezReco.dto;

import java.util.Date;

import tw.com.ezlifetech.ezReco.common.dto.CommonDto;



public class BasicSysparamHtDto extends CommonDto{
	public static final String dtoName = "basicSysparamHtDto";

	
	private String id;
	private String paramCode;
	private String paramName;
	private String paramContent;
	private String name1;
	private String name2;
	private String name3;
	private String name4;
	private String name5;
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
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamContent() {
		return paramContent;
	}
	public void setParamContent(String paramContent) {
		this.paramContent = paramContent;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getName3() {
		return name3;
	}
	public void setName3(String name3) {
		this.name3 = name3;
	}
	public String getName4() {
		return name4;
	}
	public void setName4(String name4) {
		this.name4 = name4;
	}
	public String getName5() {
		return name5;
	}
	public void setName5(String name5) {
		this.name5 = name5;
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
	
	
	
}
