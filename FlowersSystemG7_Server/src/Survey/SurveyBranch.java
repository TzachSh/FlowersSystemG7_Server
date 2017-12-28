package Survey;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import Branches.Branch;

public class SurveyBranch implements Serializable {
	private int id;
	private Date creationDate;
	private int branchId;
	private ArrayList<AnswerSurvey> answerSurvey;
	private int surveyId;
	private int surveyConclusionId;
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
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public ArrayList<AnswerSurvey> getAnswerSurvey() {
		return answerSurvey;
	}
	public void setAnswerSurvey(ArrayList<AnswerSurvey> answerSurvey) {
		this.answerSurvey = answerSurvey;
	}
	public int getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}
	public int getSurveyConclusionId() {
		return surveyConclusionId;
	}
	public void setSurveyConclusionId(int surveyConclusionId) {
		this.surveyConclusionId = surveyConclusionId;
	}
	
	public SurveyBranch(int id, Date creationDate, int branchId, ArrayList<AnswerSurvey> answerSurvey, int surveyId,
			int surveyConclusionId) {
		super();
		this.id = id;
		this.creationDate = creationDate;
		this.branchId = branchId;
		this.answerSurvey = answerSurvey;
		this.surveyId = surveyId;
		this.surveyConclusionId = surveyConclusionId;
	}
}
