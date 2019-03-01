package user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import account.Account;
import account.CheckingAccount;
import account.SavingAccount;
import data.Currency;
import data.Dollar;
import data.Loans;
import data.Transaction;

public class Customer extends User{
	private CheckingAccount checking;
	private SavingAccount saving;
	private ArrayList<Transaction> transactions;
	
	public Customer(String user, String password) {		
		super(user, password);
		checking = null;
		saving = null;
		transactions = new ArrayList<Transaction>();
	}
	
	public void savetransaction(Transaction t) {
		Transaction here = new Transaction(t);
		transactions.add(here);
	}
	
	public ArrayList<Transaction> gettransactions() {
		return this.transactions;
	}
	
	public void createSavingAccount(Currency initialMoney) {
		if (saving != null) {
			return;
		}
		saving = new SavingAccount(initialMoney);
	}
	
	public void createCheckingAccount(Currency initialMoney) {
		if (checking != null) {
			return;
		}
		checking = new CheckingAccount(initialMoney);
	}
	
	// loans bind to checking account
	public boolean reqloans(Currency money) {
		boolean res = false;
		if (checking != null) {
			checking.addloans(money);
			save(money, "Checking");
			res = true;
		} else {
			System.out.println("[Customer - reqloans] no checing account");
		}
		return res;
	}
	
	public boolean addloans(Currency money) {
		boolean res = false;
		if (checking != null) {
			checking.addloans(money);
			res = true;
		} else {
			System.out.println("[Customer - reqloans] no checing account");
		}
		return res;
	}

	
	public boolean takeoutloans(Currency money) {
		boolean res = false;
		if (checking != null) {
			res = checking.takeoutloans(money);
		} else {
			System.out.println("[Customer - reqloans] no checing account");
		}
		return res;
	}
	
	public boolean save(Currency money, String account) {
		boolean res = true;
		switch(account) {
		case "Checking":
			res = checking.save(money);
			break;
		case "Saving":
			res = saving.save(money);
			break;
		default:
			System.out.println("[Customer - save] Miss match input type");
			res = false;
		}
		return res;
	}
	
	public boolean transfer(Currency money, String account) {
		boolean res = true;
		switch(account) {
		case "Checking": 
			res = checking.transfer(money);
			break;
		case "Saving":
			res = saving.transfer(money);
			break;
		default:
			System.out.println("[Customer - transfer] Miss match input type");
			res = false;
		}
		return res;

	}
	
	public boolean has(String account) {
		boolean res = true;
		switch (account) {
		case "Checking":
			if (checking == null) { res = false; }
			break;
		case "Saving":
			if (saving == null) { res = false; }
			break;
		case "Loans":
			if (checking == null) { res = false; }
			else if (!checking.hasloans() ){ res = false; }
			break;
		default:
			System.out.println("[Customer - has] Miss match input type");
			res = false;
		}
		return res;
	}
	
	public Map<Dollar, Currency> getAccountDeposit(String account) {
		if (account.equals("Checking") && checking != null) {
			return checking.getDepositMap();
		} else if (account.equals("Saving") && saving != null) {
			return saving.getDepositMap();
		}
		
		return new HashMap<Dollar, Currency>();
	}
	
	public Loans getLoans() {
		Loans l = new Loans();
		if (checking != null) {
			l = checking.getloan();
		}
		
		return l;
	}
	
	public ArrayList<Dollar> getAllDollars(String account) {
		Account acc;
		switch(account) {
		case "Checking":
			acc = checking;
			break;
		case "Saving":
			acc = saving;
			break;
		default:
			System.out.println("[Customer getAllDollars] miss match account type");
			return new ArrayList<Dollar>();
		}
		return acc.getDollars();
	}
	
	public String getUserType() {
		return "Customer";
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getAccountInfo() {
		String res = "";
		res += getCheckingInfo();
		res += getSavingInfo();
		return res;
	}
	
	public String getTransInfo() {
		String res = "**** Transaction Info **** \n";
		res += "\n";
		res += "transaction type  |  currency  |  amount  |  from  | success \n";
		res += "\n";
		for (Transaction t : transactions) {
			String type = t.getType().toString();
			Currency money = t.getAmount();
			String from = t.getRemittance();
//			String to = current_t.getReciver();
			boolean ts = t.getResult();
			String success = "";
			if (ts) { success += "success"; }
			else { success += "fail"; }
			
//			String date = current_t.getDate().toString();
			res += type + " , " + money.getType().toString() + " , " + money.getAmount() +
					" , " + from + " , " + success;
			res += "\n";
		}
		return res;
	}
	
	private String getCheckingInfo() {
		String res = "";
		if (checking != null) {
			// deposit
			res += "**** Checking Account Info **** \n";
			res += checking.getDeposit();
			res += "\n";
			
			// if loans
			res += checking.getLoans();
			res += "\n";
		} else {
			res += "No checking account exists. \n";
			res += "\n";
		}
		return res;
	}
	private String getSavingInfo() {
		String res = "";
		if (saving != null) {
			res += "**** Saving Account Info **** \n";
			res += saving.getDeposit();
			res += "\n";
		} else {
			res += "No saving account exists. \n";
			res += "\n";
		}
		return res;

	}	
	
}
