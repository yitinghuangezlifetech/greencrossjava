package tw.com.ezlifetech.ezReco.util.ar;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonObject;

import tw.com.ezlifetech.ezReco.bean.ArMetaDataBean;
import tw.com.ezlifetech.ezReco.bean.ArReturnBean;
import tw.com.ezlifetech.ezReco.enums.ArItemDlActions;
import tw.com.ezlifetech.ezReco.util.ar.vo.TargetInfoVO;
import tw.com.ezlifetech.ezReco.util.ar.vo.VuforiaInfoVO;



/**
 * 雲端辨識平台代理操作 類別
 * 
 * @author PaulChen
 */
public class VuforiaProxyService {

	private final String successCode = "Success";
	
	/**
	 * 取得全部雲端辨識平台資訊
	 * 
	 * @param vInfoVO
	 * @return
	 */
	public String getCloudSummary(VuforiaInfoVO vInfoVO) {
		String accessKey = vInfoVO.getAccessKey();
		String secretKey = vInfoVO.getSecretKey();
		
		CloudSummary summary = new CloudSummary(accessKey, secretKey);
		String jsonInfo = summary.doExecute();
		
		return jsonInfo;
	}
	
	/**
	 * 取得雲端辨識物件，依據雲端物件ID（TARGET_ID）
	 * 
	 * @param vInfoVO
	 * @return
	 */
	public TargetInfoVO getImageTargetByTargetId(VuforiaInfoVO vInfoVO) {
		String accessKey = vInfoVO.getAccessKey();
		String secretKey = vInfoVO.getSecretKey();
		String targetId = vInfoVO.getTargetId();

		GetTarget target = new GetTarget(accessKey, secretKey, targetId);

		String jsonStr = target.doExecute();

		boolean isSuccess = isSuccess(jsonStr);
		TargetInfoVO rtnVO = new TargetInfoVO();

		if (isSuccess) {
			rtnVO = getTargetInfo(jsonStr);
			rtnVO.setSuccess(true);
		}

		return rtnVO;
	}

	/**
	 * 取得全部已上傳至雲端辨識物件
	 * 
	 * @param vInfoVO
	 * @return
	 */
	public List<TargetInfoVO> getAllImageTarget(VuforiaInfoVO vInfoVO) {
		 throw new UnsupportedOperationException(" It should be query from database. ");
//		String accessKey = vInfoVO.getAccessKey();
//		String secretKey = vInfoVO.getSecretKey();
//
//		GetAllTarget target = new GetAllTarget(accessKey, secretKey);
//
//		String jsonStr = target.doExecute();
//		System.out.println(jsonStr);
//		
//		boolean isSuccess = isSuccess(jsonStr);
//		
//		List<TargetInfoVO> rtnLs = new ArrayList<>();
//		
//		if (isSuccess) {
//		}
//		
//		return rtnLs;
	}

	/**
	 * 停用雲端辨識物件
	 * 
	 * @param vInfoVO
	 * @return
	 */
	public boolean unActiveImageTarget(VuforiaInfoVO vInfoVO) {
		String accessKey = vInfoVO.getAccessKey();
		String secretKey = vInfoVO.getSecretKey();
		String targetId = vInfoVO.getTargetId();
		boolean isActive = false;

		ActiveTarget target = new ActiveTarget(accessKey, secretKey, targetId, isActive);

		String jsonStr = target.doExecute();
		boolean isSuccess = isSuccess(jsonStr);

		return isSuccess;
	}

	/**
	 * 啟用雲端辨識物件
	 * 
	 * @param vInfoVO
	 * @return
	 */
	public boolean activeImageTarget(VuforiaInfoVO vInfoVO) {
		String accessKey = vInfoVO.getAccessKey();
		String secretKey = vInfoVO.getSecretKey();
		String targetId = vInfoVO.getTargetId();
		boolean isActive = true;

		ActiveTarget target = new ActiveTarget(accessKey, secretKey, targetId, isActive);

		String jsonStr = target.doExecute();
		boolean isSuccess = isSuccess(jsonStr);

		return isSuccess;
	}

	/**
	 * 刪除雲端辨識物件
	 * 
	 * @param vInfoVO
	 * @return
	 */
	public ArReturnBean delImageTarget(VuforiaInfoVO vInfoVO) {
		// 刪除前需先停用
//		boolean isUnActiveSuccess = unActiveImageTarget(vInfoVO);
//		if (isUnActiveSuccess == false) {
//			return false;
//		}

		String accessKey = vInfoVO.getAccessKey();
		String secretKey = vInfoVO.getSecretKey();
		String targetId = vInfoVO.getTargetId();
		
		DeleteTarget target = new DeleteTarget(accessKey, secretKey, targetId);

		String jsonStr = target.doExecute();
		
		
		ArReturnBean res = new ArReturnBean();
		res.setResValues(JsonUtil.fromJson(jsonStr, Map.class));
		res.setOrgResp(jsonStr);
		boolean isSuccess = res.getResValues().get("result_code").toString().equals("Success");
		res.setSuccessful(isSuccess);
		System.out.println("jsonStr"+jsonStr);

		return res;
	}

