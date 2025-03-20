package tw.com.ezlifetech.ezReco.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.NewsHtBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.service.SeqGenService;
import tw.com.ezlifetech.ezReco.dto.NewsHtDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.model.NewsHt;
import tw.com.ezlifetech.ezReco.model.Product;
import tw.com.ezlifetech.ezReco.model.RefProdClass;
import tw.com.ezlifetech.ezReco.respository.NewsHtRepository;
import tw.com.ezlifetech.ezReco.service.NewsHtService;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class NewsHtServiceImpl implements NewsHtService{
	
	@Autowired
	private SeqGenService seqGenService;
	
	@Autowired
	private NewsHtRepository newsHtRepository;
	
	@Override
	public String ajaxHtGridList(NewsHtBean bean, UserDto loginUser) {
		List<Map<String, Object>>  result = newsHtRepository.ajaxHtGridList(bean);
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(Map<String,Object> map : result) {
			if(StringUtils.isNotBlank((String)map.get("title_en")) && StringUtils.isNotBlank((String)map.get("content_en"))) {
				map.remove("title");
				map.remove("content");
				map.put("title", (String)map.get("title_en"));
				map.put("content", (String)map.get("content_en"));
			}
			list.add(map);
		}
		
		return PageUtil.transToGridDataSource(result,bean); 
	}
	
	@Override
	public void paper(Model model,NewsHtBean bean) {
		List<Map<String, Object>>  result = newsHtRepository.ajaxHtGridList(bean);
		model.addAttribute("newsList",result);
	}
	
	@Override
	public void paperEdit(Model model, NewsHtBean dto,UserDto loginUser) throws Exception {
		if(!StringUtil.isBlank(dto.getId())) {
			NewsHt newsHt = newsHtRepository.getEntityById(dto.getId());
			BeanUtils.copyProperties(newsHt, dto , "id");
			model.addAttribute("newsHtDto", dto);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxSaveHt(NewsHtDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		JsonObject jo = new JsonObject();
		NewsHt ht = new NewsHt();
		ht = newsHtRepository.getEntityById(dto.getId());
		
		//目前時間
		Date date = new Date();
		//設定日期格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		//進行轉換
		String dateString = sdf.format(date);
		
		if(ht == null) {
			ht = new NewsHt();
			ht.setId(seqGenService.getSystemTimeRandomNumber());
			ht.setActiveTimeS(dto.getActiveTimeS());
			ht.setActiveTimesE(dto.getActiveTimeE());
			ht.setContent(dto.getContent());
			ht.setCreateTime(dateString);
			ht.setCreateUser("admin");
			ht.setUpdateTime(dateString);
			ht.setUpdateUser("admin");
			ht.setNewsType(dto.getNewsType());
			ht.setStatus(dto.getStatus());
			ht.setTel(dto.getTel());
			ht.setTitle(dto.getTitle());
			ht.setContact(dto.getContact());			
			ht.setEmail(dto.getEmail());
			ht.setHtml(dto.getHtml());
			ht.setTitleEn(dto.getTitleEn());
			ht.setContentEn(dto.getContentEn());
//			ht.setHtml(dto.get);			
//			ht.setShowTimeS(showTimeS);
//			ht.setShowTimeE(dto.get);
		}else {
			ht.setActiveTimeS(dto.getActiveTimeS());
			ht.setActiveTimesE(dto.getActiveTimeE());
			ht.setContent(dto.getContent());
			ht.setCreateTime(dateString);
			ht.setCreateUser("admin");
			ht.setNewsType(dto.getNewsType());
			ht.setStatus(dto.getStatus());
			ht.setTel(dto.getTel());
			ht.setTitle(dto.getTitle());
			ht.setContact(dto.getContact());			
			ht.setEmail(dto.getEmail());
			ht.setHtml(dto.getHtml());
			ht.setTitleEn(dto.getTitleEn());
			ht.setContentEn(dto.getContentEn());
		}
		
		newsHtRepository.save(ht);
			
		jo.addProperty("htId", ht.getId());
		
		ajaxReturnBean.setValue(jo);
		ajaxReturnBean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxRemoveHt(NewsHtDto dto) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		
		newsHtRepository.delete(dto.getId());
		
		ajaxReturnBean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
		
	}
	
}
