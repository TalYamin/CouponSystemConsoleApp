package DAO;

import java.util.List;

import JavaBeans.Company;
import JavaBeans.Coupon;



/**
 * @author Tal Yamin
 *
 */

public interface Company_CouponDAO {
	
	//interface - queries from Company_Coupon table

	void insertCompany_Coupon(Company company, Coupon coupon) throws Exception;

	void removeCompany_Coupon(Company company, Coupon coupon) throws Exception;

	void removeCompany_Coupon(Company company) throws Exception;
	
	void removeCompany_Coupon(Coupon coupon) throws Exception;
	
	List<Long> getCompanyId(long couponId) throws Exception;

	List<Long> getAllCompaniesId() throws Exception;
	
	List<Long> getCouponId (long companyId) throws Exception;
	
	List<Long> getAllCouponsId() throws Exception;
}
