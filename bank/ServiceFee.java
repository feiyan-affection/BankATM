package bank;

import data.Currency;
import data.Dollar;

public class ServiceFee {
	private Currency base;
	public ServiceFee(long amount, Dollar type) {
		// how many need to be charge
		base = new Currency(amount, type);
	}
	
	public Currency exchangeTo(Dollar type) {
		// exchange rate
		// has't implemented yet
		// 1:1 exchange for testing
		return new Currency(base.getAmount(), type);
	}
}
