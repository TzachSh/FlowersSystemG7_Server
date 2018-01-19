package Survey;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import Branches.CustomerService;

/***
 * 
 * Entity class to define the Survey
 *
 */
public class Survey implements Serializable {
	/***
	 * Instance variables
	 */
	private int id;
	private String subject;
	private int creatorId;
	private boolean isActive;
	private Date activatedDate;
	private Date closedDate;
	private ArrayList<SurveyQuestion> surveyQuestionList;
	
	/***
	 * 
	 * @return activated date of the survey
	 */
	public Date getActivatedDate() {
		return activatedDate;
	}
	/***
	 * 
	 * @param createdDate to set 
	 */
	public void setActivatedDate(Date createdDate) {
		this.activatedDate = createdDate;
	}
	/***
	 * 
	 * @return the closing date of the survey
	 */
	public Date getClosedDate() {
		return closedDate;
	}
	/***
	 * 
	 * @param closedDate to set
	 */
	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}
	/***
	 * 
	 * @return the status of the survey
	 */
	public boolean isActive() {
		return isActive;
	}
	/***
	 * 
	 * @param isActive to set the state of the survey
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	/***
	 * 
	 * @return the id of the survey
	 */
	public int getId() {
		return id;
	}
	/***
	 * 
	 * @param id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/***
	 * 
	 * @return the survey's subject
	 */
	public String getSubject() {
		return subject;
	}
	/***
	 * 
	 * @param subject to be set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/***
	 * 
	 * @return the id of the employee which created the survey
	 */
	public int getCreatorId() {
		return creatorId;
	}
	/***
	 * 
	 * @param creatorId the creator employee's id
	 */
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	/***
	 * 
	 * @return the questions in this survey
	 */
	public ArrayList<SurveyQuestion> getSurveyQuestionList() {
		return surveyQuestionList;
	}
	/***
	 * 
	 * @param surveyQuestionList to be set
	 */
	public void setSurveyQuestionList(ArrayList<SurveyQuestion> surveyQuestionList) {
		this.surveyQuestionList = surveyQuestionList;
	}
	/***
	 * Constructor for the server use
	 * 
	 * @param id
	 * @param subject
	 * @param creatorId
	 * @param isActive
	 * @param activatedDate
	 * @param closedDate
	 */
	public Survey(int id, String subject, int creatorId,boolean isActive,Date activatedDate,Date closedDate) {
	
		this.id = id;
		this.subject = subject;
		this.creatorId = creatorId;
		this.isActive = isActive;
		this.activatedDate = activatedDate;
		this.closedDate = closedDate;
	} 
	/***
	 * Constructor for the client's side
	 * 
	 * @param subject
	 * @param creatorId
	 * @param isActive
	 */
	public Survey(String subject, int creatorId,boolean isActive) {	
		this.subject = subject;
		this.creatorId = creatorId;
		this.isActive = isActive;
	}
	
}
