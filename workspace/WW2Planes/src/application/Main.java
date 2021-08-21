package application;

import controller.PreloaderController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) {
		
		//set stage's icon and title:
		primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("iconTest.png")));
		primaryStage.setTitle("WW2 Planes");
		
		//create preloader controller with stage:
		final PreloaderController preloaderCtrlr = new PreloaderController(primaryStage);
		preloaderCtrlr.showStage(); //show stage using controller
	}
}