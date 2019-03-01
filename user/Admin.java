package user;

public class Admin extends User{
	public Admin(String user, String password) {
		super(user, password);
		// TODO Auto-generated constructor stub
	}
	
	public int gethashkey() {
		return 1;
	}

	public String getUserType() {
		return "Admin";
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
}
