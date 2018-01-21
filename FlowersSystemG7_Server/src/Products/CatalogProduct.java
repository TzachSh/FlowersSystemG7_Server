package Products;

import java.io.Serializable;
import java.util.ArrayList;

import Commons.ProductInOrder;

public class CatalogProduct extends Product implements Serializable {

	private int catPId;
	private String name;
	private String imgUrl;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public CatalogProduct(double price,int typeId,String prodName,int pId)
	{
		super(price,typeId,pId);
		this.name=prodName;
	}
	public CatalogProduct(int id, int catPid, int productTypeId, double price, ArrayList<FlowerInProduct> flowerInProductList,
			ArrayList<ProductInOrder> productInOrderList, String name, String imgUrl) {
		
		super(id, productTypeId, price, flowerInProductList, productInOrderList);
		this.catPId = catPid;
		this.name = name;
		this.imgUrl = imgUrl;
	}

	public CatalogProduct()
	{
		this.imgUrl = "";
		this.name = "";
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public int getCatalogProductId() {
		return catPId;
	}
	public void setCatalogProductId(int catPId) {
		this.catPId = catPId;
	}
}
