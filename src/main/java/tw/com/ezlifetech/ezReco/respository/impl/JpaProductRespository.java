package tw.com.ezlifetech.ezReco.respository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.Product;
import tw.com.ezlifetech.ezReco.respository.ProductRespository;


@Repository
public class JpaProductRespository extends JpaGenericRepository<Product, String> implements ProductRespository{
	
	
	
	
	@Override
	public Long getNextProductSeq() {
		return convert2Long(getBySQL("select nextval('product_seq')"));
	}

	@Override
	public String getProdMainPictByProdId(String id) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		List<Map<String, Object>> list=findListMapBySQL_map("  SELECT pro_pic_no FROM product_pic WHERE pro_no=:id and is_main_pic='Y'",params);
		if(list.size()==0) {
			return "";
		}
		
		
		return list.get(0).get("pro_pic_no").toString();
	}
	
}	
