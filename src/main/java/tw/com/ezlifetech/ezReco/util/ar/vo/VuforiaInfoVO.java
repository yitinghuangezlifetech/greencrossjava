package tw.com.ezlifetech.ezReco.util.ar.vo;

/**
 * 雲端辨識平台資訊 封裝物件
 * 
 * @author PaulChen
 */
public class VuforiaInfoVO {

	// 驗証資訊
	private String accessKey;
	private String secretKey;

	// 刪除、查詢使用
	private String targetId;

	// 新增使用
	private String imagePath;
	private String imageNm;
	
	
	private String metaData;

	public VuforiaInfoVO() {

	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImageNm() {
		return imageNm;
	}

	public void setImageNm(String imageNm) {
		this.imageNm = imageNm;
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}

}
