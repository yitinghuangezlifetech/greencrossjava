package tw.com.ezlifetech.ezReco.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.ComProfHtBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.ComProfHtDto;
import tw.com.ezlifetech.ezReco.dto.ProductDto;
import tw.com.ezlifetech.ezReco.dto.ProductPicDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.model.ComProfHt;
import tw.com.ezlifetech.ezReco.model.Product;
import tw.com.ezlifetech.ezReco.model.ProductPic;
import tw.com.ezlifetech.ezReco.model.RefProdClass;
import tw.com.ezlifetech.ezReco.respository.ComProfHtRespository;
import tw.com.ezlifetech.ezReco.service.ComProfHtService;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;
import tw.com.ezlifetech.ezReco.util.ValidatorUtil;

@Service
public class ComProfHtServiceImpl implements ComProfHtService {

	@Autowired
	private ComProfHtRespository comProfHtRespository;
	
	@Override
	public AjaxReturnBean ajaxSaveComProfHt(ComProfHtDto dto, UserDto userDto) throws Exception {
		ComProfHt comProfHt = null;
		if(StringUtil.isBlank(dto.getId())) {
			comProfHt = new ComProfHt();
			//product.setId(DateUtil.getSystemDateTime()+seqGenService.getProductNumber());
			comProfHt.setId(DateUtil.getSystemDateTime());
			dto.setId(comProfHt.getId());
			comProfHt.setCreateUser(userDto.getId());
			comProfHt.setCreateTime(DateUtil.getSystemDateTimeObject());
			comProfHt.setComCode(dto.getComCode());
		}else {
			comProfHt = comProfHtRespository.getEntityById(dto.getId());
			if(comProfHt == null) {
				 throw new Exception("product not found");
			}
			comProfHt.setUpdateUser(userDto.getId());
			comProfHt.setUpdateTime(DateUtil.getSystemDateTimeObject());
		}
		comProfHt.setComName(dto.getComName());
		comProfHt.setBan(dto.getBan());
		comProfHt.setComTel(dto.getComTel());
		comProfHt.setComEmail(dto.getComEmail());
		comProfHt.setComAdr(dto.getComAdr());
		comProfHtRespository.save(comProfHt);
		
		JsonObject jo = new JsonObject();
		jo.addProperty("id", dto.getId());
		AjaxReturnBean bean = new AjaxReturnBean();
		bean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		bean.setValue(jo);
		
		return bean;
	}

	@Override
	public void paperPage(Model model, ComProfHtDto comProfHtDto) {
		if(!StringUtil.isBlank(comProfHtDto.getId())) {
			ComProfHt comProfHt = comProfHtRespository.getEntityById(comProfHtDto.getId());
			BeanUtils.copyProperties(comProfHt, comProfHtDto , "id");
			
//			List<RefProdClass> refList = refProdClassRespository.findEntityListByJPQL("SELECT r FROM RefProdClass r WHERE r.proNo=?", dto.getId());
//			JsonArray ja = new JsonArray();
//			for(RefProdClass r :refList) {
//				ja.add(r.getClassSerno());
//			}
//			model.addAttribute("classSel", ja.toString());
			
		}
//		else {
//			model.addAttribute("classSel", "[]");
//		}
	}
	@Override
	public String comProfHtGridList(ComProfHtBean formBean) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(!StringUtil.isBlank(formBean.getComName())) {
			params.put("comName", "%"+formBean.getComName()+"%");
		}
		
