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

	/* Static connection to driver */
	private static Connection connection;

	/*
	 * Insert to Company table override method:
	 * This method receive 1 parameters: company.
	 * According to parameter, the SQL query is defined with 
	 * the company ID, name, password and email.
	 * This method receive connection to DB and create prepareStatement.
	 * Then SQL query for insert to table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
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
			throw new Exception("DB error - Company creation failed. companyId: "+ company.getCompanyId());
		}catch (Exception e) {
			throw new Exception("Company creation failed. companyId: "+ company.getCompanyId());
		} 
		finally {
			connection.close();
		}

	}

	/*
	 * Remove from Company table override method:
	 * This method receive 1 parameters: company.
	 * According to parameter, the SQL query is defined with the company ID.    
	 * This method receive connection to DB and create prepareStatement.
	 * Then SQL query for remove from table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
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
				throw new Exception("DB error - failed to remove Company. companyId: " + company.getCompanyId());
			}
			throw new Exception("failed to remove Company. companyId: " + company.getCompanyId());
		} finally {
			connection.close();
		} 

	}

	/*
	 * Update Company table override method:
	 * This method receive 1 parameters: company.
	 * According to parameter, the SQL query is defined with 
	 * the company ID, name, password and email.
	 * The updates only available for company name, password and email where the relevant ID. 
	 * This method receive connection to DB and create prepareStatement.
	 * Then SQL query for update table is executed. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
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
			throw new Exception("update Compnay failed. companyId: "+ company.getCompanyId());
		} finally {
			connection.close();
		}

	}

	/*
	 * Get company from Company table override method:
	 * This method receive 1 parameters: companyId. 
	 * There is generation of Company object which need to receive the data from table.
	 * According to parameter, the SQL query is defined with the company ID.    
	 * This method receive connection to DB and create statement.
	 * Then SQL query for get from table is executed. 
	 * There is resultSet which generated so it will be available to receive results from DB.
	 * There are setters methods of Company object which used in order to keep the results and to return the object. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
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
			throw new Exception("DB error - unable to get Company data. companyId: " + companyId);
		}catch (Exception e) {
			throw new Exception("unable to get Company data. companyId: " + companyId);
		} 
		finally {
			connection.close();
		}
		return company;
	}

	/*
	 * Get all companies list from Company table override method:
	 * There is generation of ArrayList which need to receive the data from table.
	 * the SQL query is defined for all data in table.   
	 * This method receive connection to DB and create statement.
	 * Then SQL query for get from table is executed. 
	 * There is resultSet which generated so it will be available to receive results from DB.
	 * There is function add of ArrayList which used in order to keep the results and to return the list. 
	 * If there is DB issue, SQLException is activated.
	 * Finally connection closed.
	 */
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
			throw new Exception("DB error - unable to get Company data");
		}catch (Exception e) {
			throw new Exception("unable to get Company data");
		} 
		finally {
			connection.close();
		}
		return list;

	}
}
