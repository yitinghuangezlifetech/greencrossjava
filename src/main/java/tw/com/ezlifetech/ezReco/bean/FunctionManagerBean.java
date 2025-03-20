package tw.com.ezlifetech.ezReco.bean;

import tw.com.ezlifetech.ezReco.common.bean.CommonBean;

public class FunctionManagerBean extends CommonBean {
	public static final String beanFormName = "functionManageBean";

	private String[] sortFuncId;

	private String parentId;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String[] getSortFuncId() {
		return sortFuncId;
	}

	public void setSortFuncId(String[] sortFuncId) {
		this.sortFuncId = sortFuncId;
	}
}
