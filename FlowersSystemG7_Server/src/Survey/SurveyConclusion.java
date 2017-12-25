package Survey;

import Branches.ServiceExpert;

public class SurveyConclusion {
	private int id;
	private ServiceExpert serviceExpert;
	private SurveyBranch surveyBranch;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ServiceExpert getServiceExpert() {
		return serviceExpert;
	}
	public void setServiceExpert(ServiceExpert serviceExpert) {
		this.serviceExpert = serviceExpert;
	}
	public SurveyBranch getSurveyBranch() {
		return surveyBranch;
	}
	public void setSurveyBranch(SurveyBranch surveyBranch) {
		this.surveyBranch = surveyBranch;
	}
	public SurveyConclusion(int id, ServiceExpert serviceExpert, SurveyBranch surveyBranch) {
		super();
		this.id = id;
		this.serviceExpert = serviceExpert;
		this.surveyBranch = surveyBranch;
	}

}
