package tw.com.ezlifetech.ezReco.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.ProductManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.ProductClassDto;
import tw.com.ezlifetech.ezReco.dto.ProductDto;
import tw.com.ezlifetech.ezReco.dto.ProductPicDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;

public interface ProductManagerService {

	List<ErrorBean> validateInternalSave(ProductDto dto);

	AjaxReturnBean ajaxSaveProduct(ProductDto dto, UserDto userDto)  throws Exception;

	void paperPage(Model model, ProductDto dto);

	String productGridList(ProductManagerBean formBean);

	void uploadFile(MultipartFile asyncFiles, ProductPicDto dto)  throws Exception;

	AjaxReturnBean saveProductPic(ProductPicDto productPicDto, UserDto loginUser);

	void getProductPic(HttpServletResponse response, String value) throws Exception;

	JsonArray ajaxGetAllProdPic(ProductDto dto);

	AjaxReturnBean ajaxRemovePic(ProductPicDto dto)  throws Exception;

	AjaxReturnBean ajaxSetMainPic(ProductPicDto dto ,UserDto userDto);

	AjaxReturnBean ajaxRemove(ProductDto dto) throws Exception;

	JsonArray ajaxGetAllProduct();
	
	void saveRefProdAr(String proNo, String arId) throws Exception;

}
