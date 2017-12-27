package Logic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import PacketSender.Packet;
import ocsf.server.ConnectionToClient;

public class DbUpdater<T>
{
	private DbQuery db;
	private Packet packet;
	private ConnectionToClient client;

	public DbUpdater(DbQuery db) {
		this.db = db;
		this.packet = db.getPacket();
		this.client = db.getClient();
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
			T obj = (T)packet.getParameterList().get(0);

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
		finally 
		{
			try
			{
				client.sendToClient(packet);
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
