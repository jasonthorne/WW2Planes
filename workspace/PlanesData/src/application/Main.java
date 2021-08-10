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
		
		//create instance of preloader controller:
		final PreloaderController preloaderCtrlr = new PreloaderController();
		preloaderCtrlr.showStage(); //show its stage:
	}


}
