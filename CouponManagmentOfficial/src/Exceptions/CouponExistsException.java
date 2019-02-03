package Exceptions;

public class CouponExistsException extends Exception {
	
	private String title;
	private long companyId; 
	
	public CouponExistsException (String message, String title, long companyId) {
		super(message);
		this.title = title;
		this.companyId = companyId;
	}

	public String getTitle() {
		return this.title;
	}

	public long getCompanyId() {
		return this.companyId;
	}
	
	
}
