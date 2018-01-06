package Commons;

import java.io.Serializable;
import java.sql.Date;

import Customers.Complain;
import Orders.Order;

public class Refund implements Serializable {
	
	private int id;
	private Date creationDate;
	private double amount;
	private int refundAbleId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getRefundAbleId() {
		return refundAbleId;
	}
	public void setRefundAbleId(int refundAbleId) {
		this.refundAbleId = refundAbleId;
	}

	
	public Refund(int id, Date creationDate, double amount, int refundAbleId) {
		super();
		this.id = id;
		this.creationDate = creationDate;
		this.amount = amount;
		this.refundAbleId = refundAbleId;
	}
	
	public Refund(Date creationDate, double amount, int refundAbleId) {
		super();
		this.creationDate = creationDate;
		this.amount = amount;
		this.refundAbleId = refundAbleId;
	}
	
}
