package demo;

import java.util.regex.Pattern;

public class ValidateEmail {
	private static String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

	public static boolean isValidPattern(String email) {

		return Pattern.matches(regex, email);
	}

}
