package Logic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import PacketSender.Packet;
import ocsf.server.ConnectionToClient;

/**
 * 
 *
 * @param <T> The main object type
 */
public class DbGetter {
	private ArrayList<Object> queryResult = new ArrayList<>();
	private DbQuery db;
	private Packet packet;
	private ConnectionToClient client;

	public DbGetter(DbQuery db) {
		this.db = db;
		this.packet = db.getPacket();
		this.client = db.getClient();
	}

	public void performAction(ISelect objSelect) {
		try {
			Connection con = db.connectToDB();
			String qry = objSelect.getQuery();
			PreparedStatement stmt = con.prepareStatement(qry);
			objSelect.setStatements(stmt, db.getPacket());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Object obj = objSelect.createObject(rs);
				queryResult.add(obj);
			}

			con.close();
			
			packet.setParameterList(queryResult);
		} 
		catch (Exception e) {
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
