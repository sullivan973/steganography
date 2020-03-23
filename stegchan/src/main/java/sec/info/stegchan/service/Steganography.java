package sec.info.stegchan.service;

import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Steganography {

    /**
     * Method to encode a message into the least significant bits of an image
     * @param originalImage The original image rgb writeable raster array, modified to include message
     * @param message The message to be encoded
     * @throws IllegalArgumentException image too small to encode message
     */
    public static void encodeMessage(byte[] originalImage, String message) throws IllegalArgumentException{
        //convert message into byteArray
        char[] charArray = message.toCharArray();
        int[] intArray = new int[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            intArray[i] = charArray[i];
            //System.out.print(intArray[i]);
        }
        //System.out.println();

        //create new image
        //byte[] modifiedImage = originalImage.clone();

        //check image is large enough
        if (intArray.length * 8 > originalImage.length) {
            throw new IllegalArgumentException("Image not large enough");
        }

        //zero out least significant image bits for message size plus null
        for (int i = 0; i < intArray.length * 8 + 8; i++) {
            originalImage[i] = (byte) (originalImage[i] & 0xFE);
        }
        int imageOffset = 0;
        //iterate over message bytes
        for (int i = 0; i < intArray.length; i++) {
            int add = intArray[i];

            //iterate over bits in message byte
            for (int bit = 7; bit >= 0; bit--) {
                //zero all bits except for one
                int addedBit = (add >>> bit) & 1;
                originalImage[imageOffset] = (byte) (originalImage[imageOffset] | addedBit);
                imageOffset++;
            }
        }
    }

    /**
     * Decode a message out of an image
     * @param encodedImage the image rgb raster array with an encoded message
     * @return the encoded message as a string
     */
    public static String decodeMessage(byte[] encodedImage) {
        //StringBuilder to hold message
        StringBuilder messageBuilder = new StringBuilder();

        //List to hold decoded bits
        Queue<Integer> bitList = new LinkedList<Integer>();

        //iterate over all bytes in image
        for (int i = 0; i < encodedImage.length; i++) {
            bitList.add(encodedImage[i] & 1);
        }

        //break bitList into bytes
        List<Integer> byteList = new LinkedList<Integer>();
        while(bitList.size() > 0) {
            int currentByte = 0;
            for (int j = 0; j < 8; j++) {
                currentByte = ((currentByte << 1) | bitList.remove());
                if (bitList.size() == 0) {
                    break;
                }
            }
            byteList.add(currentByte);
        }
        Integer[] array = byteList.toArray(new Integer[byteList.size()]);
        int[] newArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
            //System.out.print(newArray[i]);
        }
        //System.out.println();

        //convert bytes to characters
        for (int i = 0; i < byteList.size(); i++) {
            if (byteList.get(i).intValue() == 0) {
                break;
            }
            char currentChar = (char) byteList.get(i).intValue();
            messageBuilder.append(currentChar);
        }

        System.out.println("\nDecoded message: " + messageBuilder.toString());
        return messageBuilder.toString();
    }

    /**
     * Extracts the rgb raster bytes from a BufferedImage so encode wont mess with compression logic
     * @param image Buffered Image of an image file
     * @return rgb raster byte array that can mutate image
     */
    public static byte[] extractRGBBytes(BufferedImage image) {
        return ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    }

    /**
     * Converts a byte array of raw image data to a buffered image
     * @param imageData raw byte array of image binaries
     * @return a BufferedImage of the imageData
     * @throws IOException failed to convert bytes to an image
     */
    public static BufferedImage createBufferedImage(byte[] imageData) throws IOException {
        InputStream imageInputStream = new BufferedInputStream(new ByteArrayInputStream(imageData));
        return ImageIO.read(imageInputStream);
    }

    /**
     *
     * @param image Buffered image to convert to raw byte data
     * @param format informal image format, ie jpg, gif, png
     * @return raw byte data of image
     * @throws IOException
     */
    public static byte[] bufferedImageToBytes(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, format, bos );
        return bos.toByteArray();
    }
}