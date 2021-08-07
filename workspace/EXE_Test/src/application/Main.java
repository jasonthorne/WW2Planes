package application;

import database.SelectAll;
import table.Table;


public class Main {

	public static void main(String[] args) {
		
		//test data pull:
		SelectAll.select(Table.EVENTS);
	}
	
}