package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.function.BiConsumer;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;

import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
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
import model.Period.Block;

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
    //-------------------------
    @FXML private Tab typesTab;
    @FXML private AnchorPane typesAP;
    @FXML private PieChart typesPC;
    //-------------------
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
		airForcesLV.setCellFactory(AirForceCellController ->  new AirForceCellController(showSpeeds));
		
		//add change listener to events list view:
		/**https://stackoverflow.com/questions/12459086/how-to-perform-an-action-by-selecting-an-item-from-listview-in-javafx-2	*/
    	eventsLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Event>() {
    		
    		@Override //override change listener's changed: 
    	    public void changed(ObservableValue<? extends Event> observable, Event oldVal, Event newVal) {
    			//set air forces with event's air forces:
        	    observAirForces.setAll(newVal.getAirForces()); 
        	    showEventData(newVal); //show selected event data
        	    //testPieChart(); //=============
        	    showTypes.accept(newVal.getAirForces().get(0).getAirForceName(), newVal.getAirForces().get(0).getAirForcePlanes());
    	    }
    	});
    	
    	//show first event data:
    	showEventData(observEvents.get(0));
    }
    
    FrameController(){
    	
    }
    
    //observable lists:
    private final ObservableList<Event>observEvents = FXCollections.observableArrayList();  //events
    private final ObservableList<AirForce>observAirForces = FXCollections.observableArrayList(); //air forces
    
    //------------------------------------------------------------------
    /*
   // private final ObservableList<PieChart.Data> observPieChartData = FXCollections.observableArrayList(
           new PieChart.Data("Grapefruit", 13),
            new PieChart.Data("Oranges", 25),
            new PieChart.Data("Plums", 10),
            new PieChart.Data("Pears", 22),
            new PieChart.Data("Apples", 30));
    
   
    void testPieChart() { //(AirForce airForce, List<Plane>planes){
    	
    	System.out.println("hi");
    	observPieChartData.setAll(new PieChart.Data("Grapefruit", 13),
                new PieChart.Data("Oranges", 25),
                new PieChart.Data("Plums", 10),
                new PieChart.Data("Pears", 22),
                new PieChart.Data("Apples", 30));
    	
    	typesPC.getData().setAll(observPieChartData);
    }*/
    
    
    //consumer for showing plane types on pie chart:
    BiConsumer<String,List<Plane>> showTypes = (airForce,planes) -> {
    	
    	//list of pie chart data:
    	ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(); 
    	
		planes.forEach(plane ->{
			
			///pieChartData.add(new PieChart.Data(plane.getType(), 5));
			System.out.println(plane.getType());
			/*
			XYChart.Series<String,Number> series = new XYChart.Series<String, Number>(); //create series
			series.setName(plane.getName()); //add plane name
			series.getData().add(new Data<String, Number>("Planes",plane.getSpeed())); //add planes speed
			planeSeries.add(series); //add series to list
			*/
		});
		//speedsBC.getData().setAll(planeSeries); //set chart with series list
		//speedsBC.setTitle(airForce); //set title with air force
		typesPC.getData().setAll(pieChartData);
		
	};
    
    //------------------------------------------------------------------
    
    //consumer for showing plane speeds on bar chart:
    BiConsumer<String,List<Plane>> showSpeeds = (airForce,planes) -> {
		
		ObservableList<XYChart.Series<String,Number>>
		planeSeries = FXCollections.observableArrayList(); //list of series
		
		planes.forEach(plane ->{
			XYChart.Series<String,Number> series = new XYChart.Series<String, Number>(); //create series
			series.setName(plane.getName()); //add plane name
			series.getData().add(new Data<String, Number>("Planes",plane.getSpeed())); //add planes speed
			planeSeries.add(series); //add series to list
		});
		speedsBC.getData().setAll(planeSeries); //set chart with series list
		speedsBC.setTitle(airForce); //set title with air force
	};
    
   
    //load data from database:
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
    		planesTablesVB.getChildren().setAll(getAvailabilities(event)); //add new tables
  			FadeTransition fadeInTables = new FadeTransition(Duration.millis(300), availabilitiesAP);
  			fadeInTables.setFromValue(0);
  			fadeInTables.setToValue(1);
  			fadeInTables.play();
    	});
    	fadeOutTables.play(); //play fade out
       
    	//get event's first air force:
    	AirForce firstAirForce = event.getAirForces().get(0);
    	
    	//show first air force's speeds in bar chart;
    	showSpeeds.accept(firstAirForce.getAirForceName(),firstAirForce.getAirForcePlanes());
		
    }
    
    //get availabilities tables for given event:
	private List<TableView<Plane>>getAvailabilities(Event event) {
		
		//use tree set to sort periods by period's compareTo:
		TreeSet<Period> sortedPeriods = new TreeSet<Period>(event.getPeriods());
		Period start = sortedPeriods.first(); //get start period
		Period end = sortedPeriods.last(); //get end period
		
		//make list for holding planes tables:
		List<TableView<Plane>>planesTables = new ArrayList<TableView<Plane>>();
		
		//for each air force in event:
		event.getAirForces().forEach(airForce ->{
			
			//make observable list of planes from air force planes:
	    	ObservableList<Plane> observPlanes = FXCollections.observableArrayList(airForce.getAirForcePlanes());
	    	//add observable list to table view for planes:
	    	TableView<Plane> planesTable = new TableView<Plane>(observPlanes);
	    	
	    	//set table view size to it's anchor pane:
	    	planesTable.setPrefSize(availabilitiesAP.getPrefWidth(), availabilitiesAP.getPrefHeight());
	    	 
	    	//set cell sizes with confusing, borrowed code!:
	    	/**https://stackoverflow.com/questions/27945817/javafx-adapt-tableview-height-to-number-of-rows*/
	    	planesTable.setFixedCellSize(25);
	    	planesTable.prefHeightProperty().bind(
	    			planesTable.fixedCellSizeProperty().multiply(Bindings.size(planesTable.getItems()).add(3.0)));
	    	/** https://stackoverflow.com/questions/28428280/how-to-set-column-width-in-tableview-in-javafx */
	    	planesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    	
	    	//create air force column:
	    	TableColumn<Plane, String> airForceCol = new TableColumn<>();
	    	airForceCol.setId("airforce-col"); //give id for style sheet
	    	Label airForceLbl = new Label(airForce.getAirForceName()); //label for styled content
	    	airForceLbl.setId("airforce-col-label"); //give id for style sheet
	    	airForceCol.setGraphic(airForceLbl); //add label to column
	    	
	    	//create plane column:
	    	TableColumn<Plane,String> planeCol = new TableColumn<>("Plane");
	    	planeCol.setId("plane-col"); //give id for style sheet
	    	planeCol.setCellValueFactory(new PropertyValueFactory<Plane,String>("name")); //set cell factory
	    	airForceCol.getColumns().add(planeCol); //add plane column to air force column
	    	
	    	//year and block columns:
	    	TableColumn<Plane,String> yearCol;
	    	TableColumn<Plane,String> blockCol;  
	    	
	    	//call back for populating block column cells with plane period availabilities:
	    	Callback<TableColumn.CellDataFeatures<Plane, String>, ObservableValue<String>> callBack = 
	                new Callback<TableColumn.CellDataFeatures<Plane, String>, ObservableValue<String>>() {
	            @Override
	            public ObservableValue<String> call(TableColumn.CellDataFeatures<Plane, String> param) {
	            	 return new SimpleStringProperty(
	            			 param.getValue().getAvailabilities().get(
	            					 param.getTableColumn().getUserData()).toString());		
	            }
	        };/** https://stackoverflow.com/questions/21639108/javafx-tableview-objects-with-maps */
	        
	        
	        int currYear = start.getYear(); //holds year values
			Block currBlock; //holds block values
	    	Iterator<Block>blocksIterator; //blocks iterator
	    	boolean canAdd = false; //flag for adding values
	    	
	    	outerWhile:
	    	while(currYear <= end.getYear()) { //loop through years
	    		
	    		yearCol = new TableColumn<>(String.valueOf(currYear)); //create year column
	    		blocksIterator = Arrays.asList(Block.values()).iterator(); //(re)set blocks iterator
	    		
	    		while(blocksIterator.hasNext()) { //loop through blocks
	    			currBlock = blocksIterator.next(); //advance to next block
	    			
	    			//if found start date, allow adding of values:
	    			if(currBlock.equals(start.getBlock()) && currYear == start.getYear()) {canAdd = true;}
	    				
	    			if(canAdd) {
	    				blockCol = new TableColumn<>(String.valueOf(currBlock)); //create block column
	    				blockCol.setId("block-col"); //give block column id for style sheet
	        			blockCol.setUserData(new Period(currBlock, currYear)); //add period to block column
	        			blockCol.setCellValueFactory(callBack); //set block column cell factory
	            		yearCol.getColumns().add(blockCol); //add block column to year column
	            		
	            		//if found end date:
	    				if(currBlock.equals(end.getBlock()) && currYear == end.getYear()) {
	    					airForceCol.getColumns().add(yearCol); //add year column to air force column
	    					break outerWhile; //break from outer while
	    				}
	    			}
	    		}
	    		airForceCol.getColumns().add(yearCol); //add year column to air force column
	    		currYear++; //advance to next year
	    	}
	    	planesTable.getColumns().add(airForceCol); //add air force column to table
	    	planesTables.add(planesTable); //add table to tables
		});
		return planesTables; //return planes tables
	}
    
   
}
