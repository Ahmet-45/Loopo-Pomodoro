package application.timer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PomodoroScreen {
	private Stage stage;
	private int remainingSeconds = 25*60;
	private boolean isRunning = false;
	private Timeline timeline;
	
	
	public PomodoroScreen(Stage stage) {
		this.stage=stage;
	}
	
	
	public void show() {
		
		VBox root = new VBox(20);
		root.setPadding(new Insets(20));
		root.setAlignment(Pos.CENTER);
		
		Label timeLabel = new Label(formatTime(remainingSeconds));
		timeLabel.getStyleClass().add("time-label");
		
		
		Button toggleButton = new Button("Start");
		toggleButton.getStyleClass().add("toggle-button");
		
		toggleButton.setOnAction(_->{
			if(isRunning) {
				timeline.stop();
				toggleButton.setText("Start");
			}else {
				startTimer(timeLabel);
				toggleButton.setText("Stop");
			}
			
			isRunning = !isRunning;
		});
		
		root.getChildren().addAll(timeLabel, toggleButton);
		
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/style/application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Pomodoro Timer");
		stage.setFullScreen(true);
		
	}
	
	private void startTimer(Label timeLabel) {
		timeline = new Timeline(new KeyFrame(Duration.seconds(1),_->{
			if(remainingSeconds > 0) {
				remainingSeconds--;
				timeLabel.setText(formatTime(remainingSeconds));
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
	
	private String formatTime(int totalSeconds) {
		int minutes = totalSeconds / 60;
		int seconds = totalSeconds % 60;
		return String.format("%02d:%02d", minutes, seconds);
	}


	
}
