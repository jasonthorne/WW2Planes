package application;

import controller.DisplayController;
import javafx.application.Application;
import javafx.stage.Stage;



public class Main extends Application{

	public static void main(String[] args) {
		
		//test data pull:
		////SelectAll.select(Table.EVENTS);
		launch();
		
	}

	@Override
	public void start(Stage arg0) throws Exception {
		
		DisplayController displayCtrlr = DisplayController.getDisplayCtrlr();
		displayCtrlr.showStage();
		
	}
	
}