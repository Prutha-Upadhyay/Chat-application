package com.chatapp.ui;


import com.chatapp.database.DatabaseManager;
import com.chatapp.models.ChatRoom;
import com.chatapp.models.RegisterUser;
import com.chatapp.services.ChatRoomManager;
import com.chatapp.services.UserManager;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


/**
 * Represents the main entry point for a command-line based Chat Application.
 * <p>
 * This application provides the following features:
 * - User registration and login.
 * - Creation of chat rooms.
 * - Sending and receiving messages in chat rooms.
 * - Displaying chat history.
 * - Saving chat history to a temporary file.
 * - Loading chat history from a temporary file.
 * </p>
 * The application uses a command-line interface to interact with users. It also manages the connection to the
 * underlying database and chat room functionalities.
 *
 * @author Prutha Upadhyay
 */
public class ChatApplication {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    private static final int REGISTER_NEW_USER = 1;
    private static final int LOGIN = 2;
    private static final int CREATE_CHAT_ROOM = 3;
    private static final int SEND_MESSAGE = 4;
    private static final int DISPLAY_CHAT_HISTORY = 5;
    private static final int EXIT_APPLICATION = 6;
    private final Scanner scanner = new Scanner(System.in);
    private final static DatabaseManager databaseManager = new DatabaseManager();
    private final static UserManager userManager = new UserManager(databaseManager);
    private final ChatRoomManager chatRoomManager = new ChatRoomManager();
    String ANSI_YELLOW = "\u001B[33m";

