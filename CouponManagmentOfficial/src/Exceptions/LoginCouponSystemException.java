package Exceptions;

import Client.ClientType;

/**
 * @author Shay Ben Haroush
 *
 */

public class LoginCouponSystemException extends Exception {
	
	private String userName;
	private String password;
	private ClientType clientType;
	

	public LoginCouponSystemException(String message, String userName, String password, ClientType clientType) {
		super(String.format(message + "userName: %s, password: %s, clientType: %s", userName, password, clientType.toString()));
		this.userName = userName;
		this.password = password;
		this.clientType = clientType;
	}


	public String getUserName() {
		return userName;
	}


	public String getPassword() {
		return password;
	}


	public ClientType getClientType() {
		return clientType;
	}
	
	
	
}
