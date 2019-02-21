package DBDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DAO.Customer_CouponDAO;
import DB.DataBase;
import JavaBeans.Coupon;
import JavaBeans.Customer;
import SystemUtils.ConnectionPool;

/**
 * @author Tal Yamin
 *
 */

public class Customer_CouponDBDAO implements Customer_CouponDAO {

	private static ConnectionPool connectionPool;

	//insert query to Customer_Coupon table
	@Override
	public void insertCustomer_Coupon(Customer customer, Coupon coupon) throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();

		String sql = "insert into Customer_Coupon (Customer_ID, Coupon_ID) values (?,?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setLong(1, customer.getCustomerId());
			preparedStatement.setLong(2, coupon.getCouponId());

			preparedStatement.executeUpdate();

			System.out.println("Customer_Coupon added. customerId: " + customer.getCustomerId() + " couponId: " + coupon.getCouponId());
		} catch (SQLException e) {
			throw new Exception("Customer_Coupon addition failed. customerId: " + customer.getCustomerId() + " couponId: "+ coupon.getCouponId());
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}

	}

	//remove query by customer&coupon to Customer_Coupon table
	@Override
	public void removeCustomer_Coupon(Customer customer, Coupon coupon) throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();

		String sql = "delete from Customer_Coupon where Customer_ID = ? AND Coupon_ID = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			connection.setAutoCommit(false); // what is it ?
			preparedStatement.setLong(1, customer.getCustomerId());
			preparedStatement.setLong(2, coupon.getCouponId());
			preparedStatement.executeUpdate();
			connection.commit();
			System.out.println("Customer_Coupon remove succeeded. customerId: " + customer.getCustomerId() + "couponId: " + coupon.getCouponId());

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e2) {
				throw new Exception("DataBase error");
			}
			throw new Exception("failed to remove Customer_Coupon. customerId: " + customer.getCustomerId() + "couponId: " + coupon.getCouponId());
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}

	}

	//remove query by customer only to Customer_Coupon table
	@Override
	public void removeCustomer_Coupon(Customer customer) throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();

		String sql = "delete from Customer_Coupon where Customer_ID = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			connection.setAutoCommit(false); // what is it ?
			preparedStatement.setLong(1, customer.getCustomerId());
			preparedStatement.executeUpdate();
			connection.commit();
			System.out.println("Customer_Coupon remove succeeded. customerId: " + customer.getCustomerId());

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e2) {
				throw new Exception("DataBase error");
			}
			throw new Exception("failed to remove Customer_Coupon. customerId: " + customer.getCustomerId());
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}

	}

	//remove query by coupon only to Customer_Coupon table
	@Override
	public void removeCustomer_Coupon(Coupon coupon) throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();

		String sql = "delete from Customer_Coupon where Coupon_ID = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			connection.setAutoCommit(false); // what is it ?
			preparedStatement.setLong(1, coupon.getCouponId());
			preparedStatement.executeUpdate();
			connection.commit();
			System.out.println("Customer_Coupon remove succeeded. couponId: " + coupon.getCouponId());

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e2) {
				throw new Exception("DataBase error");
			}
			throw new Exception("failed to remove Customer_Coupon. couponId: " + coupon.getCouponId());
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}

	}

	//getCustomers query by coupon to Customer_Coupon table
	@Override
	public List<Long> getCustomerId(long couponId) throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		List<Long> customersId = new ArrayList<>();
		String sql = "select * from Customer_Coupon where Coupon_ID = " + couponId;
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

			while (resultSet.next()) {
				long customerId = resultSet.getLong(1);
				customersId.add(customerId);
			}
		} catch (SQLException e) {
			throw new Exception("unable to get Customer_Coupon data. couponId: " + couponId);
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}
		return customersId;

	}

	//getCustomers query Customer_Coupon table
	//pay attention - there are duplicate values
	@Override
	public List<Long> getAllCustomersId() throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		List<Long> customersId = new ArrayList<>();
		String sql = "select * from Customer_Coupon";
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
			while (resultSet.next()) {
				long customerId = resultSet.getLong(1);
				customersId.add(customerId);

			}
		} catch (SQLException e) {
			throw new Exception("unable to get Customer_Coupon data");
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}
		return customersId;

	}

	//getCoupons query by customer to Customer_Coupon table
	@Override
	public List<Long> getCouponId(long customerId) throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		List<Long> couponsId = new ArrayList<>();
		String sql = "select * from Customer_Coupon where Customer_ID = " + customerId;
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

			while (resultSet.next()) {
				long couponId = resultSet.getLong(2);
				couponsId.add(couponId);
			}
		} catch (SQLException e) {
			throw new Exception("unable to get Customer_Coupon data. customerId: " + customerId);
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}
		return couponsId;

	}

	//getCoupons query to Customer_Coupon table
	//pay attention - there are duplicate values
	@Override
	public List<Long> getAllCouponsId() throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		List<Long> couponsId = new ArrayList<>();
		String sql = "select * from Customer_Coupon";
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
			while (resultSet.next()) {
				long couponId = resultSet.getLong(2);
				couponsId.add(couponId);

			}
		} catch (SQLException e) {
			throw new Exception("unable to get Customer_Coupon data");
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}
		return couponsId;
	}

}
