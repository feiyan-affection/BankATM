package data;

import java.util.Date;

public class Transaction {
	private TransactionType type;
	private String remittance;
	private String reciver;
	private Currency amount;
	private Date date;
	private long hashkey;
	private boolean result;
	
	
	public Transaction(Transaction other) {
		this.type = other.getType();
		this.remittance = other.getRemittance();
		this.reciver = other.getReciver();
		this.amount = other.getAmount();
		this.date = other.getDate();
		this.result = other.getResult();
		this.hashkey = other.getUniqueKey();
	}
	
	public Transaction(TransactionType type, String from, String to, Currency amount, Date date, boolean result) {
		this.type = type;
		this.remittance = from;
		this.reciver = to;
		this.amount = amount;
		this.date = date;
		this.result = result;
		this.hashkey = this.make_hashkey();
	}
	
	
	private long make_hashkey() {
		// TO DO
		return 1;
	}
	
	public TransactionType getType() {
		return this.type;
	}
	public String getRemittance() {
		return this.remittance;
	}
	public String getReciver() {
		return this.reciver;
	}
	public Currency getAmount() {
		return this.amount;
	}
	public Date getDate() {
		return this.date;
	}
	public long getUniqueKey() {
		return this.hashkey;
	}
	public boolean getResult() {
		return this.result;
	}
}
