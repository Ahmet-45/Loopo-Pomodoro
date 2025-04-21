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
			if(UserData.registerUser(username, password)) {
				message.setText("Registration successful! You are directed to the login screen...");
			}else {
				message.setText("This username is already in use");
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
}
