package Customers;

import java.io.Serializable;

/**
 * 
 * Reply entity class
 *
 */
public class Reply implements Serializable{

	//Instance members
	private int id;
	private int complainId;
	private String replyment;
	
	/**
	 * 
	 * @return reply id
	 */
	public int getId() {
		return id;
	}
	/**
	 * 
	 * @param id - to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 
	 * @return complains id of the reply
	 */
	public int getComplainId() {
		return complainId;
	}
	/**
	 * 
	 * @param complainId - to set
	 */
	public void setComplainId(int complainId) {
		this.complainId = complainId;
	}
	/**
	 * 
	 * @return reply detials
	 */
	public String getReplyment() {
		return replyment;
	}
	/**
	 * 
	 * @param replyment to set
	 */
	public void setReplyment(String replyment) {
		this.replyment = replyment;
	}
	
	/**
	 * Constructor to initialize the following fields:
	 * @param id
	 * @param complainId
	 * @param replyment
	 * For server using
	 */
	public Reply(int id, int complainId, String replyment) {
		super();
		this.id = id;
		this.complainId = complainId;
		this.replyment = replyment;
	}
	/**
	 * Constructor to initialize the following fields:
	 * @param complainId
	 * @param replyment
	 * For client using
	 */
	public Reply(int complainId, String replyment) {
		super();
		this.complainId = complainId;
		this.replyment = replyment;
	}

}
