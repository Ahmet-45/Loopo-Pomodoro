package application.login;

import application.data.UserData;
import application.timer.PomodoroScreen;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginScreen {
	private Stage stage;
	
	public LoginScreen(Stage stage) {
		this.stage=stage;
	}
	
	public void show() {
		VBox root = new VBox(10);
		root.setPadding(new Insets(20));
		
		TextField usernameField = new TextField();
		usernameField.setPromptText("User Name");
		
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Password");
		
		Button loginButton = new Button("Log In");
		Button registerButton = new Button("Sign Up");
		
		
		
		Label message = new Label();
		
		loginButton.setOnAction(_->{
			String username = usernameField.getText();
		    String password = passwordField.getText();
		    
		    try {
		        if (username.isEmpty() || password.isEmpty()) {
		            throw new IllegalArgumentException("Please enter username and password.");
		        }

		        boolean loginSuccess = UserData.checkCredentials(username, password);
		        if (loginSuccess) {
		        	
		        	int userId = UserData.getUserId(username);
		        	if(userId == -1) {
		        		throw new Exception("User not found. Please register or check information.");
		        	}
		        	 Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                    alert.setTitle("Login Successful");
	                    alert.setHeaderText(null);
	                    alert.setContentText("Welcome, " + username + "!");
	                    alert.getDialogPane().getStylesheets().add(getClass().getResource("/application/style/application.css").toExternalForm());
	                    alert.showAndWait();
		            PomodoroScreen screen = new PomodoroScreen(stage, userId);
		            screen.show();
		        } else {
		            throw new Exception("Wrong username or password!");
		        }

		    } catch (IllegalArgumentException iae) {
		    	 showAlert(Alert.AlertType.WARNING, "Missing Info", iae.getMessage());
		    } catch (Exception e) {
		    	showAlert(Alert.AlertType.ERROR, "Login Failed", e.getMessage());
		    }

		}); 
			
		
		registerButton.setOnAction(_->{
			RegisterScreen register = new RegisterScreen(stage);
			register.show();
		});
		
		root.getChildren().addAll(usernameField, passwordField, loginButton, registerButton, message);
		
		Scene scene = new Scene(root,400,250);
		scene.getStylesheets().add(getClass().getResource("/application/style/application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Login Screen");
		
	}
	
	 private void showAlert(Alert.AlertType type, String title, String content) {
	        Alert alert = new Alert(type);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(content);
	        alert.getDialogPane().getStylesheets().add(getClass().getResource("/application/style/application.css").toExternalForm());
	        alert.showAndWait();
	    }
}

