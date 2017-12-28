package Products;

import java.io.Serializable;

public class FlowerInProduct implements Serializable {
	
	private Flower flower;
	private Product product;
	private int quantity;
	
	public Flower getFlower() {
		return flower;
	}
	public void setFlower(Flower flower) {
		this.flower = flower;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public FlowerInProduct(Flower flower, Product product, int quantity) {
		super();
		this.flower = flower;
		this.product = product;
		this.quantity = quantity;
	}
}
