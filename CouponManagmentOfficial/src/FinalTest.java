import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.apache.derby.tools.sysinfo;

import DB.DataBase;
import Exceptions.ConnectionException;
import Facades.AdminUserFacade;
import Facades.CompanyUserFacade;
import Facades.CouponClientFacade;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.CouponType;
import JavaBeans.Customer;
import SystemUtils.ClientType;
import SystemUtils.ConnectionPool;
import SystemUtils.CouponSystem;

public class FinalTest {

	public static void main(String[] args) {

		String name;
		String password;
		CouponClientFacade client;
		ClientType clientType = ClientType.ADMIN;
		List<Company> companies;
		List<Customer>customers;
		Company testCompany;
		Customer testCustomer;
		AdminUserFacade admin;
		
		

		Company abc = new Company();
		abc.setCompanyName("abc");
		abc.setCompanyEmail("abc@abc.abc");
		abc.setCompanyPassword("abcabc");

		// System.out.println("*************************************");
		// System.out.println("Coupon creation test:");
		Coupon coupon = new Coupon();
		coupon.setCouponId(919);
		coupon.setTitle("Ski weekend");
		coupon.setStartDate(LocalDate.now());
		// there is no Exception which thrown cause the limitation on the facades
		coupon.setEndDate(LocalDate.of(2019, 12, 31));
		coupon.setAmount(10);
		coupon.setType(CouponType.SPORTS);
		coupon.setCouponMessage("five stars hotel and free entrance to ski park");
		coupon.setPrice(1000);
		coupon.setImage(
				"https://www.snowmagazine.com/media/reviews/photos/original/59/e6/5f/Alpe-dhuez-istock-1-40-1450092356.jpg");
		// System.out.println(coupon);

		// ********************* Start Coupon System *********************//
		System.out.println("*************************************");
		System.out.println("//** Start Coupon System **//");
		System.out.println("Start system test: ");

		CouponSystem couponSystem = null;

		try {
			couponSystem = CouponSystem.getInstance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			ConnectionPool.getInstance().availableConnections();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		
		
		// *****************************Admin Test**************************//
		System.out.println("*************************************");
		System.out.println("// **Admin Test**//");

		// (1) bad login//
		System.out.println("*************************************");
		System.out.println("(1) Checking bad login as admin: ");

		name = "admin";
		password = "12348";

		try {
			client = couponSystem.login(name, password, clientType);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		name = "admin";
		password = "1234";

		// (2) get admin facade
		System.out.println("*************************************");
		System.out.println("(2) Checking valid login as admin: ");

		try {
			client = couponSystem.login(name, password, clientType);
			admin = (AdminUserFacade) client;

			// (3) add company
			System.out.println("*************************************");
			System.out.println("(3) add companies test:");
			admin.insertCompany(new Company(9876, "Dell", "98Dell76", "israel_office@dell.com"));
			admin.insertCompany(new Company(4321, "Elbit", "Elbit4321", "israel@elbit.com"));
			admin.insertCompany(new Company(5791, "Discount", "Discount5791", "israel@discount.com"));	
			
			// (4) print all companies
			System.out.println("*************************************");
			System.out.println("(4) Print all companies test: ");
			System.out.println("List of companies: ");
			companies = admin.getAllCompanies();

			// (5) print company
			System.out.println("*************************************");
			System.out.println("(5) Print Company test: ");
			testCompany = admin.getCompany(9876);

			// (6) remove company
			System.out.println("*************************************");
			System.out.println("(6) Remove Company test: ");
			admin.removeCompany(4321); 
			System.out.println("List of companies after removeing company: ");
			companies = admin.getAllCompanies();

			// (7) update company
			System.out.println("*************************************");
			System.out.println("(7) Update Company test: ");
			admin.updateCompany(9876, "Dell9876", "israel@dell.com");
			System.out.println("Company after update:");
			admin.getCompany(9876);

			// (8) add customer
			System.out.println("*************************************");
			System.out.println("(8) Add Customer test:");
			admin.insertCustomer(new Customer(313, "Tal Yamin", "Tal313"));
			admin.insertCustomer(new Customer(203, "Shay Ben Haroush", "Shay203"));
			admin.insertCustomer(new Customer(963, "Kobi Shasha", "Kobi963"));
			admin.insertCustomer(new Customer(741, "Mosh Ben Ari", "Mosh741"));
			
			// (9) print all customers
			System.out.println("*************************************");
			System.out.println("(9) Print all customers test: ");
			System.out.println("List of customers: ");
			customers = admin.getAllCustomers();

			// (10) print customer
			System.out.println("*************************************");
			System.out.println("(9) Print a customer test:");
			testCustomer = admin.getCustomer(313);

			
			// (11) remove customer
			System.out.println("*************************************");
			System.out.println("(11) Remove Customer test: ");
			admin.removeCustomer(741);
			System.out.println("List of customers after removeing customer: ");
			admin.getAllCustomers();
			
			// (12) update customer
			System.out.println("*************************************");
			System.out.println("(12) Update Customer test: ");
			admin.updateCustomer(313, "313Tal313");
			System.out.println("Customer after update:");
			admin.getCustomer(313);
			
			
			// (13) try to add company with name which already exists
			System.out.println("*************************************");
			System.out.println("(13) try to add company with name which already exists: ");
			admin.insertCompany(new Company(1111, "Dell", "Dell1111", "Dell@dell.com"));
			
			// (14) try to add customer with name which already exists
			System.out.println("*************************************");
			System.out.println("(14) try to add customer with name which already exists");
			admin.insertCustomer(new Customer(912, "Shay Ben Haroush", "Shay912"));
			
		
			
			

			// *****************************Company Test**************************//
			System.out.println("*************************************");
			System.out.println("// **Company Test**//");
			
			
			// (1) bad login//
			System.out.println("*************************************");
			System.out.println("(1) Checking bad login as Company: ");
			
			name = "Dell";
			password = "Dell2019";
			clientType = ClientType.COMPANY;
			
			try {
			CompanyUserFacade companyUserFacade = (CompanyUserFacade) couponSystem.login("Discount", "Discount5794", ClientType.COMPANY);
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			// **Shutdown Test**//
			System.out.println("*************************************");
			System.out.println("// **Shutdown Test**//");
			couponSystem.shutdown();
			// try {
			// couponSystem.getInstance();
			// }catch (Exception e) {
			// e.printStackTrace();
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
