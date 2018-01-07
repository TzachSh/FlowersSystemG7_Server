package Products;

import java.io.Serializable;

public class ColorProduct implements Serializable {
	private int colId;
	private String colorName;
	public int getColId() {
		return colId;
	}
	public String getColorName() {
		return colorName;
	}
	public ColorProduct(int colId, String colorName) {
		super();
		this.colId = colId;
		this.colorName = colorName;
	}
	
}
