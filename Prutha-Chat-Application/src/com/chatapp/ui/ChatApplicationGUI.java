package com.chatapp.ui;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

import com.chatapp.database.DatabaseManager;
import com.chatapp.models.ChatRoom;
import com.chatapp.models.RegisterUser;
import com.chatapp.services.ChatRoomManager;
import com.chatapp.services.UserManager;

public class ChatApplicationGUI extends Application {
    private final DatabaseManager databaseManager = new DatabaseManager();
    private final UserManager userManager = new UserManager(databaseManager);
    private final ChatRoomManager chatRoomManager = new ChatRoomManager();
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");

        showWelcomePage();

        // Add shutdown hook to log out the current user on program exit
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            RegisterUser currentUser = userManager.getCurrentUser();
            if (currentUser != null) {
                userManager.logout(currentUser);
                System.out.println("Logged out user '" + currentUser.getUserName() + "' on program exit.");
            }
        }));
    }

    private void showWelcomePage() {
        // Create JavaFX UI components for the welcome page
        Label welcomeLabel = new Label("Welcome to Chat Application!");
        Button letsGoButton = new Button("Let's Chat");
        welcomeLabel.setStyle(
                "-fx-font-size: 45px; -fx-font-weight: bold;-fx-font-family: 'Monospace'; -fx-text-fill:#EEEEEE;");
        letsGoButton.setStyle(
                "-fx-font-size: 18px; -fx-font-family: 'Arial'; -fx-background-color: #C5E898; -fx-text-fill: #860A35; -fx-border-width: 2;-fx-border-radius: 5;");
        // welcomeLabel.setPadding(new Insets(50));
        // Set event handler for the "Let's Go" button
        letsGoButton.setOnAction(event -> showMainMenu());

        // Create a layout for the welcome page
        VBox welcomeLayout = new VBox(10);
        // welcomeLayout.setStyle("-fx-background-image: url('" +
        // getClass().getResource("chatbackground.jpg") + "'); ");

        welcomeLayout.setAlignment(Pos.CENTER);
        welcomeLayout.getChildren().addAll(welcomeLabel, letsGoButton);

        // Load background image
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource("hbg.jpg").toExternalForm(), true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));

        // Apply background image to the layout
        welcomeLayout.setBackground(new Background(backgroundImage));

        // Set the scene for the welcome page
        Scene welcomeScene = new Scene(welcomeLayout, 900, 800);
        // Set the scene for the welcome page
        // Scene welcomeScene = new Scene(welcomeLayout, 800, 600);

        primaryStage.setScene(welcomeScene);
        primaryStage.setTitle("Chat Application");
        primaryStage.show();
    }

    private void showMainMenu() {
        Label titleLabel = new Label("Chat Menu");
        titleLabel.setStyle(
                "-fx-font-size: 40px; -fx-font-weight: bold; -fx-font-family: 'Monospace'; -fx-text-fill: #265073");

        // Create JavaFX UI components
        Button registerButton = new Button("Register");
        addHoverAnimation(registerButton);
        registerButton.setStyle(
                "-fx-font-size: 20px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5px;");

        Button loginButton = new Button("Login");
        addHoverAnimation(loginButton);
        loginButton.setStyle(
                "-fx-font-size: 20px; -fx-background-color: #008CBA; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5px;");

        Button createChatRoomButton = new Button("Create Chat Room");
        addHoverAnimation(createChatRoomButton);
        createChatRoomButton.setStyle(
                "-fx-font-size: 20px; -fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5px;");

        Button sendMessageButton = new Button("Send Message");
        addHoverAnimation(sendMessageButton);
        sendMessageButton.setStyle(
                "-fx-font-size: 20px; -fx-background-color: #ff9800; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5px;");

        Button displayChatHistoryButton = new Button("Display Chat History");
        addHoverAnimation(displayChatHistoryButton);
        displayChatHistoryButton.setStyle(
                "-fx-font-size: 20px; -fx-background-color: #9c27b0; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5px;");

        Button exitButton = new Button("Exit");
        addHoverAnimation(exitButton);
        exitButton.setStyle(
                "-fx-font-size: 20px; -fx-background-color: #555555; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5px;");

        // Set event handlers for the buttons
        registerButton.setOnAction(event -> registerUser());
        loginButton.setOnAction(event -> loginUser());
        createChatRoomButton.setOnAction(event -> createChatRoom());
        sendMessageButton.setOnAction(event -> sendMessage());
        displayChatHistoryButton.setOnAction(event -> displayChatHistory());
        exitButton.setOnAction(event -> exitApplication());

        // Create a layout and add UI components to it
        GridPane layout = new GridPane();
        layout.setStyle("-fx-background-color: #EED3D9;");
        layout.setAlignment(Pos.CENTER);
        layout.setHgap(50);
        layout.setVgap(20);

        // Add buttons to the grid
        layout.add(titleLabel, 0, 0, 2, 1);
        GridPane.setMargin(titleLabel, new Insets(30, 0, 30, 0));
        layout.addRow(1, registerButton, loginButton);
        layout.addRow(2, createChatRoomButton, sendMessageButton);
        layout.addRow(3, displayChatHistoryButton, exitButton);
        // layout.addRow(3, exitButton);

        // Create a scene and add layout to it
        Scene scene = new Scene(layout, 400, 300);

        // Set the scene for the stage
        primaryStage.setScene(scene);

        // Set the title of the stage
        primaryStage.setTitle("Chat Application");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");

        // Show the stage
        primaryStage.show();
    }

    private void addHoverAnimation(Button button) {
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(200), button);
        scaleIn.setFromX(1.0);
        scaleIn.setFromY(1.0);
        scaleIn.setToX(1.2);
        scaleIn.setToY(1.2);

        button.setOnMouseEntered(event -> scaleIn.play());

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(200), button);
        scaleOut.setFromX(1.2);
        scaleOut.setFromY(1.2);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        button.setOnMouseExited(event -> scaleOut.play());
    }


    public void registerUser() {
        // Create JavaFX UI components for registration

        Label regLabel = new Label("Registration");
        regLabel.setStyle(
                "-fx-font-size: 40px; -fx-font-weight: bold; -fx-font-family: 'Monospace'; -fx-text-fill: #265073");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        nameField.setStyle(
                "-fx-pref-width: 260px; -fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Monospace'; -fx-text-fill: #265073"); 
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle(
                "-fx-pref-width: 250px;-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Monospace'; -fx-text-fill: #265073"); 

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle(
                "-fx-pref-width: 260px; -fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Monospace'; -fx-text-fill: #265073"); // field

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-font-size: 20px; -fx-background-color: #4CAF50; -fx-text-fill: white;");

        // Set event handler for registration
        registerButton.setOnAction(event -> {
            // Registration logic
            String name = nameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (!name.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                userManager.registerUser(name, username, password);
                showAlert(Alert.AlertType.INFORMATION, "Registration", "User registered successfully!");
                showMainMenu(); // Go back to the main menu after registration
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration", "Invalid input. Please try again.");
            }
        });

        // Create a grid pane for registration form
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(30);
        gridPane.setVgap(30);
        gridPane.setPadding(new Insets(20));
        gridPane.setMaxSize(500, 400);

        gridPane.add(regLabel, 0, 0, 2, 1);
        GridPane.setMargin(regLabel, new Insets(30, 0, 30, 0));
        // Add components to the grid pane
        Label name = new Label("Name:");
        name.setStyle("-fx-font-size: 20px;-fx-font-family: 'sans-serif'; -fx-text-fill: #265073");
        gridPane.add(name, 0, 1);
        gridPane.add(nameField, 1, 1);

        Label usern = new Label("Username:");
        usern.setStyle("-fx-font-size: 20px; -fx-font-family: 'sans-serif'; -fx-text-fill: #265073");
        gridPane.add(usern, 0, 2);
        gridPane.add(usernameField, 1, 2);

        Label passw = new Label("Password:");
        passw.setStyle("-fx-font-size: 20px; -fx-font-family: 'sans-serif';-fx-text-fill: #265073");
        gridPane.add(passw, 0, 3);
        gridPane.add(passwordField, 1, 3);
        GridPane.setMargin(registerButton, new Insets(30, 0, 30, 0));
        gridPane.add(registerButton, 1, 4, 2, 1);
        addHoverAnimation(registerButton);
        // Set the scene for registration
        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
    }

    private void loginUser() {
        // Create JavaFX UI components for login
        Label logLabel = new Label("Login");
        logLabel.setStyle(
                "-fx-font-size: 40px; -fx-font-weight: bold; -fx-font-family: 'Monospace'; -fx-text-fill: #265073");
        Label usernameLabel = new Label("Username:");
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle(
            "-fx-pref-width: 256px;-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Monospace'; -fx-text-fill: #265073"
        );
    
        Label passwordLabel = new Label("Password:");
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle(
            "-fx-pref-width: 256px;-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Monospace'; -fx-text-fill: #265073"
        );
    
        Button loginButton = new Button("Login");
        loginButton.setStyle(
                "-fx-font-size: 16px; " + /* Font size */
                "-fx-font-weight: bold; " + /* Font weight */
                "-fx-background-color: #4CAF50; " + /* Background color */
                "-fx-text-fill: white; " + /* Text color */
                "-fx-background-radius: 10; " + /* Rounded corners */
                "-fx-padding: 10px 20px;" /* Padding */
        );
    
        // Set event handler for login
        loginButton.setOnAction(event -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
    
            RegisterUser user = userManager.login(username, password);
            if (user != null) {
                showAlert(Alert.AlertType.INFORMATION, "Login", "Login successful. Welcome, " + user.getName() + "!");
                showMainMenu(); // Go back to the main menu after registration
                // Perform actions after successful login (if needed)
            } else {
                showAlert(Alert.AlertType.ERROR, "Login", "Login failed. Please check your username and password!!!");
            }
        });
    
        // Create a layout using GridPane for login
        GridPane loginLayout = new GridPane();
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setHgap(30);
        loginLayout.setVgap(40);
        loginLayout.setPadding(new Insets(20));
        loginLayout.add(logLabel, 0, 0, 2, 1);
        GridPane.setMargin(logLabel, new Insets(30, 0, 30, 0));
        // Add components to the grid
        loginLayout.add(usernameLabel, 0, 1);
        usernameLabel.setStyle("-fx-font-size: 20px;-fx-font-family: 'sans-serif'; -fx-text-fill: #265073");
        loginLayout.add(usernameField, 1, 1);
        loginLayout.add(passwordLabel, 0, 2);
        passwordLabel.setStyle("-fx-font-size: 20px;-fx-font-family: 'sans-serif'; -fx-text-fill: #265073");
        loginLayout.add(passwordField, 1, 2);
        GridPane.setMargin(loginButton, new Insets(30, 0, 30, 0));
        addHoverAnimation(loginButton);
        loginLayout.add(loginButton, 1, 3);

        loginLayout.setStyle("-fx-background-color:#EEF5FF");
    
        // Set the scene for login
        primaryStage.setScene(new Scene(loginLayout, 400, 300));
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
    }
    
    

    private void createChatRoom() {
        RegisterUser currentUser = userManager.getCurrentUser(); // Get the current user
        if (currentUser != null) {
            // Fetch existing chat rooms from the database using the ChatRoomManager
            List<ChatRoom> existingChatRooms = databaseManager.fetchChatRooms();

            VBox layout = new VBox(10);
            Label titleLabel = new Label("Select or create a chat room:");

            // Create a combo box for existing chat rooms
            ComboBox<String> chatRoomsComboBox = new ComboBox<>();
            for (ChatRoom chatRoom : existingChatRooms) {
                chatRoomsComboBox.getItems().add(chatRoom.getRoomName());
            }

            // Create a text field for entering a new chat room name
            TextField newChatRoomField = new TextField();
            newChatRoomField.setPromptText("Enter new chat room name");

            // Create a button for either joining an existing room or creating a new one
            Button createButton = new Button("Create/Join");
            createButton.setOnAction(event -> {
                String selectedRoom = chatRoomsComboBox.getValue();
                if (selectedRoom != null && !selectedRoom.isEmpty()) {
                    // Join the selected chat room
                    ChatRoom selectedChatRoom = databaseManager.getChatRoomByName(selectedRoom);
                    selectedChatRoom.addParticipant(currentUser);
                    chatRoomManager.setCurrentChatRoom(selectedChatRoom);
                    showAlert(Alert.AlertType.INFORMATION, "Login", "Joined chat room: " + selectedRoom );
                    System.out.println("Joined chat room: " + selectedRoom);
                } else {
                    // Create a new chat room
                    String newRoomName = newChatRoomField.getText().trim();
                    if (!newRoomName.isEmpty()) {
                        ChatRoom newChatRoom = new ChatRoom(chatRoomManager.generateChatRoomId());
                        newChatRoom.setRoomName(newRoomName);
                        newChatRoom.addParticipant(currentUser);
                        databaseManager.createChatRoom(newChatRoom, currentUser.getUserId());
                        chatRoomManager.setCurrentChatRoom(newChatRoom);

                        showAlert(Alert.AlertType.INFORMATION, "Chat room", "Chat room '" + newRoomName + "' created and joined successfully");

                        // System.out.println("Chat room '" + newRoomName + "' created and joined successfully!");
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Chat room", "Please enter a valid chat room name!");
                        // System.out.println("Please enter a valid chat room name!");
                    }
                }
                // Return to main menu
                showMainMenu();
            });

            layout.getChildren().addAll(titleLabel, chatRoomsComboBox, newChatRoomField, createButton);
            // Set the scene for chat room creation/joining
            primaryStage.setScene(new Scene(layout, 400, 300));
        } else {
            showAlert(Alert.AlertType.ERROR, "Login failed", "Chatroom access failed : User not logged in!");
            System.out.println("Error: User not logged in."); // Display error message if user is not logged in
        }
    }

    private void sendMessage() {
        RegisterUser currentUser = userManager.getCurrentUser(); // Get the current user
        if (currentUser != null) {
            // Create JavaFX UI components for sending a message
            Label sLabel = new Label("Sender's username ");
            TextField senderField = new TextField();
            senderField.setPromptText("Enter sender's username");

            Label rLabel = new Label("Receiver's username ");
            TextField receiverField = new TextField();
            receiverField.setPromptText("Enter receiver's username");

            Label mLabel = new Label("Message ");
            TextArea messageTextArea = new TextArea();
            messageTextArea.setPromptText("Type your message here...");
            messageTextArea.setPrefRowCount(5); // Set preferred row count for the text area
    
            Button sendButton = new Button("Send");
            sendButton.setOnAction(event -> {
                String senderUsername = senderField.getText().trim(); // Get the sender's username
                String receiverUsername = receiverField.getText().trim(); // Get the receiver's username
                String messageContent = messageTextArea.getText().trim(); // Get the message content
    
                if (!senderUsername.isEmpty() && !receiverUsername.isEmpty() && !messageContent.isEmpty()) {
                    // Check if both sender and receiver are logged in
                    if (userManager.isLoggedIn(senderUsername) && userManager.isLoggedIn(receiverUsername)) {
                        RegisterUser senderUser = userManager.getUserByUsername(senderUsername);
                        RegisterUser receiverUser = userManager.getUserByUsername(receiverUsername);
    
                        // Proceed with sending the message
                        List<ChatRoom> chatRooms = databaseManager.fetchChatRooms();
                        if (!chatRooms.isEmpty()) {
                            ChatRoom selectedChatRoom = chatRoomManager.getCurrentChatRoom();
    
                            if (selectedChatRoom != null) {
                                String encrypt = selectedChatRoom.sendMessage(senderUser, messageContent);
                                selectedChatRoom.receiveMessage(receiverUser, encrypt);
                                showAlert(Alert.AlertType.INFORMATION, "Message Sent", "Message sent by " + senderUsername + " and received by " + receiverUsername + " successfully!");
                                showMainMenu(); // Go back to the main menu after sending the message
                            } else {
                                showAlert(Alert.AlertType.ERROR, "Error", "No chat room selected. Please create or join a chat room first.");
                            }
                        } else {
                            showAlert(Alert.AlertType.ERROR, "Error", "No chat rooms available.");
                        }
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "One or both users are not logged in.");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Please enter sender's username, receiver's username, and message.");
                }
            });
    
            // Create a layout for sending messages
            VBox sendMessageLayout = new VBox(10);
            sendMessageLayout.setAlignment(Pos.CENTER);
            sendMessageLayout.setPadding(new Insets(20));
            sendMessageLayout.setStyle("-fx-background-color: #f2f2f2; -fx-padding: 20px;");
    
            sendMessageLayout.getChildren().addAll(sLabel,senderField,rLabel, receiverField, mLabel,messageTextArea, sendButton);
    
            // Set the scene for sending messages
            primaryStage.setScene(new Scene(sendMessageLayout, 400, 300));
            primaryStage.setTitle("Send Message");
            primaryStage.show();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Send message failed: User not logged in.");
        }
    }
    
    private void displayChatHistory() {
        // Implement display chat history functionality here using JavaFX elements
    }

    private void exitApplication() {
        System.exit(0);
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.initOwner(primaryStage);
        alert.setHeaderText(null); // Remove default header text
        alert.setContentText(content);
        alert.setWidth(250);

        // Apply inline CSS for custom styling
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
                "-fx-background-color: #C6DCBA; -fx-font-size: 18px; -fx-padding: 20px;" /* Padding */
        );
         
        dialogPane.setPrefWidth(600);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
