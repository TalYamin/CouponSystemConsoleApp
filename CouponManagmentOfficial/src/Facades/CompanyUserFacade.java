package Facades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import DBDAO.CompanyDBDAO;
import DBDAO.Company_CouponDBDAO;
import DBDAO.CouponDBDAO;
import DBDAO.CustomerDBDAO;
import DBDAO.Customer_CouponDBDAO;
import Exceptions.CouponExistsException;
import Exceptions.EndDatePassedException;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.Customer;

/**
 * @author Tal Yamin
 *
 */

public class CompanyUserFacade {

	// data members of CompanyUserFacade
	private Company company;
	private String clientType = "Company";
	private CompanyDBDAO compCompany = new CompanyDBDAO();
	private CustomerDBDAO cusCompany = new CustomerDBDAO();
	private CouponDBDAO coupCompany = new CouponDBDAO();
	private Company_CouponDBDAO com_couCompany = new Company_CouponDBDAO();
	private Customer_CouponDBDAO cus_couCompany = new Customer_CouponDBDAO();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
	
	// empty CTOR of CompanyUserFacade
	public CompanyUserFacade() {

	}

	//  CTOR of CompanyUserFacade
	public CompanyUserFacade(Company company) throws Exception {
	
		this.company = company;
	}

	//insert coupon - check there is no duplicate title: insert into Coupon, Company_Coupon tables
	public void insertCoupon(Coupon coupon) throws Exception {
		try {
			
			if (coupon.getEndDate().isBefore(LocalDate.now())) {
				throw new EndDatePassedException("Company failed to add coupon - the end date already passed. ", coupon.getEndDate().toString(), coupon.getCouponId(), this.company.getCompanyId());
			}

			List<Coupon> coupons = coupCompany.getAllCoupons();

			Iterator<Coupon> i = coupons.iterator();

			while (i.hasNext()) {
				Coupon current = i.next();
				if (coupon.getTitle().equals(current.getTitle())) {
					throw new CouponExistsException("Company failed to add coupon - this coupon already exists. ", coupon.getTitle(), this.company.getCompanyId());
				}
			}
			if (!i.hasNext()) {
				coupCompany.insertCoupon(coupon);
				com_couCompany.insertCompany_Coupon(this.company, coupon);
				this.company.addCoupon(coupon);
				System.out.println("Company " + this.company.getCompanyName() +  " add new coupon: " + coupon.getCouponId());
			}

		}catch (EndDatePassedException e) {
			System.out.println(e.getMessage());
		}catch (CouponExistsException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Company failed to add coupon. couponId: " + coupon.getCouponId());
		}
	}

	
	//remove coupon by couponId: remove from Coupon, Company_Coupon, Customer_Coupon tables
	public void removeCoupon(long couponId) throws Exception {

		try {
			Coupon coupon = coupCompany.getCoupon(couponId);
			cus_couCompany.removeCustomer_Coupon(coupon);
			com_couCompany.removeCompany_Coupon(coupon);
			coupCompany.removeCoupon(coupon);
			this.company.removeCoupon(coupon);
			List<Customer> customers = cusCompany.getAllCustomers();
			for (Customer c : customers) {
				c.removeCoupon(coupon);
			}

		} catch (Exception e) {
			throw new Exception("Compnay failed to remove coupon. couponId: " + couponId);
		}

	}

	//update coupon by couponId - only can update end date & price
	public void updateCoupon(long couponId, String newEndDate, double newPrice) throws Exception {
		try {
			
			Coupon coupon = coupCompany.getCoupon(couponId);
			LocalDate endLocalDate = LocalDate.parse(newEndDate, this.formatter);
			coupon.setEndDate(endLocalDate);
			coupon.setPrice(newPrice);
			
			if (coupon.getEndDate().isBefore(LocalDate.now())) {
				throw new EndDatePassedException("Company failed to update coupon - the end date already passed. ", newEndDate, coupon.getCouponId(), this.company.getCompanyId());
			}
			
			coupCompany.updateCoupon(coupon);
		} catch (EndDatePassedException e) {
			System.out.println(e.getMessage());
		}catch (Exception e) {
			throw new Exception("Company failed to update coupon. couponId: " + couponId);
		}
	}

	
	//get specific company
	public Company getCompany() throws Exception {

		try {
			System.out.println(compCompany.getCompany(this.company.getCompanyId()));
			return compCompany.getCompany(this.company.getCompanyId());
		} catch (Exception e) {
			throw new Exception("Company failed to get company details. companyId: " + this.company.getCompanyId());
		}
	}

