package Thread;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import DBDAO.Company_CouponDBDAO;
import DBDAO.CouponDBDAO;
import DBDAO.Customer_CouponDBDAO;
import JavaBeans.Coupon;

/**
 * @author Tal Yamin
 *
 */

public class DailyCouponExpirationTask implements Runnable {

	private CouponDBDAO couTask = new CouponDBDAO();
	private Company_CouponDBDAO com_couTask = new Company_CouponDBDAO();
	private Customer_CouponDBDAO cus_couTask = new Customer_CouponDBDAO();
	private Thread taskThread;

	public DailyCouponExpirationTask() {

	}

	public void start() throws Exception {
		try {
			this.taskThread = new Thread(this);
			this.taskThread.start();
			System.out.println("Daily Coupon Expiration Task starting now");
		} catch (Exception e) {
			throw new Exception("Daily Coupon Expiration Task failed starting");
		}
	}

	@Override
	public void run() {

		try {
			System.out.println("Daily Coupon Expiration Task running now");
			List<Coupon> coupons = couTask.getAllCoupons();
			Iterator<Coupon> i = coupons.iterator();
			while (i.hasNext()) {
				Coupon current = i.next();
				if (current.getEndDate().isBefore(LocalDate.now())) {
					couTask.removeCoupon(current);
					com_couTask.removeCompany_Coupon(current);
					cus_couTask.removeCustomer_Coupon(current);
				}

			}
		} catch (Exception e) {
			System.out.println("Daily Coupon Expiration Task failed running"); // there is no option to throw exception
		}

	}

	public void stopTask() throws Exception {
		try {
			this.taskThread.interrupt();
		} catch (Exception e) {
			throw new Exception("Daily Coupon Expiration Task failed stoping");
		}
	}

}
