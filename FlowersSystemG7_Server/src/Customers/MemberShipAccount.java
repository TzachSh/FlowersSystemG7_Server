package Customers;

import java.io.Serializable;
import java.sql.Date;

public class MemberShipAccount implements Serializable{
	
	public MemberShipAccount(int acNum, int mId, Date creationDate) {
		super();
		this.acNum = acNum;
		this.mId = mId;
		this.creationDate = creationDate;
	}
	public int getAcNum() {
		return acNum;
	}
	public void setAcNum(int acNum) {
		this.acNum = acNum;
	}
	public int getmId() {
		return mId;
	}
	public void setmId(int mId) {
		this.mId = mId;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	private int acNum;
	private int mId;
	private Date creationDate;
	
	

}
