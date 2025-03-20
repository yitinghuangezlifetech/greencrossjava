package tw.com.ezlifetech.ezReco.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ezlifetech.ezReco.bean.HomeBean;
import tw.com.ezlifetech.ezReco.common.service.UserService;
import tw.com.ezlifetech.ezReco.dto.ProfFunctionDto;
import tw.com.ezlifetech.ezReco.dto.UserDto;
import tw.com.ezlifetech.ezReco.model.ProfFunction;
import tw.com.ezlifetech.ezReco.model.RefFunctionRole;
import tw.com.ezlifetech.ezReco.model.RefFunctionUser;
import tw.com.ezlifetech.ezReco.model.User;
import tw.com.ezlifetech.ezReco.respository.ProfFunctionRepository;
import tw.com.ezlifetech.ezReco.respository.RefFunctionRoleRepository;
import tw.com.ezlifetech.ezReco.respository.RefFunctionUserRepository;
import tw.com.ezlifetech.ezReco.respository.UserRepository;
import tw.com.ezlifetech.ezReco.service.LoginService;
import tw.com.ezlifetech.ezReco.service.ShoppingCarService;
import tw.com.ezlifetech.ezReco.util.Constant;
import tw.com.ezlifetech.ezReco.util.DateUtil;
import tw.com.ezlifetech.ezReco.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StringType;


