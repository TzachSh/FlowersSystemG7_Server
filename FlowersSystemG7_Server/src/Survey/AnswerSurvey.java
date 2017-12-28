package Survey;

import java.io.Serializable;

public class AnswerSurvey implements Serializable {
	private int id;
	private SurveyQuestion surveyQuestion;
	private SurveyBranch surveyBranch;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public SurveyQuestion getSurveyQuestion() {
		return surveyQuestion;
	}
	public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
	}
	public SurveyBranch getSurveyBranch() {
		return surveyBranch;
	}
	public void setSurveyBranch(SurveyBranch surveyBranch) {
		this.surveyBranch = surveyBranch;
	}
	public AnswerSurvey(int id, SurveyQuestion surveyQuestion, SurveyBranch surveyBranch) {

		this.id = id;
		this.surveyQuestion = surveyQuestion;
		this.surveyBranch = surveyBranch;
	}	
}
