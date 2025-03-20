package tw.com.ezlifetech.ezReco.respository.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonElement;

import tw.com.ezlifetech.ezReco.bean.OrderManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.model.RefOrderProd;
import tw.com.ezlifetech.ezReco.respository.RefOrderProdRespository;

@Repository
public class JpaRefOrderProdRespository extends JpaGenericRepository<RefOrderProd, String>
		implements RefOrderProdRespository {

	@Override
	public List<Map<String, Object>> getRefOrderProdByOrderNo(String order_no) {

		StringBuffer queryStr = new StringBuffer("SELECT * FROM ref_order_prod WHERE order_no IN ( " + order_no + " )");
		List<Map<String, Object>> refOrderProds = findListMapBySQL(queryStr.toString());

		return refOrderProds;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxRemove(RefOrderProd refOrderProd, UserDto dto) {
		AjaxReturnBean bean = new AjaxReturnBean();
		deleteByProperty("id", refOrderProd.getId());
		bean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		return bean;
	}

}
