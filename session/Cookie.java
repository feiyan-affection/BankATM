package session;

public class Cookie {
	private String username;
	private String password;
	private String UserType;
	private int key;
	
	public Cookie(String user, String pwd, String type) {
		this.username = user;
		this.password = pwd;
		this.UserType = type;
	}
	
	public int getkey() {
		return this.key;
	}
	
	public void setkey(int key) {
		this.key = key;
	}
	
	public String getUser() {
		return this.username;
	}
	
	public String getType() {
		return this.UserType;
	}
}
