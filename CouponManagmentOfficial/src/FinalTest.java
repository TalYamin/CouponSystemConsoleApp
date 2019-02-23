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
		List<Customer> customers;
		Company testCompany;
		Customer testCustomer;
		AdminUserFacade admin;
		CompanyUserFacade companyUser;

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

		// (2) get admin facade
		name = "admin";
		password = "1234";
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

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

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
			client = couponSystem.login(name, password, clientType);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// (2) get Company facade
		name = "Dell";
		password = "Dell9876";
		System.out.println("*************************************");
		System.out.println("(2) Checking valid login as company: ");

		try {
			client = couponSystem.login(name, password, clientType);
			companyUser = (CompanyUserFacade) client;
			
			// (3) print company
			System.out.println("*************************************");
			System.out.println("(3) Print Company test:");
			companyUser.getCompany();
			
			// (4) add coupon
			System.out.println("*************************************");
			System.out.println("(4) Add coupon test:");
			companyUser.insertCoupon(new Coupon(11, "Ski", "31/12/2019", 10, CouponType.SPORTS, "Ski vacation, 5 stars hotel", 500.5, "https://www.snowmagazine.com/media/reviews/photos/original/59/e6/5f/Alpe-dhuez-istock-1-40-1450092356.jpg"));
			companyUser.insertCoupon(new Coupon(12, "BBB", "31/5/2019", 5, CouponType.RESTURANTS, "dinner for couple", 70.3, "https://images1.calcalist.co.il/PicServer2/20122005/522772/YE1218528_l.jpg"));
			companyUser.insertCoupon(new Coupon(13, "Spa", "31/7/2019", 15, CouponType.HEALTH, "free entrance for spa facilities", 249.9, "https://img.grouponcdn.com/iam/NG2PuHCH332Ax1sL19w4GDDyvyE/NG-1500x900/v1/c700x420.jpg"));
			companyUser.insertCoupon(new Coupon(14, "Goons Pizza", "31/12/2019", 5, CouponType.RESTURANTS, "family pizza", 60.7, "https://lh3.googleusercontent.com/p/AF1QipMHRbrkD5FglAreY6ZtKzZYBeee-t4OsmupQNKn=s1600-w1280-h1280"));
			
			// (5) print all coupons
			System.out.println("*************************************");
			System.out.println("(5) Print all coupons test: ");
			System.out.println("List of coupons: ");
			companyUser.getAllCoupons();
			
			// (6) print all coupons by type
			System.out.println("*************************************");
			System.out.println("(6) Print all coupons by type test: ");
			System.out.println("List of coupons by type RESTURANTS: ");
			companyUser.getAllCouponsByType("Resturants");
			
			// (7) print all coupons by price limit
			System.out.println("*************************************");
			System.out.println("(7) Print all coupons by price limit test: ");
			System.out.println("List of coupons by price limit 100: ");
			companyUser.getAllCouponsByPrice(100);
			
			// (8) print all coupons until specific date
			System.out.println("*************************************");
			System.out.println("(8) Print all coupons until specific date test: ");
			System.out.println("List of coupons until specific date 1/8/2019: ");
			companyUser.getAllCouponsByDate("1/8/2019");
			
			// (9) remove coupon
			System.out.println("*************************************");
			System.out.println("(9) Remove coupon test: ");
			companyUser.removeCoupon(13);
			System.out.println("List of coupons after removing coupon:");
			companyUser.getAllCoupons();
			
			// (10) update coupon
			System.out.println("*************************************");
			System.out.println("(10) Update coupon test: ");
			companyUser.updateCoupon(12, "31/12/2019", 60.2);
			System.out.println("Coupon after updating:");
			companyUser.getCoupon(12);
			
			name = "admin";
			password = "1234";
			clientType = ClientType.ADMIN;
			System.out.println("*************************************");
			System.out.println("(2) Checking valid login as admin: ");

			
		    client = couponSystem.login(name, password, clientType);
		    admin = (AdminUserFacade) client;
			admin.removeCompany(9876);
			
			// (11) try to remove coupon of another company
			
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
			System.out.println(e.getMessage());
		}
	}

}
