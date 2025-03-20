package tw.com.ezlifetech.ezReco.apcommon;

import java.io.Serializable;
import java.util.List;


public class PageResult implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long total;
	private Integer page;
	private Integer pageSize;
	private Integer totalPage;
	private List<?> data;
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}

}
