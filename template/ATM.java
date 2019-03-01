package template;

import bank.Bank;

public class ATM {
	private Bank services;
	public ATM(Bank services) {
		// all UI should connect to the bankend services of bank
		this.services = services;
	}
	
	protected Bank getService() {
		return this.services;
	}
}
