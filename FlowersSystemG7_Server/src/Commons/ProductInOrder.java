package Commons;

import java.io.Serializable;

/**
 * Entity
 * Storage products in order in it's quantity
 */
public class ProductInOrder implements Serializable {
	
	/**
	 * order number
	 */
	private int orderId;
	/**
	 * product number
	 */
	private int productId;
	/**
	 * quantity in order line
	 */
	private int quantity;
	/**
	 * 
	 * @return order number
	 */
	public int getOrderId() {
		return orderId;
	}
	/**
	 * 
	 * @param orderId order number number
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	/**
	 * 
	 * @return product number
	 */
	public int getProductId() {
		return productId;
	}
	/**
	 * 
	 * @param productId product number
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}
	/**
	 * 
	 * @return amount in the order
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * 
	 * @param quantity in order
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * Constructor
	 * @param orderId order number
	 * @param productId product number
	 * @param quantity amount in order
	 */
	public ProductInOrder(int orderId, int productId, int quantity) {
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
	}
	
}
