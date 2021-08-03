package controller;

import java.util.List;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.AirForce;
import model.Event;
import model.Plane;
import table.AvailabilitiesTable;
import chart.SpeedsBarChart;

public class FrameController implements Rootable {

	//root fxml element & children:
    @FXML private StackPane rootSP;
    @FXML private AnchorPane bodyAP;
    @FXML private JFXTabPane tabsTP;
    @FXML private Tab availabilitiesTab;
    @FXML private AnchorPane availabilitiesAP;
    @FXML private ScrollPane planesTablesSP;
    @FXML private VBox planesTablesVB;
    @FXML private Tab speedsTab;
    @FXML private AnchorPane speedsAP;
    @FXML private ScrollPane speedsSP;
    //@FXML private Pane sppedsPane;
    //@FXML
   // private AnchorPane graphAP;
    
    //----------------
    @FXML private BarChart<String,Number> speedsBC;
    @FXML private CategoryAxis xAirforces;
    @FXML private NumberAxis ySpeeds;
    
    
    @FXML private HBox airForcesHB;
    @FXML private JFXListView<AirForce> airForcesLV;
    
    
    //----------------------
    /////////////https://stackoverflow.com/questions/51574266/align-contents-of-a-listview-using-javafx
    @FXML
    private HBox eventsHB;
    @FXML private JFXListView<Event> eventsLV;
    
    @FXML
    void initialize() {
    	
    	//add observable events to events list view:
		eventsLV.setItems(observEvents);
		//set list view cellFactory to create EventCellControllers:
		eventsLV.setCellFactory(EventCellController -> new EventCellController());
		
		//add change listener to events list view:
		/**https://stackoverflow.com/questions/12459086/how-to-perform-an-action-by-selecting-an-item-from-listview-in-javafx-2	*/
    	eventsLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Event>() {
    		
    		@Override //override change listener's changed: 
    	    public void changed(ObservableValue<? extends Event> observable, Event oldVal, Event newVal) {
    			
    			List<AirForce>airForces = newVal.getAirForces(); //get selected event's air forces
	        	
    			//make list of planes tables from air forces:
    			List<TableView<Plane>>planesTables = airForces.stream()
    					.map(airForce -> AvailabilitiesTable.getTable(airForce,availabilitiesAP))
    					.collect(Collectors.toList());
    			
    			planesTablesVB.getChildren().setAll(planesTables); //add planes tables to vb
    			
	        	//showSpeeds(airForces); //show air force speeds
	        	//SpeedsBarChart.getBarChart(airForces); +++++++++
    			
    			//speedsSP.getChildren().add(SpeedsBarChart.getSpeedsBarChart(airForces));
    			/////graphAP.getChildren().setAll(SpeedsBarChart.getSpeedsBarChart(airForces));
    			//////speedsBC.getData().setAll(elements) = SpeedsBarChart.getSpeedsBarChart(airForces);
    			speedsBC.setTitle("Airforce name");
    			//////////speedsBC.setStyle("-fx-font-size: " + 15 + "px;");
    			
    			/**https://stackoverflow.com/questions/29423510/display-chart-in-javafx*/
    			//for (int i=0;i<4;i++){
    				XYChart.Series<String, Number> series1 = new Series<String, Number>();
    				series1.setName(i++ + "Plane name");    
        	        series1.getData().add(new Data<String, Number>("Planes", 80));
        	        //Tooltip.install(series1.getNode(), new Tooltip("Yo dawg!"));
        	        speedsBC.getData().add(series1);
    			//}
    			
    	      
    	    }
    	});
    	
    	///////////@@@@@@@@@@@
    	//https://stackoverflow.com/questions/17429508/how-do-you-get-javafx-listview-to-be-the-height-of-its-items
    	
    	/*
    	eventsLV.setPrefWidth(observEvents.size() * 24 + 2);
    	
    	observEvents.addListener(new ListChangeListener<Event>() {
    	    @Override
    	    public void onChanged(ListChangeListener.Change change) {
    	    	eventsLV.setPrefWidth(observEvents.size() * 24 + 2);
    	    }

    	});*/
    }
    
   //https://stackoverflow.com/questions/55675064/how-to-create-a-barchart-or-a-linechart-in-javafx-using-observablelists
  
    FrameController(){
    	
    }
    
    
    int i = 0; /**+++++++++++++++++++DELETE THIS :P */
    
    
    
    private final ObservableList<Event>observEvents = FXCollections.observableArrayList(); //observable list of events
    
    void loadEventsData(FadeTransition fadeOutPreloader){ //load events data from db
    	
    	if (observEvents.isEmpty()) { //if events data is empty:
    		new Thread(() -> { //fire new thread:
    	    	try {
    	    		observEvents.addAll(database.SelectEvents.select()); //load events data
    	    		fadeOutPreloader.play(); //fade out preloader:
    	    	}catch(Exception e) { e.printStackTrace(); }
        	}).start();
    	}
    }
    
  
    private void showSpeeds(List<AirForce> airForces) {
    	////////System.out.println("airfoece SPEED : " + airForces);
    }
    
    
    
    
    
}
