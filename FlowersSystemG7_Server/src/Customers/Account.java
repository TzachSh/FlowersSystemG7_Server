package Customers;

import java.io.Serializable;
import java.util.ArrayList;

import Commons.Refund;

public class Account implements Serializable {
	private int num;
	private int customerId;
	private double balance;
	private int mId;
	private int branchId;
	private AccountStatus accountStatus;
	private String creditCard;
	private Membership memberShip;
	/**
	 * 
	 * @return membership id
	 */
	public int getmId() {
		return mId;
	}
	/**
	 * 
	 * @param mId setting the membership id 
	 */
	public void setmId(int mId) {
		this.mId = mId;
	}
	/**
	 * 
	 * @return membership object of this account
	 */
	public Membership getMemberShip() {
		return memberShip;
	}
	/**
	 * 
	 * @param memberShip setting membership object
	 */
	public void setMemberShip(Membership memberShip) {
		this.memberShip = memberShip;
	}
	/**
	 * 
	 * @return branch id 
	 */
	public int getBranchId() {
		return branchId;
	}
	/**
	 * 
	 * @param branchId setting branch id
	 */
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	/**
	 * 
	 * @return the account balance
	 */
	public double getBalance() {
		return balance;
	}
	/**
	 * 
	 * @param balance setting the balance
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}
	/**
	 * 
	 * @return the account number 
	 */
	public int getNum() {
		return num;
	}
	/**
	 * 
	 * @param num setting the account number
	 */
	public void setNum(int num) {
		this.num = num;
	}
	/**
	 * 
	 * @return the customer id
	 */
	public int getCustomerId() {
		return customerId;
	}
	/**
	 * 
	 * @param customerId setting customer id 
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	/**
	 * 
	 * @return account status object
	 */
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}
	/**
	 * 
	 * @param accountStatus setting account status object
	 */
	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}
	/**
	 * 
	 * @return credit card
	 */
	public String getCreditCard() {
		return creditCard;
	}
	/**
	 * 
	 * @param creditCard setting credit card
	 */
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	/**
	 * Account Constructor
	 * @param branchId branch number
	 * @param customerId customer id
	 * @param mId membership number
	 * @param balance balance 
	 * @param accountStatus the account status Blocked , active , closed
	 * @param creditCard credit card
	 */
	public Account(int branchId, int customerId, int mId,double balance, AccountStatus accountStatus, String creditCard) {
		super();
		this.customerId = customerId;
		this.mId = mId;
		this.balance = balance;
		this.branchId = branchId;
		this.accountStatus = accountStatus;
		this.creditCard = creditCard;
	}
	/**
	 * Account Constructor
	 * @param num account number
	 * @param branchId branch number
	 * @param customerId customer id
	 * @param mId membership number
	 * @param balance balance 
	 * @param accountStatus the account status Blocked , active , closed
	 * @param creditCard credit card
	 */
	public Account(int num, int customerId,int mId, double balance, int branchId, AccountStatus accountStatus,
			String creditCard) {
		super();
		this.num = num;
		this.customerId = customerId;
		this.mId=mId;
		this.balance = balance;
		this.branchId = branchId;
		this.accountStatus = accountStatus;
		this.creditCard = creditCard;
	}
	/**
	 * 
	 * @param branchId branch number
	 * @param customerId customer id
	 * @param balance balance 
	 * @param accountStatus the account status Blocked , active , closed
	 * @param creditCard credit card
	 */
	public Account(int branchId, int customerId,double balance, AccountStatus accountStatus, String creditCard) {
		super();
		this.customerId = customerId;
		this.balance = balance;
		this.branchId = branchId;
		this.accountStatus = accountStatus;
		this.creditCard = creditCard;
	}

}

