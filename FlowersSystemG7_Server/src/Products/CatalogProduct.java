package Products;

import java.io.Serializable;
import java.util.ArrayList;

import Commons.ProductInOrder;

/**
 * Entity
 * Catalog product details
 */
public class CatalogProduct extends Product implements Serializable {

	/**
	 * catalog product id
	 */
	private int catPId;
	/**
	 * product name
	 */
	private String name;
	/**
	 *  image path
	 */
	private String imgUrl;
	/**
	 * @return product name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name product name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return image path
	 */
	public String getImgUrl() {
		return imgUrl;
	}
	/**
	 * @param imgUrl image path
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/**
	 * @param price product price
	 * @param typeId type code of the product
	 * @param prodName product name
	 * @param pId product code generic
	 */
	public CatalogProduct(double price,int typeId,String prodName,int pId)
	{
		super(price,typeId,pId);
		this.name=prodName;
	}
	/**
	 * @param id product code generic
	 * @param catPid catalog product code
	 * @param productTypeId type code of the product
	 * @param price product price
	 * @param flowerInProductList list of flowers in product
	 * @param productInOrderList list of orders with this product
	 * @param name product name
	 * @param imgUrl image path
	 */
	public CatalogProduct(int id, int catPid, int productTypeId, double price, ArrayList<FlowerInProduct> flowerInProductList,
			ArrayList<ProductInOrder> productInOrderList, String name, String imgUrl) {
		
		super(id, productTypeId, price, flowerInProductList, productInOrderList);
		this.catPId = catPid;
		this.name = name;
		this.imgUrl = imgUrl;
	}

	/**
	 * Constructor
	 */
	public CatalogProduct()
	{
		this.imgUrl = "";
		this.name = "";
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * @return catalog product code
	 */
	public int getCatalogProductId() {
		return catPId;
	}
	/**
	 * @param catPId catalog product id
	 */
	public void setCatalogProductId(int catPId) {
		this.catPId = catPId;
	}
}
