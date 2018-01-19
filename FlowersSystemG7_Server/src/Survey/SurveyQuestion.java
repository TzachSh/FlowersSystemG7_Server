package Survey;

import java.io.Serializable;
import java.util.ArrayList;
/***
 * 
 * Entity class to define Question in Survey
 *
 */
public class SurveyQuestion implements Serializable {
	
	/***
	 * Instance variables
	 */
	private int id;
	private int surveyId;
	private int questionId;
	/***
	 * 
	 * @return this survey question id
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
	 * @return the id of the survey which a question is attached to
	 */
	public int getSurveyId() {
		return surveyId;
	}
	/***
	 * 
	 * @param surveyId to set
	 */
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}
	/***
	 * 
	 * @return the id of the question in this survey
	 */
	public int getQuestionId() {
		return questionId;
	}
	/***
	 * 
	 * @param questionId to set
	 */
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	/***
	 * Constructor for the server side
	 * 
	 * @param id
	 * @param surveyId
	 * @param questionId
	 */
	public SurveyQuestion(int id, int surveyId,int questionId) {
		super();
		this.id = id;
		this.surveyId = surveyId;
		this.questionId = questionId;
	}
	/***
	 * Constructor for the client side
	 * 
	 * @param surveyId
	 * @param questionId
	 */
	public SurveyQuestion(int surveyId, int questionId) {
		super();
		this.surveyId = surveyId;
		this.questionId = questionId;
	}
}
