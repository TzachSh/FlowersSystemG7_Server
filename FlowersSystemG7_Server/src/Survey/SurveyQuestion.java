package Survey;

import java.util.ArrayList;

public class SurveyQuestion {
	private int id;
	private Survey survey;
	private ArrayList<AnswerSurvey> answerSurveyList;
	private Question question;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Survey getSurvey() {
		return survey;
	}
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	public ArrayList<AnswerSurvey> getAnswerSurveyList() {
		return answerSurveyList;
	}
	public void setAnswerSurveyList(ArrayList<AnswerSurvey> answerSurveyList) {
		this.answerSurveyList = answerSurveyList;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public SurveyQuestion(int id, Survey survey, ArrayList<AnswerSurvey> answerSurveyList, Question question) {

		this.id = id;
		this.survey = survey;
		this.answerSurveyList = answerSurveyList;
		this.question = question;
	}
	
}
