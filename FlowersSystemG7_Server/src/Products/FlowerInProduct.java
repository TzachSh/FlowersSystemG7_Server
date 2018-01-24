package Products;

import java.io.Serializable;

public class FlowerInProduct implements Serializable {
	
	private int flowerId;
	private int productId;
	private Flower flower;
	private int quantity;
	
	public int getFlowerId() {
		return flowerId;
	}
	public void setFlowerId(int flowerId) {
		this.flowerId = flowerId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProduct(int productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public FlowerInProduct(int flowerId, int productId, int quantity) {
		this.flowerId = flowerId;
		this.productId = productId;
		this.quantity = quantity;
	}
	
	public FlowerInProduct(int flowerId, int quantity) {
		this(flowerId, 0, quantity);
	}
	public Flower getFlower() {
		return flower;
	}
	public void setFlower(Flower flower) {
		this.flower = flower;
	}
}
