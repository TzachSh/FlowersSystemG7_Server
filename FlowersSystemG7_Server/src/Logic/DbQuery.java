package Logic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import PacketSender.Packet;
import ocsf.server.ConnectionToClient;

public class DbQuery {
	private String user;
	private String password;
	private Packet packet;
	private ConnectionToClient client;

	public DbQuery(String user, String password, Packet packet, ConnectionToClient client) {
		this.user = user;
		this.password = password;
		this.packet = packet;
		this.client = client;
	}

	private Connection connectToDB() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {/* handle the error */
			System.out.println(ex.getMessage());
		}

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/test", user, password);
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}

	/**
	 * uses for update, insert and delete queries
	 * 
	 * @param objUpdate the interface that implements the relevant update
	 */
	public <T> void performAction(IUpdate<T> objUpdate)
	{
		try {
			Connection con = connectToDB();
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
			packet.setExceptionMessage(e);
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
	
	/**
	 * uses for select query
	 * 
	 * @param objSelect the interface implements the select operation
	 */
	public void select(ISelect objSelect)
	{
		try 
		{
			ArrayList<Object> objList = new ArrayList<>();
			
			Connection con = connectToDB();
			
			Statement stmt = con.createStatement();
			String qry = objSelect.getQuery();
			ResultSet rs = stmt.executeQuery(qry);
			while(rs.next())
			{
				objList.add(objSelect.createObject(rs));
			}
			
			packet.setParameterList(objList);
			
			client.sendToClient(packet);
		    con.close();
		}
		catch (Exception e)
		{
			packet.setExceptionMessage(e);
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
