package tw.com.ezlifetech.ezReco.service.impl;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.model.WebPages;
import tw.com.ezlifetech.ezReco.respository.WebPagesRespository;
import tw.com.ezlifetech.ezReco.service.AboutUsService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AboutUsServiceImpl implements AboutUsService{

	@Autowired
	private WebPagesRespository webPagesRespository;
	
	
	@Override
	public void paperPage(Model model, String language) {
		WebPages  w = webPagesRespository.getEntityByJPQL("SELECT w FROM WebPages w WHERE w.pageType = ?", "tgcc");
		if("en".equals(language)) {
			model.addAttribute("webHTML", StringEscapeUtils.unescapeXml(w.getPageContentEn()));
		}else {
			model.addAttribute("webHTML", StringEscapeUtils.unescapeXml(w.getPageContent()));
		}
		
		
		
	}

	
	
}
