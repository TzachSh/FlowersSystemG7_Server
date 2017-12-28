package Logic;

import java.awt.Color;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Commons.ProductInOrder;
import Commons.Refund;
import Commons.Status;
import Customers.Customer;
import Orders.Order;

import PacketSender.Packet;
import Products.CatalogProduct;
import Products.Flower;
import Products.FlowerInProduct;
import Products.Product;
import Products.ProductType;
import Users.Permission;
import ocsf.server.ConnectionToClient;

/**
 * 
 *
 * @param <T> The main object type
 */
public class DbGetter {
	private ArrayList<Object> queryResult = new ArrayList<>();
	private DbQuery db;
	private Packet packet;
	private ConnectionToClient client;

	public DbGetter(DbQuery db) {
		this.db = db;
		this.packet = db.getPacket();
		this.client = db.getClient();
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
			
			packet.setParameterList(queryResult);
		} 
		catch (Exception e) {
			packet.setExceptionMessage(e.getMessage());
		}
		finally
		{
			try
			{
<<<<<<< HEAD
				@SuppressWarnings("unchecked")
				T objConverted = (T) queryResult.get(i);

				String qry = objSelect.getQuery();
				PreparedStatement stmt = con.prepareStatement(qry);
				objSelect.setStatements(stmt, objConverted);

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					E obj = objSelect.createObject(rs, objConverted);
					collectionInObject.add(obj);
				}
				
				objSelect.addCollectionToObject(objConverted, collectionInObject);
=======
				client.sendToClient(packet);
>>>>>>> branch 'develop' of https://github.com/TzachSh/FlowersSystemG7_Server
			}
<<<<<<< HEAD

			con.close();
		} 
		catch (Exception e) {
			packet.setExceptionMessage(e.getMessage());
		}
	}
	
	@SuppressWarnings("deprecation")
	public void performAction()
	{
		try 
		{
			if (packet.getResultState())
=======
			catch (IOException e)
>>>>>>> branch 'develop' of https://github.com/TzachSh/FlowersSystemG7_Server
			{
<<<<<<< HEAD
		//		packet.setParameterList(queryResult);
=======
				e.printStackTrace();
>>>>>>> branch 'develop' of https://github.com/TzachSh/FlowersSystemG7_Server
			}
<<<<<<< HEAD
			ArrayList<Object> list = new ArrayList<>();
			list.add(Status.Canceled);
			list.add(new Flower("asag", 5, 5));
			list.add((new CatalogProduct(1, new ProductType(4, "sdgg"), 5.5, new ArrayList<FlowerInProduct>(), new ArrayList<ProductInOrder>(), "sss", 5, null)));
			list.add(new CatalogProduct(2, null, 5.5, null, null, "sss", 5, null));
			packet.setParameterList(list);
			client.sendToClient(packet);		
		}
		catch (IOException e) 
		{
			e.printStackTrace();
=======
>>>>>>> branch 'develop' of https://github.com/TzachSh/FlowersSystemG7_Server
		}
	}

}
