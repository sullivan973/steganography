package sec.info.stegchan.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordService {
  private static final String SALT = "super-secure-please-ignore-&*sD#d*3";

  //generates hashed password with salt using built in hashing alg. returns hex hash
  public static String generateHash(String password) throws NoSuchAlgorithmException {
    StringBuilder passwordHash = new StringBuilder();
    //salt reuse isn't ideal but is good enough. Helps protect against rainbow table attacks
    password = SALT + password;
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
}
