package supermario.logic;

import supermario.models.State;
import supermario.models.User;

public class AuthenticationHandler {
    public static RegistrationResult registerNewUser(String username, String password) {
        User user = State.getUserByName(username);
        if (user != null)
            return RegistrationResult.ERROR_USERNAME_EXISTS;
        assert username.length() == 0;
        assert password.length() == 0;

        user = new User(username, password);
        boolean result = State.addNewUser(user);

        if (!result)
            return RegistrationResult.ERROR_UNKNOWN;

        return RegistrationResult.SUCCESS;
    }

    public static LoginResult login(String username, String password) {
        User user = State.getUserByName(username);
        if (user == null || !user.validatePassword(password))
            return LoginResult.ERROR_INCORRECT_CREDENTIALS;

        State.setCurrentUser(user);

        return LoginResult.SUCCESS;
    }

    public static void logout() {
        State.setCurrentUser(null);
    }
}
