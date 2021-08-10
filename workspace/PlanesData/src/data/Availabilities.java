package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import model.Event;
import model.Period;
import model.Plane;
import model.Period.Block;

public final class Availabilities {
	
	//lists of planes tables for events:
	private final Map<Event, List<TableView<Plane>>>eventToAvailabilities = new HashMap<Event,List<TableView<Plane>>>();
	
	//singleton instance:
  	private static final Availabilities availabilities = new Availabilities();
  	
  	//private constructor for singleton:
    private Availabilities() {}
  	
  	//get singleton instance:
    public static Availabilities getAvailabilities() {
        return availabilities; 
    }
	
	
	
	public List<TableView<Plane>>getTables(Event event, Pane pane) {
	
		List<TableView<Plane>>tablesCheck = null;
		
		//return tables if present in map:
		if((tablesCheck = eventToAvailabilities.get(event)) != null) {
			System.out.println("DIDNT EXIST");
			System.out.println(tablesCheck);
			System.out.println(eventToAvailabilities);
			return tablesCheck;
		}
		System.out.println("ALREADY MADE");
		System.out.println(tablesCheck);
		System.out.println(eventToAvailabilities);
		//else, add built tables to map:
		eventToAvailabilities.put(event, buildTables(event,pane));
		//return built tables:
		return eventToAvailabilities.get(event);
	}
	
	
	//build availabilities tables:
	private List<TableView<Plane>>buildTables(Event event, Pane pane) {
		
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
	    	planesTable.setPrefSize(pane.getPrefWidth(), pane.getPrefHeight());
	    	 
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
