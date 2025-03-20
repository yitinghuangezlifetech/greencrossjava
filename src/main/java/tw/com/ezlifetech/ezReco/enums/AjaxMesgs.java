package tw.com.ezlifetech.ezReco.enums;

public enum AjaxMesgs {

	SAVE_SUCCESSFUL("ss","儲存成功","Successful storage"),
	REMOVE_SUCCESSFUL("rs","刪除成功","Successfully deleted"),
	APPLY_SUCCESSFUL("as","套用成功","Apply successfully"),
	CANCEL_SUCCESSFUL("cs","取消成功","Cancel success"),
	UPLAOD_SUCCESSFUL("us","上傳成功","Upload success"),
	ADD_SUCCESSFUL("ads","新增成功","Added successfully"),
	CHANGE_SUCCESSFUL("chgs","修改成功","Successfully modified"),
	PUNCH_SUCCESSFUL("punchs","打卡成功","Successful"),
	SAVE_FAIL("sf","儲存失敗","Storage failure"),
	REMOVE_FAIL("rf","刪除失敗","Failed to delete"),
	APPLY_FAIL("af","套用失敗","Apply failure"),
	CANCEL_FAIL("cf","取消失敗","Cancel failure"),
	SEARCH_FAIL("sef","取得失敗","Failed"),
	UPLAOD_FAIL("uf","上傳失敗","Upload failed"),
	ADD_FAIL("adf","新增失敗","Add failed"),
	PUNCH_FAILL("punchf","打卡失敗","Failed"),
	REVIEW_SUCCESSFUL("revs","審查成功","Successful review"),
	REVIEW_FAIL("revf","審查失敗","Review failed"),
	REMOVE_FAIL_PLZ_WAIT("rfpw","刪除失敗，物件正在處理中，請稍後再試","Delete failed, object is being processed, please try again later"),
	
	SUBMIT_SUCCESSFUL("subs","送出成功","Successful delivery"),
	SUBMIT_FAILL("subf","送出失敗","Failed"),
	
	;
	
	private String code;
	private String doc;
	private String docEn;
	
	AjaxMesgs(String code,String doc,String docEn) {
		this.code = code;
		this.doc = doc;
		this.docEn = docEn;
	}

	public String getCode() {
		return code;
	}

	public String getDoc() {
		return doc;
	}
	
	public String getDocEn() {
		return docEn;
	}
	
	public String getDoc(String lang) {
		return "en".equals(lang)?docEn:doc;
	}
}
