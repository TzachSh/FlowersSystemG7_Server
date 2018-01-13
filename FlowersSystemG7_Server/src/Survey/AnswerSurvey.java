package Survey;

import java.io.Serializable;

public class AnswerSurvey implements Serializable {
	
	private int id;
	private int surveyQuestionId;
	private int branchId;
	private int answer;
	
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
	
	public AnswerSurvey(int id, int surveyQuestionId,int branchId,int answer) {
		super();
		this.id = id;
		this.surveyQuestionId = surveyQuestionId;
		this.branchId = branchId;
		this.answer = answer;
	}
	
	public AnswerSurvey(int surveyQuestionId,int branchId,int answer) {
		super();
		this.surveyQuestionId = surveyQuestionId;
		this.branchId = branchId;
		this.answer = answer;
	}
}
