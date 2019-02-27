package SystemUtils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import DAO.ExpiredCouponDAO;
import DBDAO.Company_CouponDBDAO;
import DBDAO.CouponDBDAO;
import DBDAO.Customer_CouponDBDAO;
import DBDAO.ExpriedCouponDBDAO;
import Exceptions.DailyTaskException;
import JavaBeans.Coupon;

/**
 * @author Tal Yamin
 *
 */

public class DailyCouponExpirationTask implements Runnable {

	private boolean b = true;
	private CouponDBDAO couTaskDAO = new CouponDBDAO();
	private Company_CouponDBDAO com_couTaskDAO = new Company_CouponDBDAO();
	private Customer_CouponDBDAO cus_couTaskDAO = new Customer_CouponDBDAO();
	private ExpriedCouponDBDAO exp_couTaskDAO = new ExpriedCouponDBDAO();
	private Thread taskThread;

	public DailyCouponExpirationTask() {

	}

	public void startTask() throws Exception {
		try {

			taskThread = new Thread(this);
			taskThread.start();
			System.out.println("Daily Coupon Expiration Task starting now");
		} catch (Exception e) {
			throw new DailyTaskException("Daily Coupon Expiration Task failed starting");
		}
	}

	@Override
	public void run() {

		try {
			System.out.println("Daily Coupon Expiration Task running now");

			while (b) {
				List<Coupon> coupons = couTaskDAO.getAllCoupons();
				Iterator<Coupon> i = coupons.iterator();
				while (i.hasNext()) {
					Coupon current = i.next();
//					if (current.getEndDate().isBefore(LocalDate.now())) {
//						couTaskDAO.updateNoActiveCoupon(current);
//					}
				}
				List<Coupon> couponsUpdated = couTaskDAO.getAllCoupons();
				System.out.println(couponsUpdated);
				Iterator<Coupon> it = couponsUpdated.iterator();
				while (it.hasNext()) {
					Coupon currentUpdated = it.next();
					if (currentUpdated.isActive() == false) {
						exp_couTaskDAO.insertCoupon(currentUpdated);
						couTaskDAO.removeCoupon(currentUpdated);
						com_couTaskDAO.removeCompany_Coupon(currentUpdated);
						cus_couTaskDAO.removeCustomer_Coupon(currentUpdated);
					}

				}

				taskThread.sleep(2000);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Daily Coupon Expiration Task failed running"); // there is no option to throw exception
		}

	}

	public void stopTask() throws Exception {
		try {
			b = false;
			if (!taskThread.isAlive()) {
				taskThread.interrupt();
			}
			System.out.println("Daily Coupon Expiration Task stopped");
		} catch (Exception e) {
			throw new DailyTaskException("Daily Coupon Expiration Task failed stoping");
		}
	}

}
