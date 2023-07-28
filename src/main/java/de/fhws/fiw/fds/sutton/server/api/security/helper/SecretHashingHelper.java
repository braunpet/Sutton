package de.fhws.fiw.fds.sutton.server.api.security.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * The SecretHashingHelper class provides utility methods for hashing and verifying passwords using SHA-512 algorithm with salt.
 * It generates a unique salt for each password and provides methods for storing and retrieving the salt.
 */
public class SecretHashingHelper {

    /**
     * Hashes the provided password using SHA-512 algorithm with the provided salt.
     *
     * @param passwordToHash The password to be hashed.
     * @param salt The salt to be used for hashing.
     * @return The hashed password as a hexadecimal string.
     */
    public static String hashPassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     * Generates a random salt using the SHA1PRNG algorithm.
     *
     * @return The generated salt as a byte array.
     * @throws NoSuchAlgorithmException if the SHA1PRNG algorithm is not available.
     */
    public static byte[] getSalt() {
        SecureRandom sr;
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * Converts the provided salt into a string using Base64 encoding.
     *
     * @param salt The salt to be converted.
     * @return The salt as a Base64-encoded string.
     */
    public static String saltToString(byte[] salt) {
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Converts the provided Base64-encoded string back into a salt.
     *
     * @param saltString The Base64-encoded salt string.
     * @return The salt as a byte array.
     */
    public static byte[] stringToSalt(String saltString) {
        return Base64.getDecoder().decode(saltString);
    }

    /**
     * Verifies whether the entered password matches the stored password hash by hashing the entered password
     * with the provided salt and comparing it to the stored password hash.
     *
     * @param enteredPassword    The password entered by the user.
     * @param storedPasswordHash The stored password hash to compare against.
     * @param salt The salt used for hashing the entered password.
     * @return true if the entered password matches the stored password hash, false otherwise.
     */
    public static boolean verifyPassword(String enteredPassword, String storedPasswordHash, byte[] salt) {
        String hashedEnteredPassword = hashPassword(enteredPassword, salt);
        return hashedEnteredPassword.equals(storedPasswordHash);
    }
}
