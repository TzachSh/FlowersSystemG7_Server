package Products;

import java.io.Serializable;
import java.util.ArrayList;

import Commons.ProductInOrder;

/**
 * Entity
 * Product contain list of flowers,
 *  product in order list,type code of product and the original price
 */
public class Product implements Serializable {
	/**
	 * product code
	 */
	private int id;
	/**
	 * type code of the product
	 */
	private int productTypeId;
	/**
	 *  original cost
	 */
	private double price;
	/**
	 *  contain all flowers from product and it quantity
	 */
	private ArrayList<FlowerInProduct> flowerInProductList;
	/**
	 *  all orders with product and quantity of ordered product
	 */
	private ArrayList<ProductInOrder> productInOrderList;
	/**
	 * @return product id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id product id
	 * @param productTypeId product type code
	 * @param price product original cost
	 */
	public Product(int id, int productTypeId, double price) {
		super();
		this.id = id;
		this.productTypeId = productTypeId;
		this.price = price;
	}
	/**
	 * @param id product id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return product type code
	 */
	public int getProductTypeId() {
		return productTypeId;
	}
	/**
	 * @param productTypeId product type code
	 */
	public void setProductTypeId(int productTypeId) {
		this.productTypeId = productTypeId;
	}
	/**
	 * @return original cost
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price original cost
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * @return flowers in product
	 */
	public ArrayList<FlowerInProduct> getFlowerInProductList() {
		return flowerInProductList;
	}
	/**
	 * @param flowerInProductList flowers in product
	 */
	public void setFlowerInProductList(ArrayList<FlowerInProduct> flowerInProductList) {
		this.flowerInProductList = flowerInProductList;
	}
	/**
	 * @return products in order
	 */
	public ArrayList<ProductInOrder> getProductInOrderList() {
		return productInOrderList;
	}
	/**
	 * @param productInOrderList products in order
	 */
	public void setProductInOrderList(ArrayList<ProductInOrder> productInOrderList) {
		this.productInOrderList = productInOrderList;
	}
	/**
	 * @param id product id
	 * @param productTypeId product type code
	 * @param price original cost
	 * @param flowerInProductList flower in product
	 * @param productInOrderList products in order
	 */
	public Product(int id, int productTypeId, double price, ArrayList<FlowerInProduct> flowerInProductList,
			ArrayList<ProductInOrder> productInOrderList) {

		this.id = id;
		this.productTypeId = productTypeId;
		this.price = price;
		this.flowerInProductList = flowerInProductList;
		this.productInOrderList = productInOrderList;
	}
	
	/**
	 * Constructor
	 */
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
	
	/**
	 * @param price original cost
	 * @param typeId product type code
	 * @param pId product code
	 */
	public Product(double price,int typeId,int pId)
	{
		this.id=pId;
		this.price=price;
		this.productTypeId=typeId;
	}
}
