package tw.com.ezlifetech.ezReco.util.ar;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.codec.binary.Base64;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import tw.com.ezlifetech.ezReco.util.ar.conf.Settings;
import tw.com.ezlifetech.ezReco.util.ar.spi.VuforiaException;

/**
 * 上傳物件至雲端辨識平台
 * 
 * @author PaulChen
 */
public class NewTarget extends AbstractOperateTargetExecution {

	private String imageNm;
	private String imagePath;
	private String metaData;
	
	NewTarget(String accessKey, String secretKey, String imagePath, String imageNm) {
		super(accessKey, secretKey);
		
		this.imagePath = imagePath;
		this.imageNm   = imageNm;
		this.metaData="";
	}
	
	NewTarget(String accessKey, String secretKey, String imagePath, String imageNm ,String metaData) {
		super(accessKey, secretKey);
		
		this.imagePath = imagePath;
		this.imageNm   = imageNm;
		this.metaData=metaData;
	}
	
	@Override
	protected HttpUriRequest getRequest(String url) {
		String reqBodyJsonStr = getReqBody();
		
		try {
			HttpPost post = new HttpPost(url);
			post.setEntity(new StringEntity(reqBodyJsonStr));
			
			return post;
		} catch (UnsupportedEncodingException e) {
			VuforiaException exc = new VuforiaException(e);
			throw exc;
		}
	}

	@Override
	protected String getUrl() {
		StringBuilder path = new StringBuilder();
		path.append(Settings.VUFORIA_VWS_URL);
		path.append("/targets");
		
		return path.toString();
	}

	/**
	 * 取得上傳資訊所需參數，並回傳 JSON 物件
	 * 
	 * @return
	 */
	private String getReqBody() {
		Map<String, Object> jsonMp = new HashMap<>();
		
		File imageFile = FileUtils.getFile(imagePath);
		int width      = getImageWidth();
		
		try {
			byte[] image = FileUtils.readFileToByteArray(imageFile);
			jsonMp.put("name", imageNm); // Mandatory
			jsonMp.put("width", width); // Mandatory
			jsonMp.put("image", Base64.encodeBase64String(image)); // Mandatory
			// 非必填欄位
			// jsonMp.put("active_flag", 1); 
			 jsonMp.put("application_metadata", new String(Base64.encodeBase64(metaData.getBytes("UTF-8")), "UTF-8"));
		} catch (IOException e) {
			VuforiaException exc = new VuforiaException(e);
			throw exc;
		}
		
		String jsonStr = JsonUtil.toJson(jsonMp);
		return jsonStr;
	}
	
	/**
	 * 取得圖片寬度
	 * 
	 * @return
	 */
	private int getImageWidth() {
		try {
			BufferedImage bImg = ImageIO.read(new File(imagePath));
			int width = bImg.getWidth();
			
			return width;
		} catch (IOException e) {
			VuforiaException exc = new VuforiaException(e);
			throw exc;
		}
	}
	
}
