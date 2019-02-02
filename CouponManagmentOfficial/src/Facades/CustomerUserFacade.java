package Facades;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import DBDAO.Company_CouponDBDAO;
import DBDAO.CouponDBDAO;
import DBDAO.Customer_CouponDBDAO;
import JavaBeans.Coupon;
import JavaBeans.Customer;

/**
 * @author Tal Yamin
 *
 */

public class CustomerUserFacade {

	// data members of CustomerUserFacade
	private Customer customer;
	private String clientType = "Customer";
	private Customer_CouponDBDAO cus_couCustomer = new Customer_CouponDBDAO();
	private Company_CouponDBDAO com_couCustomer = new Company_CouponDBDAO();
	private CouponDBDAO couCustomer = new CouponDBDAO();

	// empty CTOR of CustomerUserFacade
	public CustomerUserFacade() {

	}

	// CTOR of CustomerUserFacade
	public CustomerUserFacade(Customer customer) {
		this.customer = customer;
	}

	
	//purchase coupon by customer - check if: customer already purchased same coupon, coupon is out of stock, coupon has expired
	public void purchaseCoupon(long couponId) throws Exception {

		try {

			List<Long> customers = cus_couCustomer.getCustomerId(couponId);

			Iterator<Long> i = customers.iterator();

			while (i.hasNext()) {
				long current = i.next();
				if (this.customer.getCustomerId() == current) {
					throw new Exception("Customer unable to purchase - already purchased same coupon");
				}
			}
			if (!i.hasNext()) {

				Coupon c = couCustomer.getCoupon(couponId);
				if (c.getAmount() <= 0) {
					throw new Exception("Customer unable to purchase - this coupon is out of stock");

				}
				if (c.getEndDate().isBefore(LocalDate.now())) {
					throw new Exception("Customer unable to purchase - this coupon has expired");
				}else {

				
				Coupon newCoupon = couCustomer.getCoupon(couponId);
				newCoupon.setAmount(newCoupon.getAmount() - 1);
				couCustomer.updateCoupon(newCoupon);
				this.customer.addCoupon(newCoupon);
				cus_couCustomer.insertCustomer_Coupon(this.customer, newCoupon);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Customer failed to purchase coupon");
		}

	}

	public List<Coupon> getAllPurchases() throws Exception {

		try {
			List<Long> coupons = cus_couCustomer.getCouponId(this.customer.getCustomerId());
			List<Coupon> couponsToGet = null;
			// run on ID of coupons in loop
			for (Long cId : coupons) {
				couponsToGet = couCustomer.getAllCoupons(cId);
			}

			return couponsToGet;
		} catch (Exception e) {
			throw new Exception("Custoemr failed to get all purchase history");
		}
	}

	public List<Coupon> getAllCouponsByType(String typeName) throws Exception {

		try {
			// get all coupons that belongs to customer from Customer_Coupon table
			List<Long> coupons = cus_couCustomer.getCouponId(this.customer.getCustomerId());
			List<Coupon> couponsToGet = null;
			for (Long cId : coupons) {
				// get all Coupons objects that belongs to company and has relevant type
				couponsToGet = couCustomer.getAllCouponsByType(cId, typeName);

			}
			return couponsToGet;
		} catch (Exception e) {
			throw new Exception("Customer failed to get coupons data by Type");
		}

	}

	public List<Coupon> getAllCouponsByPrice(double priceTop) throws Exception {

		try {
			// get all coupons that belongs to customer from Customer_Coupon table
			List<Long> coupons = cus_couCustomer.getCouponId(this.customer.getCustomerId());
			List<Coupon> couponsToGet = null;
			for (Long cId : coupons) {
				// get all Coupons objects that belongs to customer and has relevant price
				couponsToGet = couCustomer.getAllCouponsByPrice(cId, priceTop);

			}
			return couponsToGet;
		} catch (Exception e) {
			throw new Exception("Customer failed to get coupons data by Price");
		}
	}
}
