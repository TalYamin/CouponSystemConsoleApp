import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.xml.crypto.Data;



import DB.DataBase;
import DBDAO.CompanyDBDAO;
import DBDAO.Company_CouponDBDAO;
import DBDAO.CouponDBDAO;
import DBDAO.CustomerDBDAO;
import DBDAO.Customer_CouponDBDAO;
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
import SystemUtils.DailyCouponExpirationTask;

public class Test {

	public static void main(String[] args) throws Exception {

		try {

			Class.forName(DataBase.getDriverConnextion());
			
//			DataBase.DropDB();
//			DataBase.BuildDB();
			
			
			
//			DataBase.dropCompanyTable();
//			DataBase.dropCustomerTable();
//			DataBase.dropCouponTable();
//			DataBase.dropCompany_CouponTable();
//			DataBase.dropCustomer_CouponTable();
			
//			DataBase.createCompanyTable();
//			DataBase.createCustomerTable();
//			DataBase.createCouponTable();
//			DataBase.createCompany_CouponTable();
//			DataBase.createCustomer_CouponTable();
			
//			Coupon coupon = new Coupon(123, "BBB", "31/12/2019", 10, CouponType.RESTURANTS, "BBB for couple", 70.4, "bbb image");
//			Coupon coupon2 = new Coupon(456, "Gilboa", "1/6/2019", 5, CouponType.TRAVELING, "trip to giboa", 500, "giboa image");
//			Coupon coupon3 = new Coupon(789, "ski", "1/3/2019", 4, CouponType.SPORTS, "ski weekend", 1500, "ski image");
//			Coupon coupon4 = new Coupon(717, "Domino Pizza", "31/12/2019", 20, CouponType.RESTURANTS, "family pizza", 50, "domino image");
//			Coupon coupon5 = new Coupon(111, "spa", "1/1/2019", 15, CouponType.HEALTH, "spa for couple", 100.5, "spa image");
//			
//			Company company = new Company(1793, "Dell", "dell1793", "israel@dell.com");
//			Company company2 = new Company(4562, "Cellcom", "cellcom4562", "israel@cellcom.com");
//			Company company3 = new Company(7893, "Elbit", "Elbit7893", "israel@elbit.com");
//			Company company4 = new Company(1212, "Tzhal", "tzhal1212", "israel@tzhal.com");
//			
//			Customer customer = new Customer(3133, "Tal Yamin", "tal3133");
//			Customer customer2 = new Customer(2046, "Ofek Mesika", "Ofek2046");
//			Customer customer3 = new Customer(7894, "Peleg Yamin", "hello");
//			Customer customer4 = new Customer(7854, "Yoni Haviv", "yoni7854");
//			Customer customer5 = new Customer(3333, "Eti Levi", "eti3333");

			
//			AdminUserFacade adminUserFacade = new AdminUserFacade();
//			
//			CompanyUserFacade companyUserFacade = new CompanyUserFacade(company);
//			CompanyUserFacade companyUserFacade2 = new CompanyUserFacade(company2);
//			CompanyUserFacade companyUserFacade3 = new CompanyUserFacade(company3);
//			CompanyUserFacade companyUserFacade4 = new CompanyUserFacade(company4);
//			
//			CustomerUserFacade customerUserFacade = new CustomerUserFacade(customer);
//			CustomerUserFacade customerUserFacade2 = new CustomerUserFacade(customer2);
//			CustomerUserFacade customerUserFacade3 = new CustomerUserFacade(customer3);
//			CustomerUserFacade customerUserFacade4 = new CustomerUserFacade(customer4);
//			CustomerUserFacade customerUserFacade5 = new CustomerUserFacade(customer5);
			

//			Company_CouponDBDAO company_CouponDBDAO = new Company_CouponDBDAO();
//			Customer_CouponDBDAO customer_CouponDBDAO = new Customer_CouponDBDAO();
//			CouponDBDAO couponDBDAO = new CouponDBDAO();
			
			
//			company.addCoupon(coupon);
//			company.addCoupon(coupon2);
//			company_CouponDBDAO.insertCompany_Coupon(company, coupon);
//			company_CouponDBDAO.insertCompany_Coupon(company, coupon2);
//			couponDBDAO.insertCoupon(coupon);
//			couponDBDAO.insertCoupon(coupon2);
//			customer_CouponDBDAO.insertCustomer_Coupon(customer, coupon);
//			customer_CouponDBDAO.insertCustomer_Coupon(customer, coupon2);
//			customer.addCoupon(coupon);
//			customer.addCoupon(coupon2);
			
//			CompanyUserFacade companyUserFacade = new CompanyUserFacade(company);
//			companyUserFacade.insertCoupon(coupon);
//			companyUserFacade.insertCoupon(coupon2);
//			companyUserFacade.removeCoupon(123);
			
//			companyUserFacade.updateCoupon(456, "31/12/2019", 450);
//			System.out.println(companyUserFacade.getCompany());

//			companyUserFacade.insertCoupon(coupon);
//			companyUserFacade.insertCoupon(coupon2);
//			companyUserFacade.insertCoupon(coupon3);
//			companyUserFacade.insertCoupon(coupon4);
//			System.out.println(companyUserFacade.getAllCoupons());
			
//			System.out.println(companyUserFacade.getAllCouponsByType("Resturants"));
			
//			companyUserFacade.getAllCouponsByPrice(100);
			
//			companyUserFacade.getAllCouponsByDate("1/1/2020");
			
//			CompanyUserFacade companyUserFacade = new CompanyUserFacade(company);
//			companyUserFacade.insertCoupon(coupon5);
			
//			CustomerUserFacade customerUserFacade = new CustomerUserFacade(customer);
//			
//			customerUserFacade.purchaseCoupon(111);
			
//			customerUserFacade.purchaseCoupon(123);
//			customerUserFacade.purchaseCoupon(789);
//			customerUserFacade.purchaseCoupon(717);
//			customerUserFacade.purchaseCoupon(111);

//			customerUserFacade.getAllPurchases();
			
//			customerUserFacade.getAllCouponsByType("Sports");
			
//			customerUserFacade.getAllCouponsByPrice(550);
			
//			adminUserFacade.insertCompany(company2);
			
//			companyUserFacade.insertCoupon(coupon5);
			
//			customerUserFacade.purchaseCoupon(111);
			
//			adminUserFacade.insertCustomer(customer2);
			
//			companyUserFacade.insertCoupon(coupon);
			
//			customerUserFacade.purchaseCoupon(456);
			
//			adminUserFacade.insertCompany(company);
//			System.out.println("continue");
				
//			adminUserFacade.insertCompany(company4);
			
//			companyUserFacade.updateCoupon(717, "1/1/2019", 50);
			
//			************************facades tests**************************************************
			
//			adminUserFacade.insertCompany(company);
//			adminUserFacade.insertCompany(company2);
//			adminUserFacade.insertCompany(company3);
//			adminUserFacade.insertCompany(company4);
			
//			adminUserFacade.insertCustomer(customer);
//			adminUserFacade.insertCustomer(customer2);
//			adminUserFacade.insertCustomer(customer3);
//			adminUserFacade.insertCustomer(customer4);
//			adminUserFacade.insertCustomer(customer5);
			
//			adminUserFacade.insertCompany(company);
			
//			companyUserFacade.insertCoupon(coupon);
//			companyUserFacade.insertCoupon(coupon2);
			
//			companyUserFacade2.insertCoupon(coupon3);
			
//			companyUserFacade3.insertCoupon(coupon4);
			
//			companyUserFacade4.insertCoupon(coupon);
			
//			customerUserFacade.purchaseCoupon(123);
//			customerUserFacade2.purchaseCoupon(456);
//			customerUserFacade3.purchaseCoupon(789);
//			customerUserFacade4.purchaseCoupon(717);
			
//			customerUserFacade.purchaseCoupon(789);
			
//			customerUserFacade.purchaseCoupon(456);
			
//			customerUserFacade.purchaseCoupon(123);
			
//			customerUserFacade2.purchaseCoupon(789);
			
//			customerUserFacade4.purchaseCoupon(789);
			
//			customerUserFacade5.purchaseCoupon(789);
			
//			adminUserFacade.removeCompany(company);
			
//			adminUserFacade.updateCompany(company4, "12tzhal12", "tzhal_info@tzhal.com");
			
//			adminUserFacade.getAllCompanies();
			
//			adminUserFacade.getCompany(7893);
			
//			adminUserFacade.insertCustomer(customer);
			
//			adminUserFacade.removeCustomer(customer3);
			
//			adminUserFacade.updateCustomer(customer, "31tal33");
			
//			adminUserFacade.getAllCustomers();
			
//			adminUserFacade.getCustomer(3333);
			
//			customerUserFacade.purchaseCoupon(717);
//			customerUserFacade2.purchaseCoupon(717);
//			customerUserFacade4.purchaseCoupon(717);
//			customerUserFacade5.purchaseCoupon(717);
				
//			companyUserFacade3.removeCoupon(717);
			
//			companyUserFacade2.updateCoupon(789, "1/1/2019", 2000);
//			companyUserFacade2.updateCoupon(789, "1/10/2019", 2000);
			
//			customerUserFacade2.getAllPurchases();
			
//			companyUserFacade2.getCompany();
//			companyUserFacade2.insertCoupon(coupon);
//			companyUserFacade2.getAllCoupons();
//			companyUserFacade2.insertCoupon(coupon4);
//			companyUserFacade2.getAllCouponsByType("Resturants");
//			companyUserFacade2.getAllCouponsByPrice(499.9);
//			companyUserFacade2.getAllCouponsByDate("31/12/2019");
//			companyUserFacade2.insertCoupon(coupon5);
//			customerUserFacade.purchaseCoupon(717);
//			customerUserFacade.getAllPurchases();
//			customerUserFacade.getAllCouponsByType("Sports");
//			customerUserFacade.getAllCouponsByPrice(75);
			
//			Client admin = new Client();
//			AdminUserFacade adminUserFacade =  (AdminUserFacade) admin.login("admin", "1234", ClientType.ADMIN);
//			adminUserFacade.insertCompany(new Company(96369, "Asus", "Asus96369", "israel@asus.com"));
//			Client asus = new Client();
//			CompanyUserFacade companyUserFacade = (CompanyUserFacade) asus.login("Asus", "Asus96369", ClientType.COMPANY);
//			adminUserFacade.insertCustomer(new Customer(2023, "Moran Yamin", "Moran2023"));	
//			Client moranYamin = new Client();
//			CustomerUserFacade customerUserFacade = (CustomerUserFacade) moranYamin.login("Moran Yamin", "Moran2023", ClientType.CUSTOMER);
	
//			Client client = new Client();
//			CouponSystem couponSystem = CouponSystem.getInstance();
//			AdminUserFacade adminUserFacade = (AdminUserFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
//			adminUserFacade.insertCompany(new Company(5193, "Lenovo", "Lenovo5193", "israel@lenovo.com"));
//			CompanyUserFacade companyUserFacade = (CompanyUserFacade) couponSystem.login("Lenovo", "Lenovo519", ClientType.COMPANY);
//			companyUserFacade.getCompany();
//			companyUserFacade.insertCoupon(new Coupon(963, "Swimming Lesson", "30/5/2019", 5, CouponType.SPORTS, "two hours to learn swimming", 75.3, "swim image"));
//			((CompanyUserFacade) client.login("Lenovo", "Lenovo5193", ClientType.COMPANY)).removeCoupon(963);
//			((AdminUserFacade) client.login("admin", "1234", ClientType.ADMIN)).removeCompany(5193);
//			((CompanyUserFacade) client.login("BMW", "Bmw98765", ClientType.COMPANY)).insertCoupon(new Coupon(147, "Racing Trip", "19/5/2022", 5, CouponType.SPORTS, "weekend with sport car", 1550.75, "Racing car image"));
//			((CompanyUserFacade) client.login("BMW", "Bmw98765", ClientType.COMPANY)).removeCoupon(147);
//			((CompanyUserFacade) client.login("BMW", "Bmw98765", ClientType.COMPANY)).getAllCouponsByDate("31/12/2050");
//			((AdminUserFacade) client.login("admin", "1234", ClientType.ADMIN)).insertCustomer(new Customer(3133, "Tal Yamin", "Tal3133"));
//			((CustomerUserFacade) client.login("Tal Yamin", "Tal3133", ClientType.CUSTOMER)).purchaseCoupon(111111);
			
//			((AdminUserFacade) client.login("admin", "1234", ClientType.ADMIN)).getCustomer(139852);
//			((AdminUserFacade) client.login("admin", "1234", ClientType.ADMIN)).getAllCustomers();
//			((AdminUserFacade) client.login("admin", "1234", ClientType.ADMIN)).removeCustomer(3133);
//			((CompanyUserFacade) client.login("Lenovo", "Lenovo5193", ClientType.COMPANY)).updateCoupon(11111, "1/1/2022",100);
//			((CompanyUserFacade) client.login("Lenovo", "Lenovo5193", ClientType.COMPANY)).removeCoupon(963);
			
//			********************************************login test**********************************************************************
//			DataBase.DropDB();
//			DataBase.BuildDB();
//			ConnectionPool connectionPool = ConnectionPool.getInstance();
			
			
			
//			CouponSystem couponSystem = CouponSystem.getInstance();
//			((AdminUserFacade) couponSystem.login("admin", "1234", ClientType.ADMIN)).insertCompany(new Company(1234, "Dell", "Dell1234", "israel@dell.com"));
//			((AdminUserFacade) couponSystem.login("admin", "1234", ClientType.ADMIN)).insertCustomer(new Customer(313, "Tal Yamin", "Tal313"));
//			((CompanyUserFacade) couponSystem.login("Dell", "Dell123", ClientType.COMPANY)).insertCoupon(new Coupon(1, "Racing", "10/2/2019", 5, CouponType.SPORTS, "racing weekend", 1500, "race image"));
//			((CompanyUserFacade) couponSystem.login("Dell", "Dell1234", ClientType.COMPANY)).insertCoupon(new Coupon(2, "a", "10/2/2019", 5, CouponType.SPORTS, "racing weekend", 1500, "race image"));
//			System.out.println("check");
//			((CompanyUserFacade) couponSystem.login("Dell", "Dell1234", ClientType.COMPANY)).insertCoupon(new Coupon(3, "b", "10/2/2019", 5, CouponType.SPORTS, "racing weekend", 1500, "race image"));
//			((CompanyUserFacade) couponSystem.login("Dell", "Dell1234", ClientType.COMPANY)).insertCoupon(new Coupon(3, "b", "10/2/2019", 5, CouponType.SPORTS, "racing weekend", 1500, "race image"));
//			((CustomerUserFacade) couponSystem.login("Tal Yamin", "Tal313", ClientType.CUSTOMER)).purchaseCoupon(1);
//			((CompanyUserFacade) couponSystem.login("Dell", "Dell1234", ClientType.COMPANY)).insertCoupon(new Coupon(2, "Spa", "31/3/2019", 10, CouponType.HEALTH, "spa for couple", 78.8, "spa image"));
//			((CustomerUserFacade) couponSystem.login("Tal Yamin", "Tal313", ClientType.CUSTOMER)).purchaseCoupon(2);
//			((CompanyUserFacade) couponSystem.login("Dell", "Dell1234", ClientType.COMPANY)).removeCoupon(1);
//			((CustomerUserFacade) couponSystem.login("Tal Yamin", "Tal313", ClientType.CUSTOMER)).getAllPurchases();
//			((AdminUserFacade) couponSystem.login("admin", "1234", ClientType.ADMIN)).removeCompany(1234);
//			couponSystem.shutdown();
//			couponSystem.getInstance();

			
//			DataBase.alterTableAdditon("Company_Coupon", "test", "bigint");
			
//			DataBase.alterTableDropping("Customer", "test");
			
//			couponSystem.shutdown();
			
//			DataBase.createCouponTable();
//			DataBase.dropCouponTable();
			
//			ConnectionPool connectionPool = ConnectionPool.getInstance();
			
			
//			CompanyUserFacade companyUserFacade = new CompanyUserFacade(new Company(companyId, companyName, companyPassword, companyEmail))
			
//			ConnectionPool connectionPool = ConnectionPool.getInstance();
//			connectionPool.availableConnections();
//			connectionPool.closeAllConnections();
//			connectionPool.availableConnections();
//			ConnectionPool connectionPool2 = ConnectionPool.getInstance();
//			connectionPool.availableConnections();
			
			
			
//			DataBase.DropDB();
//			DataBase.BuildDB();
//			CouponSystem couponSystem = CouponSystem.getInstance();
//			Client client = new Client();
//			AdminUserFacade adminUserFacade = (AdminUserFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
//			adminUserFacade.insertCompany(new Company(123, "Dell", "Dell123", "check"));
//			adminUserFacade.insertCompany(new Company(456, "Elbit", "Elbit456", "check2"));
//			CompanyUserFacade companyUserFacade = (CompanyUserFacade) couponSystem.login("Elbit", "Elbit456", ClientType.COMPANY);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
