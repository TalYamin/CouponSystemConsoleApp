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
			

			
//			Coupon coupon = new Coupon(123, "BBB", "31/12/2019", 10, CouponType.RESTURANTS, "BBB for couple", 70.4, "bbb image");
//			Coupon coupon2 = new Coupon(456, "Gilboa", "1/6/2019", 5, CouponType.TRAVELING, "trip to giboa", 500, "giboa image");
//			Coupon coupon3 = new Coupon(789, "ski", "1/3/2019", 4, CouponType.SPORTS, "ski weekend", 1500, "ski image");
//			Coupon coupon4 = new Coupon(123, "Domino Pizza", "31/12/2019", 20, CouponType.RESTURANTS, "family pizza", 50, "domino image");
//			
//			Company company = new Company(1793, "Dell", "dell1793", "israel@dell.com");
//			Company company2 = new Company(4562, "Cellcom", "cellcom4562", "israel@cellcom.com");
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