	// get all coupons that belongs to company
	public List<Coupon> getAllCoupons() throws Exception {

		try {
			// get all coupons that belongs to company from Company_Coupon table
			List<Long> coupons = com_couCompany.getCouponId(this.company.getCompanyId());
			List<Coupon> couponsToGet = new ArrayList<>();
			// run on ID of coupons in loop
			for (Long cId : coupons) {
				// get all Coupons objects that belongs to company
				couponsToGet.add(coupCompany.getCoupon(cId));
			}
			List<Coupon>couponsToView = couponsToGet;
			for(Coupon c: couponsToView) {
				System.out.println(c);
			}
			return couponsToGet;
		} catch (Exception e) {
			throw new Exception("Company failed to get coupons data. companyId: " + this.company.getCompanyId());
		}

	}

	
	// get all coupons that belongs to company and by specific type
	public List<Coupon> getAllCouponsByType(String typeName) throws Exception {

		try {
			// get all coupons that belongs to company from Company_Coupon table
			List<Long> coupons = com_couCompany.getCouponId(this.company.getCompanyId());
			List<Coupon> couponsToGet = new ArrayList<>();
			for (Long cId : coupons) {
				// get all Coupons objects that belongs to company and has relevant type
				couponsToGet.addAll(coupCompany.getAllCouponsByType(cId, typeName));

			}
			List<Coupon>couponsToView = couponsToGet;
			for(Coupon c: couponsToView) {
				System.out.println(c);
			}
			return couponsToGet;
		} catch (Exception e) {
			throw new Exception("Company failed to get coupons data by Type. companyId: " + this.company.getCompanyId() + " couponType: " + typeName);
		}

	}

	// get all coupons that belongs to company and with price limit
	public List<Coupon> getAllCouponsByPrice(double priceTop) throws Exception {

		try {
			// get all coupons that belongs to company from Company_Coupon table
			List<Long> coupons = com_couCompany.getCouponId(this.company.getCompanyId());
			List<Coupon> couponsToGet = new ArrayList<>();
			for (Long cId : coupons) {
				// get all Coupons objects that belongs to company and has relevant price
				couponsToGet.addAll(coupCompany.getAllCouponsByPrice(cId, priceTop));

			}
			List<Coupon>couponsToView = couponsToGet;
			for(Coupon c: couponsToView) {
				System.out.println(c);
			}
			return couponsToGet;
		} catch (Exception e) {
			throw new Exception("Company failed to get coupons data by Price. companyId: " + this.company.getCompanyId() + " priceTop: " + priceTop);
		}
	}
	
	// get all coupons that belongs to company and with date limit
		public List<Coupon> getAllCouponsByDate(String untilDate) throws Exception {

			try {
				// get all coupons that belongs to company from Company_Coupon table
				List<Long> coupons = com_couCompany.getCouponId(this.company.getCompanyId());
				List<Coupon> couponsToGet = new ArrayList<>();
				for (Long cId : coupons) {
					// get all Coupons objects that belongs to company and has relevant date
					couponsToGet.addAll(coupCompany.getAllCouponsByDate(cId, untilDate));

				}
				List<Coupon>couponsToView = couponsToGet;
				for(Coupon c: couponsToView) {
					System.out.println(c);
				}
				return couponsToGet;
			} catch (Exception e) {
				throw new Exception("Company failed to get coupons data by Date. companyId: " + this.company.getCompanyId() + " untilDate: " + untilDate);
			}
		}

}
