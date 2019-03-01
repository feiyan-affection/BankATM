package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import user.Customer;

public class Report {
	private Map<String, Customer> allclients;
	public Report(Map<String, Customer> customers) {
		allclients = new HashMap<String, Customer>(customers);
	}
	
	public String getReport() {
		String res = "";
		res += "transaction type  |  currency  |  amount  |  from  | success \n";
		res += "\n";
		for (Map.Entry<String, Customer> c : allclients.entrySet()) {
			ArrayList<Transaction> thisguy = c.getValue().gettransactions();
			for (int i = 0; i < thisguy.size(); i++) {
				Transaction current_t = thisguy.get(i);
				String type = current_t.getType().toString();
				Currency money = current_t.getAmount();
				String from = current_t.getRemittance();
//				String to = current_t.getReciver();
				boolean ts = current_t.getResult();
				String success = "";
				if (ts) { success += "success"; }
				else { success += "fail"; }
				
//				String date = current_t.getDate().toString();
				res += type + " , " + money.getType().toString() + " , " + money.getAmount() +
						" , " + from + " , " + success;
				res += "\n";
			}
		}
		return res;
	}
}
