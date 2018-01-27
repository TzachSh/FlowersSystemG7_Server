package Products;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Entity
 * Flower with details as price, color and name
 */
public class Flower implements Serializable {
	
	/**
	 * flower code
	 */
	private int id;
	/**
	 * flower unique name
	 */
	private String name;
	/**
	 * flower cost
	 */
	private double price;
	/**
	 * dominant color
	 */
	private int color;
	/**
	 * flowers in product and it's quantity
	 */
	private ArrayList<FlowerInProduct> flowerInProduct;
	

	/**
	 * @return flower code
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id flower code
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return flower name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name flower name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return flower price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price flower price
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * @return flower dominant color
	 */
	public int getColor() {
		return color;
	}
	/**
	 * @param color dominant color
	 */
	public void setColor(int color) {
		this.color = color;
	}
	/**
	 * @return all flower in product with quantity
	 */
	public ArrayList<FlowerInProduct> getFlowerInProduct() {
		return flowerInProduct;
	}
	/**
	 * @param flowerInProduct all flower in product with quantity
	 */
	public void setFlowerInProduct(ArrayList<FlowerInProduct> flowerInProduct) {
		this.flowerInProduct = flowerInProduct;
	}
	
	/**
	 * @param id flower id
	 * @param name flower name
	 * @param price flower price
	 * @param color flower dominant color
	 */
	public Flower(int id, String name, double price, int color) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.color = color;
		this.flowerInProduct = null;
	}
	
	/**
	 * @param name flower name
	 * @param price flower price
	 * @param color flower dominant color
	 */
	public Flower(String name, double price, int color) {
		this(0, name, price, color);
	}
	/**
	 * @param fId flower id
	 * @param name flower name
	 */
	public Flower(int fId,String name ) {
		this.id = fId;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
