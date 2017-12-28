package Orders;

import java.io.Serializable;

public class OrderPayment implements Serializable {
	private int id;
	private int orderId;
	private PaymentMethod paymentMethod;
	private double amount;
	
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
	
	public OrderPayment(int id, int orderId, PaymentMethod paymentMethod, double amount) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.paymentMethod = paymentMethod;
		this.amount = amount;
	}
}
