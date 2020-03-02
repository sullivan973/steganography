package sec.info.stegchan;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import sec.info.stegchan.service.Steganography;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StegchanSteganographyTests {
    @Test
    void encodingTest() {
        try {
            File imageFile = new File("C:\\Users\\Thomas Sullivan\\Documents\\My Classes SP20\\Info Sec\\steganography\\stegchan\\src\\test\\java\\sec\\info\\stegchan\\test-images\\testImage.jpg");
            BufferedImage testImage = ImageIO.read(imageFile);
            byte[] data = ((DataBufferByte) testImage.getRaster().getDataBuffer()).getData();
            Steganography.encodeMessage(data, "Test message");
            String message = Steganography.decodeMessage(data);
            assertEquals("Test message", message);
        } catch(Exception e){
            assertNull(e);
        }
    }

    @Test
    void encodingTest1() {
        try {
            File imageFile = new File("C:\\Users\\Thomas Sullivan\\Documents\\My Classes SP20\\Info Sec\\steganography\\stegchan\\src\\test\\java\\sec\\info\\stegchan\\test-images\\testImage1.jpg");
            BufferedImage testImage = ImageIO.read(imageFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            //need to extract RGB Raster as byte array so compression isn't messed up
            byte[] data = ((DataBufferByte) testImage.getRaster().getDataBuffer()).getData();

            //encode message into raster rgb array
            Steganography.encodeMessage(data, "Down with the bourgeoisie");
            String message = Steganography.decodeMessage(data);
            assertEquals("Down with the bourgeoisie", message);

            //testImage was mutated by the encoding so write to file to visually inspect it renders still
            ImageIO.write(testImage, "jpg", new File("C:\\Users\\Thomas Sullivan\\Documents\\My Classes SP20\\Info Sec\\steganography\\stegchan\\src\\test\\java\\sec\\info\\stegchan\\test-images\\testOutput1.jpg") );
        } catch(Exception e){
            assertNull(e);
        }
    }
}
