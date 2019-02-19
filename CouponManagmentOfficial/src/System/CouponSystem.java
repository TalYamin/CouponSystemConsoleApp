package System;

import java.util.Iterator;
import java.util.List;

import Client.ClientType;
import DBDAO.CompanyDBDAO;
import DBDAO.Company_CouponDBDAO;
import DBDAO.CouponDBDAO;
import DBDAO.CustomerDBDAO;
import DBDAO.Customer_CouponDBDAO;
import Exceptions.LoginCouponSystemException;
import Facades.AdminUserFacade;
import Facades.CompanyUserFacade;
import Facades.CouponClientFacade;
import Facades.CustomerUserFacade;
import JavaBeans.Company;
import JavaBeans.Customer;
import Thread.DailyCouponExpirationTask;

public class CouponSystem {

	private static CouponSystem instance = new CouponSystem();
	private CompanyDBDAO companySystemDAO = new CompanyDBDAO();
	private CustomerDBDAO customerSystemDAO = new CustomerDBDAO();
	private DailyCouponExpirationTask dailyCouponExpirationTask = new DailyCouponExpirationTask();

	private CouponSystem() {
		try {
			dailyCouponExpirationTask.start();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("DailyCouponExpirationTask failed to start");
		}
	}

	public static CouponSystem getInstance() {
		return instance;

	}

	/*
	 * Login to system method: This method receive 3 parameters: userName, password
	 * and clientType. According to clientType, the method check the validity of the
	 * parameters values. If the values valid - the method return client facade. If
	 * the values not valid - LoginCouponSystemException is activated.
	 */

	public CouponClientFacade login(String userName, String password, ClientType clientType) throws Exception {

		try {
			switch (clientType) {

			case ADMIN:

				// check the validity of the parameters values
				if (userName.equals("admin") && password.equals("1234")) {
					AdminUserFacade adminF = new AdminUserFacade();
					System.out.println("Admin logged in to system");
					return adminF;
				} else {
					throw new LoginCouponSystemException("invalid details for Admin user. ", userName, password,
							clientType);
				}

			case COMPANY:

				// take the list of companies from DB
				List<Company> companies = companySystemDAO.getAllCompanies();
				Iterator<Company> i = companies.iterator();

				// for any company in DB check the validity of the parameters values
				if (i.hasNext()) {
					Company current = i.next();
					if (current.getCompanyName().equals(userName) && current.getCompanyPassword().equals(password)) {
						CompanyUserFacade companyF = new CompanyUserFacade(current);
						System.out.println("Company " + current.getCompanyName() + " logged in to system");
						return companyF;
					} else if (!i.hasNext()) {
						throw new LoginCouponSystemException("invalid details for Company user. ", userName, password,
								clientType);
					}
				}

			case CUSTOMER:

				// take the list of customers from DB
				List<Customer> customers = customerSystemDAO.getAllCustomers();
				Iterator<Customer> it = customers.iterator();

				// for any customer in DB check the validity of the parameters values
				if (it.hasNext()) {
					Customer current = it.next();
					if (current.getCustomerName().equals(userName) && current.getCustomerPassword().equals(password)) {
						CustomerUserFacade customerF = new CustomerUserFacade(current);
						System.out.println("Customer " + current.getCustomerName() + " logged in to system");
						return customerF;
					} else if (!it.hasNext()) {
						throw new LoginCouponSystemException("invalid details for Customer user. ", userName, password,
								clientType);
					}
				}

			}
		} catch (LoginCouponSystemException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Login failed");
		}
		return null;
	}
	
	public void shutdown() {
		try {
			dailyCouponExpirationTask.stopTask();
		} catch (Exception e) {
			System.out.println("DailyCouponExpirationTask failed to stop");
		}
	}

}
