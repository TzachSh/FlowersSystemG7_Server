package Customers;

import java.io.Serializable;

public class Reply implements Serializable{

	private int id;
	private int complainId;
	private int replyment;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getComplainId() {
		return complainId;
	}
	public void setComplainId(int complainId) {
		this.complainId = complainId;
	}
	public int getReplyment() {
		return replyment;
	}
	public void setReplyment(int replyment) {
		this.replyment = replyment;
	}
	
	public Reply(int id, int complainId, int replyment) {
		super();
		this.id = id;
		this.complainId = complainId;
		this.replyment = replyment;
	}
	
}
