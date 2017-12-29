package Logic;

import java.sql.Connection;
import java.sql.PreparedStatement;

import PacketSender.Command;
import PacketSender.Packet;

public class DbUpdater<T>
{
	private DbQuery db;
	private Packet packet;
	private Command cmd;
	
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
			Connection con = db.connectToDB();
			@SuppressWarnings("unchecked")
			T obj = (T)packet.getParameterForCommand(cmd).get(0);

			String query = objUpdate.getQuery();
			PreparedStatement stmt = con.prepareStatement(query);
			objUpdate.setStatements(stmt, obj);
			stmt.executeUpdate();

			con.close();
		} 
		catch (Exception e)
		{
			packet.setExceptionMessage(e.getMessage());
		} 
	}
}
