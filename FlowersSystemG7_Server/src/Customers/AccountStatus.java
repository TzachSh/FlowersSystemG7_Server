package Customers;

import java.io.Serializable;
/**
 * 
 * Enumeration to define Account Status 
 *
 */
public enum AccountStatus implements Serializable {
	/***
	 * Account possibly states
	 */
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
