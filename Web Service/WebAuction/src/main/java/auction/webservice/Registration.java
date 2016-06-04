package auction.webservice;

import auction.domain.User;
import auction.service.RegistrationMgr;

import javax.jws.WebService;

/**
 * Created by guushamm on 04/06/16.
 */
@WebService
public class Registration {
	private RegistrationMgr registrationMgr;

	public Registration() {
		registrationMgr = new RegistrationMgr();
	}

	public User registerUser(String email){
		return registrationMgr.registerUser(email);
	}

	public User getUser(String email){
		return registrationMgr.getUser(email);
	}

	public void clean(){
		registrationMgr.clean();
	}
}
