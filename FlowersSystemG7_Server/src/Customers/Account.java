package Customers;

public class Account {
	private int num;
	private Customer customer;
	private AccountStatus accountStatus;
	private String creditCard;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
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
	public Account(int num, Customer customer, AccountStatus accountStatus, String creditCard) {
		super();
		this.num = num;
		this.customer = customer;
		this.accountStatus = accountStatus;
		this.creditCard = creditCard;
	}
	
}
