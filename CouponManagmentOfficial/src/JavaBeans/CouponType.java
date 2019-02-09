package JavaBeans;

/**
 * @author Shay Ben Haroush
 *
 */

public enum CouponType {

	RESTURANTS
	{
		@Override
	    public String toString() {
	      return "Resturants";
		}
	},	
	
	HEALTH
	{
		@Override
	    public String toString() {
	      return "Health";
		}
	},
	
	SPORTS
	{
		@Override
	    public String toString() {
	      return "Sports";
		}
	},
	
	TRAVELING
	{
		@Override
	    public String toString() {
	      return "Traveling";
		}
	}
	
}
