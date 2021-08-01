package chart;

import java.util.List;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import model.AirForce;

public final class SpeedsBarChart {
	
	//singleton reference:
	private static SpeedsBarChart singleSpeedsBarChart = null;

	//===================can prob be in parent with <?><?> for BarCVhart========
	
	final static CategoryAxis xAirForces = new CategoryAxis();
	final static NumberAxis ySpeeds = new NumberAxis();
	
	//speeds bar chart:
	private static BarChart<String,Number>barChart = new BarChart<String,Number>(xAirForces, ySpeeds);
		
	//=================================================================
	
	
	
	private SpeedsBarChart(List<AirForce> airForces) {
		
		airForces.forEach(airForce ->{
			
			airForce.getAirForcePlanes().forEach(plane ->{
				
				 XYChart.Series<String,Number> seriesTEST = new XYChart.Series<String, Number>();
				 seriesTEST.setName(plane.getName());
				 seriesTEST.getData().add(new Data<String, Number>(airForce.getAirForceName(),plane.getSpeed()));
				 barChart.getData().add(seriesTEST);
			});
			
		});
		
	}
	
	
	//get speeds bar chart:
	public static BarChart<String,Number> getSpeedsBarChart(List<AirForce> airForces) {
        if (singleSpeedsBarChart == null){ //create singleton if necessary
        	singleSpeedsBarChart = new SpeedsBarChart(airForces);}
        return barChart; 
	}
	
	
	

}
