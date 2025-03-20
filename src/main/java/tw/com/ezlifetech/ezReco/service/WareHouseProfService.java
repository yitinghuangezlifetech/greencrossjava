package tw.com.ezlifetech.ezReco.service;

import java.util.List;

import org.springframework.ui.Model;

import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.dto.WareHouseProfDlDto;
import tw.com.ezlifetech.ezReco.dto.WareHouseProfDto;

public interface WareHouseProfService {

	void paper(Model model,UserDto userDto)throws Exception;

	void paperEdit(Model model, WareHouseProfDto dto) throws Exception;

	List<ErrorBean> validateInternalSaveHt(WareHouseProfDto dto);

	AjaxReturnBean ajaxSaveHt(WareHouseProfDto dto, UserDto loginUser) throws Exception;

	String ajaxDlGridList(WareHouseProfDlDto dto, UserDto loginUser);

	String ajaxHtGridList(WareHouseProfDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalRemoveHt(WareHouseProfDto dto);

	AjaxReturnBean ajaxRemoveHt(WareHouseProfDto dto, UserDto loginUser);

	AjaxReturnBean ajaxSaveDl(WareHouseProfDlDto dto, UserDto loginUser) throws Exception;

	List<ErrorBean> validateInternalSaveDl(WareHouseProfDlDto dto);

	AjaxReturnBean ajaxGetDl(WareHouseProfDlDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalGetDl(WareHouseProfDlDto dto);

	AjaxReturnBean ajaxRemoveDl(WareHouseProfDlDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalRemoveDl(WareHouseProfDlDto dto);

}
