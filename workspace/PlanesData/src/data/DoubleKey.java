package data;

/* 
 * https://stackoverflow.com/questions/14677993/how-to-create-a-hashmap-with-two-keys-key-pair-value
 */

public class DoubleKey {
	
	/*
	private final Enum<?> key1;
    private final Enum<?> key2;
	*/
    private final Object key1;
    private final Object key2;
	
   // public DoubleKey(Enum<?> key1, Enum<?> key2) {
	public DoubleKey(Object key1, Object key2) {
        this.key1 = key1;
        this.key2 = key2;
        
        System.out.println(key1 + " " + key2); //------test print
    }
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key1 == null) ? 0 : key1.hashCode());
		result = prime * result + ((key2 == null) ? 0 : key2.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DoubleKey other = (DoubleKey) obj;
		if (key1 == null) {
			if (other.key1 != null)
				return false;
		} else if (!key1.equals(other.key1))
			return false;
		if (key2 == null) {
			if (other.key2 != null)
				return false;
		} else if (!key2.equals(other.key2))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "\n[" + key1 + ", " + key2 + "]";
	}
	
	
	

}
