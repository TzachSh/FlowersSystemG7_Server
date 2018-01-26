package Customers;

import java.io.Serializable;
import java.sql.Timestamp;

import Commons.IRefundAble;

/**
 * Entity
 * Contains complain details
 *
 */
public class Complain implements IRefundAble, Serializable {
	

	/**
	 *  id complain
	 */
	private int id;
	/**
	 * creation date of a complain
	 */
	private Timestamp creationDate;
	/**
	 * complain subject
	 */
	private String title;
	/**
	 * complain message
	 */
	private String details;
	/**
	 * id of the customer
	 */
	private int customerId;
	/**
	 * customer service id who created complain
	 */
	private int customerServiceId;
	/**
	 * branch number
	 */
	private int branchId;
	/**
	 * if complain is active
	 */
	private boolean isActive;
	/**
	 * 
	 * @return branch id
	 */
	public int getBranchId() {
		return branchId;
	}
	/*** 
	 * @param branchId to set
	 */
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	/**
	 * 
	 * @return complain id
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id- set complain id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return creation date
	 */
	public Timestamp getCreationDate() {
		return creationDate;
	}

	/**
	 * 
	 * @param creationDate - set a creation date
	 */
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * 
	 * @return complain title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 
	 * @param title - set a complain title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 
	 * @return complain details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * 
	 * @param details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * 
	 * @return customer id which complains
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * 
	 * @param customerId to set
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	/**
	 * 
	 * @return complain's creator id
	 */
	public int getCustomerServiceId() {
		return customerServiceId;
	}

	/**
	 * 
	 * @param customerServiceId which created the complain
	 */
	public void setCustomerServiceId(int customerServiceId) {
		this.customerServiceId = customerServiceId;
	}

	/**
	 * 
	 * Constructor which initialize the following fields:
	 * 
	 * @param creationDate
	 * @param title
	 * @param details
	 * @param customerId
	 * @param customerServiceId
	 * @param isActive
	 * 
	 * for Client side use
	 */
	public Complain(Timestamp creationDate, String title, String details, int customerId,
			int customerServiceId,boolean isActive,int branchId) {
		super();
		this.creationDate = creationDate;
		this.title = title;
		this.details = details;
		this.customerId = customerId;
		this.customerServiceId = customerServiceId;
		this.isActive = isActive;
		this.branchId = branchId;
	}

	/**
	 * Constructor which initialize the following fields:
	 * 
	 * @param id
	 * @param creationDate
	 * @param title
	 * @param details
	 * @param customerId
	 * @param customerServiceId
	 * @param isActive
	 * 
	 * for server side use
	 */
	public Complain(int id, Timestamp creationDate, String title, String details, int customerId,
			int customerServiceId,boolean isActive,int branchId) {
		super();
		this.id = id;
		this.creationDate = creationDate;
		this.title = title;
		this.details = details;
		this.customerId = customerId;
		this.customerServiceId = customerServiceId;
		this.isActive = isActive;
		this.branchId = branchId;
	}

	/**
	 * 
	 * @return complains status
	 */
	public boolean isActive() {
		return isActive;
	}
	/**
	 * 
	 * @param isActive set the complain status
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
