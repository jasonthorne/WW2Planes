package application;

import controller.PreloaderController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) {
		
		//get preloader controller:
		final PreloaderController preloaderCtrlr = PreloaderController.getPreloaderCtrlr();
		preloaderCtrlr.showStage(); //show its stage:
	}


}
