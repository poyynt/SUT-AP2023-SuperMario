package ir.sharif.math.ap2023.supermario.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;

public class HashedPassword {
    private final int saltLength = 8; // salt length in bytes
    private byte[] salt;
    private byte[] saltedPasswordHash;

    public HashedPassword(String cleartextPassword) {
        this.salt = new byte[this.saltLength];

        SecureRandom secureRandomGenerator = new SecureRandom();
        secureRandomGenerator.nextBytes(this.salt);

        byte[] passwordBytes = cleartextPassword.getBytes(StandardCharsets.UTF_8);

        this.saltedPasswordHash = this.getHash(passwordBytes);
    }

    public boolean validate(String password) {
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);

        byte[] passwordHash = this.getHash(passwordBytes);

        if (passwordHash.length != this.saltedPasswordHash.length)
            return false;

        assert passwordHash.length == this.saltedPasswordHash.length;
        for (int i = 0; i < passwordHash.length; i++)
            if (passwordHash[i] != this.saltedPasswordHash[i])
                return false;
        return true;
    }

    private byte[] getHash(byte[] message) {
        byte[] toHash = new byte[this.salt.length + message.length];
        System.arraycopy(this.salt, 0, toHash, 0, this.salt.length);
        System.arraycopy(message, 0, toHash, this.salt.length, message.length);

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] passwordHash = digest.digest(toHash);
            return passwordHash;
        } catch (NoSuchAlgorithmException x) {
            return message;
        }
    }
}
