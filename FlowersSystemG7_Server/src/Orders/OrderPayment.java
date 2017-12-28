package Orders;

import java.io.Serializable;

public class OrderPayment implements Serializable {
	private int id;
	private Order order;
	private PaymentMethod paymentMethod;
	private double amount;
	
	public int getId() {
		return id;
	}
	public void setOpId(int id) {
		this.id = id;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
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
	public OrderPayment(int id, Order order, PaymentMethod paymentMethod, double amount) {
		super();
		this.id = id;
		this.order = order;
		this.paymentMethod = paymentMethod;
		this.amount = amount;
	}
}
