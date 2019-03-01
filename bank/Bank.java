package bank;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import data.Currency;
import data.Dollar;
import data.Report;
import data.Transaction;
import data.TransactionType;
import session.Cookie;
import user.Admin;
import user.Customer;

public class Bank {
	private Map<String, Customer> customers;
	private Map<String, Admin> stuffs;
	private Map<String, Customer> hasloans;
	private Map<Dollar, Currency> profits;
	private final long MINIMUM_REQUIREMENT = 4;
//	private ArrayList<Transaction> transactions;
	
	public Bank() {
		registerServices();
	}
	private void registerServices() {
		// save users
		customers = new HashMap<String, Customer>();
		stuffs = new HashMap<String, Admin>();
		hasloans = new HashMap<String, Customer>();
		profits = new HashMap<Dollar, Currency>();
//		transactions = new ArrayList<Transaction>();
		
		// add two customers and one stuff for testing
		insertTestcase();
	}
	
	private void insertTestcase() {
		Customer c1 = new Customer("witchcraft", "IamHungry");
		c1.createSavingAccount(new Currency(1000, Dollar.USD));
		c1.createCheckingAccount(new Currency(1000, Dollar.EUR));
		
		Customer c2 = new Customer("bankruptcy", "lackofmoney");
		c2.createSavingAccount(new Currency(50, Dollar.USD));
		c2.createCheckingAccount(new Currency(50, Dollar.CNY));
		
		// faked transaction records
//		transactions.add(new Transaction(
//				TransactionType.SAVE, "witchcraft", "witchcraft", new Currency(50, Dollar.AUD), new Date(), true));
//		transactions.add(new Transaction(
//				TransactionType.TRANSFER, "witchcraft", "witchcraft", new Currency(500, Dollar.USD), new Date(), true));
//		transactions.add(new Transaction(
//				TransactionType.REQLOAN, "bankruptcy", "bankruptcy", new Currency(100, Dollar.CNY), new Date(), true));
//		transactions.add(new Transaction(
//				TransactionType.SAVE, "bankruptcy", "bankruptcy", new Currency(50, Dollar.USD), new Date(), true));
//		transactions.add(new Transaction(
//				TransactionType.TRANSFER, "bankruptcy", "witchcraft", new Currency(500, Dollar.CAD), new Date(), true));
//		transactions.add(new Transaction(
//				TransactionType.REQLOAN, "bankruptcy", "bankruptcy", new Currency(100, Dollar.EUR), new Date(), true));
		customers.put(c1.getUsername(), c1);
		customers.put(c2.getUsername(), c2);
	}
	
	private boolean auth(Cookie cookie) {
		// TODO: authenticate validity of this cookie
		return true;
	}
	
	private void chargeServicefee(Customer c, String account, long amount, Dollar type) {
		 ServiceFee fee = new ServiceFee(amount, type);
		 ArrayList<Dollar> list = c.getAllDollars(account);
		 for (Dollar d : list) {
			 if (c.transfer(fee.exchangeTo(d), account)) {
				 // charge success, revenue of bank increase
				 addToProfit(fee.exchangeTo(d));
				 break;
			 }
		 }
	}
	
	private void addToProfit(Currency c) {
		if (this.profits.containsKey(c.getType())) {
			Currency here = this.profits.get(c.getType());
			this.profits.put(c.getType(), here.add(c));
		} else {
			this.profits.put(c.getType(), c);
		}
	}
	
	private void takeoutProfit(Currency c) {
		// bc I am a banker
		// I can have debt
		// the amount of money can be negative
		Currency here = new Currency(0, c.getType());
		if (this.profits.containsKey(c.getType())) {
			here = this.profits.get(c.getType());
		} 
		this.profits.put(c.getType(), here.minus(c));
	}
	
	public boolean create(Currency money, Cookie cookie, String type) {
		// create account need service fee
		// if initial money is less than than that amount, create fail
		if (money.getAmount() < MINIMUM_REQUIREMENT) { return false; }
		boolean res = true;
		if (auth(cookie) && cookie.getType().equals("Customer")) {
			switch(type) {
			case "Checking":
				res = createChecking(money, cookie);
				// charge fee
				chargeServicefee(customers.get(cookie.getUser()), type, MINIMUM_REQUIREMENT/2, Dollar.USD);
				break;
			case "Saving":
				res = createSaving(money, cookie);
				// charge fee
				chargeServicefee(customers.get(cookie.getUser()), type, MINIMUM_REQUIREMENT/2, Dollar.USD);				break;
			default:
				System.out.println("[Bank create] miss match account type");
				res = false;
			}
		} else {
			res = false;
		}
		return res;

	}
	
