package Survey;

import java.io.Serializable;

public class AnswerSurvey implements Serializable {
	private int id;
	private int surveyQuestionId;
	private int surveyBranchId;
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
	public int getSurveyBranchId() {
		return surveyBranchId;
	}
	public void setSurveyBranchId(int surveyBranchId) {
		this.surveyBranchId = surveyBranchId;
	}
	
	public AnswerSurvey(int id, int surveyQuestionId, int surveyBranchId) {
		super();
		this.id = id;
		this.surveyQuestionId = surveyQuestionId;
		this.surveyBranchId = surveyBranchId;
	}
}
