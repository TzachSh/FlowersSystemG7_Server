package Commons;

import java.io.Serializable;

import Orders.Order;
import Products.Product;

public class ProductInOrder implements Serializable {
	
	private Order order;
	private Product product;
	private int quantity;
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
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
	
	public ProductInOrder(Order order, Product product, int quantity) {
		this.order = order;
		this.product = product;
		this.quantity = quantity;
	}
	
}
