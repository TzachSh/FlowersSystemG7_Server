package Reports;

import java.util.ArrayList;

import Branches.IncomeReport;
import Logic.DbQuery;

/**
 * This class extends report generation for concert report
 */
public class IncomeReportGeneration extends ReportGeneration {
		
		public IncomeReportGeneration(DbQuery db, int year, int quarter) {
			super(db, year, quarter);
		}

		@Override
		public String getQueryReport(int branchId) {
			return "SELECT b.brId as 'Branch Number' ,b.brName as 'Branch Name',SUM(op.amount) as 'Amount' "
				  + "FROM `order` o, orderpayment op, branch b "
				  + "WHERE o.brId=? AND year(o.creationDate)=? AND o.stId=1 AND quarter(o.creationDate)=? AND o.oId=op.oId AND b.brId=o.brId ";
		}
		
		/**
		 * Read and get the report for specified branch id
		 * @param branchId Branch id to create for it the report
		 * @return Collection of Income report
		 * @throws Exception Exception when failed on reading the report
		 */
		public ArrayList<Object> getReport(int branchId) throws Exception
		{
			ArrayList<String[]> csvData = getReportInString(branchId);
			
			// convert each column in string array to order report entity
			ArrayList<Object> report = new ArrayList<>();
			
			for (String[] row : csvData)
			{
				int brId = Integer.valueOf(row[0]);
				String brName = row[1];
				double amount = Double.valueOf(row[2]);
				IncomeReport incomeReport = new IncomeReport(brId, brName, amount);		
				report.add(incomeReport);
			}

			return report;
		}

		@Override
		public String toString() {
			return "IncomeReport";
		}
}
