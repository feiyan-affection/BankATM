package session;

public class MakeCookie {
	public static Cookie res;
	public static Cookie makeCookie(String username, String password, String usertype) {
		res = new Cookie(username, password, usertype);
		
		// use SHA - 256 to improve
		int key = 0;
		for (char c : username.toCharArray()) {
			key = key * 10 + (c - 'a');
		}
		res.setkey(key);
		return res;
	}
}
