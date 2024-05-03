package spring.web;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Utility {
	public String encryptPassword(String password) {
		String encodedPass = new BCryptPasswordEncoder().encode(password);
		return encodedPass;
	}
	public boolean checkPass(String password, String encodedPass) {
		boolean isCorrect = new BCryptPasswordEncoder().matches(password, encodedPass);
		return isCorrect;
	}
}
