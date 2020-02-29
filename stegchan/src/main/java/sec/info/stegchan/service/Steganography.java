package sec.info.stegchan.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Steganography {

    /**
     * Method to encode a message into the least significant bits of an image
     * @param originalImage The original image
     * @param message The message to be encoded
     * @return The original image with the message embedded into it
     * @throws IllegalArgumentException image to small to encode message
     */
    public static byte[] encodeMessage(byte[] originalImage, String message) throws IllegalArgumentException{
        //convert message into byteArray
        byte[] messageByteArr = message.getBytes();

        //create new image
        byte[] modifiedImage = originalImage.clone();

        //check image is large enough
        if (messageByteArr.length * 8 > originalImage.length) {
            throw new IllegalArgumentException("Image not large enough");
        }

        //zero out least significant image bits
        for (int i = 0; i < originalImage.length; i++) {
            originalImage[i] = (byte) (originalImage[i] & 0xFE);
        }

        //iterate over message bytes
        for (byte messageByte : messageByteArr) {
            int add = messageByte;
            int imageOffset = 0;

            //iterate over bits in message byte
            for (int bit = 7; bit >= 0; bit--) {
                //zero all bits except for one
                int addedBit = (add >>> bit) & 1;
                modifiedImage[imageOffset] = (byte) (originalImage[imageOffset] | addedBit);
                imageOffset++;
            }
        }
        return modifiedImage;
    }

    /**
     * Decode a message out of an image
     * @param encodedImage the image with an encoded message
     * @return the encoded message as a string
     */
    public static String decodeMessage(byte[] encodedImage) {
        //StringBuilder to hold message
        StringBuilder messageBuilder = new StringBuilder();

        //List to hold decoded bits
        Queue<Integer> bitList = new LinkedList<Integer>();

        //iterate over all bytes in image
        for (byte imageByte : encodedImage) {
            bitList.add(imageByte & 1);
        }

        //break bitList into bytes
        List<Byte> byteList = new LinkedList<Byte>();
        for (int i = 0; i < bitList.size(); i += 8) {
            byte currentByte = 0;
            for (int j = 0; j < 8; j++) {
                currentByte = (byte) ((currentByte << j) | bitList.remove());
                byteList.add(currentByte);
            }
        }

        //convert bytes to characters
        for (byte charByte : byteList) {
            if (charByte == 0) {
                break;
            }
            char currentChar = (char) charByte;
            messageBuilder.append(currentChar);
        }

        return messageBuilder.toString();
    }
}
