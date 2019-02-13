package Client;

/**
 * @author Tal Yamin
 *
 */


//Enum which define three kinds of client type

public enum ClientType {
	
	ADMIN
	{
		@Override
	    public String toString() {
	      return "Admin";
		}
	},
	COMPANY
	{
		@Override
	    public String toString() {
	      return "Compnay";
		}
	},
	CUSTOMER
	{
		@Override
	    public String toString() {
	      return "Customer";
		}
	};

}
