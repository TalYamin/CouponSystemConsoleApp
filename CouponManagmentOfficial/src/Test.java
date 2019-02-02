import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DB.DataBase;
import JavaBeans.Coupon;
import JavaBeans.CouponType;

public class Test {

	public static void main(String[] args) throws Exception {

		try {

			Class.forName(DataBase.getDriverConnextion());
			
//			DataBase.dropCompanyTable();
//			DataBase.dropCustomerTable();
//			DataBase.dropCouponTable();
//			DataBase.dropCompany_CouponTable();
//			DataBase.dropCustomer_CouponTable();
			


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
