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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CatalogProduct other = (CatalogProduct) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public int getCatPId() {
		return catPId;
	}
	public void setCatPId(int catPId) {
		this.catPId = catPId;
	}
}
