package view;

public class PasswordValidator {

    public static boolean isValidPassword(String password) {
        // check password length
        if (password.length() < 8) {
            return false;
        }

        // check for at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // check for at least one lowercase letter
        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        // check for at least one digit
        if (!password.matches(".*\\d.*")) {
            return false;
        }

        // check for at least one special character
        if (!password.matches(".*[!@#$%^&*()_+=\\-\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            return false;
        }

        return true;
    }
}
