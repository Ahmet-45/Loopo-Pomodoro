package application.login;

import application.data.UserData;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
 * RegisterScreen provides the UI and logic for new user registration.
 * It collects username and password, validates input, and registers the user.
 */
public class RegisterScreen {
	/*
     * Primary stage for displaying the registration scene.
     */
	private Stage  stage;
	
	/*
     * Constructs a RegisterScreen with the specified JavaFX stage.
     *
     * @param stage the primary application stage
     */
	public RegisterScreen(Stage stage){
		this.stage = stage;
	}
	
	/*
     * Builds and displays the registration UI, including input fields and buttons,
     * and handles user interactions for registering and navigating back.
     */
	public void show() {
		stage.getIcons().add(
				new Image(getClass().getResourceAsStream("/images/logo.png"))
			);
		 // Create a vertical layout pane with spacing and padding
		VBox root = new VBox(10);
		root.setPadding(new Insets(20));
		
		Image logo = new Image(getClass().getResourceAsStream("/images/logo.png"));
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(50);         
        logoView.setPreserveRatio(true);  
        root.getChildren().add(logoView);
		
		// Input field for new username
		TextField usernameField = new TextField();
		usernameField.setPromptText("New User Name");
		
		// Input field for new password
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("New Password");
		
		// Buttons for registration and back navigation
		Button registerButton = new Button("Register");
		Button backButton = new Button("Back");
		
		
		
		
		// Label for displaying messages (currently unused)
		Label message = new Label();
		
		// Handle registration button click
		registerButton.setOnAction(_->{
			String username = usernameField.getText();
			String password = passwordField.getText();
			
		try {
			// Validate that all fields are filled
			if(username.isEmpty() || password.isEmpty()) {
				throw new IllegalArgumentException("Please fill in all the blanks.");
			}
			
			// Attempt to register the new user
			boolean success = UserData.registerUser(username, password);
				
				if(success) {
					// Show success alert and direct user to login screen
				    Alert alert = new Alert(Alert.AlertType.INFORMATION);
				    alert.setTitle("Successful");
				    alert.setHeaderText(null);
				    alert.setContentText("Registration successful! You are directed to the login screen.");
				    alert.getDialogPane().getStylesheets().add(getClass().getResource("/application/style/application.css").toExternalForm());
				    alert.showAndWait();
				    
				    // Navigate back to login screen
				    new LoginScreen(stage).show(); 
				}else {
				throw new Exception("This username is already registered.");
			}
			
			
		}catch (IllegalArgumentException iae) {
			// Show warning if validation fails
			showAlert(Alert.AlertType.WARNING, "Missing Information", iae.getMessage());
		
		}catch(Exception e) {
			// Show error if registration fails
			showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
		}
			
	});
		// Handle back button click to return to login screen
		backButton.setOnAction(_->{
			new LoginScreen(stage).show();
		});
		
		root.getChildren().addAll(usernameField, passwordField, registerButton, backButton, message);
		
		Scene scene = new Scene(root,400,300);
		scene.getStylesheets().add(getClass().getResource("/application/style/application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Register Screen");
		
	}
	
	/*
     * Displays an alert of the specified type with a title and message content.
     * Applies the application style sheet for consistent styling.
     *
     * @param type    the type of the alert (INFORMATION, WARNING, ERROR)
     * @param title   the dialog title
     * @param content the message content to display
     */
	private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/application/style/application.css").toExternalForm());
        alert.showAndWait();
}
}

