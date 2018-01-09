package Customers;

import java.io.Serializable;

public enum AccountStatus implements Serializable {
	Active (0),Blocked (1),Closed (2);
	
	private final int code;
	
	 private AccountStatus(int code) {
	        this.code = code;
	    }
	 
	    public int toInt() {
	        return code;
	    }
	    
	    public String toString() {
	        return String.valueOf(code);
	    }
}
