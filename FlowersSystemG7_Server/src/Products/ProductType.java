package Products;

import java.io.Serializable;

/**
 * Entity
 * This Class uses for hands the Product Type
 */
public class ProductType implements Serializable
{
	/**
	 * product type code
	 */
	private int id;
	/**
	 * description type
	 */
	private String description;
	
	/**
	 * @return type code
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id type code
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 
	 * @param id type id
	 * @param description description
	 */
	public ProductType(int id, String description) {
	
		this.id = id;
		this.description = description;
	}
	@Override
	public String toString() {
		return description;
	}
	
	
}
