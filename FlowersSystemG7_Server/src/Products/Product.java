package Products;

import java.io.Serializable;
import java.util.ArrayList;

import Commons.ProductInOrder;

public class Product implements Serializable {
	private int id;
	private int productTypeId;
	private double price;
	private ArrayList<FlowerInProduct> flowerInProductList;
	private ArrayList<ProductInOrder> productInOrderList;
	public int getId() {
		return id;
	}
	public Product(int id, int productTypeId, double price) {
		super();
		this.id = id;
		this.productTypeId = productTypeId;
		this.price = price;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(int productTypeId) {
		this.productTypeId = productTypeId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public ArrayList<FlowerInProduct> getFlowerInProductList() {
		return flowerInProductList;
	}
	public void setFlowerInProductList(ArrayList<FlowerInProduct> flowerInProductList) {
		this.flowerInProductList = flowerInProductList;
	}
	public ArrayList<ProductInOrder> getProductInOrderList() {
		return productInOrderList;
	}
	public void setProductInOrderList(ArrayList<ProductInOrder> productInOrderList) {
		this.productInOrderList = productInOrderList;
	}
	public Product(int id, int productTypeId, double price, ArrayList<FlowerInProduct> flowerInProductList,
			ArrayList<ProductInOrder> productInOrderList) {

		this.id = id;
		this.productTypeId = productTypeId;
		this.price = price;
		this.flowerInProductList = flowerInProductList;
		this.productInOrderList = productInOrderList;
	}
	
	public Product()
	{
		
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
