package Commons;

import java.io.Serializable;

/***
 * 
 * Enumeration to define a Status of order's state
 *
 */
public enum Status implements Serializable {
	/***
	 * Possibly states
	 */
	Completed (1),Pending (2),Canceled (3);

	private final int code;
	/**
	 * 
	 * @param code setting the status code
	 */
	 private Status(int code) {
	        this.code = code;
	    }
	 	/**
	 	 * 
	 	 * @return the status code
	 	 */
	    public int toInt() {
	        return code;
	    }
	    /**
	     * printing the value of the status code 
	     */
	    public String toString() {
	        return String.valueOf(code);
	    }
}
