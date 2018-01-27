package Branches;

import java.io.Serializable;
/**
 * Entity
 * Contains ready orders details for displaying in report
 */
public class OrderReport implements Serializable {
	/** category of the product */
	private String productCategory;
	/**Order id 	 */
	private int	orderId;
	/**creation date  	 */
	private String creationDate;
	/**product id 	 */
	private int productId;
	/**product name 	 */
	private String productName;
	/** product price*/
	private String price;
	/** payment method */
	private String paymentMethod;
	/**delivery number*/
	private int deliveryNumber;
	/**delivery address 	 */
	private String address;
	/**receiver phone*/
	private String phone;
	/** receiver name*/
	private String receiver;
	/**delivery status */
	private String status;
	/**
	 * @return order status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 *  setting the delivery status
	 *  @param status to set
	 *  */
	public void setStatus(String status) {
		this.status = status;
	}
	/** 
	 * @return category of the product
	 * returns the category of the product*/
	public String getProductCategory() {
		return productCategory;
	}
	/**
	 * setting category for product 
	 * @param productCategory to set
	 */
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	/**
	 * 	
	 * @return order number
	 */
	public int getOrderId() {
		return orderId;
	}
	/**
	 * 
	 * @param orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	/**
	 * 
	 * @return creation date
	 */
	public String getCreationDate() {
		return creationDate;
	}
	/**
	 * 
	 * @param creationDate to set
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * 
	 * @return product code
	 */
	public int getProductId() {
		return productId;
	}
	/**
	 * 
	 * @param productId product code to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}
	/**
	 * 
	 * @return product name
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName product name
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * 
	 * @return product price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * 
	 * @param price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * 
	 * @return payment option
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}
	
	/**
	 * @param paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	/**
	 * @return delivery number
	 */
	public int getDeliveryNumber() {
		return deliveryNumber;
	}
	/**
	 * @param deliveryNumber to set
	 */
	public void setDeliveryNumber(int deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}
	/**
	 * @return delivery address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return phone of receiver
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return receiver
	 */
	public String getReceiver() {
		return receiver;
	}
	/**
	 * @param receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
    /** 
     * 
     * @param productCategory the category of the product
     * @param orderId order number
     * @param creationDate order creation date
     * @param productId product id
     * @param productName product name
     * @param price product price
     * @param paymentMethod payment method
     * @param deliveryNumber delivery number 
     * @param address delivery address
     * @param phone receiver phone number
     * @param receiver receiver 
     * @param status deliver status
     */
	public OrderReport(String productCategory, int orderId, String creationDate, int productId, String productName,
			String price, String paymentMethod, int deliveryNumber, String address, String phone, String receiver ,String status) {

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
		this.status=status;
	}
	
	
	
	
	
	
}
