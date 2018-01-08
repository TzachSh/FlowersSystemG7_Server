package Products;

import java.io.Serializable;

public class CatalogInBranch implements Serializable
{
	private int branchId;
	private int catPid;
	private int discount;
	
	public CatalogInBranch(int branchId, int catPid, int discount) {
		super();
		this.branchId = branchId;
		this.catPid = catPid;
		this.discount = discount;
	}

	public int getBranchId() {
		return branchId;
	}

	public int getCatalogProductId() {
		return catPid;
	}

	public int getDiscount() {
		return discount;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public void setCatalogProductId(int catPid) {
		this.catPid = catPid;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}
}
