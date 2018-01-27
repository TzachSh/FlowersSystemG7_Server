package Products;

import java.io.Serializable;

/**
 * Entity
 *flower in product and its quantity
 */
public class FlowerInProduct implements Serializable {
	
	/**
	 * flower code
	 */
	private int flowerId;
	/**
	 * product code
	 */
	private int productId;
	/**
	 * flower object
	 */
	private Flower flower;
	/**
	 * amount of flowers in product
	 */
	private int quantity;
	
	/**
	 * @return flower code
	 */
	public int getFlowerId() {
		return flowerId;
	}
	/**
	 * @param flowerId flower id
	 */
	public void setFlowerId(int flowerId) {
		this.flowerId = flowerId;
	}
	/**
	 * @return product id
	 */
	public int getProductId() {
		return productId;
	}
	/**
	 * @param productId to set
	 */
	public void setProduct(int productId) {
		this.productId = productId;
	}
	/**
	 * @return get amount of flowers in the product
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity update amount of flowers in the product
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * @param flowerId flower code
	 * @param productId product code
	 * @param quantity flowers amount
	 */
	public FlowerInProduct(int flowerId, int productId, int quantity) {
		this.flowerId = flowerId;
		this.productId = productId;
		this.quantity = quantity;
	}
	
	/**
	 * @param flowerId flower code
	 * @param quantity flowers amount
	 */
	public FlowerInProduct(int flowerId, int quantity) {
		this(flowerId, 0, quantity);
	}
	/**
	 * @return flower object
	 */
	public Flower getFlower() {
		return flower;
	}
	/**
	 * @param flower flower object
	 */
	public void setFlower(Flower flower) {
		this.flower = flower;
	}
}
