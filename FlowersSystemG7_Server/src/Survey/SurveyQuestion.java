package Survey;

import java.io.Serializable;
import java.util.ArrayList;

public class SurveyQuestion implements Serializable {
	private int id;
	private int surveyId;
	private int questionId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public SurveyQuestion(int id, int surveyId,int questionId) {
		super();
		this.id = id;
		this.surveyId = surveyId;
		this.questionId = questionId;
	}
	public SurveyQuestion(int surveyId, int questionId) {
		super();
		this.surveyId = surveyId;
		this.questionId = questionId;
	}
}
