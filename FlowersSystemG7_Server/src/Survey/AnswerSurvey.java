package Survey;

import java.io.Serializable;

public class AnswerSurvey implements Serializable {
	
	private int id;
	private int surveyQuestionId;
	private int branchId;
	private int answer;
	private double avgaAnswerForQuestion;
	
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public int getAnswer() {
		return answer;
	}
	public void setAnswer(int answer) {
		this.answer = answer;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSurveyQuestionId() {
		return surveyQuestionId;
	}
	public void setSurveyQuestionId(int surveyQuestionId) {
		this.surveyQuestionId = surveyQuestionId;
	}
	
	public double getAvgaAnswerForQuestion() {
		return avgaAnswerForQuestion;
	}
	public void setAvgaAnswerForQuestion(double avgaAnswerForQuestion) {
		this.avgaAnswerForQuestion = avgaAnswerForQuestion;
	}
	public AnswerSurvey(int id, int surveyQuestionId,int branchId,int answer) {
		super();
		this.id = id;
		this.surveyQuestionId = surveyQuestionId;
		this.branchId = branchId;
		this.answer = answer;
	}
	
	public AnswerSurvey(int id, int surveyQuestionId,int branchId,double avgaAnswerForQuestion) {
		super();
		this.id = id;
		this.surveyQuestionId = surveyQuestionId;
		this.branchId = branchId;
		this.avgaAnswerForQuestion = avgaAnswerForQuestion;
	}
	
	public AnswerSurvey(int surveyQuestionId,int branchId,int answer) {
		super();
		this.surveyQuestionId = surveyQuestionId;
		this.branchId = branchId;
		this.answer = answer;
	}
}
