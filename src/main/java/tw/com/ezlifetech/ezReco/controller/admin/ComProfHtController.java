package tw.com.ezlifetech.ezReco.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.ezlifetech.ezReco.bean.ComProfHtBean;
import tw.com.ezlifetech.ezReco.bean.ProductManagerBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.ComProfHtDto;
import tw.com.ezlifetech.ezReco.dto.ProductDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.ComProfHtService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;

@Controller
@RequestMapping("/admin/comProf")
public class ComProfHtController extends GenericController{

	@Autowired
	private ComProfHtService comProfHtService;
	private static final String JAVA_NAME="ProductManagerController";
	private static Logger logger = LoggerFactory.getLogger(ComProfHtController.class);
	
	@RequestMapping("/list")
	public String comProfList(Model model, @ModelAttribute(ComProfHtBean.beanFormName) ComProfHtBean formBean) {
		
		return "admin/comProfHt/list";
	}
	
	@RequestMapping(value="/addComProfHt" , method = RequestMethod.POST)
	public String addComProf(Model model, @ModelAttribute(ComProfHtDto.dtoName) ComProfHtDto comProfHtDto) {
		
		comProfHtService.paperPage(model,comProfHtDto);
		
		return "admin/comProfHt/addComProfHt";
	}
	
	
	@RequestMapping(value = "/ajaxComProfHtGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxComProfHtGridList(ComProfHtBean formBean, HttpServletRequest request) {

		return comProfHtService.comProfHtGridList(formBean);
	}
	
	@RequestMapping(value = "/ajaxSaveComProfHt", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveComProfHt(ComProfHtDto dto, HttpServletRequest request) {
		AjaxReturnBean result =null;
		try {
			validateInternalSave(dto);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			result = comProfHtService.ajaxSaveComProfHt(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(result);
	}

	private void validateInternalSave(ComProfHtDto dto) {
		setErrorBeans(comProfHtService.validateInternalSave(dto));
		
	}

	@RequestMapping(value = "/ajaxRemove", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemove(ComProfHtDto dto, HttpServletRequest request) {
		try {
			return AjaxUtil.success(comProfHtService.ajaxRemove(dto));
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.REMOVE_FAIL.getDoc());
		}
	}

}
