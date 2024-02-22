package com.chatapp.models;

/**
 * Represents a generic user within the Chat Application. The {@code User} class encapsulates essential
 * attributes of a user, including their unique identifier (ID), display name, and their online status.
 * By default, upon instantiation, a user is considered to be offline.
 *
 * @author Prutha Upadhyay
 */
public class User {
    private int userId;
    private String name;
    private boolean userStatus; // true for online, false for offline

    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
        this.userStatus = false;  // By default user is offline
    }

    /**
     * Retrieves the unique ID of this user.
     *
     * @return the user's ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Updates the user's unique ID.
     *
     * @param userId The new user ID.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the name of the user.
     *
     * @return the name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets or updates the name of the user.
     *
     * @param name The new name for the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the online status of the user.
     *
     * @return {@code true} if the user is currently online; {@code false} otherwise.
     */
    public boolean getUserStatus() {
        return userStatus;
    }

    /**
     * Updates the online status of the user.
     *
     * @param userStatus Pass {@code true} to mark the user as online, and {@code false} to mark as offline.
     */
    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }
}