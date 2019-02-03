package Exceptions;

public class CompanyExistsException extends Exception {
	
	private String comanyName;
	
	
	public CompanyExistsException(String message, String companyName) {
		super(message);
		this.comanyName = companyName;
	}
	
	public String getCompanyName() {
		return this.comanyName;
	}
}
