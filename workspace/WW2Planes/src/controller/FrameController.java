package controller;

import java.util.List;
import java.util.function.BiConsumer;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;

import data.AvailabilityData;
import data.SpeedData;
import data.TypeData;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.AirForce;
import model.Event;
import model.Plane;

public final class FrameController implements Rootable {

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
    @FXML private Tab typesTab;
    @FXML private AnchorPane typesAP;
    @FXML private PieChart typesPC;
    @FXML private VBox typeVB;
    @FXML private VBox planeNamesVB;
    @FXML private Text typeHeadingTxt;
    @FXML private VBox listViewsVB;
    @FXML private HBox airForcesHB;
    @FXML private JFXListView<AirForce> airForcesLV;
    @FXML private HBox eventsHB;
    @FXML private JFXListView<Event> eventsLV;
   
    @FXML
    void initialize() {
    	
    	//set events list view observable events:
		eventsLV.setItems(observEvents); 
		//set events list view cellFactory to create EventCellControllers:
		eventsLV.setCellFactory(EventCellController -> new EventCellController());
		//preselect first event in list view:
		eventsLV.getSelectionModel().select(0);
		
		//set air forces list view with observable airForces:
		airForcesLV.setItems(observAirForces);
		//set air forces list view to create AirForceCellControllers:
		airForcesLV.setCellFactory(AirForceCellController ->  new AirForceCellController(showChartData));
		
		//add change listener to events list view:
		/**https://stackoverflow.com/questions/12459086/how-to-perform-an-action-by-selecting-an-item-from-listview-in-javafx-2	*/
    	eventsLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Event>() {
    		
    		@Override //override change listener's changed: 
    	    public void changed(ObservableValue<? extends Event> observable, Event oldVal, Event newVal) {
    			
    			Event selectedEvent = newVal; //get selected event
    			observAirForces.setAll(selectedEvent.getAirForces()); //update air forces with event's
    			airForcesLV.getSelectionModel().select(0); //auto select event's first air force
          	    showEventData(selectedEvent); //show selected event's data
    	    }
    	});
    	
    	//create selection event for availabilities tab:
    	availabilitiesTab.setOnSelectionChanged (event -> {
    		
    		//create fade in transition for air forces list view:
        	FadeTransition fadeInAirForces = new FadeTransition(Duration.millis(300), airForcesLV);
        	fadeInAirForces.setFromValue(0);
        	fadeInAirForces.setToValue(1);
        	fadeInAirForces.setOnFinished(e -> airForcesLV.setDisable(false)); //enable after fade in
        	
        	//create fade out transition for air forces list view:
        	FadeTransition fadeOutAirForces = new FadeTransition(Duration.millis(300), airForcesLV);
        	fadeOutAirForces.setFromValue(1);
        	fadeOutAirForces.setToValue(0);
        	fadeOutAirForces.setOnFinished(e -> airForcesLV.setDisable(true)); //disable after fade out
        	
    		//if unselected, fade in and enable list view, else fade out and disable:
    		if(!availabilitiesTab.isSelected()) { fadeInAirForces.play(); 
    		} else { fadeOutAirForces.play(); }
    	});
    	
