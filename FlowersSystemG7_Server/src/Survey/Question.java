package Survey;

import java.io.Serializable;

/***
 * 
 * Entity class to define a Question
 *
 */
public class Question implements Serializable {
	
	/**question id*/
	private int id;
	/**question*/
	private String quesiton;
	
	/***
	 * 
	 * @return question id
	 */
	public int getId() {
		return id;
	}
	/***
	 * 
	 * @param id to set
	 * 
	 */
	public void setId(int id) {
		this.id = id;
	}
	/***
	 * 
	 * @return the question
	 */
	public String getQuesiton() {
		return quesiton;
	}
	/****
	 * 
	 * @param quesiton to set
	 */
	public void setQuesiton(String quesiton) {
		this.quesiton = quesiton;
	}
	/***
	 * Constructor to be used by server
	 *  
	 * @param id question id
	 * @param quesiton question
	 */
	public Question(int id, String quesiton) {

		this.id = id;
		this.quesiton = quesiton;
	}
	
	/***
	 * Constructor to be used by client side
	 * @param quesiton quesiton
	 */
	public Question(String quesiton) {

		this.quesiton = quesiton;
	}
}
