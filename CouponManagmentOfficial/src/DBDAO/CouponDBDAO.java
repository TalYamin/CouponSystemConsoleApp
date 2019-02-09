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

	//static connection to driver
	private static Connection connection;

	//insert query to Coupon table
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
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Coupon creation failed. couponId: " + coupon.getCouponId());
		} finally {
			connection.close();
		}

	}

	//remove query to Coupon table
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
				throw new Exception("DataBase error");
			}
			throw new Exception("failed to remove Coupon. couponId: " +coupon.getCouponId());
		} finally {
			connection.close();
		}

	}

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
			throw new Exception("update Coupon failed. couponId: " + coupon.getCouponId());
		} finally {
			connection.close();
		}

	}

	//get query to Coupon table
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
			throw new Exception("unable to get Coupon data. couponId: " +couponId);
		} finally {
			connection.close();
		}
		return coupon;

	}

	//getAll query to Coupon table
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
			System.out.println(e);
			throw new Exception("unable to get Coupon data");
		} finally {
			connection.close();
		}
		return list;

	}

	//getAll query by id to Coupon table
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
			throw new Exception("unable to get Coupon data. couponId: " + couponId);
		} finally {
			connection.close();
		}
		return list;
	}

	//getAll by id and type query to Coupon table
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
			throw new Exception("unable to get Coupon data. couponId: " + couponId + " couponType: "+ typeName);
		} finally {
			connection.close();
		}
		return list;
	}

	//getAll by id and price query to Coupon table
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
			System.out.println(e);
			e.printStackTrace();
			throw new Exception("unable to get Coupon data. couponId: " + couponId + " priceTop: " +priceTop);
		} finally {
			connection.close();
		}
		return list;
	}

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
			System.out.println(e);
			e.printStackTrace();
			throw new Exception("unable to get Coupon data. couponId: " + couponId + " untilDate: " + untilDate);
		} finally {
			connection.close();
		}
		return list;
	}

}
