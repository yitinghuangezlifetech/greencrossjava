package tw.com.ezlifetech.ezReco.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import tw.com.ezlifetech.ezReco.common.model.GenericEntity;

@Entity
@Table(name = "ref_prod_ar",schema = "public")
public class RefProdAr extends GenericEntity<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String proNo;
	
	private String arId;

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 30)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "pro_no", unique = true, nullable = false, length = 30)
	public String getProNo() {
		return proNo;
	}

	public void setProNo(String proNo) {
		this.proNo = proNo;
	}
	
	@Column(name = "ar_id", unique = true, nullable = false, length = 30)
	public String getArId() {
		return arId;
	}

	public void setArId(String arId) {
		this.arId = arId;
	}
	
	
}
