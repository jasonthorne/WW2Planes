package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;

import controller.util.Availabilities;
import controller.util.EventAirForceKey;
import controller.util.Speeds;
import database.util.SelectEvents;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;
import model.AirForce;
import model.Event;
import model.Period;
import model.Plane;
import model.Plane.Type;
import model.Period.Block;

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
		
		//set air forces list view with observable airForces:
		airForcesLV.setItems(observAirForces);
		//set air forces list view to create AirForceCellControllers:
		airForcesLV.setCellFactory(AirForceCellController ->  new AirForceCellController(showCharts));
		
		//add change listener to events list view:
		/**https://stackoverflow.com/questions/12459086/how-to-perform-an-action-by-selecting-an-item-from-listview-in-javafx-2	*/
    	eventsLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Event>() {
    		
    		@Override //override change listener's changed: 
    	    public void changed(ObservableValue<? extends Event> observable, Event oldVal, Event newVal) {
    			
    			Event selectedEvent = eventsLV.getSelectionModel().getSelectedItem();
    			
    			///////System.out.println(selectedEvent);
    			////////System.out.println("HULLO " + selectedEvent.getAirForces());
    			
    			
    			
    			
    			
    			
    			
    			
    			//List<AirForce>selectedAirForces = selectedEvent.getAirForces();
    			
    			List<AirForce>selectedAirForces = eventsLV.getSelectionModel().getSelectedItem().getAirForces();
    			
    			
    			////observAirForces = FXCollections.observableArrayList(selectedAirForces/*selectedEvent.getAirForces()*/);
    			//observAirForces.setAll(newVal.getAirForces()); 
    			
    			observAirForces.setAll(selectedEvent.getAirForces()); 
    			
    			///observAirForces.setAll(selectedAirForces); /////+++++++++++WHY IS A NULL MADE HERE :P
     	  
           	    	//airForcesLV.setItems(observAirForces); //set list view with airForces
           	   
          
     	        //+++++++++THIS HOLDS OLD AIRFORCES :PP MAYBE THEY CAN BE TARGETED HERE :P @@@@@@@@@@@@@@@@@@@@@
     	    	
             	
    			
    			
    			
          	    showEventData(selectedEvent); //show selected event controller.util
    			
          	    
          	    
    			/*
        	    Platform.runLater(new Runnable() {
            	    @Override
            	    public void run() {
            	    	//set air forces with event's air forces:
            	    	observAirForces.setAll(newVal.getAirForces()); 
                  	    showEventData(newVal); //show selected event controller.util
            	    }
            	});*/
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
    	
    	
    	
    	
    	
    	
    	airForcesLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AirForce>() {
    		
    		@Override //override change listener's changed: 
    	    public void changed(ObservableValue<? extends AirForce> observable, AirForce oldVal, AirForce newVal) {
        	    Platform.runLater(new Runnable() {
            	    @Override
            	    public void run() {
            	    	AirForce airForce = airForcesLV.getSelectionModel().getSelectedItem();
            			showCharts.accept(airForce.getAirForceName(), airForce.getAirForcePlanes());
            	    }
            	});
    	    }
    	});
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	//show first event controller.util:
    	showEventData(observEvents.get(0));
    	//#############################################https://stackoverflow.com/questions/17522686/javafx-tabpane-how-to-listen-to-selection-changes
    }
    
    //observable lists:
    private final ObservableList<Event>observEvents = FXCollections.observableArrayList();  //events
    private final ObservableList<AirForce>observAirForces = FXCollections.observableArrayList(); //air forces
    
    //------------------------------------------------------------------
   
    //show plane types on pie chart:
    private void showTypes(String airForce, List<Plane>planes){
    	
    	//list of pie chart controller.util:
    	ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    	
    	//===========================MADE GLOBAL
    	//map of plane types and lists of planes of said type:
    	Map<Plane.Type, List<String>> planeTypeToNames = new HashMap<Plane.Type, List<String>>();
    	
    	//initialize map with enum keys, holding empty lists: //++++++PUT IN GLOBAL METHOD WHICH IS THEN CALLED HERE??
    	for (Plane.Type planeType : Plane.Type.values()) {
    		//add type key with empty list value to map:
    		planeTypeToNames.put(planeType, new ArrayList<String>());
    	}
    	
    	//======================
    	
    	//loop through planes:
    	planes.forEach(plane ->{
    		//add plane's name to list of names with same type:
    		planeTypeToNames.get(plane.getType()).add(plane.getName());
    	});
    	
    	
    	//loop through map's key set:
    	for (Plane.Type planeType : planeTypeToNames.keySet()) {
    		//++++++++++++++++CHECK THAT NUMBER IS GREATER THAN 1
    		int planesNum;
    		//if plane type has list entries:
    		if((planesNum = planeTypeToNames.get(planeType).size()) > 0){
    			
    			//+++++++++TEST FOR STORING controller.util for retreival later +++++++++++
    			 PieChart.Data test = new PieChart.Data(
        				 planeType.toString(),
        				 planeTypeToNames.get(planeType).size());
    			 
    			//Add type and its amount to pie chart:
        		pieChartData.add(new PieChart.Data(planeType.toString(), planesNum));
    		}
    	}
    	
    	/*
    	Platform.runLater(new Runnable() {

    	    @Override
    	    public void run() {
    	    	typesPC.getData().setAll(pieChartData);
    			typesPC.setTitle(airForce);
    	    }
    	});*/
    	
    	typesPC.getData().setAll(pieChartData);
		typesPC.setTitle(airForce);
		
		/**https://docs.oracle.com/javafx/2/charts/pie-chart.htm*/
		for (final PieChart.Data data : typesPC.getData()) {
    	    data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,
    	        new EventHandler<MouseEvent>() {
    	            @Override public void handle(MouseEvent e) {
    	            
    	                System.out.println(planeTypeToNames.get(Plane.Type.valueOf(
    	                				data.getName().toUpperCase().replace('-', '_'))));
    	             }
    	        });
    	}
		
		//@@@@@@@@@@@
		//https://stackoverflow.com/questions/11873041/javafx-piechart-incorrect-controller.util-handles-mouseevent
		
		
		
		/////////######################MAKE THIS A| SINGLWTON :P 
		
	};
    
	//consumer for showing plane speeds on bar chart:
    BiConsumer<String,List<Plane>> showCharts = (airForce,planes) -> { //++++++++++++++SHOW CHAARTS???
    	
    	//++++check collections for each premade thing, if not there, then invoke methods +++++++++++++
    	
		showSpeeds(airForce,planes);
		showTypes(airForce,planes);
		
	};
	
    //------------------------------------------------------------------
    
 
	//show plane speeds on bar chart:
	private void showSpeeds (String airForce, List<Plane>planes) {
    	/*
		ObservableList<XYChart.Series<String,Number>>
		planeSeries = FXCollections.observableArrayList(); //list of series
		
		planes.forEach(plane ->{
			XYChart.Series<String,Number> series = new XYChart.Series<String, Number>(); //create series
			series.setName(plane.getName()); //add plane name
			series.getData().add(new Data<String, Number>("Planes",plane.getSpeed())); //add planes speed
			planeSeries.add(series); //add series to list
		});
		speedsBC.getData().setAll(planeSeries); //set chart with series list
		*/
		
		//ObservableList<XYChart.Series<String,Number>>
		//planeSeries = speeds.getSeries(new EventAirForceKey(selectedEvent.getName(),airForce), planes);
		
		speedsBC.getData().setAll(speeds.buildSeries(planes));
		
		//speedsBC.getData().setAll(planeSeries); //set chart with series list
		
		speedsBC.setTitle(airForce); //set title with air force
	};
	
	
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	
	
	
	
	
	
   //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
   
    //load controller.util from database:
    void loadEventsData(FadeTransition fadeOutPreloader) { 
    	//if events controller.util is empty:
    	if (observEvents.isEmpty()) { 
    		new Thread(() -> { //fire new thread:
    	    	try {
    	    		//load events controller.util:
    	    		observEvents.addAll(SelectEvents.select()); 
    	    		//set air forces with first event's air forces:
    	    		observAirForces.setAll(observEvents.get(0).getAirForces());
    	    		fadeOutPreloader.play(); //fade out preloader:
    	    	}catch(Exception e) { e.printStackTrace(); }
        	}).start();
    	}
    }
    
    //show controller.util of given event:
    private void showEventData(Event event) {
    	
    	//create fade out transition for availability tables:
    	FadeTransition fadeOutTables = new FadeTransition(Duration.millis(300), availabilitiesAP);
    	fadeOutTables.setFromValue(1);
    	fadeOutTables.setToValue(0);
        
        //after fade out, build new tables from event, then fade back in:
    	fadeOutTables.setOnFinished(e -> {
    		
    		planesTablesVB.getChildren().setAll(availabilities.getTables(event, availabilitiesAP)); //add new tables
  			FadeTransition fadeInTables = new FadeTransition(Duration.millis(300), availabilitiesAP);
  			fadeInTables.setFromValue(0);
  			fadeInTables.setToValue(1);
  			fadeInTables.play();
    	});
    	fadeOutTables.play(); //play fade out
       
    	//get event's first air force:
    	AirForce firstAirForce = event.getAirForces().get(0);
    	
    	//show first air force's controller.util in charts;
    	///////++++++++showSpeeds.accept(firstAirForce.getAirForceName(),firstAirForce.getAirForcePlanes());
    	showCharts.accept(firstAirForce.getAirForceName(),firstAirForce.getAirForcePlanes());
    	
    	airForcesLV.getSelectionModel().select(0);
    	
    	///============https://stackoverflow.com/questions/11088612/javafx-select-item-in-listview
    	
    	
    	//https://stackoverflow.com/questions/31409982/java-best-practice-class-with-only-static-methods
    }
    
   
}
