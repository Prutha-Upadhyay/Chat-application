package com.chatapp.database;

import com.chatapp.models.ChatRoom;
import com.chatapp.models.RegisterUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code DatabaseManager} class provides a concrete implementation for interacting with the database
 * of the Chat Application, handling operations related to users and chat rooms.
 * <p>
 * Key functionalities include:
 * <ul>
 *     <li>Establishing a database connection</li>
 *     <li>CRUD operations for registered users</li>
 *     <li>Fetching user data based on authentication and username</li>
 *     <li>Managing the online status of users</li>
 *     <li>Associating and dissociating users from chat rooms</li>
 *     <li>CRUD operations for chat rooms</li>
 * </ul>
 * </p>
 * <p>
 * Important: This class utilizes raw JDBC operations. It does not implement ORM or connection pooling.
 * Ensure judicious handling of database connections to prevent potential connection leaks.
 * </p>
 *
 * @author Prutha Upadhyay
 */

public class DatabaseManager {

    private final String DB_URL = "jdbc:mysql://localhost:3306/chatapplication";
    private final String DB_USER = "root";
    private final String DB_PASS = "";
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * Provides the initialization and setup for the {@code DatabaseManager}.
     * During instantiation, this ensures that a connection to the database can be established.
     */
    public DatabaseManager() {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Connection Successful...");
            } else {
                System.out.println("Connection Failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * Establishes and returns a connection to the Chat Application database.
     *
     * @return A valid database connection.
     * @throws RuntimeException if there's an error connecting to the database.
     */
    public Connection getConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("couldn't connect!");
            throw new RuntimeException(ex);
        }
    }

    /**
     * Stores the provided user's data into the User table of the database.
     *
     * @param user The {@code RegisterUser} object containing user details to be stored.
     */
    public void storeRegisteredUser(RegisterUser user) {
        String sql = "INSERT INTO User (name, username, password) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches a registered user from the database based on the provided username and password.
     *
     * @param username The username to search for.
     * @param password The associated password to the username.
     * @return A {@code RegisterUser} object containing the fetched user details, or {@code null} if not found.
     */
    public RegisterUser fetchUserByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM User WHERE username = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("userId");
                    String name = resultSet.getString("name");
                    String dbUsername = resultSet.getString("username");
                    String dbPassword = resultSet.getString("password");
                    return new RegisterUser(userId, name, dbUsername, dbPassword);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fetches a user from the database based on the provided username.
     *
     * @param username The username to search for.
     * @return A {@code RegisterUser} object containing the fetched user details, or {@code null} if not found.
     */
    public RegisterUser fetchUserByUsername(String username) {
        String sql = "SELECT userId, name, username, password FROM User WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("userId");
                    String name = resultSet.getString("name");
                    String dbUsername = resultSet.getString("username");
                    String dbPassword = resultSet.getString("password");
                    return new RegisterUser(userId, name, dbUsername, dbPassword);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates the online status of the given user in the database.
     *
     * @param user The {@code RegisterUser} object whose online status needs to be updated.
     */
    public void updateUserOnlineStatus(RegisterUser user) {
        String sql = "UPDATE User SET online = ? WHERE userId = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, user.getUserStatus());
            preparedStatement.setInt(2, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ChatRoom getChatRoomByName(String roomName) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ChatRoom chatRoom = null;

        try {
            // Establish connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            // Prepare SQL query
            String sql = "SELECT * FROM chatroom WHERE chatRoomName = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, roomName);

            // Execute query
            resultSet = statement.executeQuery();

            // Process result set
            if (resultSet.next()) {
                int chatRoomId = resultSet.getInt("chatRoomId");
                String chatRoomName = resultSet.getString("chatRoomName");
                chatRoom = new ChatRoom(chatRoomId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return chatRoom;
    }
    

    /**
     * Inserts a user into a specific chat room in the database.
     *
     * @param userId     The ID of the user to be inserted.
     * @param chatRoomId The ID of the chat room where the user needs to be added.
     */
    /* public void insertUserToChatRoom(int userId, int chatRoomId) {
        String sql = "Insert into userchatroom (userId,chatRoomId) values (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, chatRoomId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } */
    public void insertUserToChatRoom(int userId, int chatRoomId) {
        String sql = "INSERT INTO userchatroom (userId, chatRoomId) VALUES (?, ?)";

        try (Connection connection = getConnection()) {
            // Start a transaction
            connection.setAutoCommit(false);

            try {
                // Check if the user is already in the chat room
                String checkUserInChatRoomSQL = "SELECT * FROM userchatroom WHERE userId = ? AND chatRoomId = ?";
                try (PreparedStatement checkStatement = connection.prepareStatement(checkUserInChatRoomSQL)) {
                    checkStatement.setInt(1, userId);
                    checkStatement.setInt(2, chatRoomId);
                    ResultSet resultSet = checkStatement.executeQuery();

                    if (!resultSet.next()) {
                        // User is not in the chat room, so insert them
                        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                            preparedStatement.setInt(1, userId);
                            preparedStatement.setInt(2, chatRoomId);
                            preparedStatement.executeUpdate();
                        }
                    }

                    // Commit the transaction
                    connection.commit();
                }
            } catch (SQLException e) {
                // Handle database-related exceptions
                connection.rollback(); // Rollback the transaction in case of an error
                e.printStackTrace(); // Log the exception or handle it as needed
            }
        } catch (SQLException e) {
            // Handle database connection-related exceptions
            e.printStackTrace(); // Log the exception or handle it as needed
        }
    }


    /**
     * Removes a user from a specific chat room in the database.
     *
     * @param userId     The ID of the user to be removed.
     * @param chatRoomId The ID of the chat room from which the user needs to be removed.
     */
    public void removeUserFromChatRoom(int userId, int chatRoomId) {
        String sql = "Delete from userchatroom where userId=? AND chatRoomId=?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, chatRoomId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches a list of all chat rooms from the database.
     *
     * @return A {@code List} of {@code ChatRoom} objects representing all the chat rooms.
     */
    public List<ChatRoom> fetchChatRooms() {
        List<ChatRoom> chatRooms = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM ChatRoom";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int chatRoomId = rs.getInt("chatRoomId");
                ChatRoom chatRoom = new ChatRoom(chatRoomId);
                chatRoom.setRoomName(rs.getString("chatRoomName"));
                // Set other properties as needed
                chatRooms.add(chatRoom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chatRooms;
    }

    /**
     * Inserts a new chat room into the database.
     *
     * @param chatRoom
     * @param loggedInUserId
     * @return int The {@code ChatRoom} object that needs to be inserted.
     */
    public int createChatRoom(ChatRoom chatRoom, int loggedInUserId) {
        int generatedChatRoomId = -1; // Initialize with a default value

        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO ChatRoom (chatRoomName) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, chatRoom.getRoomName());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedChatRoomId = generatedKeys.getInt(1);
            }

            // Insert a record into UserChatRoom to associate the user with the chat room
            insertUserToChatRoom(loggedInUserId, generatedChatRoomId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedChatRoomId;
    }

    /**
     * Retrieves a specific chat room based on its ID from the database.
     *
     * @param chatRoomId The ID of the chat room to fetch.
     * @return A {@code ChatRoom} object representing the fetched chat room, or {@code null} if not found.
     */
    public ChatRoom getChatRoomById(int chatRoomId) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM ChatRoom WHERE chatRoomId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, chatRoomId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                ChatRoom chatRoom = new ChatRoom(chatRoomId);
                chatRoom.setRoomName(rs.getString("chatRoomName"));
                // Set other properties as needed
                return chatRoom;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Chat room not found
    }


}
