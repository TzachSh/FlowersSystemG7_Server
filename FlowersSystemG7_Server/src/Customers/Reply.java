package Customers;

import java.io.Serializable;

/**
 * Entity
 * Contains reply message to the complain
 *
 */
public class Reply implements Serializable{

	/**reply id*/
	private int id;
	/**complain number*/
	private int complainId;
	/**reply message*/
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
	 * @param id reply id
	 * @param complainId complain id
	 * @param replyment message in reply
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
	 * @param complainId complain id
	 * @param replyment message in reply
	 * For client using
	 */
	public Reply(int complainId, String replyment) {
		super();
		this.complainId = complainId;
		this.replyment = replyment;
	}

}
