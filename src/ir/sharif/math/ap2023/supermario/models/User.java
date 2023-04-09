package ir.sharif.math.ap2023.supermario.models;

import ir.sharif.math.ap2023.supermario.utils.HashedPassword;

public class User {
    private final String username;
    private final HashedPassword hashedPassword;

    public User(String username, String password) {
        this.username = username;
        this.hashedPassword = new HashedPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public boolean validatePassword(String password) {
        return this.hashedPassword.validate(password);
    }
}
