package tw.com.ezlifetech.ezReco.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.DispatcherProfileBean;
import tw.com.ezlifetech.ezReco.bean.FileUploadBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.common.service.SeqGenService;
import tw.com.ezlifetech.ezReco.dto.DispatcherProfileDto;
import tw.com.ezlifetech.ezReco.dto.ProductPicDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.model.ComProfHt;
import tw.com.ezlifetech.ezReco.model.DispatcherProfile;
import tw.com.ezlifetech.ezReco.model.ProductPic;
import tw.com.ezlifetech.ezReco.model.WareHouseProf;
import tw.com.ezlifetech.ezReco.respository.ComProfHtRespository;
import tw.com.ezlifetech.ezReco.respository.DispatcherProfileRespository;
import tw.com.ezlifetech.ezReco.respository.WareHouseProfRespository;
import tw.com.ezlifetech.ezReco.service.DispatcherProfileService;
import tw.com.ezlifetech.ezReco.util.ConfigUtil;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;
import tw.com.ezlifetech.ezReco.util.SysParamsUtils;
import tw.com.ezlifetech.ezReco.util.ValidatorUtil;

@Service
public class DispatcherProfileServiceImpl implements DispatcherProfileService{

	@Autowired
	private DispatcherProfileRespository dispatcherProfileRespository;
	
	@Autowired
	private ComProfHtRespository comProfHtRespository;
	
	@Autowired
	private WareHouseProfRespository wareHouseProfRespository;
	
