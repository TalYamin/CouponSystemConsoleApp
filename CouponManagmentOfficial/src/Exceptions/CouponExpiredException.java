package Exceptions;

/**
 * @author Shay Ben Haroush
 *
 */

import java.time.LocalDate;

public class CouponExpiredException extends Exception {
	
	private String endDate;
	private long couponId;
	private long customerId;
	
	public CouponExpiredException (String message, String endDate, long couponId, long customerId) {
		super(String.format(message + "endDate: %s, couponId: %d, customerId: %d", endDate, couponId, customerId));
		this.endDate = endDate;
		this.couponId = couponId;
		this.customerId = customerId;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public long getCouponId() {
		return this.couponId;
	}

	public long getCustomerId() {
		return this.customerId;
	}
	
	

}
