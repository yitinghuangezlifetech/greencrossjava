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
import tw.com.ezlifetech.ezReco.dto.WebPagesDto;
import tw.com.ezlifetech.ezReco.dto.ProductPicDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.model.ProductPic;
import tw.com.ezlifetech.ezReco.model.WebPages;
import tw.com.ezlifetech.ezReco.respository.WebPagesDlRespository;
import tw.com.ezlifetech.ezReco.respository.WebPagesRespository;
import tw.com.ezlifetech.ezReco.service.WebPagesDlService;
import tw.com.ezlifetech.ezReco.service.WebPagesService;
import tw.com.ezlifetech.ezReco.util.ConfigUtil;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;
import tw.com.ezlifetech.ezReco.util.ValidatorUtil;

@Service
public class WebPagesServiceImpl  implements WebPagesService{
	
	@Autowired
	private WebPagesDlService webPagesDlService;
	
	@Autowired
	private WebPagesRespository webPagesRespository;
	
	@Autowired
	private WebPagesDlRespository webPagesDlRespository;
	
	@Autowired
	private SeqGenService seqGenService;

	/**
	 * 取得欲編輯的頁面資料
	 * @param model
	 * @return
	 */
	@Override
	public void detailPage(Model model,WebPagesDto dto) {
		
		
		
		
		WebPages webPages = null;
		
		
		webPages = webPagesRespository.getEntityByJPQL("SELECT w FROM WebPages w WHERE w.pageType = ?", dto.getPageType());
		if(webPages!=null) {
			BeanUtils.copyProperties(webPages,dto);
		}
		
		
		
		
		//		String[] nameArray = {"id", "pageType"};
//		Object[] valueArray = {dto.getId(), dto.getPageType()};
//		WebPages webPages = webPagesRespository.getEntityByProperties(nameArray, valueArray);

//		if(dto.getId()!= null) {
//			//取得可用語系選單
//			webPages = webPagesRespository.getEntityById(dto.getId());
//			
//			BeanUtils.copyProperties(webPages,dto);
//		
//		}else {
//			//取得可用語系選單
//			webPages = webPagesRespository.getEntityByProperty("pageType", dto.getPageType());
//
//			if(webPages!= null) {
//				BeanUtils.copyProperties(webPages,dto);
//			}
//		}

		model.addAttribute("webPagesDto", dto);
		
		WebPagesBean formBean = new WebPagesBean();
		if(null != webPages) {
		    formBean.setPageId(webPages.getId());
			String webPagesDlSortNo = (webPagesDlService.getMaxSortNo(formBean)).toString();
			model.addAttribute("webPagesDlSortNo", webPagesDlSortNo);
		}else {
			model.addAttribute("webPagesDlSortNo", 0);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxSaveWebPages(WebPagesDto dto, UserDto userDto) throws Exception {
		WebPages webPages=null;
		if(StringUtil.isBlank(dto.getId())) {
			webPages = new WebPages();
			webPages.setId(seqGenService.getWebPagesNumber());
			webPages.setCreateUser(userDto.getId());
			webPages.setCreateTime(DateUtil.getSystemDateTimeObject());
		}else {
			webPages = webPagesRespository.getEntityById(dto.getId());
			if(webPages==null) {
				 throw new Exception("webPages not found");
			}
			webPages.setUpdateUser(userDto.getId());
			webPages.setUpdateTime(DateUtil.getSystemDateTimeObject());
		}
	
		webPages.setPageName(dto.getPageName()); 
		webPages.setPageNameEn(dto.getPageNameEn()); 
		webPages.setPageUrl(dto.getPageUrl()); 
		webPages.setPageUrl(dto.getPageUrl()); 
		webPages.setPageType(dto.getPageType());
		webPages.setPageTitle(dto.getPageTitle()); 
		webPages.setPageTitleEn(dto.getPageTitleEn()); 
		webPages.setPageContent(dto.getPageContent()); 
		webPages.setPageContentEn(dto.getPageContentEn()); 
		webPages.setPageHtml(dto.getPageHtml()); 
		webPages.setPageHtmlEn(dto.getPageHtmlEn()); 
		webPages.setPageSeo(dto.getPageSeo()); 
		webPages.setPageGa(dto.getPageGa()); 
		webPages.setPageFb(dto.getPageFb()); 
		webPages.setPageHit(dto.getPageHit()); 
		webPages.setStatus(dto.getStatus());
		webPages.setSystemMemo(dto.getSystemMemo()); 


		webPagesRespository.save(webPages);
		
		
		JsonObject jo = new JsonObject();
		jo.addProperty("id", webPages.getId());
		AjaxReturnBean bean = new AjaxReturnBean();
		bean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		bean.setValue(jo);
		
		return bean;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxRemove(WebPagesDto dto) throws Exception {
		AjaxReturnBean bean = new AjaxReturnBean();
		WebPages webPages = webPagesRespository.getEntityById(dto.getId());
		
		webPagesRespository.delete(webPages);
		bean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		
		return bean;
	}
	
	/**
	 * 取得靜態頁面明系列表
	 * @param model
	 * @return
	 */
	@Override
	public String webPagesDlGridList(WebPagesBean formBean) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(!StringUtil.isBlank(formBean.getPageId())) {
			params.put("page_id",formBean.getPageId());
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select id,page_id,page_div_name,page_div_name_en,page_Div,page_Sort_No,page_Html,page_Html_En ");
		sql.append(" from web_pages_dl where 1 = 1 ");
		if(params.get("page_id")!=null)
			sql.append("  and page_id = :page_id ");
		
		List<Map<String, Object>> list=webPagesDlRespository.findListMapBySQL_map(sql.toString(), params);
		
		return PageUtil.transToGridDataSource(list,formBean); 
	}
	

	@Override
	public List<ErrorBean> validateInternalSave(WebPagesDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		if(StringUtil.isBlank(dto.getPageName())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_pageName");
			erBean.setLabelName("頁面名稱");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else if(dto.getPageName().length()>200) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_pageName");
			erBean.setLabelName("頁面名稱");
			erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "200"));
			list.add(erBean);
		}
		
		
		return list;
	}

	
}
