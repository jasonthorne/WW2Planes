package table;

import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeMap;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import model.AirForce;
import model.Period;
import model.Plane;
import model.Period.Block;
import model.Plane.Availability;

public interface AvailabilitiesTable {
		
	public static TableView<Plane>getTable(AirForce airforce, Pane pane) {
		
		//make observable list of planes from air force planes:
    	ObservableList<Plane> observPlanes = FXCollections.observableArrayList(airforce.getAirForcePlanes()); 
    	//add observable list to table view for planes:
    	TableView<Plane> planesTable = new TableView<Plane>(observPlanes);
    	
    	//set table view size to anchor pane:
    	planesTable.setPrefSize(pane.getPrefWidth(), pane.getPrefHeight());
    	
    	//set cell sizes with confusing, borrowed code!:
    	/**https://stackoverflow.com/questions/27945817/javafx-adapt-tableview-height-to-number-of-rows*/
    	planesTable.setFixedCellSize(25);
    	planesTable.prefHeightProperty().bind(
    			planesTable.fixedCellSizeProperty().multiply(Bindings.size(planesTable.getItems()).add(2.0)));
    	/** https://stackoverflow.com/questions/28428280/how-to-set-column-width-in-tableview-in-javafx */
    	planesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
    	//TreeMap of a plane's availabilities, sorted by period compareTo:
		TreeMap<Period,Availability> sortedAvails = new TreeMap<Period,Availability>( 
				observPlanes.get(0).getAvailabilities());
		
		Period start = sortedAvails.firstKey(); //get start period
		Period end = sortedAvails.lastKey(); //get end period
		
    	//create plane column:
    	TableColumn<Plane,String> planeCol = new TableColumn<>(airforce.getAirForceName());
    	planeCol.setId("plane-col"); //give id for style sheet
    	planeCol.setCellValueFactory(new PropertyValueFactory<Plane,String>("name")); //set cell factory
    	planesTable.getColumns().add(planeCol); //add plane column to table
    	
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
    					planesTable.getColumns().add(yearCol); //add year column to table 
    					break outerWhile; //break from outer while
    				}
    			}
    		}
    		planesTable.getColumns().add(yearCol); //add year column to table
    		currYear++; //advance to next year
    	}
		return planesTable; //return built table
	}
}
