package tw.com.ezlifetech.ezReco.service;

import java.util.List;

import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.SystemParamConfigBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.BasicSysparamDlDto;
import tw.com.ezlifetech.ezReco.dto.BasicSysparamHtDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;

public interface SystemParamConfigService {

	String systemParamHtGridList(SystemParamConfigBean formBean);

	AjaxReturnBean ajaxSaveSystemParamHt(BasicSysparamHtDto dto, UserDto loginUser) throws Exception ;

	List<ErrorBean> validateInternalSaveSystemParamHt(BasicSysparamHtDto dto);

	JsonObject ajaxGetSystemParamHtById(BasicSysparamHtDto dto);

	AjaxReturnBean ajaxRemoveSystemParamHt(BasicSysparamHtDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalRemoveSystemParamHt(BasicSysparamHtDto dto);

	void paperDetailPage(BasicSysparamHtDto dto);

	String systemParamDlGridList(BasicSysparamHtDto dto);

	AjaxReturnBean ajaxSaveSystemParamDl(BasicSysparamDlDto dto, UserDto loginUser) throws Exception;

	List<ErrorBean> validateInternalSaveSystemParamDl(BasicSysparamDlDto dto);

	JsonObject ajaxGetSystemParamDlById(BasicSysparamDlDto dto);

	AjaxReturnBean ajaxRemoveSystemParamDl(BasicSysparamDlDto dto, UserDto loginUser) throws Exception;

	List<ErrorBean> validateInternalRemoveSystemParamDl(BasicSysparamDlDto dto);

}