	@Autowired
	private SeqGenService seqGenService;
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxSaveDispatcherProfile(DispatcherProfileDto dto, UserDto userDto) throws Exception {
		DispatcherProfile dispatcherProfile = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		if(StringUtil.isBlank(dto.getId())) {
			dispatcherProfile = new DispatcherProfile();
			//product.setId(DateUtil.getSystemDateTime()+seqGenService.getProductNumber());
			dispatcherProfile.setId(getEmpId(dto));
			
			dispatcherProfile.setCreateUser(userDto.getId());
			dispatcherProfile.setCreateTime(DateUtil.getSystemDateTimeObject());
			
		}else {
			dispatcherProfile = dispatcherProfileRespository.getEntityById(dto.getId());
			if(dispatcherProfile == null) {
				 throw new Exception("product not found");
			}
			dispatcherProfile.setUpdateUser(userDto.getId());
			dispatcherProfile.setUpdateTime(DateUtil.getSystemDateTimeObject());
		}
		dispatcherProfile.setName(dto.getName());
		dispatcherProfile.setCompId(dto.getCompId());
		dispatcherProfile.setIdNumber(dto.getIdNumber());
		dispatcherProfile.setPhone(dto.getPhone());
		dispatcherProfile.setAddr(dto.getAddr());
		dispatcherProfile.setSex(dto.getSex());
		dispatcherProfile.setWhId(dto.getWhId());
		dispatcherProfile.setTimingOrOiece(dto.getTimingOrOiece());
		dispatcherProfile.setEducation(dto.getEducation());
		dispatcherProfile.setStackerLicense(dto.getStackerLicense());
		dispatcherProfile.setElectricTrailer(dto.getElectricTrailer());
		dispatcherProfile.setLaborPortection(dto.getLaborPortection());
		dispatcherProfile.setHealthInsurance(dto.getHealthInsurance());
		dispatcherProfile.setResPermitNumber(dto.getResPermitNumber());
		dispatcherProfile.setWorkPermitNumber(dto.getWorkPermitNumber());
		dispatcherProfile.setStudentPemitNumber(dto.getStudentPemitNumber());
		
		dispatcherProfile.setBirthday(sdf.parse(dto.getBirthday()));
		dispatcherProfile.setStackerLicenseLimitDate(sdf.parse(dto.getStackerLicenseLimitDate()));
		dispatcherProfile.setResPermitLimitDate(sdf.parse(dto.getResPermitLimitDate()));
		dispatcherProfile.setWorkPermitNumberLimitDate(sdf.parse(dto.getWorkPermitNumberLimitDate()));
		dispatcherProfile.setStudentPemitNumberLimitDate(sdf.parse(dto.getStudentPemitNumberLimitDate()));
		
		//dispatcherProfile.setPhotoPath(dto.getPhotoPath());
		//dispatcherProfile.setBirthday(dto.getBirthday());
		dispatcherProfileRespository.save(dispatcherProfile);
		
		JsonObject jo = new JsonObject();
		jo.addProperty("id", dto.getId());
		AjaxReturnBean bean = new AjaxReturnBean();
		bean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		bean.setValue(jo);
		
		return bean;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public DispatcherProfileDto updateDispatcherPhotoPath(DispatcherProfileDto dto, UserDto userDto) throws Exception {
		DispatcherProfileDto dispatcherProfileDto = new DispatcherProfileDto();
		DispatcherProfile dispatcherProfile = dispatcherProfileRespository.getEntityById(dto.getId());
		dispatcherProfile.setPhotoPath(dto.getPhotoPath());
		dispatcherProfile.setUpdateUser(userDto.getId());
		dispatcherProfile.setUpdateTime(DateUtil.getSystemDateTimeObject());
		dispatcherProfileRespository.save(dispatcherProfile);
		BeanUtils.copyProperties(dispatcherProfile, dispatcherProfileDto , "id");
		setDateToStr(dispatcherProfile, dispatcherProfileDto);
		return dispatcherProfileDto;
	}
	
	@Override
	public void paperPage(Model model, DispatcherProfileDto dispatcherProfileDto) {
		if(!StringUtil.isBlank(dispatcherProfileDto.getId())) {
			DispatcherProfile dispatcherProfile = dispatcherProfileRespository.getEntityById(dispatcherProfileDto.getId());
			BeanUtils.copyProperties(dispatcherProfile, dispatcherProfileDto , "id");
			setDateToStr(dispatcherProfile, dispatcherProfileDto);
		}	
		model.addAttribute("compIdSelectList", getCompIdSelectList());
		model.addAttribute("whIdSelectList", getwhIdSelectList());
	}

	private List<Map<String,Object>> getwhIdSelectList() {
		List<Map<String,Object>> whList = new  ArrayList<>();
		whList = wareHouseProfRespository.getAllwhCodeAndNameListMap();
		return whList;
	}

	public List<Map<String,Object>> getCompIdSelectList() {
		List<Map<String,Object>> compList = new  ArrayList<>();
		compList = comProfHtRespository.getAllCompCodeAndNameListMap();
		return compList;
	}
	
	
	@Override
	public String dispatcherProfileGridList(DispatcherProfileBean formBean) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(!StringUtil.isBlank(formBean.getName())) {
			params.put("name", "%"+formBean.getName()+"%");
		}
		
		if(!StringUtil.isBlank(formBean.getIdNumber())) {
			params.put("idNumber", formBean.getIdNumber());
		}
		
		if(!StringUtil.isBlank(formBean.getCompId())) {
			params.put("compId", formBean.getCompId());
		}
				
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT                            ");
		sql.append("  	id,                       ");
		sql.append("  	comp_id,                       ");
		sql.append("  	name,                       ");
		sql.append("  	id_number,                     ");
		sql.append("  	birthday,                     ");
		sql.append("  	sex,                     ");
		sql.append("  	addr                     ");
//		sql.append("  	pro_code,                     ");
//		sql.append("  	pro_spec,                     ");
//		sql.append("  	CASE WHEN status='A' THEN '啟用' WHEN status='S' THEN '停用' END as status,  ");
//		sql.append("  	create_user,                  ");
//		sql.append("  	create_time,                  ");
//		sql.append("  	update_user,                  ");
//		sql.append("  	update_time                   ");
		sql.append("  FROM                              ");
		sql.append("  	dispatcher_profile                       ");
		sql.append("  WHERE                             ");
		sql.append("  	1=1                           ");
		if(params.get("name")!=null)
			sql.append("  AND name like :name        ");
		if(params.get("idNumber")!=null)
			sql.append("  AND id_number = :idNumber           ");
		if(params.get("compId")!=null)
			sql.append("  AND comp_id = :compId           ");		
//		if(params.get("status")!=null)
//			sql.append("  AND status = :status              ");
//		sql.append(" ORDER BY pro_code ");

		
		List<Map<String, Object>> list = dispatcherProfileRespository.findListMapBySQL_map(sql.toString(), params);
		return PageUtil.transToGridDataSource(list,formBean);
	}
	
