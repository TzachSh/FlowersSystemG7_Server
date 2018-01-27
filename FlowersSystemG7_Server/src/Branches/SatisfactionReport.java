package Branches;

import java.io.Serializable;
/**
 * Entity satisfaction report contain question average grade for the question
 *
 */
public class SatisfactionReport implements Serializable{
	/**satisfaction question  */
	private String question;
	/**answer average*/
	private String avg;
	/**
	 * @return question
	 */
	public String getQuestion() {
		return question;
	}
	/**
	 * 
	 * @param question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	/**
	 * 
	 * @return average for question
	 */
	public String getAvg() {
		return avg;
	}
	/**
	 * 
	 * @param avg average for question
	 */
	public void setAvg(String avg) {
		this.avg = avg;
	}
	/**
	 * satisfaction constructor
	 * @param question satisfaction report question
	 * @param avg satisfaction question average
	 */
	public SatisfactionReport(String question, String avg) {
		super();
		this.question = question;
		this.avg = avg;
	}
	
	
	
	
}