	private boolean createSaving(Currency money, Cookie cookie) {
		if (auth(cookie) && cookie.getType().equals("Customer")) {
			Customer c = customers.get(cookie.getUser());
			c.createSavingAccount(money);
			return true;
		}
		System.out.println("[Bank createSaving] authenticate fail");
		return false;
	}
	private boolean createChecking(Currency money, Cookie cookie) {
		if (auth(cookie) && cookie.getType().equals("Customer")) {
			Customer c = customers.get(cookie.getUser());
			c.createCheckingAccount(money);
			return true;
		}
		System.out.println("[Bank createChecking] authenticate fail");
		return false;
	}
	
	// save money into my own account
	public boolean save_session(Currency money, Cookie cookie, String account) {
		boolean res = false;
		if (auth(cookie) && cookie.getType().equals("Customer")) {
			Customer customer = customers.get(cookie.getUser());
			if (customer.has(account)) {
				res = customer.save(money, account);
			}
			customer.savetransaction(new Transaction(
					TransactionType.SAVE, cookie.getUser(), cookie.getUser(), money, new Date(), res));
		} else {
			System.out.println("[Bank save_session] authenticate fail");
		}
//		transactions.add(new Transaction(
//				TransactionType.SAVE, cookie.getUser(), cookie.getUser(), money, new Date(), res));
		
		return res;
	}
	
	// internal call
	private boolean save(Currency money, String username, String account) {
		if (customers.containsKey(username)) {
			Customer customer = customers.get(username);
			if (customer.has(account)) {
				return customer.save(money, account);
			}
		} else {
			System.out.println("[Bank save] do not have this user");
		}
		return false;
	}
	
	public boolean hasChecking(Cookie cookie) {
		if (auth(cookie) && cookie.getType().equals("Customer")) {
			Customer customer = customers.get(cookie.getUser());
			return customer.has("Checking");
		}
		System.out.println("[Bank hasChecking] authenticate fail");
		return false;
	}
	
	public boolean hasSaving(Cookie cookie) {
		if (auth(cookie) && cookie.getType().equals("Customer")) {
			Customer customer = customers.get(cookie.getUser());
			return customer.has("Saving");
		}
		System.out.println("[Bank hasSaving] authenticate fail");
		return false;	
	}
	
	public String viewAccount(Cookie cookie) {
		if (auth(cookie) && cookie.getType().equals("Customer")) {
			Customer customer = customers.get(cookie.getUser());
			return customer.getAccountInfo();
		}
		return "";
	}
	
	public String viewTransactions(Cookie cookie) {
		if (auth(cookie) && cookie.getType().equals("Customer")) {
			Customer customer = customers.get(cookie.getUser());
			return customer.getTransInfo();
		}
		return "";
	}
	
	// takeout money from mine and send to others
	public boolean transfer(Currency money, Cookie cookie, String account, String to, String to_account) {
		boolean res = false;
		if (auth(cookie) && cookie.getType().equals("Customer")) {
			Customer customer = customers.get(cookie.getUser());
			if (customer.has(account)) {
				// try to pay, but would fail because of lack of enough money
				res =  customer.transfer(money, account);
				if (res) {
					res = save(money, to, to_account);
					if (!res) {
						// return money
						save(money, cookie.getUser(), account);
					} 
				}
			}
			customer.savetransaction(new Transaction(
					TransactionType.TRANSFER, cookie.getUser(), to, money, new Date(), res));
			if (account.equals("Checking")) {
				// whenever transfer money from checking, charge service fee
				chargeServicefee(customer, "Checking", MINIMUM_REQUIREMENT/2, Dollar.USD);
			}
		} else {
			System.out.println("[Bank transfer] authenticate fail");
		}
		return res;
	}
	
	public boolean requestLoans(Currency money, Cookie cookie) {
		boolean res = false;
		if (auth(cookie) && cookie.getType().equals("Customer")) {
			Customer customer = customers.get(cookie.getUser());
			if (customer.has("Checking")) {
				res = customer.reqloans(money);
			}
			if (res && !this.hasloans.containsKey(cookie.getUser())) {
				this.hasloans.put(cookie.getUser(), customer);
			}
			customer.savetransaction(new Transaction(
					TransactionType.REQLOAN, cookie.getUser(), cookie.getUser(), money, new Date(), res));
		} else {
			System.out.println("[Bank requestLoans] authenticate fail");
		}
		
//		transactions.add(new Transaction(
//				TransactionType.REQLOAN, cookie.getUser(), cookie.getUser(), money, new Date(), res));
		return res;
	}
	
