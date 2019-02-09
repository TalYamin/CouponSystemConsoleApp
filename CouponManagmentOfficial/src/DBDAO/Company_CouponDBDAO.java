package DBDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DAO.Company_CouponDAO;
import DB.DataBase;
import JavaBeans.Company;
import JavaBeans.Coupon;

/**
 * @author Tal Yamin
 *
 */

public class Company_CouponDBDAO implements Company_CouponDAO{

	//static connection to driver
	private static Connection connection;
	
	//insert query to Company_Coupon table
	@Override
	public void insertCompany_Coupon(Company company, Coupon coupon) throws Exception {
		
		connection = DriverManager.getConnection(DataBase.getConnectionString());

		String sql = "insert into Company_Coupon (Company_ID, Coupon_ID) values (?,?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setLong(1, company.getCompanyId());
			preparedStatement.setLong(2, coupon.getCouponId());

			preparedStatement.executeUpdate();

			System.out.println("Company_Coupon added. companyId: " + company.getCompanyId() + " couponId: " + coupon.getCouponId());
		} catch (Exception e) {
			throw new Exception("Company_Coupon addition failed. companyId: " + company.getCompanyId() + " couponId: " + coupon.getCouponId());
		} finally {
			connection.close();
		}
		
	}

	//remove query by company&coupon to Company_Coupon table
	@Override
	public void removeCompany_Coupon(Company company, Coupon coupon) throws Exception {
		
		connection = DriverManager.getConnection(DataBase.getConnectionString());

		String sql = "delete from Company_Coupon where Company_ID = ? AND Coupon_ID = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			connection.setAutoCommit(false); // what is it ?
			preparedStatement.setLong(1, company.getCompanyId());
			preparedStatement.setLong(2, coupon.getCouponId());
			preparedStatement.executeUpdate();
			connection.commit();
			System.out.println("Company_Coupon remove succeeded. companyId: " + company.getCompanyId() + " couponId: " + coupon.getCouponId());

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e2) {
				throw new Exception("DataBase error");
			}
			throw new Exception("failed to remove Company_Coupon. companyId "+ company.getCompanyId() + " couponId: " + coupon.getCouponId());
		} finally {
			connection.close();
		}
		
	}

	//remove query by company only to Company_Coupon table
	@Override
	public void removeCompany_Coupon(Company company) throws Exception {
		
		connection = DriverManager.getConnection(DataBase.getConnectionString());

		String sql = "delete from Company_Coupon where Company_ID = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			connection.setAutoCommit(false); // what is it ?
			preparedStatement.setLong(1, company.getCompanyId());
			preparedStatement.executeUpdate();
			connection.commit();
			System.out.println("Company_Coupon remove succeeded. comapnyId: "+ company.getCompanyId());

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e2) {
				throw new Exception("DataBase error");
			}
			throw new Exception("failed to remove Company_Coupon. comapnyId: "+ company.getCompanyId());
		} finally {
			connection.close();
		}
		
	}

	//remove query by coupon only to Company_Coupon table
	@Override
	public void removeCompany_Coupon(Coupon coupon) throws Exception {
		
		connection = DriverManager.getConnection(DataBase.getConnectionString());

		String sql = "delete from Company_Coupon where Coupon_ID = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			connection.setAutoCommit(false); // what is it ?
			preparedStatement.setLong(1, coupon.getCouponId());
			preparedStatement.executeUpdate();
			connection.commit();
			System.out.println("Company_Coupon remove succeeded. couponId: " + coupon.getCouponId());

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e2) {
				throw new Exception("DataBase error");
			}
			throw new Exception("failed to remove Company_Coupon. couponId: " + coupon.getCouponId());
		} finally {
			connection.close();
		}
		
	}

	//getCompanies query by coupon only to Company_Coupon table
	@Override
	public List<Long> getCompanyId(long couponId) throws Exception {
		
		connection = DriverManager.getConnection(DataBase.getConnectionString());
		List<Long> companiesId = new ArrayList<>();
		String sql = "select * from Company_Coupon where Coupon_ID = " + couponId;
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong(1);
				companiesId.add(companyId);
			}
		} catch (SQLException e) {
			throw new Exception("unable to get Company_Coupon data. couponId: " + couponId);
		} finally {
			connection.close();
		}
		return companiesId;
	}

	//getCompanies query to Company_Coupon table
	//pay attention - there are duplicate values
	@Override
	public List<Long> getAllCompaniesId() throws Exception {
		
		connection = DriverManager.getConnection(DataBase.getConnectionString());
		List<Long> companiesId = new ArrayList<>();
		String sql = "select * from Company_Coupon";
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
			while (resultSet.next()) {
				long companyId = resultSet.getLong(1);
				companiesId.add(companyId);

			}
		} catch (SQLException e) {
			throw new Exception("unable to get Company_Coupon data");
		} finally {
			connection.close();
		}
		return companiesId;
	}

	//getCoupons query by company only to Company_Coupon table
	@Override
	public List<Long> getCouponId(long companyId) throws Exception {
		
		connection = DriverManager.getConnection(DataBase.getConnectionString());
		List<Long> couponsId = new ArrayList<>();
		String sql = "select * from Company_Coupon where Company_ID = " + companyId;
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

			while (resultSet.next()) {
				long couponId = resultSet.getLong(2);
				couponsId.add(couponId);
			}
		} catch (SQLException e) {
			throw new Exception("unable to get Company_Coupon data. companyId: " + companyId);
		} finally {
			connection.close();
		}
		return couponsId;
	}

	//getCoupons query to Company_Coupon table
	//pay attention - there are duplicate values
	@Override
	public List<Long> getAllCouponsId() throws Exception {
		
		connection = DriverManager.getConnection(DataBase.getConnectionString());
		List<Long> couponsId = new ArrayList<>();
		String sql = "select * from Company_Coupon";
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
			while (resultSet.next()) {
				long couponId = resultSet.getLong(2);
				couponsId.add(couponId);

			}
		} catch (SQLException e) {
			throw new Exception("unable to get Company_Coupon data");
		} finally {
			connection.close();
		}
		return couponsId;
	}

}
