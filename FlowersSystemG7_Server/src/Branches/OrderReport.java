package Branches;

import java.io.Serializable;
import java.sql.Date;

public class OrderReport implements Serializable {

	private String productCategory;
	private int	orderId;
	private String creationDate;
	private int productId;
	private String productName;
	private double price;
	private String paymentMethod;
	private int deliveryNumber;
	private String address;
	private String phone;
	private String receiver;
	
	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public int getDeliveryNumber() {
		return deliveryNumber;
	}

	public void setDeliveryNumber(int deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
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

	public OrderReport(String productCategory, int orderId, String creationDate, int productId, String productName,
			double price, String paymentMethod, int deliveryNumber, String address, String phone, String receiver) {

		this.productCategory = productCategory;
		this.orderId = orderId;
		this.creationDate = creationDate;
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.paymentMethod = paymentMethod;
		this.deliveryNumber = deliveryNumber;
		this.address = address;
		this.phone = phone;
		this.receiver = receiver;
	}
	
	
	
	
	
	
}
