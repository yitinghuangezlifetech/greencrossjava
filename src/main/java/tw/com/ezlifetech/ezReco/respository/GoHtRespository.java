package tw.com.ezlifetech.ezReco.respository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import tw.com.ezlifetech.ezReco.bean.GoBean;
import tw.com.ezlifetech.ezReco.bean.GoReportBean;
import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.dto.GoHtDto;
import tw.com.ezlifetech.ezReco.model.GoHt;

public interface GoHtRespository extends GenericRepository<GoHt, String>{

	List<Map<String, Object>> ajaxHtGridList(GoBean dto);

	GoHt getUserGo(GoHtDto dto);
	
	List<Map<String, Object>> ajaxReportHtGridList(GoReportBean bean)throws ParseException;
	
	List<Map<String, Object>> ajaxExpReportHtGridList(GoReportBean bean)throws ParseException;
	
	Map<String, Object> ajaxExpReportHt(GoReportBean bean) throws ParseException;

}
