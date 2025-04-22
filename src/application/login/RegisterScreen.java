package application.login;

import application.data.UserData;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterScreen {
	private Stage  stage;
	
	public RegisterScreen(Stage stage){
		this.stage = stage;
	}
	
	public void show() {
		
		VBox root = new VBox(10);
		root.setPadding(new Insets(20));
		
		TextField usernameField = new TextField();
		usernameField.setPromptText("New User Name");
		
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("New Password");
		
		
		Button registerButton = new Button("Register");
		Button backButton = new Button("Back");
		
		
		
		
		
		Label message = new Label();
		
		registerButton.setOnAction(_->{
			String username = usernameField.getText();
			String password = passwordField.getText();
			
		try {
			if(username.isEmpty() || password.isEmpty()) {
				throw new IllegalArgumentException("Please fill in all the blanks.");
			}
			
			boolean success = UserData.registerUser(username, password);
			
				if(success) {
				    Alert alert = new Alert(Alert.AlertType.INFORMATION);
				    alert.setTitle("Successful");
				    alert.setHeaderText(null);
				    alert.setContentText("Registration successful! You are directed to the login screen.");
				    alert.getDialogPane().getStylesheets().add(getClass().getResource("/application/style/application.css").toExternalForm());
				    alert.showAndWait();

				    new LoginScreen(stage).show(); 
				}else {
				throw new Exception("This username is already registered.");
			}
			
			
		}catch (IllegalArgumentException iae) {
			
			showAlert(Alert.AlertType.WARNING, "Missing Information", iae.getMessage());
		
		}catch(Exception e) {
			showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
		}
			
	});
		
		backButton.setOnAction(_->{
			new LoginScreen(stage).show();
		});
		
		root.getChildren().addAll(usernameField, passwordField, registerButton, backButton, message);
		
		Scene scene = new Scene(root,400,250);
		scene.getStylesheets().add(getClass().getResource("/application/style/application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Register Screen");
		
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

