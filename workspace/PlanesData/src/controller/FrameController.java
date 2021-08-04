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
import javafx.scene.text.Text;
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
    @FXML private BarChart<String,Number> speedsBC;
    @FXML private CategoryAxis xAirforcesCA;
    @FXML private NumberAxis ySpeedsNA;
    @FXML private HBox airForcesHB;
    @FXML private JFXListView<AirForce> airForcesLV;
    @FXML private HBox eventsHB;
    @FXML private JFXListView<Event> eventsLV;
    
    @FXML
    void initialize() {
    	
    	//add observable events to events list view:
		eventsLV.setItems(observEvents);
		//set events list view cellFactory to create EventCellControllers:
		eventsLV.setCellFactory(EventCellController -> new EventCellController());
		
		//add change listener to events list view:
		/**https://stackoverflow.com/questions/12459086/how-to-perform-an-action-by-selecting-an-item-from-listview-in-javafx-2	*/
    	eventsLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Event>() {
    		
    		@Override //override change listener's changed: 
    	    public void changed(ObservableValue<? extends Event> observable, Event oldVal, Event newVal) {
    			
    			
    			System.out.println("A");
    			
    			//==========================================
    			//add selected event's air forces to observable airForces:
    	        observAirForces = FXCollections.observableArrayList(newVal.getAirForces());
    			
    			////////############observAirForces.setAll(newVal.getAirForces());
    	        System.out.println("B");
    	       airForcesLV.setItems(observAirForces); //set list view with airForces
    	       
    	        System.out.println("C");
    	        //////buildTables(observAirForces); //+++++++++++++DELETE THIS :P 
    			
    			
    			
    			//==========================================
    			
    			
    			/*
    			//------------------------------
    			List<AirForce>airForces = newVal.getAirForces(); //get selected event's air forces
	        	
    			//make list of planes tables from air forces:
    			List<TableView<Plane>>planesTables = airForces.stream()
    					.map(airForce -> AvailabilitiesTable.getTable(airForce,availabilitiesAP))
    					.collect(Collectors.toList());
    			
    			planesTablesVB.getChildren().setAll(planesTables); //add planes tables to vb
    			
    			//--------------------------------
    			  */
    	        
    			
    			
	        	//showSpeeds(airForces); //show air force speeds
	        	//SpeedsBarChart.getBarChart(airForces); +++++++++
    			
    			//speedsSP.getChildren().add(SpeedsBarChart.getSpeedsBarChart(airForces));
    			/////graphAP.getChildren().setAll(SpeedsBarChart.getSpeedsBarChart(airForces));
    			//////speedsBC.getData().setAll(elements) = SpeedsBarChart.getSpeedsBarChart(airForces);
    			///////////////////////speedsBC.setTitle("Airforce name");
    			//////////speedsBC.setStyle("-fx-font-size: " + 15 + "px;");
    			
    			/**https://stackoverflow.com/questions/29423510/display-chart-in-javafx*/
    	        /*
    			//for (int i=0;i<4;i++){
    				XYChart.Series<String, Number> series1 = new Series<String, Number>();
    				series1.setName("Plane name here");    
        	        series1.getData().add(new Data<String, Number>("Planes", 80));
        	        //Tooltip.install(series1.getNode(), new Tooltip("Yo dawg!"));
        	        speedsBC.getData().add(series1);
    			//}
    			*/
    	      
    	    }
    	});
    	
    	/////////////////???????airForcesLV.setItems(observAirForces); //set list view with airForces
    	String TEST = "yo";
    	//set air forces list view to create AirForceCellControllers:
    	airForcesLV.setCellFactory(AirForceCellController ->  new AirForceCellController(TEST));
    	
    	
    	//add change listener to air forces list view:
    	airForcesLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AirForce>() {
   
			@Override //override change listener's changed: 
			public void changed(ObservableValue<? extends AirForce> observable, AirForce oldVal, AirForce newVal) {
				System.out.println("airforce is: " + newVal.getAirForceName());
				
			}

    	});
    }
    
   //https://stackoverflow.com/questions/55675064/how-to-create-a-barchart-or-a-linechart-in-javafx-using-observablelists
  
    FrameController(){
    	
    }
  
    //observable list of events:
    private final ObservableList<Event>observEvents = FXCollections.observableArrayList();
    //observable list of event's air forces:
    private ObservableList<AirForce>observAirForces; // = FXCollections.observableArrayList(); 
    
    
    //load events data from db:
    void loadEventsData(FadeTransition fadeOutPreloader) { 
    	//if events data is empty:
    	if (observEvents.isEmpty()) { 
    		new Thread(() -> { //fire new thread:
    	    	try {
    	    		observEvents.addAll(database.SelectEvents.select()); //load events data
    	    		fadeOutPreloader.play(); //fade out preloader:
    	    	}catch(Exception e) { e.printStackTrace(); }
        	}).start();
    	}
    }
    
  
    private void buildTables (List<AirForce> airForces) {
    	////////System.out.println("airfoece SPEED : " + airForces);
    }
    
    private void showSpeeds(List<AirForce> airForces) {
    	////////System.out.println("airfoece SPEED : " + airForces);
    }
    
    
    
    
    
}
