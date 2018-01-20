package Customers;

import java.io.Serializable;
import java.sql.Date;

public class MemberShipAccount implements Serializable{
	private int acNum;
	private int mId;
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
