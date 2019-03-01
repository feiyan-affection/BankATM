package data;

public class Currency {
	private long amount;
	private Dollar currencyType;
	public Currency(long number, Dollar type) {
		this.amount = number;
		this.currencyType = type;
	}
	
	public Dollar getType() {
		return this.currencyType;
	}
	
	public long getAmount() {
		return this.amount;
	}
	
	public Currency add(Currency other) {
		if (!other.getType().equals(this.getType())) {
			return new Currency(0, Dollar.MISS_MATCH);
		}
		return new Currency(other.getAmount() + this.getAmount(), this.getType());
	}
	
	public Currency minus(Currency other) {
		if (!other.getType().equals(this.getType())) {
			return new Currency(0, Dollar.MISS_MATCH);
		}
		return new Currency(this.getAmount() - other.getAmount(), this.getType());
	}
}
