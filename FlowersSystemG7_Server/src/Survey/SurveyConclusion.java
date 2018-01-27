package Survey;

import java.io.Serializable;

/***
 * 
 * Entity to define the Conclusion of a Survey
 *
 */
public class SurveyConclusion implements Serializable {
	
	
	/**
	 * survey conclusion id 
	 */
	private int id;
	/**
	 * creator of the conclusion
	 */
	private int serviceExpertId;
	/**
	 * survey id which was concluded
	 */
	private int surId;
	/**
	 * conclusion message
	 */
	private String conclusion;
	/***
	 * 
	 * @return the conclusion
	 */
	public String getConclusion() {
		return conclusion;
	}
	/***
	 * 
	 * @param conclusion to be set
	 */
	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}
	/***
	 * 
	 * @return the survey id
	 */
	public int getSurId() {
		return surId;
	}
	/***
	 * 
	 * @param surId to be set
	 */
	public void setSurId(int surId) {
		this.surId = surId;
	}
	/***
	 * 
	 * @return the id of this survey conclusion
	 */
	public int getId() {
		return id;
	}
	/***
	 * 
	 * @param id to be set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/***
	 * 
	 * @return the id of the conclusion creator
	 */
	public int getServiceExpertId() {
		return serviceExpertId;
	}
	/***
	 * 
	 * @param serviceExpertId to be set
	 */
	public void setServiceExpertId(int serviceExpertId) {
		this.serviceExpertId = serviceExpertId;
	}
	/***
	 * Constructor for the server side
	 * 
	 * @param id conclusion id
	 * @param serviceExpertId service expert id
	 * @param conclusion conclusion message
	 * @param surId survey id
	 */
	public SurveyConclusion(int id,int serviceExpertId, String conclusion, int surId) {
		super();
		this.id = id;
		this.serviceExpertId = serviceExpertId;
		this.conclusion = conclusion;
		this.surId = surId;
	}
	
	/***
	 * Constructor for the client side
	 * @param serviceExpertId service expert id
	 * @param conclusion conclusion message
	 * @param surId survey id
	 */
	public SurveyConclusion(int serviceExpertId, String conclusion, int surId) {
		super();
		this.serviceExpertId = serviceExpertId;
		this.conclusion = conclusion;
		this.surId = surId;
	}
}