@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LoginServiceImpl implements LoginService{

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RefFunctionRoleRepository refFunctionRoleRepository;
	
	@Autowired
	private ProfFunctionRepository profFunctionRepository;
	
	@Autowired
	private RefFunctionUserRepository refFunctionUserRepository;
	
	@Autowired
	private ShoppingCarService shoppingCarService;
	
	@Override
	public void genSecurityImage(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("P3P","CP=CAO PSA OUR");
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);
		OutputStream os = null;
		int MAX_WIDTH = 80;
		int MAX_HEIGHT = 25;
		try {
			response.reset();
			os = response.getOutputStream();
			BufferedImage image = new BufferedImage(MAX_WIDTH, MAX_HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();
			SecureRandom random = new SecureRandom();
			g.setColor(getRandColor(200,250));
			g.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT);
			g.setFont(new Font("Times New Roman",Font.PLAIN,24));
			g.setColor(getRandColor(160,200));
			for (int i = 0; i < 155; i++) {
			    int x = random.nextInt(MAX_WIDTH);
			    int y = random.nextInt(MAX_HEIGHT);
			    int xl = random.nextInt(12);
			    int yl = random.nextInt(12);
			    g.drawLine(x, y, x+xl, y+yl);
			}

			StringBuffer randomNumber = new StringBuffer();
			for (int i = 0 ; i < 5 ; i++) {
				int rdn = 0;
				int x = 6;
			    String single = "";
				if (0 == rdn) {
					single = String.valueOf(random.nextInt(10));
					x = 4;
				}
				else if (1 == rdn) {
					single = String.valueOf(((char)(65 + random.nextInt(26))));
					x = 1;
				}
				else if (2 == rdn) {
					single = String.valueOf(((char)(97 + random.nextInt(26))));
					x = 5;
				}
			    randomNumber.append(single);
			    
			    g.setColor(new Color(20 + random.nextInt(150), 20 + random.nextInt(150), 20 + random.nextInt(150)));
			    g.drawString(single, 13 * i + x, 19);
			}
			request.getSession(true).setAttribute("randomNumber", randomNumber.toString());
			g.dispose();
			ImageIO.write(image, "JPEG", os);
			os.flush();
			os.close();
			os = null;
			response.flushBuffer();
		}catch (Exception e) {
			try {
				MethodUtils.invokeMethod(e, "printStackTrace");
			} catch (Exception ex) {
			}
		}
		finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * 顏色
	 * @param fgColor
	 * @param bgColor
	 * @return
	 */
	protected Color getRandColor(int fgColor,int bgColor){
		SecureRandom random = new SecureRandom();
	    if (fgColor > 255) fgColor = 255;
	    if (bgColor > 255) bgColor = 255;
	    int r = fgColor + random.nextInt(bgColor - fgColor);
	    int g = fgColor + random.nextInt(bgColor - fgColor);
	    int b = fgColor + random.nextInt(bgColor - fgColor);
	    return new Color(r,g,b);
	}

	@Override
	public User getUserProfNewByUserId(String userId) {
		User userProfNew = null;
		List<User> userProfNewList = userRepository.findByJPQL("select u from User u where u.id=?", userId);
		//userProfNew = userRepository.findById(userId);
		
		//List<User> userProfNewList=userRepository.findByNamedQuery("findByUserId", userId);
		if(userProfNewList.size() >0)
			userProfNew = userProfNewList.get(0);
		return userProfNew;
	}
	/**
	 * 登入失敗處理
	 * @param user
	 * 
	 */
	@Override
	public void updateUserLoginError(User user){
		int errTimes=0;
		if(StringUtil.isBlank(user.getLoginErrTimes()))
			errTimes=0;
		else
			errTimes=Integer.parseInt(user.getLoginErrTimes());
		user.setLoginErrTimes(String.valueOf(errTimes+1));
		user.setLoginErrTime(DateUtil.getSystemDateTimeObject());
		userRepository.update(user);
	}
	
	/**
	 * 登入成功後設定使用者資訊
	 * @param user
	 * @param request
	 */
	@Override
	@Transactional
	public void setUserLoginInfo(UserDto userDto, HttpServletRequest request){
		try {
			
			userDto.setSessionId(request.getSession().getId());
			userDto.setUserIp(request.getRemoteHost());
			userDto.setFuncList(getUserRoleFuncList(userDto));
			userDto.setInShoppingCarNum(shoppingCarService.getInShoppingCarNumber(userDto));
			request.getSession().setAttribute("userProf", userDto);//寫入session
			
			
			//取得功能權限清單 產生Menu存入Session
			MethodUtils.invokeMethod(request.getSession(), "setAttribute", "menuTree", getUserMenuTree(userDto));
			//loginService.getUserMenuTree(user);
			
			
			
			//更新登入記錄
			User user = userRepository.getEntityById(userDto.getId());
			updateUserLoginSuccess(user);
		} catch (Exception e) {
			try {
				MethodUtils.invokeMethod(e, "printStackTrace");
			} catch (Exception ex) {
				
			}
		}
	}
	
	private List<ProfFunctionDto> getUserRoleFuncList(UserDto userDto) {
		List<ProfFunction> functionList = profFunctionRepository.findAllEntityList();
		List<ProfFunctionDto> functionDtoList = new ArrayList<ProfFunctionDto>();	
		for(ProfFunction pf : functionList) {
			ProfFunctionDto pfDto = new ProfFunctionDto(pf);
			boolean checkUserCanUse = checkUserCanUse(userDto,pfDto);
			pfDto.setUserCanUse(checkUserCanUse);
			
			pfDto.setCanUseUrl(getFunCUrlRoot(pfDto));
			if(checkUserCanUse) {
				functionDtoList.add(0, pfDto);;
			}else {
				functionDtoList.add(pfDto);
			}
		}
		return functionDtoList;
	}

	private String getFunCUrlRoot(ProfFunctionDto pfDto) {
		String funcUrl = pfDto.getFuncUri();
		String result = "";
		if(StringUtil.isBlank(funcUrl)) {
			return result;
		}else {
			int level = 0;
			for(String subUrl :funcUrl.split("/")) {
				result=result+subUrl;
				level ++;
				if(level==2) {
					break;
				}else {
					result=result+"/";
				}
			}
		}
		return result;
	}

	private boolean checkUserCanUse(UserDto userDto, ProfFunctionDto pfDto) {
		
		
		if(pfDto.getUseState().equals("N")) {
			return false;
		}
		
		RefFunctionRole rfr = refFunctionRoleRepository.getEntityByJPQL("SELECT rfr FROM RefFunctionRole rfr WHERE rfr.funcNo=? AND rfr.roleType=? AND rfr.roleNo=?", pfDto.getFuncNo(),userDto.getRoleType(),userDto.getRoleNo());
		if(rfr!=null) {
			return true;
		}
		
		RefFunctionUser rfu =refFunctionUserRepository.getEntityByJPQL("SELECT rfu FROM RefFunctionUser rfu WHERE rfu.funcNo=? AND rfu.userId=?", pfDto.getFuncNo(),userDto.getId());
		if(rfu!=null) {
			return true;
		}
		
		
		return false;
	}
	
	private Object getUserMenuTree(UserDto userDto) {
		String tree = "";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> li = userService.getUserFunctionList(userDto, "getUserMenuTree", "", "");
		
		for (Map<String, Object> map : li) {
			if (map.get("role_type") != null && !map.get("role_type").equals("")) {
				list.add(map);
			}
		}
		//func_pict
		int index = 0;
		int count = 0;
		int ii = 0;		
		for (Map<String, Object> map : list) {
			System.out.println(">>>>>"+map);
			if (map.get("level_").toString().equals("1")) {
				index++;
				if (tree.equals(""))
					tree += "<li id='"+map.get("func_id")+"'>";
				else
					tree += "</ul></li><li id='"+map.get("func_id")+"'>";
				tree += "<a id='a"+map.get("func_id")+"' href='#list-group-" + index + "'  aria-expanded='false' data-toggle='collapse'>";
				tree += " <i class=\""+(map.get("func_pict")==null?"icon-home":map.get("func_pict").toString())+"\"></i>" + map.get("func_name");
				tree += "&nbsp;<span class='caret'></span></a>";
				tree += "<ul id='list-group-" + index + "' class='collapse list-unstyled' >";
			} else if (map.get("level_").toString().equals("2")) {
				if (map.get("func_uri") == null) {
					tree += "<div class='lvlTwo'>";
					tree += "<a data-target='#list-group-" + index + map.get("func_id").toString()
							.substring(0, 3) + "' href='#' class='' aria-expanded='true'><li>";
					tree += " <i class=\""+(map.get("func_pict")==null?"icon-home":map.get("func_pict").toString())+"\"></i>" + map.get("func_name") + "&nbsp;<span class='caret'>" 
							+ "</span></li></a>";
					tree += "<div id='list-group-" + index + map.get("func_id").toString().substring(0, 3) + 
							"' class='list-group-div' style='display:none' >";
					count = 0;
					ii = 0;
					for (Map<String, Object> map3 : list) {
						if (((String) map3.get("parent_id")).equals((String) map.get("func_id"))) count++;
					}
				} else {
					tree += "<li id='"+map.get("func_id")+"' class='"+map.get("restriction")+"'><a href='#' onclick=\"doAction('" + map.get("func_uri") + 
							" ', '" + map.get("func_id") + "', '" + map.get("func_name") + "')\" funcId='" + map.get("func_id") + "'>" +
							"&nbsp;&nbsp; <i class=\""+(map.get("func_pict")==null?"icon-home":map.get("func_pict").toString())+"\"></i>" + map.get("func_name") + "</a></li>";
				}
			} else {
				tree += "<li id='"+map.get("func_id")+"' class='"+map.get("restriction")+"'><a href='#' onclick=\"doAction('" + map.get("func_uri") + 
						" ', '" + map.get("func_id") + "', '" + map.get("func_name") + "')\" funcId='" + map.get("func_id") + "'>" +
						"&nbsp;&nbsp;<span class='glyphicon glyphicon glyphicon-play' style='font-size:8px;'></span>" + map.get("func_name") + "</a></li>";
				ii++;
				if (ii == count) tree += "</div></div>";
			}
		}
		tree += "</ul></li>";
		return tree;
	}

	/**
	 * 取得登入者每個功能的權限控制
	 * @param roleType
	 * @param roleNo
	 * @return Map<String,String>
	 * 
	 */
	private Map<String, String> getFuncAuthority(String roleType, String roleNo) {
		Map<String,String> funcAuthorityMap= new HashMap<String,String>();
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,StringType> scalarMap = new HashMap<String,StringType>();
		StringBuffer sql=new StringBuffer("SELECT  rf FROM RefFunctionRole rf where roleType =:roleType and roleNo =:roleNo ");
		params.put("roleType", roleType);
		params.put("roleNo", roleNo);
		List<RefFunctionRole> list=refFunctionRoleRepository.findByJPQLForPlaceholder(sql.toString(), params);
		for(RefFunctionRole obj:list){
			funcAuthorityMap.put(obj.getFuncNo(),obj.getAuthority());
		}
		
		return funcAuthorityMap;
	}

	/**
	 * 登入成功處理
	 * @param user
	 * 
	 */
	@Override
	public void updateUserLoginSuccess(User user){
		user.setLoginTime(DateUtil.getSystemDateTimeObject());
		user.setLoginErrTimes("");
		userRepository.update(user);
	}

	@Override
	public String getBreadcrumbPath(String funcId) {
		StringBuilder sql = new StringBuilder();
		/*sql.append("select t.func_name ");
		sql.append("from prof_function t ");
		sql.append("where t.func_id <> '000000' ");
		sql.append("start with t.func_id = ? ");
		sql.append("connect by prior t.parent_id = t.func_id ");
		sql.append("order by t.func_id ");*/ //Oracle
		sql.append("WITH RECURSIVE cte AS (                                   ");
		sql.append("   SELECT func_name,func_id,parent_id                     ");
		sql.append("   FROM   prof_function                                   ");
		sql.append("   where func_id = ?                                      ");
	    sql.append("                                                          ");
		sql.append("   UNION  ALL                                             ");
	    sql.append("                                                          ");
		sql.append("   SELECT e.func_name,e.func_id,e.parent_id               ");
		sql.append("   FROM   cte c                                           ");
		sql.append("   JOIN   prof_function e ON c.parent_id = e.func_id      ");
		sql.append("   )                                                      ");
		sql.append("SELECT *                                                  ");
		sql.append("FROM   cte                                                ");
		sql.append("ORDER BY  func_id                                         ");
		List<Map<String, Object>> list = profFunctionRepository.findListMapBySQL(sql.toString(), funcId);
		String path = "";
		for (Map<String, Object> map : list) {
			path += "<li>"+map.get("func_name") + "</li>";
		}
		path = path.substring(0, path.length() - 1);
		return path; 
	}

	@Override
	public void ajaxChangeLocal(HttpServletRequest request,HttpServletResponse response, HomeBean formBean) {
		request.getSession(true).setAttribute("local", formBean.getLocal());
	}
	
}
