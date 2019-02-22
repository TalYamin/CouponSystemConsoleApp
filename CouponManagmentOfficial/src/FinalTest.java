import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.apache.derby.tools.sysinfo;

import DB.DataBase;
import Exceptions.ConnectionException;
import Facades.AdminUserFacade;
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

		try {

			// Class.forName(DataBase.getDriverConnextion());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String name;
		String password;
		CouponClientFacade clientFacade;
		ClientType clientType = ClientType.ADMIN;
		List<Company> companies;
		Company testCompany;
		Customer testCustomer;
		AdminUserFacade admin;

		Company abc = new Company();
		abc.setCompanyName("abc");
		abc.setCompanyEmail("abc@abc.abc");
		abc.setCompanyPassword("abcabc");

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
		System.out.println(coupon);

		try {
			ConnectionPool.getInstance().availableConnections();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// **Admin Test**//

		CouponSystem couponSystem = null;

		try {
			couponSystem = CouponSystem.getInstance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// (1) bad login//

		name = "admin";
		password = "12348";
		System.out.println("(1) checking bad login as admin: ");
		try {
			clientFacade = couponSystem.login(name, password, clientType);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		name = "admin";
		password = "1234";

		// (2)get admin facade

		try {
			clientFacade = couponSystem.login(name, password, clientType);
			admin = (AdminUserFacade) clientFacade;

			// add a customer

			admin.insertCustomer(new Customer(313, "Tal Yamin", "Tal313"));

			// (3) print a customer

			testCustomer = admin.getCustomer(313);
			

			// add company

			admin.insertCompany(new Company(9876, "Dell", "Dell9876", "israel@dell.com"));
			admin.insertCompany(new Company(4321, "Elbit", "Elbit4321", "israel@elbit.com"));

			// (4) print all companies

			companies = admin.getAllCompanies();
			
			
			// (5) print a company

			testCompany = admin.getCompany(9876);
			
			
			//()
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
