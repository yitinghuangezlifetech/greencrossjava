package tw.com.ezlifetech.ezReco.util.ar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SignatureException;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;

import tw.com.ezlifetech.ezReco.util.ar.conf.Settings;
import tw.com.ezlifetech.ezReco.util.ar.spi.VuforiaException;



/**
 * 
 * @author PaulChen
 */
public class SignatureBuilder {

	private static Set<String> acceptHttpMethod;
	static {
		acceptHttpMethod = new HashSet<>();
		acceptHttpMethod.add(Settings.HTTP_METHOD_POST);
		acceptHttpMethod.add(Settings.HTTP_METHOD_GET);
		acceptHttpMethod.add(Settings.HTTP_METHOD_PUT);
		acceptHttpMethod.add(Settings.HTTP_METHOD_DELETE);
	}
	
	SignatureBuilder() {
		
	}
	
	/**
	 * 
	 * 
	 * @param request
	 * @param secretKey
	 * @return String
	 */
	public String tmsSignature(HttpUriRequest request, String secretKey) {
		String method = request.getMethod().toUpperCase();
		String contentType = "";
		String hexDigest = "d41d8cd98f00b204e9800998ecf8427e"; // Hex digest of an empty string
		
		boolean isAccept = acceptHttpMethod.contains(method);
		if (isAccept == false) {
			RuntimeException exc = new VuforiaException("ERROR: Invalid content type passed to Sig Builder");
			throw exc;
		}
		
		if (method.equalsIgnoreCase(Settings.HTTP_METHOD_POST) || method.equalsIgnoreCase(Settings.HTTP_METHOD_PUT)) {
			contentType = "application/json";
			// If this is a POST or PUT the request should have a request body
			hexDigest = contentMD5((HttpEntityEnclosingRequestBase) request);
		}

		// Date in the header and date used to calculate the hash must be the same
		String dateValue = request.getFirstHeader("Date").getValue();
		String requestPath = request.getURI().getPath();
		
		StringBuilder toDigestBuilder = new StringBuilder();
		toDigestBuilder.append(method);
		toDigestBuilder.append("\n");
		toDigestBuilder.append(hexDigest);
		toDigestBuilder.append("\n");
		toDigestBuilder.append(contentType);
		toDigestBuilder.append("\n");
		toDigestBuilder.append(dateValue);
		toDigestBuilder.append("\n");
		toDigestBuilder.append(requestPath);
		String toDigest = toDigestBuilder.toString();

		String shaHashed = "";
		
		try {
			shaHashed = calculateRFC2104HMAC(secretKey, toDigest);
		} catch (SignatureException e) {
			VuforiaException exc = new VuforiaException(e);
			throw exc;
		}
		
		return new String(shaHashed);
	}

	/**
	 * 
	 * 
	 * @param httpMethod
	 * @return
	 */
	private String contentMD5(HttpEntityEnclosingRequestBase httpMethod) {
		ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
		
		try {
			httpMethod.getEntity().writeTo(requestOutputStream);
		} catch (IOException e) {
			VuforiaException exc = new VuforiaException("ERROR: IOException caught when writing Content MD5 hash", e);
			throw exc;
		}

		return DigestUtils.md5Hex(requestOutputStream.toByteArray()).toLowerCase();
	}

	/**
	 * 
	 * 
	 * @param key
	 * @param data
	 * @return
	 * @throws java.security.SignatureException
	 */
	private String calculateRFC2104HMAC(String key, String data) throws java.security.SignatureException {
		String result = "";
		try {
			// get an hmac_sha1 key from the raw key bytes
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");

			// get an hmac_sha1 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);

			// compute the hmac on input data bytes
			byte[] rawHmac = mac.doFinal(data.getBytes());

			// base64-encode the hmac
			result = new String(Base64.encodeBase64(rawHmac, false));

		} catch (Exception e) {
			VuforiaException exc = new VuforiaException(e);
			throw exc;
		}
		
		return result;
	}
}
