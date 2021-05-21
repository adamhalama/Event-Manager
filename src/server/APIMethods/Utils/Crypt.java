package server.APIMethods.Utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Crypt
{
  public static String encryptPassword(String password)
      throws GeneralSecurityException, IOException
  {
    String key = "/KGOySYqV17FBhCb3tTJgA==";
    return encrypt(password, Base64.getDecoder().decode(key));
  }
  private static String encrypt(String password, byte[] salt) throws
      GeneralSecurityException, UnsupportedEncodingException
  {
    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] hash = factory.generateSecret(spec).getEncoded();
    return base64Encode(hash);
  }
  public static String createKey() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    return base64Encode(salt);
  }
  private static String base64Encode(byte[] bytes) {
    return Base64.getEncoder().encodeToString(bytes);
  }
}
