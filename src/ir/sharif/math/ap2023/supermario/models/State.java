package ir.sharif.math.ap2023.supermario.models;

import java.util.ArrayList;
import java.util.List;

public class State {
    private static List<User> allUsers = new ArrayList<>();
    private static User currentUser = null;
    private static GameState currentGame = null;

    public static User getUserByName(String username) {
        for (User u: allUsers)
            if (u.getUsername().equals(username))
                return u;
        return null;
    }

    public static boolean addNewUser(User u) {
        if (getUserByName(u.getUsername()) == null) {
            allUsers.add(u);
            return true;
        }
        else
            return false;
    }

    public static void loadAllUsers(List<User> users) {
        allUsers = users;
    }

    public static List<User> getAllUsers() {
        return allUsers;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        State.currentUser = currentUser;
    }

    public static GameState getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(GameState currentGame) {
        State.currentGame = currentGame;
    }
}
