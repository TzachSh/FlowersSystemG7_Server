package Reports;


/**
 * Interface that implements the whole data for each line in the report,<br>
 * Each field separators by the String array 
 */
 public interface IReport {
 	/**
 	 * return the query for generation report<br>
 	 * The query must contains in the where condition the prepared statements in this order:<br>
 	 * <b>branchId, year, quarter</b><br><br>
 	 * In Example:
 	 * <pre>
 	 * {@code
	 * SELECT * FROM reportTable
	 * WHERE brId=? AND YEAR(creationDate)=? AND QUARTER(creationDate)=?
	 * }
	 * </pre>
 	 *  
 	 * @param branchId The branch id to perform the query
 	 */
 	String getQueryReport(int branchId);
 	
 	/**
 	 * Create and return concrete object based on the row
 	 * @param row The row to create from it the object
 	 */
 	Object createObject(String[] row);
}
