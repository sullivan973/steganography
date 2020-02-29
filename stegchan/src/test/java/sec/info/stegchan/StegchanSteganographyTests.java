package sec.info.stegchan;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sec.info.stegchan.service.Steganography;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class StegchanSteganographyTests {
    @Test
    void encodingTest() {
        try {
            BufferedImage testImage = ImageIO.read(new File("testImage.jpg"));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(testImage, "jpg", bos );
            byte[] data = bos.toByteArray();
            byte[] encoded = Steganography.encodeMessage(data, "Test message");
            String message = Steganography.decodeMessage(encoded);
            assertEquals(message, "Test message");
        } catch(Exception e){
            assertNull(e);
        }
    }
}
