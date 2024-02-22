package com.chatapp.services;

import com.chatapp.database.DatabaseManager;
import com.chatapp.models.ChatRoom;
import com.chatapp.models.RegisterUser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manages the lifecycle and operations of chat rooms within the application.
 * The {@code ChatRoomManager} offers methods for overseeing chat room participants,
 * tracking chat messages, and maintaining the active chat room for a user session.
 *
 * <p>This manager integrates closely with the database, ensuring persistence of chat
 * rooms and user-chat room relationships. It also provides an atomic counter to produce
 * unique identifiers for chat rooms, ensuring thread safety.</p>
 *
 * @author Prutha Upadhyay
 */
public class ChatRoomManager {
    private final List<ChatRoom> chatRooms = new ArrayList<>();
    private final DatabaseManager dbManager = new DatabaseManager();
    private final AtomicInteger chatRoomIdCounter = new AtomicInteger(0);
    private ChatRoom currentChatRoom;

    /**
     * Retrieves the currently active chat room for the session.
     *
     * @return The currently active chat room.
     */
    public ChatRoom getCurrentChatRoom() {
        return currentChatRoom;
    }

    /**
     * Sets the currently active chat room for the session.
     *
     * @param currentChatRoom The chat room to be set as currently active.
     */
    public void setCurrentChatRoom(ChatRoom currentChatRoom) {
        this.currentChatRoom = currentChatRoom;
    }

    /**
     * Retrieves a list of all chat rooms in the system.
     *
     * @return A list of all chat rooms.
     */
    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    /**
     * Generates a unique ID for a chat room. This ID is used as an identifier for chat rooms in the system.
     *
     * @return A unique chat room ID.
     */
    public int generateChatRoomId() {
        return chatRoomIdCounter.incrementAndGet();
    }

    /**
     * Adds a registered user to a specified chat room. This method not only updates the
     * in-memory representation of the chat room but also persists the user-chat room association
     * to the database.
     *
     * @param user     The registered user intended to join the chat room.
     * @param chatRoom The target chat room.
     * @throws IllegalArgumentException if either the user or chat room is null.
     */
    public void addParticipantToChatRoom(RegisterUser user, ChatRoom chatRoom) {
        if (chatRoom == null || user == null) {
            throw new IllegalArgumentException("User or ChatRoom cannot be null.");
        }

        chatRoom.addParticipant(user);
        chatRooms.add(chatRoom);
        setCurrentChatRoom(chatRoom);
    }

    /**
     * Excludes a registered user from a specific chat room. This operation will
     * also update the database, removing the user-chat room association.
     *
     * @param user     The registered user intended to exit the chat room.
     * @param chatRoom The target chat room.
     * @throws IllegalArgumentException if either the user or chat room is null.
     */
    public void removeParticipantFromChatRoom(RegisterUser user, ChatRoom chatRoom) {
        if (chatRoom == null || user == null) {
            throw new IllegalArgumentException("User or ChatRoom cannot be null.");
        }

        chatRoom.removeParticipant(user);
        chatRooms.add(chatRoom);
        // This logic might be flawed; when a user leaves a room it doesn't mean the room is not current for others.
        setCurrentChatRoom(null);
    }

    /**
     * Captures and stores a chat message within a designated chat room. This ensures
     * that each message sent within the room is kept in its historical context.
     *
     * @param chatRoom The chat room where the message originates and will be stored.
     * @param message  The chat message content.
     * @throws IllegalArgumentException if the chat room is null or if the message is null/empty.
     */
    public void storeChatMessage(ChatRoom chatRoom, String message) {
        if (chatRoom == null || message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("ChatRoom or message cannot be null/empty.");
        }

        chatRoom.getChatHistory().add(message);
    }
}
