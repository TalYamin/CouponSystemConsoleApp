package JavaBeans;


/**
 * @author Shay Ben Haroush
 *
 */

/*
 * This class sets Customer type object.
 */


public class Customer {

	/* Data members of Customer */
	private long customerId;
	private String customerName;
	private String customerPassword;
	

	
	/* Empty CTOR Customer */
	public Customer(){
		
	}

	//Full CTOR Company: sets the customerId, customerName, customerPassword */
	public Customer(long customerId, String customerName, String customerPassword) throws Exception {
		try {
		
		setCustomerId(customerId);
		setCustomerName(customerName);
		setCustomerPassword(customerPassword);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	
	/* Getter method to receive the value of customer id */
	public long getCustomerId() {
		return this.customerId;
	}

	/* Setter method to set the value of customer id */
	public void setCustomerId(long customerId) throws Exception {
		
		try {
		
		if (customerId > 0) {
			this.customerId = customerId;
		}else {
			throw new Exception("Not vaild ID for Customer");
		}
		
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	/* Getter method to receive the value of customer name */
	public String getCustomerName() {
		return this.customerName;
	}

	/* Setter method to set the value of customer name */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/* Getter method to receive the value of customer password */
	public String getCustomerPassword() {
		return this.customerPassword;
	}
	
	/* Setter method to set the value of customer password */
	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}


	/* toString method of Company - allows pattern to print*/
	@Override
	public String toString() {
		return "Customer [customerId=" + this.getCustomerId() + ", customerName=" + this.getCustomerName()
				+ ", customerPassword=" + this.getCustomerPassword() + "]";
	}

}
