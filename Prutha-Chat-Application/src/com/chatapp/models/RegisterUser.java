package com.chatapp.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a registered user in the Chat Application. The {@code RegisterUser} class encapsulates
 * attributes and functionalities specific to registered users, such as maintaining a history of
 * sent messages and handling user authentication credentials (username and password).
 * <p>
 * This design ensures a separation of concerns, allowing registered users to have a richer set
 * of capabilities in the chat application, while building upon the basic attributes and functionalities
 * provided by the parent {@code User} class.
 * </p>
 *
 * @author Prutha Upadhyay
 */
public class RegisterUser extends User {
    private final List<String> messagesSent = new ArrayList<>();
    private String userName;
    private String password;

    public RegisterUser(int userId, String name, String userName, String password) {
        super(userId, name);
        this.userName = userName;
        this.password = password;
    }

    /**
     * Sends a message to the specified chat room. After sending, the message is
     * stored in the user's sent message history.
     *
     * @param chatRoom The chat room recipient of the message.
     * @param message  The content of the message.
     */
    public void sendMessage(ChatRoom chatRoom, String message) {
        chatRoom.sendMessage(this, message);
        messagesSent.add(message);  // if you wish to keep track of messages sent by a user
    }

    /**
     * Provides an immutable view of the messages sent by the user across all chat rooms.
     * This ensures that the internal representation of sent messages cannot be
     * modified externally, adding an extra layer of security and integrity.
     *
     * @return An unmodifiable list representing the history of messages sent by the user.
     */
    public List<String> getMessagesSent() {
        return Collections.unmodifiableList(messagesSent);
    }

    /**
     * Retrieves the username used for authentication in the chat application.
     *
     * @return the username of the registered user.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets or updates the username for the registered user.
     *
     * @param userName The new username.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Retrieves the password used for authentication in the chat application.
     *
     * @return the password of the registered user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets or updates the password for the registered user.
     *
     * @param password The new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}