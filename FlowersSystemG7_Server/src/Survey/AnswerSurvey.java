package Survey;

import java.io.Serializable;

/***
 * 
 * Entity class to define Answer of Questions in Survey  
 *
 */
public class AnswerSurvey implements Serializable {
	/**
	 * answer survey id
	 */
	private int id;
	/**
	 * question id
	 */
	private int surveyQuestionId;
	/**
	 * branch number
	 */
	private int branchId;
	/**
	 * customer answer
	 */
	private int answer;
	/**
	 * average answer for question
	 */
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
	 * @param branchId branch number to update
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
	 * @param answer answer to update
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
	 * @param id answer survey id
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
	 * @param surveyQuestionId update survey question id 
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
	 * @param id answer survey id
	 * @param surveyQuestionId survey question id
	 * @param branchId branch number
	 * @param answer answer for the question
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
	 * @param id answer survey id
	 * @param surveyQuestionId survey question id
	 * @param branchId branch number
	 * @param avgaAnswerForQuestion average answer for current question
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
	 * @param surveyQuestionId survey question id
	 * @param branchId branch number
	 * @param answer answer for the question
	 */
	public AnswerSurvey(int surveyQuestionId,int branchId,int answer) {
		super();
		this.surveyQuestionId = surveyQuestionId;
		this.branchId = branchId;
		this.answer = answer;
	}
}