	/**
	 * 新增雲端辨識物件
	 * 
	 * @param vInfoVO
	 * @return
	 */
	public ArReturnBean createImageTarget(VuforiaInfoVO vInfoVO) {
		String accessKey = vInfoVO.getAccessKey();
		String secretKey = vInfoVO.getSecretKey();
		String imagePath = vInfoVO.getImagePath();
		String imageNm = vInfoVO.getImageNm();
		String metaData = vInfoVO.getMetaData();
		NewTarget target = new NewTarget(accessKey, secretKey, imagePath, imageNm ,metaData);

		// FIXME 此處需再使用多執行緒封裝，以取得上傳圖像資訊。如：辨識率等
		String jsonStr = target.doExecute();
		
		
		
		ArReturnBean res = new ArReturnBean();
		
		res.setResValues(JsonUtil.fromJson(jsonStr, Map.class));
		res.setOrgResp(jsonStr);
		
		
		
		
		boolean isSuccess = res.getResValues().get("result_code").toString().equals("TargetCreated");
		res.setSuccessful(isSuccess);
		
		System.out.println("jsonStr"+jsonStr);
		return res;
	}
	
	
	/**
	 * 新增雲端辨識物件
	 * 
	 * @param vInfoVO
	 * @return
	 */
	public boolean updateImageTarget(VuforiaInfoVO vInfoVO) {
		String accessKey = vInfoVO.getAccessKey();
		String secretKey = vInfoVO.getSecretKey();
		String imagePath = vInfoVO.getImagePath();
		String imageNm = vInfoVO.getImageNm();
		String metaData = vInfoVO.getMetaData();
		String targetId = vInfoVO.getTargetId();
		UpdateTarget target = new UpdateTarget(accessKey, secretKey, imagePath, imageNm ,metaData,targetId);

		// FIXME 此處需再使用多執行緒封裝，以取得上傳圖像資訊。如：辨識率等
		
		String jsonStr = target.doExecute();
		System.out.println("jsonStr"+jsonStr);
		
		boolean isSuccess = isSuccess(jsonStr);

		return isSuccess;
	}

	// ==============================================================================================
	// tools method.
	// ==============================================================================================

	/**
	 * 取得雲端辨識物件封裝資訊
	 * 
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private TargetInfoVO getTargetInfo(String jsonStr) {
		Map<String, Object> jsonMp = JsonUtil.fromJson(jsonStr, Map.class);
		Map<String, Object> targetMp = (Map<String, Object>) MapUtils.getObject(jsonMp, "target_record");

		TargetInfoVO rtnVO = new TargetInfoVO();
		if (targetMp != null) {
			// 轉成JSON後再封裝成物件
			String targetJsonStr = JsonUtil.toJson(targetMp);
			rtnVO = JsonUtil.fromJson(targetJsonStr, TargetInfoVO.class);
		}
		return rtnVO;
	}

	/**
	 * 判斷執行結果
	 * 
	 * @param jsonStr
	 * @return
	 */
	private boolean isSuccess(String jsonStr) {
		@SuppressWarnings("unchecked")
		Map<String, Object> jsonMp = JsonUtil.fromJson(jsonStr, Map.class);
		String resultCode = MapUtils.getString(jsonMp, "result_code");

		boolean isSuccess = StringUtils.equalsIgnoreCase(resultCode, successCode);

		return isSuccess;
	}
	
	
	public String genMetaData(ArMetaDataBean arMetaData) {
		JsonObject jo = new JsonObject();
		jo.addProperty("recognID", arMetaData.getRecognID());
		jo.addProperty("applicateID", arMetaData.getApplicateID());
		jo.addProperty("applicateCate", arMetaData.getApplicateCate());
		jo.addProperty("topic", arMetaData.getTopic());
		jo.addProperty("urlPath", arMetaData.getUrlPath());
		jo.addProperty("desc", arMetaData.getDesc());
		jo.addProperty("action", arMetaData.getAction());
		
		
		return jo.toString();
	}
	
	
	public String getActionByCode(String code) {
		
		for(ArItemDlActions arItemDlAction:ArItemDlActions.values()) {
			if(arItemDlAction.getCode().equals(code)) {
				return arItemDlAction.getAction();
			}
		}
		
		
		return null;
	}

}
