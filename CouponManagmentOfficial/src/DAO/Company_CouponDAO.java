package DAO;

import java.util.List;

import JavaBeans.Company;
import JavaBeans.Coupon;



/**
 * @author Shay Ben Haroush
 *
 */

public interface Company_CouponDAO {
	
	/*
	 * Interface - queries for Company_Coupon table. 
	 * Methods which will be overridden in Company_Coupon DBDAO class.
	 */

	void insertCompany_Coupon(Company company, Coupon coupon) throws Exception;

	void removeCompany_Coupon(Company company, Coupon coupon) throws Exception;

	void removeCompany_Coupon(Company company) throws Exception;
	
	void removeCompany_Coupon(Coupon coupon) throws Exception;
	
	List<Long> getCompanyId(long couponId) throws Exception;

	List<Long> getAllCompaniesId() throws Exception;
	
	List<Long> getCouponId (long companyId) throws Exception;
	
	List<Long> getAllCouponsId() throws Exception;
}
