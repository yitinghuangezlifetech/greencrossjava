package tw.com.ezlifetech.ezReco.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.WebPagesBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.controller.GenericController;
import tw.com.ezlifetech.ezReco.dto.WebPagesDto;
import tw.com.ezlifetech.ezReco.dto.WebPagesDlDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.service.WebPagesDlService;
import tw.com.ezlifetech.ezReco.service.WebPagesService;
import tw.com.ezlifetech.ezReco.util.AjaxUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Controller
@RequestMapping("/admin/webPages")
public class WebPagesController extends GenericController{
	private static final String JAVA_NAME="WebPagesController";
	private static Logger logger = LoggerFactory.getLogger(WebPagesController.class);
	
	@Autowired
	private WebPagesService webPagesService;
	
	@Autowired
	private WebPagesDlService webPagesDlService;
	
	//首頁編輯
	@RequestMapping("/index")
	public String index(Model model, @ModelAttribute(WebPagesDto.dtoName) WebPagesDto dto) {
		dto.setPageType("index");
		webPagesService.detailPage(model,dto);
		
		return "admin/webPages/index";
	}
	
	//首頁編輯
	@RequestMapping("/seo")
	public String seo(Model model, @ModelAttribute(WebPagesDto.dtoName) WebPagesDto dto) {
		dto.setPageType("seo");
		webPagesService.detailPage(model,dto);
		
		return "admin/webPages/seo";
	}
	
	//首頁編輯
	@RequestMapping("/tgcc")
	public String tgcc(Model model, @ModelAttribute(WebPagesDto.dtoName) WebPagesDto dto) {
		dto.setPageType("tgcc");
		webPagesService.detailPage(model,dto);
		
		return "admin/webPages/tgcc";
	}
	
	@RequestMapping(value = "/ajaxSaveWebPages", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveWebPages(WebPagesDto dto, HttpServletRequest request) {
		AjaxReturnBean result =null;
		try {
			validateInternalSave(dto);
			if (hasError()) {
				return AjaxUtil.validateFail(mesgToJSONString());
			}
			result = webPagesService.ajaxSaveWebPages(dto,getLoginUser());
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(result);
	}
	
	@RequestMapping(value = "/ajaxRemove", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemove(WebPagesDto dto, HttpServletRequest request) {
		try {
			return AjaxUtil.success(webPagesService.ajaxRemove(dto));
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.REMOVE_FAIL.getDoc());
		}
	}


	private void validateInternalSave(WebPagesDto dto) {
		
		setErrorBeans(webPagesService.validateInternalSave(dto));
	}
	

	/**
	 * 取得靜態頁面明細列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ajaxWebPagesDlGridList", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxWebPagesDlGridList(WebPagesBean formBean, HttpServletRequest request) {
		//語系資料重畫面中取得
		
		return webPagesDlService.webPagesDlGridList(formBean); 
	}
	
	/**
     * ajax取得靜態頁面明細資料
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetWebPagesDlById", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxGetWebPagesDlById(WebPagesDlDto dto, HttpServletRequest request) {

		JsonObject result = null;
		try {
			result = webPagesDlService.ajaxGetWebPagesDlById(dto);
		} catch (Exception e) {
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}

		return AjaxUtil.success(result);
	}
	
	/**
	*  儲存要靜態頁面明細資料
	* @param model
	* @return
	*/
	@RequestMapping(value = "/ajaxSaveWebPagesDl", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxSaveWebPagesDl(WebPagesDlDto dto, HttpServletRequest request) {
		AjaxReturnBean ajaxReturnBean =null;
		try {
			// 先驗證輸入資訊
			validateInternalSaveWebPagesDl(dto);
			// 檢查有無驗證錯誤
			if(hasError()){
				// 有則回傳 檢覈的錯誤訊息
				return AjaxUtil.validateFail(mesgToJSONString());
			}	
			// 無檢核錯誤 則執行存檔動作
			ajaxReturnBean = webPagesDlService.ajaxSaveWebPagesDl(dto,getLoginUser());
		}catch (Exception e) {
			// 例外狀況
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(ajaxReturnBean);
	}
	
	/**
	*  驗證要編輯的靜態頁面明細資料
	* @param model
	* @return
	*/
	private void validateInternalSaveWebPagesDl(WebPagesDlDto dto) {
		setErrorBeans(webPagesDlService.validateInternalSaveWebPagesDl(dto));
	}
	
	/**
     * ajax刪除靜態頁面明細資料
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ajaxRemoveWebPagesDl", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxRemoveWebPagesDl(WebPagesDlDto dto, HttpServletRequest request) {
		AjaxReturnBean ajaxReturnBean =null;
		try {
			// 先驗證輸入資訊

			// 檢查有無驗證錯誤
			if(hasError()){
				// 有則回傳 檢覈的錯誤訊息
				return AjaxUtil.validateFail(mesgToJSONString());
			}	
			// 無檢核錯誤 則執行存檔動作
			ajaxReturnBean = webPagesDlService.ajaxRemoveWebPagesDl(dto);
		}catch (Exception e) {
			// 例外狀況
			handleException(e,logger,"JAVA_NAME:"+JAVA_NAME+" Error Mesg:"+e.getMessage());
			return AjaxUtil.fail(AjaxMesgs.SAVE_FAIL.getDoc());
		}
		return AjaxUtil.success(ajaxReturnBean);
	}

	/**
	 * 取得靜態頁面明細列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ajaxMaxSortNo", produces = "application/json;charset=utf-8")
	public @ResponseBody String ajaxMaxSortNo(WebPagesBean formBean, HttpServletRequest request) {
		//語系資料重畫面中取得
		
		return webPagesDlService.getMaxSortNo(formBean).toString(); 
	}}
