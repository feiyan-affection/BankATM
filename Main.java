import bank.Bank;
import ui.LoginGeneric;

public class Main {
	public static void main(String Args[]) throws Exception {
		Bank services = new Bank();
		
		LoginGeneric gLogin = new LoginGeneric("Welcome to ATM", services);
		gLogin.show();
	}
}
