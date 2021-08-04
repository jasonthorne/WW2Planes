package controller;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
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
    	
    	//set events list view observable events:
		eventsLV.setItems(observEvents); 
		//set events list view cellFactory to create EventCellControllers:
		eventsLV.setCellFactory(EventCellController -> new EventCellController());
		
		//set air forces list view with observable airForces:
		airForcesLV.setItems(observAirForces);
		//set air forces list view to create AirForceCellControllers:
		airForcesLV.setCellFactory(AirForceCellController ->  new AirForceCellController(showSpeedsBC));
		
		
		
		/*
		//add change listener to air forces list view:
		airForcesLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AirForce>() {
		
			@Override //override change listener's changed: 
			public void changed(ObservableValue<? extends AirForce> observable, AirForce oldVal, AirForce newVal) {
				System.out.println("airforce is: " + newVal.getAirForceName());
				
			}
		
		});
		*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//add change listener to events list view:
		/**https://stackoverflow.com/questions/12459086/how-to-perform-an-action-by-selecting-an-item-from-listview-in-javafx-2	*/
    	eventsLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Event>() {
    		
    		@Override //override change listener's changed: 
    	    public void changed(ObservableValue<? extends Event> observable, Event oldVal, Event newVal) {
    			
    			
    			System.out.println("A");
    			
    			//==========================================
    			//add selected event's air forces to observable airForces:
    	        //observAirForces = FXCollections.observableArrayList(newVal.getAirForces());
    			
    			
    			//add selected event's air forces to observable airForces:
        	    observAirForces.setAll(newVal.getAirForces());
        	    
        	    
        	    		
        	    	//airForcesLV.setItems(observAirForces); //set list view with airForces
        	    	       
        	    	       ///airForcesLV.getSelectionModel().select(0);
        	    	       
        	  //++LOAD FIRST AIFORCE GRAPH HER ON SELECTION!! 
    			System.out.println("AFTER EVENT SELECTION: first af is: " + observAirForces.get(0).getAirForceName());
    			
    		
    			///////////observAirForces.setAll(newVal.getAirForces());
    	        System.out.println("B");
    	       //////////airForcesLV.setItems(observAirForces); //set list view with airForces
    	       
    	      ////////// airForcesLV.getSelectionModel().select(0);
    	       
    	        System.out.println("C");
    	        //////buildTables(observAirForces); //+++++++++++++DELETE THIS :P 
    			
    			
    	        showSpeedsBC.accept(observAirForces.get(0).getAirForceName(), observAirForces.get(0).getAirForcePlanes());
    	        
    			
    			//==========================================
    	       //////////speedsBC.getData().clear();
    	        
    			
    			
    			//------------------------------
    			List<AirForce>airForces = newVal.getAirForces(); //get selected event's air forces
	        	
    			//make list of planes tables from air forces:
    			List<TableView<Plane>>planesTables = airForces.stream()
    					.map(airForce -> AvailabilitiesTable.getTable(airForce,availabilitiesAP))
    					.collect(Collectors.toList());
    			
    			planesTablesVB.getChildren().setAll(planesTables); //add planes tables to vb
    			
    			//--------------------------------
    			 
    	        
    			
    			
	        	//showSpeeds(airForces); //show air force speeds
	        	//SpeedsBarChart.getBarChart(airForces); +++++++++
    			
    			//speedsSP.getChildren().add(SpeedsBarChart.getSpeedsBarChart(airForces));
    			/////graphAP.getChildren().setAll(SpeedsBarChart.getSpeedsBarChart(airForces));
    			//////speedsBC.getData().setAll(elements) = SpeedsBarChart.getSpeedsBarChart(airForces);
    			///////////////////speedsBC.setTitle("Airforce name");
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
    	
    	
    	System.out.println("first af is: " + observAirForces.get(0).getAirForceName());
    	
    	//--------------------------------------------
    	showSpeedsBC.accept(
    			observAirForces.get(0).getAirForceName(), 
    			observAirForces.get(0).getAirForcePlanes());
    	 
    	/// consumeStr.accept("Hi");
    	
    	//make list of planes tables from air forces:
		List<TableView<Plane>>planesTables = observAirForces.stream()
				.map(airForce -> AvailabilitiesTable.getTable(airForce,availabilitiesAP))
				.collect(Collectors.toList());
		
		planesTablesVB.getChildren().setAll(planesTables); //add planes tables to vb
    	
    	
    	
    	
    	//--------------------------------------------------    
    	    
    	
    }
    
   //https://stackoverflow.com/questions/55675064/how-to-create-a-barchart-or-a-linechart-in-javafx-using-observablelists
  
    FrameController(){
    	
    }
    
    //observable lists:
    private final ObservableList<Event>observEvents = FXCollections.observableArrayList();  //events
    private final ObservableList<AirForce>observAirForces = FXCollections.observableArrayList(); //air forces
    
    //bi consumer for showing plane speeds on bar chart:
    BiConsumer<String,List<Plane>> showSpeedsBC = (airForce,planes) -> {
		
		ObservableList<XYChart.Series<String,Number>>
		planeSeries = FXCollections.observableArrayList(); //list of series
		
		planes.forEach(plane ->{
			XYChart.Series<String,Number> series = new XYChart.Series<String, Number>(); //create series
			series.setName(plane.getName()); //add plane name
			series.getData().add(new Data<String, Number>("Planes",plane.getSpeed())); //add planes speed
			planeSeries.add(series); //add series to list
		});

		speedsBC.getData().setAll(planeSeries); //set char with series list
		//+++BELOW SHOULD ALSO HAVE EVENT NAME - UI think we should make a new title which is above the chart, and is populated with everything
		//this means we can have consumer instead of bi consumer too! 
  		speedsBC.setTitle(airForce); //set chart title //+++++++++++++++++++++++++ANIMATED THIS (fade old from view and then this into view :P)
	};
    
   
    //load data from database:
    void loadData(FadeTransition fadeOutPreloader) { 
    	//if events data is empty:
    	if (observEvents.isEmpty()) { 
    		new Thread(() -> { //fire new thread:
    	    	try {
    	    		observEvents.addAll(database.SelectEvents.select()); //load events data
    	    		//set air forces with first event's air forces:
    	    		observAirForces.setAll(observEvents.get(0).getAirForces()); 
    	    		fadeOutPreloader.play(); //fade out preloader:
    	    	}catch(Exception e) { e.printStackTrace(); }
        	}).start();
    	}
    }
    
    private void showData() {
    	
    }
  
    private void buildTables (List<AirForce> airForces) {
    	////////System.out.println("airfoece SPEED : " + airForces);
    }
    
    private void showSpeeds(List<AirForce> airForces) {
    	////////System.out.println("airfoece SPEED : " + airForces);
    }
    
    
    
    
    
}
