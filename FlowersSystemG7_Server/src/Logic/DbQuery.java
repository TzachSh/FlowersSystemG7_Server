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
	/**
	 * user to connect to the database
	 */
	private String user;
	/**
	 * password to conncet to the database
	 */
	private String password;
	/**
	 * packet contains data and command to execute
	 */
	private Packet packet;
	/**
	 * client who sent the request
	 */
	private ConnectionToClient client;
	/**
	 * database name
	 */
	private String database;
	/**
	 * connection to the database
	 */
	private Connection conn;

	/**
	 * Constructor that initialize all parameters
	 * 
	 * @param user the user name for server database
	 * @param password the password for server database
	 * @param packet the packet that received from the client
	 * @param client the client that send the request
	 * @param database where all data stored
	 */
	public DbQuery(String user, String password, Packet packet, ConnectionToClient client,String database) {
		this.user = user;
		this.password = password;
		this.packet = packet;
		this.client = client;
		this.database = database;
	}
	/**
	 * init connection fields
	 * @param user -user database
	 * @param password - password database
	 * @param database - database name
	 */
	public DbQuery(String user, String password, String database) {
		this.user = user;
		this.password = password;
		this.database = database;
	}
	
	/**
	 * Send the final packet to the client 
	 * @throws IOException error message
	 * 
	 */
	public void sendToClient() throws IOException
	{
		client.sendToClient(packet);
	}
	
	/**
	 * Getter for password to the database
	 * @return passwor
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Getter for the packet that received from the client
	 * @return packet with commands and data
	 */
	public Packet getPacket() {
		return packet;
	}

	/**
	 * Getter for the client that send the request
	 * @return client connection details 
	 */
	public ConnectionToClient getClient() {
		return client;
	}

	/**
	 * Create the connection to the database based on the user and password
	 * @throws Exception message error
	 */
	public void connectToDB() throws Exception{
		conn = null;
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://localhost/" +database + "?allowMultiQueries=true", user, password);

	}
	/**
	 * closing current connection to database
	 * @throws SQLException message error when close database connection
	 */
	public void connectionClose() throws SQLException 
	{
		conn.close();
	}
	/**
	 * get current connection
	 * @return connection to the database
	 */
	public Connection getConnection() {
		return conn;
	}
	

	
}
