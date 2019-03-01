package bank;

import java.util.HashMap;
import java.util.Map;

import data.Currency;
import data.Dollar;
import data.Loans;
import user.Customer;

public class Interest {
	private final static long BOUNDARY_FOR_POOR = 200;
	private final static long BOUNDARY_FOR_RICH = 100000;
	private final static Dollar STANDARD_TYPE = Dollar.USD;
	private final static double RATE_FOR_POOR = 0.02;
	private final static double RATE_FOR_RICH = 0.01;
	
	private final static double RATE_FOR_LOAN = 0.1;

	
	public static Currency getInterest(Customer customer, String account) {
		Map<Dollar, Currency> m = customer.getAccountDeposit(account);
		if (m.containsKey(STANDARD_TYPE) && m.get(STANDARD_TYPE).getAmount() > BOUNDARY_FOR_RICH) {
			return new Currency(new Integer((int) (m.get(STANDARD_TYPE).getAmount() * RATE_FOR_RICH)), 
					STANDARD_TYPE);
		} 
		
		return new Currency(0, Dollar.MISS_MATCH);
	}
	
	public static boolean isPoor(Customer customer, String account) {
		Map<Dollar, Currency> m = customer.getAccountDeposit(account);
		
		// check if this account of customer possess enough money to get rid of 
		// label of the poor
		// only check USD at this time
		if (m.containsKey(STANDARD_TYPE) && m.get(STANDARD_TYPE).getAmount() < BOUNDARY_FOR_POOR) {
			return true;
		}
		return false;
	}
	
	public static boolean isRich(Customer customer, String account) {
		Map<Dollar, Currency> m = customer.getAccountDeposit(account);

		if (m.containsKey(STANDARD_TYPE) && m.get(STANDARD_TYPE).getAmount() > BOUNDARY_FOR_RICH) {
			return true;
		}
		return false;

	}
	
	public static Map<Dollar, Currency> interestForLoan(Customer customer) {
		Loans l = customer.getLoans();
		Map<Dollar, Currency> target = l.getMap();
		Map<Dollar, Currency> res = new HashMap<Dollar, Currency>();
		for (Map.Entry<Dollar, Currency> entry : target.entrySet()) {
			Dollar key = entry.getKey();
			Currency c = entry.getValue();
			Currency out = new Currency(new Integer((int) (c.getAmount() * RATE_FOR_LOAN)), key);
			res.put(key, out);
		}
		return res;
	}
}
