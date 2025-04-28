package application.timer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import application.data.FocusSession;
import application.data.FocusSessionDAO;

/*
 * PomodoroScreen manages the full-screen Pomodoro timer UI,
 * including work/break cycles, menu interactions, and stats/settings overlays.
 */
public class PomodoroScreen {
	/*
     * Primary stage for displaying the Pomodoro UI.
     */
	private Stage stage;
	/*
     * Time line for scheduling count down updates every second.
     */
	private Timeline timeline;
	/*
     * Root pane combining content and slide-out menu.
     */
	private StackPane root;
	
	// Main content layout and hidden slide-out menu panel
	private VBox content;
	private VBox slideMenu;
	
	// Timer state
	private int remainingSeconds = 25;	// Default 25 minutes work interval
	private boolean isRunning = false;	// Indicates if timer is active
	private boolean isWorkTime = true;	 // Toggle between work and break
	private boolean readyToStart = true;	// Prevents restart mid-cycle
	
	// User session tracking
	private int userId;
	private LocalDateTime sessionStartTime;
	
	// UI controls
	private Label timeLabel;
	private Label statusLabel;
	private Button toggleButton;
	
	public VBox getSlideMenu() {
		return slideMenu;
	}


	public void setSlideMenu(VBox slideMenu) {
		this.slideMenu = slideMenu;
	}
	
	/*
     * Constructs a PomodoroScreen for the given stage and user ID.
     * @param stage primary application stage
     * @param userId ID of the authenticated user for stats tracking
     */
	public PomodoroScreen(Stage stage, int userId) {
		this.stage = stage;
		this.userId = userId;
	}
	
	/*
     * Builds and displays the Pomodoro timer UI, sets up menus, and starts the cycle.
     */
	public void show() {
		 // Initialize main controls: start/stop button, status and time labels
		toggleButton = new Button("Start");
		toggleButton.getStyleClass().add("toggle-button");
		statusLabel = new Label("Working Time");
		timeLabel = new Label(formatTime(remainingSeconds));
		timeLabel.getStyleClass().add("time-label");
		
		 // Handle toggle button clicks to start/stop timer
		toggleButton.setOnAction(_->{
			if(isRunning) {
				if(timeline != null) timeline.stop();
				toggleButton.setText("Start");
				isRunning = false;
			}else if (readyToStart){
				startTimer();
				sessionStartTime = LocalDateTime.now();
				toggleButton.setText("Stop");
				isRunning = true;
				readyToStart = false;
			}
		});
		
		// Center the content vertically and horizontally
		content = new VBox(20,statusLabel, timeLabel, toggleButton);
		content.setAlignment(Pos.CENTER);
		
		// Top bar with menu button
		Button menuButton = new Button("☰");
		menuButton.setStyle("-fx-font-size: 18px; -fx-background-color: transparent; -fx-text-fill: white;");
		HBox topBar = new HBox(menuButton);
		topBar.setAlignment(Pos.TOP_LEFT);
		topBar.setPadding(new Insets(10));
		
		Button statsButton = new Button("Show Stats");
		Button settingsButton = new Button("Settings");
		statsButton.setMaxWidth(Double.MAX_VALUE);
		settingsButton.setMaxWidth(Double.MAX_VALUE);
		
		// Slide-out menu setup (initially hidden)
		VBox slideMenu = new VBox(10, statsButton, settingsButton);
		slideMenu.setId("slideMenu");
		slideMenu.setStyle("-fx-background-color: #2c2c2c; -fx-padding: 20;");
		slideMenu.setPrefWidth(200);
		slideMenu.setMaxWidth(200);
		slideMenu.setTranslateX(-200);
		slideMenu.setVisible(false);
		slideMenu.setMouseTransparent(true);
		
		// Function to close the slide menu with animation
		Runnable closeMenu = () -> {
			 TranslateTransition tt = new TranslateTransition(Duration.millis(300), slideMenu);
			 tt.setToX(-slideMenu.getPrefWidth());
			 tt.setOnFinished(_->{
				 slideMenu.setVisible(false);
				 slideMenu.setMouseTransparent(true);
			 });
			 tt.play();
		};
		
		// Toggle slide menu visibility on menu button click
		menuButton.setOnAction(_->{
			if(!slideMenu.isVisible()) {
				slideMenu.setVisible(true);
				slideMenu.setMouseTransparent(false);
				TranslateTransition tt = new TranslateTransition(Duration.millis(300), slideMenu);
				tt.setToX(0);
				tt.play();
			} else {
					closeMenu.run();
				}
			});
		
		 // Add outside-click listener to close menu
		root = new StackPane(new VBox(topBar, content),slideMenu);
		StackPane.setAlignment(slideMenu, Pos.TOP_LEFT);
		root.addEventFilter(MouseEvent.MOUSE_PRESSED, e ->{
			if(slideMenu.isVisible()) {
				Bounds b = slideMenu.localToScene(slideMenu.getBoundsInLocal());
				Point2D clickPoint = new Point2D(e.getSceneX(), e.getSceneY());
				if(!b.contains(clickPoint)) {
					closeMenu.run();
				}
			}
		});
		
		// Bind menu buttons to overlay methods
		statsButton.setOnAction(_-> showStatsOverlay());
		settingsButton.setOnAction(_-> showSettingsOverlay());
		
		// Create and show full-screen scene
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/style/application.css").toExternalForm());
		slideMenu.prefHeightProperty().bind(scene.heightProperty());
		
		stage.setFullScreenExitHint("");
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.setTitle("Pomodoro Timer");
		stage.show();
		
		 // Start the first Pomodoro work/break cycle
		startPomodoroCycle();
		
	}
	
