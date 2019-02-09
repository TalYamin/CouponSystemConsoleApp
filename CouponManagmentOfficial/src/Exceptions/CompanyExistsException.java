package Exceptions;

/**
 * @author Shay Ben Haroush
 *
 */

public class CompanyExistsException extends Exception {
	
	private String comanyName;
	
	
	public CompanyExistsException(String message, String companyName) {
		super(String.format(message + "%s", companyName));
		this.comanyName = companyName;
	}
	
	public String getCompanyName() {
		return this.comanyName;
	}
}
