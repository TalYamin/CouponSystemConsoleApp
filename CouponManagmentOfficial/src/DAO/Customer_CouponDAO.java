package DAO;

import java.util.List;

import JavaBeans.Coupon;
import JavaBeans.Customer;

/**
 * @author Tal Yamin
 *
 */

public interface Customer_CouponDAO {

	//interface - queries from Customer_Coupon table
	
	void insertCustomer_Coupon(Customer customer, Coupon coupon) throws Exception;

	void removeCustomer_Coupon(Customer customer, Coupon coupon) throws Exception;

	void removeCustomer_Coupon(Customer customer) throws Exception;
	
	void removeCustomer_Coupon(Coupon coupon) throws Exception;
	
	List<Long> getCustomerId(long couponId) throws Exception;

	List<Long> getAllCustomersId() throws Exception;
	
	List<Long> getCouponId (long customerId) throws Exception;
	
	List<Long> getAllCouponsId() throws Exception;
}
