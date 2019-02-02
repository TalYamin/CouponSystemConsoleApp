import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DB.DataBase;
import DBDAO.CompanyDBDAO;
import DBDAO.Company_CouponDBDAO;
import DBDAO.CouponDBDAO;
import DBDAO.CustomerDBDAO;
import DBDAO.Customer_CouponDBDAO;
import Facades.AdminUserFacade;
import Facades.CompanyUserFacade;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.CouponType;
import JavaBeans.Customer;

public class Test {

	public static void main(String[] args) throws Exception {

		try {

			Class.forName(DataBase.getDriverConnextion());
			
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
			
			Coupon coupon = new Coupon(123, "BBB", "31/12/2019", 10, CouponType.RESTURANTS, "BBB for couple", 70.4, "bbb image");
			Coupon coupon2 = new Coupon(456, "Gilboa", "1/6/2019", 5, CouponType.TRAVELING, "trip to giboa", 500, "giboa image");
			Coupon coupon3 = new Coupon(789, "ski", "1/3/2019", 4, CouponType.SPORTS, "ski weekend", 1500, "ski image");
			Coupon coupon4 = new Coupon(717, "Domino Pizza", "31/12/2019", 20, CouponType.RESTURANTS, "family pizza", 50, "domino image");
			
			Company company = new Company(1793, "Dell", "dell1793", "israel@dell.com");
			Company company2 = new Company(4562, "Cellcom", "cellcom4562", "israel@cellcom.com");
			Company company3 = new Company(7893, "Elbit", "Elbit7893", "israel@elbit.com");
			
			
			Customer customer = new Customer(3133, "Tal Yamin", "tal3133");
			Customer customer2 = new Customer(2046, "Ofek Mesika", "Ofek2046");
			Customer customer3 = new Customer(7894, "Peleg Yamin", "hello");
			

			
			AdminUserFacade adminUserFacade = new AdminUserFacade();
			

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
			
			CompanyUserFacade companyUserFacade = new CompanyUserFacade(company);
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
