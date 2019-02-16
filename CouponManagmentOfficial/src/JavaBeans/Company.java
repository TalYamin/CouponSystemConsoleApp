package JavaBeans;

import java.util.ArrayList;
import java.util.List;



/**
 * @author Shay Ben Haroush
 *
 */
public class Company {

	//data members of Company
	private long companyId;
	private String companyName;
	private String companyPassword;
	private String companyEmail;

	//empty CTOR Company
	public Company() {

	}

	//full CTOR Company
	public Company(long companyId, String companyName, String companyPassword, String companyEmail) {
		this.companyId = companyId;
		this.companyName = companyName;
		this.companyPassword = companyPassword;
		this.companyEmail = companyEmail;
	}

	
	//getter and setters methods of Company
	
	public long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyPassword() {
		return this.companyPassword;
	}

	public void setCompanyPassword(String companyPassword) {
		this.companyPassword = companyPassword;
	}

	public String getCompanyEmail() {
		return this.companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	
	//toString method of Company
	
	@Override
	public String toString() {
		return "Company [companyId=" + this.getCompanyId() + ", companyName=" + this.getCompanyName()
				+ ", companyPassword=" + this.getCompanyPassword() + ", companyEmail=" + this.getCompanyEmail() + "]";
	}

}
