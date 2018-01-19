package Survey;

import java.io.Serializable;

/***
 * 
 * Entity class to define Answer of Questions in Survey  
 *
 */
public class AnswerSurvey implements Serializable {
	
	/***
	 * Instance variables
	 */
	private int id;
	private int surveyQuestionId;
	private int branchId;
	private int answer;
	private double avgaAnswerForQuestion;
	
	/***
	 * 
	 * @return branch id
	 */
	public int getBranchId() {
		return branchId;
	}
	/***
	 * 
	 * @param branchId to set
	 */
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	/***
	 * 
	 * @return the value number of the answer
	 */
	public int getAnswer() {
		return answer;
	}
	/***
	 * 
	 * @param answer to set
	 */
	public void setAnswer(int answer) {
		this.answer = answer;
	}
	/***
	 * 
	 * @return answer id
	 */
	public int getId() {
		return id;
	}
	/***
	 * 
	 * @param id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/***
	 * 
	 * @return id of the question in survey of this answer
	 */
	public int getSurveyQuestionId() {
		return surveyQuestionId;
	}
	/***
	 * 
	 * @param surveyQuestionId to set 
	 */
	public void setSurveyQuestionId(int surveyQuestionId) {
		this.surveyQuestionId = surveyQuestionId;
	}
	
	/***
	 * 
	 * @return average of the answers for a specific question
	 */
	public double getAvgaAnswerForQuestion() {
		return avgaAnswerForQuestion;
	}
	/***
	 * 
	 * @param avgaAnswerForQuestion set average answers for a specific question
	 */
	public void setAvgaAnswerForQuestion(double avgaAnswerForQuestion) {
		this.avgaAnswerForQuestion = avgaAnswerForQuestion;
	}
	/***
	 * Constructor to be used by server
	 * 
	 * @param id
	 * @param surveyQuestionId
	 * @param branchId
	 * @param answer
	 */
	public AnswerSurvey(int id, int surveyQuestionId,int branchId,int answer) {
		super();
		this.id = id;
		this.surveyQuestionId = surveyQuestionId;
		this.branchId = branchId;
		this.answer = answer;
	}
	
	/***
	 * Constructor to be used by server to return the average answers for a question
	 * 
	 * @param id
	 * @param surveyQuestionId
	 * @param branchId
	 * @param avgaAnswerForQuestion
	 */
	public AnswerSurvey(int id, int surveyQuestionId,int branchId,double avgaAnswerForQuestion) {
		super();
		this.id = id;
		this.surveyQuestionId = surveyQuestionId;
		this.branchId = branchId;
		this.avgaAnswerForQuestion = avgaAnswerForQuestion;
	}
	/***
	 * Constructor to be used by client side to create an AnswerSurvey object
	 * 
	 * @param surveyQuestionId
	 * @param branchId
	 * @param answer
	 */
	public AnswerSurvey(int surveyQuestionId,int branchId,int answer) {
		super();
		this.surveyQuestionId = surveyQuestionId;
		this.branchId = branchId;
		this.answer = answer;
	}
}