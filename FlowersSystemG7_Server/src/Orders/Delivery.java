package Orders;

import java.io.Serializable;

/***
 * 
 * Entity class to define a delivery
 *
 */
public class Delivery implements Serializable {
	
	/**delivery code*/
	private int id;
	/**address to delivery*/
	private String address;
	/**phone of the receiver*/
	private String phone;
	/**receiver's name*/
	private String receiver;
	/**order number to delivery*/
	private int orderId;
	/***
	 * 
	 * @return delivery id
	 */
	public int getId() {
		return id;
	}
	/***
	 * 
	 * @param id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/***
	 * 
	 * @return address
	 */
	public String getAddress() {
		return address;
	}
	/***
	 * 
	 * @param address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/***
	 * 
	 * @return phone
	 */
	public String getPhone() {
		return phone;
	}
	/***
	 * 
	 * @param phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/***
	 * 
	 * @return receiver name
	 */
	public String getReceiver() {
		return receiver;
	}
	/***
	 * 
	 * @param receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	/***
	 * 
	 * @return order it
	 */
	public int getOrderId() {
		return orderId;
	}
	/***
	 * 
	 * @param orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	/***
	 * Constructor for server side
	 * @param id delivery code
	 * @param address receiver's address
	 * @param phone receiver's phone
	 * @param receiver receiver's name
	 * @param orderId order number
	 */
	public Delivery(int id, String address, String phone, String receiver, int orderId) {
		super();
		this.id = id;
		this.address = address;
		this.phone = phone;
		this.receiver = receiver;
		this.orderId = orderId;
	}
	/***
	 * Constructor for client side
	 * @param address receiver's address
	 * @param phone receiver's phone
	 * @param receiver receiver's name
	 */
	public Delivery(String address, String phone, String receiver) {
		super();
		this.address = address;
		this.phone = phone;
		this.receiver = receiver;
	}
}
