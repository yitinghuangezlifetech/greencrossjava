package tw.com.ezlifetech.ezReco.service;

import java.util.List;

import org.springframework.ui.Model;

import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.RefCompWarehouseDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.dto.WareHouseProfDlDto;

public interface LogistcProfService {

	String ajaxGridList(RefCompWarehouseDto dto, UserDto loginUser);

	String ajaxDlSelectList(WareHouseProfDlDto dto, UserDto loginUser);

	void paperPage(Model model, UserDto loginUser);

	AjaxReturnBean ajaxSave(RefCompWarehouseDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalSave(RefCompWarehouseDto dto);

	String ajaxWcsSelectList(RefCompWarehouseDto dto, UserDto loginUser);

	AjaxReturnBean ajaxGetRefData(RefCompWarehouseDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalGetRefData(RefCompWarehouseDto dto);

	List<ErrorBean> validateInternalRemove(RefCompWarehouseDto dto);

	AjaxReturnBean ajaxRemove(RefCompWarehouseDto dto, UserDto loginUser);

}
