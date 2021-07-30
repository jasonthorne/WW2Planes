package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.AirForce;
import model.Event;
import model.Period;
import model.Plane;
import model.Period.Block;
import model.Plane.Availability;

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
    @FXML private BarChart<?, ?> speedsBC;
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
    		
    		@Override  //override change listener's changed: 
    	    public void changed(ObservableValue<? extends Event> observable, Event oldVal, Event newVal) {
    			List<AirForce>airForces = newVal.getAirForces(); //get selected event's air forces
	        	//////showAvailabilities(airForces); //show air force availabilities
    			
    			planesTables.clear(); ///////////////MAKE THIS BETTER :P
    			airForces.forEach(airForce ->{
    				showAvailabilities(airForce);
    	    	});
    			
	        	showSpeeds(airForces); //show air force speeds
	        	
	        	planesTablesVB.getChildren().setAll(planesTables);
    	    }
    	});
    	
    	tv.setPrefSize(planesTablesSP.getPrefWidth(), planesTablesSP.getPrefHeight());
    	planesTablesVB.getChildren().setAll(tv,tv2,tv3,tv4, tv5, tv6);
    	
    }
    
    TableView<Plane> tv = new TableView<Plane>();
    TableView<Plane> tv2 = new TableView<Plane>();
    TableView<Plane> tv3 = new TableView<Plane>();
    TableView<Plane> tv4 = new TableView<Plane>();
    TableView<Plane> tv5 = new TableView<Plane>();
    TableView<Plane> tv6 = new TableView<Plane>();
    
    
    FrameController(){
    	/////this.preloaderCtrlr = preloaderCtrlr;
    	
    
    }
    
    private final ObservableList<Event>observEvents = FXCollections.observableArrayList(); //observable list of events
    //////////////////////////private ObservableList<AirForce>observAirForces;  //observable list of event's air forces
    
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
    
    //++++++++++++++++++THIS SHOULD MAYBE BE OBSERVABLE??????? ++++++++++++++
    List<TableView<Plane>>planesTables = new ArrayList<TableView<Plane>>(); //list of planesTables
    
    private void showAvailabilities(AirForce airForce) {
    	
    	//make observable list of planes from air force planes:
    	ObservableList<Plane> observPlanes = FXCollections.observableArrayList(airForce.getAirForcePlanes()); 
    	//add observable list to table view for planes:
    	TableView<Plane> planesTable = new TableView<Plane>(observPlanes);
    	//set table view size to scroll pane:
    	//planesTable.setPrefSize(planesTablesSP.getPrefWidth(), planesTablesSP.getPrefHeight());
    	
    	
    	//----------------------------
    	planesTable.setFixedCellSize(25);
    	planesTable.prefHeightProperty().bind(planesTable.fixedCellSizeProperty().multiply(Bindings.size(planesTable.getItems()).add(2.0)));
    	planesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
    	//-------------------------------
    	
    	//TreeMap of a plane's availabilities, sorted by period compareTo:
		TreeMap<Period,Availability> sortedAvails = new TreeMap<Period,Availability>( 
				observPlanes.get(0).getAvailabilities());
		
		Period start = sortedAvails.firstKey(); //get start period
		Period end = sortedAvails.lastKey(); //get end period
		
    	//create plane column:
    	//////////TableColumn<Plane,String> planeCol = new TableColumn<>("Plane");
    	TableColumn<Plane,String> planeCol = new TableColumn<>(airForce.getAirForceName());
    	
    	//set cell factory:
    	planeCol.setCellValueFactory( new PropertyValueFactory<Plane,String>("name"));
    			
    	//add plane column to table:
    	planesTable.getColumns().add(planeCol); 
    	
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
    				blockCol.setStyle( "-fx-alignment: CENTER;"); ///+++++++++HAVE THIS IN A CSS FILE FOR TABLE VIEWS ////https://stackoverflow.com/questions/13455326/javafx-tableview-text-alignment
        			blockCol.setUserData(new Period(currBlock, currYear)); //add period to block column
        			blockCol.setCellValueFactory(callBack); //set block column cell factory
            		yearCol.getColumns().add(blockCol); //add block column to year column
            		
            		//if found end date:
    				if(currBlock.equals(end.getBlock()) && currYear == end.getYear()) {
    					planesTable.getColumns().add(yearCol); //add year column to table 
    					break outerWhile; //break from outer while
    				}
    			}
    		}
    		planesTable.getColumns().add(yearCol); //add year column to table
    		currYear++; //advance to next year
    	}
    	//add table to planesTablesVB:
    	//////////planesTablesVB.getChildren().setAll(planesTable);
    	planesTables.add(planesTable);
    	
    }
    
    
    
    
    
    private void showSpeeds(List<AirForce> airForces) {
    	System.out.println("airfoece SPEED : " + airForces);
    }
    
    
    
    
    
}
