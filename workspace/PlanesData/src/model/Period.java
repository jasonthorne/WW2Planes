package model;

public final class Period implements Comparable <Period>{
	
	//blocks of a year:
	public enum Block {
		EARLY("Early"), MID("Mid"), LATE("Late");
		
		private final String block; //name of block
		
		//constructor sets name of block:
		private Block(String block) { this.block = block; } 
		@Override 
		public String toString() { return block; } //return chosen block
	}
	
	private final Block block; //block
	private final int year; //year
	
	//constructor:
	public Period (Block block, int year) {
		this.block = block;
		this.year = year;
	}
	
	//getters:
	public Block getBlock() { return block; }
	public int getYear() { return year; }

	//to string:
	@Override
	public String toString() { return block + " " + year; }
	
	//for ordering during TreeMap insertion:
	@Override	
	public int compareTo(Period otherPeriod) { //compare periods
		
		//values of years: 
		Integer thisYear = year; 
		Integer otherYear = otherPeriod.getYear();
		
		//ordinal values of blocks:
		Integer thisBlock = block.ordinal(); 
		Integer otherBlock = otherPeriod.getBlock().ordinal(); 
		
		//if years are the same, order by blocks:
		if(thisYear.equals(otherYear)) { 
			return thisBlock.compareTo(otherBlock); }
		
		//if years aren't the same, order by years:
		if(!(thisYear.equals(otherYear))) { 
			return thisYear.compareTo(otherYear); }
		
		return 0; //same periods
	}

	//for unique HashMap insertion:
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((block == null) ? 0 : block.hashCode());
		result = prime * result + year;
		return result;
	}
	
	//or unique HashMap insertion:
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Period other = (Period) obj;
		if (block != other.block)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
}