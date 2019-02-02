package DAO;

import java.util.List;

import JavaBeans.Customer;

/**
 * @author Tal Yamin
 *
 */

public interface CustomerDAO {
	
	//interface - queries from Customer table

	void insertCustomer(Customer customer) throws Exception;

	void removeCustomer(Customer customer) throws Exception;

	void updateCustomer(Customer customer) throws Exception;

	Customer getCustomer(long customerId) throws Exception;

	List<Customer> getAllCustomers() throws Exception;

}
