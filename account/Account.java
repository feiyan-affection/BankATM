package account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import data.Currency;
import data.Dollar;

public abstract class Account {
	protected Map <Dollar, Currency> deposit;
	public Account(Currency initialMoney) {
		deposit = new HashMap<Dollar, Currency>();
		save(initialMoney);
	}
	
	public boolean save(Currency in) {
		// how dare you save a negative number ???
		if (in.getAmount() > 0) {
			Currency tmp = new Currency(0, in.getType());
			if (deposit.containsKey(in.getType())) {
				tmp = deposit.get(in.getType());
			} 
			deposit.put(in.getType(), in.add(tmp));
			return true;
		}
		System.out.println("[Account save] save an negative number");
		return false;
	}
	
	public boolean transfer(Currency out) {
		// before you make any transfer, you should 
		// ensure you got such a currency account
		// and your balance at that account should >= than 
		// the amount you are trying to take out
		if (deposit.containsKey(out.getType()) 
				&& deposit.get(out.getType()).getAmount() >= out.getAmount()) {
			Currency tmp = deposit.get(out.getType());
			Currency result = tmp.minus(out);
			if (result.getAmount() <= 0) {
				deposit.remove(out.getType());
			} else {
				deposit.put(out.getType(), tmp.minus(out));
			}
			return true;
		} else {
			System.out.println("[Account transfer] my account does not get such currency type");
			System.out.println("[Account transfer] my account does not get enough money");
		} 		
		return false;
	}
	
	public String getDeposit() {
		String res = "";
		for (Map.Entry<Dollar, Currency> entry : deposit.entrySet()) {
			res += entry.getKey().toString();
			res += ":  ";
			res += entry.getValue().getAmount();
			res += "\n";
		}
		return res;
	}
	
	public ArrayList<Dollar> getDollars() {
		ArrayList<Dollar> res = new ArrayList<Dollar>();
		for (Map.Entry<Dollar, Currency> entry : deposit.entrySet()) {
			res.add(entry.getKey());
		}
		return res;
	}
	
	public Map<Dollar, Currency> getDepositMap() {
		return this.deposit;
	}
	abstract String getAccountType();
}
