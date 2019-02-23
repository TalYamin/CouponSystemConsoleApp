package Exceptions;

public class ConnectionException extends Exception {

	private int availableConnections;
	
	public ConnectionException(String message, int availableConnections) {
		super(String.format(message + "available Connections: %d", availableConnections));
		this.availableConnections = availableConnections;
	}

	public int getAvailableConnections() {
		return availableConnections;
	}
	
	
}
