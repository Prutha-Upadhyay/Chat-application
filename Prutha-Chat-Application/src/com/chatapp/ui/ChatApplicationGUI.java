package com.chatapp.ui;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.InputStream;
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
    RegisterUser senderUser;
    RegisterUser receiverUser;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");

        showWelcomePage();

        // Add shutdown hook to log out the current user on program exit
        // Add shutdown hook to log out all logged-in users on program exit
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            userManager.logoutAllUsersOnExit();
        }));

    }

    // private void showWelcomePage() {
    // // Create JavaFX UI components for the welcome page
    // Label welcomeLabel = new Label("Welcome to ");
    // Label welcomeLabel1 = new Label(" Chat Application! ");
    // Button letsGoButton = new Button("Let's Chat");
    // welcomeLabel.setStyle(
    // "-fx-font-size: 45px; -fx-font-weight: bold;-fx-font-family: 'Monospace';
    // -fx-text-fill:#F0251C;");
    // welcomeLabel1.setStyle(
    // "-fx-font-size: 45px; -fx-font-weight: bold;-fx-font-family: 'Monospace';
    // -fx-text-fill:#F0251C;");
    // letsGoButton.setStyle(
    // "-fx-font-size: 20px; -fx-background-color: #008CBA; -fx-text-fill: white;
    // -fx-padding: 10px 20px; -fx-border-radius: 5px;");
    // // welcomeLabel.setPadding(new Insets(50));
    // // Set event handler for the "Let's Go" button
    // letsGoButton.setOnAction(event -> showMainMenu());

    // // Create a layout for the welcome page
    // VBox welcomeLayout = new VBox(10);

    // welcomeLayout.setAlignment(Pos.CENTER);
    // welcomeLayout.getChildren().addAll(welcomeLabel,welcomeLabel1, letsGoButton);

    // // Load background image
    // BackgroundImage backgroundImage = new BackgroundImage(
    // new Image(getClass().getResource("10179762.jpg").toExternalForm(), true),
    // BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
    // BackgroundPosition.DEFAULT,
    // new BackgroundSize(1.0, 1.0, true, true, false, false));

    // // Apply background image to the layout
    // welcomeLayout.setBackground(new Background(backgroundImage));

    // // Set the scene for the welcome page
    // Scene welcomeScene = new Scene(welcomeLayout, 900, 800);
    // // Set the scene for the welcome page
    // // Scene welcomeScene = new Scene(welcomeLayout, 800, 600);

    // primaryStage.setScene(welcomeScene);
    // primaryStage.setTitle("Chat Application");
    // primaryStage.show();
    // }
    private void showWelcomePage() {
        // Create JavaFX UI components for the welcome page
        Label welcomeLabel = new Label("Welcome to");
        Label appNameLabel = new Label("Chat Application!");
        Button letsGoButton = new Button("Let's Chat");

        // Set styles for the labels and button
        welcomeLabel.setStyle(
                "-fx-font-size: 45px; -fx-font-weight: bold; -fx-font-family: 'Monospace'; -fx-text-fill: #F0251C;");
        appNameLabel.setStyle(
                "-fx-font-size: 45px; -fx-font-weight: bold; -fx-font-family: 'Monospace'; -fx-text-fill: #F0251C;");
        letsGoButton.setStyle(
                "-fx-font-size: 20px; -fx-background-color: #1D5180; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5px;");

        // Set event handler for the "Let's Go" button
        letsGoButton.setOnAction(event -> showMainMenu());
        addHoverAnimation(letsGoButton);
        // Create a layout for the welcome page using GridPane
        GridPane welcomeLayout = new GridPane();
        welcomeLayout.setAlignment(Pos.CENTER);
        welcomeLayout.setVgap(20);

        // Add components to the GridPane
        welcomeLayout.add(welcomeLabel, 0, 0, 2, 1);
        welcomeLayout.add(appNameLabel, 0, 1, 2, 1);
        welcomeLayout.add(letsGoButton, 0, 2, 2, 1);

        // Set column constraints
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.CENTER);
        welcomeLayout.getColumnConstraints().add(columnConstraints);

        // Load background image
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource("10179762.jpg").toExternalForm(), true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));

        // Apply background image to the layout
        welcomeLayout.setBackground(new Background(backgroundImage));

        // Set the scene for the welcome page
        Scene welcomeScene = new Scene(welcomeLayout, 900, 800);

        primaryStage.setScene(welcomeScene);
        primaryStage.setTitle("Chat Application");
        primaryStage.show();
    }

    private void showMainMenu() {
        Label titleLabel = new Label("Chat Menu");
        titleLabel.setStyle(
                "-fx-font-size: 40px; -fx-font-weight: bold; -fx-font-family: 'Monospace'; -fx-text-fill: #A229EA");

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
        // layout.setStyle("-fx-background-color: #EED3D9;");
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setHgap(50);
        layout.setVgap(20);
        layout.setPadding(new Insets(130));
        // Add buttons to the grid
        layout.add(titleLabel, 0, 0, 2, 1);
        GridPane.setMargin(titleLabel, new Insets(30, 0, 30, 0));
        layout.addRow(1, registerButton, loginButton);
        layout.addRow(2, createChatRoomButton, sendMessageButton);
        layout.addRow(3, displayChatHistoryButton, exitButton);
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource("8573475.jpg").toExternalForm(), true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));

        // Apply background image to the layout
        layout.setBackground(new Background(backgroundImage));

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
                "-fx-font-size: 45px; -fx-font-weight: bolder; -fx-font-family: 'Monospace'; -fx-text-fill: #F6EEE0");
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
        registerButton.setStyle("-fx-font-size: 20px; -fx-background-color: #370E08; -fx-text-fill: #F2DCC6;");
        Button backButton = new Button("Back");
        // Styling the back button
        backButton.setStyle("-fx-font-size: 20px; -fx-background-color: #370E08; -fx-text-fill: #F2DCC6;");

        // Add event handler to the back button
        backButton.setOnAction(event -> {
            primaryStage.close(); // Close the current window
            showMainMenu(); // Open the main menu window
        });

        // Set event handler for registration
        registerButton.setOnAction(event -> {
            // Registration logic
            String name = nameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (!name.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                if (isValidPassword(password)) {
                    userManager.registerUser(name, username, password);
                    showAlert(Alert.AlertType.INFORMATION, "Registration", "User registered successfully!");
                    showMainMenu(); // Go back to the main menu after registration
                } else {
                    showAlert(Alert.AlertType.ERROR, "Registration",
                            "Password must be at least 8 characters long and contain at least one digit, one uppercase letter, and one special character.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration", "Invalid input. Please try again.");
            }
        });

        // Create a grid pane for registration form
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER_LEFT);
        gridPane.setHgap(30);
        gridPane.setVgap(30);
        gridPane.setPadding(new Insets(20));
        gridPane.setMaxSize(500, 400);

        gridPane.add(regLabel, 0, 0, 2, 1);
        GridPane.setMargin(regLabel, new Insets(30, 0, 30, 0));
        // Add components to the grid pane
        Label name = new Label("Name ");
        name.setStyle("-fx-font-size: 20px;-fx-font-family:'Helvetica';-fx-font-weight: bold; -fx-text-fill: #F2DCC6");
        gridPane.add(name, 0, 1);
        gridPane.add(nameField, 1, 1);
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource("8285686.jpg").toExternalForm(), true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));

        // Apply background image to the layout
        gridPane.setBackground(new Background(backgroundImage));

        Label usern = new Label("Username ");
        usern.setStyle(
                "-fx-font-size: 20px; -fx-font-family:'Helvetica';-fx-font-weight: bold; -fx-text-fill: #F2DCC6");
        gridPane.add(usern, 0, 2);
        gridPane.add(usernameField, 1, 2);

        Label passw = new Label("Password ");
        passw.setStyle("-fx-font-size: 20px; -fx-font-family:'Helvetica';-fx-font-weight: bold;-fx-text-fill: #F2DCC6");
        gridPane.add(passw, 0, 3);
        gridPane.add(passwordField, 1, 3);
        // GridPane.setMargin(registerButton, new Insets(30, 0, 0, 0));
        // GridPane.setMargin(backButton, new Insets(30, 0, 0, 0));
        gridPane.add(registerButton, 1, 5);
        // GridPane.setMargin(backButton, new Insets(30, 0, 30, 0));
        gridPane.add(backButton, 1, 6);
        addHoverAnimation(registerButton);
        addHoverAnimation(backButton);
        // Set the scene for registration
        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");

    }

    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long and contain at least one digit,
        // one uppercase letter, and one special character
        String regex = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return password.matches(regex);
    }

    private void loginUser() {
        // Create JavaFX UI components for login
        Label logLabel = new Label("Login");
        logLabel.setStyle(
                "-fx-font-size: 45px; -fx-font-weight: bold; -fx-font-family: 'Monospace'; -fx-text-fill: #3D550C");
        Label usernameLabel = new Label("Username  ");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle(
                "-fx-pref-width: 256px;-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Monospace'; -fx-text-fill: #265073");

        Label passwordLabel = new Label("Password ");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle(
                "-fx-pref-width: 256px;-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Monospace'; -fx-text-fill: #265073");

        Button loginButton = new Button("Login");
        loginButton.setStyle(
                "-fx-font-size: 16px; " + /* Font size */
                        "-fx-font-weight: bold; " + /* Font weight */
                        "-fx-background-color: #CFB51B; " + /* Background color */
                        "-fx-text-fill:#A13380; " + /* Text color */
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
        Button backButton = new Button("Back");
        // Styling the back button
        backButton.setStyle(
                "-fx-font-size: 16px;-fx-font-weight: bold;-fx-background-color:#CFB51B;  -fx-text-fill:#A13380;-fx-background-radius: 10;-fx-padding: 10px 20px;");

        // Add event handler to the back button
        backButton.setOnAction(event -> {
            primaryStage.close(); // Close the current window
            showMainMenu(); // Open the main menu window
        });

        // Create a layout using GridPane for login
        GridPane loginLayout = new GridPane();
        loginLayout.setAlignment(Pos.CENTER_RIGHT);
        loginLayout.setHgap(40);
        loginLayout.setVgap(40);
        loginLayout.setPadding(new Insets(150));
        // loginLayout.setMaxWidth(500);
        // GridPane.setMargin(loginLayout, new Insets(1000));
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource("9594696.jpg").toExternalForm(), true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));

        // Apply background image to the layout
        loginLayout.setBackground(new Background(backgroundImage));
        loginLayout.add(logLabel, 0, 0, 2, 1);
        GridPane.setMargin(logLabel, new Insets(30, 0, 30, 0));
        // Add components to the grid
        loginLayout.add(usernameLabel, 0, 1);
        usernameLabel.setStyle(
                "-fx-font-size: 22px; -fx-font-family:'Helvetica';-fx-font-weight: bold; -fx-text-fill: #A13380");
        loginLayout.add(usernameField, 1, 1);
        loginLayout.add(passwordLabel, 0, 2);
        passwordLabel.setStyle(
                "-fx-font-size: 22px; -fx-font-family:'Helvetica';-fx-font-weight: bold; -fx-text-fill: #A13380");
        loginLayout.add(passwordField, 1, 2);
        // GridPane.setMargin(loginButton, new Insets(30, 0, 0, 0));
        addHoverAnimation(loginButton);
        loginLayout.add(loginButton, 0, 3);
        loginLayout.add(backButton, 1, 3);

        // loginLayout.setStyle("-fx-background-color:#EEF5FF");

        // Set the scene for login
        primaryStage.setScene(new Scene(loginLayout, 400, 300));
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
    }

    // private void createChatRoom() {
    // RegisterUser currentUser = userManager.getCurrentUser(); // Get the current
    // user
    // if (currentUser != null) {
    // // Fetch existing chat rooms from the database using the ChatRoomManager
    // List<ChatRoom> existingChatRooms = databaseManager.fetchChatRooms();

    // // Main title
    // Label mainTitleLabel = new Label("Create or Join Chat Room");
    // mainTitleLabel.setStyle("-fx-font-size: 45px; -fx-font-weight: bold;");

    // // Section title for joining existing chat rooms
    // Label existingTitleLabel = new Label("Join Existing Chat Room");
    // existingTitleLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

    // // Combo box for existing chat rooms
    // ComboBox<String> chatRoomsComboBox = new ComboBox<>();
    // for (ChatRoom chatRoom : existingChatRooms) {
    // chatRoomsComboBox.getItems().add(chatRoom.getRoomName());
    // }

    // chatRoomsComboBox.setStyle("-fx-background-color: white; " +
    // "-fx-border-color: gray; " +
    // "-fx-border-width: 2px; " +
    // "-fx-border-radius: 5px; " +
    // "-fx-padding: 5px;");
    // chatRoomsComboBox.setPrefWidth(200);
    // // Button for joining existing chat room
    // Button joinButton = new Button("Join");
    // joinButton.setStyle(
    // "-fx-font-size: 16px; " + /* Font size */
    // "-fx-font-weight: bold; " + /* Font weight */
    // "-fx-background-color: #4CAF50; " + /* Background color */
    // "-fx-text-fill: white; " + /* Text color */
    // "-fx-background-radius: 10; " + /* Rounded corners */
    // "-fx-padding: 10px 40px;" /* Padding */
    // );
    // // joinButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50;
    // -fx-text-fill: white;");
    // joinButton.setOnAction(event -> {
    // String selectedRoom = chatRoomsComboBox.getValue();
    // if (selectedRoom != null && !selectedRoom.isEmpty()) {
    // // Join the selected chat room
    // ChatRoom selectedChatRoom = databaseManager.getChatRoomByName(selectedRoom);
    // selectedChatRoom.addParticipant(currentUser);
    // chatRoomManager.setCurrentChatRoom(selectedChatRoom);
    // showAlert(Alert.AlertType.INFORMATION, "Login", "Joined chat room: " +
    // selectedRoom);
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Chat room", "Please select a chat room to
    // join!");
    // }
    // // Return to main menu
    // showMainMenu();
    // });

    // // Section title for creating/joining new chat rooms
    // Label createTitleLabel = new Label("Create New Chat Room");
    // createTitleLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

    // // Text field for new chat room name
    // TextField newChatRoomField = new TextField();
    // newChatRoomField.setPromptText("Enter New Chat Room Name");

    // Separator separator = new Separator();
    // separator.setPrefWidth(900); // Adjust width as needed
    // separator.setMaxWidth(1000);

    // // Button for creating new chat room
    // Button createButton = new Button("Create & Join");
    // // createButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50;
    // -fx-text-fill: white;");
    // createButton.setStyle(
    // "-fx-font-size: 16px; " + /* Font size */
    // "-fx-font-weight: bold; " + /* Font weight */
    // "-fx-background-color: #4CAF50; " + /* Background color */
    // "-fx-text-fill: white; " + /* Text color */
    // "-fx-background-radius: 10; " + /* Rounded corners */
    // "-fx-padding: 10px 30px;" /* Padding */
    // );
    // createButton.setOnAction(event -> {
    // String newRoomName = newChatRoomField.getText().trim();
    // if (!newRoomName.isEmpty()) {
    // ChatRoom newChatRoom = new ChatRoom(chatRoomManager.generateChatRoomId());
    // newChatRoom.setRoomName(newRoomName);
    // newChatRoom.addParticipant(currentUser);
    // databaseManager.createChatRoom(newChatRoom, currentUser.getUserId());
    // chatRoomManager.setCurrentChatRoom(newChatRoom);
    // showAlert(Alert.AlertType.INFORMATION, "Chat room",
    // "Chat room '" + newRoomName + "' created and joined successfully");
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Chat room", "Please enter a valid chat room
    // name!");
    // }
    // // Return to main menu
    // showMainMenu();
    // });

    // // GridPane to arrange sections vertically and center them
    // GridPane gridPane = new GridPane();
    // gridPane.setAlignment(Pos.CENTER);
    // gridPane.setVgap(30); // Vertical gap between sections

    // // Add components to the GridPane
    // gridPane.add(mainTitleLabel, 0, 0, 2, 1);
    // gridPane.add(existingTitleLabel, 0, 1);
    // gridPane.add(chatRoomsComboBox, 0, 2, 2, 1);
    // gridPane.add(joinButton, 0,3);
    // GridPane.setMargin(joinButton, new Insets(9, 0, 0, 0));
    // gridPane.add(separator, 0, 4,2,1);
    // gridPane.add(createTitleLabel, 0, 5);
    // gridPane.add(newChatRoomField, 0, 6, 2, 1);
    // gridPane.add(createButton, 0, 7);
    // GridPane.setMargin(joinButton, new Insets(9, 0, 0, 0));

    // // Set the scene
    // primaryStage.setScene(new Scene(gridPane, 600, 400)); // Adjust size as
    // needed
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Login failed", "Chatroom access failed:
    // User not logged in!");
    // System.out.println("Error: User not logged in."); // Display error message if
    // user is not logged in
    // }
    // }

    // private void createChatRoom() {
    // RegisterUser currentUser = userManager.getCurrentUser(); // Get the current
    // user
    // if (currentUser != null) {
    // // Fetch existing chat rooms from the database using the ChatRoomManager
    // List<ChatRoom> existingChatRooms = databaseManager.fetchChatRooms();

    // // Main title
    // Label mainTitleLabel = new Label("Create or Join Chat Room");
    // mainTitleLabel.setStyle("-fx-font-size: 45px; -fx-font-weight: bold;");

    // // Section title for joining existing chat rooms
    // Label existingTitleLabel = new Label("Join Existing Chat Room");
    // existingTitleLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

    // // Combo box for existing chat rooms
    // ComboBox<String> chatRoomsComboBox = new ComboBox<>();
    // for (ChatRoom chatRoom : existingChatRooms) {
    // chatRoomsComboBox.getItems().add(chatRoom.getRoomName());
    // }

    // chatRoomsComboBox.setStyle("-fx-background-color: white; " +
    // "-fx-border-color: gray; " +
    // "-fx-border-width: 2px; " +
    // "-fx-border-radius: 5px; " +
    // "-fx-padding: 5px;");
    // chatRoomsComboBox.setPrefWidth(200);

    // // Button for joining existing chat room
    // Button joinButton = new Button("Join");
    // joinButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
    // joinButton.setOnAction(event -> {
    // String selectedRoom = chatRoomsComboBox.getValue();
    // if (selectedRoom != null && !selectedRoom.isEmpty()) {
    // // Join the selected chat room
    // ChatRoom selectedChatRoom = databaseManager.getChatRoomByName(selectedRoom);
    // if (selectedChatRoom != null) {
    // selectedChatRoom.setRoomName(selectedRoom);
    // selectedChatRoom.addParticipant(currentUser);
    // chatRoomManager.setCurrentChatRoom(selectedChatRoom);
    // showAlert(Alert.AlertType.INFORMATION, "Login", "Joined chat room: " +
    // selectedRoom);
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Chat room", "Failed to join chat room: " +
    // selectedRoom);
    // }
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Chat room", "Please select a chat room to
    // join!");
    // }
    // // Return to main menu
    // showMainMenu();
    // });

    // // Section title for creating new chat room
    // Label createTitleLabel = new Label("Create New Chat Room");
    // createTitleLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

    // // Text field for new chat room name
    // TextField newChatRoomField = new TextField();
    // newChatRoomField.setPromptText("Enter New Chat Room Name");

    // // Button for creating new chat room
    // Button createButton = new Button("Create & Join");
    // createButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
    // createButton.setOnAction(event -> {
    // String newRoomName = newChatRoomField.getText().trim();
    // if (!newRoomName.isEmpty()) {
    // ChatRoom newChatRoom = new ChatRoom(chatRoomManager.generateChatRoomId());
    // newChatRoom.setRoomName(newRoomName);
    // newChatRoom.addParticipant(currentUser);
    // databaseManager.createChatRoom(newChatRoom, currentUser.getUserId());
    // chatRoomManager.setCurrentChatRoom(newChatRoom);
    // showAlert(Alert.AlertType.INFORMATION, "Chat room",
    // "Chat room '" + newRoomName + "' created and joined successfully");
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Chat room", "Please enter a valid chat room
    // name!");
    // }
    // // Return to main menu
    // showMainMenu();
    // });

    // // GridPane to arrange sections vertically and center them
    // GridPane gridPane = new GridPane();
    // gridPane.setAlignment(Pos.CENTER);
    // gridPane.setVgap(30); // Vertical gap between sections

    // // Add components to the GridPane
    // gridPane.add(mainTitleLabel, 0, 0, 2, 1);
    // gridPane.add(existingTitleLabel, 0, 1);
    // gridPane.add(chatRoomsComboBox, 0, 2, 2, 1);
    // gridPane.add(joinButton, 0, 3);
    // GridPane.setMargin(joinButton, new Insets(9, 0, 0, 0));
    // gridPane.add(createTitleLabel, 0, 4);
    // gridPane.add(newChatRoomField, 0, 5, 2, 1);
    // gridPane.add(createButton, 0, 6);

    // // Set the scene
    // primaryStage.setScene(new Scene(gridPane, 600, 400)); // Adjust size as
    // needed
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Login failed", "Chatroom access failed:
    // User not logged in!");
    // System.out.println("Error: User not logged in."); // Display error message if
    // user is not logged in
    // }
    // }

    private void createChatRoom() {
        RegisterUser currentUser = userManager.getCurrentUser();
        if (currentUser != null) {
            List<ChatRoom> existingChatRooms = databaseManager.fetchChatRooms();

            // Main title
            Label mainTitleLabel = new Label("Create or Join Chat Room");

            // Section title for joining existing chat rooms
            Label existingTitleLabel = new Label("Join Existing Chat Room");
            existingTitleLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: #007BFF;");

            // Combo box for existing chat rooms
            ComboBox<String> chatRoomsComboBox = new ComboBox<>();
            for (ChatRoom chatRoom : existingChatRooms) {
                chatRoomsComboBox.getItems().add(chatRoom.getRoomName());
            }

            chatRoomsComboBox.setStyle("-fx-background-color: white; " +
                    "-fx-border-color: gray; " +
                    "-fx-border-width: 2px; " +
                    "-fx-border-radius: 5px; " +
                    "-fx-padding: 5px; " +
                    "-fx-font-size: 16px;");
            chatRoomsComboBox.setPrefWidth(200);

            // Button for joining existing chat room
            Button joinButton = new Button("Join");
            joinButton.setStyle(
                    "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #007BFF; -fx-padding: 5px 10px; -fx-text-fill: white; -fx-border-radius: 5px;");
            joinButton.setOnAction(event -> {
                String selectedRoom = chatRoomsComboBox.getValue();
                if (selectedRoom != null && !selectedRoom.isEmpty()) {
                    ChatRoom selectedChatRoom = databaseManager.getChatRoomByName(selectedRoom);
                    if (selectedChatRoom != null) {
                        selectedChatRoom.setRoomName(selectedRoom);
                        selectedChatRoom.addParticipant(currentUser);
                        chatRoomManager.setCurrentChatRoom(selectedChatRoom);
                        showAlert(Alert.AlertType.INFORMATION, "Login", "Joined chat room: " + selectedRoom);
                        showMainMenu();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Chat room", "Failed to join chat room: " + selectedRoom);
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Chat room", "Please select a chat room to join!");
                }

            });
            Button backButton = new Button("Back");
            // Styling the back button
            backButton.setStyle(
                    "-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color:#4DA5C9; -fx-text-fill: #E9EADB; -fx-padding:10px 20px");

            // Add event handler to the back button
            backButton.setOnAction(event -> {
                primaryStage.close(); // Close the current window
                showMainMenu(); // Open the main menu window
            });

            // Section title for creating new chat room
            Label createTitleLabel = new Label("Create New Chat Room");
            createTitleLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: #007BFF;");

            // Text field for new chat room name
            TextField newChatRoomField = new TextField();
            newChatRoomField.setPromptText("Enter New Chat Room Name");
            newChatRoomField.setStyle(
                    "-fx-background-color: white; -fx-border-color: gray; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-padding: 5px; -fx-font-size: 16px;");

            // Button for creating new chat room
            Button createButton = new Button("Create & Join");
            createButton.setOnAction(event -> {
                String newRoomName = newChatRoomField.getText().trim();
                if (!newRoomName.isEmpty()) {
                    ChatRoom newChatRoom = new ChatRoom(chatRoomManager.generateChatRoomId());
                    newChatRoom.setRoomName(newRoomName);
                    newChatRoom.addParticipant(currentUser);
                    databaseManager.createChatRoom(newChatRoom, currentUser.getUserId());
                    chatRoomManager.setCurrentChatRoom(newChatRoom);
                    showAlert(Alert.AlertType.INFORMATION, "Chat room",
                            "Chat room '" + newRoomName + "' created and joined successfully");
                    showMainMenu();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Chat room", "Please enter a valid chat room name!");
                }

            });

            // GridPane to arrange sections vertically and center them
            GridPane gridPane = new GridPane();

            // Style the GridPane
            gridPane.setAlignment(Pos.CENTER_LEFT);
            gridPane.setHgap(20); // Horizontal gap between components
            gridPane.setVgap(30); // Vertical gap between components
            gridPane.setPadding(new Insets(70)); // Add padding around the GridPane

            // Style main title label
            mainTitleLabel.setStyle(
                    "-fx-font-size: 45px; -fx-font-weight: bolder; -fx-text-fill: #4DA5C9;-fx-font-family:'Monospace'");

            // Style existing chat room title label
            existingTitleLabel.setStyle(
                    "-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: #7A828D; -fx-font-family:'Verdana'");

            // Style create chat room title label
            createTitleLabel.setStyle(
                    "-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: #7A828D; -fx-font-family:'Verdana'");

            // Style text field for new chat room name
            newChatRoomField.setStyle("-fx-font-size: 18px; -fx-font-family:'Verdana'");

            // Style buttons
            joinButton.setStyle(
                    "-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: #4DA5C9; -fx-text-fill: #E9EADB; -fx-font-family:'Verdana' ;-fx-padding:10px 50px");
            createButton.setStyle(
                    "-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color:#4DA5C9; -fx-text-fill: #E9EADB; -fx-padding:10px 20px");
            addHoverAnimation(joinButton);
            addHoverAnimation(createButton);
            // Set maximum width for text field and combo box
            newChatRoomField.setMaxWidth(276);
            chatRoomsComboBox.setMaxWidth(200);

            // Add components to the GridPane
            gridPane.add(mainTitleLabel, 0, 0, 2, 1);
            gridPane.add(existingTitleLabel, 0, 1);
            gridPane.add(chatRoomsComboBox, 0, 2, 2, 1);
            gridPane.add(joinButton, 0, 3);
            gridPane.add(createTitleLabel, 0, 4);
            gridPane.add(newChatRoomField, 0, 5, 2, 1);
            gridPane.add(createButton, 0, 6);
            gridPane.add(backButton, 0, 7);

            // Set grid lines visible for debugging layout
            gridPane.setGridLinesVisible(false);

            try {
                // Load the image from resources
                InputStream inputStream = getClass().getResourceAsStream("m003t0620_b_online_platform_19sep22.jpg");
                Image image = new Image(inputStream);

                // Create the background image
                BackgroundImage backgroundImage = new BackgroundImage(
                        image,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.2, true, true, false, false));

                // Apply background image to the layout
                gridPane.setBackground(new Background(backgroundImage));
            } catch (NullPointerException e) {
                System.out.println("Image file not found!");
                e.printStackTrace();
            }

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), gridPane);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();

            // Set the scene
            primaryStage.setScene(new Scene(gridPane, 600, 400)); // Adjust size as needed
            primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitHint("");
        } else {
            showAlert(Alert.AlertType.ERROR, "Login failed", "Chatroom access failed: User not logged in!");
            System.out.println("Error: User not logged in."); // Display error message if user is not logged in
        }
    }

    // private void sendMessage() {
    // RegisterUser currentUser = userManager.getCurrentUser(); // Get the current
    // user
    // if (currentUser != null) {
    // // Create JavaFX UI components for sending a message
    // Label sLabel = new Label("Sender's username ");
    // TextField senderField = new TextField();
    // senderField.setPromptText("Enter sender's username");

    // Label rLabel = new Label("Receiver's username ");
    // TextField receiverField = new TextField();
    // receiverField.setPromptText("Enter receiver's username");

    // Label mLabel = new Label("Message ");
    // TextArea messageTextArea = new TextArea();
    // messageTextArea.setPromptText("Type your message here...");
    // messageTextArea.setPrefRowCount(5); // Set preferred row count for the text
    // area

    // Button sendButton = new Button("Send");
    // sendButton.setOnAction(event -> {
    // String senderUsername = senderField.getText().trim(); // Get the sender's
    // username
    // String receiverUsername = receiverField.getText().trim(); // Get the
    // receiver's username
    // String messageContent = messageTextArea.getText().trim(); // Get the message
    // content

    // if (!senderUsername.isEmpty() && !receiverUsername.isEmpty() &&
    // !messageContent.isEmpty()) {
    // // Check if both sender and receiver are logged in
    // if (userManager.isLoggedIn(senderUsername) &&
    // userManager.isLoggedIn(receiverUsername)) {
    // RegisterUser senderUser = userManager.getUserByUsername(senderUsername);
    // RegisterUser receiverUser = userManager.getUserByUsername(receiverUsername);

    // // Proceed with sending the message
    // List<ChatRoom> chatRooms = databaseManager.fetchChatRooms();
    // if (!chatRooms.isEmpty()) {
    // ChatRoom selectedChatRoom = chatRoomManager.getCurrentChatRoom();

    // if (selectedChatRoom != null) {
    // String encrypt = selectedChatRoom.sendMessage(senderUser, messageContent);
    // selectedChatRoom.receiveMessage(receiverUser, encrypt);
    // showAlert(Alert.AlertType.INFORMATION, "Message Sent", "Message sent by "
    // + senderUsername + " and received by " + receiverUsername + "
    // successfully!");
    // receiveMessage(senderUsername, messageContent);
    // showMainMenu(); // Go back to the main menu after sending the message
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Error",
    // "No chat room selected. Please create or join a chat room first.");
    // }
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Error", "No chat rooms available.");
    // }
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Error", "One or both users are not logged
    // in.");
    // }
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Error",
    // "Please enter sender's username, receiver's username, and message.");
    // }
    // });

    // // Create separate layouts for sender and receiver messages
    // VBox senderMessageLayout = new VBox(5);
    // senderMessageLayout.setAlignment(Pos.TOP_RIGHT);
    // VBox receiverMessageLayout = new VBox(5);
    // receiverMessageLayout.setAlignment(Pos.TOP_LEFT);

    // // Style the sender and receiver message layouts
    // senderMessageLayout.setStyle("-fx-background-color: #DCF8C6; -fx-padding:
    // 5px;");
    // receiverMessageLayout.setStyle("-fx-background-color: #FFFFFF; -fx-padding:
    // 5px;");

    // // Add sender and receiver messages to their respective layouts
    // senderMessageLayout.getChildren().addAll(new Label("Sender's username"),
    // senderField, messageTextArea);
    // receiverMessageLayout.getChildren().addAll(new Label("Receiver's username"),
    // receiverField,
    // messageTextArea);

    // // Create a container to hold both sender and receiver message layouts
    // HBox messageContainer = new HBox(10);
    // messageContainer.getChildren().addAll(senderMessageLayout,
    // receiverMessageLayout);

    // // Set the scene for sending messages
    // primaryStage.setScene(new Scene(messageContainer, 600, 400));
    // primaryStage.setTitle("Send Message");
    // primaryStage.show();
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Error", "Send message failed: User not
    // logged in.");
    // }
    // }

    private void sendMessage() {
        RegisterUser currentUser = userManager.getCurrentUser();
        if (currentUser != null) {
            if (chatRoomManager.getCurrentChatRoom() != null) {

                // Create JavaFX UI components for sending a message
                Label maiLabel = new Label("Send Message : " + chatRoomManager.getCurrentChatRoom().getRoomName());
                Label sLabel = new Label("Sender's username ");
                TextField senderField = new TextField();
                senderField.setPromptText("Enter sender's username");

                Label rLabel = new Label("Receiver's username ");
                TextField receiverField = new TextField();
                receiverField.setPromptText("Enter receiver's username");

                Label mLabel = new Label("Message:");
                TextArea messageTextArea = new TextArea();
                messageTextArea.setPromptText("Type your message here...");
                messageTextArea.setPrefRowCount(1); // Initially set to one line
                messageTextArea.setWrapText(true); // Enable text wrapping
                // messageTextArea.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial';");
                // // Apply styles

                // Adjust height and enable vertical scrolling
                messageTextArea.setPrefHeight(50); // Set initial height
                messageTextArea.textProperty().addListener((obs, oldText, newText) -> {
                    int lineCount = newText.split("\n").length;
                    if (lineCount > 4) { // Limit the number of visible lines before enabling scroll bar
                        messageTextArea.setPrefHeight(50 + (lineCount - 4) * 17); // Increase height as needed
                    }
                });
                Button sendButton = new Button("Send");

                Button backButton = new Button("Back");
                // Styling the back button
                backButton.setStyle(
                        "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #FA7270; -fx-padding: 5px 10px; -fx-text-fill: white; -fx-border-radius: 5px;");

                // Add event handler to the back button
                backButton.setOnAction(event -> {
                    primaryStage.close(); // Close the current window
                    showMainMenu(); // Open the main menu window
                });

                // Apply styles to the labels
                maiLabel.setStyle(
                        "-fx-font-size: 45px; -fx-font-weight: bolder; -fx-text-fill: #F24449;-fx-font-family:'Monospace'");
                sLabel.setStyle(
                        "-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: #67595E; -fx-font-family:'Verdana'"); // Adjust

                rLabel.setStyle(
                        "-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: #67595E; -fx-font-family:'Verdana'");

                mLabel.setStyle(
                        "-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: #F24449; -fx-font-family:'Verdana'"); // Adjust
                messageTextArea.setStyle("-fx-font-size: 16px; -fx-font-family:'Verdana';");
                sendButton.setStyle(
                        "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #FA7270; -fx-padding: 5px 10px; -fx-text-fill: white; -fx-border-radius: 5px;");
                // Apply styles to the text fields
                senderField.setStyle("-fx-font-size: 18px; -fx-font-family:'Verdana';");
                receiverField.setStyle("-fx-font-size: 18px; -fx-font-family:'Verdana'");
                senderField.setMaxWidth(260);
                receiverField.setMaxWidth(260);
                messageTextArea.setMaxWidth(420);

                // Create a GridPane for organizing the components
                GridPane gridPane = new GridPane();
                GridPane gridPane2 = new GridPane();
                gridPane.setAlignment(Pos.TOP_LEFT);
                gridPane.setHgap(20);
                gridPane.setVgap(30);
                gridPane.setPadding(new Insets(40, 20, 20, 20)); // Add more padding to the top

                // Add components to the GridPane
                gridPane.add(maiLabel, 0, 0, 2, 1);
                gridPane.add(sLabel, 0, 1);
                gridPane.add(senderField, 0, 2);
                gridPane.add(rLabel, 0, 3);
                gridPane.add(receiverField, 0, 4);

                gridPane2.setAlignment(Pos.BOTTOM_RIGHT);
                gridPane2.setHgap(20);
                gridPane2.setVgap(30);
                gridPane2.setPadding(new Insets(20));

                gridPane2.add(mLabel, 0, 0);
                gridPane2.add(messageTextArea, 0, 1);
                gridPane2.add(sendButton, 1, 1);
                gridPane2.add(backButton, 1, 2);

                // // Create a VBox to hold the send button
                // VBox buttonBox = new VBox(10);
                // buttonBox.setAlignment(Pos.CENTER);
                // buttonBox.getChildren().add(sendButton);

                // Set an action for the send button
                sendButton.setOnAction(event -> {
                    String senderUsername = senderField.getText().trim(); // Get the sender's username
                    String receiverUsername = receiverField.getText().trim(); // Get the receiver's username
                    String messageContent = messageTextArea.getText().trim(); // Get the message content

                    if (!senderUsername.isEmpty() && !receiverUsername.isEmpty() && !messageContent.isEmpty()) {
                        senderUser = userManager.getUserByUsername(senderUsername);
                        receiverUser = userManager.getUserByUsername(receiverUsername);
                        // Check if both sender and receiver are logged in
                        if (senderUser != null && receiverUser != null) {
                            if (userManager.isLoggedIn(senderUsername) && userManager.isLoggedIn(receiverUsername)) {

                                // Proceed with sending the message
                                List<ChatRoom> chatRooms = databaseManager.fetchChatRooms();
                                if (!chatRooms.isEmpty()) {
                                    ChatRoom selectedChatRoom = chatRoomManager.getCurrentChatRoom();

                                    if (selectedChatRoom != null) {
                                        String encrypt = selectedChatRoom.sendMessage(senderUser, messageContent);
                                        selectedChatRoom.receiveMessage(receiverUser, encrypt);
                                        showAlert(Alert.AlertType.INFORMATION, "Message Sent", "Message sent by "
                                                + senderUsername + " and received by " + receiverUsername
                                                + " successfully!");
                                        showMainMenu(); // Go back to the main menu after sending the message
                                    } else {
                                        showAlert(Alert.AlertType.ERROR, "Error",
                                                "No chat room selected. Please create or join a chat room first.");
                                    }
                                } else {
                                    showAlert(Alert.AlertType.ERROR, "Error", "No chat rooms available.");
                                }
                            } else {
                                showAlert(Alert.AlertType.ERROR, "Error", "One or both users are not logged in.");
                            }
                        } else {
                            showAlert(Alert.AlertType.ERROR, "Error", "Invalid sender or receiver username.");
                        }
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error",
                                "Please enter sender's username, receiver's username, and message.");
                    }
                });

                // Create a VBox to hold the two GridPanes
                VBox vbox = new VBox(220); // Set spacing between the GridPanes
                // vbox.setAlignment(Pos.CENTER); // Align the GridPanes to the center
                vbox.setPadding(new Insets(20)); // Add padding around the VBox

                // Add the GridPanes to the VBox
                vbox.getChildren().addAll(gridPane, gridPane2);

                try {
                    // Load the image from resources
                    InputStream inputStream = getClass().getResourceAsStream(
                            "email-envelope-inbox-shape-social-media-notification-icon-speech-bubbles-3d-cartoon-banner-website-ui-pink-background-3d-rendering-illustration.jpg");
                    Image image = new Image(inputStream);

                    // Create the background image
                    BackgroundImage backgroundImage = new BackgroundImage(
                            image,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.DEFAULT,
                            new BackgroundSize(0.998, 1.0, true, true, false, false));

                    // Apply background image to the layout
                    vbox.setBackground(new Background(backgroundImage));
                } catch (NullPointerException e) {
                    System.out.println("Image file not found!");
                    e.printStackTrace();
                }
                // Set the scene with the VBox containing both GridPanes
                primaryStage.setScene(new Scene(vbox, 800, 600));
                // primaryStage.setScene(new Scene(gridPane, 600, 400));
                primaryStage.setTitle("Send Message");
                // primaryStage.show();
                primaryStage.setFullScreen(true);
                primaryStage.setFullScreenExitHint("");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No chat rooms are available.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Send message failed: User not logged in.");
        }
    }

    // private void displayChatHistory() {
    // RegisterUser currentUser = userManager.getCurrentUser(); // Get the current
    // user
    // if (currentUser != null) {
    // ChatRoom currentChatRoom = chatRoomManager.getCurrentChatRoom(); // Get the
    // current chat room
    // if (currentChatRoom != null) {
    // String roomName = chatRoomManager.getCurrentChatRoom().getRoomName(); // Get
    // the chat room name
    // if (roomName != null && !roomName.isEmpty()) { // Check if room name is valid
    // List<String> chatHistory = currentChatRoom.getChatHistory(); // Get the chat
    // history for the current
    // // chat room
    // if (!chatHistory.isEmpty()) {
    // // Create a layout for displaying chat history
    // VBox historyLayout = new VBox(10);
    // historyLayout.setAlignment(Pos.TOP_LEFT);
    // historyLayout.setPadding(new Insets(20));

    // // Add chat room name as title
    // Label titleLabel = new Label("Chat History: " + roomName);
    // titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

    // // Create a TextArea to display chat history
    // TextArea historyTextArea = new TextArea();
    // historyTextArea.setEditable(false); // Make the text area read-only
    // historyTextArea.setWrapText(true); // Enable text wrapping
    // historyTextArea.setStyle("-fx-font-size: 14px;");

    // // Add each message to the text area
    // for (String message : chatHistory) {
    // historyTextArea.appendText(message + "\n\n"); // Add some spacing between
    // messages
    // }

    // // Add components to the layout
    // historyLayout.getChildren().addAll(titleLabel, historyTextArea);

    // // Set the scene for displaying chat history
    // primaryStage.setScene(new Scene(historyLayout, 800, 600)); // Adjust size as
    // needed
    // primaryStage.setTitle("Chat History - " + roomName);
    // primaryStage.show();
    // } else {
    // showAlert(Alert.AlertType.INFORMATION, "Chat History",
    // "No chat history available for the current room.");
    // }
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Chat History", "Chat room name is not
    // available.");
    // }
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Chat History", "No active chat room
    // found.");
    // }
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Chat History", "User not logged in.");
    // }
    // }

    // private void displayChatHistory() {
    // RegisterUser currentUser = userManager.getCurrentUser();
    // if (currentUser != null) {
    // ChatRoom currentChatRoom = chatRoomManager.getCurrentChatRoom();
    // if (currentChatRoom != null) {
    // String roomName = chatRoomManager.getCurrentChatRoom().getRoomName();
    // if (roomName != null && !roomName.isEmpty()) {
    // List<String> chatHistory = currentChatRoom.getChatHistory();
    // if (!chatHistory.isEmpty()) {
    // VBox chatContainer = new VBox(10);
    // chatContainer.setPadding(new Insets(20));
    // chatContainer.setAlignment(Pos.TOP_CENTER);

    // Label titleLabel = new Label("Chat History: " + roomName);
    // titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

    // VBox messagesContainer = new VBox(10);
    // messagesContainer.setAlignment(Pos.TOP_LEFT);

    // for (String message : chatHistory) {
    // String[] parts = message.split(" | ", 3);
    // if (parts.length == 3) {
    // String timestamp = parts[0];
    // String sender = parts[1];
    // String content = parts[2];

    // Label messageLabel = new Label(content);
    // messageLabel.setStyle("-fx-padding: 8px 12px; -fx-background-color: #DCF8C6;
    // -fx-background-radius: 20;");

    // HBox messageBox = new HBox();
    // if (sender.equals(currentUser.getUserName())) {
    // messageLabel.setAlignment(Pos.CENTER_RIGHT);
    // messageBox.setAlignment(Pos.CENTER_RIGHT);
    // messageBox.getChildren().addAll(messageLabel, createCircle(Color.WHITE));
    // } else {
    // messageLabel.setAlignment(Pos.CENTER_LEFT);
    // messageBox.setAlignment(Pos.CENTER_LEFT);
    // messageBox.getChildren().addAll(createCircle(Color.LIGHTGRAY), messageLabel);
    // }

    // messagesContainer.getChildren().add(messageBox);
    // }
    // }

    // ScrollPane scrollPane = new ScrollPane(messagesContainer);
    // scrollPane.setFitToWidth(true);

    // chatContainer.getChildren().addAll(titleLabel, scrollPane);

    // primaryStage.setScene(new Scene(chatContainer, 800, 600));
    // primaryStage.setTitle("Chat History - " + roomName);
    // primaryStage.show();
    // } else {
    // showAlert(Alert.AlertType.INFORMATION, "Chat History",
    // "No chat history available for the current room.");
    // }
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Chat History", "Chat room name is not
    // available.");
    // }
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Chat History", "No active chat room
    // found.");
    // }
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Chat History", "User not logged in.");
    // }
    // }

    // private void displayChatHistory() {
    // RegisterUser currentUser = userManager.getCurrentUser();
    // if (currentUser != null) {
    // ChatRoom currentChatRoom = chatRoomManager.getCurrentChatRoom();
    // if (currentChatRoom != null) {
    // String roomName = chatRoomManager.getCurrentChatRoom().getRoomName();
    // if (roomName != null && !roomName.isEmpty()) {
    // List<String> chatHistory = currentChatRoom.getChatHistory();
    // if (!chatHistory.isEmpty()) {
    // GridPane chatGrid = new GridPane();
    // chatGrid.setPadding(new Insets(20));
    // chatGrid.setAlignment(Pos.TOP_CENTER);
    // chatGrid.setHgap(10);
    // chatGrid.setVgap(10);

    // Label titleLabel = new Label("Chat History: " + roomName);
    // titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    // GridPane.setConstraints(titleLabel, 0, 0);

    // for (int i = 0; i < chatHistory.size(); i++) {
    // String message = chatHistory.get(i);
    // String[] parts = message.split(" | ", 3);
    // if (parts.length == 3) {
    // String timestamp = parts[0];
    // String sender = parts[1];
    // String content = parts[2];

    // Label messageLabel = new Label(content);
    // messageLabel.setStyle("-fx-padding: 8px 12px; -fx-background-color: #DCF8C6;
    // -fx-background-radius: 20;");

    // HBox messageBox = new HBox();
    // if (sender.equals(currentUser.getUserName())) {
    // messageLabel.setAlignment(Pos.CENTER_RIGHT);
    // messageBox.setAlignment(Pos.CENTER_RIGHT);
    // messageBox.getChildren().addAll(messageLabel, createCircle(Color.WHITE));
    // } else {
    // messageLabel.setAlignment(Pos.CENTER_LEFT);
    // messageBox.setAlignment(Pos.CENTER_LEFT);
    // messageBox.getChildren().addAll(createCircle(Color.LIGHTGRAY), messageLabel);
    // }

    // GridPane.setConstraints(messageBox, 0, i + 1);
    // chatGrid.getChildren().add(messageBox);
    // }
    // }

    // ScrollPane scrollPane = new ScrollPane(chatGrid);
    // scrollPane.setFitToWidth(true);

    // VBox chatContainer = new VBox(10, titleLabel, scrollPane);
    // chatContainer.setAlignment(Pos.TOP_CENTER);

    // primaryStage.setScene(new Scene(chatContainer, 800, 600));
    // primaryStage.setTitle("Chat History - " + roomName);
    // primaryStage.show();
    // } else {
    // showAlert(Alert.AlertType.INFORMATION, "Chat History",
    // "No chat history available for the current room.");
    // }
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Chat History", "Chat room name is not
    // available.");
    // }
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Chat History", "No active chat room
    // found.");
    // }
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Chat History", "User not logged in.");
    // }
    // }

    // private void displayChatHistory() {
    // RegisterUser currentUser = userManager.getCurrentUser();
    // if (currentUser != null) {
    // ChatRoom currentChatRoom = chatRoomManager.getCurrentChatRoom();
    // if (currentChatRoom != null) {
    // String roomName = chatRoomManager.getCurrentChatRoom().getRoomName();
    // if (roomName != null && !roomName.isEmpty()) {
    // List<String> chatHistory = currentChatRoom.getChatHistory();
    // if (!chatHistory.isEmpty()) {

    // // Load the background image
    // GridPane chatGridPane = new GridPane();
    // chatGridPane.setPadding(new Insets(70));
    // chatGridPane.setAlignment(Pos.CENTER);
    // chatGridPane.setVgap(30); // Set vertical gap between elements
    // chatGridPane.setHgap(20);

    // Label titleLabel = new Label("Chat History: " + roomName);
    // titleLabel.setStyle("-fx-font-size: 45px; -fx-font-weight: bold;");
    // chatGridPane.add(titleLabel, 0, 0, 2, 1); // Add titleLabel to the grid

    // int row = 1; // Start adding messages from the second row
    // for (String message : chatHistory) {
    // String[] parts = message.split("\\|", 3);

    // if (parts.length == 3) {
    // String timestamp = parts[0];
    // String sender = parts[1];
    // String content = parts[2];

    // Label messageLabel = new Label(content);
    // messageLabel.setStyle(
    // "-fx-padding: 8px 12px; -fx-background-color: #DCF8C6; -fx-background-radius:
    // 20; -fx-font-size:18px");

    // Label sLabel = new Label(sender);
    // Label rLabel = new Label(receiverUser.getName());

    // HBox messageBox = new HBox(10); // Set spacing between nodes in the HBox
    // if (sender.equals(senderUser.getName())) {
    // messageLabel.setAlignment(Pos.CENTER_LEFT);
    // messageBox.setAlignment(Pos.CENTER_LEFT);
    // messageBox.getChildren().addAll(createCircle(Color.LIGHTGRAY), sLabel,
    // messageLabel);
    // } else if (sender.equals(receiverUser.getName())) {
    // messageLabel.setAlignment(Pos.CENTER_RIGHT);
    // messageBox.setAlignment(Pos.CENTER_RIGHT);
    // messageBox.getChildren().addAll(messageLabel, rLabel,
    // createCircle(Color.LIGHTGRAY));
    // }

    // Label timestampLabel = new Label(timestamp);
    // timestampLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #888888;");

    // // Create a GridPane to hold the timestamp and message
    // GridPane timestampMessagePane = new GridPane();
    // timestampMessagePane.setAlignment(Pos.TOP_CENTER); // Center align the
    // content within
    // // the

    // // GridPane
    // timestampMessagePane.setHgap(30); // Set horizontal gap between nodes in the
    // GridPane
    // timestampMessagePane.setVgap(30);

    // // Add timestamp label and message box to the GridPane
    // VBox messageWithTime = new VBox(10); // Set the vertical gap between nodes
    // messageWithTime.setAlignment(Pos.CENTER); // Center align the content within
    // the VBox
    // messageWithTime.setStyle(
    // "-fx-padding: 10px; -fx-background-color: #FFFFFF; -fx-background-radius:
    // 10;");
    // // Set preferred width and height of the VBox
    // messageWithTime.setPrefWidth(500); // Set preferred width (adjust as needed)
    // messageWithTime.setMinHeight(80); // Set minimum height (adjust as needed)

    // // Add timestamp, sender, and message content to the VBox
    // messageWithTime.getChildren().addAll(timestampLabel, messageBox); // Add more
    // nodes as
    // // needed

    // // Add the messageWithTime VBox to the parent container (e.g., another VBox,
    // // GridPane, etc.)
    // timestampMessagePane.getChildren().add(messageWithTime);

    // chatGridPane.add(timestampMessagePane, 0, row);
    // chatGridPane.setStyle("-fx-background-color: transparent;");
    // row++;

    // }
    // }

    // ScrollPane scrollPane = new ScrollPane(chatGridPane);
    // scrollPane.setFitToWidth(true);

    // // Create a VBox to hold the chat history and set the background image
    // VBox root = new VBox(scrollPane);
    // root.setBackground(new Background(new BackgroundImage(
    // new Image(getClass().getResourceAsStream(
    // "blue-pink-text-meagre-box-chatting-box-white-background-by-3d-render.jpg")),
    // BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
    // BackgroundPosition.CENTER,
    // new BackgroundSize(100, 100, true, true, true, false)))); // Adjust the size
    // of the
    // // background image here

    // // Set the size of the VBox
    // root.setPrefSize(800, 600); // Set your desired size here

    // primaryStage.setScene(new Scene(root, 800, 600));
    // primaryStage.setTitle("Chat History - " + roomName);
    // primaryStage.show();

    // } else {
    // showAlert(Alert.AlertType.INFORMATION, "Chat History",
    // "No chat history available for the current room.");
    // }
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Chat History", "Chat room name is not
    // available.");
    // }
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Chat History", "No active chat room
    // found.");
    // }
    // } else {
    // showAlert(Alert.AlertType.ERROR, "Chat History", "User not logged in.");
    // }
    // }

    private void displayChatHistory() {
        RegisterUser currentUser = userManager.getCurrentUser();
        if (currentUser != null) {

            ChatRoom currentChatRoom = chatRoomManager.getCurrentChatRoom();
            if (currentChatRoom != null) {
                String roomName = chatRoomManager.getCurrentChatRoom().getRoomName();
                if (roomName != null && !roomName.isEmpty()) {
                    List<String> chatHistory = currentChatRoom.getChatHistory();
                    if (!chatHistory.isEmpty()) {

                        GridPane chatGridPane = new GridPane();
                        // Remove padding from the GridPane
                        chatGridPane.setPadding(new Insets(20, 20, 20, 20));
                        chatGridPane.setAlignment(Pos.CENTER);
                        chatGridPane.setVgap(60); // Set vertical gap between elements
                        chatGridPane.setHgap(30);
                        chatGridPane.setPrefWidth(500);
                        chatGridPane.setGridLinesVisible(false);

                        Label titleLabel = new Label("Chat History: " + roomName);
                        titleLabel.setStyle(
                                "-fx-font-size: 45px; -fx-font-weight: bolder; -fx-text-fill: #B98371;-fx-font-family:'Monospace'");
                        chatGridPane.add(titleLabel, 0, 0, 2, 1);

                        int row = 1; // Start adding messages from the second row
                        for (String message : chatHistory) {
                            String[] parts = message.split("\\|", 3);

                            if (parts.length == 3) {
                                String timestamp = parts[0];
                                String sender = parts[1];
                                String content = parts[2];

                                Label messageLabel = new Label(content);
                                messageLabel.setWrapText(true); // Enable text wrapping
                                messageLabel.setStyle(
                                        "-fx-padding: 8px 12px; -fx-background-color: #DCF8C6; -fx-background-radius: 20; -fx-font-size:18px");

                                Label sLabel = new Label(sender);
                                sLabel.setWrapText(true);
                                sLabel.setStyle(
                                        "-fx-font-size: 10px; -fx-text-fill:#6EA5BD;-fx-font-weight: bolder; -fx-background-color: #EED6D3; -fx-background-radius: 5; -fx-padding: 5px 10px");
                                Label rLabel = new Label(receiverUser.getName());
                                rLabel.setWrapText(true);
                                rLabel.setStyle(
                                        "-fx-font-size: 10px; -fx-text-fill:#B98371; -fx-font-weight: bolder;-fx-background-color: #E7F2F8; -fx-background-radius: 5; -fx-padding: 5px 10px");

                                HBox messageBox = new HBox(10); // Set spacing between nodes in the HBox
                                if (sender.equals(senderUser.getName())) {
                                    messageLabel.setAlignment(Pos.CENTER_LEFT);
                                    messageBox.setAlignment(Pos.CENTER_LEFT);
                                    messageBox.getChildren().addAll(createCircle(Color.GREEN), sLabel,
                                            messageLabel);
                                } else if (sender.equals(receiverUser.getName())) {
                                    messageLabel.setAlignment(Pos.CENTER_RIGHT);
                                    messageBox.setAlignment(Pos.CENTER_RIGHT);
                                    messageBox.getChildren().addAll(messageLabel, rLabel,
                                            createCircle(Color.GREEN));
                                }

                                Label timestampLabel = new Label(timestamp);
                                timestampLabel.setStyle(
                                        "-fx-font-size: 18px; -fx-text-fill: #84848A;-fx-background-color: #E2E2E2; -fx-background-radius: 10; -fx-padding: 5px 10px");

                                GridPane timestampMessagePane = new GridPane();
                                timestampMessagePane.setAlignment(Pos.TOP_CENTER);
                                timestampMessagePane.setHgap(40);
                                timestampMessagePane.setVgap(50);

                                VBox messageWithTime = new VBox(20);
                                messageWithTime.setAlignment(Pos.CENTER);
                                messageWithTime.setStyle(
                                        "-fx-padding: 10px;");
                                messageWithTime.setPrefWidth(500);
                                messageWithTime.setMinHeight(80);

                                messageWithTime.getChildren().addAll(timestampLabel, messageBox);

                                timestampMessagePane.getChildren().add(messageWithTime);

                                chatGridPane.add(timestampMessagePane, 0, row);
                                row++;

                            }
                        }
                        Button backButton = new Button("Back");
                        // Styling the back button
                        backButton.setStyle(
                                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #A229EA; -fx-padding: 5px 10px; -fx-text-fill: white; -fx-border-radius: 5px;");

                        // Add event handler to the back button
                        backButton.setOnAction(event -> {
                            primaryStage.close(); // Close the current window
                            showMainMenu(); // Open the main menu window
                        });
                        addHoverAnimation(backButton);
                        chatGridPane.add(backButton, 0, row + 1);
                        StackPane stackPane = new StackPane();

                        // Set the background image
                        try {
                            // Load the image from resources
                            InputStream inputStream = getClass().getResourceAsStream(
                                    "blue-pink-text-meagre-box-chatting-box-white-background-by-3d-render.jpg");
                            Image image = new Image(inputStream);

                            // Create the background image
                            BackgroundImage backgroundImage = new BackgroundImage(
                                    image,
                                    BackgroundRepeat.NO_REPEAT,
                                    BackgroundRepeat.NO_REPEAT,
                                    BackgroundPosition.CENTER,
                                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
                                            true));

                            // Apply background image to the layout
                            stackPane.setBackground(new Background(backgroundImage));
                            // Apply a blur effect to the StackPane
                        } catch (NullPointerException e) {
                            System.out.println("Image file not found!");
                            e.printStackTrace();
                        }

                        // Add the chat GridPane to the StackPane
                        stackPane.getChildren().add(chatGridPane);

                        // Create the ScrollPane and add the StackPane to it
                        ScrollPane scrollPane = new ScrollPane(stackPane);
                        scrollPane.setFitToWidth(true);
                        scrollPane.setFitToHeight(true); // Add this line to make the ScrollPane fit the height as well

                        // Create a new Group and add the ScrollPane to it
                        // Group root = new Group(scrollPane);

                        // Create the Scene using the Group
                        Scene scene = new Scene(scrollPane, 800, 600);

                        // Set the scene to the primaryStage
                        primaryStage.setScene(scene);

                        primaryStage.setTitle("Chat History - " + roomName);
                        primaryStage.setFullScreen(true);
                        primaryStage.setFullScreenExitHint("");
                        // primaryStage.show();

                    } else {

                        showAlert(Alert.AlertType.ERROR, "Chat History", "No Chat history!!");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Chat History", "Chat room name is not available.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Chat History", "No active chat room found.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Chat History", "User not logged in.");
        }
    }

    private Circle createCircle(Color color) {
        Circle circle = new Circle(5);
        circle.setFill(color);
        return circle;
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
        

        dialogPane.setPrefWidth(700);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
