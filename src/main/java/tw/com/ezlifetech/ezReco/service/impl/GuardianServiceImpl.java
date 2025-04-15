package tw.com.ezlifetech.ezReco.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
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

import tw.com.ezlifetech.ezReco.bean.GuardianBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.service.SeqGenService;
import tw.com.ezlifetech.ezReco.dto.GuardianDto;
import tw.com.ezlifetech.ezReco.dto.GuardianPicDto;
import tw.com.ezlifetech.ezReco.dto.ProductDto;
import tw.com.ezlifetech.ezReco.dto.ProductPicDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.model.Guardian;
import tw.com.ezlifetech.ezReco.model.GuardianPic;
import tw.com.ezlifetech.ezReco.model.ProductClass;
import tw.com.ezlifetech.ezReco.model.ProductPic;
import tw.com.ezlifetech.ezReco.respository.GuardianPicRepository;
import tw.com.ezlifetech.ezReco.respository.GuardianRepository;
import tw.com.ezlifetech.ezReco.respository.ProductClassRespository;
import tw.com.ezlifetech.ezReco.service.GuardianService;
import tw.com.ezlifetech.ezReco.service.ProductClassManagerService;
import tw.com.ezlifetech.ezReco.util.ConfigUtil;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GuardianServiceImpl implements GuardianService{
	
	@Autowired
	private SeqGenService seqGenService;
	
	@Autowired
	private GuardianRepository guardianRepository;
	
	@Autowired
	private ProductClassRespository productClassRespository;
	
	@Autowired
	private ProductClassManagerService productClassManagerService;
	
	@Autowired
	private GuardianPicRepository guardianPicRespository;
	
	
	@Override
	public String ajaxHtGridList(GuardianBean bean, UserDto loginUser) {
		List<Map<String, Object>>  result = guardianRepository.ajaxHtGridList(bean);
		
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
		
		return PageUtil.transToGridDataSource(list,bean); 
	}
	
	@Override
	public void paper(Model model,GuardianBean bean,String language,String classType) {
		List<Map<String, Object>>  result = guardianRepository.ajaxHtGridList(bean);
		model.addAttribute("guardianList",result);
		model.addAttribute("prodClassTreeMap",ajaxGetAllProdClass("　",language,classType));
	}
	
	@Override
	public void paperEdit(Model model, GuardianBean dto,UserDto loginUser) throws Exception {
		if(!StringUtil.isBlank(dto.getId())) {
			Guardian guardian = guardianRepository.getEntityById(dto.getId());
			ProductClass productClass = productClassRespository.getEntityById(guardian.getClassId());
			BeanUtils.copyProperties(guardian, dto , "id");
			dto.setClassName(productClass.getClassName());
			model.addAttribute("guardianDto", dto);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxSaveHt(GuardianDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		JsonObject jo = new JsonObject();
		Guardian ht = new Guardian();
		ht = guardianRepository.getEntityById(dto.getId());
		
		//目前時間
		Date date = new Date();
		//設定日期格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		//進行轉換
		String dateString = sdf.format(date);
		
		if(ht == null) {
			ht = new Guardian();
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
			ht.setClassId(dto.getClassId());
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
			ht.setClassId(dto.getClassId());
		}
		
		guardianRepository.save(ht);
			
		jo.addProperty("htId", ht.getId());
		
		ajaxReturnBean.setValue(jo);
		ajaxReturnBean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxRemoveHt(GuardianDto dto) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		
		guardianRepository.delete(dto.getId());
		
		ajaxReturnBean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
		
	}
	
	private List<Map<String,Object>> ajaxGetAllProdClass(String preStr,String language,String classType) {
		List<ProductClass> pList = new ArrayList<ProductClass>();
		if("1".equals(classType)) {
			pList = productClassRespository.findEntityListByJPQL("SELECT p FROM ProductClass p WHERE p.classType='1' AND p.classParentId=?", "000000");
		}
		else if("2".equals(classType)) {
			pList = productClassRespository.findEntityListByJPQL("SELECT p FROM ProductClass p WHERE p.classType='2' AND p.classParentId=?", "000000");
		}
		List<Map<String,Object>>list = new ArrayList<Map<String,Object>>();
		
		
		
		
		
		if(pList==null || pList.size()==0) {
			
		}else {
			for(ProductClass p : pList) {
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("classId", p.getId().toString());
				String className ="";
				if("en".equals(language)) {
					className = StringUtil.repeatStr(0,preStr)+"‧"+(StringUtil.isBlank(p.getClassNameEn())?"No classification":p.getClassNameEn());
				}else {
					className = StringUtil.repeatStr(0,preStr)+"‧"+p.getClassName();
					
				}
				
				
				map.put("className",className );
				map.put("isRoot", "Y");
				
				
				List<Map<String,String>>childlist = new ArrayList<Map<String,String>>();
				productClassManagerService.deepProdClass(childlist, 1, "　", p.getId(),language);
				map.put("cList", childlist);
				list.add(map);
			}
		}
		
		
		
		return list;
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean saveGuardianPic(GuardianPicDto guardianPicDto, UserDto loginUser) {
		AjaxReturnBean bean = new AjaxReturnBean();
		GuardianPic pic = new GuardianPic();
		pic.setId(guardianPicDto.getTmpId());
		pic.setProNo(guardianPicDto.getProNo());
		pic.setPicPatch(guardianPicDto.getPicPatch());
		pic.setCreateUser(loginUser.getId());
		pic.setCreateTime(DateUtil.getSystemDateTimeObject());
		pic.setContentType(guardianPicDto.getContentType());
		guardianPicRespository.save(pic);
		
		JsonObject jo = new JsonObject();
		jo.addProperty("prodId", pic.getId());
		bean.setMessage(AjaxMesgs.UPLAOD_SUCCESSFUL.getDoc());
		bean.setValue(jo);
		return bean;
	}
	
	@Override
	public void uploadFile(MultipartFile asyncFiles, GuardianPicDto dto) throws Exception {
		dto.setTmpId(seqGenService.getProductPicNumber());
		dto.setContentType(asyncFiles.getContentType());
		String filePath = ConfigUtil.getValue("uploadpath")+File.separator+ConfigUtil.PRODUCT_PIC_PATH;
		File dir = new File(filePath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		String origFileName = asyncFiles.getOriginalFilename();
		String fileName = dto.getTmpId()+StringUtil.getSubFileName(origFileName);
		File file = new File(filePath, fileName);
		FileUtils.writeByteArrayToFile(file, asyncFiles.getBytes());
		//File.separator會依不同OS判斷要用"/"or"\"去串接
		dto.setPicPatch(ConfigUtil.PRODUCT_PIC_PATH+File.separator+fileName);
		
		
	}
	
	@Override
	public void getGuardianPic(HttpServletResponse response, String value) throws Exception {
		
		String filePath = ConfigUtil.getValue("uploadpath")+File.separator;
		File picFile = null;
		GuardianPic pic = guardianPicRespository.getEntityById(value);
		if(pic!=null) {
			String picPatch = pic.getPicPatch().replace("\\", "/");
	        picFile = Paths.get(filePath, picPatch).toFile();
	        response.setContentType(pic.getContentType());
		}else {
			try (InputStream is = getClass().getResourceAsStream("/shopping-bag.png")) {
	            if (is == null) {
	                throw new FileNotFoundException("shopping-bag.png資源不存在");
	            }
	            response.setContentType("image/png");
	            response.setContentLength(is.available());
	            FileCopyUtils.copy(is, response.getOutputStream());
	            return;
	        }
		}
		
		response.setContentLength((int)picFile.length());
		FileCopyUtils.copy(Files.readAllBytes(picFile.toPath()), response.getOutputStream());
		
	}
	
	
	@Override
	public JsonArray ajaxGetAllGuardianPic(GuardianDto dto) {
		JsonArray ja = new JsonArray();
		
		if(!StringUtil.isBlank(dto.getId())) {
			List<GuardianPic> list =guardianPicRespository.findByJPQL("SELECT p FROM GuardianPic p WHERE p.proNo = ? ORDER BY p.createTime ", dto.getId());
			for(GuardianPic p : list) {
				String isMain="";
				JsonObject jo = new JsonObject();
				jo.addProperty("name", p.getId());
				
				if(p.getIsMainPic()==null) {
					isMain = "N";
				}else {
					isMain = p.getIsMainPic().equals("Y")?"Y":"N";
				}
				
				jo.addProperty("isMain", isMain);
				ja.add(jo);
			}
		}
		
		
		return ja;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxRemovePic(GuardianPicDto dto) throws Exception {
		AjaxReturnBean bean = new AjaxReturnBean();
		GuardianPic pp = guardianPicRespository.getEntityById(dto.getId());
		
		File pict = new File(ConfigUtil.getValue("uploadpath")+File.separator+pp.getPicPatch());
		if(pict.exists()) {
			pict.delete();
		}
		
		guardianPicRespository.delete(pp);
		bean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		
		return bean;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AjaxReturnBean ajaxSetMainPic(GuardianPicDto dto ,UserDto userDto) {
		AjaxReturnBean bean = new AjaxReturnBean();
		guardianPicRespository.updateBySQL("UPDATE guardian_pic SET is_main_pic = null WHERE pro_no = ?",dto.getProNo());
		
		GuardianPic pp = guardianPicRespository.getEntityById(dto.getId());
		pp.setIsMainPic("Y");
		pp.setUpdateUser(userDto.getId());
		pp.setUpdateTime(DateUtil.getSystemDateTimeObject());
		
		guardianPicRespository.save(pp);
		
		guardianRepository.updateBySQL("UPDATE guardian SET main_pic_no = ? WHERE id = ?",pp.getId(),dto.getProNo());
		
		bean.setMessage(AjaxMesgs.APPLY_SUCCESSFUL.getDoc());
		return bean;
	}
}
