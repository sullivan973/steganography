package sec.info.stegchan;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import sec.info.stegchan.service.Steganography;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StegchanSteganographyTests {
    @Test
    void encodingTest() {
        try {
            File imageFile = new File("C:\\Users\\Sovie\\IdeaProjects\\steganography\\stegchan\\src\\test\\java\\sec\\info\\stegchan\\test-images\\testImage.jpg");
            BufferedImage testImage = ImageIO.read(imageFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(testImage, "jpg", bos );
            byte[] data = bos.toByteArray();
            byte[] encoded = Steganography.encodeMessage(data, "Test message");
            String message = Steganography.decodeMessage(encoded);
            assertEquals("Test message", message);
        } catch(Exception e){
            assertNull(e);
        }
    }

    @Test
    void encodingTest1() {
        try {
            File imageFile = new File("C:\\Users\\Sovie\\IdeaProjects\\steganography\\stegchan\\src\\test\\java\\sec\\info\\stegchan\\test-images\\testImage1.jpg");
            BufferedImage testImage = ImageIO.read(imageFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(testImage, "jpg", bos );
            byte[] data = bos.toByteArray();
            byte[] encoded = Steganography.encodeMessage(data, "Down with the bourgeoisie");
            String message = Steganography.decodeMessage(encoded);
            assertEquals("Down with the bourgeoisie", message);
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            BufferedImage bImage2 = ImageIO.read(bis);
            ImageIO.write(bImage2, "jpg", new File("C:\\Users\\Sovie\\IdeaProjects\\steganography\\stegchan\\src\\test\\java\\sec\\info\\stegchan\\test-images\\testOutput1.jpg") );
        } catch(Exception e){
            assertNull(e);
        }
    }
}
