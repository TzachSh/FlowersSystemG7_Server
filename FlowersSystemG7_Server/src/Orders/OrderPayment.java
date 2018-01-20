package Orders;

import java.io.Serializable;
import java.sql.Date;

public class OrderPayment implements Serializable {
	private int id;
	private int orderId;
	private PaymentMethod paymentMethod;
	private double amount;
	private Date paymentDate;
	
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public OrderPayment(int id, int orderId, PaymentMethod paymentMethod, double amount, Date paymentDate) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.paymentMethod = paymentMethod;
		this.amount = amount;
		this.paymentDate = paymentDate;
	}
	public OrderPayment(PaymentMethod paymentMethod, double amount, Date paymentDate) {
		super();
		this.paymentMethod = paymentMethod;
		this.amount = amount;
		this.paymentDate = paymentDate;
	}
	public OrderPayment(PaymentMethod paymentMethod, double amount) {
		super();
		this.paymentMethod = paymentMethod;
		this.amount = amount;
	}
}
