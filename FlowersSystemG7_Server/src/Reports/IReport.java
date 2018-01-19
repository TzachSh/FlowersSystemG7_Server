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
 	
 	/**
 	 * Create and return concrete object based on the row
 	 * @param row The row to create from it the object
 	 */
 	Object createObject(String[] row);
}
