package application.timer;

import application.data.FocusSessionDAO;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
 * StatisticsScreen displays the daily focus statistics for a user.
 * It shows total focus time and allows navigation back to the Pomodoro timer.
 */
public class StatisticsScreen {
	
	/*
     * Builds and shows the statistics scene on the given stage.
     * Fetches today's total focus duration from the database and displays it.
     *
     * @param stage  the JavaFX stage to display the scene
     * @param userId ID of the authenticated user
     */
	public void show(Stage stage, int userId) {
		// Create DAO to retrieve focus session data
		FocusSessionDAO dao = new FocusSessionDAO();
		// Query total focus minutes for today
		int totalToday = dao.getTodayFocusDuration(userId);
		
		// Header label for the statistics screen
		Label header = new Label("Daily Statistics");
		header.setStyle("-fx-font-size: 30px; -fx-text-fill: white; -fx-font-weight: bold;");
		
		// Label to show the retrieved total focus duration
		Label durationLabel = new Label("Daily total focus time: "+totalToday+" minutes");
		durationLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");
		
		// Back button to return to the Pomodoro timer screen
		Button backButton = new Button("Back");
		backButton.setStyle("-fx-font-size: 16px; -fx-background-color: #3c3c3c; -fx-text-fill: white;");
		backButton.setOnAction(_->{
			// Navigate back to PomodoroScreen with the same user ID
			PomodoroScreen pomodoro = new PomodoroScreen(stage, userId);
			pomodoro.show();
		});
		
		// Layout container for all UI elements
		VBox root = new VBox(20, header, durationLabel, backButton);
		root.setAlignment(Pos.CENTER);
		root.setStyle("-fx-background-color: #1e1e1e;");
		
		// Create scene, apply stylesheet, and show full-screen
		Scene scene = new Scene(root, 400, 300);
		scene.getStylesheets().add(getClass().getResource("/application/style/application.css").toExternalForm());
		
		stage.setFullScreenExitHint("");
		stage.setScene(scene);
		stage.setTitle("Statistics");
		stage.setFullScreen(true);
	}
}
