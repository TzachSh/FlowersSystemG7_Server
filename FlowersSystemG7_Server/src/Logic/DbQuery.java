package Logic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import PacketSender.Packet;

import ocsf.server.ConnectionToClient;

/**
 * This class uses for hands all the details about database connection and the client with its packet
 *
 */
public class DbQuery {
	private String user;
	private String password;
	private Packet packet;
	private ConnectionToClient client;

	/**
	 * Constructor that initialize all parameters
	 * 
	 * @param user the user name for server database
	 * @param password the password for server database
	 * @param packet the packet that received from the client
	 * @param client the client that send the request
	 */
	public DbQuery(String user, String password, Packet packet, ConnectionToClient client) {
		this.user = user;
		this.password = password;
		this.packet = packet;
		this.client = client;
		
	}
	
	/**
	 * Send the final packet to the client 
	 * 
	 */
	public void sendToClient()
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
	
	/**
	 * Getter for password to the database
	 * 
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Getter for the packet that received from the client
	 *
	 */
	public Packet getPacket() {
		return packet;
	}

	/**
	 * Getter for the client that send the request
	 * 
	 */
	public ConnectionToClient getClient() {
		return client;
	}

	/**
	 * Create the connection to the database based on the user and password
	 * 
	 */
	public Connection connectToDB() {
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

	
}
