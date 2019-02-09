package Exceptions;

/**
 * @author Shay Ben Haroush
 *
 */

import java.time.LocalDate;

public class EndDatePassedException extends Exception {
	
	private String endDate;
	private long couponId;
	private long companyId;

	public EndDatePassedException(String message, String endDate, long couponId, long companyId) {
		super(String.format(message + "endDate: %s, couponId: %d, companyId: %d", endDate, couponId, companyId));
		this.endDate = endDate;
		this.couponId = couponId;
		this.companyId = companyId;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public long getCouponId() {
		return this.couponId;
	}

	public long getCompanyId() {
		return this.companyId;
	}
	
	
}
