package application.login;

import application.data.UserData;
import application.timer.PomodoroScreen;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * LoginScreen handles user authentication and navigation to the Pomodoro screen.
 */
public class LoginScreen {
	/*
     * Primary stage for displaying the login scene.
     */
	private Stage stage;
	
	/*
     * Constructs a LoginScreen with the specified JavaFX stage.
     *
     * @param stage the primary application stage
     */
	public LoginScreen(Stage stage) {
		this.stage=stage;
	}
	
	/*
     * Builds and displays the login UI, including input fields and buttons,
     * and handles user interactions for login and registration.
     */
	
	public void show() {
		 // Create a vertical layout pane with spacing and padding
		VBox root = new VBox(10);
		root.setPadding(new Insets(20));
		
		// Username input field with prompt text
		TextField usernameField = new TextField();
		usernameField.setPromptText("User Name");
		
		// Password input field with prompt text
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Password");
		
		 // Buttons for login and registration actions
		Button loginButton = new Button("Log In");
		Button registerButton = new Button("Sign Up");
		
		
		// Label for displaying messages (currently unused)
		Label message = new Label();
		
		 // Handle login button click
		loginButton.setOnAction(_->{
			String username = usernameField.getText();
		    String password = passwordField.getText();
		    
		    try {
		    	 // Ensure both fields are filled
		        if (username.isEmpty() || password.isEmpty()) {
		            throw new IllegalArgumentException("Please enter username and password.");
		        }

		        // Verify user credentials
		        boolean loginSuccess = UserData.checkCredentials(username, password);
		        if (loginSuccess) {
		        	// Retrieve user ID for the authenticated user
		        	int userId = UserData.getUserId(username);
		        	if(userId == -1) {
		        		throw new Exception("User not found. Please register or check information.");
		        	}
		        	 // Show login success alert
		        	 Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                    alert.setTitle("Login Successful");
	                    alert.setHeaderText(null);
	                    alert.setContentText("Welcome, " + username + "!");
	                    alert.getDialogPane().getStylesheets().add(getClass().getResource("/application/style/application.css").toExternalForm());
	                    alert.showAndWait();
	                // Navigate to Pomodoro screen with the authenticated user ID    
		            PomodoroScreen screen = new PomodoroScreen(stage, userId);
		            screen.show();
		        } else {
		            throw new Exception("Wrong username or password!");
		        }

		    } catch (IllegalArgumentException iae) {
		    	// Show warning if input validation fails
		    	 showAlert(Alert.AlertType.WARNING, "Missing Info", iae.getMessage());
		    } catch (Exception e) {
		    	// Show error alert for any other login issue
		    	showAlert(Alert.AlertType.ERROR, "Login Failed", e.getMessage());
		    }

		}); 
			
		// Handle register button click to show registration screen
		registerButton.setOnAction(_->{
			RegisterScreen register = new RegisterScreen(stage);
			register.show();
		});
		
		// Add all UI components to the layout
		root.getChildren().addAll(usernameField, passwordField, loginButton, registerButton, message);
		
		// Create and style the scene
		Scene scene = new Scene(root,400,250);
		scene.getStylesheets().add(getClass().getResource("/application/style/application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Login Screen");
		
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

