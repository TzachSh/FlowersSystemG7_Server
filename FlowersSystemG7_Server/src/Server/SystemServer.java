package Server;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import Logic.DbGetter;
import Logic.DbQuery;
import Logic.DbUpdater;
import Logic.ISelect;
import Logic.ISelectCollection;
import Logic.IUpdate;

import PacketSender.Command;
import PacketSender.Packet;
import Products.CatalogProduct;
import Products.Flower;
import Products.FlowerInProduct;
import Products.Product;
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

	public void getCatalogProductsHandler(DbQuery db)
	{	
		DbGetter<CatalogProduct> dbGet = new DbGetter<>(db);
		
		dbGet.setMainGetter(new ISelect() {
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
				//String image = rs.getString(4);
				int typeId = rs.getInt(5);
				String typeDesc = rs.getString(6);
				double price = rs.getDouble(7);

				ProductType pType = new ProductType(typeId, typeDesc);
				CatalogProduct catalogPro = new CatalogProduct(id, pType, price, null, null, productName, discount, "");
				return (Object)catalogPro;
			}

			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { }
		});
		
		
		// each product has a collection of flowers
		// so, register the flower collection to each product
		dbGet.<FlowerInProduct>setCollectionInObject(new ISelectCollection<CatalogProduct, FlowerInProduct>() {

			@Override
			public String getQuery() 
			{
				return "SELECT FP.flower,FP.quantity,F.price,F.color FROM flowerinproduct FP INNER JOIN flower F ON FP.flower=F.flower WHERE FP.pId=?";
			}

			@Override
			public void setStatements(PreparedStatement stmt, CatalogProduct obj) throws SQLException
			{
				stmt.setInt(1, obj.getId());
			}

			@Override
			public FlowerInProduct createObject(ResultSet rs, CatalogProduct mainObj) throws SQLException
			{
				Product pro = (Product)mainObj;
				
				String name = rs.getString(1);
				double price = rs.getDouble(3);
				int color = rs.getInt(4);
				int qty = rs.getInt(2);
				
				Flower flower = new Flower(name, price, color);
				FlowerInProduct flowerInProduct = new FlowerInProduct(flower, pro, qty);
				return flowerInProduct;
			}

			@Override
			public void addCollectionToObject(CatalogProduct obj, ArrayList<FlowerInProduct> objList)
			{
				obj.setFlowerInProductList(objList);
			}
		});
		
		dbGet.performAction();
	}
	
	public void updateCatalogProductHandler(DbQuery db)
	{
		DbUpdater<CatalogProduct> dbUpdate = new DbUpdater<>(db);
	
		dbUpdate.performAction(new IUpdate<CatalogProduct>() {

			@Override
			public String getQuery() {
				return "UPDATE product P INNER JOIN ProductType T ON P.pId = T.typeId "
						+ "INNER JOIN CatalogProduct C ON P.pId = C.pId "
						+ "SET C.productName = ?, C.discount = ?, C.image = ?, T.description = ?, P.price = ? "
						+ "WHERE P.pId = ?";
			}

			@Override
			public void setStatements(PreparedStatement stmt, CatalogProduct obj) throws SQLException {
				stmt.setString(1, obj.getName());
				stmt.setInt(2, obj.getSaleDiscountPercent());
				stmt.setString(3, obj.getImgUrl());
				stmt.setString(4, obj.getProductType().getDescription());
				stmt.setDouble(5, obj.getPrice());
				stmt.setInt(6, obj.getId());
			}
		});
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Packet packet = (Packet) msg;
		DbQuery db = new DbQuery(user, password, packet, client);
		Command key = packet.getmsgKey();

		if (key.equals(Command.getCatalogProducts)) {
			getCatalogProductsHandler(db);
		}

		else if (key.equals(Command.updateCatalogProduct)) {
			updateCatalogProductHandler(db);
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
			System.out.println(String.format("Server has started listening on port: %d", port));
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}

}
