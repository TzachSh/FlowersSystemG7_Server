package Reports;

import java.util.ArrayList;

import Branches.OrderReport;
import Logic.DbQuery;

public class TestMain {

	public static void main(String[] args) {
		DbQuery db = new DbQuery("root", "aA123456", "test");
		ReportGeneration rep = new OrderReportGeneration(db, 2017, 1);
		try {
			//rep.performReport();
			
		 ArrayList<OrderReport> rr = ((OrderReportGeneration)rep).getReport(2);
			System.out.println(rr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
