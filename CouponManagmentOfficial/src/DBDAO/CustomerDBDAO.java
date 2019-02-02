package DBDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DAO.CustomerDAO;
import DB.DataBase;
import JavaBeans.Customer;

/**
 * @author Tal Yamin
 *
 */

public class CustomerDBDAO implements CustomerDAO {

	//static connection to driver
	private static Connection connection;

	//insert query to Customer table
	@Override
	public void insertCustomer(Customer customer) throws Exception {

		connection = DriverManager.getConnection(DataBase.getConnectionString());

		String sql = "insert into Customer(ID, CUST_NAME, PASSWORD) values (?,?,?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setLong(1, customer.getCustomerId());
			preparedStatement.setString(2, customer.getCustomerName());
			preparedStatement.setString(3, customer.getCustomerPassword());

			preparedStatement.executeUpdate();

			System.out.println("Customer created: " + customer.toString());
		} catch (SQLException e) {
			throw new Exception("Customer creation failed");
		} finally {
			connection.close();
		}

	}

	//remove query to Customer table
	@Override
	public void removeCustomer(Customer customer) throws Exception {

		connection = DriverManager.getConnection(DataBase.getConnectionString());

		String sql = "delete from Customer where ID = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			connection.setAutoCommit(false); // what is it ?
			preparedStatement.setLong(1, customer.getCustomerId());
			preparedStatement.executeUpdate();
			connection.commit();
			System.out.println("remove succeeded. Customer removed id: " + customer.getCustomerId());

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e2) {
				throw new Exception("DataBase error");
			}
			throw new Exception("failed to remove Customer");
		} finally {
			connection.close();
		}

	}

	//update query to Customer table
	@Override
	public void updateCustomer(Customer customer) throws Exception {

		connection = DriverManager.getConnection(DataBase.getConnectionString());

		String sql = String.format("update Customer set CUST_NAME= '%s',PASSWORD = '%s'  where ID = %d",
				customer.getCustomerName(), customer.getCustomerPassword(), customer.getCustomerId());

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.executeUpdate();

			System.out.println("update Customer succeeded. id which updated: " + customer.getCustomerId());
		} catch (SQLException e) {
			throw new Exception("update Customer failed");
		} finally {
			connection.close();
		}

	}

	//get query to Customer table
	@Override
	public Customer getCustomer(long customerId) throws Exception {
		
		connection = DriverManager.getConnection(DataBase.getConnectionString());
		Customer customer = new Customer();
		try (Statement statement = connection.createStatement()) {
			String sql = "SELECT * FROM Customer WHERE ID=" + customerId;
			ResultSet resultSet = statement.executeQuery(sql);
			resultSet.next();
			customer.setCustomerId(resultSet.getLong(1));
			customer.setCustomerName(resultSet.getString(2));
			customer.setCustomerPassword(resultSet.getString(3));

		} catch (SQLException e) {
			throw new Exception("unable to get Customer data");
		} finally {
			connection.close();
		}
		return customer;

	}

	//getAll query to Customer table
	@Override
	public synchronized List<Customer> getAllCustomers() throws Exception {
		
		connection = DriverManager.getConnection(DataBase.getConnectionString());
		List<Customer> list = new ArrayList<>();
		String sql = "select * from Customer";
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

			while (resultSet.next()) {
				long customerId = resultSet.getLong(1);
				String customerName = resultSet.getString(2);
				String customerPassword = resultSet.getString(3);

				list.add(new Customer(customerId, customerName, customerPassword));
			}

		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("unable to get Customer data");
		} finally {
			connection.close();
		}
		return list;

	}

}
