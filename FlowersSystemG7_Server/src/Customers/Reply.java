package Customers;

import java.io.Serializable;

public class Reply implements Serializable{

	private int id;
	private int complainId;
	private String replyment;
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
	public String getReplyment() {
		return replyment;
	}
	public void setReplyment(String replyment) {
		this.replyment = replyment;
	}
	
	public Reply(int id, int complainId, String replyment) {
		super();
		this.id = id;
		this.complainId = complainId;
		this.replyment = replyment;
	}

	public Reply(int complainId, String replyment) {
		super();
		this.complainId = complainId;
		this.replyment = replyment;
	}

}
