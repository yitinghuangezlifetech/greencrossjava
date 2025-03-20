package tw.com.ezlifetech.ezReco.util.ar.spi;

/**
 * Vuforia VWS 呼叫異常時拋出本例外
 * 
 * @author PaulChen
 */
public class VuforiaException extends RuntimeException {

	private static final long serialVersionUID = -929503352510778047L;

	public VuforiaException() {

	}

	public VuforiaException(Throwable cause) {
		super(cause);
	}
	
	public VuforiaException(String message) {
		super(message);
	}
	
	public VuforiaException(String message, Throwable cause) {
		super(message, cause);
	} 

}
