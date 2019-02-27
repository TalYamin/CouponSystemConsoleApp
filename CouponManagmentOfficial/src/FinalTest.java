import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.apache.derby.tools.sysinfo;

import DB.DataBase;
import Exceptions.ConnectionException;
import Facades.AdminUserFacade;
import Facades.CompanyUserFacade;
import Facades.CouponClientFacade;
import Facades.CustomerUserFacade;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.CouponType;
import JavaBeans.Customer;
import SystemUtils.ClientType;
import SystemUtils.ConnectionPool;
import SystemUtils.CouponSystem;

public class FinalTest {

	public static void main(String[] args) {


		CouponClientFacade client;
		List<Company> companies;
		List<Customer> customers;
		Company testCompany;
		Customer testCustomer;
		AdminUserFacade admin;
		CompanyUserFacade companyUser;
		CompanyUserFacade companyUser2;
		CompanyUserFacade companyUser3;
		CustomerUserFacade customerUser;
		CustomerUserFacade customerUser2;
		
		

		// ********************* Start Coupon System *********************//
		System.out.println("//** Start Coupon System **//");
		System.out.println("*************************************");
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
		System.out.println();System.out.println();
		System.out.println("// **Admin Test**//");

		// (1) bad login as admin//
		System.out.println("*************************************");
		System.out.println("(1) Checking bad login as admin: ");

		try {
			admin = (AdminUserFacade) couponSystem.login("admin", "12348", ClientType.ADMIN);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// (2) get admin facade
		System.out.println("*************************************");
		System.out.println("(2) Checking valid login as admin: ");

		try {
			admin = (AdminUserFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);

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
		System.out.println();System.out.println();
		System.out.println("// **Company Test**//");

		// (1) bad login as Company//
		System.out.println("*************************************");
		System.out.println("(1) Checking bad login as Company: ");

		try {
			companyUser = (CompanyUserFacade) couponSystem.login("Dell", "Dell2019", ClientType.COMPANY);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// (2) get Company facade	
		System.out.println("*************************************");
		System.out.println("(2) Checking valid login as company: ");

		try {
			companyUser = (CompanyUserFacade)couponSystem.login("Dell", "Dell9876", ClientType.COMPANY);
			
			// (3) print company
			System.out.println("*************************************");
			System.out.println("(3) Print Company test:");
			companyUser.getCompany();
			
			// (4) add coupon
			System.out.println("*************************************");
			System.out.println("(4) Add coupon test:");
			companyUser.insertCoupon(new Coupon(11, "Ski", "31/12/2019", 1, CouponType.SPORTS, "Ski vacation, 5 stars hotel", 500.5, "https://www.snowmagazine.com/media/reviews/photos/original/59/e6/5f/Alpe-dhuez-istock-1-40-1450092356.jpg"));
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
			
			//(11) try to add coupon with same title
			System.out.println("*************************************");
			System.out.println("(11) try to add coupon with same title: ");
			companyUser.insertCoupon(new Coupon(15, "BBB", "31/12/2019", 5, CouponType.RESTURANTS, "breakfast for couple", 50.5, "https://media-cdn.tripadvisor.com/media/photo-s/07/14/8a/20/enjoy.jpg"));
			
			//(12) try to add coupon with date that already passed
			System.out.println("*************************************");
			System.out.println("(12) Try to add coupon with date that already passed: ");
			companyUser.insertCoupon(new Coupon(16, "Cafe Cafe", "1/1/2012", 5, CouponType.RESTURANTS, "breakfast for couple", 50.5, "https://media-cdn.tripadvisor.com/media/photo-s/07/14/8a/20/enjoy.jpg"));
			
			// (13) try to remove coupon of another company
			System.out.println("*************************************");
			System.out.println("(13) Try to remove coupon of another company: ");
			System.out.println("Login of another company and add coupon:");
			companyUser2 = (CompanyUserFacade) couponSystem.login("Discount", "Discount5791", ClientType.COMPANY);
			companyUser2.insertCoupon(new Coupon(17, "Gilboa Trip", "31/12/2019", 15, CouponType.TRAVELING, "traveling in Gilboa Mountain", 1000, "https://upload.wikimedia.org/wikipedia/he/c/c9/%D7%92%D7%9C%D7%91%D7%95%D7%A2.JPG"));
			System.out.println("Comapny try to remove coupon of another company: ");
			companyUser.removeCoupon(17);
			
			// (14) try to update coupon with date that already passed
			System.out.println("*************************************");
			System.out.println("(14) Try to update coupon with date that already passed: ");
			companyUser.updateCoupon(11, "1/1/2014", 1000);
			
			// (15) try to update coupon of another company
			System.out.println("*************************************");
			System.out.println("(15) Try to update coupon of another company: ");
			companyUser.updateCoupon(17, "31/12/19", 27.3);
			
			// (16) try to get coupon of another company
			System.out.println("*************************************");
			System.out.println("(16) Try to get coupon of another company: ");
			companyUser.getCoupon(17);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		// *****************************Customer Test**************************//
		System.out.println("*************************************");
		System.out.println();System.out.println();
		System.out.println("// **Customer Test**//");
				
		
		// (1) bad login as Customer//	
		 System.out.println("*************************************");
		 System.out.println("(1) Checking bad login as Company: ");
				
		try {
			customerUser = (CustomerUserFacade)couponSystem.login("Kobi Shasha", "KobiKobi", ClientType.CUSTOMER);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
				
		// (2) get Customer facade			
		System.out.println("*************************************");
		System.out.println("(2) Checking valid login as customer: ");
		
		try {
			customerUser = (CustomerUserFacade)couponSystem.login("Kobi Shasha", "Kobi963", ClientType.CUSTOMER);
			
			// (3) print customer
			System.out.println("*************************************");
			System.out.println("(3) Print Customer test:");
			customerUser.getCustomer();
				
			
			// (4) Purchase Coupon
			System.out.println("*************************************");
			System.out.println("(4) Purchase Coupon test:");
			customerUser.purchaseCoupon(11);
			customerUser.purchaseCoupon(12);
			customerUser.purchaseCoupon(14);
			customerUser.purchaseCoupon(17);
			
			//(5) try to purchase coupon which already purchased
			System.out.println("*************************************");
			System.out.println("(5) Try to purchase coupon which already purchased:");
			customerUser.purchaseCoupon(11);
			
			//(6) try to purchase coupon which out of stock
			System.out.println("*************************************");
			System.out.println("first need to login with another customer:");
			customerUser2 = (CustomerUserFacade)couponSystem.login("Shay Ben Haroush", "Shay203", ClientType.CUSTOMER);
			System.out.println("(6) try to purchase coupon which out of stock: ");
			customerUser2.purchaseCoupon(11);
			
			//(7) try to purchase coupon which already expired
			System.out.println("*************************************");
			System.out.println("(7) try to purchase coupon which already expired: ");
			customerUser2.purchaseCoupon(16);
			
			//(8) print all coupons that belong to customer
			System.out.println("*************************************");
			System.out.println("(8) Print all coupons that belong to customer test: ");
			customerUser.getAllPurchases();
			
			//(9) print all coupons that belong to customer by type
			System.out.println("*************************************");
			System.out.println("(9) Print all coupons that belong to customer by type RESTURANTS: ");
			customerUser.getAllCouponsByType("Resturants");
			
			//(10) print all coupons that belong to customer by price limit
			System.out.println("*************************************");	
			System.out.println("(10) Print all coupons that belong to customer by price limit 100: ");
			customerUser.getAllCouponsByPrice(100);
			
			
			
			//** Daily Coupon Expiration Task Test ** // 
			System.out.println("*************************************");
			System.out.println();System.out.println();
			System.out.println("//** Daily Coupon Expiration Task Test ** //");
			System.out.println("*************************************");
			admin = (AdminUserFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
			admin.insertCompany(new Company(777, "Cellcom", "cellcom777", "israel@cellcom.com"));
			companyUser3 = (CompanyUserFacade) couponSystem.login("Cellcom", "cellcom777", ClientType.COMPANY);
			companyUser3.insertCoupon(new Coupon(21, "Falafel Moosa", "1/1/2019", 5, CouponType.RESTURANTS, "falafel for couple", 50, "http://www.10bis.co.il/ImageViewer.aspx?imgName=4195&imgDir=ResProfile"));
			companyUser3.insertCoupon(new Coupon(22, "Sushi", "1/2/2019", 10, CouponType.RESTURANTS, "Sushi meal", 25, "http://images.saloona.co.il/tadmit11/files/2017/07/76bkg4n0im664qrktf82.jpg"));
			companyUser3.insertCoupon(new Coupon(23, "Metzada", "1/1/2019", 2, CouponType.TRAVELING, "Metzada Trip", 500, "https://new.goisrael.com/he/sites/default/files/styles/1397x735_article_full/public/Massada%201397X735_2.jpg?itok=B_e3i_G1"));
			
			
			// **Shutdown Test**//
			System.out.println("*************************************");
			System.out.println();System.out.println();
			System.out.println("// **Shutdown Test**//");
			System.out.println("*************************************");
			couponSystem.shutdown();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
			
		// **Restart System Test**//
		System.out.println("*************************************");
		System.out.println();System.out.println();
		System.out.println("// **Restart and Shutdown System Test** //");
		System.out.println("*************************************");
		try {
			 CouponSystem couponSystem2 = null;	
			 couponSystem2 = CouponSystem.getInstance();
			 couponSystem2.shutdown();
		//	 DataBase.DropDB();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
