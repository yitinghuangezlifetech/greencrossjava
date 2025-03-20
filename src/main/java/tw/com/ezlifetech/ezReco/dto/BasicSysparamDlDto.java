package tw.com.ezlifetech.ezReco.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import tw.com.ezlifetech.ezReco.common.dto.CommonDto;
import tw.com.ezlifetech.ezReco.model.BasicSysparamDl;


public class BasicSysparamDlDto  extends CommonDto{
	public static final String dtoName = "basicSysparamDlDto";

	private String id;
	private String htId;
	private String paramCode;
	private String param1;
	private String param2;
	private String param3;
	private String param4;
	private String param5;
	private String isDelete;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String systemMemo;
	
	public BasicSysparamDlDto() {
		
	}
	
	public BasicSysparamDlDto(BasicSysparamDl dl) {
		BeanUtils.copyProperties(dl, this);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHtId() {
		return htId;
	}
	public void setHtId(String htId) {
		this.htId = htId;
	}
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	public String getParam3() {
		return param3;
	}
	public void setParam3(String param3) {
		this.param3 = param3;
	}
	public String getParam4() {
		return param4;
	}
	public void setParam4(String param4) {
		this.param4 = param4;
	}
	public String getParam5() {
		return param5;
	}
	public void setParam5(String param5) {
		this.param5 = param5;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
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
