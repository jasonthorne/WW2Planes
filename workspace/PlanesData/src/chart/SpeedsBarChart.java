package chart;

import java.util.List;

import model.AirForce;

public final class SpeedsBarChart {
	
	//singleton reference:
	private static SpeedsBarChart singleSpeedsBarChart = null;

	private SpeedsBarChart(List<AirForce> airForces) {
		
	}
	
	//get speeds bar chart singleton:
	public static SpeedsBarChart getSpeedsBarChart(List<AirForce> airForces) {
        if (singleSpeedsBarChart == null){ //create singleton if necessary
        	singleSpeedsBarChart = new SpeedsBarChart(airForces);}
        return singleSpeedsBarChart; 
	}
	
	

}
