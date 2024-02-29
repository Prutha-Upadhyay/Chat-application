package com.chatapp.utils;

/**
 * Utility class offering methods for encryption and decryption using the Caesar
 * Cipher technique.
 * <p>
 * The Caesar Cipher is a substitution cipher that shifts letters of the
 * alphabet by a specified number of positions.
 * This particular implementation focuses solely on lowercase alphabetic
 * characters, leaving non-alphabetical
 * characters unchanged during the encryption or decryption process.
 * </p>
 *
 * @author Prutha Upadhyay
 */
public class CaesarCipher {

  // private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
  private static final String ALPHABET_LOWER = "abcdefghijklmnopqrstuvwxyz";
  private static final String ALPHABET_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  // private static final int ALPHABET_SIZE = ALPHABET.length();

  /**
   * Encrypts the input message using the Caesar Cipher mechanism.
   *
   * <p>
   * This method transforms only lowercase alphabetic characters in the message.
   * It maps each character to its
   * shifted equivalent in the alphabet based on the provided shiftKey.
   * Non-alphabetic characters remain unaffected.
   * </p>
   *
   * @param message  The plaintext to be encrypted.
   * @param shiftKey The number of positions to shift each letter in the alphabet
   *                 for encryption.
   * @return The encrypted version of the input message.
   */
  public static String encrypt(String message, int shiftKey) {
    // message = message.toLowerCase(); // Convert the message to lowercase

    StringBuilder encryptedMessage = new StringBuilder();

    for (int i = 0; i < message.length(); i++) {
      char character = message.charAt(i);

      if (Character.isLetter(character)) {
        if (Character.isUpperCase(character)) {
          int position = ALPHABET_UPPER.indexOf(character);
          int encryptedPosition = (position + shiftKey) % 26;
          char encryptedCharacter = ALPHABET_UPPER.charAt(encryptedPosition);
          encryptedMessage.append(encryptedCharacter);
        } else if (Character.isLowerCase(character)) {
          int position = ALPHABET_LOWER.indexOf(character);
          int encryptedPosition = (position + shiftKey) % 26;
          char encryptedCharacter = ALPHABET_LOWER.charAt(encryptedPosition);
          encryptedMessage.append(encryptedCharacter);
        }

      } else {
        encryptedMessage.append(character); // Keep non-alphabet characters unchanged
      }
    }

    return encryptedMessage.toString();
  }

  /**
   * Decrypts an encrypted message that used the Caesar Cipher for its encryption.
   *
   * <p>
   * The decryption process is a reversal of the encryption. It's realized by
   * shifting the encrypted characters
   * by the inverse of the original shiftKey. Specifically, if the original shift
   * for encryption was x, the shift
   * for decryption would be (26 - x), reversing the original encryption.
   * </p>
   *
   * @param encryptedMessage The ciphered message to decrypt.
   * @param shiftKey         The shift value originally used for encryption.
   * @return The plaintext version of the encrypted message.
   */
  public static String decrypt(String encryptedMessage, int shiftKey) {
    // encryptedMessage = encryptedMessage.toLowerCase(); // Convert the encrypted message to lowercase

    StringBuilder decryptedMessage = new StringBuilder();

    for (int i = 0; i < encryptedMessage.length(); i++) {
      char character = encryptedMessage.charAt(i);
      if (Character.isLetter(character)) {
        if (Character.isUpperCase(character)) {
          int position = ALPHABET_UPPER.indexOf(character);
          int decryptedPosition = (position - shiftKey + ALPHABET_UPPER.length()) % ALPHABET_UPPER.length();
          char decryptedCharacter = ALPHABET_UPPER.charAt(decryptedPosition);
          decryptedMessage.append(decryptedCharacter);
        } else if (Character.isLowerCase(character)) {
          int position = ALPHABET_LOWER.indexOf(character);
          int decryptedPosition = (position - shiftKey + ALPHABET_LOWER.length()) % ALPHABET_LOWER.length();
          char decryptedCharacter = ALPHABET_LOWER.charAt(decryptedPosition);
          decryptedMessage.append(decryptedCharacter);
        }
        

      }else {
        decryptedMessage.append(character); // Keep non-alphabet characters unchanged
      }
      // if (Character.isLetter(character)) {
      // int position = ALPHABET.indexOf(character);
      // // Calculate the decrypted position by subtracting the shiftKey and handle
      // // negative values
      // int decryptedPosition = (position - shiftKey + ALPHABET_SIZE) %
      // ALPHABET_SIZE;
      // char decryptedCharacter = ALPHABET.charAt(decryptedPosition);
      // decryptedMessage.append(decryptedCharacter);
      // } else {
      // decryptedMessage.append(character); // Keep non-alphabet characters unchanged
      // }
    }

    return decryptedMessage.toString();
  }

  /*
   * public static String decrypt(String cipherText, int shiftKey) {
   * cipherText = cipherText.toLowerCase();
   * String message = "";
   * for (int ii = 0; ii < cipherText.length(); ii++) {
   * int charPosition = ALPHABET.indexOf(cipherText.charAt(ii));
   * int keyVal = (charPosition - shiftKey) % 26;
   * if (keyVal < 0) {
   * keyVal = ALPHABET.length() + keyVal;
   * }
   * char replaceVal = ALPHABET.charAt(keyVal);
   * message += replaceVal;
   * }
   * return message;
   * }
   * 
   * public static String encrypt(String message, int shiftKey) {
   * message = message.toLowerCase();
   * String cipherText = "";
   * for (int ii = 0; ii < message.length(); ii++) {
   * int charPosition = ALPHABET.indexOf(message.charAt(ii));
   * int keyVal = (shiftKey + charPosition) % 26;
   * char replaceVal = ALPHABET.charAt(keyVal);
   * cipherText += replaceVal;
   * }
   * return cipherText;
   * }
   */

}
