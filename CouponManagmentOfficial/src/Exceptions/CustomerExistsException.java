package Exceptions;

/**
 * @author Shay Ben Haroush
 *
 */

public class CustomerExistsException extends Exception {
	
	private String customerName;
	
	public CustomerExistsException(String message, String  customerName) {
		super(String.format(message + "customerName: %s", customerName));
		this.customerName = customerName;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	
}
