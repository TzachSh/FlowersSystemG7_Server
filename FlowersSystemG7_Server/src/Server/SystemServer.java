package Server;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import Commons.Refund;
import Customers.Account;
import Customers.AccountStatus;
import Customers.Complain;
import Customers.Reply;
import Logic.DbGetter;
import Logic.DbQuery;
import Logic.DbUpdater;
import Logic.ISelect;
import Logic.IUpdate;

import PacketSender.Command;
import PacketSender.Packet;
import Products.CatalogProduct;
import Products.FlowerInProduct;
import Products.ProductType;
import javafx.scene.control.TextArea;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class SystemServer extends AbstractServer {

/*	private static final int DEFAULT_PORT = 5555;*/
	private String user = "root";
	private String password = "root";
	private String database = "test";
	private TextArea txtLog;

	public SystemServer(int port, TextArea txtLog) {
		super(port);
		this.txtLog = txtLog;
	}

	public boolean changeListening(String database, String user, String password) throws IOException {
		String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());// get datetime for log print
		if (!isListening())// check if listen
		{
			if (database.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please Fill DataBase name", "Error", JOptionPane.ERROR_MESSAGE);
				txtLog.setText(time + "---database name missing\n\r" + txtLog.getText());
				return false;
			}
			if (user.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please Fill user name", "Error", JOptionPane.ERROR_MESSAGE);
				txtLog.setText(time + "---user name missing\n\r" + txtLog.getText());
				return false;
			}
			this.user = user;
			this.password = password;
			this.database = database;
			try {
				DbQuery db = new DbQuery(user, password, database);
				db.connectToDB();
				db.connectionClose();
				listen(); // Start listening for connections
			} catch (Exception e) {
				txtLog.setText(time + "---" + e.getMessage() + "\n\r" + txtLog.getText());
				return false;
			}
		} else {

			connectionClose();
		}
		return true;
	}

	/**
	 * closing connection and write to log
	 */
	private void connectionClose() {
		if (isListening())
			try {
				stopListening();
				close();

				// System.out.println("Connection closed");
			} catch (IOException e) {
				// TODO Auto-generated catch block

				// e.printStackTrace();
			}
	}
	
	public void addComplainHandler(DbQuery db , Command key)
	{
		DbUpdater<Complain> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<Complain>() {
			
			@Override
			public void setStatements(PreparedStatement stmt, Complain obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setDate(1,obj.getCreationDate());
				stmt.setString(2, obj.getDetails());
				stmt.setString(3, obj.getTitle());
				stmt.setInt(4, obj.getCustomerId());
				stmt.setInt(5, obj.getCustomerServiceId());
			}
			
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "INSERT INTO complain (creationDate, details, title,cId,eId) " + 
					   "VALUES (?,?,?,?,?);";
			}
		});
	}

	public void getCatalogProductsHandler(DbQuery db, Command key) {
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

				CatalogProduct catalogPro = new CatalogProduct(id, typeId, price, null, null, productName, discount,
						"");
				return (Object) catalogPro;
			}

			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
			}
		});
	}

	public void getFlowersHandler(DbQuery db, Command key) {
		DbGetter dbGet = new DbGetter(db, key);

		dbGet.performAction(new ISelect() {
			@Override
			public String getQuery() {
				return "SELECT FP.fId, FP.pId, FP.quantity " + "FROM flowerinproduct FP";
			}

			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				int fId = rs.getInt(1);
				int productID = rs.getInt(2);
				int qty = rs.getInt(3);

				FlowerInProduct fp = new FlowerInProduct(fId, productID, qty);
				return (Object) fp;
			}

			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
			}
		});
	}

	public void getComplainsHandler(DbQuery db , Command key)
	{
		DbGetter dbGetter = new DbGetter(db, key);
		dbGetter.performAction(new ISelect() {
			
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "SELECT * FROM complain";
			}
			
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				int complainId = rs.getInt(1);
				java.sql.Date creationDate = rs.getDate(2);
				String details = rs.getString(3);
				String title = rs.getString(4);
				int customerId = rs.getInt(5);
				int creatorId = rs.getInt(6);

				Complain complain = new Complain(complainId, creationDate, title, details, customerId,creatorId);
				return (Object) complain;
			}
		});
	}
	
	public void updateCatalogProductHandler(DbQuery db, Command key) {
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
				else if (key.equals(Command.addComplain)) {
					addComplainHandler(db,key);
				}
				else if(key.equals(Command.getComplains)) {
					getComplainsHandler(db, key);
				}
				else if(key.equals(Command.addReply)) {
					addReplyHandler(db,key);
				}
				else if(key.equals(Command.addComplainRefund)) {
					addComplainRefundHandler(db,key);
				}	
				else if(key.equals(Command.refundAccount)) {
					updateAccountsBalanceHandler(db, key);
				}
		}
			db.connectionClose();
	}
		catch (Exception e) {
			txtLog.setText(time+"---"+e.getMessage()+"\n\r"+ txtLog.getText());
			packet.setExceptionMessage(e.getMessage());
		}
		finally {
			try {
				db.sendToClient();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void addComplainRefundHandler(DbQuery db, Command key) {
		// TODO Auto-generated method stub
		
		DbUpdater<Refund> dbUpdate = new DbUpdater<>(db, key);
		
		dbUpdate.performAction(new IUpdate<Refund>() {
			
			@Override
			public void setStatements(PreparedStatement stmt, Refund obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setDate(1,obj.getCreationDate());
				stmt.setDouble(2, obj.getAmount());
				stmt.setInt(3, obj.getRefundAbleId());
			}
			
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "UPDATE account " 
				+ "SET C.productName = ?, C.discount = ?, C.image = ?, P.typeId = ?, P.price = ? "
				+ "WHERE P.pId = ?";
			}
		});
	}

	private void addReplyHandler(DbQuery db, Command key) {
		// TODO Auto-generated method stub
		DbUpdater<Reply> dbUpdate = new DbUpdater<>(db, key);
		
		dbUpdate.performAction(new IUpdate<Reply>() {
			
			@Override
			public void setStatements(PreparedStatement stmt, Reply obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setInt(1,obj.getComplainId());
				stmt.setString(2, obj.getReplyment());
			}
			
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "INSERT INTO reply (comId, replyment) " + 
				   "VALUES (?,?);";
			}
		});
	}
	
	public void updateAccountsBalanceHandler(DbQuery db , Command key)
	{
		DbUpdater<Refund> dbUpdate = new DbUpdater<>(db, key);
		
		dbUpdate.performAction(new IUpdate<Refund>() {
			
			@Override
			public void setStatements(PreparedStatement stmt, Refund obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setDouble(1, obj.getAmount());
				stmt.setInt(2, obj.getRefundAbleId());
			}
			
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "UPDATE account A INNER JOIN complain C ON A.cId = C.cId " +
					   "SET A.balance = A.balance + ? " +
					   "WHERE C.cId = ?";
			}
		});
	}


}
