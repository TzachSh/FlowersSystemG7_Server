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
	/** return the status of the delivery*/
	public String getStatus() {
		return status;
	}
	/** setting the delivery status*/
	public void setStatus(String status) {
		this.status = status;
	}
	/** returns the category of the product*/
	public String getProductCategory() {
		return productCategory;
	}
	/**setting category for product */
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	/**returns order id*/
	public int getOrderId() {
		return orderId;
	}
	/**setting order id */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	/** returns creation date*/
	public String getCreationDate() {
		return creationDate;
	}
	/** setting creation date */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	/**returns product id*/
	public int getProductId() {
		return productId;
	}
	/**setting product id */
	public void setProductId(int productId) {
		this.productId = productId;
	}
	/** returns product name*/
	public String getProductName() {
		return productName;
	}
	/**setting product name */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**getting price*/
	public String getPrice() {
		return price;
	}
	/**setting price*/
	public void setPrice(String price) {
		this.price = price;
	}
	/** returns payment method*/
	public String getPaymentMethod() {
		return paymentMethod;
	}
	/** setting payment method*/
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	/** returns the delivery number */
	public int getDeliveryNumber() {
		return deliveryNumber;
	}
	/** setting delivery number*/
	public void setDeliveryNumber(int deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}
	/** returns the address*/
	public String getAddress() {
		return address;
	}
	/**setting the address */
	public void setAddress(String address) {
		this.address = address;
	}
	/** returns phone number */
	public String getPhone() {
		return phone;
	}
	/** setting phone  number*/
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/** returns the receiver*/
	public String getReceiver() {
		return receiver;
	}
	/**setting receiver*/
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
