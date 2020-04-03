package sec.info.stegchan;

import org.junit.jupiter.api.Test;
import sec.info.stegchan.service.PasswordService;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class PasswordServiceTest {

  //tests to see if the hash is repeatable for the same string
  @Test
  void hashIsRepeatable() throws NoSuchAlgorithmException {
    String input1 = "Hash me!";
    String output1 = PasswordService.generateHash(input1);
    String input2 = "Hash me!";
    String output2 = PasswordService.generateHash(input2);

    assertEquals(output1, output2);
  }

  //testing for collisions is impossible, we will just trust SHA-512
}