package chart;

import java.util.List;

import javafx.scene.chart.BarChart;
import model.AirForce;

public interface SpeedsBarChart {
	
	public static BarChart<String,Number>getBarChart(List<AirForce> airforces) {
		
		airforces.forEach(airforce -> System.out.println(airforce));
		return null;
	}

}
