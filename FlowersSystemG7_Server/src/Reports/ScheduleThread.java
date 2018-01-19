package Reports;


import Logic.DbQuery;
import Server.SystemServer;
import javafx.application.Platform;

/**
 * Thread for running the schedule task immediately 
 *
 */
public class ScheduleThread extends Thread {

	private DbQuery db;
	private SystemServer sysServer;
	
	public ScheduleThread(DbQuery db, SystemServer sysServer)
	{
		this.db = db;
		this.sysServer = sysServer;
	}
	
	public void run()
	{

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try
				{
					sysServer.logStartSchedule();
					ScheduleTask task = new ScheduleTask(db, sysServer);
					task.performAllReports();
				}
				catch (Exception e)
				{
					sysServer.logErrorSchedule(e.getMessage());
				}
				
				sysServer.logEndSchedule();
			}
		});
	}

	
}


