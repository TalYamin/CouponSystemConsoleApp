package Facades;

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

public class AdminUserFacade {

	// data members of AdminUserFacade
	private String userName = "admin";
	private String password = "1234";
	private String clientType = "Admin";
	private CompanyDBDAO compAdmin = new CompanyDBDAO();
	private CustomerDBDAO custAdmin = new CustomerDBDAO();
	private CouponDBDAO coupAdmin = new CouponDBDAO();
	private Company_CouponDBDAO com_couAdmin = new Company_CouponDBDAO();
	private Customer_CouponDBDAO cus_couAdmin = new Customer_CouponDBDAO();

	// empty CTOR of AdminUserFacade
	public AdminUserFacade() {

	}

	// insert company to Company table after check there is not duplicate name
	public void insertCompany(Company company) throws Exception {
		try {

			List<Company> companies = compAdmin.getAllCompanies();

			Iterator<Company> i = companies.iterator();

			while (i.hasNext()) {
				Company current = i.next();
				if (company.getCompanyName().equals(current.getCompanyName())) {
					throw new Exception("Admin failed to add company - this company already exists");

				}
			}
			if (!i.hasNext()) {
				compAdmin.insertCompany(company);
				System.out.println("Admin added new company: " + company.getCompanyId());

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Admin failed to add company");

		}

	}

	// remove company - include: remove all coupons from Coupon, Company_Coupon,
	// Customer_Coupon tables
	public void removeCompany(Company company) throws Exception {

		try {

			// get all coupons that belongs to company from Company_Coupon table
			List<Long> coupons = com_couAdmin.getCouponId(company.getCompanyId());

			// run on ID of coupons in loop
			for (Long cId : coupons) {

				// get all Coupons objects that belongs to company and remove them from Coupon
				// and Customer_Coupon and Company_Coupon table
				List<Coupon> couponsToRemove = coupAdmin.getAllCoupons(cId);
				for (Coupon c : couponsToRemove) {
					coupAdmin.removeCoupon(c);
					cus_couAdmin.removeCustomer_Coupon(c);
					com_couAdmin.removeCompany_Coupon(c);
				}

			}
			// remove company from Company table
			compAdmin.removeCompany(company);
		} catch (Exception e) {
			throw new Exception("Admin failed to remove company");
		}

	}

	// update company - can't update companyId or compayName
	public void updateCompany(Company company, String newCompanyPassword, String newCompanyEmail) throws Exception {
		try {
			company.setCompanyPassword(newCompanyPassword);
			company.setCompanyEmail(newCompanyEmail);
			compAdmin.updateCompany(company);
		} catch (Exception e) {
			throw new Exception("Admin failed to update company");
		}
	}

	// get all companies
	public List<Company> getAllCompanies() throws Exception {
		try {
			System.out.println(compAdmin.getAllCompanies());
			return compAdmin.getAllCompanies();
		} catch (Exception e) {
			throw new Exception("Admin failed to get all companies");
		}
	}

	// get specific company by companyId
	public Company getCompany(long companyId) throws Exception {
		try {
			System.out.println(compAdmin.getCompany(companyId));
			return compAdmin.getCompany(companyId);
		} catch (Exception e) {
			throw new Exception("Admin failed to get a company");
		}
	}

	// insert customer to Customer table after check there is not duplicate name
	public void insertCustomer(Customer customer) throws Exception {
		try {

			List<Customer> customers = custAdmin.getAllCustomers();

			Iterator<Customer> i = customers.iterator();
			while (i.hasNext()) {
				Customer current = i.next();
				if (customer.getCustomerName().equals(current.getCustomerName())) {
					throw new Exception("Admin failed to add customer - this customer already exists");
				}
			}

			if (!i.hasNext()) {
				custAdmin.insertCustomer(customer);
				System.out.println("Admin added new custoemr: " + customer.getCustomerId());
			}
		} catch (Exception e) {
			throw new Exception("Admin failed to add customer");
		}

	}

	// remove customer - include: remove all coupons that belong to customer from
	// Customer_Coupon table and customer from Customer table
	public void removeCustomer(Customer customer) throws Exception {

		try {
			cus_couAdmin.removeCustomer_Coupon(customer);
			custAdmin.removeCustomer(customer);

		} catch (Exception e) {
			throw new Exception("Admin failed to remove customer");
		}

	}

	// update customer - can't update customerId or customerName
	public void updateCustomer(Customer customer, String newCustomerPassword) throws Exception {
		try {
			customer.setCustomerPassword(newCustomerPassword);
			custAdmin.updateCustomer(customer);
		} catch (Exception e) {
			throw new Exception("Admin failed to update customer");
		}
	}

	// get all customers
	public List<Customer> getAllCustomers() throws Exception {
		try {
			System.out.println(custAdmin.getAllCustomers());
			return custAdmin.getAllCustomers();
		} catch (Exception e) {
			throw new Exception("Admin failed to get all customers");
		}
	}
	
	// get specific customer by customerId
	public Customer getCustomer(long customerId) throws Exception {
		try {
			System.out.println(custAdmin.getCustomer(customerId));
			return custAdmin.getCustomer(customerId);
		} catch (Exception e) {
			throw new Exception("Admin failed to get a customer");
		}
	}

}
