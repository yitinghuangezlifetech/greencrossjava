package tw.com.ezlifetech.ezReco.service;

import java.util.List;

import org.springframework.ui.Model;

import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.dto.WareHouseProfDlDto;
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftDlDto;
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftHtDto;

public interface WorkClassShiftsConfigService {

	void paper(Model model);

	void paperEdit(Model model, WorkClassShiftHtDto dto,UserDto loginUser) throws Exception;

	List<ErrorBean> validateInternalSaveHt(WorkClassShiftHtDto dto) throws Exception;

	AjaxReturnBean ajaxSaveHt(WorkClassShiftHtDto dto, UserDto loginUser) throws Exception;

	String ajaxDlGridList(WorkClassShiftHtDto dto, UserDto loginUser);

	String ajaxHtGridList(WorkClassShiftHtDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalRemoveHt(WorkClassShiftHtDto dto);

	AjaxReturnBean ajaxRemoveHt(WorkClassShiftHtDto dto, UserDto loginUser);

	AjaxReturnBean ajaxSaveDl(WorkClassShiftDlDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalSaveDl(WorkClassShiftDlDto dto);

	AjaxReturnBean ajaxGetDl(WorkClassShiftDlDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalGetDl(WorkClassShiftDlDto dto);

	AjaxReturnBean ajaxRemoveDl(WorkClassShiftDlDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalRemoveDl(WorkClassShiftDlDto dto);

	AjaxReturnBean ajaxSetMainDl(WorkClassShiftDlDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalSetMainDl(WorkClassShiftDlDto dto);

	AjaxReturnBean ajaxDisSetMainDl(WorkClassShiftDlDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalDisSetMainDl(WorkClassShiftDlDto dto);

	String ajaxDlSelectList(WareHouseProfDlDto dto, UserDto loginUser);

	AjaxReturnBean ajaxTimes2PageCount(WorkClassShiftDlDto dto, UserDto loginUser);

}
