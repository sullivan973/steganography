//Author(s): Cassidy Murphy, Marley Stipich and Thomas Sullivan
//Derived from: https://dzone.com/articles/storing-passwords-java-web
package sec.info.stegchan.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordService {
  private static final String SALT_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-";
  private static final int SALT_LENGTH = 32;
  //A CSPRNG used to generate salts
  private static SecureRandom random = new SecureRandom();

  //generates hashed password using built in hashing alg. returns hex hash
  public static String generateHash(String password) throws NoSuchAlgorithmException {
    StringBuilder passwordHash = new StringBuilder();
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
    byte[] hashedBytes = messageDigest.digest(password.getBytes());
    char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};
    for (int idx = 0; idx < hashedBytes.length; ++idx) {
      byte b = hashedBytes[idx];
      passwordHash.append(digits[(b & 0xf0) >> 4]);
      passwordHash.append(digits[b & 0x0f]);
    }
    return passwordHash.toString();
  }

  //generates a new salt of length SALT_LENGTH using a CSPRNG
  public static String generateSalt()
  {
    StringBuilder salt = new StringBuilder(SALT_LENGTH);
    for(int i = 0; i < SALT_LENGTH; i++) {
      salt.append(SALT_CHARS.charAt(random.nextInt(SALT_CHARS.length())));
    }
    return salt.toString();
  }
}
