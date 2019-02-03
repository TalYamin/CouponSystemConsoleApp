package DAO;

import java.util.List;

import JavaBeans.Coupon;

/**
 * @author Tal Yamin
 *
 */

public interface CouponDAO {
	
	//interface - queries from Coupon table

	void insertCoupon(Coupon coupon) throws Exception;

	void removeCoupon(Coupon coupon) throws Exception;

	void updateCoupon(Coupon coupon) throws Exception;

	Coupon getCoupon(long couponId) throws Exception;

	List<Coupon> getAllCoupons() throws Exception;
	
	List<Coupon> getAllCoupons(long couponId) throws Exception;

	List<Coupon> getAllCouponsByType(long couponId, String typeName) throws Exception;
	
	List<Coupon> getAllCouponsByPrice(long couponId, double priceTop) throws Exception;
	
	List<Coupon> getAllCouponsByDate(long couponId, String untilDate) throws Exception;
}