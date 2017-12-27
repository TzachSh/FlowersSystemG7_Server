package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import Logic.DbQuery;
import Logic.ISelect;
import Logic.IUpdate;
import PacketSender.Command;
import PacketSender.Packet;
import Products.CatalogProduct;
import Products.ProductType;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class SystemServer extends AbstractServer {

	private static final int DEFAULT_PORT = 5555;
	private String user = "root";
	private String password = "aA123456";

	public SystemServer(int port) {
		super(port);
		
	}
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client)  {
	
		Packet packet = (Packet)msg;
		DbQuery db = new DbQuery(user, password, packet, client);
		Command key = packet.getmsgKey();
		
		if(key.equals(Command.getCatalogProducts))
		{
			db.select(new ISelect() {

				@Override
				public String getQuery() {
					return "SELECT P.pId, C.productName, C.discount, C.image, T.typeId, T.description, P.price " + 
							"FROM product P INNER JOIN ProductType T ON P.pId = T.typeId " + 
										   "INNER JOIN CatalogProduct C ON P.pId = C.pId";
				}

				@Override
				public Object createObject(ResultSet rs) throws SQLException 
				{
					int id = rs.getInt(1);
					String productName = rs.getString(2);
					int discount = rs.getInt(3);
					String image = rs.getString(4);
					int typeId = rs.getInt(5);
					String typeDesc = rs.getString(6);
					double price = rs.getDouble(7);
				
					ProductType pType = new ProductType(typeId, typeDesc);
					return new CatalogProduct(id, pType, price, null, null, productName, discount, image);
				}
			});
		}
	
		else if (key.equals(Command.updateCatalogProduct))
		{
			db.performAction(new IUpdate<CatalogProduct>() {

				@Override
				public String getQuery() {
					return "UPDATE product P INNER JOIN ProductType T ON P.pId = T.typeId " + 
											 "INNER JOIN CatalogProduct C ON P.pId = C.pId " + 
							"SET C.productName = ?, C.discount = ?, C.image = ?, T.description = ?, P.price = ? " + 
							"WHERE P.pId = ?";
				}

				@Override
				public void setStatements(PreparedStatement stmt, CatalogProduct obj) throws SQLException
				{
					stmt.setString(1, obj.getName());
					stmt.setInt(2, obj.getSaleDiscountPercent());
					stmt.setString(3, obj.getImgUrl());
					stmt.setString(4, obj.getProductType().getDescription());
					stmt.setDouble(5, obj.getPrice());
					stmt.setInt(6, obj.getId());
				}
				
			});
		}
	}
	
	
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
			System.out.println(String.format("Server has started listening on port: %d" , port));
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}

}
