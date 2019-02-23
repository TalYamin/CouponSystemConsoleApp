package SystemUtils;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import DB.DataBase;
import DBDAO.CompanyDBDAO;
import DBDAO.CustomerDBDAO;
import Exceptions.DailyTaskException;
import Exceptions.LoginCouponSystemException;
import Facades.AdminUserFacade;
import Facades.CompanyUserFacade;
import Facades.CouponClientFacade;
import Facades.CustomerUserFacade;
import JavaBeans.Company;
import JavaBeans.Customer;
import SystemUtils.DailyCouponExpirationTask;

/**
 * @author Shay Ben Haroush and Tal Yamin
 *
 */

public class CouponSystem {

	private static CouponSystem instance = new CouponSystem();
	private CompanyDBDAO companySystemDAO = new CompanyDBDAO();
	private CustomerDBDAO customerSystemDAO = new CustomerDBDAO();
	private ConnectionPool connectionPool;
	private static DailyCouponExpirationTask dailyCouponExpirationTask;

	private CouponSystem() {
		System.out.println("Welcome to Coupon System");
		try {
			DataBase.BuildDB();
		} catch (Exception e) {
			System.out.println("DB already exists");
		}
		
	}

	public static CouponSystem getInstance() throws Exception {
		try {
			ConnectionPool connectionPool = ConnectionPool.getInstance();
			dailyCouponExpirationTask = new DailyCouponExpirationTask();
			dailyCouponExpirationTask.startTask();
			return instance;
		} catch (DailyTaskException e) {
			System.out.println(e.getMessage());
		}catch (Exception e) {
			throw new Exception("DailyCouponExpirationTask failed to start");
		}
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
				while (i.hasNext()) {
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
				while (it.hasNext()) {
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
			connectionPool = ConnectionPool.getInstance();
			connectionPool.closeAllConnections();
			dailyCouponExpirationTask.stopTask();
			System.out.println("Shutdown...");
		} catch (Exception e) {
			System.out.println("DailyCouponExpirationTask failed to stop");
		}
	}

}
