package Client;

import java.util.Iterator;
import java.util.List;

import javax.security.auth.login.LoginException;

import DBDAO.CompanyDBDAO;
import DBDAO.CustomerDBDAO;
import Facades.AdminUserFacade;
import Facades.CompanyUserFacade;
import Facades.CouponClientFacade;
import Facades.CustomerUserFacade;
import JavaBeans.Company;
import JavaBeans.Customer;
import Exceptions.LoginCouponSystemException;



/**
 * @author Tal Yamin
 *
 */

public class Client {

	//access to DBDAO objects
	
	private CompanyDBDAO comClientDAO = new CompanyDBDAO();
	private CustomerDBDAO cusClientDAO = new CustomerDBDAO();

	
	//login method which check client type, and according to client check the validity of the input values 
	
	public CouponClientFacade login(String userName, String password, ClientType clientType) throws Exception {

		try {

			switch (clientType) {

			case ADMIN:

				if (userName.equals("admin") && password.equals("1234")) {
					AdminUserFacade adminF = new AdminUserFacade();
					System.out.println("Admin logged in to system");
					return adminF;
				}else {
					throw new LoginCouponSystemException("invalid details for Admin user. ", userName, password, clientType);
				}
			
			case COMPANY:
				
				//take the list of companies from DB
				List<Company>companies = comClientDAO.getAllCompanies();
				Iterator<Company>i = companies.iterator();
				
				//for any company in DB check if details valid
				if (i.hasNext()) {
					Company current = i.next();
					if (current.getCompanyName().equals(userName) && current.getCompanyPassword().equals(password)) {
						CompanyUserFacade companyF = new CompanyUserFacade(current);
						System.out.println("Company " + current.getCompanyName() + " logged in to system");
						return companyF;
					}else if (!i.hasNext()) {
						throw new LoginCouponSystemException("invalid details for Company user. ", userName, password, clientType);
					}
				}
				
			case CUSTOMER:
				
				//take the list of customers from DB
				List<Customer>customers = cusClientDAO.getAllCustomers();
				Iterator<Customer>it = customers.iterator();
				
				//for any customer in DB check if details valid
				if (it.hasNext()) {
					Customer current = it.next();
					if (current.getCustomerName().equals(userName) && current.getCustomerPassword().equals(password)) {
						CustomerUserFacade customerF = new CustomerUserFacade(current);
						System.out.println("Customer " + current.getCustomerName() + " logged in to system");
						return customerF;
					}else if (!it.hasNext()){
						throw new LoginCouponSystemException("invalid details for Customer user. ", userName, password, clientType);
					}
				}

			}	
		} catch (LoginCouponSystemException e) {
			System.out.println(e.getMessage());
		}catch (Exception e) {
			throw new Exception("Login failed");
		}
		return null;

	}
}
