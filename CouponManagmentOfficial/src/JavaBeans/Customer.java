package JavaBeans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shay Ben Haroush
 *
 */


public class Customer {

	//data members Customer
	private long customerId;
	private String customerName;
	private String customerPassword;
	

	
	//empty CTOR Customer
	public Customer(){
		
	}

	//full CTOR Customer
	public Customer(long customerId, String customerName, String customerPassword) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerPassword = customerPassword;
	}

	
	//getters and setters methods of Customer
	
	public long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPassword() {
		return this.customerPassword;
	}

	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}


	//toString method of Customer
	@Override
	public String toString() {
		return "Customer [customerId=" + this.getCustomerId() + ", customerName=" + this.getCustomerName()
				+ ", customerPassword=" + this.getCustomerPassword() + "]";
	}

}
