package JavaBeans;

public enum CouponType {

	RESTURANTS
	{
		@Override
	    public String toString() {
	      return "Resturants";
		}
	},
	
	FOOD
	{
		@Override
	    public String toString() {
	      return "Food";
		}
	},
	
	ELECTRICITY
	{
		@Override
	    public String toString() {
	      return "Electricity";
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
	
	CAMPING
	{
		@Override
	    public String toString() {
	      return "Camping";
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
