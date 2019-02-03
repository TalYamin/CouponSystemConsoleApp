package Exceptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CouponExpiredException extends Exception {
	
	private LocalDate endDate;
	private long couponId;
	private long customerId;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
	
	public CouponExpiredException (String message, String endDate, long couponId, long customerId) {
		super(message);
		LocalDate endLocalDate = LocalDate.parse(endDate, this.formatter);
		this.endDate = endLocalDate;
		this.couponId = couponId;
		this.customerId = customerId;
	}

	public LocalDate getEndDate() {
		return this.endDate;
	}

	public long getCouponId() {
		return this.couponId;
	}

	public long getCustomerId() {
		return this.customerId;
	}
	
	

}
