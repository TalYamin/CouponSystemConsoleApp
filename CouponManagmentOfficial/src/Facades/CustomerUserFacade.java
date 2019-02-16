package Facades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import Client.ClientType;
import DBDAO.Company_CouponDBDAO;
import DBDAO.CouponDBDAO;
import DBDAO.Customer_CouponDBDAO;
import Exceptions.CouponExpiredException;
import Exceptions.NoDetailsFoundException;
import Exceptions.OutOfStockException;
import Exceptions.SamePurchaseException;
import JavaBeans.Coupon;
import JavaBeans.Customer;

/**
 * @author Tal Yamin
 *
 */

public class CustomerUserFacade implements CouponClientFacade {

	// data members of CustomerUserFacade
	private Customer customer;
	private ClientType clientType = ClientType.CUSTOMER;
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
			
			//check if couponId exist
			List<Coupon>coupons = couCustomer.getAllCoupons();
			Iterator<Coupon>it = coupons.iterator();
			int flag = 0;
			while(it.hasNext()) {
				Coupon current = it.next();
				if (current.getCouponId() == couponId) {
					flag = 1;
				}
			}
			if (!it.hasNext() && flag == 0) {
				throw new NoDetailsFoundException("couponId does not exist in system", this.customer.getCustomerId(), this.clientType);
			}

			List<Long> customers = cus_couCustomer.getCustomerId(couponId);

			Iterator<Long> i = customers.iterator();

			while (i.hasNext()) {
				long current = i.next();
				if (this.customer.getCustomerId() == current) {
					throw new SamePurchaseException("Customer unable to purchase - already purchased same coupon. ", couponId, this.customer.getCustomerId());
				}
			}
			if (!i.hasNext()) {

				Coupon c = couCustomer.getCoupon(couponId);
				if (c.getAmount() <= 0) {
					throw new OutOfStockException("Customer unable to purchase - this coupon is out of stock. ", c.getAmount(), couponId, this.customer.getCustomerId());

				}
				if (c.getEndDate().isBefore(LocalDate.now())) {
					throw new CouponExpiredException("Customer unable to purchase - this coupon has expired. ", c.getEndDate().toString(), couponId, this.customer.getCustomerId());
				}else {

				
				Coupon newCoupon = couCustomer.getCoupon(couponId);
				newCoupon.setAmount(newCoupon.getAmount() - 1);
				couCustomer.updateCoupon(newCoupon);
				this.customer.addCoupon(newCoupon);
				cus_couCustomer.insertCustomer_Coupon(this.customer, newCoupon);
				System.out.println("Customer " + customer.getCustomerName() + " purchased successfully Coupon " + couponId);
				}
			}
		}catch (NoDetailsFoundException e) {
			System.out.println(e.getMessage());
		}catch (SamePurchaseException e) {
			System.out.println(e.getMessage());
		}catch (OutOfStockException e) {
			System.out.println(e.getMessage());
		}catch (CouponExpiredException e) {
			System.out.println(e.getMessage());
		}catch (Exception e) {
			throw new Exception("Customer failed to purchase coupon. couponId: " + couponId + " customerId: " + this.customer.getCustomerId());
		}

	}

	
	
	// get all coupons which customer purchased
	public List<Coupon> getAllPurchases() throws Exception {

		try {
			// get all coupons that belongs to customer from Customer_Coupon table
			List<Long> coupons = cus_couCustomer.getCouponId(this.customer.getCustomerId());
			List<Coupon> couponsToGet = new ArrayList<>();
			// run on ID of coupons in loop
			for (Long cId : coupons) {
				// get all Coupons objects that belongs to customer
				couponsToGet.add(couCustomer.getCoupon(cId));
			}
			
			if (couponsToGet.isEmpty()) {
				throw new NoDetailsFoundException("Customer " + this.customer.getCustomerId() + " failed to get all purchase history - no details found", this.customer.getCustomerId(), this.clientType);
			}
			
			List<Coupon>couponsToView = couponsToGet;
			for(Coupon c: couponsToView) {
				System.out.println(c);
			}
			return couponsToGet;
		}catch (NoDetailsFoundException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Custoemr failed to get all purchase history. customerId: " + this.customer.getCustomerId());
		}
		return null;
	}

	// get all coupons that belongs to customer and by specific type
	public List<Coupon> getAllCouponsByType(String typeName) throws Exception {

		try {
			// get all coupons that belongs to customer from Customer_Coupon table
			List<Long> coupons = cus_couCustomer.getCouponId(this.customer.getCustomerId());
			List<Coupon> couponsToGet = new ArrayList<>();
			for (Long cId : coupons) {
				// get all Coupons objects that belongs to customer and has relevant type
				couponsToGet.addAll(couCustomer.getAllCouponsByType(cId, typeName));

			}
			
			if (couponsToGet.isEmpty()) {
				throw new NoDetailsFoundException("Customer " + this.customer.getCustomerId() + " failed to get all coupons by type - no details found", this.customer.getCustomerId(), this.clientType);
			}
			List<Coupon>couponsToView = couponsToGet;
			for(Coupon c: couponsToView) {
				System.out.println(c);
			}
			return couponsToGet;
		}catch (NoDetailsFoundException e) {
			System.out.println(e.getMessage());
		}catch (Exception e) {
			throw new Exception("Customer failed to get coupons data by Type. customerId: " + this.customer.getCustomerId() + " couponType: " + typeName);
		}
		return null;

	}
	
	// get all coupons that belongs to customer and with price limit
	public List<Coupon> getAllCouponsByPrice(double priceTop) throws Exception {

		try {
			// get all coupons that belongs to customer from Customer_Coupon table
			List<Long> coupons = cus_couCustomer.getCouponId(this.customer.getCustomerId());
			List<Coupon> couponsToGet = new ArrayList<>();
			for (Long cId : coupons) {
				// get all Coupons objects that belongs to customer and has relevant price
				couponsToGet.addAll(couCustomer.getAllCouponsByPrice(cId, priceTop));

			}
			if (couponsToGet.isEmpty()) {
				throw new NoDetailsFoundException("Customer " + this.customer.getCustomerId() + " failed to get all coupons by price - no details found", this.customer.getCustomerId(), this.clientType);
			}
			List<Coupon>couponsToView = couponsToGet;
			for(Coupon c: couponsToView) {
				System.out.println(c);
			}
			return couponsToGet;
		}catch (NoDetailsFoundException e) {
			System.out.println(e.getMessage());
		}catch (Exception e) {
			throw new Exception("Customer failed to get coupons data by Price. customerId: " + this.customer.getCustomerId() + " priceTop: " + priceTop);
		}
		return null;
	}

	// override from interface - make it available to return facade for login method
	@Override
	public void login(String name, String password, ClientType clientType) throws Exception {
		
		
	}
}
