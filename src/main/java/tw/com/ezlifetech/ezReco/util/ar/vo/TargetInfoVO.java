package tw.com.ezlifetech.ezReco.util.ar.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 雲端辨識平台 辨識物件資訊 封裝類別
 * 
 * @author PaulChen
 */
public class TargetInfoVO {

	private String targetId;
	private Boolean activeFlag;
	private String name;
	private Float width;
	private Integer trackingRating;
	private String recoRating;

	private boolean isSuccess = false;

	public TargetInfoVO() {

	}

	public String getTargetId() {
		return targetId;
	}

	@JsonProperty("target_id")
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	@JsonProperty("active_flag")
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	public Float getWidth() {
		return width;
	}

	@JsonProperty("width")
	public void setWidth(Float width) {
		this.width = width;
	}

	public Integer getTrackingRating() {
		return trackingRating;
	}

	@JsonProperty("tracking_rating")
	public void setTrackingRating(Integer trackingRating) {
		this.trackingRating = trackingRating;
	}

	public String getRecoRating() {
		return recoRating;
	}

	@JsonProperty("reco_rating")
	public void setRecoRating(String recoRating) {
		this.recoRating = recoRating;
	}

	public boolean isSuccess() {
		return isSuccess;
	}
	
	@JsonIgnore
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}
