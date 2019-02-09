package Exceptions;

/**
 * @author Shay Ben Haroush
 *
 */

public class CouponExistsException extends Exception {
	
	private String title;
	private long companyId; 
	
	public CouponExistsException (String message, String title, long companyId) {
		super(String.format(message + "couponTitle: %s, companyId: %d", title, companyId));
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
