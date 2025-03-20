package tw.com.ezlifetech.ezReco.service;

import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import tw.com.ezlifetech.ezReco.bean.GoBean;
import tw.com.ezlifetech.ezReco.bean.GoReportBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.GoHtDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;

public interface GoService {

	void paper(Model model,GoBean bean);

	void reportPaper(Model model, GoReportBean bean);
	
	void paperEdit(Model model, GoHtDto dto,UserDto loginUser) throws Exception;

	List<ErrorBean> validateInternalSaveHt(GoHtDto dto);

	AjaxReturnBean ajaxSaveHt(GoHtDto dto, UserDto loginUser) throws ParseException, Exception;

	String ajaxHtGridList(GoBean bean, UserDto loginUser);
	
	String ajaxDlGridList(GoHtDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalRemoveHt(GoHtDto dto);

	AjaxReturnBean ajaxRemoveHt(GoHtDto dto, UserDto loginUser);

	String ajaxGetComIdSelectListByWhId(GoBean bean, UserDto loginUser);

	String ajaxGetWhIdSelectList(GoBean bean, UserDto loginUser);

	String ajaxGetDisIdSelectListByWhIdCompId(GoBean bean, UserDto loginUser);

	List<ErrorBean> validateInternalPunch(GoBean bean);

	AjaxReturnBean ajaxPunch(GoBean bean, UserDto loginUser);

	void ajaxGetDispatcherPic(HttpServletResponse response, String value) throws Exception;
	
	String ajaxReportHtGridList(GoReportBean bean, UserDto loginUser)throws ParseException;

	Map<String, Object> strdatePlusMonth(int month);

	AjaxReturnBean ajaxBtnControl(GoHtDto dto, UserDto loginUser);

	List<ErrorBean> validateInternalBtnControl(GoHtDto dto);

	String ajaxExpReportHtGridList(GoReportBean bean, UserDto loginUser)throws ParseException;
	
	Map<String,Object> ajaxExpReportHt(GoReportBean bean, UserDto loginUser) throws ParseException;
	
	void export(GoReportBean bean, UserDto loginUser, String excelName, OutputStream out);
	
	
	/**
	 * 計算要減幾小時(休息時間)
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	double countSubHour(Date start,Date end) throws Exception ;
}
