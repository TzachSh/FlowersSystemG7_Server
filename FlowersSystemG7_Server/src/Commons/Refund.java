package Commons;

import java.io.Serializable;
import java.sql.Date;

import Customers.Complain;
import Orders.Order;

/***
 * 
 * Entity class to define a refund
 *
 */
public class Refund implements Serializable {
	/***
	 * Instance variables
	 */
	private int id;
	private Date creationDate;
	private double amount;
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
	 * @param id
	 * @param creationDate
	 * @param amount
	 * @param refundAbleId
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
	 * @param creationDate
	 * @param amount
	 * @param refundAbleId
	 */
	public Refund(Date creationDate, double amount, int refundAbleId) {
		super();
		this.creationDate = creationDate;
		this.amount = amount;
		this.refundAbleId = refundAbleId;
	}
	
}
