package Client;

import java.util.Iterator;
import java.util.List;

import DBDAO.CompanyDBDAO;
import DBDAO.CustomerDBDAO;
import Facades.AdminUserFacade;
import Facades.CompanyUserFacade;
import Facades.CouponClientFacade;
import Facades.CustomerUserFacade;
import JavaBeans.Company;
import JavaBeans.Customer;

public class Client {

	private CompanyDBDAO comClientDAO = new CompanyDBDAO();
	private CustomerDBDAO cusClientDAO = new CustomerDBDAO();

	public CouponClientFacade login(String userName, String password, ClientType clientType) throws Exception {

		try {

			switch (clientType) {

			case ADMIN:

				if (userName.equals("admin") && password.equals("1234")) {
					AdminUserFacade adminF = new AdminUserFacade();
					System.out.println("Admin loginned to system");
					return adminF;
				}else {
					throw new Exception("invalid details for Admin user");
				}
			
			case COMPANY:
				
				List<Company>companies = comClientDAO.getAllCompanies();
				Iterator<Company>i = companies.iterator();
				
				if (i.hasNext()) {
					Company current = i.next();
					if (current.getCompanyName().equals(userName) && current.getCompanyPassword().equals(password)) {
						CompanyUserFacade companyF = new CompanyUserFacade(current);
						System.out.println("Company " + current.getCompanyName() + " loginned to system");
						return companyF;
					}else if (!i.hasNext()) {
						throw new Exception("invalid details for Company user");
					}
				}
				
			case CUSTOMER:
				
				List<Customer>customers = cusClientDAO.getAllCustomers();
				Iterator<Customer>it = customers.iterator();
				
				if (it.hasNext()) {
					Customer current = it.next();
					if (current.getCustomerName().equals(userName) && current.getCustomerPassword().equals(password)) {
						CustomerUserFacade customerF = new CustomerUserFacade(current);
						System.out.println("Customer " + current.getCustomerName() + " loginned to system");
						return customerF;
					}else if (!it.hasNext()){
						throw new Exception("invalid details for Customer user");
					}
				}

			}	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;

	}
}
