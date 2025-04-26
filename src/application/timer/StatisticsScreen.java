package application.timer;

import application.data.FocusSessionDAO;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StatisticsScreen {
	
	
	public void show(Stage stage, int userId) {
		FocusSessionDAO dao = new FocusSessionDAO();
		int totalToday = dao.getTodayFocusDuration(userId);
		
		Label header = new Label("Daily Statistics");
		header.setStyle("-fx-font-size: 30px; -fx-text-fill: white; -fx-font-weight: bold;");
		
		
		Label durationLabel = new Label("Daily total focus time: "+totalToday+" minutes");
		durationLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");
		
		Button backButton = new Button("Back");
		backButton.setStyle("-fx-font-size: 16px; -fx-background-color: #3c3c3c; -fx-text-fill: white;");
		backButton.setOnAction(_->{
			PomodoroScreen pomodoro = new PomodoroScreen(stage, userId);
			pomodoro.show();
		});
		
		VBox root = new VBox(20, header, durationLabel, backButton);
		root.setAlignment(Pos.CENTER);
		root.setStyle("-fx-background-color: #1e1e1e;");
		
		Scene scene = new Scene(root, 400, 300);
		scene.getStylesheets().add(getClass().getResource("/application/style/application.css").toExternalForm());
		stage.setFullScreenExitHint("");
		stage.setScene(scene);
		stage.setTitle("Statistics");
		stage.setFullScreen(true);
	}
}