	public List<Map<String,Object>> dispatcherProfileList(String userId,String comId) {
		List<Map<String,Object>> dispatcherProfileList = new  ArrayList<>();
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(!StringUtil.isBlank(userId)) {
			params.put("userId", "%"+userId+"%");
		}
				
		if(!StringUtil.isBlank(comId)) {
			params.put("compId", comId);
		}
				
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT                            ");
		sql.append("  	id,                       ");
		sql.append("  	user_id,                       ");
		sql.append("  	comp_id,                       ");
		sql.append("  	name,                       ");
		sql.append("  	id_number,                     ");
		sql.append("  	birthday,                     ");
		sql.append("  	sex,                     ");
		sql.append("  	addr                     ");
		sql.append("  FROM                              ");
		sql.append("  	dispatcher_profile                       ");
		sql.append("  WHERE                             ");
		sql.append("  	1=1                           ");
		if(params.get("userId")!=null)
			sql.append("  AND user_id like :userId        ");
		if(params.get("compId")!=null)
			sql.append("  AND comp_id = :compId           ");		

		
		dispatcherProfileList = dispatcherProfileRespository.findListMapBySQL_map(sql.toString(), params);
		
		return dispatcherProfileList;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxRemove(DispatcherProfileDto dto) throws Exception {
		AjaxReturnBean bean = new AjaxReturnBean();
		dispatcherProfileRespository.delete(dto.getId());
		
		bean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		return bean;
	}

	@Override
	public List<ErrorBean> validateInternalSave(DispatcherProfileDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		boolean isError = false;
		if(StringUtil.isBlank(dto.getName())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_Name"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName("姓名"); // 欄位名稱
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else {
			if(dto.getName().length()>10) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_Name"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("姓名"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.OUT_OF_RANGE.getDoc().replace("{!range}", "10"));
				list.add(erBean);
			}
		}
		
		if(StringUtil.isBlank(dto.getCompId())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_compId"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName(""); // 欄位名稱
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "所屬公司"));
			list.add(erBean);
			isError = true;
		}
		
