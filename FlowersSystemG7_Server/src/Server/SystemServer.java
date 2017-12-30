package Server;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import Logic.DbGetter;
import Logic.DbQuery;
import Logic.DbUpdater;
import Logic.ISelect;
import Logic.IUpdate;

import PacketSender.Command;
import PacketSender.Packet;
import Products.CatalogProduct;
import Products.FlowerInProduct;
<<<<<<< HEAD
import Products.ProductType;
=======
import javafx.scene.control.TextArea;
>>>>>>> branch 'develop' of https://github.com/TzachSh/FlowersSystemG7_Server
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class SystemServer extends AbstractServer {

	//private static final int DEFAULT_PORT = 5555;
	private String user = "root";
<<<<<<< HEAD
	private String password = "aA123456";
=======
	private String password = "root";
	private String database;
	private TextArea txtLog;
>>>>>>> branch 'develop' of https://github.com/TzachSh/FlowersSystemG7_Server

	public SystemServer(int port,TextArea txtLog) {
		super(port);
		this.txtLog=txtLog;
	}

<<<<<<< HEAD
=======
	public boolean changeListening(String database,String user, String password) throws IOException 
	{
		String time=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());//get datetime for log print
		if(!isListening())//check if listen
		{
			if(database.isEmpty()) {
				JOptionPane.showMessageDialog(null,"Please Fill DataBase name","Error",JOptionPane.ERROR_MESSAGE);
				txtLog.setText(time+"---database name missing\n\r"+txtLog.getText());
				return false;	
			}
			if(user.isEmpty())
			{
				JOptionPane.showMessageDialog(null,"Please Fill user name","Error",JOptionPane.ERROR_MESSAGE);
				txtLog.setText(time+"---user name missing\n\r"+txtLog.getText());
				return false;
			}
			this.user=user;
			this.password=password;
			this.database=database;
			try {
				DbQuery db = new DbQuery(user, password, database);
				db.connectToDB();
				db.connectionClose();
				listen(); // Start listening for connections
			}
			catch (Exception e) {
				txtLog.setText(time+"---"+e.getMessage()+"\n\r"+txtLog.getText());
				return false;
			}
		}
		else
		{
			
			connectionClose();
		}
		return true;
	}
	/**
	 * closing connection and write to log
	 * */
	private void connectionClose()
	{
		if(isListening())
			try {
				stopListening();
				close();
				
//				System.out.println("Connection closed");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
	//			e.printStackTrace();
			}
	}
>>>>>>> branch 'develop' of https://github.com/TzachSh/FlowersSystemG7_Server
	public void getCatalogProductsHandler(DbQuery db, Command key)
	{	
		DbGetter dbGet = new DbGetter(db, key);
		
		dbGet.performAction(new ISelect() {
			@Override
			public String getQuery() {
				return "SELECT P.pId, C.productName, C.discount, C.image, T.typeId, T.description, P.price "
						+ "FROM product P INNER JOIN ProductType T ON P.pId = T.typeId "
						+ "INNER JOIN CatalogProduct C ON P.pId = C.pId";
			}

			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				int id = rs.getInt(1);
				String productName = rs.getString(2);
				int discount = rs.getInt(3);
				int typeId = rs.getInt(5);
				double price = rs.getDouble(7);

				CatalogProduct catalogPro = new CatalogProduct(id, typeId, price, null, null, productName, discount, "");
				return (Object)catalogPro;
			}

			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { }
		});
	}
	
	public void getFlowersHandler(DbQuery db, Command key)
	{	
		DbGetter dbGet = new DbGetter(db, key);
		
		dbGet.performAction(new ISelect() {
			@Override
			public String getQuery() {
				return "SELECT FP.fId, FP.pId, FP.quantity " + 
						"FROM flowerinproduct FP";
			}

			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				int fId = rs.getInt(1);
				int productID = rs.getInt(2);
				int qty = rs.getInt(3);

				FlowerInProduct fp = new FlowerInProduct(fId, productID, qty);
				return (Object)fp;
			}

			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { }
		});
	}
	
	public void updateCatalogProductHandler(DbQuery db,  Command key)
	{
		DbUpdater<CatalogProduct> dbUpdate = new DbUpdater<>(db, key);
	
		dbUpdate.performAction(new IUpdate<CatalogProduct>() {

			@Override
			public String getQuery() {
				return "UPDATE product P INNER JOIN ProductType T ON P.pId = T.typeId "
						+ "INNER JOIN CatalogProduct C ON P.pId = C.pId "
						+ "SET C.productName = ?, C.discount = ?, C.image = ?, P.typeId = ?, P.price = ? "
						+ "WHERE P.pId = ?";
			}

			@Override
			public void setStatements(PreparedStatement stmt, CatalogProduct obj) throws SQLException {
				stmt.setString(1, obj.getName());
				stmt.setInt(2, obj.getSaleDiscountPercent());
				stmt.setString(3, obj.getImgUrl());
				stmt.setInt(4, obj.getProductTypeId());
				stmt.setDouble(5, obj.getPrice());
				stmt.setInt(6, obj.getId());
			}
		});
	}
	
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Packet packet = (Packet) msg;
<<<<<<< HEAD
		DbQuery db = new DbQuery(user, password, packet, client);
		for (Command key : packet.getCommands())
		{
			if (key.equals(Command.getCatalogProducts)) {
				getCatalogProductsHandler(db, key);
			}

			else if (key.equals(Command.updateCatalogProduct)) {
				updateCatalogProductHandler(db, key);
			}
			
			else if (key.equals(Command.getFlowers)) {
				getFlowersHandler(db, key);
			}
=======
		String time=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		txtLog.setText(time+"---from: "+client+" commands: "+packet.getCommands()+"\n\r"+txtLog.getText());
		DbQuery db = new DbQuery(user, password, packet, client,database);
		try {
			db.connectToDB();
			for (Command key : packet.getCommands())
			{
				if (key.equals(Command.getCatalogProducts)) {
					getCatalogProductsHandler(db, key);
				}
	
				else if (key.equals(Command.updateCatalogProduct)) {
					updateCatalogProductHandler(db, key);
				}
				
				else if (key.equals(Command.getFlowers)) {
					getFlowersHandler(db, key);
				}
				
				
			}
			db.connectionClose();
>>>>>>> branch 'develop' of https://github.com/TzachSh/FlowersSystemG7_Server
		}
<<<<<<< HEAD
		
		db.sendToClient();
=======
		catch (Exception e) {
			txtLog.setText(time+"---"+e.getMessage()+"\n\r"+ txtLog.getText());
			packet.setExceptionMessage(e.getMessage());
		}
		finally {
			db.sendToClient();
		}
>>>>>>> branch 'develop' of https://github.com/TzachSh/FlowersSystemG7_Server
	}
/*
	public static void main(String[] args) {
		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(args[0]); // Get port from command line
		} catch (Throwable t) {
			port = DEFAULT_PORT; // Set port to 5555
		}

		SystemServer sc = new SystemServer(port);

		try {
			sc.listen(); // Start listening for connections
			System.out.println(String.format("Server has started listening on port: %d", port));
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}*/

}