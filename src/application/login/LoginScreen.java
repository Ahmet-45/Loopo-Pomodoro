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
			if(UserData.checkCredentials(usernameField.getText(), passwordField.getText())) {
				message.setText("Login successful.");
				PomodoroScreen pomodoro = new PomodoroScreen(stage);
				pomodoro.show();
				
			}else {
				message.setText("Wrong username or password!");
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
}
