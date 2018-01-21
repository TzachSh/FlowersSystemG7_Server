package Schedules;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;

import Logic.DbQuery;
import Reports.ComplainsReportGeneration;
import Reports.IncomeReportGeneration;
import Reports.OrderReportGeneration;
import Reports.Quarter;
import Reports.ReportGeneration;
import Reports.SatisfactionReportGeneration;
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
			
				
				// run reports checker
				performAllReports(true);
					
				// run reseting and paying memberships monthly and yearly
				performPayingForMemberships(true);	
				
				// run deleting all memberships that passed
				performDeletingMemberships(true);
			}
		});
		
	}
	
	/**
	 * Perform deleting membership accounts that passed
	 * @param onlyOnEndOfDay 'true' - Delete memberships only on the end of the day<br>
	 * 						 'false' - Delete memberships on every hour
	 */
	public void performDeletingMemberships(boolean onlyOnEndOfDay)
	{
		Calendar currentDate = Calendar.getInstance();
		int hour = currentDate.get(Calendar.HOUR_OF_DAY);
		
		if (!onlyOnEndOfDay || hour == 0)
		{
			sysServer.printlogMsg("Finding Memberships for deleting...");
			MemberShipDeleting ms = new MemberShipDeleting(db);
			try
			{
				ms.performDeleteAllMembershipsThatPassed();
				ArrayList<String> accounts = ms.getMembershipDeleted();
				if (accounts.size() > 0)
				{
					sysServer.printlogMsg(String.format("Deleted %d membership accounts!", accounts.size()));
					for (String s : accounts)
					{
						sysServer.printlogMsg(s);
					}
				}
			}
			catch (Exception e)
			{
				sysServer.logErrorSchedule(e.getMessage());
			}
			
			sysServer.printlogMsg("End deleting Scheduling");
		}
	}
	
	/**
	 * Perform charging for all membership accounts for the last month, only if the current day is the first day of the month
	 * @param onlyOnFirstMonth 'true' - perform charging only on the first month,<br>
	 * 					        'false' - perform charging on every day on the month
	 */
	public void performPayingForMemberships(boolean onlyOnFirstMonth)
	{
		Calendar currentDate = Calendar.getInstance();
		// only if the current date is the first of the month
		// or flag for first month is off
		if (!onlyOnFirstMonth || MemberShipPayment.isFirstDayofMonth(currentDate))
		{
			// calc the last month
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			
			sysServer.printlogMsg(String.format("Finding Memberships for Charging [Year: %d, Month: %d]", year, month));
			MemberShipPayment mp = new MemberShipPayment(db, year, month);
			try
			{
				mp.performPaying();
				
				ArrayList<String> charged = mp.getChargedAccounts();
				if (charged.size() > 0)
				{
					sysServer.printlogMsg(String.format("Charged for %d membership accounts!", charged.size()));
					for (String s : charged)
					{
						sysServer.printlogMsg(s);
					}
				}
			}
			catch (Exception e)
			{
				sysServer.logErrorSchedule(e.getMessage());
			}
			sysServer.printlogMsg("End Charging Scheduling");
		}
	}
	
	/**
	 * Run all reports for the last quarter, and if there are not exists, create csv for each of them
	 * @param onlyOnFirstQuarter 'true' - performs reports only on the first date of current quarter,<br>
	 * 							 'false' - performs reports on every day
	 */
	public void performAllReports(boolean onlyOnFirstQuarter)
	{
		Calendar currentDate = Calendar.getInstance();
		if (!onlyOnFirstQuarter || Quarter.getFirstDayOfQuarter().equals(currentDate.getTime()))
		{
			// calc the last quarter details
			Quarter lastQuarter = Quarter.getLastQuarter();
			int year = lastQuarter.getYear();
			int quarter = lastQuarter.getQuarter();
			
			sysServer.printlogMsg(String.format("Starting Reports Generation [Year: %d, Quarter: %d]", year, quarter));
			
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
			sysServer.printlogMsg("Ends Reports Generation Scheduling");
		}
	}

}
