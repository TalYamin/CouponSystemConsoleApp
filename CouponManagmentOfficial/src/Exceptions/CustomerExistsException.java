package Exceptions;

public class CustomerExistsException extends Exception {
	
	private String customerName;
	
	public CustomerExistsException(String message, String  customerName) {
		super(message);
		this.customerName = customerName;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	
}
