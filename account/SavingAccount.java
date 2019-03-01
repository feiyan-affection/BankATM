package account;

import data.Currency;

public class SavingAccount extends Account{

	public SavingAccount(Currency initialMoney) {
		super(initialMoney);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getAccountType() {
		// TODO Auto-generated method stub
		return "Saving";
	}
	
}
