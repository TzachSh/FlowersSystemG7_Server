package Customers;

import java.io.Serializable;

public class Membership implements Serializable {
	private int num;
	private MembershipType membershipType;
	private double discount;
	/**
	 * 
	 * @return the membership's discount
	 */
	public double getDiscount() {
		return discount;
	}
	/**
	 * 
	 * @param discount setting the discount
	 */
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	/**
	 * 
	 * @return getting the membship number
	 */
	public int getNum() {
		return num;
	}
	/**
	 * 
	 * @param num setting the membership number
	 */
	public void setNum(int num) {
		this.num = num;
	}
	/**
	 * 
	 * @return membership type
	 */
	public MembershipType getMembershipType() {
		return membershipType;
	}
	/**
	 * 
	 * @param membershipType setting membership type
	 */
	public void setMembershipType(MembershipType membershipType) {
		this.membershipType = membershipType;
	}
	/**
	 * membership constructor
	 * @param num membership number
	 * @param membershipType membership type
	 * @param discount the membership discount
	 */
	public Membership(int num, MembershipType membershipType,double discount) {
		super();
		this.num = num;
		this.membershipType = membershipType;
		this.discount=discount;
	}
}
