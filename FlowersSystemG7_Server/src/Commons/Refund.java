package Commons;

import java.io.Serializable;
import java.sql.Date;

/**
 * Entity class to define a refund
 */
public class Refund implements Serializable {
	/***
	 * refund id
	 */
	private int id;
	/**
	 * creation of the refund
	 */
	private Date creationDate;
	/**
	 * amount value in the refund
	 */
	private double amount;
	/**
	 * purpose refund id order/complain key 
	 */
	private int refundAbleId;
	/***
	 * 
	 * @return refund id
	 */
	public int getId() {
		return id;
	}
	/**
	 * 
	 * @param id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/***
	 * 
	 * @return creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/***
	 * 
	 * @param creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * 
	 * @return refund amount
	 */
	public double getAmount() {
		return amount;
	}
	/***
	 * 
	 * @param amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/***
	 * 
	 * @return the refund able object id - can be a complain or an order
	 */
	public int getRefundAbleId() {
		return refundAbleId;
	}
	/***
	 * 
	 * @param refundAbleId to set
	 */
	public void setRefundAbleId(int refundAbleId) {
		this.refundAbleId = refundAbleId;
	}

	/***
	 * Constructor for the server side use
	 * @param id refund id
	 * @param creationDate creation of refund
	 * @param amount amount of refund
	 * @param refundAbleId purpose of the refund
	 */
	public Refund(int id, Date creationDate, double amount, int refundAbleId) {
		super();
		this.id = id;
		this.creationDate = creationDate;
		this.amount = amount;
		this.refundAbleId = refundAbleId;
	}
	/***
	 * Constructor for the client side
	 * @param creationDate creation of refund
	 * @param amount amount of refund
	 * @param refundAbleId purpose of the refund
	 */
	public Refund(Date creationDate, double amount, int refundAbleId) {
		super();
		this.creationDate = creationDate;
		this.amount = amount;
		this.refundAbleId = refundAbleId;
	}
	
}
