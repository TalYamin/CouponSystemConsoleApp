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

	// full CTOR of CompanyUserFacade : check for duplicate and if not exist -
	// insert into Company table
	public CompanyUserFacade(Company company) throws Exception {
	
		this.company = company;
	}

	//insert coupon - check there is no duplicate title: insert into Coupon, Company_Coupon tables
	public void insertCoupon(Coupon coupon) throws Exception {
		try {

			List<Coupon> coupons = coupCompany.getAllCoupons();

			Iterator<Coupon> i = coupons.iterator();

			while (i.hasNext()) {
				Coupon current = i.next();
				if (coupon.getTitle().equals(current.getTitle())) {
					throw new Exception("Company failed to add coupon - this coupon already exists");
				}
			}
			if (!i.hasNext()) {
				coupCompany.insertCoupon(coupon);
				com_couCompany.insertCompany_Coupon(this.company, coupon);
				this.company.addCoupon(coupon);
				System.out.println("Company add new coupon");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Company failed to add coupon");
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
			throw new Exception("Compnay failed to remove coupon");
		}

	}

	//update coupon by couponId - only can update end date & price
	public void updateCoupon(long couponId, String newEndDate, double newPrice) throws Exception {
		try {
			Coupon coupon = coupCompany.getCoupon(couponId);
			LocalDate endLocalDate = LocalDate.parse(newEndDate, this.formatter);
			coupon.setEndDate(endLocalDate);
			coupon.setPrice(newPrice);
			coupCompany.updateCoupon(coupon);
		} catch (Exception e) {
			throw new Exception("Company failed to update coupon");
		}
	}

	
	//get specific company
	public Company getCompany() throws Exception {

		try {
			System.out.println(compCompany.getCompany(this.company.getCompanyId()));
			return compCompany.getCompany(this.company.getCompanyId());
		} catch (Exception e) {
			throw new Exception("Company failed to get company details");
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
			System.out.println(couponsToGet);
			return couponsToGet;
		} catch (Exception e) {
			throw new Exception("Company failed to get coupons data");
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
			System.out.println(couponsToGet);
			return couponsToGet;
		} catch (Exception e) {
			throw new Exception("Company failed to get coupons data by Type");
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
			System.out.println(couponsToGet);
			return couponsToGet;
		} catch (Exception e) {
			throw new Exception("Company failed to get coupons data by Price");
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
				System.out.println(couponsToGet);
				return couponsToGet;
			} catch (Exception e) {
				throw new Exception("Company failed to get coupons data by Date");
			}
		}

}
