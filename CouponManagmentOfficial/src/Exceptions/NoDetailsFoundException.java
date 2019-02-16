package Exceptions;

import Client.ClientType;

public class NoDetailsFoundException extends Exception {
	
	private long clientId;
	private ClientType clientType;
	
	public NoDetailsFoundException(String message, long clientId, ClientType clientType) {
		super(message);
		this.clientId = clientId;
		this.clientType = clientType;
	}

	public long getClientId() {
		return clientId;
	}

	public ClientType getClientType() {
		return clientType;
	}

	
}
