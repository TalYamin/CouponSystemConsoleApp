package Exceptions;

import Client.ClientType;

public class ObjectNotFoundException extends Exception {

	private long clientId;
	private ClientType clientType;
	private long objectId;
	
	public ObjectNotFoundException(String message, long clientId, ClientType clientType, long objectId) {
		super(String.format(message + " objectId: %d", objectId));
		this.clientId = clientId;
		this.clientType = clientType;
		this.objectId = objectId;
	}

	public long getClientId() {
		return clientId;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public long getObjectId() {
		return objectId;
	}
	
	
	
}
