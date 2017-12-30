package Logic;

import java.sql.Connection;
import java.sql.PreparedStatement;

import PacketSender.Command;
import PacketSender.Packet;

/**
 * This Class uses for execute update queries to database with the relevant implement
 *
 * @param <T> Type of the object we want to update
 */
public class DbUpdater<T>
{
	private DbQuery db;
	private Packet packet;
	private Command cmd;
	
	/**
	 * Constructor that initialize all parameters that need for execute the update query
	 * 
	 * @param db This object contains information about database connection and the client with its packet
	 * @param cmd The relevant command that we want to execute for
	 */
	public DbUpdater(DbQuery db, Command cmd) {
		this.db = db;
		this.packet = db.getPacket();
		this.cmd = cmd;
	}
	
	/**
	 * uses for update, insert and delete queries
	 * 
	 * @param objUpdate the interface that implements the relevant update
	 */
	public void performAction(IUpdate<T> objUpdate)
	{
		try {
			// create the connection to db
			Connection con = db.getConnection();
			
			// get the relevant object for update in db that passed from the client as a parameter
			@SuppressWarnings("unchecked")
			T obj = (T)packet.getParameterForCommand(cmd).get(0);
			
			// get the query from the implemention
			String query = objUpdate.getQuery();
			
			// set the statements from the implemention
			PreparedStatement stmt = con.prepareStatement(query);
			objUpdate.setStatements(stmt, obj);
			
			stmt.executeUpdate();
		} 
		catch (Exception e)
		{
			packet.setExceptionMessage(e.getMessage());
		} 
	}
}
