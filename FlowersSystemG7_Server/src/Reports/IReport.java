package Reports;


/**
 * Interface that implements the whole data for each line in the report,
 * each field separators by the String array 
 */
public interface IReport {
 	/**
 	 * return the query for generation report
 	 * @param branchId The branch id to perform the query
 	 */
 	String getQueryReport(int branchId);
}
