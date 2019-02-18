package DAO;

import java.util.List;

import JavaBeans.Company;

/**
 * @author Shay Ben Haroush
 *
 */

public interface CompanyDAO {
	
	/*
	 * Interface - queries for Company table.
	 * Methods which will be overridden in Company DBDAO class.
	 */
	
	void insertCompany(Company company) throws Exception;

	void removeCompany(Company company) throws Exception;

	void updateCompany(Company company) throws Exception;

	Company getCompany(long companyId) throws Exception;

	List<Company> getAllCompanies() throws Exception;


}
