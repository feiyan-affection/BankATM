package data;

import java.util.HashMap;
import java.util.Map;

public class Loans {
	private Map<Dollar, Currency> l;
	public Loans() {
		l = new HashMap<Dollar, Currency>();
	}
	public Map<Dollar, Currency> getMap() {
		return l;
	}
	
	public void add(Currency other) {
		if (l.containsKey(other.getType())) {
			l.put(other.getType(), l.get(other.getType()).add(other));
		} else {
			l.put(other.getType(), other);
		}
	}
	
	public Currency takeout(Currency other) {
		Currency res;
		if (l.containsKey(other.getType()) ) {
			long leftamount = l.get(other.getType()).getAmount();
			long rightamount = other.getAmount();
			
			if (leftamount <= rightamount) {
				l.remove(other.getType());
			} else {
				l.put(other.getType(), new Currency(leftamount - rightamount, other.getType()));
			}
			res = new Currency(Math.min(leftamount, rightamount), other.getType());
		} else {
			res = new Currency(0, Dollar.MISS_MATCH);
		}
		return res;
	}
	
	public boolean hasloan() {
		if (l.size() == 0) {
			return false;
		}
		return true;
	}
	
	public String getInfo() {
		String res = "";
		for (Map.Entry<Dollar, Currency> entry : l.entrySet()) {
			res += entry.getKey().toString();
			res += ":  ";
			res += entry.getValue().getAmount();
			res += "\n";
		}
		return res;	
	}
}
