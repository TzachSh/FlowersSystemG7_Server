package Reports;


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
		public String getQueryReport() {
			return "SELECT b.brId as 'Branch Number' ,b.brName as 'Branch Name',SUM(op.amount) as 'Amount' "
				  + "FROM `order` o, orderpayment op, branch b "
				  + "WHERE o.brId=? AND year(o.creationDate)=? AND o.stId=1 AND quarter(o.creationDate)=? AND o.oId=op.oId AND b.brId=o.brId ";
		}
		
		@Override
		public Object createObject(String[] row) {
			int brId = Integer.valueOf(row[0]);
			String brName = row[1];
			double amount = Double.valueOf(row[2]);
			return new IncomeReport(brId, brName, amount);	
		}

		@Override
		public String toString() {
			return "IncomeReport";
		}


}
