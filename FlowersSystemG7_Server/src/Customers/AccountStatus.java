package Customers;

import java.io.Serializable;
/**
 * Enumeration to define account status
 *
 */
public enum AccountStatus implements Serializable {
	Active (0),Blocked (1);
	
	private final int code;
	/**
	 * 
	 * @param code setting the status code
	 */
	 private AccountStatus(int code) {
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
