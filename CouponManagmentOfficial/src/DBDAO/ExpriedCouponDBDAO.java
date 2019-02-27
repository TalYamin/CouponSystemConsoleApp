package DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DAO.ExpiredCouponDAO;
import JavaBeans.Coupon;
import SystemUtils.ConnectionPool;

public class ExpriedCouponDBDAO implements ExpiredCouponDAO {

	private static ConnectionPool connectionPool;

	@Override
	public void insertCoupon(Coupon coupon) throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();

		String sql = "insert into Expired_Coupon(ID, TITLE, START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE, ACTIVE) values (?,?,?,?,?,?,?,?,?,?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setLong(1, coupon.getCouponId());
			preparedStatement.setString(2, coupon.getTitle());
			preparedStatement.setString(3, coupon.getStartDate().toString());
			preparedStatement.setString(4, coupon.getEndDate().toString());
			preparedStatement.setInt(5, coupon.getAmount());
			preparedStatement.setString(6, coupon.getType().toString());
			preparedStatement.setString(7, coupon.getCouponMessage());
			preparedStatement.setDouble(8, coupon.getPrice());
			preparedStatement.setString(9, coupon.getImage());
			preparedStatement.setBoolean(10, coupon.isActive());

			preparedStatement.executeUpdate();

			System.out.println("Expired Coupon created: " + coupon.toString());
		} catch (SQLException e) {
			throw new Exception("DB error - Expired Coupon creation failed. couponId: " + coupon.getCouponId());
		} catch (Exception e) {
			throw new Exception("Expired Coupon creation failed. couponId: " + coupon.getCouponId());
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}

	}

}
