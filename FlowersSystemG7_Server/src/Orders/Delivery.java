package Orders;

import java.io.Serializable;

public class Delivery implements Serializable {
	private int id;
	private String address;
	private String phone;
	private String receiver;
	private Order Order;
	
	public int getId() {
		return id;
	}
	public void setdId(int id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Order getOrder() {
		return Order;
	}
	public void setOrder(Order order) {
		Order = order;
	}
	public Delivery(int id, String address, String phone, String receiver, Orders.Order order) {
		super();
		this.id = id;
		this.address = address;
		this.phone = phone;
		this.receiver = receiver;
		Order = order;
	}
}
