package Logic;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import PacketSender.Command;
import PacketSender.Packet;
import ocsf.server.ConnectionToClient;

/**
 * This Class uses for execute select queries to database with the relevant implement
 *
 */
public class DbGetter {
	/** the collection that used for the select result */
	private ArrayList<Object> queryResult = new ArrayList<>();
	private DbQuery db;
	private Packet packet;
	private ConnectionToClient client;
	private Command cmd;

	
	/**
	 * Constructor that initialize all parameters that need for execute the select query
	 * 
	 * @param db This object contains information about database connection and the client with its packet
	 * @param cmd The relevant command that we want to execute for
	 */
	public DbGetter(DbQuery db, Command cmd) {
		this.db = db;
		this.packet = db.getPacket();
		this.client = db.getClient();
		this.cmd = cmd;
	}
	
	/**
	 * uses for select queries
	 * 
	 * @param objSelect the interface that implements the relevant select
	 */
	public void performAction(ISelect objSelect) {
		try {
			// create the connection to db
			Connection con = db.getConnection();
			
			// get the query from the implemention
			String qry = objSelect.getQuery();
			
			// set the statements from the implemention
			PreparedStatement stmt = con.prepareStatement(qry);
			objSelect.setStatements(stmt, db.getPacket());
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				// create the object from the implemention
				Object obj = objSelect.createObject(rs);
				queryResult.add(obj);

			}

			con.close();
			packet.setParametersForCommand(cmd, queryResult);
		} 
		catch (Exception e) {
			packet.setExceptionMessage(e.getMessage());
		}
	}

}
