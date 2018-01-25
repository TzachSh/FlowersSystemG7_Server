package Branches;

import java.io.Serializable;

public class IncomeReport implements Serializable {
	
	
	private int brId;
	private String brName;
	private String amount;
	
	public IncomeReport(int brId, String brName, String amount) {

		this.brId = brId;
		this.brName = brName;
		this.amount = amount;
	}
	public int getBrId() {
		return brId;
	}
	public void setBrId(int brId) {
		this.brId = brId;
	}
	public String getBrName() {
		return brName;
	}
	public void setBrName(String brName) {
		this.brName = brName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	

}
