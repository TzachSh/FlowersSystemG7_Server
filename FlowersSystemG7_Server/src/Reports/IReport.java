package Reports;


/**
 * Interface that implements the whole data for each line in the report,
 * each field separators by the String array 
 */
public interface IReport {
 	/** 
	 * return the position in the csv data array that explain the branch id position
	 */
 	int getIndexOfBranchInArray();
	
 	/**
 	 * return the query for generation report
 	 */
 	String getQueryReport();
}
