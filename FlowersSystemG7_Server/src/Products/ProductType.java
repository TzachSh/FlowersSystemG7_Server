package Products;

public class ProductType  {
	private int id;
	private String description;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public ProductType(int id, String description) {
	
		this.id = id;
		this.description = description;
	}
}
