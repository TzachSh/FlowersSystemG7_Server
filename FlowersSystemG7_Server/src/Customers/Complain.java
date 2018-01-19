package Customers;

import java.io.Serializable;
import java.sql.Date;

import Branches.CustomerService;
import Commons.IRefundAble;
import Commons.Refund;

/**
 * 
 * Complain entity class
 *
 */
public class Complain implements IRefundAble, Serializable {
	
	/***
	 * Instance members
	 */
	private int id;
	private Date creationDate;
	private String title;
	private String details;
	private int customerId;
	private int customerServiceId;
	private int branchId;
	private boolean isActive;
	/***
	 * 
	 * @return branch id
	 */
	public int getBranchId() {
		return branchId;
	}

	/***
	 * 
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
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * 
	 * @param creationDate - set a creation date
	 */
	public void setCreationDate(Date creationDate) {
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
	public Complain(Date creationDate, String title, String details, int customerId,
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
	public Complain(int id, Date creationDate, String title, String details, int customerId,
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
