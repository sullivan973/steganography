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

    private static String[] KEYS = new String[] {"LIVING", "BELLIGERENT", "CHIN", "HEAP", "STEEP", "INTEREST", "SELF", "WREN",
        "DEAFENING", "ANNOUNCE", "ILLEGAL", "PHYSICAL", "DOOR", "HARMONY", "HYDRANT", "WIDE"};

    /**
     * Wrapper around Steg class. Embeds message into base64 image
     * @param base64Image base64 original image to be encoded
     * @param message message to be embedded in image
     * @param imageType image type for used for compression
     * @return byte array blob of the encoded image to be saved in the database
     */
    public static byte[] encodeBase64(String base64Image, String message, String imageType) throws IOException {
        //turn base64 data into binary
        byte[] imageBinary = Base64Utils.decodeFromString(base64Image);
        //encode message into image and get encoded bytes
        BufferedImage image = createBufferedImage(imageBinary);
        byte[] RGBBytes = Steganography.extractRGBBytes(image);
        Steganography.encodeMessage(RGBBytes, message);
        //save jpegs as png so we can avoid compression problems
        if(imageType.equals("jpeg")) {
            imageType = "png";
        }
        byte[] encodedImage = bufferedImageToBytes(image, imageType);
        return encodedImage;
    }

    /**
     * Decodes a message out raw image Binaries. Wrapper decode method
     * @param imageBinary binaries from the database to decode
     * @return message hidden in image
     * @throws IOException if createBufferedImage fails
     */
    public static String decodeFromBinaries(byte[] imageBinary) throws IOException {
        BufferedImage image = createBufferedImage(imageBinary);
        byte[] data = extractRGBBytes(image);
        return decodeMessage(data);
    }

    /**
     * Method to encode a message into the least significant bits of an image
     * @param originalImage The original image rgb writeable raster array, modified to include message
     * @param message The message to be encoded
     * @throws IllegalArgumentException image too small to encode message
     */
    public static void encodeMessage(byte[] originalImage, String message) throws IllegalArgumentException{
        //encrypt message
        String encryptionKey = getKey(originalImage[originalImage.length-5]);
        System.out.println(encryptionKey);
        String encryptedMessage = vigenereEncode(encryptionKey, message);
        //convert message into byteArray
        char[] charArray = encryptedMessage.toCharArray();
        int[] intArray = new int[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            intArray[i] = charArray[i];
        }

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
     * Decode a message out of a RGB image array
     * @param encodedImage the image rgb raster array with an encoded message
     * @return the encoded message as a string
     */
    public static String decodeMessage(byte[] encodedImage) {

        //get vigenere key
        String key = getKey(encodedImage[encodedImage.length-5]);
        System.out.println(key);

        //StringBuilder to hold message
        StringBuilder messageBuilder = new StringBuilder();

        //List to hold decoded bits
        Queue<Integer> bitList = new LinkedList<Integer>();

        //iterate over all bytes in image
        int zeroCounter = 0;
        for (int i = 0; i < encodedImage.length; i++) {
            //if the last 8 LSB were zero then that's the null char, break
            if(zeroCounter == 8) {
                break;
            }
            int lsb = encodedImage[i] & 1;
            if(lsb == 0) {
                zeroCounter++;
            } else {
                zeroCounter = 0;
            }
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
        }

        //convert bytes to characters
        for (int i = 0; i < byteList.size(); i++) {
            if (byteList.get(i).intValue() == 0) {
                break;
            }
            char currentChar = (char) byteList.get(i).intValue();
            messageBuilder.append(currentChar);
        }

        //decode
        String message = vigenereDecode(key, messageBuilder.toString());

        return message;
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
     * Coverts a bufferedImage back into Binaries
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

    public static String vigenereEncode(String key, String message) {
        char[] keyArray = key.toCharArray();
        StringBuilder encodedMessage = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char messageChar = message.charAt(i);
            if (Character.isAlphabetic(messageChar)) {
                int keyNum = i % keyArray.length;
                int keyShift = ((int) keyArray[keyNum]) - 65;
                char newChar = (char) ((((int) Character.toUpperCase(messageChar) + keyShift - 65) % 26) + 65);
                encodedMessage.append(Character.isUpperCase(messageChar) ? newChar : Character.toLowerCase(newChar));
            } else {
                encodedMessage.append(messageChar);
            }

        }
        return encodedMessage.toString();
    }

    public static String vigenereDecode(String key, String encoded) {
        char[] keyArray = key.toCharArray();
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < encoded.length(); i++) {
            char encodedChar = encoded.charAt(i);
            if (Character.isAlphabetic(encodedChar)) {
                int keyNum = i % keyArray.length;
                int keyShift = ((int) keyArray[keyNum]) - 65;
                char newChar = (char) ((((int) Character.toUpperCase(encodedChar) - keyShift + 26 - 65) % 26) + 65);
                message.append(Character.isUpperCase(encodedChar) ? newChar : Character.toLowerCase(newChar));
            } else {
                message.append(encodedChar);
            }
        }
        return message.toString();
    }

    public static String getKey(byte keyNum) {
        int keyIndex = (int) keyNum;
        return Steganography.KEYS[Math.abs(keyIndex % 16)];
    }
}
