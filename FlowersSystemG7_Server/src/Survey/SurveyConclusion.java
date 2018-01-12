package Survey;

import java.io.Serializable;

import Branches.ServiceExpert;

public class SurveyConclusion implements Serializable {
	private int id;
	private int serviceExpertId;
	private String conclusion;
	
	public String getConclusion() {
		return conclusion;
	}
	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}
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

	public SurveyConclusion(int id, int serviceExpertId, int surveyBranchId) {
		super();
		this.id = id;
		this.serviceExpertId = serviceExpertId;
	}
	
	public SurveyConclusion(int serviceExpertId, String conclusion) {
		super();
		this.serviceExpertId = serviceExpertId;
		this.conclusion = conclusion;
	}
}