    /**
     * Entry point for the ChatApplication. Creates an instance of ChatApplication and starts the main loop.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        ChatApplication app = new ChatApplication();
        app.run();
    }

    /**
     * Initiates the main application loop, providing users with a main menu to access various features.
     * Handles user choices and corresponding actions.
     */
    public void run() {
        String title = ANSI_GREEN + "WELCOME TO CHAT APPLICATION" + ANSI_RESET;
        int width = 100; // Adjust the width as needed
        int bWidth = 150;
        char borderChar = '*';
        String border = String.valueOf(borderChar).repeat(bWidth);
        String centeredTitle = String.format("%" + width + "s", title);
        System.out.println(border);
        System.out.println();
        System.out.println(centeredTitle);
        System.out.println();
        System.out.println(border);
        System.out.println();
        System.out.println();

        while (true) {
            System.out.println();
            displayMainMenu();

            try {
                int ch = scanner.nextInt();
                System.out.println();

                switch (ch) {
                    case REGISTER_NEW_USER:
                        int rwidth = 30;
                        String rTitle = ANSI_GREEN + "REGISTRTION MENU" + ANSI_RESET;
                        String rcenteredTitle = String.format("%" + rwidth + "s", rTitle);
                        System.out.println();
                        System.out.println(ANSI_RED + "+--------------------------+" + ANSI_RESET);
                        System.out.println(ANSI_GREEN + "|" + rcenteredTitle + ANSI_GREEN + "     |" + ANSI_RESET);
                        System.out.println(ANSI_RED + "+--------------------------+" + ANSI_RESET);
                        System.out.println();
                        System.out.println();
                        userRegistration();
                        break;
                    case LOGIN:
                        int lwidth = 30;
                        String lTitle = ANSI_GREEN + "   LOGIN MENU      " + ANSI_RESET;
                        String lcenteredTitle = String.format("%" + lwidth + "s", lTitle);
                        System.out.println();
                        System.out.println(ANSI_RED + "+--------------------------+" + ANSI_RESET);
                        System.out.println(ANSI_GREEN + "|" + lcenteredTitle + ANSI_GREEN + "     |" + ANSI_RESET);
                        System.out.println(ANSI_RED + "+--------------------------+" + ANSI_RESET);
                        System.out.println();
                        System.out.println();
                        userLogin();
                        break;
                    case CREATE_CHAT_ROOM:
                        int cwidth = 30;
                        String cTitle = ANSI_GREEN + "CHATROOM CREATION MENU" + ANSI_RESET;
                        String ccenteredTitle = String.format("%" + cwidth + "s", cTitle);
                        System.out.println();
                        System.out.println(ANSI_RED + "+--------------------------+" + ANSI_RESET);
                        System.out.println(ANSI_GREEN + "|" + ccenteredTitle + ANSI_GREEN + "    |" + ANSI_RESET);
                        System.out.println(ANSI_RED + "+--------------------------+" + ANSI_RESET);
                        System.out.println();
                        System.out.println();
                        chatRoomCreation();
                        break;
                    case SEND_MESSAGE:
                        int swidth = 30;
                        String sTitle = ANSI_GREEN + "MESSAGE SNEDING-RECIEVING MENU" + ANSI_RESET;
                        String scenteredTitle = String.format("%" + swidth + "s", sTitle);
                        System.out.println();
                        System.out.println(ANSI_RED + "+-----------------------------+" + ANSI_RESET);
                        System.out.println(ANSI_GREEN + "|" + scenteredTitle + ANSI_GREEN + " |" + ANSI_RESET);
                        System.out.println(ANSI_RED + "+-----------------------------+" + ANSI_RESET);
                        System.out.println();
                        System.out.println();
                        messageSendingReceiving();
                        break;
                    case DISPLAY_CHAT_HISTORY:
                        int dwidth = 30;
                        String dTitle = ANSI_GREEN + "DISPLAY CHAT HISTORY MENU" + ANSI_RESET;
                        String dcenteredTitle = String.format("%" + dwidth + "s", dTitle);
                        System.out.println();
                        System.out.println(ANSI_RED + "+--------------------------+" + ANSI_RESET);
                        System.out.println(ANSI_GREEN + "|" + dcenteredTitle + ANSI_GREEN + "|" + ANSI_RESET);
                        System.out.println(ANSI_RED + "+--------------------------+" + ANSI_RESET);
                        System.out.println();
                        System.out.println();
                        displayChatHistory();
                        break;
                    case EXIT_APPLICATION:
                        addShutdownHook();
                        exitApplication();
                        break;
                    default:
                        System.out.println(ANSI_RED + "Invalid choice, try again!" + ANSI_RESET);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(ANSI_RED + "Please enter a valid number." + ANSI_RESET);
                scanner.nextLine();
            }
        }
    }

    /**
     * Displays the main menu options to the user.
     * <p>
     * This method presents the user with options to register, log in, create/join chat rooms,
     * send/receive messages, display chat history, and exit the application.
     * </p>
     */
    private void displayMainMenu() {
        String title = ANSI_RED + "+--------------------------+" + ANSI_RESET;
        String title1 = ANSI_GREEN + "|Chat Application Main Menu|" + ANSI_RESET;
        String title2 = ANSI_RED + "+--------------------------+" + ANSI_RESET;
        int width = 100; // Adjust the width as needed
        String centeredTitle = String.format("%" + width + "s", title);
        String centeredTitle1 = String.format("%" + width + "s", title1);
        String centeredTitle2 = String.format("%" + width + "s", title2);
        System.out.println();
        System.out.println(centeredTitle);
        System.out.println(centeredTitle1);
        System.out.println(centeredTitle2);
        System.out.println();
        System.out.println();
        System.out.println(REGISTER_NEW_USER + ". Register a new user");
        System.out.println(LOGIN + ". Log in");
        System.out.println(CREATE_CHAT_ROOM + ". Create a chat room"+ ANSI_GREEN +" OR "+ANSI_RESET +"Join existing chat room");
        System.out.println(SEND_MESSAGE + ". Send a message " +ANSI_GREEN+"AND "+ ANSI_RESET+"Receive a massage");
        System.out.println(DISPLAY_CHAT_HISTORY + ". Display chat history");
        System.out.println(EXIT_APPLICATION + ". Exit from the chat application");
        System.out.print("Select an option: ");
    }

    /**
     * Facilitates the user registration process by obtaining necessary details from the user.
     * <p>
     * Prompts the user to input their name, desired username, and password. It ensures the name
     * and username are not empty before proceeding with the registration.
     * </p>
     */
    void userRegistration() {
        scanner.nextLine();
        System.out.print("Enter your name : ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println(ANSI_RED + "Name cannot be empty!" + ANSI_RESET);
            return;
        }

        System.out.print("Enter your username: ");
        String userName = scanner.nextLine().trim();

        if (userName.isEmpty()) {
            System.out.println(ANSI_RED + "Username cannot be empty!" + ANSI_RESET);
            return;
        }

        System.out.print("Enter your password : ");
        String pass = scanner.nextLine().trim();

        if (pass.isEmpty()) {
            System.out.println(ANSI_RED + "Password cannot be empty!" + ANSI_RESET);
            return;
        }

        userManager.registerUser(name, userName, pass);
        System.out.println();
        System.out.println(ANSI_GREEN + "User registered successfully!" + ANSI_RESET);
    }

    /**
     * Manages the user login functionality by collecting username and password inputs.
     * <p>
     * Validates the given credentials against the stored user details to determine successful
     * or failed login attempts.
     * </p>
     */
    void userLogin() {
        scanner.nextLine();
        System.out.print("Enter username : ");
        String username = scanner.nextLine();
        System.out.print("Enter password : ");
        String password = scanner.nextLine();
        System.out.println();
        RegisterUser user = userManager.login(username, password);
        if (user != null) {
            System.out.println(ANSI_GREEN + "Login successful. Welcome, " + user.getName() + "!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Login failed. Please check your username and password!!!" + ANSI_RESET);
        }
    }

    /**
     * Provides functionality for creating or joining chat rooms.
     * <p>
     * Lists all the available chat rooms and gives users the option to join an existing chat room
     * by specifying its ID or create a new one by entering a new chat room name.
     * </p>
     */
    void chatRoomCreation() {
        try {
            Scanner scanner = new Scanner(System.in);
            RegisterUser currentUser = userManager.getCurrentUser();

            // Fetch existing chat rooms from the database using the ChatRoomManager
            List<ChatRoom> existingChatRooms = databaseManager.fetchChatRooms();
            String title = ANSI_YELLOW + "------------------------" + ANSI_GREEN + "EXISTING CHATROOMS" + ANSI_RESET + ANSI_YELLOW + "-------------------------" + ANSI_RESET;
            int width = 130;
            String centeredTitle = String.format("%" + width + "s", title);
            System.out.println();
            System.out.println(centeredTitle);
            System.out.println();
            for (ChatRoom chatRoom : existingChatRooms) {
                System.out.println("Chat room id : " + ANSI_GREEN + chatRoom.getChatRoomId() + ANSI_RESET + ": " + ANSI_YELLOW + chatRoom.getRoomName() + ANSI_RESET);
            }
            System.out.println();

            System.out.print("Enter the chat room ID to join (or 0 to create a new chat room): ");
            int chatRoomId = scanner.nextInt();
            System.out.println();
            if (chatRoomId == 0) {
                scanner.nextLine();
                System.out.print("Enter new chat room name : ");
                String roomName = scanner.nextLine();

                ChatRoom selectedChatRoom = new ChatRoom(chatRoomManager.generateChatRoomId());
                selectedChatRoom.setRoomName(roomName);

                // Create the chat room and get its ID
                int createdChatRoomId = databaseManager.createChatRoom(selectedChatRoom, currentUser.getUserId());

                if (createdChatRoomId != -1) {
                    selectedChatRoom = databaseManager.getChatRoomById(createdChatRoomId);
                    selectedChatRoom.addParticipant(currentUser);
                    chatRoomManager.setCurrentChatRoom(selectedChatRoom);
                    System.out.println(ANSI_GREEN + "Chat room '" + ANSI_YELLOW + roomName + ANSI_RESET + ANSI_GREEN + "' created successfully!" + ANSI_RESET);

                    // Insert a record into UserChatRoom to associate the user with the chat room
                    databaseManager.insertUserToChatRoom(currentUser.getUserId(), createdChatRoomId);
                } else {
                    System.out.println(ANSI_RED + "Failed to create the chat room!!!" + ANSI_RESET);
                }
            } else {
                ChatRoom selectedChatRoom = databaseManager.getChatRoomById(chatRoomId);

                if (selectedChatRoom != null) {
                    selectedChatRoom.addParticipant(currentUser);
                    chatRoomManager.setCurrentChatRoom(selectedChatRoom);
                    System.out.println(ANSI_GREEN + "Hooray!Joined chat room with ID " + ANSI_YELLOW + chatRoomId + ANSI_RESET + ANSI_GREEN + " !" + ANSI_RESET);

                    // Insert a record into UserChatRoom to associate the user with the chat room
                    databaseManager.insertUserToChatRoom(currentUser.getUserId(), chatRoomId);
                } else {
                    System.out.println(ANSI_RED + "Chat room with ID " + ANSI_RESET + ANSI_YELLOW + chatRoomId + ANSI_RESET + ANSI_RED + " does not exist!!!!" + ANSI_RESET);
                }
            }
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error during chat room creation: " + ANSI_RESET + e.getMessage());
        }
    }

    /**
     * Enables users to send and receive messages within chat rooms.
     * <p>
     * Allows sending messages between users specified by usernames within a selected chat room.
     * </p>
     */
    void messageSendingReceiving() {
        try {
            scanner.nextLine();
            System.out.print("Enter sender's username : ");
            String senderUsername = scanner.nextLine();
            System.out.print("Enter receiver's username : ");
            String receiverUsername = scanner.nextLine();
            System.out.print("Enter message : ");
            String message = scanner.nextLine();

            RegisterUser sender = userManager.getUserByUsername(senderUsername);
            RegisterUser receiver = userManager.getUserByUsername(receiverUsername);

            if (sender != null && receiver != null) {
                List<ChatRoom> chatRooms = databaseManager.fetchChatRooms();
                if (!chatRooms.isEmpty()) {
                    ChatRoom selectedChatRoom = chatRoomManager.getCurrentChatRoom();

                    if (selectedChatRoom != null) {
                        String encrypt = selectedChatRoom.sendMessage(sender, message);
                        selectedChatRoom.receiveMessage(receiver, encrypt);
                        System.out.println();
                        System.out.println(ANSI_GREEN + "Message sent by " + ANSI_YELLOW + senderUsername + ANSI_GREEN + " and received by " + ANSI_YELLOW + receiverUsername + ANSI_GREEN + " successfully in chat room " + ANSI_YELLOW + selectedChatRoom.getRoomName() + ANSI_GREEN + " !");
                    } else {
                        System.out.println(ANSI_RED + "No chat room selected. Please create or join a chat room first!!!" + ANSI_RESET); // Without chatroom you can not send message
                    }
                } else {
                    System.out.println(ANSI_RED + "No chatrooms are availble!!!" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "User(s) not found. Please check usernames!!!" + ANSI_RESET); // If invaild username
            }

        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error sending or receiving message : " + ANSI_RESET + e.getMessage());
        }
    }

    /**
     * Provides a menu to interact with chat history-related features.
     * <p>
     * Offers options to display chat history stored in an ArrayList, save the chat history to a
     * temporary file, and load chat history from a temporary file.
     * </p>
     */
    void displayChatHistory() {
        while (true) {
            System.out.println();
            String title = ANSI_YELLOW + "------------------------" + ANSI_GREEN + "CHAT HISTORY MENU" + ANSI_YELLOW + "-------------------------" + ANSI_RESET;
            int width = 120;
            String centeredTitle = String.format("%" + width + "s", title);
            System.out.println();
            System.out.println(centeredTitle);
            System.out.println();
            System.out.println("1. Display chat history from ArrayList");
            System.out.println("2. Save chat history to a temp file");
            System.out.println("3. Load chat history from a temp file");
            System.out.println("4. Back to Main Menu");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            System.out.println();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayHistoryFromArrayList();
                    break;
                case 2:
                    saveHistoryToTempFile();
                    break;
                case 3:
                    loadHistoryFromTempFile();
                    break;
                case 4:
                    System.out.println(ANSI_GREEN + "EXITING FROM CHAT HISTORY MENU..." + ANSI_RESET);
                    return; // Return to the main loop
                default:
                    System.out.println(ANSI_RED + "Invalid choice. Please select a valid option." + ANSI_RESET);
                    break;
            }
        }
    }

    /**
     * Displays the chat history for the current chat room from an ArrayList.
     */
    private void displayHistoryFromArrayList() {
        System.out.println();
        System.out.println(ANSI_YELLOW + "CHATROOM HISTORY FROM ARRAYLIST" + ANSI_RESET);
        System.out.println();
        ChatRoom chatRoom = chatRoomManager.getCurrentChatRoom();
        if (chatRoom != null) {
            for (String message : chatRoom.getChatHistory()) {
                System.out.println(message);
            }
        } else {
            System.out.println(ANSI_RED + "No active chat room found!!!" + ANSI_RESET);
        }
    }

    /**
     * Allows users to store the chat history of the currently active chat room to a temporary file.
     * <p>
     * The chat history is saved to a file named after the chat room with a ".txt" extension.
     * </p>
     */
    private void saveHistoryToTempFile() {
        String savePath = chatRoomManager.getCurrentChatRoom().getRoomName() + ".txt";
        System.out.print(ANSI_GREEN + "Chat History will be saved to " + ANSI_RESET + ANSI_YELLOW + savePath + ANSI_GREEN + "..." + ANSI_RESET);
        System.out.println();
        ChatRoom chatRoom = chatRoomManager.getCurrentChatRoom();
        if (chatRoom != null) {
            boolean saveFlag = chatRoomManager.getCurrentChatRoom().saveChatHistoryToFile(savePath);

            if (saveFlag) {
                System.out.println(ANSI_GREEN + "Chat history saved successfully to " + ANSI_YELLOW + savePath + ANSI_GREEN + " !" + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Failed to save chat history!!!" + ANSI_RESET);
            }

        } else {
            System.out.println(ANSI_RED + "No active chat room found!!!" + ANSI_RESET);
        }
    }

    /**
     * Loads chat messages into the current chat room from a temporary file.
     * <p>
     * Fetches and loads messages from a file named after the chat room with a ".txt" extension.
     * </p>
     */
    private void loadHistoryFromTempFile() {
        String loadPath = chatRoomManager.getCurrentChatRoom().getRoomName() + ".txt";
        System.out.print(ANSI_GREEN + "Chat History will be loaded from " + ANSI_RESET + ANSI_YELLOW + loadPath + ANSI_GREEN + "..." + ANSI_RESET);

        ChatRoom chatRoom = chatRoomManager.getCurrentChatRoom();
        System.out.println();
        if (chatRoom != null) {
            boolean loadFlag = chatRoomManager.getCurrentChatRoom().loadMessagesFromFile(loadPath);

            if (loadFlag) {
                System.out.println(ANSI_GREEN + "Chat history loaded successfully form " + ANSI_YELLOW + loadPath + ANSI_GREEN + " !" + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Failed to load chat history!!!" + ANSI_RESET);
            }

        } else {
            System.out.println(ANSI_RED + "No active chat room found!!!" + ANSI_RESET);
        }
    }
    /**
     * Log out the current logged in user
     */

     private static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            RegisterUser currentUser = userManager.getCurrentUser();
            if (currentUser != null) {
                userManager.logout(currentUser);
                System.out.println("Logged out user '" + currentUser.getUserName() + "' on program exit.");
            }
        }));
}

    /**
     * Ends the application and presents a farewell message to the user.
     */
    void exitApplication() {
        System.out.println(ANSI_GREEN + "THANK YOU FOR USING OUR CHAT APPLICATION!!!" + ANSI_RESET);
        System.out.println();
        System.exit(0);
    }
}
