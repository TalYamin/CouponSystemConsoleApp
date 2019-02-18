package DAO;

import java.util.List;

import JavaBeans.Coupon;
import JavaBeans.Customer;

/**
 * @author Shay Ben Haroush
 *
 */

public interface Customer_CouponDAO {
	
	/*
	 * Interface - queries for Customer_Coupon table.
	 * Methods which will be overridden in Customer_Coupon DBDAO class.
	 */
	
	void insertCustomer_Coupon(Customer customer, Coupon coupon) throws Exception;

	void removeCustomer_Coupon(Customer customer, Coupon coupon) throws Exception;

	void removeCustomer_Coupon(Customer customer) throws Exception;
	
	void removeCustomer_Coupon(Coupon coupon) throws Exception;
	
	List<Long> getCustomerId(long couponId) throws Exception;

	List<Long> getAllCustomersId() throws Exception;
	
	List<Long> getCouponId (long customerId) throws Exception;
	
	List<Long> getAllCouponsId() throws Exception;
}
