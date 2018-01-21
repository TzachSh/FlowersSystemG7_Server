package Products;

import java.io.Serializable;
import java.util.ArrayList;

import Commons.ProductInOrder;

public class CustomProduct extends Product implements Serializable {
	private String blessing;

	public String getBlessing() {
		return blessing;
	}

	public void setBlessing(String blessing) {
		this.blessing = blessing;
	}

	public CustomProduct(int id, int productTypeId, double price, ArrayList<FlowerInProduct> flowerInProductList,
			ArrayList<ProductInOrder> productInOrderList, String blessing) {
		super(id, productTypeId, price, flowerInProductList, productInOrderList);
		this.blessing = blessing;
	}
	public CustomProduct(double price,int typeId,int pId) {
		super(price,typeId,pId);
	}
}
