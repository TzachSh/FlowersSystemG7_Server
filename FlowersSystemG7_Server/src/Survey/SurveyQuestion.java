package Survey;

import java.io.Serializable;
import java.util.ArrayList;

public class SurveyQuestion implements Serializable {
	private int id;
	private int surveyId;
	private ArrayList<AnswerSurvey> answerSurveyList;
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
	public ArrayList<AnswerSurvey> getAnswerSurveyList() {
		return answerSurveyList;
	}
	public void setAnswerSurveyList(ArrayList<AnswerSurvey> answerSurveyList) {
		this.answerSurveyList = answerSurveyList;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public SurveyQuestion(int id, int surveyId, ArrayList<AnswerSurvey> answerSurveyList, int questionId) {
		super();
		this.id = id;
		this.surveyId = surveyId;
		this.answerSurveyList = answerSurveyList;
		this.questionId = questionId;
	}
}