		if(!StringUtil.isBlank(formBean.getBan())) {
			params.put("ban", formBean.getBan());
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT                            ");
		sql.append("  	com_id,                       ");
		sql.append("  	com_name,                       ");
		sql.append("  	ban,                     ");
		sql.append("  	com_tel,                     ");
		sql.append("  	com_adr,                     ");
		sql.append("  	com_email                     ");
//		sql.append("  	pro_code,                     ");
//		sql.append("  	pro_spec,                     ");
//		sql.append("  	CASE WHEN status='A' THEN '啟用' WHEN status='S' THEN '停用' END as status,  ");
//		sql.append("  	create_user,                  ");
//		sql.append("  	create_time,                  ");
//		sql.append("  	update_user,                  ");
//		sql.append("  	update_time                   ");
		sql.append("  FROM                              ");
		sql.append("  	com_prof_ht                       ");
		sql.append("  WHERE                             ");
		sql.append("  	1=1                           ");
		if(params.get("comName")!=null)
			sql.append("  AND com_name like :comName        ");
		if(params.get("ban")!=null)
			sql.append("  AND ban = :ban           ");
//		if(params.get("status")!=null)
//			sql.append("  AND status = :status              ");
//		sql.append(" ORDER BY pro_code ");

		
		List<Map<String, Object>> list = comProfHtRespository.findListMapBySQL_map(sql.toString(), params);
		
		
		
		return PageUtil.transToGridDataSource(list,formBean);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxRemove(ComProfHtDto dto) throws Exception {
		AjaxReturnBean bean = new AjaxReturnBean();
		comProfHtRespository.delete(dto.getId());
		
		bean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		return bean;
	}

	@Override
	public List<ErrorBean> validateInternalSave(ComProfHtDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		if(StringUtil.isBlank(dto.getComCode())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_comCode"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName("廠編"); // 欄位名稱
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else {
			if(dto.getComCode().length()>30) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_comCode"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("廠編"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.OUT_OF_RANGE.getDoc().replace("{!range}", "30"));
				list.add(erBean);
			}
		}
		
		if(StringUtil.isBlank(dto.getBan())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_ban"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName("統編"); // 欄位名稱
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else {
			if(dto.getBan().length()>10) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_ban"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("統編"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.OUT_OF_RANGE.getDoc().replace("{!range}", "10"));
				list.add(erBean);
			}else if(!ValidatorUtil.isValidTWBID(dto.getBan())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_ban"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("統編"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.DATA_FORMAT_NOT_MATCH.getDoc());
				list.add(erBean);
			}
		}
		
		if(StringUtil.isBlank(dto.getComName())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_comName"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName("公司名稱"); // 欄位名稱
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else {
			if(dto.getBan().length()>50) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_comName"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("公司名稱"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.OUT_OF_RANGE.getDoc().replace("{!range}", "50"));
				list.add(erBean);
			}
		}
		
		if(StringUtil.isBlank(dto.getComTel())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_comTel"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName("連絡電話"); // 欄位名稱
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else {
			if(dto.getComTel().length()>20) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_comTel"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("連絡電話"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.OUT_OF_RANGE.getDoc().replace("{!range}", "20"));
				list.add(erBean);
			}else if(!ValidatorUtil.isNumber(dto.getComTel())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_comTel"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("連絡電話"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.PLZ_INPUT_NUMBER.getDoc());
				list.add(erBean);
			}
		}
		
		if(StringUtil.isBlank(dto.getComEmail())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_comEmail"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName("Email"); // 欄位名稱
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else {
			if(dto.getComEmail().length()>100) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_comEmail"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("Email"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.OUT_OF_RANGE.getDoc().replace("{!range}", "100"));
				list.add(erBean);
			}else if(!ValidatorUtil.isValidEmail(dto.getComEmail())){
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_comEmail"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("Email"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.DATA_FORMAT_NOT_MATCH.getDoc());
				list.add(erBean);
			}
		}
		
		
		if(StringUtil.isBlank(dto.getComAdr())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_comAdr"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName("公司地址"); // 欄位名稱
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else {
			if(dto.getComEmail().length()>200) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_comAdr"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("公司地址"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.OUT_OF_RANGE.getDoc().replace("{!range}", "200"));
				list.add(erBean);
			}
		}
		
		
		if(StringUtil.isBlank(dto.getId())) {
			if(comProfHtRespository.isComCodeRepeat(dto)) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_comCode"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("廠編"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.DATA_EXIST.getDoc());
				list.add(erBean);
			}
		}
		
		return list;
	}	
	
	@Override
	public ComProfHtDto getComProfHt(UserDto userDto) throws Exception {
		ComProfHtDto comProfHtDto = new ComProfHtDto();
		ComProfHt comProfHt = null;
		
		if(null != userDto) {
			comProfHt = new ComProfHt();
//			String[] nameArray = new String[] {"com_id", "ban"};
//			Object[] valueArray = new Object[] {userDto.getComId(),userDto.getBan()};
//			comProfHt = comProfHtRespository.getEntityByProperties(nameArray, valueArray);
			comProfHt = comProfHtRespository.getEntityById(userDto.getComId());
			if(null != comProfHt) {
				BeanUtils.copyProperties(comProfHt, comProfHtDto);
				
				return comProfHtDto;
			}
		}
		
		return null;
	}

}
