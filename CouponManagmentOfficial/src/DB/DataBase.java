package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import SystemUtils.ConnectionPool;

/**
 * @author Tal Yamin
 *
 */

public class DataBase {

	/* DB connection port */
	private static String connectionString = "jdbc:derby://localhost:3301/CouponManagment;create=true";
	/* DB derby driver */
	private static String DriverConnection = "org.apache.derby.jdbc.ClientDriver";
	/* Static connection - should be in connection pool */

	private static ConnectionPool connectionPool;

	
	/* Get method for DB connection port */
	public static String getConnectionString() {
		return connectionString;
	}

	/* Get method for DB derby driver */
	public static String getDriverConnextion() {
		return DriverConnection;
	}

	
	
	/*
	 * Create Company table method:
	 * This method receive connection to DB and create statement.
	 * Then SQL query for create Company table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	public static void createCompanyTable() throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		
		String sql = "create table Company (" + "ID bigint not null primary key, " + "COMP_NAME varchar(50) not null, "
				+ "PASSWORD varchar(50) not null, " + "EMAIL varchar(50) not null)";

		try (Statement statement = connection.createStatement()) {

			statement.executeUpdate(sql);

			System.out.println("Company table has been created");

		} catch (SQLException e) {
			throw new Exception("unable to create Company table");
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}

	}

	
	/*
	 * Create Customer table method:
	 * This method receive connection to DB and create statement.
	 * Then SQL query for create Customer table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	public static void createCustomerTable() throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		
		
		String sql = "create table Customer (" + "ID bigint not null primary key, " + "CUST_NAME varchar(50) not null, "
				+ "PASSWORD varchar(50) not null)";

		try (Statement statement = connection.createStatement()) {

			statement.executeUpdate(sql);

			System.out.println("Customer table has been created");

		} catch (SQLException e) {
			throw new Exception("unable to create Customer table");
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}

	}

	/*
	 * Create Coupon table method:
	 * This method receive connection to DB and create statement.
	 * Then SQL query for create Coupon table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	public static void createCouponTable() throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();

		
		
		String sql = "create table Coupon (" + "ID bigint not null primary key, " + "TITLE varchar(50) not null, "
				+ "START_DATE date not null, " + "END_DATE date not null, " + "AMOUNT integer not null, "
				+ "TYPE varchar(50) not null, " + "MESSAGE varchar(50) not null, " + " PRICE float not null, "
				+ "IMAGE varchar(200) not null," + "Active boolean not null)";

		try (Statement statement = connection.createStatement()) {

			statement.executeUpdate(sql);

			System.out.println("Coupon table has been created");

		} catch (SQLException e) {
			throw new Exception("unable to create Coupon table");
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}

	}

	/*
	 * Create Customer_Coupon table method:
	 * This table is Join table which combines two columns as unified primary key.  
	 * This method receive connection to DB and create statement.
	 * Then SQL query for create Customer_Coupon table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	public static void createCustomer_CouponTable() throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();

		String sql = "create table Customer_Coupon (" + "Customer_ID bigint, " + "Coupon_ID bigint, "
				+ "primary key (Customer_ID, Coupon_ID))";

		try (Statement statement = connection.createStatement()) {

			statement.executeUpdate(sql);

			System.out.println("Customer_Coupon table has been created");

		} catch (SQLException e) {
			throw new Exception("unable to create Customer_Coupon table");
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}

	}

	/*
	 * Create Company_Coupon table method:
	 * This table is Join table which combines two columns as unified primary key.  
	 * This method receive connection to DB and create statement.
	 * Then SQL query for create Company_Coupon table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	public static void createCompany_CouponTable() throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();

		String sql = "create table Company_Coupon (" + "Company_ID bigint, " + "Coupon_ID bigint, "
				+ "primary key (Company_ID, Coupon_ID))";

		try (Statement statement = connection.createStatement()) {

			statement.executeUpdate(sql);

			System.out.println("Company_Coupon table has been created");

		} catch (SQLException e) {
			throw new Exception("unable to create Company_Coupon table");
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}

	}
	
	
	public static void createExpiredCouponTable() throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		
		String sql = "create table Expired_Coupon (" + "ID bigint not null primary key, " + "TITLE varchar(50) not null, "
				+ "START_DATE date not null, " + "END_DATE date not null, " + "AMOUNT integer not null, "
				+ "TYPE varchar(50) not null, " + "MESSAGE varchar(50) not null, " + " PRICE float not null, "
				+ "IMAGE varchar(200) not null," + "Active boolean not null)";

		try (Statement statement = connection.createStatement()) {

			statement.executeUpdate(sql);

			System.out.println("ExpiredCoupon table has been created");

		} catch (SQLException e) {
			throw new Exception("unable to create ExpiredCoupon table");
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}

	}
	
	
	
	
	

	/*
	 * Drop Company table method:
	 * This method receive connection to DB and create statement.
	 * Then SQL query for drop Company table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	public static void dropCompanyTable() throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();

		String sql = "drop table Company";

		try (Statement statement = connection.createStatement()) {

			statement.executeUpdate(sql);

			System.out.println("Company Table dropped successfully");

		} catch (SQLException e) {
			throw new Exception("unable to drop Company Table");
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}
	}

	/*
	 * Drop Customer table method:
	 * This method receive connection to DB and create statement.
	 * Then SQL query for drop Customer table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	public static void dropCustomerTable() throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();

		String sql = "drop table Customer";

		try (Statement statement = connection.createStatement()) {

			statement.executeUpdate(sql);

			System.out.println("Customer Table dropped successfully");

		} catch (SQLException e) {
			throw new Exception("unable to drop Customer Table");
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}
	}

	/*
	 * Drop Coupon table method:
	 * This method receive connection to DB and create statement.
	 * Then SQL query for drop Coupon table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	public static void dropCouponTable() throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();

		String sql = "drop table Coupon";

		try (Statement statement = connection.createStatement()) {

			statement.executeUpdate(sql);

			System.out.println("Coupon Table dropped successfully");

		} catch (SQLException e) {
			throw new Exception("unable to drop Coupon Table");
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}
	}

	/*
	 * Drop Customer_Coupon table method:
	 * This method receive connection to DB and create statement.
	 * Then SQL query for drop Customer_Coupon table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	public static void dropCustomer_CouponTable() throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();

		String sql = "drop table Customer_Coupon";

		try (Statement statement = connection.createStatement()) {

			statement.executeUpdate(sql);

			System.out.println("Customer_Coupon Table dropped successfully");

		} catch (SQLException e) {
			throw new Exception("unable to drop Customer_Coupon Table");
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}
	}

	/*
	 * Drop Company_Coupon table method:
	 * This method receive connection to DB and create statement.
	 * Then SQL query for drop Company_Coupon table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	public static void dropCompany_CouponTable() throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();

		String sql = "drop table Company_Coupon";

		try (Statement statement = connection.createStatement()) {

			statement.executeUpdate(sql);

			System.out.println("Company_Coupon Table dropped successfully");

		} catch (SQLException e) {
			throw new Exception("unable to drop Company_Coupon Table");
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}
	}
	
	
	
	public static void dropExpiredCouponTable() throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();

		String sql = "drop table Expired_Coupon";

		try (Statement statement = connection.createStatement()) {

			statement.executeUpdate(sql);

			System.out.println("ExpiredCoupon Table dropped successfully");

		} catch (SQLException e) {
			throw new Exception("unable to drop ExpiredCoupon Table");
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}
	}
	
	

	/*
	 * Build DB method:
	 * This method include all the methods which create tables in DB.
	 * This method receive 1 parameter: connection.
	 * Any method receive connection to DB and create statement.
	 * Then SQL query for any table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	public static void BuildDB() throws Exception {

		try {
			DataBase.createCompanyTable();
			DataBase.createCustomerTable();
			DataBase.createCouponTable();
			DataBase.createCompany_CouponTable();
			DataBase.createCustomer_CouponTable();
			DataBase.createExpiredCouponTable();
		} catch (SQLException e) {
			throw new Exception("unable to build all tables of DB");
		}
	}

	/*
	 * Drop DB method:
	 * This method include all the methods which drop tables from DB.
	 * This method receive 1 parameter: connection.
	 * Any method receive connection to DB and create statement.
	 * Then SQL query for any table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	public static void DropDB() throws Exception {

		try {
			DataBase.dropCompanyTable();
			DataBase.dropCustomerTable();
			DataBase.dropCouponTable();
			DataBase.dropCompany_CouponTable();
			DataBase.dropCustomer_CouponTable();
			DataBase.dropExpiredCouponTable();
		} catch (SQLException e) {
			throw new Exception("unable to drop all tables of DB");
		}
	}

	/*
	 * Alter table for adding column method:
	 * This method receive 3 parameters: table, columnName and dataType. 
	 * According to parameters, the SQL query is defined with 
	 * the relevant table, and with details for new column.
	 * This method receive connection to DB and create prepareStatement.
	 * Then SQL query for alter table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	public static void alterTableAdditon(String table, String columnName, String dataType) throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();

		String sql = String.format("alter table %s add %s %s", table, columnName, dataType);

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.executeUpdate();

			System.out.println("Company table has been altered - column " + columnName + " has been added");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("alter Company table failed.");
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}
	}

	/*
	 * Alter table for dropping column method:
	 * This method receive 2 parameters: table and columnName. 
	 * According to parameters, the SQL query is defined with 
	 * the relevant table and with the relevant column.
	 * This method receive connection to DB and create prepareStatement.
	 * Then SQL query for alter table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	public static void alterTableDropping(String table, String columnName) throws Exception {

		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();

		String sql = String.format("alter table %s drop column %s", table, columnName);

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.executeUpdate();

			System.out.println("Company table has been altered - column " + columnName + " has been dropped");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("alter Company table failed.");
		} finally {
			connection.close();
			connectionPool.returnConnection(connection);
		}
	}

}
