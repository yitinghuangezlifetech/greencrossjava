package tw.com.ezlifetech.ezReco.service.impl;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;

import com.google.gson.JsonObject;
import com.ibm.icu.text.SimpleDateFormat;
import com.itextpdf.text.Font;

import tw.com.ezlifetech.ezReco.bean.GoBean;
import tw.com.ezlifetech.ezReco.bean.GoReportBean;
import tw.com.ezlifetech.ezReco.common.bean.AjaxReturnBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.common.service.SeqGenService;
import tw.com.ezlifetech.ezReco.dto.GoHtDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.CommonStatus;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.enums.GoSysType;
import tw.com.ezlifetech.ezReco.enums.GoType;
import tw.com.ezlifetech.ezReco.model.DispatcherProfile;
import tw.com.ezlifetech.ezReco.model.GoHt;
import tw.com.ezlifetech.ezReco.model.ProductPic;
import tw.com.ezlifetech.ezReco.model.WorkClassShiftDl;
import tw.com.ezlifetech.ezReco.respository.ComProfHtRespository;
import tw.com.ezlifetech.ezReco.respository.DispatcherProfileRespository;
import tw.com.ezlifetech.ezReco.respository.GoHtRespository;
import tw.com.ezlifetech.ezReco.respository.RefCompWarehouseRespository;
import tw.com.ezlifetech.ezReco.respository.WareHouseProfRespository;
import tw.com.ezlifetech.ezReco.respository.WorkClassShiftDlRespository;
import tw.com.ezlifetech.ezReco.respository.WorkClassShiftHtRespository;
import tw.com.ezlifetech.ezReco.service.DispatcherProfileService;
import tw.com.ezlifetech.ezReco.service.GoService;
import tw.com.ezlifetech.ezReco.util.ConfigUtil;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;
import tw.com.ezlifetech.ezReco.util.ValidatorUtil;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GoServiceImpl implements GoService{

	
	@Autowired
	private GoHtRespository GoHtRespository;
	
	@Autowired
	private ComProfHtRespository comProfHtRespository;
	
	@Autowired
	private RefCompWarehouseRespository refCompWarehouseRespository;
	
	@Autowired
	private DispatcherProfileService dispatcherProfileService; 
	
	@Autowired
	private DispatcherProfileRespository dispatcherProfileRespository;
	
	@Autowired
	private SeqGenService seqGenService;
	
	@Autowired
	private WareHouseProfRespository wareHouseProfRespository;
	
	@Autowired
	private WorkClassShiftHtRespository workClassShiftHtRespository;
	
	@Autowired
	private WorkClassShiftDlRespository workClassShiftDlRespository;
	
	@Override
	public void paper(Model model,GoBean bean) {
		
	}

	@Override
	public void reportPaper(Model model,GoReportBean bean) {
		Map<String, Object> m = strdatePlusMonth(4);
		model.addAttribute("sCreateTime", m.get("sCreateTime"));
		model.addAttribute("eGoSystime", m.get("eGoSystime"));
	}
	@Override
	public void paperEdit(Model model, GoHtDto dto,UserDto loginUser) throws Exception {

		if(StringUtil.isBlank(dto.getId())) {
			
		}else {
			GoHt ht = GoHtRespository.getEntityById(dto.getId());
			BeanUtils.copyProperties(dto,ht);
			
		}
		
		model.addAttribute("compIdSelectList", getCompIdSelectList(loginUser.getId()));
		
	}

	private List<Map<String,Object>> getCompIdSelectList(String userId) {
		List<Map<String,Object>> compList = new  ArrayList<>();
		compList = comProfHtRespository.getCompIdAndNameListMapByUserId(userId);
		return compList;
	}

	@Override
	
	public List<ErrorBean> validateInternalSaveHt(GoHtDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();

		if(!StringUtil.isBlank(dto.getId()))
			if(!isHtStatusCanSave(dto.getId())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("nona"); // 對應前端錯誤提示 的 Id 
				erBean.setLabelName(""); // 欄位名稱
				erBean.setMesg("合約目前狀態無法修改資料");
				list.add(erBean);
				return list;
			}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxSaveHt(GoHtDto dto, UserDto loginUser) throws Exception {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		JsonObject jo = new JsonObject();
		GoHt ht = null;
		ht = GoHtRespository.getUserGo(dto);
		
		if(null == ht && GoType.GoToWork.getTypeCode().equals(dto.getGoStatus())) {
			ht = new GoHt();
			ht.setId(seqGenService.getSystemTimeRandomNumber());
			//取得上班打卡時間  上班打卡，以1小時為單位進行統計(無條件進位)
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH0000");
			Calendar nowTime= Calendar.getInstance();  
			nowTime.setTime(DateUtil.getSystemDateTimeObject());  
			ht.setSGoSystime(nowTime.getTime());
			nowTime.add(Calendar.HOUR_OF_DAY,1);  
			System.out.println("date:"+sdf.format(nowTime.getTime()));
			ht.setSGoTime(sdf.parse(sdf.format(nowTime.getTime())));
			ht.setSCreateUser(loginUser.getId());
			ht.setSCreateTime(DateUtil.getSystemDateTimeObject());
			ht.setUpdateUser(loginUser.getId());
			ht.setUpdateTime(DateUtil.getSystemDateTimeObject());
		}else if(GoType.GoOffWork.getTypeCode().equals(dto.getGoStatus())){
			//ht = GoHtRespository.getEntityById(dto.getId());
			//取得下班打卡時間  下班打卡。以0.5小時為單位進行統計(無條件捨去)
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH0000");
			//取得現在時間 if 現在分鐘>30 則以sdf.format(Cal.getTime())+30分鐘存入 else 現在分鐘<30 則以sdf.format(Cal.getTime())存入
 			Calendar nowTime = Calendar.getInstance();
			//nowTime.add(Calendar.MINUTE, 30);//30分钟后的时间
 			ht.setEGoSystime(nowTime.getTime());
 			if(nowTime.get(Calendar.MINUTE)>30) {
				nowTime.setTime(sdf.parse(sdf.format(nowTime.getTime())));
				nowTime.add(Calendar.MINUTE, 30);
			}else {
				nowTime.setTime(sdf.parse(sdf.format(nowTime.getTime())));
			}
			ht.setEGoTime(nowTime.getTime());
			System.out.println("date:"+nowTime.getTime());
			//計算工作時間  每累積4.5小時，扣掉0.5小時(休息時間)，每累積9小時工作時數，扣除1小時(休息時間)，以扣除後時數進行費用計算。
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			Long go_time = sdf.parse(sdf.format(nowTime.getTime())).getTime() - ht.getSGoSystime().getTime(); 
			long totalMinutes = go_time/(1000* 60);
			long totalHrs = totalMinutes/60;
			double offWorkHrs = countSubHour(ht.getSGoTime(),ht.getEGoTime());
	      	ht.setGoTime(String.valueOf(totalHrs + offWorkHrs));
	      	
//			long days = go_time / (1000 * 60 * 60 * 24);
//		    long hours = (go_time-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
//		    long minutes = (go_time-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
//			System.out.println(""+days+"天"+hours+"小時"+minutes+"分"+totalMinutes+"總分鐘數");
		    
			//取得合約關聯檔
			String wcsId = refCompWarehouseRespository.getWcsId(dto.getComId(),dto.getWhId());
			//取得合約資料
			WorkClassShiftDl workClassShiftDl = workClassShiftDlRespository.getEntityByProperty("htId", wcsId);
			
//	      	ht.setGoSalary(sumSalaryType1(dto,sumTotalMinutes(totalMinutes),workClassShiftDl));
			ht.setGoSalary(sumSalaryType1(dto,(totalHrs + offWorkHrs),workClassShiftDl).getGoSalary());
	      	
			ht.setUpdateUser(loginUser.getId());
			ht.setUpdateTime(DateUtil.getSystemDateTimeObject());
		}else {
			ht.setUpdateUser(loginUser.getId());
			ht.setUpdateTime(DateUtil.getSystemDateTimeObject());
		}
		ht.setComId(dto.getComId());
		ht.setUserId(dto.getUserId());
		ht.setUserName(dto.getUserName());
		ht.setWhId(dto.getWhId());
		ht.setGoStatus(dto.getGoStatus());
		ht.setGoSysType(GoSysType.Gordon.getTypeCode());
		ht.setGoGeneralHr(dto.getGoGeneralHr());
		ht.setGoGeneralSal(dto.getGoGeneralSal());
		ht.setGoOvtHr(dto.getGoOvtHr());
		ht.setGoOvtSal(dto.getGoOvtSal());
//		ht.setPrincipalId(dto.getPrincipalId());
//		ht.setSiteSuperId(dto.getSiteSuperId());
//		ht.setConfigName(dto.getConfigName());
		
		
		GoHtRespository.save(ht);
		
		jo.addProperty("htId", ht.getId());
		
		ajaxReturnBean.setValue(jo);
		ajaxReturnBean.setMessage(AjaxMesgs.SAVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}


	@Override
	public String ajaxHtGridList(GoBean bean, UserDto loginUser) {
		List<Map<String, Object>>  res = GoHtRespository.ajaxHtGridList(bean);
//		for(Map<String, Object> m:res) {
//			m.put("status_text", CommonStatus.getStstusTextByCode(m.get("status").toString()));
//		}
		return PageUtil.transToGridDataSource(res,bean); 
	}

	@Override
	public String ajaxReportHtGridList(GoReportBean bean, UserDto loginUser) throws ParseException {
		List<Map<String, Object>>  res = GoHtRespository.ajaxReportHtGridList(bean);
//		for(Map<String, Object> m:res) {
//			m.put("status_text", CommonStatus.getStstusTextByCode(m.get("status").toString()));
//		}
		return PageUtil.transToGridDataSource(res,bean); 
	}
	@Override
	public String ajaxExpReportHtGridList(GoReportBean bean, UserDto loginUser) throws ParseException {
//		//取得合約關聯檔
//		String wcsId = refCompWarehouseRespository.getWcsId(bean.getComId(),bean.getWhId());
//		//取得合約資料
//		WorkClassShiftDl workClassShiftDl = workClassShiftDlRespository.getEntityByProperty("htId", wcsId);	
		List<Map<String, Object>>  res = GoHtRespository.ajaxExpReportHtGridList(bean);
		return PageUtil.transToGridDataSource(res,bean);
	}

	@Override
	public Map<String,Object> ajaxExpReportHt(GoReportBean bean, UserDto loginUser) throws ParseException {
//		//取得合約關聯檔
//		String wcsId = refCompWarehouseRespository.getWcsId(bean.getComId(),bean.getWhId());
//		//取得合約資料
//		WorkClassShiftDl workClassShiftDl = workClassShiftDlRespository.getEntityByProperty("htId", wcsId);	
		return GoHtRespository.ajaxExpReportHt(bean);
	}
	
	@Override
	public List<ErrorBean> validateInternalRemoveHt(GoHtDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public AjaxReturnBean ajaxRemoveHt(GoHtDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		GoHt ht = GoHtRespository.getEntityById(dto.getId());
		
		if(ht!=null) {
			//List<WorkClassShiftDl>  dlList = workClassShiftDlRespository.findEntityListByJPQL("SELECT d FROM WorkClassShiftDl d WHERE htId=?", dto.getId());
			//for(WorkClassShiftDl d : dlList) {
				//workClassShiftDlRespository.delete(d);
			//}
			//GoHtRespository.delete(ht);
			
			ht.setGoStatus(CommonStatus.DELETE.getStatusCode());
		}
		
		
		
		ajaxReturnBean.setMessage(AjaxMesgs.REMOVE_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}


	private boolean isHtStatusCanSave(String htId) {
		GoHt ht =GoHtRespository.getEntityById(htId);
		if(CommonStatus.AGREE.getStatusCode().equals(ht.getGoStatus())
				|| CommonStatus.REFUSE.getStatusCode().equals(ht.getGoStatus())
				) {
			return false;
		}
		return true;
	}

	@Override
	public String ajaxDlGridList(GoHtDto dto, UserDto loginUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String ajaxGetComIdSelectListByWhId(GoBean bean, UserDto loginUser) {
		
		return PageUtil.transToGridDataSource(comProfHtRespository.getAllCompCodeAndNameListMapByWhId(bean.getWhId(),loginUser),bean); 
	}

	@Override
	public String ajaxGetWhIdSelectList(GoBean bean, UserDto loginUser) {
		List<Map<String, Object>>  res = new ArrayList<Map<String, Object>>();
//		Map<String, Object> m = new HashMap<String, Object>();
//		m.put("wh_id", "");
//		m.put("wh_name", "請選擇");
//		res.add(m);
		res.addAll(wareHouseProfRespository.getAllWhIdList());
		return PageUtil.transToGridDataSource(res,bean); 
	}

	@Override
	public String ajaxGetDisIdSelectListByWhIdCompId(GoBean bean, UserDto loginUser) {
		List<Map<String, Object>>  res = new ArrayList<Map<String, Object>>();
		//dispatcherProfileService
		res.addAll(dispatcherProfileRespository.getAllDisIdSelectListByWhIdCompId(bean.getWhId(),bean.getComId()));
		return PageUtil.transToGridDataSource(res,bean); 
	}

	@Override
	public List<ErrorBean> validateInternalPunch(GoBean bean) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		if( StringUtil.isBlank(bean.getComId())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_comId"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName(""); // 欄位名稱
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "協力廠商"));
			list.add(erBean);
		}else if (StringUtil.isBlank(bean.getWhId())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_whId"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName(""); // 欄位名稱
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "倉別"));
			list.add(erBean);
		}else if (StringUtil.isBlank(bean.getEmpId())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_empId"); // 對應前端錯誤提示 的 Id 
			erBean.setLabelName(""); // 欄位名稱
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "員工編號/姓名"));
			list.add(erBean);
		}
		
		return list;
	}

	@Override
	public AjaxReturnBean ajaxPunch(GoBean bean, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
	
		
		
		
		ajaxReturnBean.setMessage(AjaxMesgs.PUNCH_SUCCESSFUL.getDoc());
		return ajaxReturnBean;
	}
	
	@Override
	public void ajaxGetDispatcherPic(HttpServletResponse response, String value) throws Exception {
		
		String filePath = ConfigUtil.getValue("uploadpath")+File.separator;
		File picFile = null;
		DispatcherProfile dispatcherProfile = dispatcherProfileRespository.getEntityById(value);
		if(dispatcherProfile!=null) {
			picFile = new File (filePath,dispatcherProfile.getPhotoPath());
			response.setContentType("image/jpg");
		}else {
			 URL fileUrl = getClass().getResource("/shopping-bag.png");
			 picFile = new File(fileUrl.getFile());
			 response.setContentType("image/png");
		}
			
		
		
		response.setContentLength((int)picFile.length());
		FileCopyUtils.copy(Files.readAllBytes(picFile.toPath()), response.getOutputStream());
		
		
	}

	@Override
	public Map<String,Object> strdatePlusMonth(int month){
		Map<String,Object> res = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date sd = new Date();
		Date ed = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(ed);
		c.add(Calendar.MONTH, month);
		ed = c.getTime();
		res.put("sdate", sdf.format(sd));
		res.put("edate", sdf.format(ed));
		return res;
	}

	@Override
	public AjaxReturnBean ajaxBtnControl(GoHtDto dto, UserDto loginUser) {
		AjaxReturnBean ajaxReturnBean = new AjaxReturnBean();
		JsonObject jo = new JsonObject();
		boolean showGoW = false,showGoH=false;
		
		GoHt ht = null;
		ht = GoHtRespository.getUserGo(dto);
		if(ht==null) {
			showGoW = true;
		}else {
			showGoH = true;
		}
		
		jo.addProperty("showGoW", showGoW?"Y":"N");
		jo.addProperty("showGoH", showGoH?"Y":"N");
		ajaxReturnBean.setValue(jo);
		return ajaxReturnBean;
	}

	@Override
	public List<ErrorBean> validateInternalBtnControl(GoHtDto dto) {
		List<ErrorBean> list = new ArrayList<ErrorBean>();
		
		
		
		return list;
	}

	private Long sumTotalMinutes(Long totalMinutes){
		if(totalMinutes > 810) {
			totalMinutes = totalMinutes - 90;
		}else if(totalMinutes > 540){
			totalMinutes = totalMinutes - 60;
		}else if(totalMinutes > 270){
			totalMinutes = totalMinutes - 30;
		}
		return totalMinutes;
	}
	
	private GoHtDto sumSalaryType1(GoHtDto dto,double totalMinutes,WorkClassShiftDl workClassShiftDl){
		String salary = "";
		int countSalary = 0 ;
		double restMinutes = 0;
		/**計算出勤時間 大於一般上班時間時  將出勤時間減去一般上班時間 取得超時時間
		並將出勤時間乘上合約金額計入薪資資料
		**/
		if(totalMinutes > 8 ) {
			restMinutes = totalMinutes -(8) ;
			countSalary += 8 * Integer.parseInt(workClassShiftDl.getSalary());
			dto.setGoGeneralHr("8");
			dto.setGoGeneralSal(String.valueOf(countSalary));
		}else {
			countSalary += totalMinutes* Integer.parseInt(workClassShiftDl.getSalary());
			dto.setGoGeneralHr(String.valueOf(totalMinutes));
			dto.setGoGeneralSal(String.valueOf(countSalary));
		}
		dto.setGoOvtHr(String.valueOf(restMinutes));
		dto.setGoOvtSal(String.valueOf(restMinutes/(60) * Integer.parseInt(workClassShiftDl.getExtendIn())));
		countSalary += restMinutes * Integer.parseInt(workClassShiftDl.getExtendIn());
		
		salary = String.valueOf(countSalary);
		
		dto.setGoSalary(salary);
		
		return dto;
	}
	
	private String sumSalaryType2(Long totalMinutes,WorkClassShiftDl workClassShiftDl){
		String salary = "";
		int countSalary = 0 ;
		/**計算出勤時間 大於一般上班時間時  將出勤時間減去一般上班時間 取得超時時間
		並將出勤時間乘上合約金額計入薪資資料
		**/
		if(totalMinutes > 8 * 60 ) {
			totalMinutes = totalMinutes -(8*60) ;
			countSalary += 8 * Integer.parseInt(workClassShiftDl.getSalary());
		}else {
			countSalary += totalMinutes/(60)* Integer.parseInt(workClassShiftDl.getSalary());
		}
		if(totalMinutes > 2 * 60 ) {
			totalMinutes = totalMinutes -(2*60) ;
			countSalary += 2 * Integer.parseInt(workClassShiftDl.getExtendIn());
		}else {
			countSalary += totalMinutes/(60)* Integer.parseInt(workClassShiftDl.getExtendIn());
		}
		if(totalMinutes > 2 * 60 ) {
			totalMinutes = totalMinutes -(2*60) ;
			countSalary += 2 * Integer.parseInt(workClassShiftDl.getExtendOut());
		}else {
			countSalary += totalMinutes/(60)* Integer.parseInt(workClassShiftDl.getExtendOut());
		}
		salary = String.valueOf(countSalary);
		
		return salary;
	}
	
	@Override
	public void export(GoReportBean bean, UserDto loginUser, String excelName, OutputStream out) {
	    try {
	      // 第一步，建立一個webbook，對應一個Excel檔案
	      HSSFWorkbook wb = new HSSFWorkbook();
	      //生成一個表格
	      HSSFSheet sheet = wb.createSheet(excelName);
	      sheet.setColumnWidth(0, 17 * 256);
	      sheet.setColumnWidth(1, 17 * 256);
	      sheet.setColumnWidth(2, 17 * 256);
	      sheet.setColumnWidth(3, 20 * 256);
	      sheet.setColumnWidth(4, 20 * 256);
	      sheet.setColumnWidth(5, 20 * 256);
	      sheet.setColumnWidth(6, 20 * 256);

	      
	      // 第三步，在sheet中新增表頭第0行
	      HSSFRow row = sheet.createRow(0);
	       
	      // 第四步，建立單元格，並設定值表頭 設定表頭居中
	      HSSFCellStyle style = wb.createCellStyle();
	      style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 建立一個居中格式
	      HSSFCell cell = row.createCell(0);
	      cell.setCellStyle(style);
	      // 出勤資料
	      if("Y".equals(bean.getAttendance())) {
	    	  List<Map<String, Object>>  list = GoHtRespository.ajaxReportHtGridList(bean);
	    	  this.insertData2(wb, sheet, row, list, out);
	      }
	      // 結帳資料
	      else {
		      Map<String,Object> map = ajaxExpReportHt(bean,loginUser); 
		      //Byte kjzz = qyjbxxMapper.getKjzz(sblsh);
		      //List<A> record = this.selectBySblsh(sblsh);
		      this.insertData(wb, sheet, row, map, out);	    	  
	      }
	      
	    } catch (Exception e) {
	      e.getMessage();
	    }
	}
	   
	  /**
	   * 匯入結帳資料到表格中
	   * @param wb execl檔案
	   * @param sheet 表格
	   * @param row 表格行
	   * @param record 要匯出的資料
	   * @param out 輸出流
	   */
	  public void insertData(HSSFWorkbook wb,HSSFSheet sheet,HSSFRow row,Map<String,Object> map,OutputStream out){
	    try {
//	    	String[] title = new String[0] ;
//	    	if(map.get("com_name") != null) {
//	    		title[0] = (String)map.get("com_name"); 
//	    	}
	    	  // 字體格式
	    	  HSSFFont font = wb.createFont();
	    	  //font.setColor(HSSFColor.BLACK.index); // 顏色
	    	  //font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗體字
	    	  font.setFontName("標楷體");
	    	  font.setFontHeightInPoints((short) 16);
	    	  
	    	String[] title = {"三商家購三商家購(股)公司  物流中心"};
	    	
	    	
	    	 row = sheet.createRow(1);
	    	 row.setHeight((short) ((short) 30 * 20));
	      for(int i=0;i<title.length;i++){
	        row.createCell(i).setCellValue(title[i]);
	      }
	      
	      row = sheet.createRow(2);
	      HSSFCellStyle styleRow1 = wb.createCellStyle();
	      styleRow1.setBorderBottom((short) 1);
	      styleRow1.setBorderTop((short) 2);
	      styleRow1.setBorderLeft((short) 1);
	      styleRow1.setBorderRight((short) 1);
	      row.setHeight((short) ((short) 30 * 20));
	      
	      row.createCell(0).setCellValue("項目");
	      row.createCell(1).setCellValue("總時數");
	      row.createCell(2).setCellValue("單價(固定)");
	      row.createCell(3).setCellValue("金額");
	      row.createCell(4).setCellValue("備註");
	      //row.setRowStyle(styleRow1);
	      
	      String generalHr = null;
	      String generalSal = null;
	      String ovtHr = null;
	      String ovtSal = null;
	      
	      row = sheet.createRow(3);
	      HSSFCellStyle styleRow2 = wb.createCellStyle();
	      styleRow2.setBorderBottom((short) 1);
	      styleRow2.setBorderTop((short) 2);
	      styleRow2.setBorderLeft((short) 1);
	      styleRow2.setBorderRight((short) 1);
	      row.setHeight((short) ((short) 30 * 20));
	      
	      row.createCell(0).setCellValue("勞務報酬(時)");
	      
	      if(map.get("generalhr") != null) {
	    	  row.createCell(1).setCellValue(map.get("generalhr").toString());
	    	  generalHr = map.get("generalhr").toString();
	      }
	      else {
	    	  row.createCell(1).setCellValue("0");
	    	  generalHr = "0";
	      }
	      if(map.get("salary") != null) {
	    	  row.createCell(2).setCellValue(map.get("salary").toString());
	      }
	      else {
	    	  row.createCell(2).setCellValue("0");
	      }	      
	      if(map.get("generalsal") != null) {
	    	  row.createCell(3).setCellValue(map.get("generalsal").toString());
	    	  generalSal = map.get("generalsal").toString();
	      }
	      else {
	    	  row.createCell(3).setCellValue("0");
	    	  generalSal = "0";
	      }
	      row.createCell(4).setCellValue("");
	      //row.setRowStyle(styleRow2);
	      
	      row = sheet.createRow(4);
	      row.setHeight((short) ((short) 30 * 20));
	      
	      row.createCell(0).setCellValue("超時加班");
	      if(map.get("ovthr") != null) {
	    	  row.createCell(1).setCellValue(map.get("ovthr").toString());
	    	  ovtHr = map.get("ovthr").toString();
	      }
	      else {
	    	  row.createCell(1).setCellValue("0");
	    	  ovtHr = "0";
	      }
	      if(map.get("salary") != null) {
	    	  row.createCell(2).setCellValue(map.get("salary").toString());
	      }
	      else {
	    	  row.createCell(2).setCellValue("0");
	      }	      
	      if(map.get("ovtsal") != null) {
	    	  row.createCell(3).setCellValue(map.get("ovtsal").toString());
	    	  ovtSal = map.get("ovtsal").toString();
	      }
	      else {
	    	  row.createCell(3).setCellValue("0");
	    	  ovtSal = "0";
	      }
	      row.createCell(4).setCellValue("");
	      
//	      row = sheet.createRow(5);
//	      row.setHeight((short) ((short) 30 * 20));	      
//	      row.createCell(0).setCellValue("勞務報酬(時)延長工時2小時以上");
//	      row.createCell(1).setCellValue("");
//	      row.createCell(2).setCellValue("");
//	      row.createCell(3).setCellValue("");
//	      row.createCell(4).setCellValue("");
//	      
//	      
//	      row = sheet.createRow(6);
//	      row.setHeight((short) ((short) 30 * 20));	      
//	      row.createCell(0).setCellValue("夜間加計");
//	      row.createCell(1).setCellValue("");
//	      row.createCell(2).setCellValue("");
//	      row.createCell(3).setCellValue("");
//	      row.createCell(4).setCellValue("");
 
	      
	      row = sheet.createRow(5);
	      row.setHeight((short) ((short) 30 * 20));	      
	      row.createCell(0).setCellValue("");
	      row.createCell(1).setCellValue("");
	      row.createCell(2).setCellValue("");
	      row.createCell(3).setCellValue("");
	      row.createCell(4).setCellValue("");
 
	      row = sheet.createRow(6);
	      row.setHeight((short) ((short) 30 * 20));	      
	      row.createCell(0).setCellValue("合計");
	      row.createCell(1).setCellValue(String.valueOf(Double.parseDouble(generalHr) + Double.parseDouble(ovtHr)));
	      row.createCell(2).setCellValue("");
	      row.createCell(3).setCellValue(String.valueOf(Double.parseDouble(generalSal) + Double.parseDouble(ovtSal)));
	      row.createCell(4).setCellValue("");
	      
//	      row = sheet.createRow(8);
//	      row.setHeight((short) ((short) 30 * 20));	      
//	      row.createCell(0).setCellValue("");
//	      row.createCell(1).setCellValue("");
//	      row.createCell(2).setCellValue("稅額:");
//	      row.createCell(3).setCellValue("");
//	      row.createCell(4).setCellValue("");
//	      
//	      row = sheet.createRow(7);
//	      row.setHeight((short) ((short) 30 * 20));	      
//	      row.createCell(0).setCellValue("");
//	      row.createCell(1).setCellValue("");
//	      row.createCell(2).setCellValue("合計:");
//	      row.createCell(3).setCellValue("");
//	      row.createCell(4).setCellValue("");
	      row = sheet.createRow(7);
	      row = sheet.createRow(8);
	      row = sheet.createRow(9);
	      row = sheet.createRow(10);
	      
	      row.setHeight((short) ((short) 30 * 20));	      
	      row.createCell(0).setCellValue("主管會簽:");
	      row.createCell(1).setCellValue("");
	      row.createCell(2).setCellValue("會簽:");
	      row.createCell(3).setCellValue("");
	      row.createCell(4).setCellValue("");
	      row = sheet.createRow(11);
	      row = sheet.createRow(12);
	      row.setHeight((short) ((short) 30 * 20));	
	      row.createCell(0).setCellValue("核准:");
	      row.createCell(1).setCellValue("");
	      row.createCell(2).setCellValue("部門簽核:");
	      row.createCell(3).setCellValue("");
	      row.createCell(4).setCellValue("經辦:");
//	      for(int i=0;i<record.size();i++){
//	        row = sheet.createRow(i+2);
//	        A data = record.get(i);
//	        row.createCell(0).setCellValue(data.getHc());
//	        row.createCell(1).setCellValue(data.getXm());
//	        BigDecimal je = data.getJe();
//	        if(je!=null){
//	          row.createCell(2).setCellValue(je.doubleValue());
//	        }
//	      }
	      //合併單元格，前面2位代表開頭結尾行，後面2位代表開頭結尾列
	      CellRangeAddress region = new CellRangeAddress(1,1,0,4);
	      sheet.addMergedRegion(region);
	      wb.write(out);
	      out.flush();
	      out.close();
	      wb.close();
	    } catch (Exception e) {
	     e.getMessage();
	    }
	  }

	  
	  /**
	   * 匯入差勤資料到表格中
	   * @param wb execl檔案
	   * @param sheet 表格
	   * @param row 表格行
	   * @param record 要匯出的資料
	   * @param out 輸出流
	   */
	  public void insertData2(HSSFWorkbook wb,HSSFSheet sheet,HSSFRow row,List<Map<String,Object>> list,OutputStream out){
	    try {
	    	  // 字體格式
	    	  HSSFFont font = wb.createFont();
	    	  //font.setColor(HSSFColor.BLACK.index); // 顏色
	    	  //font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗體字
	    	  font.setFontName("標楷體");
	    	  //font.setFontHeightInPoints((short) 16);
	    	  
	    	String[] title = {"倉別","員工編號","姓名","正常工時總時數","超時工時總時數","正常工時總金額","超時工時總金額"};
	    	//row.setHeight((short) ((short) 30 * 20));
	      for(int i=0;i<title.length;i++){
	        row.createCell(i).setCellValue(title[i]);
	      }
	      
	      for(int j = 0; j  < list.size(); j++) {
	    	  row = sheet.createRow(j + 1);
	    	  //row.setHeight((short) ((short) 30 * 20));
		      row.createCell(0).setCellValue(list.get(j).get("wh_name") == null ? "" :list.get(j).get("wh_name").toString());
		      row.createCell(1).setCellValue(list.get(j).get("user_id") == null ? "" :list.get(j).get("user_id").toString());
		      row.createCell(2).setCellValue(list.get(j).get("user_name") == null ? "" :list.get(j).get("user_name").toString());
		      row.createCell(3).setCellValue(list.get(j).get("total_go_general_hr") == null ? "" :list.get(j).get("total_go_general_hr").toString());
		      row.createCell(4).setCellValue(list.get(j).get("total_go_ovt_hr") == null ? "" :list.get(j).get("total_go_ovt_hr").toString());
		      row.createCell(5).setCellValue(list.get(j).get("total_go_general_sal") == null ? "" :list.get(j).get("total_go_general_sal").toString());
		      row.createCell(6).setCellValue(list.get(j).get("total_go_ovt_sal") == null ? "" :list.get(j).get("total_go_ovt_sal").toString());
	      }
	      wb.write(out);
	      out.flush();
	      out.close();
	      wb.close();
	    } catch (Exception e) {
	     e.getMessage();
	    }
	  }

	  
	@Override
	public double countSubHour(Date startInput, Date endInput) throws Exception {
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		Date start=null , end=null;
		
		if(endInput.after(startInput)) {
			start =hm.parse(hm.format(startInput)) ;
			end =hm.parse(hm.format(endInput));
		}else if(endInput.before(startInput)){
			start =hm.parse(hm.format(startInput)) ;
			end =hm.parse("23:59");
		}else {
			return 0;
		}
		
		
		List<Map<String,Date>> breakDateList = new ArrayList<Map<String,Date>>();
		Map<String,Date> b1 = new HashMap<String,Date>();
		b1.put("sDate", hm.parse("12:00"));
		b1.put("eDate", hm.parse("13:00"));
		
		Map<String,Date> b2 = new HashMap<String,Date>();
		b2.put("sDate", hm.parse("17:00"));
		b2.put("eDate", hm.parse("17:30"));
		
		Map<String,Date> b3 = new HashMap<String,Date>();
		b3.put("sDate", hm.parse("21:30"));
		b3.put("eDate", hm.parse("22:00"));
		
		
		breakDateList.add(b1);
		breakDateList.add(b2);
		breakDateList.add(b3);
		
		double subHConut = 0.0;
		
		for(Map<String,Date> breakDateMap : breakDateList) {
			if(start.before(breakDateMap.get("sDate")) && end.after(breakDateMap.get("eDate"))) {
				long tCount = ((breakDateMap.get("eDate").getTime()-breakDateMap.get("sDate").getTime()))/1000L;
				
				System.out.println(">1>tCount:"+tCount);
				System.out.println(">1>subHConut:"+subHConut);
				subHConut = subHConut -tCount;
				System.out.println(">1>subHConut:"+subHConut);
			}else {
				if(start.before(breakDateMap.get("sDate")) && breakDateMap.get("eDate").after(end) && end.after(breakDateMap.get("sDate"))) {
					long tCount = ((end.getTime()-breakDateMap.get("sDate").getTime()))/1000L;
					System.out.println(">2>tCount:"+tCount);
					System.out.println(">2>subHConut:"+subHConut);
					subHConut = subHConut - tCount;
					System.out.println(">2>subHConut:"+subHConut);
				}else {
					if(breakDateMap.get("sDate").before(start) && end.after(breakDateMap.get("eDate")) && start.before(breakDateMap.get("eDate"))) {
						long tCount =((breakDateMap.get("eDate").getTime()-start.getTime()))/1000L;
						System.out.println(">3>tCount:"+tCount);
						System.out.println(">3>subHConut:"+subHConut);
						subHConut = subHConut - tCount;
						System.out.println(">3>subHConut:"+subHConut);
					}else {
						if((start.equals(breakDateMap.get("sDate")) && end.equals(breakDateMap.get("eDate")) )||
								(start.before(breakDateMap.get("sDate")) && end.equals(breakDateMap.get("eDate"))) ||
								(start.equals(breakDateMap.get("sDate")) && end.after(breakDateMap.get("eDate")))
						){
							long tCount = ((breakDateMap.get("eDate").getTime()-breakDateMap.get("sDate").getTime()))/1000L;
							System.out.println(">4>tCount:"+tCount);
							System.out.println(">4>subHConut:"+subHConut);
							subHConut =subHConut -tCount;
							System.out.println(">4>subHConut:"+subHConut);
						}
					}
				}
			}
		}
		
		
		
		return ((subHConut)/60.0/60.0);
	}
}
