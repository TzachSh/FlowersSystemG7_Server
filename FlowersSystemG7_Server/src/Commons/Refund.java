package Commons;

import java.sql.Date;

import Customers.Complain;
import Orders.Order;

public class Refund {
	private int id;
	private Date creationDate;
	private double amount;
	private IRefundAble refundAble;
	private Status status;
	
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
	public IRefundAble getRefundAble() {
		return refundAble;
	}
	public void setRefundAble(IRefundAble refundAble) {
		this.refundAble = refundAble;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Refund(int id, Date creationDate, double amount, IRefundAble refundAble, Status status) {

		this.id = id;
		this.creationDate = creationDate;
		this.amount = amount;
		this.refundAble = refundAble;
		this.status = status;
	}
	
}
