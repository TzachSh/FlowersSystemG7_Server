package Branches;

import java.io.Serializable;
import java.util.ArrayList;

import Survey.SurveyConclusion;
import Users.Permission;
/**
 * Entity
 * Service expert with list of survey conclusions
 *
 */
public class ServiceExpert extends Employee implements Serializable {
	/**survey conclusions list */
	private ArrayList<SurveyConclusion> surveyConclusionList;
	/**
	 * 
	 * @return conclusions for survey
	 */
	public ArrayList<SurveyConclusion> getSurveyConclusionList() {
		return surveyConclusionList;
	}
	/**
	 * 
	 * @param surveyConclusionList to set
	 */
	public void setSurveyConclusionList(ArrayList<SurveyConclusion> surveyConclusionList) {
		this.surveyConclusionList = surveyConclusionList;
	}
	/**
	 * 
	 * @param uId user id
	 * @param user user name
	 * @param password user password
	 * @param isLogged if the user is logged 
	 * @param permission use's permission
	 * @param eId employee id
	 * @param role employee role
	 * @param bId branch id
	 * @param surveyConclusionList survey conclusion list
	 */
	public ServiceExpert(int uId, String user, String password, boolean isLogged,Permission permission, int eId, Role role, int bId,
			ArrayList<SurveyConclusion> surveyConclusionList) {
		super(uId, user, password, isLogged,permission, eId, role, bId);
		this.surveyConclusionList = surveyConclusionList;
	}
}
