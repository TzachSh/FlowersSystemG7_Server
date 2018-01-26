package Branches;

import java.io.Serializable;

public class SatisfactionReport implements Serializable{
	/**satisfaction question  */
	private String question;
	/**answer average*/
	private String avg;
	/**returns the question*/
	public String getQuestion() {
		return question;
	}
	/**setting question*/
	public void setQuestion(String question) {
		this.question = question;
	}
	/**returns average for question*/
	public String getAvg() {
		return avg;
	}
	/**setting average for question*/
	public void setAvg(String avg) {
		this.avg = avg;
	}
	/**
	 * satisfaction constrcutor
	 * @param question satisfaction report question
	 * @param avg satisfaction question average
	 */
	public SatisfactionReport(String question, String avg) {
		super();
		this.question = question;
		this.avg = avg;
	}
	
	
	
	
}