	public boolean takeoutLoans(Currency money, Cookie cookie) {
		boolean res = false;
		if (auth(cookie) && cookie.getType().equals("Customer")) {
			Customer customer = customers.get(cookie.getUser());
			if (customer.has("Checking")) {
				res = customer.takeoutloans(money);
				if (res && (this.hasloans.containsKey(cookie.getUser()) && !customer.has("Loans"))) {
					this.hasloans.remove(cookie.getUser());
				}
			}
			customer.savetransaction(new Transaction(
					TransactionType.PAYLOAN, cookie.getUser(), cookie.getUser(), money, new Date(), res));
		} else {
			System.out.println("[Bank takeoutLoans] authenticate fail");
		}
		
//		transactions.add(new Transaction(
//				TransactionType.TAKEOUT, cookie.getUser(), cookie.getUser(), money, new Date(), res));
		return res;
	}
	
	public boolean login(String username, char[] pwd, String type) {
		boolean res = true;
		switch(type) {
		case "Customer":
			res = customers.containsKey(username);
			if (res && customers.get(username).getPassword().equals(new String(pwd))) {
				return true;
			}
			return false;
		case "Admin":
			res = stuffs.containsKey(username);
			if (res && stuffs.get(username).getPassword().equals(new String(pwd))) {
				return true;
			} 
			return false;
		default:
			System.out.println("[Bank login] miss match type");
			return false;
		}
	}
	
	public boolean register(String username, char[] pwd, String type) {
		boolean res = true;
		res = !customers.containsKey(username) && !stuffs.containsKey(username);
		if (!res) {
			return res;
		}
		// create user
		switch(type) {
		case "Customer":
			customers.put(username, new Customer(username, new String(pwd)));
			break;
		case "Admin":
			stuffs.put(username, new Admin(username, new String(pwd)));
			break;
		default:
			System.out.println("[Bank register] miss match any type");
		}
		return res;
	}
	
	
	// Admin methods below ...

	public String collectReport(Cookie cookie) {
		String res = "";
		if (auth(cookie) && cookie.getType().equals("Admin")) {
			res += " **** Daily Report **** \n";
			Report r = new Report(this.customers);
			res += r.getReport();
		}
		return res;
	}	
	
	
	public String collectAllPoorGuys(Cookie cookie) {
		String res = "";
		if (auth(cookie) && cookie.getType().equals("Admin")) {
			res += " **** Guys have debt **** \n";
			res += "\n";
			
			for (Map.Entry<String, Customer> entry : hasloans.entrySet()) {
				Customer c = entry.getValue();
				res += "[" + c.getUsername() + "] \n";
				res += c.getAccountInfo();
				res += "\n";
			}
			
		}
		return res;
	}
	
	public String spyOneclient(String username, Cookie cookie) {
		String res = "";
		if (auth(cookie) && cookie.getType().equals("Admin")) {
			res += " **** INFO **** \n";
			res += "\n";
			if (customers.containsKey(username)) {
				Customer customer = customers.get(username);
				res += customer.getAccountInfo();
			} else {
				res += "404 not found :)";
			}
			
		}
		return res;
	}
	
	public String viewProfit(Cookie cookie) {
		String res = "";
		if (auth(cookie) && cookie.getType().equals("Admin")) {
			chargeInerest();
			res += " **** Profit **** \n";
			res += "\n";
			for (Map.Entry<Dollar, Currency> entry : profits.entrySet()) {
				res += entry.getKey().toString();
				res += ":  ";
				res += entry.getValue().getAmount();
				res += "\n";
			}
		}
		return res;
	}
	
	private void chargeInerest() { 			
		for (Map.Entry<String, Customer> c : customers.entrySet()) {
			Customer customer = c.getValue();
			// pay interest
			payinterest(customer, "Saving");
				
			// charge interest
			if (customer.has("Loans")) {
				Map<Dollar, Currency> minus = Interest.interestForLoan(customer);	
				chargefromloan(minus, customer);
			}
		}
		return;
	}
	
	private void payinterest(Customer customer, String account) {
		if (Interest.isRich(customer, account)) {
			// sad ! need to pay for them
			customer.save(Interest.getInterest(customer, account), account);
			this.takeoutProfit(Interest.getInterest(customer, account));
		} 
	}
	
	private void chargefromloan(Map<Dollar, Currency> out, Customer customer) {
		// wowowowow earn money hahaha
		for (Map.Entry<Dollar, Currency> entry : out.entrySet()) {
			customer.addloans(entry.getValue());
			this.addToProfit(entry.getValue());
		}
	}
}
