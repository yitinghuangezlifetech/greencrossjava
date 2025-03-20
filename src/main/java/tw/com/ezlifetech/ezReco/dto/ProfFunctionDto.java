package tw.com.ezlifetech.ezReco.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.beans.BeanUtils;

import tw.com.ezlifetech.ezReco.common.dto.CommonDto;
import tw.com.ezlifetech.ezReco.model.ProfFunction;



public class ProfFunctionDto extends CommonDto{
	
	public static final String dtoName = "profFunctionDto";
	
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
	
	private List<ProfFunctionDto> children = new ArrayList<ProfFunctionDto>();;

	private boolean isUserCanUse;
	private String canUseUrl;
	public ProfFunctionDto() {
		
	}
	
	public ProfFunctionDto(ProfFunction profFunction) {
		BeanUtils.copyProperties(profFunction, this ,"children");
		if(profFunction.getChildren()!=null)
			for(ProfFunction p : profFunction.getChildren()) {
				children.add(new ProfFunctionDto(p));
			}
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFuncNo() {
		return funcNo;
	}

	public void setFuncNo(String funcNo) {
		this.funcNo = funcNo;
	}

	public String getFuncUri() {
		return funcUri;
	}

	public void setFuncUri(String funcUri) {
		this.funcUri = funcUri;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(String sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getUseState() {
		return useState;
	}

	public void setUseState(String useState) {
		this.useState = useState;
	}

	

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	

	public String getSystemMemo() {
		return systemMemo;
	}

	public void setSystemMemo(String systemMemo) {
		this.systemMemo = systemMemo;
	}

	public List<ProfFunctionDto> getChildren() {
		return children;
	}

	public void setChildren(List<ProfFunctionDto> children) {
		this.children = children;
	}

	public String getFuncPict() {
		return funcPict;
	}

	public void setFuncPict(String funcPict) {
		this.funcPict = funcPict;
	}

	public boolean isUserCanUse() {
		return isUserCanUse;
	}

	public void setUserCanUse(boolean isUserCanUse) {
		this.isUserCanUse = isUserCanUse;
	}

	public String getCanUseUrl() {
		return canUseUrl;
	}

	public void setCanUseUrl(String canUseUrl) {
		this.canUseUrl = canUseUrl;
	}  
	
	
}
