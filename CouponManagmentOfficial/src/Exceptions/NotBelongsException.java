package Exceptions;

import SystemUtils.ClientType;

/**
 * @author Shay Ben Haroush
 *
 */

public class NotBelongsException extends Exception{
	
	private long comapnyId;
	private String clientType;
	private long couponId;
	
	public NotBelongsException (String message, long comapnyId, String clientType, long couponId) {
		super(String.format(message + "comapnyId: %d, clientType: %s, couponId: %d" , comapnyId, clientType, couponId));
		this.comapnyId = comapnyId;
		this.clientType = clientType;
		this.couponId = couponId;
	}

	public long getComapnyId() {
		return comapnyId;
	}

	public String getClientType() {
		return clientType;
	}

	public long getCouponId() {
		return couponId;
	}

	
	

}
