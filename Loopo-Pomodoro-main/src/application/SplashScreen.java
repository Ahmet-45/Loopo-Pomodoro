package application;
import application.login.LoginScreen;
import javafx.animation.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class SplashScreen {
	public void ShowSplash(Stage Stage) {
		ImageView logo = new ImageView(new Image("file:LoopoFoto.png"));
		logo.setFitWidth(300);
		logo.setPreserveRatio(true);
		logo.setOpacity(0);



		FadeTransition fade = new FadeTransition(Duration.seconds(5),logo);
		fade.setFromValue(0);
		fade.setToValue(1);
		fade.play();
		
		PauseTransition pause = new PauseTransition(Duration.seconds(5));
		pause.setOnFinished(e -> {
			Stage.close();
			Stage loginStage = new Stage();
			LoginScreen login = new LoginScreen(loginStage);
			login.show();
			loginStage.show();
			
		});
		pause.play();
		
		StackPane root = new StackPane(logo);
		Scene sceneSplash = new Scene(root,600,400);
		Stage.initStyle(StageStyle.UNDECORATED);
		Stage.setScene(sceneSplash);
		Stage.show();
	}

	

}
