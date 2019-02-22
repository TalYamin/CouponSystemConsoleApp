package JavaBeans;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Shay Ben Haroush
 *
 */

public class Coupon {
	
	//data members of Coupon
	private long couponId;
	private String title;
	private LocalDate startDate;
	private LocalDate endDate;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
	private int amount;
	private CouponType type;  
	private String couponMessage;
	private double price;
	private String image;
	private String customStartDate;
	private String customeEndDate;
	
	
	//empty CTOR of Coupon
	public Coupon(){
		
	}
	
	
	//full CTOR of Coupon
	public Coupon(long couponId, String title, String endDate, int amount, CouponType type,
			String couponMessage, double price, String image) {
		this.couponId = couponId;
		this.title = title;
		this.startDate = LocalDate.now();
		LocalDate endLocalDate = LocalDate.parse(endDate, this.formatter);
		this.endDate = endLocalDate;
		this.amount = amount;
		this.type = type;
		this.couponMessage = couponMessage;
		this.price = price;
		this.image = image;
	}

	
	//getters and setters methods of Coupon
	public long getCouponId() {
		return this.couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getStartDate() {
		return this.startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return this.endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public CouponType getType() {
		return this.type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	public String getCouponMessage() {
		return this.couponMessage;
	}

	public void setCouponMessage(String couponMessage) {
		this.couponMessage = couponMessage;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	//toString method of Coupon
	@Override
	public String toString() {
		
		customStartDate = this.startDate.format(this.formatter);
		customeEndDate = this.endDate.format(this.formatter);
		
		return "Coupon [couponId=" + this.getCouponId() + ", title=" + this.getTitle() + ", startDate=" + this.customStartDate + ", endDate=" + this.customeEndDate
				+ ", amount=" + this.getAmount() + ", type=" + this.getType() + ", couponMessage=" + this.getCouponMessage() + ", price=" + this.getPrice()
				+ ", image=" + this.getImage() + "]";
	}
	
	
	
	
	
	

}