package com.chatapp.models;

import com.chatapp.utils.CaesarCipher;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a chat room within the Chat Application, responsible for managing participants,
 * storing chat history, and facilitating message communication between users.
 * <p>
 * The chat room ensures messages are encrypted using a Caesar Cipher technique for added security.
 * Additionally, it supports functionalities to save chat histories to a file and load them back,
 * providing persistence of chat interactions across sessions.
 * </p>
 *
 * @author Prutha Upadhyay
 */
public class ChatRoom {
    private static final int CIPHER_SHIFT = 3; // Move magic number to a named constant
    private final int chatRoomId;
    private final List<RegisterUser> participants = new ArrayList<>();
    private final List<String> chatHistory = new ArrayList<>();
    private String roomName;

    public ChatRoom(int chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    /**
     * Retrieves the unique ID of this chat room.
     *
     * @return the ID of this chat room.
     */
    public int getChatRoomId() {
        return chatRoomId;
    }

    /**
     * Retrieves a list of participants present in the chat room. The list is unmodifiable.
     *
     * @return an unmodifiable list of {@code RegisterUser} participants.
     */
    public List<RegisterUser> getParticipants() {
        return Collections.unmodifiableList(participants);
    }

    /**
     * Retrieves the chat history of the room. The list is unmodifiable.
     *
     * @return an unmodifiable list of chat messages.
     */
    public List<String> getChatHistory() {
        return Collections.unmodifiableList(chatHistory);
    }

    /**
     * Adds a participant to the chat room.
     *
     * @param user The {@code RegisterUser} to be added.
     */
    public void addParticipant(RegisterUser user) {
        participants.add(user);
    }

    /**
     * Removes a participant from the chat room.
     *
     * @param user The {@code RegisterUser} to be removed.
     */
    public void removeParticipant(RegisterUser user) {
        participants.remove(user);
    }

    /**
     * Sends an encrypted message to the chat room. The message is first encrypted using Caesar Cipher,
     * then added to the chat history and finally displayed to all participants of the chat room.
     *
     * @param senderUser  The user sending the message.
     * @param message The plaintext message content.
     * @return The encrypted version of the sent message.
     */
    public String sendMessage(User senderUser, String message) {
        String encryptedMessage = CaesarCipher.encrypt(message, CIPHER_SHIFT);
        String finalMessage = senderUser.getName() + " : " + encryptedMessage;
        System.out.println();
        System.out.println("Encrypted message : " + encryptedMessage);
        chatHistory.add(finalMessage);
        return encryptedMessage;
    }

    /**
     * Processes a received message in the chat room by decrypting it. The decrypted message is then
     * added to the chat history and displayed to all participants.
     *
     * @param receiverUser The user receiving the message.
     * @param message  The encrypted message content.
     */
    public void receiveMessage(User receiverUser, String message) {
        String decryptedMessage = CaesarCipher.decrypt(message, CIPHER_SHIFT);
        System.out.println();
        System.out.println("Decrypted Messege : " + decryptedMessage);
        String finalMessage = receiverUser.getName() + " : " + decryptedMessage;
        chatHistory.add(finalMessage);
    }

    /**
     * Attempts to persistently save the chat room's history to a specified file. Each message is written
     * on a new line in the file.
     *
     * @param pathname The destination path and filename for the chat history.
     * @return {@code true} if the chat history was successfully saved; {@code false} otherwise.
     */
    public boolean saveChatHistoryToFile(String pathname) {
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(pathname));
            for (String string : chatHistory) {
                bw.write(string);
                bw.newLine();
            }
            bw.close();
            return true; // Saved to file 
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Failed
        }
    }

    /**
     * Attempts to load a chat room's history from a specified file. Each message in the file is added
     * to the chat room's history and displayed to participants.
     *
     * @param pathname The source path and filename of the chat history to load.
     * @return {@code true} if the chat history was successfully loaded; {@code false} otherwise.
     */
    public boolean loadMessagesFromFile(String pathname) {
        try (BufferedReader br = new BufferedReader(new FileReader(pathname))) {
            String text;
            while ((text = br.readLine()) != null) {
                System.out.println(text);
            }
            br.close();
            return true; // Loaded from file
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Failed
        }
    }

    /**
     * Retrieves the name of the chat room.
     *
     * @return the name of the chat room.
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Sets or updates the name of the chat room.
     *
     * @param roomName The new name for the chat room.
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
