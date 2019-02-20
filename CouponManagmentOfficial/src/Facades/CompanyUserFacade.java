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
import Exceptions.NoDetailsFoundException;
import Exceptions.ObjectNotFoundException;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.Customer;
import SystemUtils.ClientType;

/**
 * @author Tal Yamin
 *
 */

public class CompanyUserFacade implements CouponClientFacade {

	// data members of CompanyUserFacade
	private Company company;
	private ClientType clientType = ClientType.COMPANY;
	private CompanyDBDAO compCompanyDAO = new CompanyDBDAO();
	private CustomerDBDAO cusCompanyDAO = new CustomerDBDAO();
	private CouponDBDAO coupCompanyDAO = new CouponDBDAO();
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

			List<Coupon> coupons = coupCompanyDAO.getAllCoupons();

			Iterator<Coupon> i = coupons.iterator();

			while (i.hasNext()) {
				Coupon current = i.next();
				if (coupon.getTitle().equals(current.getTitle())) {
					throw new CouponExistsException("Company failed to add coupon - this coupon already exists. ", coupon.getTitle(), this.company.getCompanyId());
				}
			}
			if (!i.hasNext()) {
				coupCompanyDAO.insertCoupon(coupon);
				com_couCompany.insertCompany_Coupon(this.company, coupon);
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
			
			//check if couponId exist
			List<Coupon>coupons = coupCompanyDAO.getAllCoupons();
			Iterator<Coupon>i = coupons.iterator();
			int flag = 0;
			while(i.hasNext()) {
				Coupon current = i.next();
				if (current.getCouponId() == couponId) {
					flag = 1;
				}
			}
			if (!i.hasNext() && flag == 0) {
				throw new ObjectNotFoundException("couponId does not exist in system. ", this.company.getCompanyId(), this.clientType, couponId);
			}
			
			Coupon coupon = coupCompanyDAO.getCoupon(couponId);
			cus_couCompany.removeCustomer_Coupon(coupon);
			com_couCompany.removeCompany_Coupon(coupon);
			coupCompanyDAO.removeCoupon(coupon);

		}catch (ObjectNotFoundException e) {
			System.out.println(e.getMessage());
		}catch (Exception e) {
			throw new Exception("Compnay failed to remove coupon. couponId: " + couponId);
		}

	}

	//update coupon by couponId - only can update end date & price
	public void updateCoupon(long couponId, String newEndDate, double newPrice) throws Exception {
		try {
			
			//check if couponId exist
			List<Coupon>coupons = coupCompanyDAO.getAllCoupons();
			Iterator<Coupon>i = coupons.iterator();
			int flag = 0;
			while(i.hasNext()) {
				Coupon current = i.next();
				if (current.getCouponId() == couponId) {
					flag = 1;
				}
			}
			if (!i.hasNext() && flag == 0) {
				throw new ObjectNotFoundException("couponId does not exist in system", this.company.getCompanyId(), this.clientType, couponId);
			}
			
			Coupon coupon = coupCompanyDAO.getCoupon(couponId);
			LocalDate endLocalDate = LocalDate.parse(newEndDate, this.formatter);
			coupon.setEndDate(endLocalDate);
			coupon.setPrice(newPrice);
			
			if (coupon.getEndDate().isBefore(LocalDate.now())) {
				throw new EndDatePassedException("Company failed to update coupon - the end date already passed. ", newEndDate, coupon.getCouponId(), this.company.getCompanyId());
			}
			
			coupCompanyDAO.updateCoupon(coupon);
		}catch (ObjectNotFoundException e) {
			System.out.println(e.getMessage());
		}catch (EndDatePassedException e) {
			System.out.println(e.getMessage());
		}catch (Exception e) {
			throw new Exception("Company failed to update coupon. couponId: " + couponId);
		}
	}

	
	//get specific company
	public Company getCompany() throws Exception {

		try {
			System.out.println(compCompanyDAO.getCompany(this.company.getCompanyId()));
			return compCompanyDAO.getCompany(this.company.getCompanyId());
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
				couponsToGet.add(coupCompanyDAO.getCoupon(cId));
			}
			
			if (couponsToGet.isEmpty()) {
				throw new NoDetailsFoundException("Company " + this.company.getCompanyId() +" failed to get all coupons - no details found", this.company.getCompanyId(), this.clientType);
			}
			
			List<Coupon>couponsToView = couponsToGet;
			for(Coupon c: couponsToView) {
				System.out.println(c);
			}
			return couponsToGet;
		}catch (NoDetailsFoundException e) {
			System.out.println(e.getMessage());
		}catch (Exception e) {
			throw new Exception("Company failed to get coupons data. companyId: " + this.company.getCompanyId());
		}
		return null;

	}

	
	// get all coupons that belongs to company and by specific type
	public List<Coupon> getAllCouponsByType(String typeName) throws Exception {

		try {
			// get all coupons that belongs to company from Company_Coupon table
			List<Long> coupons = com_couCompany.getCouponId(this.company.getCompanyId());
			List<Coupon> couponsToGet = new ArrayList<>();
			for (Long cId : coupons) {
				// get all Coupons objects that belongs to company and has relevant type
				couponsToGet.addAll(coupCompanyDAO.getAllCouponsByType(cId, typeName));

			}
			
			if (couponsToGet.isEmpty()) {
				throw new NoDetailsFoundException("Company " + this.company.getCompanyId() +" failed to get all coupons by type - no details found", this.company.getCompanyId(), this.clientType);
			}
			
			List<Coupon>couponsToView = couponsToGet;
			for(Coupon c: couponsToView) {
				System.out.println(c);
			}
			return couponsToGet;
		}catch (NoDetailsFoundException e) {
			System.out.println(e.getMessage());
		}catch (Exception e) {
			throw new Exception("Company failed to get coupons data by Type. companyId: " + this.company.getCompanyId() + " couponType: " + typeName);
		}
		return null;

	}

	// get all coupons that belongs to company and with price limit
	public List<Coupon> getAllCouponsByPrice(double priceTop) throws Exception {

		try {
			// get all coupons that belongs to company from Company_Coupon table
			List<Long> coupons = com_couCompany.getCouponId(this.company.getCompanyId());
			List<Coupon> couponsToGet = new ArrayList<>();
			for (Long cId : coupons) {
				// get all Coupons objects that belongs to company and has relevant price
				couponsToGet.addAll(coupCompanyDAO.getAllCouponsByPrice(cId, priceTop));

			}
			
			if (couponsToGet.isEmpty()) {
				throw new NoDetailsFoundException("Company " + this.company.getCompanyId() +" failed to get all coupons by price - no details found", this.company.getCompanyId(), this.clientType);
			}
			
			List<Coupon>couponsToView = couponsToGet;
			for(Coupon c: couponsToView) {
				System.out.println(c);
			}
			return couponsToGet;
		}catch (NoDetailsFoundException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Company failed to get coupons data by Price. companyId: " + this.company.getCompanyId() + " priceTop: " + priceTop);
		}
		return null;
	}
	
	// get all coupons that belongs to company and with date limit
		public List<Coupon> getAllCouponsByDate(String untilDate) throws Exception {

			try {
				// get all coupons that belongs to company from Company_Coupon table
				List<Long> coupons = com_couCompany.getCouponId(this.company.getCompanyId());
				List<Coupon> couponsToGet = new ArrayList<>();
				for (Long cId : coupons) {
					// get all Coupons objects that belongs to company and has relevant date
					couponsToGet.addAll(coupCompanyDAO.getAllCouponsByDate(cId, untilDate));

				}
				
				if (couponsToGet.isEmpty()) {
					throw new NoDetailsFoundException("Company " + this.company.getCompanyId() +" failed to get all coupons by date - no details found", this.company.getCompanyId(), this.clientType);
				}
				
				List<Coupon>couponsToView = couponsToGet;
				for(Coupon c: couponsToView) {
					System.out.println(c);
				}
				return couponsToGet;
			}catch (NoDetailsFoundException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				throw new Exception("Company failed to get coupons data by Date. companyId: " + this.company.getCompanyId() + " untilDate: " + untilDate);
			}
			return null;
		}

		// override from interface - make it available to return facade for login method
		@Override
		public void login(String name, String password, ClientType clientType) throws Exception {
			
			
		}

}
