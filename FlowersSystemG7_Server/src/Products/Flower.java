package Products;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Flower {
	
	private int id;
	private double price;
	private Color color;
	private ArrayList<FlowerInProduct> flowerInProduct;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public ArrayList<FlowerInProduct> getFlowerInProduct() {
		return flowerInProduct;
	}
	public void setFlowerInProduct(ArrayList<FlowerInProduct> flowerInProduct) {
		this.flowerInProduct = flowerInProduct;
	}
	
	public Flower(int id, double price, Color color, ArrayList<FlowerInProduct> flowerInProduct) {

		this.id = id;
		this.price = price;
		this.color = color;
		this.flowerInProduct = flowerInProduct;
	}
	

	
	
}
