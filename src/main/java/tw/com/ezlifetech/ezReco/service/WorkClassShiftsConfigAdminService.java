package tw.com.ezlifetech.ezReco.service;

import java.util.List;

import org.springframework.ui.Model;

import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftDlDto;
import tw.com.ezlifetech.ezReco.dto.WorkClassShiftHtDto;

public interface WorkClassShiftsConfigAdminService {

	void paper(Model model);

	void paperEdit(Model model, WorkClassShiftHtDto dto,UserDto loginUser) throws Exception;

	String ajaxHtGridList(WorkClassShiftHtDto dto, UserDto loginUser);

	AjaxReturnBean ajaxGetDl(WorkClassShiftDlDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalGetDl(WorkClassShiftDlDto dto);

	String ajaxDlGridList(WorkClassShiftHtDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalApply(WorkClassShiftHtDto dto);

	AjaxReturnBean ajaxAgree(WorkClassShiftHtDto dto, UserDto loginUser);

	AjaxReturnBean ajaxReturn(WorkClassShiftHtDto dto, UserDto loginUser);

	AjaxReturnBean ajaxRef(WorkClassShiftHtDto dto, UserDto loginUser);

}
