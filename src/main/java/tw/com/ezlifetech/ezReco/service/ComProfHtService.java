package tw.com.ezlifetech.ezReco.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;

import tw.com.ezlifetech.ezReco.bean.ComProfHtBean;
import tw.com.ezlifetech.ezReco.bean.ProductManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.ComProfHtDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;


public interface ComProfHtService {

//	List<ErrorBean> validateInternalSave(ProductDto dto);

	AjaxReturnBean ajaxSaveComProfHt(ComProfHtDto dto, UserDto userDto)  throws Exception;

	void paperPage(Model model, ComProfHtDto comProfHtDto);

	String comProfHtGridList(ComProfHtBean formBean);
	
	AjaxReturnBean ajaxRemove(ComProfHtDto dto) throws Exception;

	List<ErrorBean> validateInternalSave(ComProfHtDto dto);

	ComProfHtDto getComProfHt(UserDto userDto) throws Exception;

//	void uploadFile(MultipartFile asyncFiles, ProductPicDto dto)  throws Exception;
//
//	AjaxReturnBean saveProductPic(ProductPicDto productPicDto, UserDto loginUser);
//
//	void getProductPic(HttpServletResponse response, String value) throws Exception;
//
//	JsonArray ajaxGetAllProdPic(ProductDto dto);
//
//	AjaxReturnBean ajaxRemovePic(ProductPicDto dto)  throws Exception;
//
//	AjaxReturnBean ajaxSetMainPic(ProductPicDto dto ,UserDto userDto);
//
//	AjaxReturnBean ajaxRemove(ProductDto dto) throws Exception;
//
//	JsonArray ajaxGetAllProduct();
}
