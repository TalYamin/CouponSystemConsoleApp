package Exceptions;

public class SamePurchaseException extends Exception {
	
	private long couponId;
	private long customerId;
	
	public SamePurchaseException(String message, long couponId, long customerId) {
		super(message);
		this.couponId = couponId;
		this.customerId = customerId;
	}

	public long getCouponId() {
		return this.couponId;
	}

	public long getCustomerId() {
		return this.customerId;
	}

}
