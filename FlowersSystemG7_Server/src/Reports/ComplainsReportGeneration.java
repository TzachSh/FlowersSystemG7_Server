package Reports;

import java.util.ArrayList;

import Customers.Complain;
import Logic.DbQuery;

/**
 * This class extends report generation for concert report
 */ 
public class ComplainsReportGeneration extends ReportGeneration {
	
	public ComplainsReportGeneration(DbQuery db, int year, int quarter) {
		super(db, year, quarter);
	}

	@Override
	public String getQueryReport(int branchId) {
		return "SELECT * FROM complain " +
			   "WHERE brId=? and YEAR(creationDate)=? and QUARTER(creationDate)=?";
	}
	
	/**
	 * Read and get the report for specified branch id
	 * @param branchId Branch id to create for it the report
	 * @return Collection of Complains report
	 * @throws Exception Exception when failed on reading the report
	 */
	public ArrayList<Object> getReport(int branchId) throws Exception
	{
		ArrayList<String[]> csvData = getReportInString(branchId);
		
		// convert each column in string array to order report entity
		ArrayList<Object> report = new ArrayList<>();
		
		for (String[] row : csvData)
		{
			int complainId = Integer.valueOf(row[0]);
			java.sql.Date creationDate = java.sql.Date.valueOf(row[1]);
			String details = row[2];
			String title = row[3];
			int customerId = Integer.valueOf(row[4]);
			int creatorId = Integer.valueOf(row[5]);
			boolean isActive = Boolean.valueOf(row[6]);
			
			Complain complain = new Complain(complainId, creationDate, title, details, customerId,creatorId,isActive,branchId);
			report.add(complain);
		}

		return report;
	}

	@Override
	public String toString() {
		return "ComplainReport";
	}
}
