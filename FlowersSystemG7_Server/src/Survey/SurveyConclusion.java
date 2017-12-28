package Survey;

import java.io.Serializable;

import Branches.ServiceExpert;

public class SurveyConclusion implements Serializable {
	private int id;
	private int serviceExpertId;
	private int surveyBranchId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getServiceExpertId() {
		return serviceExpertId;
	}
	public void setServiceExpertId(int serviceExpertId) {
		this.serviceExpertId = serviceExpertId;
	}
	public int getSurveyBranchId() {
		return surveyBranchId;
	}
	public void setSurveyBranchId(int surveyBranchId) {
		this.surveyBranchId = surveyBranchId;
	}
	public SurveyConclusion(int id, int serviceExpertId, int surveyBranchId) {
		super();
		this.id = id;
		this.serviceExpertId = serviceExpertId;
		this.surveyBranchId = surveyBranchId;
	}
}
