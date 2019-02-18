package DBDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import DAO.CouponDAO;
import DB.DataBase;
import JavaBeans.Coupon;
import JavaBeans.CouponType;

/**
 * @author Tal Yamin
 *
 */

public class CouponDBDAO implements CouponDAO {

	/* Static connection to driver */
	private static Connection connection;

	/*
	 * Insert to Coupon table override method:
	 * This method receive 1 parameters: coupon.
	 * According to parameter, the SQL query is defined with 
	 * the coupon ID, title, startDate, endDate, amount, type, message, price and image.
	 * This method receive connection to DB and create prepareStatement.
	 * Then SQL query for insert to table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	@Override
	public void insertCoupon(Coupon coupon) throws Exception {

		connection = DriverManager.getConnection(DataBase.getConnectionString());

		String sql = "insert into Coupon(ID, TITLE, START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE) values (?,?,?,?,?,?,?,?,?)";

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

			preparedStatement.executeUpdate();

			System.out.println("Coupon created: " + coupon.toString());
		}catch (SQLException e) {
			throw new Exception("DB error - Coupon creation failed. couponId: " + coupon.getCouponId());
		} 
		catch (Exception e) {
			throw new Exception("Coupon creation failed. couponId: " + coupon.getCouponId());
		} finally {
			connection.close();
		}

	}

	/*
	 * Remove from Coupon table override method:
	 * This method receive 1 parameters: coupon.
	 * According to parameter, the SQL query is defined with the coupon ID.    
	 * This method receive connection to DB and create prepareStatement.
	 * Then SQL query for remove from table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	@Override
	public void removeCoupon(Coupon coupon) throws Exception {

		connection = DriverManager.getConnection(DataBase.getConnectionString());

		String sql = "delete from Coupon where ID = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			connection.setAutoCommit(false); // what is it ?
			preparedStatement.setLong(1, coupon.getCouponId());
			preparedStatement.executeUpdate();
			connection.commit();
			System.out.println("remove succeeded. Coupon removed id: " + coupon.getCouponId());

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e2) {
				throw new Exception("DB error - failed to remove Coupon. couponId: " +coupon.getCouponId());
			}
			throw new Exception("failed to remove Coupon. couponId: " +coupon.getCouponId());
		} finally {
			connection.close();
		}

	}
	
	/*
	 * Update Coupon table override method:
	 * This method receive 1 parameters: coupon.
	 * According to parameter, the SQL query is defined with 
	 * the coupon ID, title, startDate, endDate, amount, type, message, price and image.
	 * The updates only available for coupon title, startDate, endDate, amount, type, message, price and image. where the relevant ID. 
	 * This method receive connection to DB and create prepareStatement.
	 * Then SQL query for update table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	//update query to Coupon table
	@Override
	public void updateCoupon(Coupon coupon) throws Exception {

		connection = DriverManager.getConnection(DataBase.getConnectionString());

		String sql = String.format(
				"update Coupon set TITLE = '%s',START_DATE = '" + coupon.getStartDate().toString() + "', END_DATE = '"
						+ coupon.getEndDate().toString()
						+ "', AMOUNT = %d, TYPE = '%s', MESSAGE = '%s', PRICE = %f, IMAGE = '%s' where ID = %d",
				coupon.getTitle(), coupon.getAmount(), coupon.getType().toString(), coupon.getCouponMessage(), coupon.getPrice(),
				coupon.getImage(), coupon.getCouponId());

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.executeUpdate();

			System.out.println("update Coupon succeeded. id which updated: " + coupon.getCouponId());
		} catch (SQLException e) {
			throw new Exception("DB error - update Coupon failed. couponId: " + coupon.getCouponId());
		}catch (Exception e) {
			throw new Exception("update Coupon failed. couponId: " + coupon.getCouponId());
		} 
		finally {
			connection.close();
		}

	}
	/*
	 * Get coupon from Coupon table override method:
	 * This method receive 1 parameters: couponId. 
	 * There is generation of Coupon object which need to receive the data from table.
	 * According to parameter, the SQL query is defined with the coupon ID.    
	 * This method receive connection to DB and create statement.
	 * Then SQL query for get from table is executed. 
	 * There is resultSet which generated so it will be available to receive results from DB.
	 * There is setters methods of Coupon object which used in order to keep the results and to return the object. 
	 * Switch case: by the string of Type that received in resultSet - there is setter of Enum couponType.
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	@Override
	public Coupon getCoupon(long couponId) throws Exception {

		connection = DriverManager.getConnection(DataBase.getConnectionString());
		Coupon coupon = new Coupon();
		try (Statement statement = connection.createStatement()) {
			String sql = "SELECT * FROM Coupon WHERE ID=" + couponId;
			ResultSet resultSet = statement.executeQuery(sql);
			resultSet.next();
			coupon.setCouponId(resultSet.getLong(1));
			coupon.setTitle(resultSet.getString(2));
			coupon.setStartDate(LocalDate.parse(resultSet.getString(3)));
			coupon.setEndDate(LocalDate.parse(resultSet.getString(4)));
			coupon.setAmount(resultSet.getInt(5));
			switch (resultSet.getString(6)) {
			case "Resturants":
				coupon.setType(CouponType.RESTURANTS);
				break;
			case "Health":
				coupon.setType(CouponType.HEALTH);
				break;
			case "Sports":
				coupon.setType(CouponType.SPORTS);
				break;
			case "Traveling":
				coupon.setType(CouponType.TRAVELING);
				break;
			default:
				break;
			}
			coupon.setCouponMessage(resultSet.getString(7));
			coupon.setPrice(resultSet.getDouble(8));
			coupon.setImage(resultSet.getString(9));

		} catch (SQLException e) {
			throw new Exception("DB error - unable to get Coupon data. couponId: " +couponId);
		}catch (Exception e) {
			throw new Exception("unable to get Coupon data. couponId: " +couponId);
		}
		finally {
			connection.close();
		}
		return coupon;

	}
	/*
	 * Get all coupons list from Coupon table override method:
	 * There is generation of ArrayList which need to receive the data from table.
	 * There is generation of Coupon object which need to receive the data from table.
	 * the SQL query is defined for all data in table.   
	 * This method receive connection to DB and create statement.
	 * Then SQL query for get from table is executed. 
	 * There is resultSet which generated so it will be available to receive results from DB.
	 * There are setters methods of Coupon object which used in order to keep the results and to return the object. 
	 * There is function add of ArrayList which used in order to add the Coupon objects and return the list. 
	 * Switch case: by the string of Type that received in resultSet - there is setter of Enum couponType.
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	@Override
	public List<Coupon> getAllCoupons() throws Exception {

		connection = DriverManager.getConnection(DataBase.getConnectionString());
		List<Coupon> list = new ArrayList<>();
		String sql = "select * from Coupon";
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

			while (resultSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setCouponId(resultSet.getLong(1));
				coupon.setTitle(resultSet.getString(2));
				coupon.setStartDate(LocalDate.parse(resultSet.getString(3)));
				coupon.setEndDate(LocalDate.parse(resultSet.getString(4)));
				coupon.setAmount(resultSet.getInt(5));
				switch (resultSet.getString(6)) {
				case "Resturants":
					coupon.setType(CouponType.RESTURANTS);
					break;
				case "Health":
					coupon.setType(CouponType.HEALTH);
					break;
				case "Sports":
					coupon.setType(CouponType.SPORTS);
					break;
				case "Traveling":
					coupon.setType(CouponType.TRAVELING);
					break;
				default:
					break;
				}
				coupon.setCouponMessage(resultSet.getString(7));
				coupon.setPrice(resultSet.getDouble(8));
				coupon.setImage(resultSet.getString(9));

				list.add(coupon);
			}

		} catch (SQLException e) {
			throw new Exception("DB error - unable to get Coupon data");
		}catch (Exception e) {
			throw new Exception("unable to get Coupon data");
		}finally {
			connection.close();
		}
		return list;

	}
	
	/*
	 * Get coupons list from Coupon table override method:
	 * This method receive 1 parameters: couponId. 
	 * There is generation of ArrayList which need to receive the data from table.
	 * There is generation of Coupon object which need to receive the data from table.
	 * the SQL query is defined with the coupon ID.   
	 * This method receive connection to DB and create statement.
	 * Then SQL query for get from table is executed. 
	 * There is resultSet which generated so it will be available to receive results from DB.
	 * There are setters methods of Coupon object which used in order to keep the results and to return the object. 
	 * There is function add of ArrayList which used in order to add the Coupon objects and return the list. 
	 * Switch case: by the string of Type that received in resultSet - there is setter of Enum couponType.
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	@Override
	public List<Coupon> getAllCoupons(long couponId) throws Exception {

		connection = DriverManager.getConnection(DataBase.getConnectionString());
		List<Coupon> list = new ArrayList<>();
		String sql = "select * from Coupon where ID = " + couponId;
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

			while (resultSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setCouponId(resultSet.getLong(1));
				coupon.setTitle(resultSet.getString(2));
				coupon.setStartDate(LocalDate.parse(resultSet.getString(3)));
				coupon.setEndDate(LocalDate.parse(resultSet.getString(4)));
				coupon.setAmount(resultSet.getInt(5));
				switch (resultSet.getString(6)) {
				case "Resturants":
					coupon.setType(CouponType.RESTURANTS);
					break;
				case "Health":
					coupon.setType(CouponType.HEALTH);
					break;
				case "Sports":
					coupon.setType(CouponType.SPORTS);
					break;
				case "Traveling":
					coupon.setType(CouponType.TRAVELING);
					break;
				default:
					break;
				}
				coupon.setCouponMessage(resultSet.getString(7));
				coupon.setPrice(resultSet.getDouble(8));
				coupon.setImage(resultSet.getString(9));

				list.add(coupon);
			}

		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("DB error - unable to get Coupon data. couponId: " + couponId);
		}catch (Exception e) {
			throw new Exception("unable to get Coupon data. couponId: " + couponId);
		}finally {
			connection.close();
		}
		return list;
	}

	/*
	 * Get coupons list from Coupon table override method:
	 * This method receive 2 parameters: couponId and typeName. 
	 * There is generation of ArrayList which need to receive the data from table.
	 * There is generation of Coupon object which need to receive the data from table.
	 * the SQL query is defined with the coupon ID and typeName.  
	 * This method receive connection to DB and create statement.
	 * Then SQL query for get from table is executed. 
	 * There is resultSet which generated so it will be available to receive results from DB.
	 * There are setters methods of Coupon object which used in order to keep the results and to return the object. 
	 * There is function add of ArrayList which used in order to add the Coupon objects and return the list. 
	 * Switch case: by the string of Type that received in resultSet - there is setter of Enum couponType.
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	@Override
	public List<Coupon> getAllCouponsByType(long couponId, String typeName) throws Exception {

		connection = DriverManager.getConnection(DataBase.getConnectionString());
		List<Coupon> list = new ArrayList<>();
		String sql = String.format("select * from Coupon where ID = %d and TYPE = '%s'", couponId, typeName);

		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

			while (resultSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setCouponId(resultSet.getLong(1));
				coupon.setTitle(resultSet.getString(2));
				coupon.setStartDate(LocalDate.parse(resultSet.getString(3)));
				coupon.setEndDate(LocalDate.parse(resultSet.getString(4)));
				coupon.setAmount(resultSet.getInt(5));
				switch (resultSet.getString(6)) {
				case "Resturants":
					coupon.setType(CouponType.RESTURANTS);
					break;
				case "Health":
					coupon.setType(CouponType.HEALTH);
					break;
				case "Sports":
					coupon.setType(CouponType.SPORTS);
					break;
				case "Traveling":
					coupon.setType(CouponType.TRAVELING);
					break;
				default:
					break;
				}
				coupon.setCouponMessage(resultSet.getString(7));
				coupon.setPrice(resultSet.getDouble(8));
				coupon.setImage(resultSet.getString(9));

				list.add(coupon);
			}

		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("DB error - unable to get Coupon data. couponId: " + couponId + " couponType: "+ typeName);
		}catch (Exception e) {
			throw new Exception("unable to get Coupon data. couponId: " + couponId + " couponType: "+ typeName);
		}finally {
			connection.close();
		}
		return list;
	}

	/*
	 * Get coupons list from Coupon table override method:
	 * This method receive 2 parameters: couponId and priceTop. 
	 * There is generation of ArrayList which need to receive the data from table.
	 * There is generation of Coupon object which need to receive the data from table.
	 * the SQL query is defined with the coupon ID and priceTop.  
	 * This method receive connection to DB and create statement.
	 * Then SQL query for get from table is executed. 
	 * There is resultSet which generated so it will be available to receive results from DB.
	 * There are setters methods of Coupon object which used in order to keep the results and to return the object. 
	 * There is function add of ArrayList which used in order to add the Coupon objects and return the list. 
	 * Switch case: by the string of Type that received in resultSet - there is setter of Enum couponType.
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	@Override
	public List<Coupon> getAllCouponsByPrice(long couponId, double priceTop) throws Exception {
		
		connection = DriverManager.getConnection(DataBase.getConnectionString());
		List<Coupon> list = new ArrayList<>();
		String sql = "select * from Coupon where ID = " + couponId + " and PRICE <= " + priceTop;
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

			while (resultSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setCouponId(resultSet.getLong(1));
				coupon.setTitle(resultSet.getString(2));
				coupon.setStartDate(LocalDate.parse(resultSet.getString(3)));
				coupon.setEndDate(LocalDate.parse(resultSet.getString(4)));
				coupon.setAmount(resultSet.getInt(5));
				switch (resultSet.getString(6)) {
				case "Resturants":
					coupon.setType(CouponType.RESTURANTS);
					break;
				case "Health":
					coupon.setType(CouponType.HEALTH);
					break;
				case "Sports":
					coupon.setType(CouponType.SPORTS);
					break;
				case "Traveling":
					coupon.setType(CouponType.TRAVELING);
					break;
				default:
					break;
				}
				coupon.setCouponMessage(resultSet.getString(7));
				coupon.setPrice(resultSet.getDouble(8));
				coupon.setImage(resultSet.getString(9));

				list.add(coupon);
			}

		} catch (SQLException e) {
			throw new Exception("DB error - unable to get Coupon data. couponId: " + couponId + " priceTop: " +priceTop);
		}catch (Exception e) {
			throw new Exception("unable to get Coupon data. couponId: " + couponId + " priceTop: " +priceTop);
		}finally {
			connection.close();
		}
		return list;
	}

	/*
	 * Get coupons list from Coupon table override method:
	 * This method receive 2 parameters: couponId and untilDate. 
	 * There is Data Time Formatter which enable parsing date string to Local Date.
	 * There is generation of ArrayList which need to receive the data from table.
	 * There is generation of Coupon object which need to receive the data from table.
	 * the SQL query is defined with the coupon ID and untilDate.  
	 * This method receive connection to DB and create statement.
	 * Then SQL query for get from table is executed. 
	 * There is resultSet which generated so it will be available to receive results from DB.
	 * There are setters methods of Coupon object which used in order to keep the results and to return the object. 
	 * There is function add of ArrayList which used in order to add the Coupon objects and return the list. 
	 * Switch case: by the string of Type that received in resultSet - there is setter of Enum couponType.
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
	@Override
	public List<Coupon> getAllCouponsByDate(long couponId, String untilDate) throws Exception {
		connection = DriverManager.getConnection(DataBase.getConnectionString());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
		List<Coupon> list = new ArrayList<>();
		LocalDate untilLocalDate = LocalDate.parse(untilDate, formatter);
		String sql = "select * from Coupon where ID = " + couponId + " and END_DATE <= '" + untilLocalDate.toString()+"'";
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

			while (resultSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setCouponId(resultSet.getLong(1));
				coupon.setTitle(resultSet.getString(2));
				coupon.setStartDate(LocalDate.parse(resultSet.getString(3)));
				coupon.setEndDate(LocalDate.parse(resultSet.getString(4)));
				coupon.setAmount(resultSet.getInt(5));
				switch (resultSet.getString(6)) {
				case "Resturants":
					coupon.setType(CouponType.RESTURANTS);
					break;
				case "Health":
					coupon.setType(CouponType.HEALTH);
					break;
				case "Sports":
					coupon.setType(CouponType.SPORTS);
					break;
				case "Traveling":
					coupon.setType(CouponType.TRAVELING);
					break;
				default:
					break;
				}
				coupon.setCouponMessage(resultSet.getString(7));
				coupon.setPrice(resultSet.getDouble(8));
				coupon.setImage(resultSet.getString(9));

				list.add(coupon);
			}

		} catch (SQLException e) {
			throw new Exception("DB error - unable to get Coupon data. couponId: " + couponId + " untilDate: " + untilDate);
		}catch (Exception e) {
			throw new Exception("unable to get Coupon data. couponId: " + couponId + " untilDate: " + untilDate);
		}finally {
			connection.close();
		}
		return list;
	}

}
