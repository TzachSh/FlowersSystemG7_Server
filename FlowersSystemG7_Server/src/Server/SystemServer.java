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
import Branches.Employee;
import Branches.Role;
import Commons.Refund;
import Customers.Account;
import Customers.AccountStatus;
import Customers.Complain;
import Customers.Customer;
import Customers.Membership;
import Customers.MembershipType;
import Customers.Reply;
import Logic.DbGetter;
import Logic.DbQuery;
import Logic.DbUpdater;
import Logic.ISelect;
import Logic.IUpdate;
import Orders.Order;
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
import Survey.AnswerSurvey;
import Survey.Question;
import Survey.Survey;
import Survey.SurveyConclusion;
import Survey.SurveyQuestion;
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
	private String password = "1q2w3e!";
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
	
	/**
	 * Get all active catalog products
	 */
	public void getActiveCatalogProductsHandler(DbQuery db, Command key)
	{	
		DbGetter dbGet = new DbGetter(db, key);
		
		dbGet.performAction(new ISelect() {
			@Override
			public String getQuery() {
				return "SELECT P.pId, C.productName, P.typeId, P.price, C.catPId "
						+ "FROM product P INNER JOIN CatalogProduct C ON P.pId = C.pId "
						+ "WHERE P.isActive=1";
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
	
	/**
	 * Get all catalog products ; active and not active
	 */
	public void getAllCatalogProductsHandler(DbQuery db, Command key)
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
	
	public void setProductAsDeletedHandler(DbQuery db,  Command key)
	{
		DbUpdater<Product> dbUpdate = new DbUpdater<>(db, key);
	
		dbUpdate.performAction(new IUpdate<Product>() {

			@Override
			public String getQuery() {
				return "UPDATE product SET isActive=0 WHERE pId = ?";
			}

			@Override
			public void setStatements(PreparedStatement stmt, Product obj) throws SQLException {
				stmt.setInt(1, obj.getId());
			}
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
						+ "WHERE C.productName = ? AND P.isActive=1";
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
				return "SELECT C.image FROM catalogproduct C INNER JOIN product P "
						+ "ON C.pId=P.pId WHERE P.isActive=1;";
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
	public void getOrderHandler(DbQuery db,  Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		
		dbGet.performAction(new ISelect() {
			@Override
			public String getQuery() {
				return "SELECT * FROM order WHERE brId = ?";
			}

			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				int oId=rs.getInt(1);
				Date creationDate=rs.getDate(2);
				Date requestedDate=rs.getDate(3);
				int cId=rs.getInt(4);
				int stId=rs.getInt(5);
				int brId=rs.getInt(6);
				Order order=new Order();
				return (Object)order;
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
				stmt.setString(1, obj.getServerPath());
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
	
	/** Add Sale To Catalog In Branch  */
	public void addSaleCatalogInBranchHandler(DbQuery db,  Command key)
	{
		DbUpdater<CatalogInBranch> dbUpdate = new DbUpdater<>(db, key);
	
		dbUpdate.performAction(new IUpdate<CatalogInBranch>() {

			@Override
			public String getQuery() {
				return "INSERT INTO cataloginbranch (brId, catPId, discount) VALUES (?, ?, ?);";
			}

			@Override
			public void setStatements(PreparedStatement stmt, CatalogInBranch obj) throws SQLException {
				stmt.setInt(1, obj.getBranchId());
				stmt.setInt(2, obj.getCatalogProductId());
				stmt.setInt(3, obj.getDiscount());
			}
		});
	}
	
	/** Delete Sale for Catalog In Branch  */
	public void deleteSaleCatalogInBranchHandler(DbQuery db,  Command key)
	{
		DbUpdater<CatalogInBranch> dbUpdate = new DbUpdater<>(db, key);
	
		dbUpdate.performAction(new IUpdate<CatalogInBranch>() {

			@Override
			public String getQuery() {
				return "DELETE FROM cataloginbranch WHERE brId=? AND catPId=?;";
			}

			@Override
			public void setStatements(PreparedStatement stmt, CatalogInBranch obj) throws SQLException {
				stmt.setInt(1, obj.getBranchId());
				stmt.setInt(2, obj.getCatalogProductId());
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
	public void getEmployeeByuIdHandler(DbQuery db, Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
		@Override
		public String getQuery() {
		return "SELECT uId, eId, role, brId FROM employe where uId=?";
	}

	@Override
	public Object createObject(ResultSet rs) throws SQLException {
		int uId = rs.getInt(1);
		int eId = rs.getInt(2);
		String role = rs.getString(3);
		int brId = rs.getInt(4);
		
		Role roleEnum = null;
		if (role.equals((Role.Branch).toString()))
			roleEnum = Role.Branch;
		else if (role.equals((Role.BranchesManager).toString()))
			roleEnum = Role.BranchesManager;
		else if (role.equals((Role.BranchManager).toString()))
			roleEnum = Role.BranchManager;
		else if (role.equals((Role.CustomerService).toString()))
			roleEnum = Role.CustomerService;
		else if (role.equals((Role.ServiceExpert).toString()))
			roleEnum = Role.ServiceExpert;
		else if (role.equals((Role.SystemManager).toString()))
			roleEnum = Role.SystemManager;
		
		return new Employee(uId, eId, roleEnum, brId);
	}

	@Override
	public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { 
		Integer uId = (Integer) packet.getParameterForCommand(Command.getEmployeeByUid).get(0);
		stmt.setInt(1, uId);
		}
	});
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
		Integer cus = (Integer) packet.getParameterForCommand(Command.getCustomersKeyByuId).get(0);
		stmt.setInt(1, cus);
		}
	});
}
	
	/** Set user logged in state */
    public void updateUserIsLoggedHandler(DbQuery db,  Command key)
    {
        DbUpdater<User> dbUpdate = new DbUpdater<>(db, key);
   
        dbUpdate.performAction(new IUpdate<User>() {
 
            @Override
            public String getQuery() {
                return "UPDATE user SET isLogged=? WHERE uId=?";
            }
 
            @Override
            public void setStatements(PreparedStatement stmt, User obj) throws SQLException {
            	int logged = (obj.isLogged() ? 1 : 0);
                stmt.setInt(1, logged);
                stmt.setInt(2, obj.getuId());
            }
        });
    }
	
	/** Get User instance by it's username and password */
		public void getUserByNameAndPassHandler(DbQuery db, Command key)
		{
			DbGetter dbGet = new DbGetter(db, key);
			dbGet.performAction(new ISelect() {
			@Override
			public String getQuery() {
			return "SELECT uId, user, password, isLogged, permission FROM user where uId=? AND password=?";
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
			
			if(perm.equals((Permission.Administrator).toString()))
				permission= Permission.Administrator;
			else if(perm.equals((Permission.Blocked).toString()))
				permission= Permission.Blocked;
			else if(perm.equals((Permission.Limited).toString()))
				permission= Permission.Limited;
			else if(perm.equals((Permission.Client).toString()))
				permission= Permission.Client;
		
			return new User(uId, user, password, isloggedbool, permission);
		}

		@Override
		public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { 
			ArrayList<Object> params = packet.getParameterForCommand(Command.getUserByNameAndPass);
			User user = (User)params.get(0);
			
			stmt.setString(1, user.getUser());
			stmt.setString(2, user.getPassword());
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
				//User user = (User) packet.getParameterForCommand(Command.getUserByuId).get(0);
				//stmt.setInt(1, user.getuId());
				stmt.setInt(1, (Integer)packet.getParameterForCommand(Command.getUserByuId).get(0));
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
				Integer customerCid = (Integer) packet.getParameterForCommand(Command.getAccountbycID).get(0);
				stmt.setInt(1, customerCid);
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
				int balance=rs.getInt(4);
				String creditCard=rs.getString(5);
				String statusString=rs.getString(6);
				AccountStatus status = AccountStatus.Active;
				
				Account newacc;
				if(statusString.equals((AccountStatus.Active).toString()))
					status= AccountStatus.Active;
				else if(statusString.equals((AccountStatus.Blocked).toString()))
					status= AccountStatus.Blocked;
				else if(statusString.equals((AccountStatus.Closed).toString()))
					status= AccountStatus.Closed;
				
				newacc=new Account(acNum, brId, cId, balance, status, creditCard);
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
				stmt.setDouble(3, obj.getBalance());
				stmt.setString(4, obj.getCreditCard());
				stmt.setString(5, obj.getAccountStatus().toString());
			}
			
			
		});
	}
	
	/**
	 * Getting the branch name by brID
	 * @param db -Stores database information 
	 * @param key  - Command operation which is performed
	 */
	public void getBranchBybrIdHandler(DbQuery db,  Command key)
	{

		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
			
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				// TODO Auto-generated method stub
				Integer branchid = (Integer) packet.getParameterForCommand(Command.getBranchBybrId).get(0);
				stmt.setInt(1,branchid );
			}
			
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "select b.brName from branch b where b.brId=?";
			}
			
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				String brname=rs.getString(1);
				
				
				return (Object)brname;
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
				return "update Account set creditCard=?, balance=?, status=? where brId=? and cId=?";
			}

			@Override
			public void setStatements(PreparedStatement stmt, Account obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setString(1,obj.getCreditCard());
				stmt.setDouble(2, obj.getBalance());
				stmt.setString(3,obj.getAccountStatus().toString());
				stmt.setInt(4,obj.getCustomerId());
				stmt.setInt(5, obj.getBranchId());
			}
		});
	}
	
	/**
	 * Handle complain updating by querying a matching "Update Statement",
	 * @param db -Stores database information 
	 * @param key - Command operation which is performed
	 */
	public void updateComplainHandler(DbQuery db,  Command key)
	{
		DbUpdater<Complain> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<Complain>() {

			/**
			 * Initialize complain updating query
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "UPDATE complain SET creationDate=?, details=?, title=?, cId=?, eId=?,isActive=? " +
					   "WHERE comId=?";
			}
			/**
			 * Initialize the relevant fields of the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Complain obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setDate(1, obj.getCreationDate());
				stmt.setString(2, obj.getDetails());
				stmt.setString(3, obj.getTitle());
				stmt.setInt(4, obj.getCustomerId());
				stmt.setInt(5, obj.getCustomerServiceId());
				stmt.setBoolean(6, obj.isActive());
				stmt.setInt(7, obj.getId());
			}
		});
	}
	
	/**
	 * Handle adding a new reply
	 * @param db -Stores database information 
	 * @param key - Command operation which is performed
	 */
	private void addReplyHandler(DbQuery db, Command key) {
		// TODO Auto-generated method stub
		DbUpdater<Reply> dbUpdate = new DbUpdater<>(db, key);
		
		dbUpdate.performAction(new IUpdate<Reply>() {
			/**
			 * Register the fields which is used for the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Reply obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setInt(1,obj.getComplainId());
				stmt.setString(2, obj.getReplyment());
			}
			/**
			 * Register an Insert query
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "INSERT INTO reply (comId, replyment) " + 
				   	   "VALUES (?,?);";
			}
		});
	}
	
	/**
	 * Handle a new complain add
	 * @param db
	 * @param key
	 */
	public void addComplainHandler(DbQuery db , Command key)
	{
		DbUpdater<Complain> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<Complain>() {
			/**
			 * Register the fields for the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Complain obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setDate(1,obj.getCreationDate());
				stmt.setString(2, obj.getDetails());
				stmt.setString(3, obj.getTitle());
				stmt.setInt(4, obj.getCustomerId());
				stmt.setInt(5, obj.getCustomerServiceId());
				stmt.setBoolean(6, obj.isActive());
				stmt.setInt(7, obj.getBranchId());
			}
			/**
			 * Register an Insert query
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "INSERT INTO complain (creationDate, details, title,cId,eId,isActive,brId) " + 
					   "VALUES (?,?,?,?,?,?,?);";
			}
		});
	}
	public void getComplainsForReportHandler(DbQuery db , Command key)
	{
		DbGetter dbGetter = new DbGetter(db, key);
		dbGetter.performAction(new ISelect() {

			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				// TODO Auto-generated method stub
				int brId = (int)packet.getParameterForCommand(key).get(0);
				int year =(int)packet.getParameterForCommand(key).get(1);
				int quar =(int)packet.getParameterForCommand(key).get(2);
				stmt.setInt(1,brId);
				stmt.setInt(2, year);
				stmt.setInt(3, quar);
				}

			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "SELECT * FROM complain where brId=? and YEAR(creationDate)=? and QUARTER(creationDate)=?";
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
				boolean isActive = rs.getBoolean(7);
				int branchId = rs.getInt(8);

				Complain complain = new Complain(complainId, creationDate, title, details, customerId,creatorId,isActive,branchId);
				return (Object) complain;
			}
		});
	}
	/**
	 * 
	 * @param db -Stores database information 
	 * @param key - Command operation which is performed
	 */
	private void addComplainRefundHandler(DbQuery db, Command key) {
		// TODO Auto-generated method stub
		
		DbUpdater<Refund> dbUpdate = new DbUpdater<>(db, key);
		/**
		 * Register fields for the query
		 */
		dbUpdate.performAction(new IUpdate<Refund>() {
			
			@Override
			public void setStatements(PreparedStatement stmt, Refund obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setDate(1,obj.getCreationDate());
				stmt.setDouble(2, obj.getAmount());
				stmt.setInt(3, obj.getRefundAbleId());
			}
			/**
			 * Register an Insert query
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "INSERT INTO refund (createDate, amount, comId) " + 
						"VALUES (?, ?, ?)";
			}
		});
	}
	
	/**
	 * 
	 * @param db -Stores database information 
	 * @param key - Command operation which is performed
	 */
	public void getComplainsHandler(DbQuery db , Command key)
	{
		DbGetter dbGetter = new DbGetter(db, key);
		dbGetter.performAction(new ISelect() {
			/**
			 *	No fields to register for this query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			/**
			 * Perform a Select query to get all the complains
			 */
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
				boolean isActive = rs.getBoolean(7);
				int branchId = rs.getInt(8);

				Complain complain = new Complain(complainId, creationDate, title, details, customerId,creatorId,isActive,branchId);
				return (Object) complain;
			}
		});
	}
	
	private void addSurveyHandler(DbQuery db , Command key)
	{
		DbUpdater<Survey> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<Survey>() {

			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "INSERT INTO survey (subject, creatorId,isActive) " + 
				"VALUES (?, ?,?)";
			}

			@Override
			public void setStatements(PreparedStatement stmt, Survey obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setString(1, obj.getSubject());
				stmt.setInt(2, obj.getCreatorId());
				stmt.setBoolean(3, obj.isActive());
			}
		});
	}
	
	private void getSurveyHandler(DbQuery db , Command key)
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
				return "SELECT * FROM survey";
			}
			
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				
				return new Survey(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getBoolean(4),rs.getInt(5));
			}
		});
	}
	
	private void getQuestionsHandler(DbQuery db , Command key)
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
				return "SELECT * FROM question";
			}
			
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				
				return new Question(rs.getInt(1),rs.getString(2));
			}
		});
	}
	
	private void addQuestionsToSurveyHandler(DbQuery db , Command key)
	{
		DbUpdater<SurveyQuestion> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<SurveyQuestion>() {

			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "INSERT INTO surveyquestion (surId,qId) " + 
						"SELECT surId,qId FROM(" + 
						"		(SELECT * FROM survey S ORDER BY S.surId DESC LIMIT 1) as LastSurveyID ," + 
						"		(SELECT * FROM question Q ORDER BY Q.qId DESC LIMIT 6) as LastQuestions" + 
						");";
			}

			@Override
			public void setStatements(PreparedStatement stmt, SurveyQuestion obj) throws SQLException {
				// TODO Auto-generated method stub
			}
		});
	}

	private void addQuestionHandler(DbQuery db , Command key)
	{
		DbUpdater<Question> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<Question>() {

			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "INSERT INTO question (question) " + 
				"VALUES (?)";
			}

			@Override
			public void setStatements(PreparedStatement stmt, Question obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setString(1, obj.getQuesiton());
			}
		});
	}
	
	private void getSurveyQuestionsHandler(DbQuery db , Command key)
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
				return "SELECT * FROM surveyquestion;";
			}
			
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return new SurveyQuestion(rs.getInt(1),rs.getInt(2),rs.getInt(3));
			}
		});
	}
	
	private void updateSurveyHandler(DbQuery db, Command key) {
		DbUpdater<Survey> dbUpdater = new DbUpdater<>(db, key);
		dbUpdater.performAction(new IUpdate<Survey>() {

			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "UPDATE survey " +
					   "SET subject = ?, creatorId = ?, isActive = ? , scId = ?" +
				 	   "WHERE surId=?";
			}

			@Override
			public void setStatements(PreparedStatement stmt, Survey obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setString(1, obj.getSubject());
				stmt.setInt(2, obj.getCreatorId());
				stmt.setBoolean(3, obj.isActive());
				stmt.setInt(4, obj.getId());
				stmt.setInt(5, obj.getSurveyConclusionId());
			}
		});
	}
	
	private void addAnswerSurveyHandler(DbQuery db , Command key)
	{
		DbUpdater<AnswerSurvey> dbUpdater = new DbUpdater<>(db, key);
		dbUpdater.performAction(new IUpdate<AnswerSurvey>() {

			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "INSERT INTO answersurvey (sqId, bId, answer) VALUES (?, ?, ?)";
			}

			@Override
			public void setStatements(PreparedStatement stmt, AnswerSurvey obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setInt(1, obj.getSurveyQuestionId());
				stmt.setInt(2,obj.getBranchId());
				stmt.setInt(3,obj.getAnswer());
			}
		});
	}
	
	private void getAverageAnswersBySurveyIdHandler(DbQuery db , Command key)
	{
		DbGetter dbGetter = new DbGetter(db, key);
		dbGetter.performAction(new ISelect() {
			
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				// TODO Auto-generated method stub
				Integer surveyId = (Integer)(packet.getParameterForCommand(Command.getAverageAnswersBySurveyId).get(0));
				stmt.setInt(1, surveyId);
			}
			
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return  "SELECT answersurvey.answerId, answersurvey.sqId,answersurvey.bId ,AVG(answersurvey.answer) as answer " +
						"FROM surveyquestion , answersurvey , survey " +
						"WHERE surveyquestion.surId = ? AND surveyquestion.sqId = answersurvey.sqId " +
						"GROUP BY surveyquestion.sqId;";
			}
			
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return new AnswerSurvey(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4));
			}
		});
	}
	
	private void addConclusionHandler(DbQuery db , Command key)
	{
		DbUpdater<SurveyConclusion> dbUpdater = new DbUpdater<>(db, key);
		dbUpdater.performAction(new IUpdate<SurveyConclusion>() {

			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "INSERT INTO surveyconclusion (expertId,conclusion) " +
					   "VALUES (?,?);";
			}

			@Override
			public void setStatements(PreparedStatement stmt, SurveyConclusion obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setInt(1, obj.getServiceExpertId());
				stmt.setString(2, obj.getConclusion());
			}
		});
	}

	private void getConclusionsHandler(DbQuery db , Command key)
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
				return "SELECT * FROM surveyconclusion;";
			}
			
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return new SurveyConclusion(rs.getInt(1),rs.getInt(2),rs.getString(3));
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
					getActiveCatalogProductsHandler(db, key);
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
				else if(key.equals(Command.getAccountbycID))
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
				else if(key.equals(Command.updateComplain)) {
					updateComplainHandler(db,key);
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
				else if(key.equals(Command.addSurvey))
					addSurveyHandler(db, key);
				else if(key.equals(Command.addQuestions))
					addQuestionHandler(db,key);
				else if(key.equals(Command.addQuestionsToServey))
					addQuestionsToSurveyHandler(db,key);
				else if(key.equals(Command.getSurvey))
					getSurveyHandler(db, key);
				else if(key.equals(Command.getQuestions))
					getQuestionsHandler(db, key);
				else if(key.equals(Command.getSurveyQuestions))
					getSurveyQuestionsHandler(db, key);
				else if(key.equals(Command.addAnswerSurvey))
					addAnswerSurveyHandler(db,key);
				else if(key.equals(Command.getAverageAnswersBySurveyId))
					getAverageAnswersBySurveyIdHandler(db, key);
				else if(key.equals(Command.addConclusion))
					addConclusionHandler(db,key);
				else if(key.equals(Command.getConclusions))
					getConclusionsHandler(db, key);
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
				else if(key.equals(Command.updateUserByuId))
					updateUserByuIdHandler(db, key);
				else if(key.equals(Command.updateCustomerByuId))
					updateCustomerByuIdHandler(db, key);
				else if(key.equals(Command.updateAccountsBycId))
					updateAccountsBycIdHandler(db,key);
				else if(key.equals(Command.getAccountbycID))
					getAccountBycIdHandler(db, key);
				else if(key.equals(Command.setProductAsDeleted))
					setProductAsDeletedHandler(db, key);
				else if(key.equals(Command.getAllCatalogProducts))
					getAllCatalogProductsHandler(db, key);
				else if (key.equals(Command.addSaleCatalogInBranch))
					addSaleCatalogInBranchHandler(db, key);
				else if(key.equals(Command.deleteSaleCatalogInBranch))
					deleteSaleCatalogInBranchHandler(db, key);
				else if(key.equals(Command.getBranchBybrId))
					getBranchBybrIdHandler(db, key);
				else if(key.equals(Command.updateSurvey))
					updateSurveyHandler(db,key);
				else if(key.equals(Command.getUserByNameAndPass))
					getUserByNameAndPassHandler(db, key);
				else if(key.equals(Command.getEmployeeByUid))
					getEmployeeByuIdHandler(db, key);
				else if (key.equals(Command.setUserLoggedInState))
					updateUserIsLoggedHandler(db, key);
				else if(key.equals(Command.getComplainsForReport))
					getComplainsForReportHandler(db,key);
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
