package Reports;

import java.util.TimerTask;

import Logic.DbQuery;
import Server.SystemServer;
import javafx.application.Platform;

/**
 * ScheduleTask class that runs every night at 0:00 am 
 *
 */
public class ScheduleTask extends TimerTask {
	
	private DbQuery db;
	private SystemServer sysServer;
	public ScheduleTask(DbQuery db, SystemServer sysServer)
	{
		this.db = db;
		this.sysServer = sysServer;
	}
	
	@Override
	public void run()
	{
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				sysServer.logStartSchedule();
				
				// run reports checker
				performAllReports();
					
				// run reseting and paying memberships monthly and yearly
					
				
			
				
				sysServer.logEndSchedule();
			}
		});
		
	}
	
	/**
	 * Run all reports for the last quarter, and if there are not exists, create csv for each of them
	 */
	public void performAllReports()
	{
		// calc the last quarter details
		Quarter lastQuarter = Quarter.getLastQuarter();
		int year = lastQuarter.getYear();
		int quarter = lastQuarter.getQuarter();
		
		OrderReportGeneration orderReport = new OrderReportGeneration(db, year, quarter);
		IncomeReportGeneration incomeReport = new IncomeReportGeneration(db, year, quarter);
		SatisfactionReportGeneration satisfactionReport = new SatisfactionReportGeneration(db, year, quarter);
		ComplainsReportGeneration complainReport = new ComplainsReportGeneration(db, year, quarter);
		
		ReportGeneration[] repCollection = new ReportGeneration[] { orderReport, incomeReport, satisfactionReport, complainReport };
		for (ReportGeneration rep : repCollection)
		{
			try
			{
				rep.performReport();
			}
			catch (Exception e) 
			{
				sysServer.logErrorSchedule(e.getMessage());
			}
		}
	}

}
