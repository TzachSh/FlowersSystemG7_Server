package Reports;

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
	
	@Override
	public Object createObject(String[] row) {
		int complainId = Integer.valueOf(row[0]);
		java.sql.Date creationDate = java.sql.Date.valueOf(row[1]);
		String details = row[2];
		String title = row[3];
		int customerId = Integer.valueOf(row[4]);
		int creatorId = Integer.valueOf(row[5]);
		boolean isActive = Boolean.valueOf(row[6]);
		int branchId = Integer.valueOf(row[7]);
		
		return new Complain(complainId, creationDate, title, details, customerId,creatorId,isActive,branchId);
	}
	
	@Override
	public String toString() {
		return "ComplainReport";
	}


}
