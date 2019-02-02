package DAO;

import java.util.List;

import JavaBeans.Company;

/**
 * @author Tal Yamin
 *
 */

public interface CompanyDAO {
	
	//interface - queries from Company table
	
	void insertCompany(Company company) throws Exception;

	void removeCompany(Company company) throws Exception;

	void updateCompany(Company company) throws Exception;

	Company getCompany(long companyId) throws Exception;

	List<Company> getAllCompanies() throws Exception;


}
