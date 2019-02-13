package Facades;

/**
 * @author Tal Yamin
 *
 */

import Client.ClientType;

//interface which include login method

public interface CouponClientFacade {

	void login(String name, String password, ClientType clientType) throws Exception;
	
	
}
