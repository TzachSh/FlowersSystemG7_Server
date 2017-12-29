package Logic;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import PacketSender.Command;
import PacketSender.Packet;

public class DbGetter {
	private ArrayList<Object> queryResult = new ArrayList<>();
	private DbQuery db;
	private Packet packet;
	private Command cmd;
	
	public DbGetter(DbQuery db, Command cmd) {
		this.db = db;
		this.packet = db.getPacket();
		this.cmd = cmd;
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
			
			packet.setParametersForCommand(cmd, queryResult);
		} 
		catch (Exception e) {
			packet.setExceptionMessage(e.getMessage());
		}
	}

}
