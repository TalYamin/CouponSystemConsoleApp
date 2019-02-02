package DBDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DAO.CompanyDAO;
import DB.DataBase;
import JavaBeans.Company;

/**
 * @author Tal Yamin
 *
 */

public class CompanyDBDAO implements CompanyDAO {

	//static connection to driver
	private static Connection connection;

	
	//insert query to Company table
	@Override
	public void insertCompany(Company company) throws Exception {

		connection = DriverManager.getConnection(DataBase.getConnectionString());

		String sql = "insert into Company(ID, COMP_NAME, PASSWORD, EMAIL) values (?,?,?,?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setLong(1, company.getCompanyId());
			preparedStatement.setString(2, company.getCompanyName());
			preparedStatement.setString(3, company.getCompanyPassword());
			preparedStatement.setString(4, company.getCompanyEmail());

			preparedStatement.executeUpdate();

			System.out.println("Company created: " + company.toString());
		} catch (SQLException e) {
			throw new Exception("Company creation failed");
		} finally {
			connection.close();
		}

	}

	
	//remove query to Company table
	@Override
	public void removeCompany(Company company) throws Exception {

		connection = DriverManager.getConnection(DataBase.getConnectionString());

		String sql = "delete from Company where ID = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			connection.setAutoCommit(false); // what is it ?
			preparedStatement.setLong(1, company.getCompanyId());
			preparedStatement.executeUpdate();
			connection.commit();
			System.out.println("remove succeeded. Company removed id: " + company.getCompanyId()); 
																									
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e2) {
				throw new Exception("DataBase error");
			}
			throw new Exception("failed to remove Company");
		} finally {
			connection.close();
		} 

	}

	//update query to Company table
	@Override
	public void updateCompany(Company company) throws Exception {

		connection = DriverManager.getConnection(DataBase.getConnectionString());

		String sql = String.format("update Company set COMP_NAME= '%s',PASSWORD = '%s', EMAIL= '%s' where ID = %d",
				company.getCompanyName(), company.getCompanyPassword(), company.getCompanyEmail(),
				company.getCompanyId());

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.executeUpdate();

			System.out.println("update Company succeeded. id which updated: " + company.getCompanyId());
		} catch (SQLException e) {
			throw new Exception("update Compnay failed");
		} finally {
			connection.close();
		}

	}

	//get query to Company table
	@Override
	public Company getCompany(long companyId) throws Exception {

		connection = DriverManager.getConnection(DataBase.getConnectionString());
		Company company = new Company();
		try (Statement statement = connection.createStatement()) {
			String sql = "SELECT * FROM Company WHERE ID=" + companyId;
			ResultSet resultSet = statement.executeQuery(sql);
			resultSet.next();
			company.setCompanyId(resultSet.getLong(1));
			company.setCompanyName(resultSet.getString(2));
			company.setCompanyPassword(resultSet.getString(3));
			company.setCompanyEmail(resultSet.getString(4));

		} catch (SQLException e) {
			throw new Exception("unable to get Company data");
		} finally {
			connection.close();
		}
		return company;
	}

	//getAll query to Company table
	@Override
	public synchronized List<Company> getAllCompanies() throws Exception {

		connection = DriverManager.getConnection(DataBase.getConnectionString());
		List<Company> list = new ArrayList<>();
		String sql = "select * from Company";
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong(1);
				String companyName = resultSet.getString(2);
				String companyPassword = resultSet.getString(3);
				String companyEmail = resultSet.getString(4);

				list.add(new Company(companyId, companyName, companyPassword, companyEmail));

			}

		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("unable to get Company data");
		} finally {
			connection.close();
		}
		return list;

	}
}
