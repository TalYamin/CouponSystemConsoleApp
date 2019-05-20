package JavaBeans;

/**
 * @author Shay Ben Haroush
 *
 */

/*
 * This class sets Company type object.
 */

public class Company {

	/* Data members of Company */
	private long companyId;
	private String companyName;
	private String companyPassword;
	private String companyEmail;

	/* Empty CTOR Company */
	public Company() {

	}

	// Full CTOR Company: sets the companyId, companyName, companyPassword,
	// companyEmail */
	public Company(long companyId, String companyName, String companyPassword, String companyEmail) throws Exception {

		try {

			setCompanyId(companyId);
			setCompanyName(companyName);
			setCompanyPassword(companyPassword);
			setCompanyEmail(companyEmail);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/* Getter method to receive the value of company id */
	public long getCompanyId() {
		return this.companyId;
	}

	/* Setter method to set the value of company id */
	public void setCompanyId(long companyId) throws Exception {
		try {

			if (companyId > 0) {
				this.companyId = companyId;
			} else {
				throw new Exception("Not vaild ID for Company");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/* Getter method to receive the value of company name */
	public String getCompanyName() {
		return this.companyName;
	}

	/* Setter method to set the value of company name */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/* Getter method to receive the value of company password */
	public String getCompanyPassword() {
		return this.companyPassword;
	}

	/* Setter method to set the value of company password */
	public void setCompanyPassword(String companyPassword) {
		this.companyPassword = companyPassword;
	}

	/* Getter method to receive the value of company email */
	public String getCompanyEmail() {
		return this.companyEmail;
	}

	/* Setter method to set the value of company email */
	public void setCompanyEmail(String companyEmail) throws Exception {
		try {

			if (companyEmail.contains("@")) {
				this.companyEmail = companyEmail;
			} else {
				throw new Exception("Not vaild Email for Company");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/* toString method of Company - allows pattern to print */

	@Override
	public String toString() {
		return "Company [companyId=" + this.getCompanyId() + ", companyName=" + this.getCompanyName()
				+ ", companyPassword=" + this.getCompanyPassword() + ", companyEmail=" + this.getCompanyEmail() + "]";
	}

}