	/*
     * Displays an overlay with today's focus stats retrieved from the database.
     */
	private void showStatsOverlay() {
		VBox statsPane = new VBox(15);
		statsPane.setStyle("-fx-background-color:rgba(0,0,0,0.85); -fx-padding:20;");
		statsPane.prefWidth(500);
		statsPane.setMaxWidth(500);
		statsPane.setMaxHeight(600);
		statsPane.setAlignment(Pos.CENTER);
		
		// Header label
		Label header = new Label("Your Focus Stats");
		header.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
		
		FocusSessionDAO dao = new FocusSessionDAO();
		// Fetch total focus minutes for today
		int totalMinutes = dao.getTodayFocusDuration(userId);
		Label minutesLabel = new Label("Todays focus time: "+totalMinutes+" minutes");
		minutesLabel.setStyle("-fx-text-fill: white; -fx-font-size: 30px;");
		// Fetch total sessions count for today
		int sessionCount = dao.getSessionsByDate(userId, LocalDate.now()).size();
		Label sessionCountLabel = new Label("Total Focus Time: "+sessionCount);
		sessionCountLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");
		
		 // Close button to remove the overlay
		Button close = new Button("Close");
		close.setStyle(
		        "-fx-background-radius: 4; " +
		        "-fx-padding: 6 12; " +
		        "-fx-font-size: 14px;"
		    );
		close.setOnAction(_ -> root.getChildren().remove(statsPane));
		
		// Assemble and add to root
		statsPane.getChildren().addAll(header, minutesLabel, sessionCountLabel, close);
		root.getChildren().add(statsPane);
		StackPane.setAlignment(statsPane, Pos.CENTER);
	}
	
	/*
     * Displays a settings overlay (placeholder for future options).
     */
	private void showSettingsOverlay() {
		VBox settingsPane = new VBox(15);
		settingsPane.setStyle("-fx-background-color:rgba(0,0,0,0.85); -fx-padding:20;");
		settingsPane.setMaxWidth(300);
		settingsPane.setMaxHeight(400);
		settingsPane.setAlignment(Pos.CENTER);
		
		Label header = new Label("Settings");
		Button close = new Button("CLose");
		close.setOnAction(_-> root.getChildren().remove(settingsPane));
		
		settingsPane.getChildren().addAll(header, new Label("Ayarlar burda olacak"),close);
		
		root.getChildren().add(settingsPane);
		StackPane.setAlignment(settingsPane, Pos.CENTER);
		
	}
	
	/*
     * Initializes a new Pomodoro cycle by resetting timer and UI state.
     */
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
		
	/*
     * Starts the count down and records work sessions on completion.
     */
	private void startTimer() {
		timeline = new Timeline(new KeyFrame(Duration.seconds(1),_->{
			remainingSeconds--;
			updateTimer();
			
			
			if(remainingSeconds <= 0) {
				timeline.stop();
				// Record focus session if ending a work period
				if(sessionStartTime != null && isWorkTime) {
					LocalDateTime sessionEndTime = LocalDateTime.now();
					FocusSession session = new FocusSession(
							userId,
							sessionStartTime.toString(),
							sessionEndTime.toString(),
							25,
							false
						);
						FocusSessionDAO dao = new FocusSessionDAO();
						dao.insertSession(session);
				}
				
				isWorkTime = !isWorkTime;
				startPomodoroCycle();
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
	
	
	/*
     * Updates the time label text based on remaining seconds.
     */
	private void updateTimer() {
		int minutes = remainingSeconds / 60;
		int seconds = remainingSeconds % 60;
		timeLabel.setText(String.format("%02d:%02d", minutes, seconds));
	}
	
	/*
     * Formats seconds into MM:SS string.
     * @param totalSeconds total remaining seconds
     * @return formatted time string
     */
	private String formatTime(int totalSeconds) {
		int minutes = totalSeconds / 60;
		int seconds = totalSeconds % 60;
		return String.format("%02d:%02d", minutes, seconds);
	}
}
