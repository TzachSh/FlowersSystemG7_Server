package Schedules;


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
	private ThreadType type;
	
	public enum ThreadType
	{
		Reports,
		Paying,
		DeleteMemberships
	}
	
	public ScheduleThread(DbQuery db, SystemServer sysServer,ThreadType type)
	{
		this.db = db;
		this.sysServer = sysServer;
		this.type = type;
	}
	
	public void run()
	{

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				
				ScheduleTask task = new ScheduleTask(db, sysServer);
				try
				{
					switch (type)
					{
						case Reports: 
							task.performAllReports(false);
							break;
							
						case Paying:
							task.performPayingForMemberships(false);
							break;
							
						case DeleteMemberships:
							task.performDeletingMemberships(false);
							break;
					}
				}
				catch (Exception e)
				{
					sysServer.logErrorSchedule(e.getMessage());
				}
			}
		});
	}

	
}


