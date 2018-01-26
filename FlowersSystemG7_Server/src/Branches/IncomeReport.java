package Branches;

import java.io.Serializable;

/**
 * Entity class that contains the income report data
 *  
 */
public class IncomeReport implements Serializable {
	
	/** Branch id */
	private int brId;
	/** Branch name */
	private String brName;
	/** Total income amount */
	private String amount;
	
	/**
	 * Constructor for initialize all instance attributes
	 * @param brId branch id
	 * @param brName branch name
	 * @param amount income amount
	 */
	public IncomeReport(int brId, String brName, String amount) {

		this.brId = brId;
		this.brName = brName;
		this.amount = amount;
	}
	
	/**
	 * Getter for get the branch id
	 */
	public int getBrId() {
		return brId;
	}
	
	/**
	 * Setter for set the branch id
	 * @param brId branch id
	 */ 
	public void setBrId(int brId) {
		this.brId = brId;
	}
	
	/**
	 * Getter for get the branch name
	 */
	public String getBrName() {
		return brName;
	}
	
	/**
	 * Setter for set the branch name
	 * @param brName branch bane
	 */
	public void setBrName(String brName) {
		this.brName = brName;
	}
	
	/**
	 * Getter for get the income amount
	 */
	public String getAmount() {
		return amount;
	}
	
	/**
	 * Setter for set the income amount
	 * @param amount the income amount
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	

}
