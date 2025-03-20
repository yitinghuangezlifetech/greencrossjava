package tw.com.ezlifetech.ezReco.service.impl;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.WebPagesBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.common.service.SeqGenService;
import tw.com.ezlifetech.ezReco.dto.WebPagesDlDto;
import tw.com.ezlifetech.ezReco.dto.ProductPicDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.model.ProductPic;
import tw.com.ezlifetech.ezReco.model.WebPagesDl;
import tw.com.ezlifetech.ezReco.respository.WebPagesDlRespository;
import tw.com.ezlifetech.ezReco.service.WebPagesDlService;
import tw.com.ezlifetech.ezReco.util.ConfigUtil;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;
import tw.com.ezlifetech.ezReco.util.ValidatorUtil;

@Service
public class WebPagesDlServiceImpl  implements WebPagesDlService{
	
	@Autowired
	private WebPagesDlRespository webPagesDlRespository;
	
	@Autowired
	private SeqGenService seqGenService;

	/**
	 * 取得靜態頁面明系列表
	 * @param model
	 * @return
	 */
	@Override
	public String webPagesDlGridList(WebPagesBean formBean) {
		Map<String,Object> params = new HashMap<String,Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(!StringUtil.isBlank(formBean.getPageId())) {
			params.put("page_id",formBean.getPageId());
		}
		
		if(params.get("page_id")!=null) {
			StringBuffer sql = new StringBuffer();
			sql.append(" select id,page_id,page_div_name,page_div_name_en,page_Div,page_Sort_No,page_Html,page_Html_En,create_user,create_time,update_user,update_time ");
			sql.append(" from web_pages_dl where 1 = 1 ");
			sql.append("  and page_id = :page_id ");
			
			list=webPagesDlRespository.findListMapBySQL_map(sql.toString(), params);
		}
		
		return PageUtil.transToGridDataSource(list,formBean); 
	}
	
	/**
     * ajax取得要編輯的法規屬性資料
	 * @param model
	 * @return
	 */
	@Override
	public JsonObject ajaxGetWebPagesDlById(WebPagesDlDto dto) {
		JsonObject jo = new JsonObject();
		WebPagesDl h = webPagesDlRespository.getEntityById(dto.getId());
//		WebPagesDl h = webPagesDlRespository.getEntityByProperty("pageId",dto.getPageId());

//		String[] nameArray = {"id", "page_id"};
//		Object[] valueArray = {dto.getId(), dto.getPageId()};
//		WebPagesDl d =webPagesDlRespository.getEntityByProperties(nameArray, valueArray);
		
		jo.addProperty("id", h.getId());
		jo.addProperty("pageId", h.getPageId());
		jo.addProperty("pageDivName", h.getPageDivName());
		jo.addProperty("pageDivNameEn", h.getPageDivNameEn());
		jo.addProperty("pageDiv", h.getPageDiv());
		jo.addProperty("pageSortNo", h.getPageSortNo());
		jo.addProperty("pageHtml", h.getPageHtml());
		jo.addProperty("pageHtmlEn", h.getPageHtmlEn());
		jo.addProperty("status", h.getStatus());
		return jo;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxSaveWebPagesDl(WebPagesDlDto dto, UserDto userDto) throws Exception {
		WebPagesDl webPagesDl=null;
		if(StringUtil.isBlank(dto.getId())) {
			webPagesDl = new WebPagesDl();
			webPagesDl.setId(seqGenService.getWebPagesDlNumber());
			webPagesDl.setPageId(dto.getPageId());
			webPagesDl.setCreateUser(userDto.getId());
			webPagesDl.setCreateTime(DateUtil.getSystemDateTimeObject());
		}else {
			webPagesDl = webPagesDlRespository.getEntityById(dto.getId());
			if(webPagesDl==null) {
				 throw new Exception("webPagesDl not found");
			}
			webPagesDl.setUpdateUser(userDto.getId());
			webPagesDl.setUpdateTime(DateUtil.getSystemDateTimeObject());
		}
		webPagesDl.setPageDiv(dto.getPageDiv());
		webPagesDl.setPageDivName(dto.getPageDivName());
		webPagesDl.setPageDivNameEn(dto.getPageDivNameEn());
		webPagesDl.setPageHtml(dto.getPageHtml()); 
		webPagesDl.setPageHtmlEn(dto.getPageHtmlEn()); 
		webPagesDl.setPageSortNo(dto.getPageSortNo()); 
		webPagesDl.setStatus(dto.getStatus());
		webPagesDl.setSystemMemo(dto.getSystemMemo()); 


		webPagesDlRespository.save(webPagesDl);
		
		
		JsonObject jo = new JsonObject();
		jo.addProperty("id", webPagesDl.getId());
		AjaxReturnBean bean = new AjaxReturnBean();
		bean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		bean.setValue(jo);
		
		return bean;
	}

	@Override
	public List<ErrorBean> validateInternalSaveWebPagesDl(WebPagesDlDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		if(StringUtil.isBlank(dto.getPageHtml())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_pageHtml");
			erBean.setLabelName("html編輯器");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}
		if(StringUtil.isBlank(dto.getPageHtmlEn())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_pageHtmlEn");
			erBean.setLabelName("html編輯器");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}
		
		
		return list;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxRemoveWebPagesDl(WebPagesDlDto dto) throws Exception {
		AjaxReturnBean bean = new AjaxReturnBean();
		WebPagesDl webPagesDl = webPagesDlRespository.getEntityById(dto.getId());
		
		webPagesDlRespository.delete(webPagesDl);
		bean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		
		return bean;
	}
	
	/**
	 * 取得靜態頁面明系列表
	 * @param model
	 * @return
	 */
	@Override
	public Long getMaxSortNo(WebPagesBean formBean) {
		
		return webPagesDlRespository.getMaxSortNo(formBean);
	}
	
}
