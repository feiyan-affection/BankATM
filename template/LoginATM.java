package template;

import bank.Bank;
import session.Cookie;

public abstract class LoginATM extends ATM{
	private Cookie cookie;
	public LoginATM(Bank services, Cookie cookie) {
		super(services);
		// TODO Auto-generated constructor stub
		this.cookie = cookie;
	}
	
	protected Cookie getCookie() {
		return this.cookie;
	}
	
	abstract public void show();
}
