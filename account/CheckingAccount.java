package account;

import data.Currency;
import data.Dollar;
import data.Loans;

public class CheckingAccount extends Account{
	private Loans loans;
	
	public CheckingAccount(Currency initialMoney) {
		super(initialMoney);
		// TODO Auto-generated constructor stub
		loans = new Loans();
	}

	@Override
	public String getAccountType() {
		// TODO Auto-generated method stub
		return "Checking";
	}
	
	public void addloans(Currency loan) {
		loans.add(loan);
	}
	
	public boolean takeoutloans(Currency pay) {
		// if my checking account >= pay
		boolean res = true;
		if (deposit.containsKey(pay.getType()) 
				&& deposit.get(pay.getType()).getAmount() >= pay.getAmount() ) {
			// maximum could be taken of
			Currency c = loans.takeout(pay);
			if (c.getType() != Dollar.MISS_MATCH) {
				this.transfer(c);
			}
		} else {
			System.out.println("[CheckingAccount takeoutloans] no enough money");
			res = false;
		}
		return res;
	}
	public Loans getloan() {
		return this.loans;
	}
	public String getLoans() {
		String res = "";
		if (loans.hasloan()) {
			res += "**** Loans Info **** \n";
			res += loans.getInfo();
		} else {
			res += "No loans so far. \n";
		}
		return res;
	}
	
	public boolean hasloans() {
		return loans.hasloan();
	}

}
