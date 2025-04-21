package application.data;

import java.util.HashMap;

public class UserData {
	private static HashMap<String, String> users = new HashMap<String, String>();
	
	public static boolean registerUser(String username, String password) {
		if(users.containsKey(username)) {
			return false;
		}
		users.put(username, password);
		return true;
	}
	
	
	public static boolean checkCredentials(String username, String password) {
		return users.containsKey(username) && users.get(username).equals(password);
	}
}
