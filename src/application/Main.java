package application;
	
import javafx.application.Application;
import javafx.stage.Stage;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		Stage splashStage = new Stage();
		SplashScreen splash = new SplashScreen();
		splash.ShowSplash(splashStage);
		
		application.data.DatabaseHelper.initializeDatabase();

		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
