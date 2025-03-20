package tw.com.ezlifetech.ezReco.common.bean;

public class ErrorBean {
	private String mesg;
	private String labelName;
	private String errSpanId;
	public String getMesg() {
		return mesg;
	}
	public void setMesg(String mesg) {
		this.mesg = mesg;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getErrSpanId() {
		return errSpanId;
	}
	public void setErrSpanId(String errSpanId) {
		this.errSpanId = errSpanId;
	}
	
	
}
