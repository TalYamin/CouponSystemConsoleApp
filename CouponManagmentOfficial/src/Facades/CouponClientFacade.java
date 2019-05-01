package Facades;

import SystemUtils.ClientType;

//interface which include login method

public interface CouponClientFacade {

	CouponClientFacade login(String name, String password, ClientType clientType) throws Exception;
	
	
}
