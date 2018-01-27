package Products;

import java.io.Serializable;

/**
 *Entity
 *Catalog In branch with special discount
 */
/**
 * @author VladimirB
 *
 */
/**
 * @author VladimirB
 *
 */
/**
 * @author VladimirB
 *
 */
/**
 * @author VladimirB
 *
 */
public class CatalogInBranch implements Serializable
{
	/**
	 * branch number
	 */
	private int branchId;
	/**
	 * catalog product code
	 */
	private int catPid;
	/**
	 * discount of the catalog product
	 */
	private int discount;
	
	/**
	 * @param branchId branch number
	 * @param catPid catalog product id
	 * @param discount discount sale
	 */
	public CatalogInBranch(int branchId, int catPid, int discount) {
		super();
		this.branchId = branchId;
		this.catPid = catPid;
		this.discount = discount;
	}
	
	/**
	 * @return branch number
	 */
	public int getBranchId() {
		return branchId;
	}
	
	/**
	 * @return catalog product code
	 */
	public int getCatalogProductId() {
		return catPid;
	}
	
	/**
	 * @return discount sale
	 */
	public int getDiscount() {
		return discount;
	}
	/**
	 * @param branchId branch number
	 */
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	
	/**
	 * @param catPid catalog product id
	 */
	public void setCatalogProductId(int catPid) {
		this.catPid = catPid;
	}

	/**
	 * @param discount discount sale
	 */
	public void setDiscount(int discount) {
		this.discount = discount;
	}
}
