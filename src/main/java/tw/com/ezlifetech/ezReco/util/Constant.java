package tw.com.ezlifetech.ezReco.util;

/**
 * 放置靜態屬性
 * @author 
 *
 */
public class Constant {
	//一般共用
	public final static String LANGUAGE_ZH_TW="zh_TW";//語系
	public final static String SESSION_USER="userProf";//SESSION 物件名稱
	public final static String FLAG_Y="Y";//Y
	public final static String FLAG_N="N";//N
	public final static String FLAG_O="O";//舊資料
	public final static String FLAG_F="F";//失敗
	public final static String FLAG_R="R";//重送
	public final static String FLAG_U="U";//修改
	
	//審核狀態
	public final static String FLOW_STATUS_W="W";//暫存
	public final static String FLOW_STATUS_C="C";//審核中
	public final static String FLOW_STATUS_Y="Y";//完成
	public final static String FLOW_STATUS_R="R";//退件
	public final static String FLOW_STATUS_P="P";//待傳送(待審核)

	
	//角色
	public static final String ROLE_TYPE_COSMED = "C";//康是
	public static final String ROLE_TYPE_SUPPLIER = "S";//供應商
	public static final String ROLE_NO_PRODUCT_LEADER = "10";//商品部主管
	public static final String ROLE_NO_TEAMLEADER = "11";//TeamLeader
	public static final String ROLE_NO_CM = "12";//CM	
	public static final String ROLE_NO_MANAGEMENT = "13";//行銷管理	
	
	//檔案
	public final static String JSONFILE_LAST_MODIFY_="JSONFILE_LAST_MODIFY_";//jsonfile 檔案最後修改時間
	public static final String JSON_FILE_ROOT_PATH = "/resources/json/";//JSON File存放路徑
	public static final String UPLOAD_TEMP_FILE_PATH = "/resources/upload/";//檔案上傳暫存 File存放路徑
	public static final String REMARK_IMAGE_PATH = "/img/remark.jpg";//?
	public static final String SOURCE_TYPE_QUOTATION="quotation";//提報圖
	
	public static final String PREVIEW_MODE = "preview";//預覽模式
	public static final String ENCRYPT_MODE = "SHA-1";//加密原則
	
	//使用者狀態
	public static final String USER_STATUS_WAIT="W";//未啟用
	public static final String USER_STATUS_ACTIVITY="A";//使用中
	public static final String USER_STATUS_PAUSE="P";//停用
	public static final String USER_STATUS_DELETE="D";//已刪除
	public static final String USER_STATUS_LOCK="L";//已刪除
	
	//操作訊息
	public static final String MSG_LOGIN_ERROR="帳號密碼不正確，請重新輸入";
	public static final String MSG_LOGIN_LOCK="使用者帳號已鎖定，請洽系統管理人員";
	public static final String MSG_SECURITY_ERROR ="驗證碼不正確";
	public static final String MSG_ERROR="系統發生錯誤，請洽系統管理人員";
	public static final String MSG_UPDATE_SUCCESS="儲存成功";
	public static final String MSG_UPDATE_FAILED="儲存失敗";
	public static final String MSG_ADD_SUCCESS="新增成功";
	public static final String MSG_ADD_FAILED="新增失敗";
	public static final String MSG_DELETE_SUCCESS="刪除成功";
	public static final String MSG_DELETE_FAILED="刪除失敗";
	public static final String MSG_WS_DUPLICATE_ERROR="資料重覆，請重新輪入";
	public static final String MSG_WS_ERROR="系統發生錯誤，請洽系統管理人員";
	public static final String MSG_WS_ERROR_KEY="Private Key已失效";
	public static final String WS_RESULT_Y="Y";
	public static final String WS_RESULT_N="N";
	public static final String MSG_SUPPLIER_NOEXIST_ERROR="廠編不存在";
	public static final String MSG_SUPPLIER_EXIST_ERROR="廠編已存在";
	public static final String MSG_RE_ENTER="請重新輸入";
	
	//JSON CODE
	public static final String JSON_CODE_USER_NAME="userName";//帳號名稱
	
	//目錄名稱
	public static final String BULLETIN_FOLDER = "bulletin"; //公告目錄
	public static final String NEWPRODUCT_FOLDER = "quotation"; //新品提報目錄
	
	//FlowType
	public static final String FLOW_TYPE_NEW = "newPrd";//新品提報
	public static final String FLOW_TYPE_MODIFY_PRD = "modifyPrd";//商品異動
	public static final String FLOW_TYPE_EC = "ec";//EC審核
	public static final String FLOW_TYPE_ACCT35_AUDIT = "outStock";//缺貨罰款
	public static final String FLOW_TYPE_ACCT06_AUDIT = "support";//檔期廣告費
	public static final String FLOW_TYPE_ACCT22_AUDIT = "pallet";//特殊陳列費
	
	public static final String DC_99 = "2352978499";//北倉
	public static final String DC_98 = "2352978498";//南倉
	public static final String DELIVERY_TYPE_DC="1";//物流商
	public static final String DELIVERY_TYPE_SELF="2";//自送商
	
	//網購資訊
	public static final String EC_CATEGORY_YAHOO_MALL = "yahoo"; 
	public static final String EC_CATEGORY_YAHOO_SHOP = "yahooShop"; 
	public static final String EC_CATEGORY_ESHOP_MALL = "eshop";
	public static final String EC_CATEGORY_ESHOP_SHOP = "eshopShop";
	public static final String EC_CATEGORY_SHOPEE_MALL = "shopee";
	public static final String EC_CATEGORY_SHOPEE_SHOP = "shopeeShop";
		
	//排除生效日
	public static final String EXCLUDE_EFFECTIVE_DATE_NEWPRD="newPrd";//新品生效日
	public static final String EXCLUDE_EFFECTIVE_DATE_MODIFYPRD="modifyPrd";//異動生效日
	public static final String EXCLUDE_EFFECTIVE_DATE_COST="cost";//成本生效日
	public static final String EXCLUDE_EFFECTIVE_DATE_PRICE="price";//零售生效日
}
