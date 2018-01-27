package Products;

import java.io.Serializable;
import java.util.ArrayList;

import Commons.ProductInOrder;

/**
 * Entity
 * custom product created by customers
 */
public class CustomProduct extends Product implements Serializable {
	/**
	 * blessing message
	 */
	private String blessing;

	/**
	 * @return blessing message
	 */
	public String getBlessing() {
		return blessing;
	}

	/**
	 * @param blessing blessing message
	 */
	public void setBlessing(String blessing) {
		this.blessing = blessing;
	}

	/**
	 * @param id generic product id
	 * @param productTypeId type code
	 * @param price price of the product
	 * @param flowerInProductList all flowers in the product
	 * @param productInOrderList all order line with product
	 * @param blessing blessing message
	 */
	public CustomProduct(int id, int productTypeId, double price, ArrayList<FlowerInProduct> flowerInProductList,
			ArrayList<ProductInOrder> productInOrderList, String blessing) {
		super(id, productTypeId, price, flowerInProductList, productInOrderList);
		this.blessing = blessing;
	}
	/**
	 * @param price price of the product
	 * @param typeId type code
	 * @param pId generic product id
	 */
	public CustomProduct(double price,int typeId,int pId) {
		super(price,typeId,pId);
	}
}
