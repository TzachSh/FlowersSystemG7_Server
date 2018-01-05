package Customers;
import Customers.AccountStatus;
import java.io.Serializable;

public class Account implements Serializable {
	private int num;
	private int customerId;
	private int balance;
	private AccountStatus accountStatus;
	private String creditCard;
	
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	public Account(int num, int customerId,int balance, AccountStatus accountStatus, String creditCard) {
		super();
		this.num = num;
		this.customerId = customerId;
		this.balance=balance;
		this.accountStatus = accountStatus;
		this.creditCard = creditCard;
	}
	public Account( int customerId,int balance, AccountStatus accountStatus, String creditCard) {
		super();
		this.customerId = customerId;
		this.balance=balance;
		this.accountStatus = accountStatus;
		this.creditCard = creditCard;
	}
}
