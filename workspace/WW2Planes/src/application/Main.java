package application;

import controller.PreloaderController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) {
		
		//set stage's icon and title:
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/spitfire.png")));
		stage.setTitle("WW2 Planes");
		
		//--------------------------------------------
		
		stage.setOnCloseRequest(event ->{
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					System.out.println("closing stage"); //+++serialize data ++++
					//https://www.javatpoint.com/serialization-in-java
				}
			});
		});
		//----------------------------------------
		
		//create preloader controller with stage:
		final PreloaderController preloaderCtrlr = new PreloaderController(stage);
		preloaderCtrlr.showStage(); //show stage using controller
	}
}