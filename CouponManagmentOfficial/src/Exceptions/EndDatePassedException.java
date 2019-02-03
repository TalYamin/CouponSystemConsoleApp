package Exceptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EndDatePassedException extends Exception {
	
	private LocalDate endDate;
	private long couponId;
	private long companyId;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

	public EndDatePassedException(String message, String endDate, long couponId, long companyId) {
		super(message);
		LocalDate endLocalDate = LocalDate.parse(endDate, this.formatter);
		this.endDate = endLocalDate;
		this.couponId = couponId;
		this.companyId = companyId;
	}

	public LocalDate getEndDate() {
		return this.endDate;
	}

	public long getCouponId() {
		return this.couponId;
	}

	public long getCompanyId() {
		return this.companyId;
	}
	
	
}