		if(StringUtil.isBlank(dto.getWhId())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_whId"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName(""); // 欄位名稱
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "倉別"));
			list.add(erBean);
			isError = true;
		}
		
		if(StringUtil.isBlank(dto.getIdNumber())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_idNumber"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName("身分證字號"); // 欄位名稱
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
			isError = true;
		}else {
			if(!ValidatorUtil.isValidTWPID(dto.getIdNumber())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_idNumber"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("身分證字號"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.DATA_FORMAT_NOT_MATCH.getDoc());
				list.add(erBean);
				isError = true;
			}
		}
		
		if(StringUtil.isBlank(dto.getAddr())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_addr"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName("通訊地址"); // 欄位名稱
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else {
			if(dto.getAddr().length()>200) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_addr"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("通訊地址"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.OUT_OF_RANGE.getDoc().replace("{!range}", "200"));
			}
		}
		
		if(StringUtil.isBlank(dto.getPhone())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_phone"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName("連絡電話"); // 欄位名稱
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}else {
			if(dto.getPhone().length()>50) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_phone"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName("連絡電話"); // 欄位名稱
				erBean.setMesg(ErrorMesgs.OUT_OF_RANGE.getDoc().replace("{!range}", "50"));
			}
		}
		
		if(StringUtil.isBlank(dto.getId())) {
			if(!isError) {
				String empId = getEmpId(dto);
				DispatcherProfile  d = dispatcherProfileRespository.getEntityById(empId);
				if(d != null) {
					ErrorBean erBean = new ErrorBean();
					erBean.setErrSpanId("err_empId"); // 對應前端錯誤提示 的 Id 
					erBean.setLabelName("派遣人員資料已存在"); // 欄位名稱
					erBean.setMesg(ErrorMesgs.DATA_EXIST.getDoc());
					list.add(erBean);
				}
			}
			
		}
		
		return list;
	}

	private String getEmpId(DispatcherProfileDto dto) {
		String res = dto.getWhId()+dto.getCompId()+dto.getIdNumber().substring(6, dto.getIdNumber().length());
		return res;
	}	
	
	private DispatcherProfileDto setDateToStr(DispatcherProfile dispatcherProfile, DispatcherProfileDto dispatcherProfileDto) {
		if(dispatcherProfile.getBirthday() != null) {	
			dispatcherProfileDto.setBirthday(dateToStr(dispatcherProfile.getBirthday()));
		}	
		if(dispatcherProfile.getStackerLicenseLimitDate() != null) {	
			dispatcherProfileDto.setStackerLicenseLimitDate(dateToStr(dispatcherProfile.getStackerLicenseLimitDate()));
		}	
		if(dispatcherProfile.getResPermitLimitDate() != null) {	
			dispatcherProfileDto.setResPermitLimitDate(dateToStr(dispatcherProfile.getResPermitLimitDate()));
		}	
		if(dispatcherProfile.getWorkPermitNumberLimitDate() != null) {	
			dispatcherProfileDto.setWorkPermitNumberLimitDate(dateToStr(dispatcherProfile.getWorkPermitNumberLimitDate()));
		}	
		if(dispatcherProfile.getStudentPemitNumberLimitDate() != null) {	
			dispatcherProfileDto.setStudentPemitNumberLimitDate(dateToStr(dispatcherProfile.getStudentPemitNumberLimitDate()));
		}	
		return dispatcherProfileDto;
	}
	
	private String dateToStr(Date date) {
		//設定日期格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		//進行轉換
		String dateString = sdf.format(date);
		return dateString;		
	}
	
	@Override
	public DispatcherProfileDto uploadFile(MultipartFile asyncFiles, DispatcherProfileDto dto) throws Exception {
		//dto.setTmpId(seqGenService.getProductPicNumber());
		//dto.setContentType(asyncFiles.getContentType());
		String filePath = ConfigUtil.getValue("uploadpath")+File.separator+ConfigUtil.DISPATCHER_PROFILE_PIC_PATH;
		File dir = new File(filePath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		String origFileName = asyncFiles.getOriginalFilename();
		String fileName = dto.getId()+origFileName;
		File file = new File(filePath, fileName);
		FileUtils.writeByteArrayToFile(file, asyncFiles.getBytes());
		//File.separator會依不同OS判斷要用"/"or"\"去串接
		dto.setPhotoPath(ConfigUtil.DISPATCHER_PROFILE_PIC_PATH+File.separator+fileName);	
		
		return dto;
		//

	}
	
	@Override
	public void getDispatcherPic(HttpServletResponse response, String id) throws Exception {
		DispatcherProfile dispatcherProfile = dispatcherProfileRespository.getEntityById(id);
		String filePath = ConfigUtil.getValue("uploadpath")+File.separator;
		File picFile = null;
			picFile = new File (filePath,dispatcherProfile.getPhotoPath());
			response.setContentType("image/png");

			
		
		
		response.setContentLength((int)picFile.length());
		FileCopyUtils.copy(Files.readAllBytes(picFile.toPath()), response.getOutputStream());
		
		
	}
}
