package Customers;

import java.io.Serializable;
import java.sql.Date;

/**
 * 
 * Entity 
 * Membership of account contain membership code account of the customer
 * in the branch and creation date of the membership
 *
 */
public class MemberShipAccount implements Serializable{
	/**customer account number*/
	private int acNum;
	/**membership code*/
	private int mId;
	/**creation of the membership*/
	private Date creationDate;
	/**
	 * membership account constructor
	 * @param acNum account number
	 * @param mId membership id
	 * @param creationDate creation date
	 */
	public MemberShipAccount(int acNum, int mId, Date creationDate) {
		super();
		this.acNum = acNum;
		this.mId = mId;
		this.creationDate = creationDate;
	}
	/**
	 * membership account constructor
	 * @param acNum account number
	 */
	public MemberShipAccount(int acNum) {
		super();
		this.acNum = acNum;

	}
	/**
	 * 
	 * @return account number
	 */
	public int getAcNum() {
		return acNum;
	}
	/**
	 * 
	 * @param acNum setting account number
	 */
	public void setAcNum(int acNum) {
		this.acNum = acNum;
	}
	/**
	 * 
	 * @return membership number
	 */
	public int getmId() {
		return mId;
	}
	/**
	 * 
	 * @param mId setting membership number
	 */
	public void setmId(int mId) {
		this.mId = mId;
	}
	/**
	 * 
	 * @return membership's creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * 
	 * @param creationDate setting membership's creation date
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	

}
