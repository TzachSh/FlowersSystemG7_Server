package Branches;

import java.io.Serializable;

public class IncomeReport implements Serializable {
	
	
	private int brId;
	private String brName;
	private double amount;
	
	public IncomeReport(int brId, String brName, double amount) {

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
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	

}
