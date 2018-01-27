package Products;

import java.io.Serializable;
/**
 * Entity
 * Flower colors
 *
 */
public class ColorProduct implements Serializable {
	
	/**
	 * code color
	 */
	private int colId;
	/**
	 * color name
	 */
	private String colorName;
	/**
	 * @return code color
	 */
	public int getColId() {
		return colId;
	}
	/**
	 * @return name color
	 */
	public String getColorName() {
		return colorName;
	}
	/**
	 * @param colId set color code
	 * @param colorName set name color
	 */
	public ColorProduct(int colId, String colorName) {
		super();
		this.colId = colId;
		this.colorName = colorName;
	}
	@Override
	public String toString() {
		return colorName;
	}
	
}
