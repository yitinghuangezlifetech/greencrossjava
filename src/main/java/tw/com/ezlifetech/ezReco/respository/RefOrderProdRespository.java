package tw.com.ezlifetech.ezReco.respository;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;

import tw.com.ezlifetech.ezReco.bean.OrderManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.model.RefOrderProd;

public interface RefOrderProdRespository extends GenericRepository<RefOrderProd, String> {

	//public List<RefOrderProd> getRefOrderProdByOrderNo(String id);
	
	public List<Map<String, Object>> getRefOrderProdByOrderNo(String id);

	public AjaxReturnBean ajaxRemove(RefOrderProd refOrderProd, UserDto dto);
}
