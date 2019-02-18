package DAO;

import java.util.List;

import JavaBeans.Customer;

/**
 * @author Shay Ben Haroush
 *
 */

public interface CustomerDAO {
	
	/*
	 * Interface - queries for Customer table.
	 * Methods which will be overridden in Customer DBDAO class.
	 */

	void insertCustomer(Customer customer) throws Exception;

	void removeCustomer(Customer customer) throws Exception;

	void updateCustomer(Customer customer) throws Exception;

	Customer getCustomer(long customerId) throws Exception;

	List<Customer> getAllCustomers() throws Exception;

}
