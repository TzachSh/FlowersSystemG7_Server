package Branches;

import java.io.Serializable;

public class SatisfactionReport implements Serializable{

	private String question;
	private String avg;
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAvg() {
		return avg;
	}
	public void setAvg(String avg) {
		this.avg = avg;
	}
	public SatisfactionReport(String question, String avg) {
		super();
		this.question = question;
		this.avg = avg;
	}
	
	
	
	
}
