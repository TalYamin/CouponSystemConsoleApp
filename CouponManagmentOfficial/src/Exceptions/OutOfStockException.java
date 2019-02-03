package Exceptions;

public class OutOfStockException extends Exception {
	
	private int amount;
	private long couponId;
	private long customerId;
	
	public OutOfStockException (String message, int amount, long couponId, long customerId) {
		super(message);
		this.amount = amount;
		this.couponId = couponId;
		this.customerId = customerId;
	}

	public int getAmount() {
		return amount;
	}

	public long getCouponId() {
		return couponId;
	}

	public long getCustomerId() {
		return customerId;
	}
	
}
