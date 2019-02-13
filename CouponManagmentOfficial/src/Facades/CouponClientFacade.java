package Facades;

import Client.ClientType;

public interface CouponClientFacade {

	void login(String name, String password, ClientType clientType) throws Exception;
	
	
}
