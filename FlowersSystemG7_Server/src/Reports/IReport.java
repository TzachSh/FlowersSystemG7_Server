package Reports;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Interface that implements the whole data for each line in the report,
 * each field separators by the String array 
 */
public interface IReport {
	/** 
	 * create and return all the lines in the report that each column is shown as string array
	 * @return Linked Hash Map that each key is the branch Id,
	 * 		   and each value is the branch data collection
	 */
 	LinkedHashMap<Integer, ArrayList<String[]>> createCsvDataForEachBranch();
	
	
}
