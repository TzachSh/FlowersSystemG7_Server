package Server;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import Branches.Branch;
import Customers.Account;
import Customers.AccountStatus;
import Customers.Customer;
import Customers.Membership;
import Customers.MembershipType;
import Logic.DbGetter;
import Logic.DbQuery;
import Logic.DbUpdater;
import Logic.ISelect;
import Logic.IUpdate;
import PacketSender.Command;
import PacketSender.FileSystem;
import PacketSender.Packet;
import Products.CatalogInBranch;
import Products.CatalogProduct;
import Products.ColorProduct;
import Products.Flower;
import Products.FlowerInProduct;
import Products.Product;
import Products.ProductType;
import Users.Permission;
import Users.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;


public class SystemServer extends AbstractServer{

	public SystemServer(int port) {
		super(port);
		// TODO Auto-generated constructor stub
	}

	//private static final int DEFAULT_PORT = 5555;
	private String user = "root";
	private String password = "aA123456";
	private String database;
	private static final int DEFAULT_PORT = 5555;
	@FXML
	private TextField txtPort;
	@FXML
	private Button btnSubmit;
	@FXML
	private TextArea txtLog;
	@FXML
	private TextField txtDb;
	@FXML
	private TextField txtUser;
	@FXML
	private PasswordField txtPass;
	@FXML
	private Button btnClear;
	int port = 0; // Port to listen on
	public SystemServer() {
		super(DEFAULT_PORT);
	}
	/**
	 * 
	 * @param msg log message to see the information
	 */
	private void printlogMsg(String msg)
	{
		String time=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());//get datetime for log print
		txtLog.setText(time+"---"+msg+"\n\r"+txtLog.getText());
	}
	/**
	 * if button pressed check
	 * the function check if server already listen to port 
	 * if yes then stop to listen
	 *  otherwise start listen and update button text
	 * */
	public void onSubmitClicked(ActionEvent event)
	{
		if(!isListening())//check if not listen
		{
			try {
				port = Integer.parseInt(txtPort.getText()); // Get port from command line
				this.setPort(port);
			} catch (Throwable t) {//if  port is wrong or listening already
				printlogMsg("ERROR - Could not listen for clients from this port! Using default port");
				this.setPort(DEFAULT_PORT);
				return;
			}
		}
			if(changeListening(txtDb.getText(), txtUser.getText(), txtPass.getText()))//check if switch listening is complete
			{
				if(btnSubmit.getText().equals("Start service")) {//if it wasn't listening
					database=txtDb.getText();
					user = txtUser.getText();
					password=txtPass.getText();
					printlogMsg("Server has started listening on port:"+port);//write to log
					btnSubmit.setText("Stop service");//update button
				}
				else//if it was listen
				{
					printlogMsg("Server has finished listening on port:"+port);
					btnSubmit.setText("Start service");///update button
				}
			}
	}
	/***
	 * clear log text area
	 */
	public void onClearClicked(ActionEvent event)
	{
		txtLog.clear();
	}
	public void start(Stage arg0) throws Exception {
		
		String title = "Server";
		String srcFXML = "/Server/App.fxml";
		String srcCSS = "/Server/application.css";
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(srcFXML));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource(srcCSS).toExternalForm());
			arg0.setTitle(title);
			arg0.setScene(scene);
			arg0.show();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		arg0.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				// TODO Auto-generated method stub
				Platform.exit();
			}
		});
	}

	public boolean changeListening(String database,String user, String password) 
	{
		if(!isListening())//if start service has been pressed
		{
			if(database.isEmpty()) {
				JOptionPane.showMessageDialog(null,"Please Fill DataBase name","Error",JOptionPane.ERROR_MESSAGE);
				printlogMsg("database name missing\n\r");
				return false;	
			}
			if(user.isEmpty())
			{
				JOptionPane.showMessageDialog(null,"Please Fill user name","Error",JOptionPane.ERROR_MESSAGE);
				printlogMsg("user name missing");
				return false;
			}
			try {
				DbQuery db = new DbQuery(user, password, database);//check connection to database
				db.connectToDB();
				db.connectionClose();
				listen(); // Start listening for connections
			}
			catch (Exception e) {
				printlogMsg(e.getMessage());
				return false;
			}
		}
		else//if stop service has been pressed
		{
			try {
				stopListening();
				close();
			} catch (IOException e) {
				printlogMsg(e.getMessage());
			}
		}
		return true;
	}
	public void getCatalogProductsHandler(DbQuery db, Command key)
	{	
		DbGetter dbGet = new DbGetter(db, key);
		
		dbGet.performAction(new ISelect() {
			@Override
			public String getQuery() {
				return "SELECT P.pId, C.productName, P.typeId, P.price, C.catPId "
						+ "FROM product P INNER JOIN CatalogProduct C ON P.pId = C.pId";
			}

			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				int id = rs.getInt(1);
				String productName = rs.getString(2);
			
				int typeId = rs.getInt(3);
				double price = rs.getDouble(4);
				
				int catPid = rs.getInt(5);
				
				CatalogProduct catalogPro = new CatalogProduct(id, catPid, typeId, price, null, null, productName, "");

				return (Object)catalogPro;
			}

			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { }
		});
	}
	
	public void getFlowersInProductHandler(DbQuery db, Command key)
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
	
	private void createFlower(DbQuery db, Command key) {
		DbUpdater<Flower> dbUpdate = new DbUpdater<>(db, key);
		
		dbUpdate.performAction(new IUpdate<Flower>() {
			@Override
			public String getQuery() {
				return "Insert flower(flower,price,colId) values(?,?,?)";
			}
			@Override
			public void setStatements(PreparedStatement stmt, Flower obj) throws SQLException {
				stmt.setString(1, obj.getName());
				stmt.setDouble(2, obj.getPrice());
				stmt.setInt(3, obj.getColor());
		}
});

}

	private void getColors(DbQuery db, Command key) {
	DbGetter dbGet = new DbGetter(db, key);
		
		dbGet.performAction(new ISelect() {
			@Override
			public String getQuery() {
				return "SELECT * from  Color";
			}

			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				int colId =rs.getInt(1);
				String color = rs.getString(2);
				
				return (Object)new ColorProduct(colId,color);
			}

			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { }
		});
		
	}

		
	private void getDiscounts(DbQuery db,Command key) {
		DbGetter dbGet = new DbGetter(db, key);
		
		dbGet.performAction(new ISelect() {
			@Override
			public String getQuery() {
				return "SELECT catPId,discount,brId from  CatalogInBranch where brId=?";
			}
			
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				int catPId =rs.getInt(1);
				int discount = rs.getInt(2);
				int branch=rs.getInt(3);
				return (Object)new CatalogInBranch(branch, catPId, discount);
			}

			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { 
				stmt.setInt(1, (int) packet.getParameterForCommand(Command.getDiscountsByBranch).get(0));
			}
		});
	}
	
	public void getFlowersHandler(DbQuery db, Command key)
	{	
		DbGetter dbGet = new DbGetter(db, key);
		
		dbGet.performAction(new ISelect() {
			@Override
			public String getQuery() {
				return "SELECT fId, flower, price, colId FROM flower";
			}

			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				int fId = rs.getInt(1);
				String flowerName = rs.getString(2);
				double price = rs.getDouble(3);
				int color = rs.getInt(4);
			
				Flower flower = new Flower(fId, flowerName, price, color);
				return (Object)flower;
			}

			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { }
		});
	}
	
	public void getProductTypesHandler(DbQuery db, Command key)
	{	
		DbGetter dbGet = new DbGetter(db, key);
		
		dbGet.performAction(new ISelect() {
			@Override
			public String getQuery() {
				return "SELECT typeId, description FROM producttype";
			}

			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				int id = rs.getInt(1);
				String desc = rs.getString(2);
				
				ProductType type = new ProductType(id, desc);
				return (Object)type;
			}

			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { }
		});
	}
	
	public void getCatalogProductsByNameHandler(DbQuery db, Command key)
	{	
		DbGetter dbGet = new DbGetter(db, key);
		
		dbGet.performAction(new ISelect() {
			@Override
			public String getQuery() {
				return "SELECT P.pId, C.productName, T.typeId, T.description, P.price, C.catPId "
						+ "FROM product P INNER JOIN ProductType T ON P.pId = T.typeId "
						+ "INNER JOIN CatalogProduct C ON P.pId = C.pId"
						+ "WHERE C.productName = ?";
			}

			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				int id = rs.getInt(1);
				String productName = rs.getString(2);
			
				int typeId = rs.getInt(3);
				double price = rs.getDouble(5);
				
				int catPid = rs.getInt(6);
				
				CatalogProduct catalogPro = new CatalogProduct(id, catPid, typeId, price, null, null, productName, "");
				return (Object)catalogPro;
			}

			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException
			{ 
				CatalogProduct pro = (CatalogProduct)packet.getParameterForCommand(key).get(0);
				stmt.setString(1, pro.getName());
				
			}
		});
	}
	
	public void getCatalogImageHandler(DbQuery db,  Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		
		dbGet.performAction(new ISelect() {
			@Override
			public String getQuery() {
				return "SELECT image FROM catalogproduct;";
			}

			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				
				String imagePath = rs.getString(1);
				FileSystem file = new FileSystem(imagePath);
				return file;
			}

			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { }
		});
	}
	
	public void getBranchesHandler(DbQuery db,  Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		
		dbGet.performAction(new ISelect() {
			@Override
			public String getQuery() {
				return "SELECT brId, brName FROM branch;";
			}

			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				int brId = rs.getInt(1);
				String brName = rs.getString(2);
				return new Branch(brId, brName);
			}

			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { }
		});
	}
	
	public void getBranchSalesHandler(DbQuery db,  Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		
		dbGet.performAction(new ISelect() {
			@Override
			public String getQuery() {
				return "SELECT brId, catPId, discount FROM cataloginbranch WHERE brId = ?;";
			}

			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				int brId = rs.getInt(1);
				int catPid = rs.getInt(2);
				int discount = rs.getInt(3);
				
				return new CatalogInBranch(brId, catPid, discount);
			}

			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				int brId = (int)packet.getParameterForCommand(key).get(0);
				stmt.setInt(1, brId);
			}
		});
	}
	
	public void updateImageInProductHandler(DbQuery db,  Command key)
	{
		DbUpdater<FileSystem> dbUpdate = new DbUpdater<>(db, key);
		
		dbUpdate.performAction(new IUpdate<FileSystem>() {

			@Override
			public String getQuery() {
				return "UPDATE catalogproduct SET image = ? WHERE pId = ?;";
			}

			@Override
			public void setStatements(PreparedStatement stmt, FileSystem obj) throws SQLException {
				stmt.setString(1, obj.getServerFilePath());
				stmt.setInt(2, obj.getProductId());
				
				try
				{
					obj.saveImageOnServer();
				}
				catch (IOException e)
				{
					throw new SQLException(e);
				}
			}
		});
	}
	
	public void updateProductHandler(DbQuery db,  Command key)
	{
		DbUpdater<Product> dbUpdate = new DbUpdater<>(db, key);
	
		dbUpdate.performAction(new IUpdate<Product>() {

			@Override
			public String getQuery() {
				return "UPDATE product SET typeId = ?, price = ? WHERE pId = ?";
			}

			@Override
			public void setStatements(PreparedStatement stmt, Product obj) throws SQLException {
				stmt.setInt(1, obj.getProductTypeId());
				stmt.setDouble(2, obj.getPrice());
				stmt.setInt(3, obj.getId());
			}
		});
	}
	
	/*
	//getting all users
	public void getUsersHandler(DbQuery db, Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
		@Override
		public String getQuery()
		{
			return "SELECT * FROM User";
		}

		@Override
		public Object createObject(ResultSet rs) throws SQLException 
		{
			int uId = rs.getInt(1);
			String user =rs.getString(2);
			String password = rs.getString(3);
			int islogged = rs.getInt(4);
			String perm=rs.getString(5);
			Permission permission = null;
			boolean isloggedbool=(islogged==1);
			User newuser;
			if(perm.equals((Permission.Administrator).toString()))
			permission= Permission.Administrator;
			else if(perm.equals((Permission.Blocked).toString()))
			permission= Permission.Blocked;
			else if(perm.equals((Permission.Limited).toString()))
			permission= Permission.Limited;
		
			newuser=new User(uId, user, password, isloggedbool, permission);
			return (Object)newuser;
		}
	
		@Override
		public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { }
		}
			
		);
	}
	
	
	//getting Customer by User ID
	public void getCustomersKeyByuIdHandler(DbQuery db, Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
		@Override
		public String getQuery() {
		return "SELECT * FROM customer where uId=?";
	}

	@Override
	public Object createObject(ResultSet rs) throws SQLException {
		int cId = rs.getInt(1);
		int uId=rs.getInt(2);
		int mId=rs.getInt(3);
		Customer cus;
		cus=new Customer(cId, uId, mId);
		return (Object)cus;
	}

	@Override
	public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { 
		Customer cus = (Customer) packet.getParameterForCommand(Command.getCustomersKeyByuId).get(0);
		stmt.setInt(1, cus.getuId());
		}
	});
}

	//getting user by uId
	public void getUserByuIdHandler(DbQuery db, Command key)
	{
		
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
			
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				// TODO Auto-generated method stub
				User user = (User) packet.getParameterForCommand(Command.getUserByuId).get(0);
				stmt.setInt(1, user.getuId());
			}
			
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "SELECT * "+ "FROM User u where uId=?";
			}
			
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				int uId = rs.getInt(1);
				String user =rs.getString(2);
				String password = rs.getString(3);
				int islogged = rs.getInt(4);
				String perm=rs.getString(5);
				Permission permission = null;
				boolean isloggedbool=(islogged==1);
				User newuser;
				if(perm.equals((Permission.Administrator).toString()))
				permission= Permission.Administrator;
				else if(perm.equals((Permission.Blocked).toString()))
				permission= Permission.Blocked;
				else if(perm.equals((Permission.Limited).toString()))
				permission= Permission.Limited;

				newuser=new User(uId, user, password, isloggedbool, permission);
			return (Object)newuser;
			}
		});
		
	}
		
	//updating Account
	public void updateAccountbycID(DbQuery db, Command key)
	{
		DbUpdater<Account> dbUpdate = new DbUpdater<>(db, key);
	}
	//getting user by User Name 
	public void getUserByUserNameHandler(DbQuery db, Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
		@Override
		public String getQuery() {
		return "SELECT * "+ "FROM User u where user=?";
	}

	@Override
	public Object createObject(ResultSet rs) throws SQLException {
		int uId = rs.getInt(1);
		String user =rs.getString(2);
		String password = rs.getString(3);
		int islogged = rs.getInt(4);
		String perm=rs.getString(5);
		Permission permission = null;
		boolean isloggedbool=(islogged==1);
		User newuser;
		if(perm.equals((Permission.Administrator).toString()))
		permission= Permission.Administrator;
		else if(perm.equals((Permission.Blocked).toString()))
		permission= Permission.Blocked;
		else if(perm.equals((Permission.Limited).toString()))
		permission= Permission.Limited;

		newuser=new User(uId, user, password, isloggedbool, permission);
	return (Object)newuser;
	}

	@Override
	public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { 
		User user = (User) packet.getParameterForCommand(Command.getUsersByUserName).get(0);
		stmt.setString(1, user.getUser());
		}
	});
	}
	
	//adding user 
	public void addUserHandler(DbQuery db, Command key)
	{
		DbUpdater<User> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<User>() {
	
		@Override
		public String getQuery() {
		// TODO Auto-generated method stub
		return "insert into User(uId,user,password,isLogged,permission) values(?,?,?,?,?)";
	
		}
	
		@Override
		public void setStatements(PreparedStatement stmt, User obj) throws SQLException {
		// TODO Auto-generated method stub
		int islog=0;
		stmt.setInt(1, obj.getuId());
		stmt.setString(2, obj.getUser());
		stmt.setString(3, obj.getPassword());
		if(obj.isLogged()==true)
		islog=1;
		stmt.setInt(4, islog);
		stmt.setString(5, obj.getPermission().toString());
		}
		});
	}
	
	
	public void getAccountBycIdHandler(DbQuery db, Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
			
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				// TODO Auto-generated method stub
				Account acc = (Account) packet.getParameterForCommand(Command.getAccountBycId).get(0);
				stmt.setInt(1, acc.getCustomerId());
			}
			
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "select * from account where cId=?";
			}
			
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				int acNum = rs.getInt(1);
				int brId =rs.getInt(2);
				int cId = rs.getInt(3);
				int Balance=rs.getInt(4);
				String Creditcard=rs.getString(5);
				String Statusstring=rs.getString(6);
				AccountStatus status = AccountStatus.Active;
				
				Account newacc;
				if(Statusstring.equals((AccountStatus.Active).toString()))
					status= AccountStatus.Active;
				else if(Statusstring.equals((AccountStatus.Blocked).toString()))
					status= AccountStatus.Blocked;
				else if(Statusstring.equals((AccountStatus.Closed).toString()))
					status= AccountStatus.Closed;
				

				newacc=new Account(acNum, cId, brId, Balance, status, Creditcard);
			return (Object)newacc;
				
			}
		});
	}

	//adding customer 
	public void addCustomertHandler(DbQuery db, Command key)
	{
		DbUpdater<Customer> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<Customer>() {
	
		@Override
		public String getQuery() {
		// TODO Auto-generated method stub
			return "INSERT into Customer (uId,mId) values(?,?)";
	
		}
	
		@Override
		public void setStatements(PreparedStatement stmt, Customer obj) throws SQLException {
		// TODO Auto-generated method stub
			stmt.setInt(1, obj.getuId());
			stmt.setInt(2, obj.getMembershipId()); 
		}
	});
	}
	
	//adding Accout
	public void addAccountrHandler(DbQuery db, Command key)
	{
		DbUpdater<Account> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<Account>() {

			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "INSERT into Account (brId,cId,balance,creditCard,status) values(?,?,?,?,?)";
				
			}

			@Override
			public void setStatements(PreparedStatement stmt, Account obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setInt(1, obj.getBranchId());
				stmt.setInt(2, obj.getCustomerId());
				stmt.setInt(3, obj.getBalance());
				stmt.setString(4, obj.getCreditCard());
				stmt.setString(5, obj.getAccountStatus().toString());
			}
			
			
		});
		}
			
	//getting all MemberShip
	public void getMemberShipHandler(DbQuery db, Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
		@Override
		public String getQuery() {
		return "SELECT * " + 
		"FROM MemberShip";
	}

	@Override
	public Object createObject(ResultSet rs) throws SQLException {
		int mId = rs.getInt(1);
		String memship =rs.getString(2);
		Double discount = rs.getDouble(3);
		MembershipType memtype;
		Membership newmemship;
	
		if(memship.equals((MembershipType.Normal).toString()))
		memtype= MembershipType.Normal;
		else if(memship.equals((MembershipType.Monthly).toString()))
		memtype= MembershipType.Monthly;
		else
		memtype= MembershipType.Yearly;

	newmemship=new Membership(mId, memtype, discount);
	return (Object)newmemship;
	}

	@Override
	public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { }
	});
	}
	
	
	//update user after updating by uID
	public void updateUserByuIdHandler(DbQuery db,  Command key)
	{
		DbUpdater<User> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<User>() {

			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "update user set user=?,password=? where uId=?";
			
			}
			@Override
			public void setStatements(PreparedStatement stmt, User obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setString(1,obj.getUser());
				stmt.setString(2,obj.getPassword());
				stmt.setInt(3,obj.getuId());
			}
			
		});
	}
	
	
	//updating Customer after updating by cId
	public void updateCustomerByuIdHandler(DbQuery db,  Command key)
	{
		DbUpdater<Customer> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<Customer>() {

			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "update customer set mId=? where uId=?";
			}

			@Override
			public void setStatements(PreparedStatement stmt, Customer obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setInt(1,obj.getMembershipId());
				stmt.setInt(2,obj.getuId());
			}
		});
	}
	

	//updating account after updating by cId
	public void updateAccountsBycIdHandler(DbQuery db,  Command key)
	{
		DbUpdater<Account> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<Account>() {

			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "update Account set creditCard=?,status=? where cId=?";
			}

			@Override
			public void setStatements(PreparedStatement stmt, Account obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setString(1,obj.getCreditCard());
				stmt.setString(2,obj.getAccountStatus().toString());
				stmt.setInt(3,obj.getCustomerId());
			}
		});
	}
	
		*/
	
	
	
	//Elias @@@@@@@@@@@@@@@@@@@@
	public void updateCatalogProductHandler(DbQuery db,  Command key)
	{
		DbUpdater<CatalogProduct> dbUpdate = new DbUpdater<>(db, key);
	
		dbUpdate.performAction(new IUpdate<CatalogProduct>() {

			@Override
			public String getQuery() {
				return "UPDATE catalogproduct SET productName = ? WHERE pId = ?";
			}

			@Override
			public void setStatements(PreparedStatement stmt, CatalogProduct obj) throws SQLException {
				stmt.setString(1, obj.getName());
				stmt.setInt(2, obj.getId());
			}
		});
	}

	
	public void deleteFlowersInProductHandler(DbQuery db,  Command key)
	{
		DbUpdater<CatalogProduct> dbUpdate = new DbUpdater<>(db, key);
	
		dbUpdate.performAction(new IUpdate<CatalogProduct>() {
			@Override
			public String getQuery() {
				return "DELETE FROM flowerinproduct WHERE pId = ?;";
			}

			@Override
			public void setStatements(PreparedStatement stmt, CatalogProduct obj) throws SQLException {
				stmt.setInt(1, obj.getId());
			}
		});
	}
	
	public void updateFlowersInProductHandler(DbQuery db,  Command key)
	{
		DbUpdater<FlowerInProduct> dbUpdate = new DbUpdater<>(db, key);
	
		dbUpdate.performAction(new IUpdate<FlowerInProduct>() {

			@Override
			public String getQuery() {
				return "INSERT INTO flowerinproduct (fId, pId, quantity) VALUES (?, ?, ?);";
			}

			@Override
			public void setStatements(PreparedStatement stmt, FlowerInProduct obj) throws SQLException {
				stmt.setInt(1, obj.getFlowerId());
				stmt.setInt(2, obj.getProductId());
				stmt.setInt(3, obj.getQuantity());
			}
		});
	}
	
	
	/**
	 * Insert a catalog product
	 */
	public void insertCatalogProductHandler(DbQuery db,  Command key)
	{
		Packet packet = db.getPacket();
		try {
			// get all parameters
			CatalogProduct pro = (CatalogProduct)packet.getParameterForCommand(Command.insertCatalogProduct).get(0);
			FileSystem catalogImage = (FileSystem)packet.getParameterForCommand(Command.updateCatalogImage).get(0);
			ArrayList<FlowerInProduct> fpList = packet.<FlowerInProduct>convertedResultListForCommand(Command.insertFlowersInProduct);
	
			// prepare the values string for query
			String formatSql = "";
			for (int i = 0; i < fpList.size(); i++)
			{
				FlowerInProduct fp = fpList.get(i);
				formatSql += String.format("(%d, @pId, %d)", fp.getFlowerId(), fp.getQuantity());
				if (i != fpList.size() - 1)
					formatSql += ",";
			}
			
			

			// create the connection to database
			db.connectToDB();
			Connection con = db.getConnection();
			
			String query = "INSERT INTO product (typeId, price) VALUES (?, ?); " + 
	    				"SET @pId = LAST_INSERT_ID(); " + 
	    				"INSERT INTO catalogproduct (pId, productName) VALUES (@pId, ?); " +
	    				"INSERT INTO flowerinproduct (fId, pId, quantity) VALUES " + formatSql + ";";
	    	
	 
	    	// set the statements from the implemention
	    	PreparedStatement stmt = con.prepareStatement(query);
	    	stmt.setInt(1, pro.getProductTypeId());
			stmt.setDouble(2, pro.getPrice());
			stmt.setString(3, pro.getName());
	    	stmt.executeUpdate();
	    
	    	
	    	// get the new product id
	    	String queryPid = "SELECT P.pId FROM product P " + 
	    			"INNER JOIN CatalogProduct C ON P.pId = C.pId " + 
	    			"WHERE C.productName = ?";
	    	
	    	// set the statements from the implemention
	    	PreparedStatement stmtPid = con.prepareStatement(queryPid);
	    	stmtPid.setString(1, pro.getName());
	    	ResultSet rs = stmtPid.executeQuery();
			while (rs.next()) {
				pro.setId(rs.getInt(1));
			}
	    	
			con.close();
			
			// link the new product id to image
		    catalogImage.setProductId(pro.getId());
		}
		catch (Exception e)
		{
			packet.setExceptionMessage(e.getMessage());
		}
	}
	
	// *** Elias ******
	 //getting all users
	public void getUsersHandler(DbQuery db, Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
		@Override
		public String getQuery()
		{
			return "SELECT * FROM User";
		}

		@Override
		public Object createObject(ResultSet rs) throws SQLException 
		{
			int uId = rs.getInt(1);
			String user =rs.getString(2);
			String password = rs.getString(3);
			int islogged = rs.getInt(4);
			String perm=rs.getString(5);
			Permission permission = null;
			boolean isloggedbool=(islogged==1);
			User newuser;
			if(perm.equals((Permission.Administrator).toString()))
			permission= Permission.Administrator;
			else if(perm.equals((Permission.Blocked).toString()))
			permission= Permission.Blocked;
			else if(perm.equals((Permission.Limited).toString()))
			permission= Permission.Limited;
		
			newuser=new User(uId, user, password, isloggedbool, permission);
			return (Object)newuser;
		}
	
		@Override
		public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { }
		}
			
		);
	}
	//getting Customer by User ID
	public void getCustomersKeyByuIdHandler(DbQuery db, Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
		@Override
		public String getQuery() {
		return "SELECT * FROM customer where uId=?";
	}

	@Override
	public Object createObject(ResultSet rs) throws SQLException {
		int cId = rs.getInt(1);
		int uId=rs.getInt(2);
		int mId=rs.getInt(3);
		Customer cus;
		cus=new Customer(cId, uId, mId);
		return (Object)cus;
	}

	@Override
	public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { 
		Customer cus = (Customer) packet.getParameterForCommand(Command.getCustomersKeyByuId).get(0);
		stmt.setInt(1, cus.getuId());
		}
	});
}
	
	//getting user by uId
	public void getUserByuIdHandler(DbQuery db, Command key)
	{
		
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
			
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				// TODO Auto-generated method stub
				User user = (User) packet.getParameterForCommand(Command.getUserByuId).get(0);
				stmt.setInt(1, user.getuId());
			}
			
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "SELECT * "+ "FROM User u where uId=?";
			}
			
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				int uId = rs.getInt(1);
				String user =rs.getString(2);
				String password = rs.getString(3);
				int islogged = rs.getInt(4);
				String perm=rs.getString(5);
				Permission permission = null;
				boolean isloggedbool=(islogged==1);
				User newuser;
				if(perm.equals((Permission.Administrator).toString()))
				permission= Permission.Administrator;
				else if(perm.equals((Permission.Blocked).toString()))
				permission= Permission.Blocked;
				else if(perm.equals((Permission.Limited).toString()))
				permission= Permission.Limited;

				newuser=new User(uId, user, password, isloggedbool, permission);
			return (Object)newuser;
			}
		});
		
	}
	//updating Account
	public void updateAccountbycID(DbQuery db, Command key)
	{
		DbUpdater<Account> dbUpdate = new DbUpdater<>(db, key);
	}
	//getting user by User Name 
	public void getUserByUserNameHandler(DbQuery db, Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
		@Override
		public String getQuery() {
		return "SELECT * "+ "FROM User u where user=?";
	}

	@Override
	public Object createObject(ResultSet rs) throws SQLException {
		int uId = rs.getInt(1);
		String user =rs.getString(2);
		String password = rs.getString(3);
		int islogged = rs.getInt(4);
		String perm=rs.getString(5);
		Permission permission = null;
		boolean isloggedbool=(islogged==1);
		User newuser;
		if(perm.equals((Permission.Administrator).toString()))
		permission= Permission.Administrator;
		else if(perm.equals((Permission.Blocked).toString()))
		permission= Permission.Blocked;
		else if(perm.equals((Permission.Limited).toString()))
		permission= Permission.Limited;

		newuser=new User(uId, user, password, isloggedbool, permission);
	return (Object)newuser;
	}

	@Override
	public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { 
		User user = (User) packet.getParameterForCommand(Command.getUsersByUserName).get(0);
		stmt.setString(1, user.getUser());
		}
	});
	}
	//adding user 
	public void addUserHandler(DbQuery db, Command key)
	{
		DbUpdater<User> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<User>() {
	
		@Override
		public String getQuery() {
		// TODO Auto-generated method stub
		return "insert into User(uId,user,password,isLogged,permission) values(?,?,?,?,?)";
	
		}
	
		@Override
		public void setStatements(PreparedStatement stmt, User obj) throws SQLException {
		// TODO Auto-generated method stub
		int islog=0;
		stmt.setInt(1, obj.getuId());
		stmt.setString(2, obj.getUser());
		stmt.setString(3, obj.getPassword());
		if(obj.isLogged()==true)
		islog=1;
		stmt.setInt(4, islog);
		stmt.setString(5, obj.getPermission().toString());
		}
		});
	}
	
	
	public void getAccountBycIdHandler(DbQuery db, Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
			
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				// TODO Auto-generated method stub
				Account acc = (Account) packet.getParameterForCommand(Command.getAccountbycID).get(0);
				stmt.setInt(1, acc.getCustomerId());
			}
			
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "select * from account where cId=?";
			}
			
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				int acNum = rs.getInt(1);
				int brId =rs.getInt(2);
				int cId = rs.getInt(3);
				int Balance=rs.getInt(4);
				String Creditcard=rs.getString(5);
				String Statusstring=rs.getString(6);
				AccountStatus status = AccountStatus.Active;
				
				Account newacc;
				if(Statusstring.equals((AccountStatus.Active).toString()))
					status= AccountStatus.Active;
				else if(Statusstring.equals((AccountStatus.Blocked).toString()))
					status= AccountStatus.Blocked;
				else if(Statusstring.equals((AccountStatus.Closed).toString()))
					status= AccountStatus.Closed;
				

				newacc=new Account(acNum, cId, brId, Balance, status, Creditcard);
			return (Object)newacc;
				
			}
		});
	}
	
	//adding customer 
	public void addCustomertHandler(DbQuery db, Command key)
	{
		DbUpdater<Customer> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<Customer>() {
	
		@Override
		public String getQuery() {
		// TODO Auto-generated method stub
			return "INSERT into Customer (uId,mId) values(?,?)";
	
		}
	
		@Override
		public void setStatements(PreparedStatement stmt, Customer obj) throws SQLException {
		// TODO Auto-generated method stub
			stmt.setInt(1, obj.getuId());
			stmt.setInt(2, obj.getMembershipId()); 
		}
	});
	}
	
	//adding Accout
	public void addAccountrHandler(DbQuery db, Command key)
	{
		DbUpdater<Account> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<Account>() {

			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "INSERT into Account (brId,cId,balance,creditCard,status) values(?,?,?,?,?)";
				
			}

			@Override
			public void setStatements(PreparedStatement stmt, Account obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setInt(1, obj.getBranchId());
				stmt.setInt(2, obj.getCustomerId());
				stmt.setInt(3, obj.getBalance());
				stmt.setString(4, obj.getCreditCard());
				stmt.setString(5, obj.getAccountStatus().toString());
			}
			
			
		});
		}
	//getting all MemberShip
	public void getMemberShipHandler(DbQuery db, Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
		@Override
		public String getQuery() {
		return "SELECT * " + 
		"FROM MemberShip";
	}

	@Override
	public Object createObject(ResultSet rs) throws SQLException {
		int mId = rs.getInt(1);
		String memship =rs.getString(2);
		Double discount = rs.getDouble(3);
		MembershipType memtype;
		Membership newmemship;
	
		if(memship.equals((MembershipType.Normal).toString()))
		memtype= MembershipType.Normal;
		else if(memship.equals((MembershipType.Monthly).toString()))
		memtype= MembershipType.Monthly;
		else
		memtype= MembershipType.Yearly;

	newmemship=new Membership(mId, memtype, discount);
	return (Object)newmemship;
	}

	@Override
	public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { }
	});
	}
	//update user after updating by uID
	public void updateUserByuIdHandler(DbQuery db,  Command key)
	{
		DbUpdater<User> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<User>() {

			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "update user set user=?,password=? where uId=?";
			
			}
			@Override
			public void setStatements(PreparedStatement stmt, User obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setString(1,obj.getUser());
				stmt.setString(2,obj.getPassword());
				stmt.setInt(3,obj.getuId());
			}
			
		});
	}
	//updating Customer after updating by cId
	public void updateCustomerByuIdHandler(DbQuery db,  Command key)
	{
		DbUpdater<Customer> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<Customer>() {

			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "update customer set mId=? where uId=?";
			}

			@Override
			public void setStatements(PreparedStatement stmt, Customer obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setInt(1,obj.getMembershipId());
				stmt.setInt(2,obj.getuId());
			}
		});
	}
	//updating account after updating by cId
	public void updateAccountsBycIdHandler(DbQuery db,  Command key)
	{
		DbUpdater<Account> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<Account>() {

			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "update Account set creditCard=?,status=? where cId=?";
			}

			@Override
			public void setStatements(PreparedStatement stmt, Account obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setString(1,obj.getCreditCard());
				stmt.setString(2,obj.getAccountStatus().toString());
				stmt.setInt(3,obj.getCustomerId());
			}
		});
	}
	
	// *****

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Packet packet = (Packet) msg;
		printlogMsg("from: "+client+" commands: "+packet.getCommands());
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
				else if(key.equals(Command.getMemberShip))
					getMemberShipHandler(db, key);
				else if(key.equals(Command.getUsers))
					getUsersHandler(db, key);
				else if(key.equals(Command.addCustomers))
					addCustomertHandler(db,key);
				else if(key.equals(Command.addUsers))
					addUserHandler(db, key);
				else if(key.equals(Command.getUsersByUserName))
					getUserByUserNameHandler(db, key);
				else if(key.equals(Command.addAccounts))
					addAccountrHandler(db, key);
				else if(key.equals(Command.getCustomersKeyByuId))
					getCustomersKeyByuIdHandler(db, key);
				else if(key.equals(Command.getUserByuId))
					getUserByuIdHandler(db, key);
				else if(key.equals(Command.getAccountBycId))
					getAccountBycIdHandler(db, key);
				else if(key.equals(Command.updateUserByuId))
					updateUserByuIdHandler(db, key);
				else if(key.equals(Command.updateCustomerByuId))
					updateCustomerByuIdHandler(db, key);
				else if(key.equals(Command.updateAccountsBycId))
					updateAccountsBycIdHandler(db,key);
				else if(key.equals(Command.getColors))
					getColors(db,key);
				else if(key.equals(Command.addFlower))
					createFlower(db,key);
				else if(key.equals(Command.getDiscountsByBranch))
					getDiscounts(db,key);
				
			
				else if (key.equals(Command.updateProduct)) {
					updateProductHandler(db, key);
				}
			
				else if (key.equals(Command.deleteFlowersInProduct)) {
					deleteFlowersInProductHandler(db, key);
				}
				
				else if (key.equals(Command.getFlowersInProducts)) {
					getFlowersInProductHandler(db, key);
				}
				
				else if (key.equals(Command.getProductTypes)) {
					getProductTypesHandler(db, key);
				}
				
				else if (key.equals(Command.insertCatalogProduct)) {
					insertCatalogProductHandler(db, key);
				}
				
				else if (key.equals(Command.updateFlowersInProduct)){
					updateFlowersInProductHandler(db, key);
				}
				
				else if (key.equals(Command.updateCatalogImage)){
					updateImageInProductHandler(db, key);
				}
				
				else if (key.equals(Command.getCatalogImage)) {
					getCatalogImageHandler(db, key);
				}
				
				else if (key.equals(Command.getBranches)) {
					getBranchesHandler(db, key);
				}
				
				else if (key.equals(Command.getBranchSales)) {
					getBranchSalesHandler(db, key);
				}
				
				else if(key.equals(Command.getMemberShip))
					getMemberShipHandler(db, key);
				else if(key.equals(Command.getUsers))
					getUsersHandler(db, key);
				else if(key.equals(Command.addCustomers))
					addCustomertHandler(db,key);
				else if(key.equals(Command.addUsers))
					addUserHandler(db, key);
				else if(key.equals(Command.getUsersByUserName))
					getUserByUserNameHandler(db, key);
				else if(key.equals(Command.addAccounts))
					addAccountrHandler(db, key);
				else if(key.equals(Command.getCustomersKeyByuId))
					getCustomersKeyByuIdHandler(db, key);
				else if(key.equals(Command.getUserByuId))
					getUserByuIdHandler(db, key);
				else if(key.equals(Command.getAccountbycID))
					getAccountBycIdHandler(db, key);
				else if(key.equals(Command.updateUserByuId))
					updateUserByuIdHandler(db, key);
				else if(key.equals(Command.updateCustomerByuId))
					updateCustomerByuIdHandler(db, key);
				else if(key.equals(Command.updateAccountsBycId))
					updateAccountsBycIdHandler(db,key);
				
			}

			db.connectionClose();
		}
		catch (Exception e) {
			printlogMsg(e.getMessage());
			packet.setExceptionMessage(e.getMessage());
		}
		finally {
			try {
				db.sendToClient();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				printlogMsg(e.getMessage());
			}
		}
	}

}
