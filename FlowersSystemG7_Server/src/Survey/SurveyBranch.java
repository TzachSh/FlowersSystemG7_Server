package Survey;

import java.sql.Date;
import java.util.ArrayList;

import Branches.Branch;

public class SurveyBranch {
	private int id;
	private Date creationDate;
	private Branch branch;
	private ArrayList<AnswerSurvey> answerSurvey;
	private Survey survey;
	private SurveyConclusion surveyConclusion;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	public ArrayList<AnswerSurvey> getAnswerSurvey() {
		return answerSurvey;
	}
	public void setAnswerSurvey(ArrayList<AnswerSurvey> answerSurvey) {
		this.answerSurvey = answerSurvey;
	}
	public Survey getSurvey() {
		return survey;
	}
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	public SurveyConclusion getSurveyConclusion() {
		return surveyConclusion;
	}
	public void setSurveyConclusion(SurveyConclusion surveyConclusion) {
		this.surveyConclusion = surveyConclusion;
	}
	public SurveyBranch(int id, Date creationDate, Branch branch, ArrayList<AnswerSurvey> answerSurvey, Survey survey,
			SurveyConclusion surveyConclusion) {

		this.id = id;
		this.creationDate = creationDate;
		this.branch = branch;
		this.answerSurvey = answerSurvey;
		this.survey = survey;
		this.surveyConclusion = surveyConclusion;
	}

}
