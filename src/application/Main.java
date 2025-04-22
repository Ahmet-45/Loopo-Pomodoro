package application;
	
import application.login.LoginScreen;
import javafx.application.Application;
import javafx.stage.Stage;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		LoginScreen login = new LoginScreen(primaryStage);
		login.show();
		primaryStage.show();
		
		application.data.DatabaseHelper.initializeDatabase();

		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
