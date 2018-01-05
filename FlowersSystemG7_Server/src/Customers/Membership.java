package Customers;

import java.io.Serializable;

public class Membership implements Serializable {
	private int num;
	private MembershipType membershipType;
	private double discount;
	
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public MembershipType getMembershipType() {
		return membershipType;
	}
	public void setMembershipType(MembershipType membershipType) {
		this.membershipType = membershipType;
	}
	public Membership(int num, MembershipType membershipType,double discount) {
		super();
		this.num = num;
		this.membershipType = membershipType;
		this.discount=discount;
	}
}
