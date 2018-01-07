package Products;

import java.io.Serializable;

public class CatalogBranch implements Serializable {
	private int catPId;
	private int branchId;
	private double discount;
	public CatalogBranch(int catPId, int branchId, double discount) {
		super();
		this.catPId = catPId;
		this.branchId = branchId;
		this.discount = discount;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getCatPId() {
		return catPId;
	}
	public int getBranchId() {
		return branchId;
	}
	
}
