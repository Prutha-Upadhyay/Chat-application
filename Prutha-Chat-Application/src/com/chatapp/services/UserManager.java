package com.chatapp.services;

import com.chatapp.database.DatabaseManager;
import com.chatapp.models.RegisterUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Centralizes the management and operations related to users within the Chat Application.
 * The {@code UserManager} class provides a cohesive set of methods for registering users,
 * authenticating user login/logout sessions, and interfacing with the underlying database for
 * user data persistence and retrieval.
 *
 * <p>The class streamlines user registration by assigning unique user IDs using an atomic counter.
 * It maintains the session's current logged-in user and serves as a gateway for all user-related
 * operations, ensuring the application's front-end user functionalities synchronize seamlessly
 * with the underlying database.</p>
 *
 * @author Prutha Upadhyay
 */
public class UserManager {

    private final List<RegisterUser> registerUsers = new ArrayList<>();
    private final DatabaseManager databaseManager;
    private final AtomicInteger userIdCnt = new AtomicInteger(0);
    private RegisterUser currentUser;
    private Map<String, Boolean> loggedInUsers = new HashMap<>(); // Map to store logged-in status of users

    // Other methods in the UserManager class...


    public UserManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    public boolean isLoggedIn(String senderUsername) {
        return loggedInUsers.getOrDefault(senderUsername, false);
    }

    /**
     * Returns a list of all registered users in the system.
     *
     * @return A list containing all registered users.
     */
    public List<RegisterUser> getRegisterUsers() {
        return registerUsers;
    }

    private int generateUserId() {
        return userIdCnt.incrementAndGet();
    }

    /**
     * Registers a new user within the system and persists their data in the database.
     * After successful registration, the newly registered user is set as the current active
     * user for the ongoing session.
     *
     * @param name     Display name of the user.
     * @param userName The unique identifier chosen by the user for logging into the system.
     * @param password The authentication password specified by the user.
     */
    public void registerUser(String name, String userName, String password) {
        int newId = generateUserId();
        RegisterUser regUser = new RegisterUser(newId, name, userName, password);
        registerUsers.add(regUser);
        databaseManager.storeRegisteredUser(regUser);

        currentUser = regUser; // Set the current user to the newly registered user
    }

    /**
     * Attempts to authenticate a user's login using the provided credentials.
     * Upon successful authentication, the user's status is set to online within the database,
     * and they become the current active user for the session.
     *
     * @param username The unique identifier of the user.
     * @param password The authentication password of the user.
     * @return The authenticated user if credentials are valid, or {@code null} if authentication fails.
     */
    public RegisterUser login(String username, String password) {
        try (Connection connection = databaseManager.getConnection()) {
            String sql = "SELECT * FROM User WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("userId");
                String name = resultSet.getString("name");
                String dbUsername = resultSet.getString("username");
                String dbPassword = resultSet.getString("password");

                RegisterUser loggedInUser = new RegisterUser(userId, name, dbUsername, dbPassword);
                loggedInUser.setUserStatus(true);

                // Update user status to online in the database
                databaseManager.updateUserOnlineStatus(loggedInUser);
                loggedInUsers.put(username, true);

                // currentUser = loggedInUser; // seting the current user

                if (loggedInUser != null) {
                    currentUser = loggedInUser; // Set the current user upon successful login
                }
            
                return loggedInUser;

                // return loggedInUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Login failed...
    }

    /**
     * Updates the user's status to offline in both the application session and the database.
     *
     * @param user The user who is to be logged out.
     */
    public void logout(RegisterUser user) {
        user.setUserStatus(false);// marking the user offline
        loggedInUsers.put(user.getUserName(), false);
        //database entry for offline
        databaseManager.updateUserOnlineStatus(user);
    }

    /**
     * Retrieves the currently logged-in user for the session.
     *
     * @return The current user.
     */
    public RegisterUser getCurrentUser() {
        return currentUser;
    }

    /**
     * Queries the database to retrieve details of a user based on their unique username.
     *
     * @param username The unique identifier of the desired user.
     * @return A {@code RegisterUser} object containing details of the user if found, or {@code null} if not present.
     */
    public RegisterUser getUserByUsername(String username) {
        return databaseManager.fetchUserByUsername(username);
    }


    
}
