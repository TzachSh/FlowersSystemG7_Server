package Logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
	
	public String getPassword() {
		return password;
	}

	public Packet getPacket() {
		return packet;
	}

	public ConnectionToClient getClient() {
		return client;
	}

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
