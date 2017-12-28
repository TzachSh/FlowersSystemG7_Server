package Products;

import java.io.Serializable;
import java.util.ArrayList;

import Commons.ProductInOrder;

public class CatalogProduct extends Product implements Serializable {

	private String name;
	private int saleDiscountPercent;
	private String imgUrl;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSaleDiscountPercent() {
		return saleDiscountPercent;
	}
	public void setSaleDiscountPercent(int saleDiscountPercent) {
		this.saleDiscountPercent = saleDiscountPercent;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public CatalogProduct(int id, int productTypeId, double price, ArrayList<FlowerInProduct> flowerInProductList,
			ArrayList<ProductInOrder> productInOrderList, String name, int saleDiscountPercent, String imgUrl) {
		
		super(id, productTypeId, price, flowerInProductList, productInOrderList);
		this.name = name;
		this.saleDiscountPercent = saleDiscountPercent;
		this.imgUrl = imgUrl;
	}
}
