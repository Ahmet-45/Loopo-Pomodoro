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
	private Timeline timeline;
	private int remainingSeconds = 25;
	private boolean isRunning = false;
	private boolean isWorkTime = true;
	private boolean readyToStart = true;
	
	
	private Label timeLabel;
	private Label statusLabel;
	private Button toggleButton;
	
	
	public PomodoroScreen(Stage stage) {
		this.stage=stage;
	}
	
	
	public void show() {
		
		VBox root = new VBox(20);
		root.setPadding(new Insets(20));
		root.setAlignment(Pos.CENTER);
		
		statusLabel = new Label("Working Time");
		
		timeLabel = new Label(formatTime(remainingSeconds));
		timeLabel.getStyleClass().add("time-label");
		
		
		toggleButton = new Button("Start");
		toggleButton.getStyleClass().add("toggle-button");
		
		toggleButton.setOnAction(_->{
			if(isRunning) {
				if(timeline != null) timeline.stop();
				toggleButton.setText("Start");
				isRunning = false;
			}else if (readyToStart){
				startTimer();
				toggleButton.setText("Stop");
				isRunning = true;
				readyToStart = false;
			}
		});
		
		root.getChildren().addAll(statusLabel, timeLabel, toggleButton);
		
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/style/application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Pomodoro Timer");
		stage.setFullScreen(true);
		
		startPomodoroCycle();
		
	}
	
	private void startPomodoroCycle() {
		if(timeline != null) {
			timeline.stop();
		}
		
		
		if(isWorkTime) {
			remainingSeconds = 25;
			statusLabel.setText("Working Time");
			statusLabel.setStyle("-fx-text-fill: lightgreen");
			
		}else {
			remainingSeconds = 10;
			statusLabel.setText("Break Time");
			statusLabel.setStyle("-fx-text-fill: orange");
		}
		
		updateTimer();
		readyToStart = true;
		toggleButton.setText("Start");
		isRunning= false;
		
	}
		
	private void startTimer() {
		timeline = new Timeline(new KeyFrame(Duration.seconds(1),_->{
			remainingSeconds--;
			updateTimer();
			
			
			if(remainingSeconds <= 0) {
				timeline.stop();
				isWorkTime = !isWorkTime;
				startPomodoroCycle();
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
	
	
	private void updateTimer() {
		int minutes = remainingSeconds / 60;
		int seconds = remainingSeconds % 60;
		timeLabel.setText(String.format("%02d:%02d", minutes, seconds));
	}
	
	private String formatTime(int totalSeconds) {
		int minutes = totalSeconds / 60;
		int seconds = totalSeconds % 60;
		return String.format("%02d:%02d", minutes, seconds);
	}
}
