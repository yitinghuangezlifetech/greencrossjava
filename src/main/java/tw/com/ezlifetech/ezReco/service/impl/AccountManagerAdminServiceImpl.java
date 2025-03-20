package tw.com.ezlifetech.ezReco.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import tw.com.ezlifetech.ezReco.bean.AccountManagerAdminBean;
import tw.com.ezlifetech.ezReco.common.bean.ErrorBean;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.enums.AjaxMesgs;
import tw.com.ezlifetech.ezReco.enums.ErrorMesgs;
import tw.com.ezlifetech.ezReco.enums.UserStatus;
import tw.com.ezlifetech.ezReco.model.Role;
import tw.com.ezlifetech.ezReco.model.User;
import tw.com.ezlifetech.ezReco.respository.ComProfHtRespository;
import tw.com.ezlifetech.ezReco.respository.RoleRepository;
import tw.com.ezlifetech.ezReco.respository.UserRepository;
import tw.com.ezlifetech.ezReco.service.AccountManagerAdminService;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.PageUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;
import tw.com.ezlifetech.ezReco.util.ValidatorUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AccountManagerAdminServiceImpl implements AccountManagerAdminService{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ComProfHtRespository comProfHtRespository;
	
	@Override
	public String ajaxAccountGridList(AccountManagerAdminBean formBean, UserDto loginUser) {
		Map<String, Object> params = new HashMap<String, Object>();

		if (!StringUtil.isBlank(formBean.getVcode())) {
			params.put("vcode", formBean.getVcode());
		}

		if (!StringUtil.isBlank(formBean.getVcodeName())) {
			params.put("vcodeName", "%" + formBean.getVcodeName() + "%");
		}

		StringBuffer sql = new StringBuffer();
		sql.append("   SELECT *                                                ");
		sql.append("   FROM                                                    ");
		sql.append("     hf_user                                    ");
		sql.append("   WHERE 1 = 1                                             ");

		if (params.get("vcode") != null) {
			sql.append("     AND ( USER_ID = :vcode OR PARENT_USER_ID = :vcode )   ");
		}

		if (params.get("vcodeName") != null) {
			sql.append("     AND USER_NAME LIKE :vcodeName                         ");
		}

		sql.append("   ORDER BY USER_ID                                        ");

		List<Map<String, Object>> list = userRepository.findListMapBySQL_map(sql.toString(), params);

		return PageUtil.transToGridDataSource(list, formBean);
	}

	@Override
	public void paperPage(Model model, UserDto dto) {
		User targetUser = null;
		if (!StringUtil.isBlank(dto.getId())) {
			targetUser = userRepository.getEntityById(dto.getId());

		}
		if (targetUser != null) {
			model.addAttribute("isEditMode", "Y");
			BeanUtils.copyProperties(targetUser, dto);
			dto.setRoleId(dto.getRoleType() + dto.getRoleNo());
			dto.setUserStartDateStr(DateUtil.parseDateToString(dto.getUserStartDate(), "yyyy/MM/dd"));
			if (dto.getUserCloseDate() != null)
				dto.setUserCloseDateStr(DateUtil.parseDateToString(dto.getUserCloseDate(), "yyyy/MM/dd"));
			else
				dto.setUserCloseDateStr("");

		} else {
			model.addAttribute("isEditMode", "N");
		}

		getRoleList(model, dto);
		getUserStatusList(model, dto);
		model.addAttribute("compIdSelectList", getCompIdSelectList());
	}

	private List<Map<String,Object>> getCompIdSelectList() {
		List<Map<String,Object>> compList = new  ArrayList<>();
		compList = comProfHtRespository.getAllCompIdAndNameListMap();
		return compList;
	}
	
	

	private void getUserStatusList(Model model, UserDto dto) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (UserStatus status : UserStatus.values()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("statusCode", status.getStatusCode());
			map.put("statusValue", status.getStatusValue());
			list.add(map);
		}

		model.addAttribute("userStatusList", list);
	}

	private void getRoleList(Model model, UserDto dto) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (!StringUtil.isBlank(dto.getParentUserId())) {
			params.put("parentUserId", dto.getParentUserId());
		}

		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT *                                            ");
		sql.append("  FROM                                                ");
		sql.append("    hf_role                                ");
		sql.append("  WHERE 1 = 1                                         ");
		if (params.get("parentUserId") == null) {
			sql.append("    AND ROLE_OWNER is null                            ");
		} else {
			sql.append("    AND (ROLE_OWNER is null OR ROLE_OWNER =  :parentUserId )  ");
		}
		sql.append("  ORDER BY CREATE_TIME                                ");

		List<Map<String, Object>> list = roleRepository.findListMapBySQL_map(sql.toString(), params);

		model.addAttribute("roleSelList", list);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String ajaxSaveAccount(UserDto dto, UserDto loginUserDto) throws Exception {
		User user = null;
		if (!dto.getIsEditMode().equals("Y")) {
			user = new User();
			user.setId(dto.getIdStr());
			user.setParentUserId(dto.getParentUserIdStr());
			user.setCreateTime(DateUtil.getSystemDateTimeObject());
			user.setCreateUser(loginUserDto.getId());
		}else {
			user = userRepository.getEntityById(dto.getId());
			if(user==null) {
				throw new Exception("user not found!");
			}
			user.setUpdateTime(DateUtil.getSystemDateTimeObject());
			user.setUpdateUser(loginUserDto.getId());
		}
		
		
		user.setUserName(dto.getUserName());
		
		Role role = roleRepository.getEntityById(dto.getRoleId());
		user.setRoleType(role.getRoleType());
		user.setRoleNo(role.getRoleNo());
		
		user.setBan(dto.getBan());
		user.setUserStatus(dto.getUserStatus());
		
		Date s = DateUtil.parseDay(dto.getUserStartDateStr(), "yyyy/MM/dd");
		user.setUserStartDate(s);
		
		if(!StringUtil.isBlank(dto.getUserCloseDateStr())) {
			Date c = DateUtil.parseDay(dto.getUserCloseDateStr(), "yyyy/MM/dd");
			user.setUserCloseDate(c);
		}
		
		
		
		
		user.setContact(dto.getContact());
		user.setUserEmail(dto.getUserEmail());
		user.setUserTel(dto.getRoleType());
		user.setUserExt(dto.getUserExt());
		user.setUserFax(dto.getUserFax());
		
		user.setPwdKeeper(dto.getPwdKeeper());
		user.setPwdKeeperEmail(dto.getPwdKeeperEmail());
		
		if(!StringUtil.isBlank(dto.getUserPwd())) {
			user.setUserPwd(DigestUtils.sha1Hex(dto.getUserPwd()));
			user.setChangePwdTime(DateUtil.getSystemDateTimeObject());
		}
		user.setComId(dto.getComId());
		userRepository.save(user);
		
		return AjaxMesgs.SAVE_SUCCESSFUL.getDoc();
	}

	@Override
	public List<ErrorBean> validateInternalSaveAccount(UserDto dto) throws Exception {
		List<ErrorBean> list = new ArrayList<ErrorBean>();

		// 使用者帳號檢查
		if (!dto.getIsEditMode().equals("Y")) {
			
			if (StringUtil.isBlank(dto.getIdStr())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_id");
				erBean.setLabelName("使用者帳號");
				erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
				list.add(erBean);
			} else if (dto.getIdStr().length() > 30) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_id");
				erBean.setLabelName("使用者帳號");
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "30"));
				list.add(erBean);
			}
			
			if (userRepository.getEntityById(dto.getIdStr()) != null) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_id");
				erBean.setLabelName("使用者帳號");
				erBean.setMesg(ErrorMesgs.DATA_EXIST.getDoc());
				list.add(erBean);
			}
		}else {
			if (StringUtil.isBlank(dto.getId())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_id");
				erBean.setLabelName("使用者帳號");
				erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
				list.add(erBean);
			} else if (dto.getId().length() > 30) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_id");
				erBean.setLabelName("使用者帳號");
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "30"));
				list.add(erBean);
			}
		}

		// 使用者帳號名稱
		if (StringUtil.isBlank(dto.getUserName())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_userName");
			erBean.setLabelName("使用者名稱");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		} else if (dto.getUserName().length() > 50) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_userName");
			erBean.setLabelName("使用者名稱");
			erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "30"));
			list.add(erBean);
		}

		// 使用者主帳號檢查
		if (!dto.getIsEditMode().equals("Y")) {

			if (!StringUtil.isBlank(dto.getParentUserIdStr()))
				if (userRepository.getEntityByJPQL("SELECT u FROM User u WHERE u.id=? and (u.parentUserId is null OR u.parentUserId='') ", dto.getParentUserIdStr()) == null) {
					ErrorBean erBean = new ErrorBean();
					erBean.setErrSpanId("err_parentUserId");
					erBean.setLabelName("使用者主帳號");
					erBean.setMesg(ErrorMesgs.DATA_NOT_EXIST.getDoc());
					list.add(erBean);
				}
		}

		// 使用者角色檢查
		if (StringUtil.isBlank(dto.getRoleId())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_roleId");
			erBean.setLabelName("");
			erBean.setMesg(ErrorMesgs.PLEASE_SELECT.getDoc().replace("{!label}", "使用者角色"));
			list.add(erBean);
		}

		// 檢查使用者統編
		if (StringUtil.isBlank(dto.getBan())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_ban");
			erBean.setLabelName("使用者統編");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		} else if (dto.getBan().length() > 10) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_ban");
			erBean.setLabelName("使用者統編");
			erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "10"));
			list.add(erBean);
		}

		// 帳號開始日期 // 帳號結束日期
		if (StringUtil.isBlank(dto.getUserStartDateStr())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_userStartDateStr");
			erBean.setLabelName("帳號開始日期");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		}

		if (!StringUtil.isBlank(dto.getUserCloseDateStr())) {
			Date s = DateUtil.parseDay(dto.getUserStartDateStr(), "yyyy/MM/dd");
			Date c = DateUtil.parseDay(dto.getUserCloseDateStr(), "yyyy/MM/dd");

			if (!s.before(c)) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_userCloseDateStr");
				erBean.setLabelName("帳號結束日期");
				erBean.setMesg(ErrorMesgs.MUST_BIGGER_THEN.getDoc().replace("{!label}", "帳號開始日期"));
				list.add(erBean);
			}
		}

		// 聯絡人 檢查
		if (!StringUtil.isBlank(dto.getContact())) {
			if (dto.getContact().length() > 30) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_contact");
				erBean.setLabelName("聯絡人");
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "30"));
				list.add(erBean);
			}
		}

		// 電子郵件 檢查
		if (!StringUtil.isBlank(dto.getUserEmail())) {
			if (dto.getUserEmail().length() > 100) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_userEmail");
				erBean.setLabelName("電子郵件");
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "100"));
				list.add(erBean);
			}

			if (!ValidatorUtil.isValidEmail(dto.getUserEmail())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_userEmail");
				erBean.setLabelName("電子郵件");
				erBean.setMesg(ErrorMesgs.DATA_FORMAT_NOT_MATCH.getDoc());
				list.add(erBean);
			}
		}

		// 電話 檢查
		if (!StringUtil.isBlank(dto.getUserTel())) {
			if (dto.getUserTel().length() > 30) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_userTel");
				erBean.setLabelName("電話");
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "30"));
				list.add(erBean);
			}
		}

		// 分機 檢查
		if (!StringUtil.isBlank(dto.getUserExt())) {
			if (StringUtil.isBlank(dto.getUserTel())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_userTel");
				erBean.setLabelName("若分機有值，則電話");
				erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
				list.add(erBean);
			}

			if (dto.getUserExt().length() > 10) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_userTel");
				erBean.setLabelName("電話");
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "10"));
				list.add(erBean);
			}
		}

		// 傳真 檢查
		if (!StringUtil.isBlank(dto.getUserFax())) {
			if (dto.getUserFax().length() > 30) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_userFax");
				erBean.setLabelName("傳真");
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "30"));
				list.add(erBean);
			}
		}

		// 地址 檢查
		if (!StringUtil.isBlank(dto.getUserAddr())) {
			if (dto.getUserAddr().length() > 200) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_userFax");
				erBean.setLabelName("地址");
				erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "200"));
				list.add(erBean);
			}
		}

		// 密碼保管人 檢查
		if (StringUtil.isBlank(dto.getPwdKeeper())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_pwdKeeper");
			erBean.setLabelName("密碼保管人");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		} else if (dto.getPwdKeeper().length() > 30) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_pwdKeeper");
			erBean.setLabelName("密碼保管人");
			erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "30"));
			list.add(erBean);
		}

		// 密碼保管人電子郵件檢查
		if (StringUtil.isBlank(dto.getPwdKeeperEmail())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_pwdKeeperEmail");
			erBean.setLabelName("密碼保管人電子郵件");
			erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
			list.add(erBean);
		} else if (dto.getPwdKeeperEmail().length() > 100) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_pwdKeeperEmail");
			erBean.setLabelName("密碼保管人電子郵件");
			erBean.setMesg(ErrorMesgs.CAN_NOT_OUT_OF_RANGE.getDoc().replace("{!range}", "100"));
			list.add(erBean);
		} else if (!ValidatorUtil.isValidEmail(dto.getPwdKeeperEmail())) {
			ErrorBean erBean = new ErrorBean();
			erBean.setErrSpanId("err_pwdKeeperEmail");
			erBean.setLabelName("密碼保管人電子郵件");
			erBean.setMesg(ErrorMesgs.DATA_FORMAT_NOT_MATCH.getDoc());
			list.add(erBean);
		}

		// 密碼更新
		
		if(StringUtil.isBlank(dto.getUserPwd())){
			if(!dto.getIsEditMode().equals("Y")) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_userPwd");
				erBean.setLabelName("帳號密碼");
				erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
				list.add(erBean);
			}
		}else if (!(StringUtil.isBlank(dto.getUserPwd()) && StringUtil.isBlank(dto.getUserPwdAgain()))) {
			if (StringUtil.isBlank(dto.getUserPwd())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_userPwd");
				erBean.setLabelName("帳號密碼");
				erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
				list.add(erBean);
			} else if (StringUtil.isBlank(dto.getUserPwdAgain())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_userPwdAgain");
				erBean.setLabelName("帳號密碼確認");
				erBean.setMesg(ErrorMesgs.CAN_NOT_EMPTY.getDoc());
				list.add(erBean);
			} else if (!dto.getUserPwd().equals(dto.getUserPwdAgain())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_userPwdAgain");
				erBean.setLabelName("帳號密碼 與 帳號密碼確認");
				erBean.setMesg(ErrorMesgs.NOT_EQU.getDoc());
				list.add(erBean);
			} else if (!ValidatorUtil.isValidPwd(dto.getUserPwd())) {
				ErrorBean erBean = new ErrorBean();
				erBean.setErrSpanId("err_userPwd");
				erBean.setLabelName("帳號密碼");
				erBean.setMesg(ErrorMesgs.DATA_FORMAT_NOT_MATCH.getDoc());
				list.add(erBean);
			}
		}

		return list;
	}

}
