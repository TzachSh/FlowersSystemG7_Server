package Survey;

import java.io.Serializable;
import java.util.ArrayList;

import Branches.CustomerService;

public class Survey implements Serializable {
	private int id;
	private String subject;
	private CustomerService creator;
	private ArrayList<SurveyBranch> surveyBranchList;
	private ArrayList<SurveyQuestion> surveyQuestionList;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public CustomerService getCreator() {
		return creator;
	}
	public void setCreator(CustomerService creator) {
		this.creator = creator;
	}
	public ArrayList<SurveyBranch> getSurveyBranchList() {
		return surveyBranchList;
	}
	public void setSurveyBranchList(ArrayList<SurveyBranch> surveyBranchList) {
		this.surveyBranchList = surveyBranchList;
	}
	public ArrayList<SurveyQuestion> getSurveyQuestionList() {
		return surveyQuestionList;
	}
	public void setSurveyQuestionList(ArrayList<SurveyQuestion> surveyQuestionList) {
		this.surveyQuestionList = surveyQuestionList;
	}
	public Survey(int id, String subject, CustomerService creator, ArrayList<SurveyBranch> surveyBranchList,
			ArrayList<SurveyQuestion> surveyQuestionList) {
	
		this.id = id;
		this.subject = subject;
		this.creator = creator;
		this.surveyBranchList = surveyBranchList;
		this.surveyQuestionList = surveyQuestionList;
	} 
	
}
