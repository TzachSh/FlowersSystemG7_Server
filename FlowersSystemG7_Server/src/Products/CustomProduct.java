package Products;

import java.util.ArrayList;

import Commons.ProductInOrder;

public class CustomProduct extends Product{
	private String blessing;

	public String getBlessing() {
		return blessing;
	}

	public void setBlessing(String blessing) {
		this.blessing = blessing;
	}

	public CustomProduct(int id, ProductType productType, double price, ArrayList<FlowerInProduct> flowerInProductList,
			ArrayList<ProductInOrder> productInOrderList, String blessing) {
		super(id, productType, price, flowerInProductList, productInOrderList);
		this.blessing = blessing;
	}

}
