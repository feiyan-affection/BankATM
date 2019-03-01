package user;

public abstract class User {
	protected String username;
	protected String password;
	
	public User(String user, String password) {
		this.username = user;
		this.password = password;
	}
	
	public String getUserName() {
		return this.username;
	}
	public String getPassword() {
		return this.password;
	}
	
	abstract public String getUserType();
	 
}