    	//show first event's data:
    	showEventData(observEvents.get(0));
    }
    
    //observable lists:
    private final ObservableList<Event>observEvents = FXCollections.observableArrayList();  //events
    private final ObservableList<AirForce>observAirForces = FXCollections.observableArrayList(); //air forces
    
    //data processing objects:
    private final AvailabilityData availabilityData = new AvailabilityData();
    private final SpeedData speedData = new SpeedData();
    private final TypeData typeData = new TypeData();
   
    
    //load events data from database:
    void loadEventsData(FadeTransition fadeOutPreloader) { 
    	//if events data is empty:
    	if (observEvents.isEmpty()) { 
    		new Thread(() -> { //fire new thread:
    	    	try {
    	    		//load events data:
    	    		observEvents.addAll(database.SelectEvents.select());
    	    		//set air forces with first event's air forces:
    	    		observAirForces.setAll(observEvents.get(0).getAirForces());
    	    		fadeOutPreloader.play(); //fade out preloader:
    	    	}catch(Exception e) { e.printStackTrace(); }
        	}).start();
    	}
    }
    
    //show data of given event:
    private void showEventData(Event event) {
    	
    	//create fade out transition for availability tables:
    	FadeTransition fadeOutTables = new FadeTransition(Duration.millis(300), availabilitiesAP);
    	fadeOutTables.setFromValue(1);
    	fadeOutTables.setToValue(0);
        
        //after fade out, build new tables from event, then fade back in:
    	fadeOutTables.setOnFinished(e -> {
    		
			planesTablesVB.getChildren().setAll(availabilityData.getData(event, availabilitiesAP)); //add new tables
  			FadeTransition fadeInTables = new FadeTransition(Duration.millis(300), availabilitiesAP);
  			fadeInTables.setFromValue(0);
  			fadeInTables.setToValue(1);
  			fadeInTables.play();
    	});
    	fadeOutTables.play(); //play fade out
       
    	//get event's first air force:
    	AirForce firstAirForce = event.getAirForces().get(0);
    	//show first air force's data in charts;
    	showChartData.accept(firstAirForce.getAirForceName(),firstAirForce.getAirForcePlanes());
    }
    
    //populates and shows plane names v box with names of given type:
	private void showPlaneNamesVB(String planeType) {
		//get plane names from plane type:
    	List<String>planeNames = typeData.getPlaneNamesForType(planeType); 
    	
    	//set type heading text with number of planes and given type:
    	typeHeadingTxt.setText(String.valueOf(planeNames.size()) + " " + planeType);
    	//add plane names to plane names v box:
    	planeNames.forEach(planeName-> planeNamesVB.getChildren().add(new Label(planeName)));
    
    	//create and play fade in transition for plane names v box:
    	FadeTransition fadeInPlaneNames = new FadeTransition(Duration.millis(300), planeNamesVB);
    	fadeInPlaneNames.setFromValue(0);
    	fadeInPlaneNames.setToValue(1);
    	fadeInPlaneNames.play();
	}
	
	//resets plane names v box with names of given type:
	private void resetPlaneNamesVB(String planeType) {
		//create and play fade out transition for plane names v box:
    	FadeTransition fadeOutPlaneNames = new FadeTransition(Duration.millis(300), planeNamesVB);
    	fadeOutPlaneNames.setFromValue(1);
    	fadeOutPlaneNames.setToValue(0);
    	fadeOutPlaneNames.setOnFinished(e -> {
    		
    		//after fade out, remove all plane name labels;
    		planeNamesVB.getChildren().removeIf(node -> node instanceof Label);
    		showPlaneNamesVB(planeType); //fade in plane names of given plane type
    	});
    	fadeOutPlaneNames.play();
	}
    
    //consumer for showing selected air force's chart data:
    BiConsumer<String,List<Plane>>showChartData = (airForce, planes) -> { 
    	
    	//show bar chart data:
    	speedsBC.setTitle(airForce); //set title with air force
    	speedsBC.getData().setAll(speedData.getData(planes)); //set data with speeds
    	
    	//show pie chart data:
    	typesPC.setTitle(airForce); //set title with air force
    	typesPC.getData().setAll(typeData.getData(planes)); //set data with types
    	planeNamesVB.setOpacity(0); //hide plane names v box
    	planeNamesVB.getChildren().removeIf(node -> node instanceof Label); //remove plane names
    	
    	//add mouse events to each pie chart data slice:
		/**https://docs.oracle.com/javafx/2/charts/pie-chart.htm*/
    	typesPC.getData().forEach(data ->{
    	
    		//add mouse press event to show planes of selected type:
			data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
	            @Override public void handle(MouseEvent event) {
            		//if plane names are present:
	            	if(planeNamesVB.getChildren().size() > 1) { 
	            		resetPlaneNamesVB(data.getName()); //reset plane names
	            	}else { showPlaneNamesVB(data.getName()); } //show plane names
	            }
			});
    	});
	};

}
