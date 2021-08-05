package table;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import model.AirForce;
import model.Event;
import model.Period;
import model.Plane;
import model.Plane.Availability;

public abstract class Availabilities { //++++++++ADD Table :P
	
	private Period start;
	private Period end;
	
	private List<AirForce>airForces;
	
	List<TableView<Plane>>TESTstartHere(List<AirForce> airForces, Pane pane){
		
		this.airForces = new ArrayList<AirForce>(airForces);
		
		setPeriods();
		setSizes(pane);
		
		return null;
		
	}
	
	//set start & end periods:
	private void setPeriods() {
		
		//TreeMap of a plane's availabilities, sorted by period compareTo:
		TreeMap<Period,Availability> sortedAvails = new TreeMap<Period,Availability>( 
				airForces.get(0).getAirForcePlanes().get(0).getAvailabilities());
		
		this.start = sortedAvails.firstKey(); //get start period
		this.end = sortedAvails.lastKey(); //get end period
	}
	
	//set table and cell sizes:
	private void setSizes(Pane pane) {
		
	}

}
