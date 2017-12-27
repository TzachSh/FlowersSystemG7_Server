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
public class DbGetter<T> {
	private ArrayList<Object> queryResult = new ArrayList<>();
	private DbQuery db;
	private Packet packet;
	private ConnectionToClient client;

	public DbGetter(DbQuery db) {
		this.db = db;
		this.packet = db.getPacket();
		this.client = db.getClient();
	}

	public void setMainGetter(ISelect objSelect) {
		try {
			Connection con = db.connectToDB();
			String qry = objSelect.getQuery();
			PreparedStatement stmt = con.prepareStatement(qry);
			objSelect.setStatements(stmt, db.getPacket());
			ResultSet rs = stmt.executeQuery(qry);

			while (rs.next()) {
				Object obj = objSelect.createObject(rs);
				queryResult.add(obj);
			}

			con.close();
		} 
		catch (Exception e) {
			packet.setExceptionMessage(e.getMessage());
		}
	}

	public <E> void setCollectionInObject(ISelectCollection<T, E> objSelect) {
		if (!packet.getResultState())
			return;

		try 
		{
			Connection con = db.connectToDB();
			ArrayList<E> collectionInObject = new ArrayList<>();
			for (int i = 0; i < queryResult.size(); i++) 
			{
				@SuppressWarnings("unchecked")
				T objConverted = (T) queryResult.get(i);

				String qry = objSelect.getQuery();
				PreparedStatement stmt = con.prepareStatement(qry);
				objSelect.setStatements(stmt, objConverted);

				ResultSet rs = stmt.executeQuery(qry);

				while (rs.next()) {
					E obj = objSelect.createObject(rs, objConverted);
					collectionInObject.add(obj);
				}
				
				objSelect.addCollectionToObject(objConverted, collectionInObject);
			}

			con.close();
		} 
		catch (Exception e) {
			packet.setExceptionMessage(e.getMessage());
		}
	}
	
	public void performAction()
	{
		try 
		{
			if (packet.getResultState())
			{
				packet.setParameterList(queryResult);
			}
			
			client.sendToClient(packet);
			
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}
