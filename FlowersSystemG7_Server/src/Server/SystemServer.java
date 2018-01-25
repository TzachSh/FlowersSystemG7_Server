package Server;



import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import Branches.Branch;
import Branches.Employee;
import Branches.Role;
import Commons.ProductInOrder;
import Commons.Refund;
import Customers.Account;
import Customers.AccountStatus;
import Customers.Complain;
import Customers.Customer;
import Customers.MemberShipAccount;
import Customers.Membership;
import Customers.MembershipType;
import Customers.Reply;
import Logic.DbGetter;
import Logic.DbQuery;
import Logic.DbUpdater;
import Logic.ISelect;
import Logic.IUpdate;
import Orders.Delivery;
import Orders.Order;
import Orders.OrderPayment;
import Orders.PaymentMethod;
import PacketSender.Command;
import PacketSender.FileSystem;
import PacketSender.Packet;
import Products.CatalogInBranch;
import Products.CatalogProduct;
import Products.ColorProduct;
import Products.CustomProduct;
import Products.Flower;
import Products.FlowerInProduct;
import Products.Product;
import Products.ProductType;
import Reports.ComplainsReportGeneration;
import Reports.IncomeReportGeneration;
import Reports.OrderReportGeneration;
import Reports.ReportGeneration;
import Reports.SatisfactionReportGeneration;
import Schedules.ScheduleTask;
import Schedules.ScheduleThread;
import Schedules.ScheduleThread.ThreadType;
import Survey.AnswerSurvey;
import Survey.Question;
import Survey.Survey;
import Survey.SurveyConclusion;
import Survey.SurveyQuestion;
import Users.Permission;
import Users.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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
	private Timer timer = new Timer();
	private static final int DEFAULT_PORT = 5555;
	private DbQuery dbConnection;
    @FXML
    private MenuButton btnSchedule;  
    @FXML
    private MenuItem btnCharging;
    @FXML
    private MenuItem btnReport;
    @FXML
    private MenuItem btnDelete;
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
    @FXML
    private Pane paneDetails;
	int port = 0; // Port to listen on
	public SystemServer() {
		super(DEFAULT_PORT);
	}
	/**
	 * 
	 * @param msg log message to see the information
	 */
	public void printlogMsg(String msg)
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
				paneDetails.setDisable(false);
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
					
					// start scheduling task every night at 0:00 am
					dbConnection = new DbQuery(user, password, database);
					Calendar today = Calendar.getInstance();
					today.set(Calendar.HOUR_OF_DAY, 0);
					today.set(Calendar.MINUTE, 0);
					today.set(Calendar.SECOND, 0);
					timer = new Timer();
					timer.schedule(new ScheduleTask(dbConnection, this), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); // period: 1 day
					
					paneDetails.setDisable(true);
					btnSchedule.setDisable(false);
					btnSubmit.setText("Stop service");//update button
				}
				else//if it was listen
				{
					printlogMsg("Server has finished listening on port:"+port);
					
					// cancel the scheduling
					timer.cancel();
					
					paneDetails.setDisable(false);
					btnSchedule.setDisable(true);
					btnSubmit.setText("Start service");///update button
				}
			}
	}
	
	
	public void logErrorSchedule(String msgError)
	{
		printlogMsg("Failed: " + msgError);
	}
	
	public void onClickForceReportsButton()
	{
		ScheduleThread thread = new ScheduleThread(dbConnection, this, ThreadType.Reports);
		thread.start();
	}
	
	public void onClickForceChargingButton()
	{
		ScheduleThread thread = new ScheduleThread(dbConnection, this, ThreadType.Paying);
		thread.start();
	}
	
	public void onClickForceDeletingButton()
	{
		ScheduleThread thread = new ScheduleThread(dbConnection, this, ThreadType.DeleteMemberships);
		thread.start();
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
				// cancel the scheduling
				timer.cancel();
				
				System.exit(0);
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
	/**
	 * this function delete membership from specific account
	 * @param db -Stores database information 
	 * @param key  - Command operation which is performed
	 */
	public void deleteMemberShipAccountByacNumHandler(DbQuery db,  Command key)
	{
		DbUpdater<MemberShipAccount> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<MemberShipAccount>() {
			
			@Override
			public void setStatements(PreparedStatement stmt, MemberShipAccount obj) throws SQLException {
				stmt.setInt(1, obj.getAcNum());

			}
			
			@Override
			public String getQuery() {
				return "delete from membershipaccount where membershipaccount.acNum=?; ";			
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
	/**
	 * 
	 * @param db
	 * @param key
	 */
	public void getBranchesHandler(DbQuery db,  Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		
		dbGet.performAction(new ISelect() {
			@Override
			public String getQuery() {
				return "SELECT brId, brName FROM branch WHERE brName <> 'Service';";
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
	
	public void getBranchesIncludeServiceHandler(DbQuery db,  Command key)
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
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ delete this function @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	/*public void getOrderHandler(DbQuery db,  Command key)
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
				double total = rs.getDouble(7);
				Order order=new Order();
				return (Object)order;
			}

			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				int brId = (int)packet.getParameterForCommand(key).get(0);
				stmt.setInt(1, brId);
			}
		});
	}*/
	
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
	
	 /**
	  * getting all users 
	  * @param db -Stores database information 
	  * @param key  - Command operation which is performed
	  */
	public void getUsersHandler(DbQuery db, Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
		/**
		 * Perform a Select query to get all the complains
		 */
		@Override
		public String getQuery()
		{
			return "SELECT * FROM User";
		}
		/**
		 * Parse the result set in to a User object
		 */
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
			//checking which permission the user have
			if(perm.equals((Permission.Administrator).toString()))
			permission= Permission.Administrator;
			else if(perm.equals((Permission.Blocked).toString()))
			permission= Permission.Blocked;
			else if(perm.equals((Permission.Limited).toString()))
			permission= Permission.Limited;
			//returning the user information
			newuser=new User(uId, user, password, isloggedbool, permission);
			return (Object)newuser;
		}
		/**
		 *	No fields to register for this query
		 */
		@Override
		public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { }
		}	
		);
	}
	
	private void getCustomersHandler(DbQuery db , Command key)
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
				return "SELECT * FROM customer;";
			}
			
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return new Customer(rs.getInt(1),rs.getInt(2));
			}
		});
	}
	
	 /**
	  * getting employee by user id  
	  * @param db -Stores database information 
	  * @param key  - Command operation which is performed
	  */
	public void getEmployeeByuIdHandler(DbQuery db, Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
		/**
		 * Perform a Select query to get all the employee by user id
		 */
		@Override
		public String getQuery() {
		return "SELECT uId, eId, role, brId FROM employee where uId=?";
	}
	/**
	 * Parse the result set in to a Employee object
	 */
	@Override
	public Object createObject(ResultSet rs) throws SQLException {
		int uId = rs.getInt(1);
		int eId = rs.getInt(2);
		String role = rs.getString(3);
		int brId = rs.getInt(4);
		//getting the role
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
	/**
	 *	setting user id field for this query
	 */
	@Override
	public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { 
		Integer uId = (Integer) packet.getParameterForCommand(Command.getEmployeeByUid).get(0);
		stmt.setInt(1, uId);
		}
	});
}
	
	/**
	  * getting customer by user id
	  * @param db -Stores database information 
	  * @param key  - Command operation which is performed
	  */
	public void getCustomersKeyByuIdHandler(DbQuery db, Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
		/**
		 * Perform a Select query to get customer
		 */
		@Override
		public String getQuery() {
		return "SELECT cId,uId FROM customer where uId=?";
	}
	/**
	 * Parse the result set in to a Customer object
	 */	
	@Override
	public Object createObject(ResultSet rs) throws SQLException {
		int cId = rs.getInt(1);
		int uId=rs.getInt(2);
		Customer cus;
		cus=new Customer(cId, uId);
		return (Object)cus;
	}
	/**
	 *	adding customer user id  field for this query
	 */
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
			/**
			 * checking the presmission
			 */
			if(perm.equals((Permission.Administrator).toString()))
				permission= Permission.Administrator;
			else if(perm.equals((Permission.Blocked).toString()))
				permission= Permission.Blocked;
			else if(perm.equals((Permission.Limited).toString()))
				permission= Permission.Limited;
		
			return new User(uId, user, password, isloggedbool, permission);
		}
		/**
		 * Initialize the relevant fields of the query
		 */
		@Override
		public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { 
			ArrayList<Object> params = packet.getParameterForCommand(Command.getUserByNameAndPass);
			User user = (User)params.get(0);
			
			stmt.setString(1, user.getUser());
			stmt.setString(2, user.getPassword());
			}
		});
	}
	
	/**
	  * getting user by user id
	  * @param db -Stores database information 
	  * @param key  - Command operation which is performed
	  */
	public void getUserByuIdHandler(DbQuery db, Command key)
	{
		
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
			/**
			 *	adding user id field for this query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				stmt.setInt(1, (Integer)packet.getParameterForCommand(Command.getUserByuId).get(0));
			}
			/**
			 * Perform a Select query to get user
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "SELECT * "+ "FROM User u where uId=?";
			}
			/**
			 * Parse the result set in to a User object
			 */
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
				/**
				 * checking the permission
				 */
				if(perm.equals((Permission.Administrator).toString()))
				permission= Permission.Administrator;
				else if(perm.equals((Permission.Blocked).toString()))
				permission= Permission.Blocked;
				else if(perm.equals((Permission.Limited).toString()))
				permission= Permission.Limited;
				/**
				 * building the user information 
				 */
				newuser=new User(uId, user, password, isloggedbool, permission);
			return (Object)newuser;
			}
		});
		
	}

	/**
	  * getting user by user name
	  * @param db -Stores database information 
	  * @param key  - Command operation which is performed
	  */
	public void getUserByUserNameHandler(DbQuery db, Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
		/**
		 * Perform a Select query to get the user 
		 */
		@Override
		public String getQuery() {
		return "SELECT * "+ "FROM User u where user=?";
	}
	/**
	 * Parse the result set in to a User object
	 */
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
		/**
		 * adding the user information
		 */
		newuser=new User(uId, user, password, isloggedbool, permission);
	return (Object)newuser;
	}
	/**
	 *	adding user name fields for this query
	 */
	@Override
	public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { 
		User user = (User) packet.getParameterForCommand(Command.getUsersByUserName).get(0);
		stmt.setString(1, user.getUser());
		}
	});
	}
	/**
	  * adding user to database
	  * @param db -Stores database information 
	  * @param key  - Command operation which is performed
	  */
	public void addUserHandler(DbQuery db, Command key)
	{
		DbUpdater<User> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<User>() {
		/**
		 * Perform a Insert query to add user
		 */	
		@Override
		public String getQuery() {
		// TODO Auto-generated method stub
		return "insert into User(uId,user,password,isLogged,permission) values(?,?,?,?,?)";
	
		}
		/**
		 * Initialize the relevant fields of the query
		 */
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
	
	/**
	  * getting account by customer id and branch number
	  * @param db -Stores database information 
	  * @param key  - Command operation which is performed
	  */
	public void getAccountbycIDandBranchHandler(DbQuery db, Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
			/**
			 * Initialize the relevant fields of the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				// TODO Auto-generated method stub
				Integer customerCid = (Integer) packet.getParameterForCommand(Command.getAccountbycIDandBranch).get(0);
				Integer brId = (Integer) packet.getParameterForCommand(Command.getAccountbycIDandBranch).get(1);
				stmt.setInt(1, customerCid);
				stmt.setInt(2, brId);

			}
			/**
			 * Perform a Select query to get account by customer id and branch number 
			 */	
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "SELECT * FROM account  where account.cId=? and account.brId=?";
			}
			/**
			 * Parse the result set in to a Account object
			 */
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
				
				newacc=new Account(acNum, cId, 0, balance, brId, status, creditCard);
				return (Object)newacc;
				
			}
		});
	}	
	/**
	  * adding customer to db		
	  * @param db -Stores database information 
	  * @param key  - Command operation which is performed
	  */
	public void addCustomertHandler(DbQuery db, Command key)
	{
		DbUpdater<Customer> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<Customer>() {
		/**
		 * Perform a Insert query to get all the complains
		 */	
		@Override
		public String getQuery() {
		// TODO Auto-generated method stub
			return "INSERT into Customer (uId) values(?)";
	
		}
		/**
		 * Initialize the relevant fields of the query
		 */
		@Override
		public void setStatements(PreparedStatement stmt, Customer obj) throws SQLException {
		// TODO Auto-generated method stub
			stmt.setInt(1, obj.getuId());
		}
	});
	}
	
	/**
	  * adding account to db
	  * @param db -Stores database information 
	  * @param key  - Command operation which is performed
	  */
	public void addAccountrHandler(DbQuery db, Command key)
	{
		DbUpdater<Account> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<Account>() {
			/**
			 * Perform a Insert query to add account
			 */	
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "INSERT into Account (brId,cId,balance,creditCard,status) values(?,?,?,?,?)";
				
			}
			/**
			 * Initialize the relevant fields of the query
			 */
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
			/**
			 * Initialize the relevant fields of the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				// TODO Auto-generated method stub
				Integer branchid = (Integer) packet.getParameterForCommand(Command.getBranchBybrId).get(0);
				stmt.setInt(1,branchid );
			}
			/**
			 * Perform a Select query to get branch 
			 */	
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "select b.brName from branch b where b.brId=?";
			}
			/**
			 * Parse the result set in to a Branch object
			 */
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				String brname=rs.getString(1);
				
				
				return (Object)brname;
			}
		});
	}
	
	
	/**
	  * getting all memberships
	  * @param db -Stores database information 
	  * @param key  - Command operation which is performed
	  */
	public void getMemberShipHandler(DbQuery db, Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
		/**
		 * Perform a Select query to get all the memberships
		 */	
		@Override
		public String getQuery() {
		return "SELECT * " + 
		"FROM MemberShip";
	}
	/**
	 * Parse the result set in to a membership object
	 */	
	@Override
	public Object createObject(ResultSet rs) throws SQLException {
		int mId = rs.getInt(1);
		String memship =rs.getString(2);
		Double discount = rs.getDouble(3);
		MembershipType memtype;
		Membership newmemship;
	
	   if(memship.equals((MembershipType.Monthly).toString()))
		memtype= MembershipType.Monthly;
		else
		memtype= MembershipType.Yearly;

	newmemship=new Membership(mId, memtype, discount);
	return (Object)newmemship;
	}
	/**
	 * Initialize the relevant fields of the query
	 */
	@Override
	public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException { }
	});
	}
	/**
	  * update user by user id 
	  * @param db -Stores database information 
	  * @param key  - Command operation which is performed
	  */
	public void updateUserByuIdHandler(DbQuery db,  Command key)
	{
		DbUpdater<User> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<User>() {
			/**
			 * Perform a Update query to update user information
			 */	
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "update user set user=?,password=?,isLogged =?,permission=? where uId=?";
			
			}
			/**
			 * Initialize the relevant fields of the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, User obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setString(1,obj.getUser());
				stmt.setString(2,obj.getPassword());
				stmt.setBoolean(3, obj.isLogged());
				stmt.setString(4, obj.getPermission().name());
				stmt.setInt(5,obj.getuId());
			}
			
		});
	}
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@maybe delete this function
	//updating Customer after updating by cId
	public void updateCustomerByuIdHandler(DbQuery db,  Command key)
	{
		DbUpdater<Customer> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<Customer>() {
			/**
			 * Perform a Select query to get all the complains
			 */	
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "update customer set where uId=?";
			}
			/**
			 * Initialize the relevant fields of the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Customer obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setInt(2,obj.getuId());
			}
		});
	}
	/**
	  * updating account after updating by cId
	  * @param db -Stores database information 
	  * @param key  - Command operation which is performed
	  */
	public void updateAccountsBycIdHandler(DbQuery db,  Command key)
	{
		DbUpdater<Account> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<Account>() {
			/**
			 * Perform a Update query to update Account 
			 */	
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "update Account set creditCard=?, balance=?, status=? where brId=? and cId=?";
			}
			/**
			 * Initialize the relevant fields of the query
			 */
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
				stmt.setTimestamp(1, obj.getCreationDate());
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
				
				stmt.setString(1, obj.getDetails());
				stmt.setString(2, obj.getTitle());
				stmt.setInt(3, obj.getCustomerId());
				stmt.setInt(4, obj.getCustomerServiceId());
				stmt.setBoolean(5, obj.isActive());
				stmt.setInt(6, obj.getBranchId());
			}
			/**
			 * Register an Insert query
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "INSERT INTO complain (creationDate, details, title,cId,eId,isActive,brId) " + 
					   "VALUES (NOW(),?,?,?,?,?,?);";
			}
		});
	}
	/**
	  * adding membership account to db
	  * @param db -Stores database information 
	  * @param key  - Command operation which is performed
	  */
	public void addMemberShipAccountHandler(DbQuery db,Command key) {
		DbUpdater<MemberShipAccount> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<MemberShipAccount>() {
			/**
			 * Perform a Insert query to add new membership account
			 */	
			@Override
			public String getQuery() {
				return "insert into MemberShipAccount values (?,?,?)";
			}
			/**
			 * Parse the result set in to a Membership account object
			 */
			@Override
			public void setStatements(PreparedStatement stmt, MemberShipAccount obj) throws SQLException {
				stmt.setInt(1, obj.getAcNum());
				stmt.setInt(2, obj.getmId());
				stmt.setDate(3,obj.getCreationDate());
				
			}
		});
		
	}

	public void getOrderReportHandler(DbQuery db, Command key)
	{
		Packet packet = db.getPacket();
		
		try
		{
			ArrayList<Integer> params = packet.<Integer>convertedResultListForCommand(Command.getOrderReport);
			int branchId = params.get(0);
			int year = params.get(1);
			int quarter = params.get(2);
			
			ReportGeneration orderReport = new OrderReportGeneration(db, year, quarter);
			ArrayList<Object> report = orderReport.getReport(branchId);
			packet.setParametersForCommand(Command.getOrderReport, report);
			
		}
		catch (Exception e)
		{
			packet.setExceptionMessage(e.getMessage());
		}
	}
	
	public void getIncomeReportHandler(DbQuery db, Command key)
	{
		Packet packet = db.getPacket();
		
		try
		{
			ArrayList<Integer> params = packet.<Integer>convertedResultListForCommand(Command.getIncomeReport);
			int branchId = params.get(0);
			int year = params.get(1);
			int quarter = params.get(2);
			
			ReportGeneration orderReport = new IncomeReportGeneration(db, year, quarter);
			ArrayList<Object> report = orderReport.getReport(branchId);
			packet.setParametersForCommand(Command.getIncomeReport, report);
			
		}
		catch (Exception e)
		{
			packet.setExceptionMessage(e.getMessage());
		}
	}
	
	public void getComplainsForReportHandler(DbQuery db, Command key)
	{
		Packet packet = db.getPacket();
		
		try
		{
			ArrayList<Integer> params = packet.<Integer>convertedResultListForCommand(Command.getComplainsForReport);
			int branchId = params.get(0);
			int year = params.get(1);
			int quarter = params.get(2);
			
			ReportGeneration orderReport = new ComplainsReportGeneration(db, year, quarter);
			ArrayList<Object> report = orderReport.getReport(branchId);
			packet.setParametersForCommand(Command.getComplainsForReport, report);
			
		}
		catch (Exception e)
		{
			packet.setExceptionMessage(e.getMessage());
		}
	}
	
	public void getSatisfactionReportHandler(DbQuery db, Command key)
	{
		Packet packet = db.getPacket();
		
		try
		{
			ArrayList<Integer> params = packet.<Integer>convertedResultListForCommand(Command.getSatisfactionReport);
			int branchId = params.get(0);
			int year = params.get(1);
			int quarter = params.get(2);
			
			ReportGeneration orderReport = new SatisfactionReportGeneration(db, year, quarter);
			ArrayList<Object> report = orderReport.getReport(branchId);
			packet.setParametersForCommand(Command.getSatisfactionReport, report);
		}
		catch (Exception e)
		{
			packet.setExceptionMessage(e.getMessage());
		}
	}
	/**
	 * Handle adding a refund
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
			
			/***
			 * Initialize statement data for the query
			 */
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
	 * Handle getting all of the complains
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
			
			/***
			 * Parse the result set in to a Complain object
			 */
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				int complainId = rs.getInt(1);
				java.sql.Timestamp creationDate = rs.getTimestamp(2);
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
	
	/***
	 * Handle survey adding
	 * @param db - database information
	 * @param key - operation to be performed
	 */
	private void addSurveyHandler(DbQuery db , Command key)
	{
		DbUpdater<Survey> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<Survey>() {

			/***
			 * Insert query to be defined for survey adding
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "INSERT INTO survey (subject,creatorId,isActive) " + 
				"VALUES (?,?,?)";
			}
			/***
			 * Set statement for the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Survey obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setString(1, obj.getSubject());
				stmt.setInt(2, obj.getCreatorId());
				stmt.setBoolean(3, obj.isActive());
			}
		});
	}
	/***
	 * Handle get all surveys from the server
	 * @param db - database information
	 * @param key - operation to be performed
	 */
	private void getSurveyHandler(DbQuery db , Command key)
	{
		DbGetter dbGetter = new DbGetter(db, key);
		dbGetter.performAction(new ISelect() {
			
			/***
			 * Statement to initialize for the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				// No statement needed
				
			}
			
			/***
			 * Perform Select query to get the surveys
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "SELECT * FROM survey";
			}
			/***
			 * Parse the result set in to a Survey object
			 */
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return new Survey(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getBoolean(4),rs.getDate(5),rs.getDate(6));
			}
		});
	}
	
	/***
	 * Handle getting all the questions
	 * @param db - database information
	 * @param key - Operation to be performed
	 */
	private void getQuestionsHandler(DbQuery db , Command key)
	{
		DbGetter dbGetter = new DbGetter(db, key);
		dbGetter.performAction(new ISelect() {
			/***
			 * Statements to be initialized for the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				// No statements needed
				
			}
			/***
			 * Select query to get all the questions
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "SELECT * FROM question";
			}
			/***
			 * Parsing the result set in to a Question object
			 */
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				
				return new Question(rs.getInt(1),rs.getString(2));
			}
		});
	}
	/***
	 * Handle Attaching question to survey 
	 * @param db
	 * @param key
	 */
	private void addQuestionsToSurveyHandler(DbQuery db , Command key)
	{
		DbUpdater<SurveyQuestion> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<SurveyQuestion>() {

			/***
			 * Initialize a query to be performed
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "INSERT INTO surveyquestion (surId,qId) " + 
						"SELECT surId,qId FROM(" + 
						"		(SELECT * FROM survey S ORDER BY S.surId DESC LIMIT 1) as LastSurveyID ," + 
						"		(SELECT * FROM question Q ORDER BY Q.qId DESC LIMIT 6) as LastQuestions" + 
						");";
			}
			/***
			 * Statements to be initialized for the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, SurveyQuestion obj) throws SQLException {
				// No statements needed
			}
		});
	}
	
	/***
	 * Handle adding a question
	 * @param db - database information
	 * @param key - operation to be performed
	 */
	private void addQuestionHandler(DbQuery db , Command key)
	{
		DbUpdater<Question> dbUpdate = new DbUpdater<>(db, key);
		dbUpdate.performAction(new IUpdate<Question>() {

			/***
			 * Initialize qury for Insert the question
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "INSERT INTO question (question) " + 
				"VALUES (?)";
			}

			/***
			 * Statements to be initialized for the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Question obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setString(1, obj.getQuesiton());
			}
		});
	}
	
	/***
	 * Handle getting all question in survey
	 * @param db - database information
	 * @param key - operation to be performed
	 */
	private void getSurveyQuestionsHandler(DbQuery db , Command key)
	{
		DbGetter dbGetter = new DbGetter(db, key);
		dbGetter.performAction(new ISelect() {
			
			/**
			 *  Statements to be set for the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			/***
			 * Initializing Select query to get all the survey question data
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "SELECT * FROM surveyquestion;";
			}
			/***
			 * Parsing the result set in to a SurveyQuestion object
			 */
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return new SurveyQuestion(rs.getInt(1),rs.getInt(2),rs.getInt(3));
			}
		});
	}
	/***
	 * Handle survey updating
	 * @param db - database information
	 * @param key - operation to be performed
	 */
	private void updateSurveyHandler(DbQuery db, Command key) {
		DbUpdater<Survey> dbUpdater = new DbUpdater<>(db, key);
		dbUpdater.performAction(new IUpdate<Survey>() {

			/***
			 * Initialize a query for survey Updating to be performed
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "UPDATE survey " +
					   "SET subject = ?, creatorId = ?, isActive = ? ,activatedDate = ?,closedDate = ? " +
				 	   "WHERE surId=?";
			}
			/***
			 * Initialize statements for the updating query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Survey obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setString(1, obj.getSubject());
				stmt.setInt(2, obj.getCreatorId());
				stmt.setBoolean(3, obj.isActive());
				stmt.setDate(4, obj.getActivatedDate());
				stmt.setDate(5, obj.getClosedDate());
				stmt.setInt(6, obj.getId());
			}
		});
	}
	
	/***
	 * Handle adding an answer
	 * @param db - database information
	 * @param key - operation to be performed
	 */
	private void addAnswerSurveyHandler(DbQuery db , Command key)
	{
		DbUpdater<AnswerSurvey> dbUpdater = new DbUpdater<>(db, key);
		dbUpdater.performAction(new IUpdate<AnswerSurvey>() {

			/***
			 * Answer query Insertion to be performed
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "INSERT INTO answersurvey (sqId, brId, answer) VALUES (?, ?, ?)";
			}
			/***
			 * Initialize the statements for the answer insertion query 
			 */
			@Override
			public void setStatements(PreparedStatement stmt, AnswerSurvey obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setInt(1, obj.getSurveyQuestionId());
				stmt.setInt(2,obj.getBranchId());
				stmt.setInt(3,obj.getAnswer());
			}
		});
	}
	
	/***
	 * Handle getting an average of a survey questions 
	 * @param db - database information
	 * @param key - operation to be performed
	 */
	private void getAverageAnswersBySurveyIdHandler(DbQuery db , Command key)
	{
		DbGetter dbGetter = new DbGetter(db, key);
		dbGetter.performAction(new ISelect() {
			
			/***
			 * Setting statements for the Select questions average query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				// TODO Auto-generated method stub
				Integer surveyId = (Integer)(packet.getParameterForCommand(Command.getAverageAnswersBySurveyId).get(0));
				stmt.setInt(1, surveyId);
			}
			
			/***
			 * Initialize the query to be performed
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return  "SELECT answersurvey.answerId, answersurvey.sqId,answersurvey.brId ,AVG(answersurvey.answer) as answer " +
						"FROM surveyquestion , answersurvey , survey " +
						"WHERE surveyquestion.surId = ? AND surveyquestion.sqId = answersurvey.sqId " +
						"GROUP BY surveyquestion.sqId;";
			}
			
			/***
			 * Parsing the result set in to an AnswerSurvey object
			 */
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return new AnswerSurvey(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getDouble(4));
			}
		});
	}
	/***
	 * Handle adding a conclusion by service expert
	 * @param db - database information
	 * @param key - operation to be performed
	 */
	private void addConclusionHandler(DbQuery db , Command key)
	{
		DbUpdater<SurveyConclusion> dbUpdater = new DbUpdater<>(db, key);
		dbUpdater.performAction(new IUpdate<SurveyConclusion>() {
			/***
			 * Initialize the Insertion query of the conclusion
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "INSERT INTO surveyconclusion (expertId,conclusion,surId) " +
					   "VALUES (?,?,?);";
			}
			/***
			 * Set statements for the Insert query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, SurveyConclusion obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setInt(1, obj.getServiceExpertId());
				stmt.setString(2, obj.getConclusion());
				stmt.setInt(3, obj.getSurId());
			}
		});
	}
	
	/***
	 * Handle getting all conclusion 
	 * @param db - database information
	 * @param key - operation to be performed
	 */
	private void getConclusionsHandler(DbQuery db , Command key)
	{
		DbGetter dbGetter = new DbGetter(db, key);
		dbGetter.performAction(new ISelect() {
			/***
			 * Initialize statements for the Selection query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				// No needed
				
			}
			/***
			 * Initialize the query of the survey conclusion getting 
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "SELECT * FROM surveyconclusion;";
			}
			/***
			 * Parsing the result set in to a SurveyConclusion object
			 */
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return new SurveyConclusion(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getInt(4));
			}
		});
	}
	private void createCustomProduct(DbQuery db , Command key) {
		Packet packet = db.getPacket();
		try {
			// get all parameters
			CustomProduct pro = (CustomProduct)packet.getParameterForCommand(Command.CreateCustomProduct).get(0);
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
	    				"INSERT INTO customproduct (pId, blessing) VALUES (@pId, ?); " +
	    				"INSERT INTO flowerinproduct (fId, pId, quantity) VALUES " + formatSql + ";";
	    	
	 
	    	// set the statements from the implemention
	    	PreparedStatement stmt = con.prepareStatement(query);
	    	stmt.setInt(1, pro.getProductTypeId());
			stmt.setDouble(2, pro.getPrice());
			stmt.setString(3, pro.getBlessing());
	    	stmt.executeUpdate();
	    
	    	
	    	// get the new product id
	    	String queryPid = "select Max(pId) from customproduct";
	    	
	    	// set the statements from the implemention
	    	PreparedStatement stmtPid = con.prepareStatement(queryPid);
	    	ResultSet rs = stmtPid.executeQuery();
			while (rs.next()) {
				pro.setId(rs.getInt(1));
			}
	    	
			con.close();
		}
		catch (Exception e)
		{
			packet.setExceptionMessage(e.getMessage());
		}
		
	}
	/***
	 * Handle a user deletion
	 * @param db - database information
	 * @param key - operation to be performed
	 */
	private void deleteUserHandler(DbQuery db , Command key)
	{
		DbUpdater<User> dbUpdater = new DbUpdater<>(db, key);
		dbUpdater.performAction(new IUpdate<User>() {
			/**
			 * Initialize statements for the Delete query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, User obj) throws SQLException {
				// TODO Auto-generated method stub
				stmt.setInt(1, obj.getuId());
			}
			/***
			 * Initialize query of the user Deletion 
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "DELETE FROM user "+
						"WHERE uId = ?;";
			}
		});
	}
	private void getOrderByCIdandBrId(DbQuery db , Command key) {
		DbGetter dbGetter = new DbGetter(db, key);
		Packet pack =db.getPacket();
		Account account = (Account) pack.getParameterForCommand(key).get(0);
		int brId = account.getBranchId();
		int cId = account.getCustomerId();
		dbGetter.performAction(new ISelect() {
			/***
			 * Initialize statements for the Selection query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				
				stmt.setInt(1, cId);
				stmt.setInt(2, brId);
				
			}
			/***
			 * Initialize the query of the survey conclusion getting 
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "SELECT oId,creationDate,requestedDate,stId,total FROM test.`order` where cId=? and brId=?;";
			}
			/***
			 * Parsing the result set in to a SurveyConclusion object
			 */
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				//new Order(id, creationDate, requestedDate, cId, stId, brId, total)
				int id = rs.getInt(1);
				java.sql.Date creation = rs.getDate(2);
				Timestamp requested = rs.getTimestamp(3);
				int cId = account.getCustomerId();
				int stId = rs.getInt(4);
				int brId=account.getBranchId();
				double total = rs.getDouble(5);
				
				return new Order(id, creation, requested, cId, stId, brId, total);
			}
		});
	}
	/***
	 * Handle get refunds
	 * @param db - database information
	 * @param key - command operation to perform
	 */
	private void getRefundsHandler(DbQuery db, Command key) {
		// TODO Auto-generated method stub
		DbGetter dbGetter = new DbGetter(db, key);
		
		dbGetter.performAction(new ISelect() {
			/**
			 * Statements to initialize for the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				// No statements needed
			}
			
			/**
			 * Perform Select query to get all the refunds
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "SELECT * FROM refund;";
			}
			/**
			 * Parse the result set to a Refund object
			 */
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return new Refund(rs.getInt(1),rs.getDate(2),rs.getDouble(3),rs.getInt(5));
			}
		});	
	}
	private void getProductsInOrder(DbQuery db, Command key) throws Exception {
		db.getPacket().addCommand(Command.getAllProductsInOrder);
		Packet packet = db.getPacket();
		ArrayList<ProductInOrder> prodLine = packet.<ProductInOrder>convertedResultListForCommand(Command.getOrderInProductsDetails);
		String valuesIn = new String("(");
		int i;
		for(i = 0 ; i < prodLine.size()-1;i++)
		{
			valuesIn+=prodLine.get(i).getProductId()+",";
		}
		valuesIn+=prodLine.get(i).getProductId()+")";
		
		String query = "select productName,price,typeId,product.pId \r\n" + 
		"from product inner join catalogproduct \r\n" + 
		"	on product.pId= catalogproduct.pId \r\n" + 
		"where product.pId in "+valuesIn+"\r\n" + 
		"union\r\n" + 
		"select 'custom product' as productName,price,typeId,product.pId \r\n" + 
		"from product inner join customproduct\r\n" + 
		"	on product.pId= customproduct.pId \r\n" + 
		"where product.pId in "+valuesIn+";";
		// get the new product id
			db.connectToDB();
			Connection con = db.getConnection();
	    	// set the statements from the implemention
	    	PreparedStatement stmtPid;
			
			stmtPid = con.prepareStatement(query);
		
	    	ResultSet rs = stmtPid.executeQuery();
	    	ArrayList<Object> prodList = new ArrayList<>();
			while (rs.next()) {
				String prodName = rs.getString(1);
				double price = rs.getDouble(2);
				int typeId = rs.getInt(3);
				int pId = rs.getInt(4);
				if(prodName.equals("custom"))
					prodList.add(new CustomProduct(price,typeId,pId));
				else
					prodList.add(new CatalogProduct(price,typeId,prodName,pId));
			}
	    	
			con.close();
			packet.setParametersForCommand(Command.getAllProductsInOrder, prodList);
			packet.addCommand(Command.getFlowersInProductInOrder);
			packet.setParametersForCommand(Command.getFlowersInProductInOrder, prodList);
			
	}
	/***
	 * Handle getting all of the replies 
	 * @param db - database information
	 * @param key - of the operation to perform
	 */
	private void getRepliesHandler(DbQuery db, Command key) {
		// TODO Auto-generated method stub
		
		DbGetter dbGetter = new DbGetter(db, key);
		
		dbGetter.performAction(new ISelect() {
			
			/***
			 * Adding statements to the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				// No statements needed
			}
			/***
			 * Define Select query to get all of the replies
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "SELECT * FROM reply;";
			}
			/***
			 * Parsing the data result to a Reply object
			 */
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return new Reply(rs.getInt(1),rs.getInt(2),rs.getString(3));
			}
		});
		
	}

	/**
	 *updating membership account  
	 * @param db - database information
	 * @param key - of the operation to perform
	 */
	public void updateMemberShipAccountByAcNumHandler(DbQuery db, Command key)
	{
		DbUpdater<MemberShipAccount> dbUpdate = new DbUpdater<>(db, key);
		
		dbUpdate.performAction(new IUpdate<MemberShipAccount>() {
			/**
			 * Perform a Update query to Update membership account 
			 */	
			@Override
			public String getQuery() {
				return "UPDATE MemberShipAccount SET mId=? , CreationDate=?  WHERE acNum = ?";
			}
			/**
			 * Adding statements to the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, MemberShipAccount obj) throws SQLException {
				stmt.setInt(1, obj.getmId());
				stmt.setDate(2,obj.getCreationDate());
				stmt.setInt(3,obj.getAcNum());
			}
		});
	}
	/**
	 *getting  membership account  by account number
	 * @param db - database information
	 * @param key - of the operation to perform
	 */
	public void getMemberShipAccountByAcNumHandler(DbQuery db, Command key)
	{
		DbGetter dbGetter = new DbGetter(db, key);
		
		dbGetter.performAction(new ISelect() {
			/**
			 * Adding statements to the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				int acNum = (int)packet.getParameterForCommand(key).get(0);
				stmt.setInt(1, acNum);

			}
			/**
			 * Perform a Select query to membership account by account number
			 */	
			@Override
			public String getQuery() {
				return "Select * from MemberShipAccount where acNum=?";
			}
			/***
			 * Parsing the data result to a membership account object
			 */
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				int acNum=rs.getInt(1);
				int mId=rs.getInt(2);
				java.sql.Date creationDate=rs.getDate(3);
				MemberShipAccount memacc=new MemberShipAccount(acNum, mId, creationDate);
				return (Object) memacc;
			}
		});
	}
	/**
	 *getting  membership by customer id number
	 * @param db - database information
	 * @param key - of the operation to perform
	 */
	private void getMembershipsBycID(DbQuery db, Command key) {
		DbGetter dbGetter = new DbGetter(db, key);
		
		dbGetter.performAction(new ISelect() {
			/**
			 * Adding statements to the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				int acNum = (int)packet.getParameterForCommand(key).get(0);
				stmt.setInt(1, acNum);

			}
			/**
			 * Perform a Select query to get membserhip account by customer id
			 */	
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "Select membershipaccount.acNum,membershipaccount.mId,membershipaccount.CreationDate from  account inner join MemberShipAccount on account.acNum= membershipaccount.acNum\r\n" + 
						"where cId=?";
			}
			/***
			 * Parsing the data result to a membership account object
			 */
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				int acNum=rs.getInt(1);
				int mId=rs.getInt(2);
				java.sql.Date creationDate=rs.getDate(3);
				
				return (Object) (new MemberShipAccount(acNum, mId, creationDate));
			}
		});
		
	}
	private void createOrder(DbQuery db, Command key) {
		Packet packet = db.getPacket();
		try {
			// get all parameters
			Order order = (Order)packet.getParameterForCommand(Command.createOrder).get(0);
			ArrayList<ProductInOrder> prodInOrderList = packet.<ProductInOrder>convertedResultListForCommand(Command.createProductsInOrder);
			ArrayList<OrderPayment> paymentList = packet.<OrderPayment>convertedResultListForCommand(Command.createOrderPayments);
			Delivery delivery = (Delivery)packet.getParameterForCommand(Command.createDelivery).get(0);
			// prepare the values string for query
			String formatSqlProdLines = "";
			String formatSqlPaymentsLines="";
			for (int i = 0; i < prodInOrderList.size(); i++)
			{
				ProductInOrder proLine = prodInOrderList.get(i);
				formatSqlProdLines += String.format("(%d,@oId, %d)", proLine.getProductId(),proLine.getQuantity());
				if (i != prodInOrderList.size() - 1)
					formatSqlProdLines += ",";
			}
			for (int i = 0; i < paymentList.size(); i++)
			{
				OrderPayment payment = paymentList.get(i);
				if(payment.getPaymentDate()!=null)
					formatSqlPaymentsLines += String.format("INSERT INTO orderpayment (oId,paymentMethod,amount,paymentDate) VALUES "+"(@oId,'%s', %.2f,'%s')", payment.getPaymentMethod(),payment.getAmount(),payment.getPaymentDate());
				else
					formatSqlPaymentsLines += String.format("INSERT INTO orderpayment (oId,paymentMethod,amount) VALUES "+"(@oId,'%s', %.2f)", payment.getPaymentMethod(),payment.getAmount());
				if (i != paymentList.size() - 1)
					formatSqlPaymentsLines += ";";
			}
			

			// create the connection to database
			db.connectToDB();
			Connection con = db.getConnection();
			
			String query = "INSERT INTO `order` (`creationDate`,`requestedDate`,`cId`,`stId`,`brId`,`Total`) VALUES (curdate(), ?,?,1,?,?); " + 
	    				"SET @oId = LAST_INSERT_ID(); "  
	    				 + formatSqlPaymentsLines + ";"+
	    				"INSERT INTO `test`.`productinorder`(`pId`,`oId`,`quantity`)VALUES" + formatSqlProdLines+";"+
	    				"INSERT INTO `test`.`delivery`(`Address`,`phone`,`receiver`,`oId`)"
	    				+ "VALUES('"+delivery.getAddress()+"','"+delivery.getPhone()+"','"+delivery.getReceiver()+"',@oId);"
	    				;
	    	
	 
	    	// set the statements from the implemention
	    	PreparedStatement stmt = con.prepareStatement(query);
	    	stmt.setTimestamp(1, order.getRequestedDate());
			stmt.setInt(2, order.getCustomerId());
			stmt.setInt(3, order.getBrId());
			stmt.setDouble(4, order.getTotal());
	    	stmt.executeUpdate();
	    
			con.close();
		}
		catch (Exception e)
		{
			packet.setExceptionMessage(e.getMessage());
		}
	}
	/**
	 *getting  account  by user id number
	 * @param db - database information
	 * @param key - of the operation to perform
	 */
	private void getAccountByuIdHandler(DbQuery db, Command key)
	{
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
			/**
			 * Adding statements to the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				Integer customerCid = (Integer) packet.getParameterForCommand(Command.getAccountByuId).get(0);
				stmt.setInt(1, customerCid);

			}
			/**
			 * Perform a Select query to get account by user id 
			 */	
			@Override
			public String getQuery() {
				return "SELECT acNum,brId,account.cId,balance,creditCard,status FROM account inner join customer on customer.cId=account.cId  where customer.uId=?";
			}
			/***
			 * Parsing the data result to a Account account object
			 */
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
				
				newacc=new Account(acNum, cId, 0, balance, brId, status, creditCard);
				return (Object)newacc;
				
			}
		});
	}
	/**
	 *getting  account by customer id number
	 * @param db - database information
	 * @param key - of the operation to perform
	 */
	private void getAccountbycID(DbQuery db, Command key) {
		DbGetter dbGet = new DbGetter(db, key);
		dbGet.performAction(new ISelect() {
			/**
			 * Adding statements to the query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				Integer customerCid = (Integer) packet.getParameterForCommand(Command.getAccountbycID).get(0);
				stmt.setInt(1, customerCid);

			}
			/**
			 * Perform a Select query to get account by customer id
			 */	
			@Override
			public String getQuery() {
				return "SELECT * FROM account  where account.cId=?";
			}
			/***
			 * Parsing the data result to a Account account object
			 */
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
				
				newacc=new Account(acNum, cId, 0, balance, brId, status, creditCard);
				return (Object)newacc;
				
			}
		});
		
	}
	private void getFlowerInProductInOrder(DbQuery db, Command key) throws Exception {
		Packet packet = db.getPacket();
		ArrayList<Product> prodLine = packet.<Product>convertedResultListForCommand(Command.getFlowersInProductInOrder);
		String valuesIn = new String("(");
		int i;
		for(i = 0 ; i < prodLine.size()-1;i++)
		{
			valuesIn+=prodLine.get(i).getId()+",";
		}
		valuesIn+=prodLine.get(i).getId()+")";
		String query = "Select pId,fId,quantity from flowerInProduct where pId in "+valuesIn+";";
		// get the new product id
			db.connectToDB();
			Connection con = db.getConnection();
	    	// set the statements from the implemention
	    	PreparedStatement stmtPid;
			
			stmtPid = con.prepareStatement(query);
		
	    	ResultSet rs = stmtPid.executeQuery();
	    	ArrayList<Object> floInProd = new ArrayList<>();
			while (rs.next()) {
				int pId = rs.getInt(1);
				int fId = rs.getInt(2);
				int quantity = rs.getInt(3);
				floInProd.add(new FlowerInProduct(fId,pId,quantity));
			}
	    	
			con.close();
			packet.setParametersForCommand(Command.getFlowersInProductInOrder, floInProd);
			packet.addCommand(Command.getAllFlowersInOrder);
			packet.setParametersForCommand(Command.getAllFlowersInOrder, floInProd);
	}
	private void getAllFlowersFromOrder(DbQuery db, Command key) throws Exception {
		Packet packet = db.getPacket();
		ArrayList<FlowerInProduct> prodLine = packet.<FlowerInProduct>convertedResultListForCommand(Command.getAllFlowersInOrder);
		String valuesIn = new String("(");
		int i;
		for(i = 0 ; i < prodLine.size()-1;i++)
		{
			valuesIn+=prodLine.get(i).getFlowerId()+",";
		}
		valuesIn+=prodLine.get(i).getFlowerId()+")";
		String query = "Select * from flower where fId in "+valuesIn+";";
		// get the new product id
			db.connectToDB();
			Connection con = db.getConnection();
	    	// set the statements from the implemention
	    	PreparedStatement stmtPid;
			
			stmtPid = con.prepareStatement(query);
		
	    	ResultSet rs = stmtPid.executeQuery();
	    	ArrayList<Object> floList = new ArrayList<>();
			while (rs.next()) {
				int fId = rs.getInt(1);
				String flower = rs.getString(2);
				floList.add(new Flower(fId,flower));
			}
	    	
			con.close();
			packet.setParametersForCommand(Command.getAllFlowersInOrder, floList);
	}
	private void getOrderPaymentsDetails(DbQuery db, Command key) {
		DbGetter dbGetter = new DbGetter(db, key);
		Packet pack =db.getPacket();
		int brId = ((Account) pack.getParameterForCommand(key).get(0)).getBranchId();
		dbGetter.performAction(new ISelect() {
			/***
			 * Initialize statements for the Selection query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				
				stmt.setInt(1, brId);				
			}
			/***
			 * Initialize the query of the survey conclusion getting 
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "SELECT pId,orderpayment.oId,paymentMethod,amount,paymentDate FROM test.orderpayment inner join `order` on orderpayment.oId=`order`.oId where `order`.brId=?;";
			}
			/***
			 * Parsing the result set in to a SurveyConclusion object
			 */
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				int pId = rs.getInt(1);
				int oId = rs.getInt(2);
				String method = rs.getString(3);
				double amount = rs.getDouble(4);
				java.sql.Date date = rs.getDate(5);

				return new OrderPayment(pId, oId, PaymentMethod.valueOf(method), amount, date);
			}
		});
		
	}
	private void getOrderProductsDetails(DbQuery db, Command key) {
		DbGetter dbGetter = new DbGetter(db, key);
		Packet packet =db.getPacket();
		int oId = (Integer) packet.getParameterForCommand(key).get(0);
		dbGetter.performAction(new ISelect() {
			/***
			 * Initialize statements for the Selection query
			 */
			@Override
			public void setStatements(PreparedStatement stmt, Packet packet) throws SQLException {
				
				stmt.setInt(1, oId);
			}
			/***
			 * Initialize the query of the survey conclusion getting 
			 */
			@Override
			public String getQuery() {
				// TODO Auto-generated method stub
				return "SELECT pId,quantity FROM test.productinorder where  oId=?;";
			}
			/***
			 * Parsing the result set in to a SurveyConclusion object
			 */
			@Override
			public Object createObject(ResultSet rs) throws SQLException {
				int pId = rs.getInt(1);
				int quantity = rs.getInt(1);

				return new ProductInOrder(oId, pId, quantity);
			}
		});
			
	}
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Packet packet = (Packet) msg;
		printlogMsg("from: "+client+" commands: "+packet.getCommands());
		DbQuery db = new DbQuery(user, password, packet, client,database);
		try {
			db.connectToDB();
			for (Command key : packet.getCommands())
			{
				switch(key)
				{
				case getCatalogProducts:
					getActiveCatalogProductsHandler(db, key);
					break;
				case updateCatalogProduct:
					updateCatalogProductHandler(db, key);
					break;
				case getFlowers:
					getFlowersHandler(db, key);
					break;
				case getMemberShip:
					getMemberShipHandler(db, key);
					break;
				case getUsers:
					getUsersHandler(db, key);
					break;
				case addCustomers:
					addCustomertHandler(db,key);
					break;
				case addUsers:
					addUserHandler(db, key);
					break;
				case getUsersByUserName:
					getUserByUserNameHandler(db, key);
					break;
				case addAccounts:
					addAccountrHandler(db, key);
					break;
				case getCustomersKeyByuId:
					getCustomersKeyByuIdHandler(db, key);
					break;
				case getUserByuId:
					getUserByuIdHandler(db, key);
					break;
				case getAccountbycIDandBranch:
					getAccountbycIDandBranchHandler(db, key);;
					break;
				case updateUserByuId:
					updateUserByuIdHandler(db, key);
					break;
				case updateCustomerByuId:
					updateCustomerByuIdHandler(db, key);
					break;
				case getColors:
					getColors(db,key);
					break;
				case addFlower:
					createFlower(db,key);
					break;
				case getDiscountsByBranch:
					getDiscounts(db,key);
					break;
				case updateProduct:
					updateProductHandler(db, key);
					break;
				case deleteFlowersInProduct:
					deleteFlowersInProductHandler(db, key);
					break;
				case getFlowersInProducts:
					getFlowersInProductHandler(db, key);
					break;
				case getProductTypes:
					getProductTypesHandler(db, key);
					break;
				case insertCatalogProduct:
					insertCatalogProductHandler(db, key);
					break;
				case updateFlowersInProduct:
					updateFlowersInProductHandler(db, key);
					break;
				case updateCatalogImage:
					updateImageInProductHandler(db, key);
					break;
				case getCatalogImage:
					getCatalogImageHandler(db, key);
					break;
				case getBranches:
					getBranchesHandler(db, key);
					break;
				case getBranchSales:
					getBranchSalesHandler(db, key);
					break;
				case updateComplain:
					updateComplainHandler(db,key);
					break;
				case addComplain:
					addComplainHandler(db,key);
					break;
				case getComplains:
					getComplainsHandler(db, key);
					break;
				case addReply:
					addReplyHandler(db,key);
					break;
				case addComplainRefund:
					addComplainRefundHandler(db,key);
					break;
				case getReplies:
					getRepliesHandler(db,key);
					break;
				case getRefunds:
					getRefundsHandler(db,key);
					break;
				case addSurvey:
					addSurveyHandler(db, key);
					break;
				case addQuestions:
					addQuestionHandler(db,key);
					break;
				case addQuestionsToServey:
					addQuestionsToSurveyHandler(db,key);
					break;
				case getSurvey:
					getSurveyHandler(db, key);
					break;
				case getQuestions:
					getQuestionsHandler(db, key);
					break;
				case getSurveyQuestions:
					getSurveyQuestionsHandler(db, key);
					break;
				case addAnswerSurvey:
					addAnswerSurveyHandler(db,key);
					break;
				case getAverageAnswersBySurveyId:
					getAverageAnswersBySurveyIdHandler(db, key);
					break;
				case addConclusion:
					addConclusionHandler(db,key);
					break;
				case getConclusions:
					getConclusionsHandler(db, key);
					break;
				case updateAccountsBycId:
					updateAccountsBycIdHandler(db,key);
					break;
				case setProductAsDeleted:
					setProductAsDeletedHandler(db, key);
					break;
				case getAllCatalogProducts:
					getAllCatalogProductsHandler(db, key);
					break;
				case addSaleCatalogInBranch:
					addSaleCatalogInBranchHandler(db, key);
					break;
				case deleteSaleCatalogInBranch:
					deleteSaleCatalogInBranchHandler(db, key);
					break;
				case getBranchBybrId:
					getBranchBybrIdHandler(db, key);
					break;
				case updateSurvey:
					updateSurveyHandler(db,key);
					break;
				case getUserByNameAndPass:
					getUserByNameAndPassHandler(db, key);
					break;
				case getEmployeeByUid:
					getEmployeeByuIdHandler(db, key);
					break;
				case setUserLoggedInState:
					updateUserIsLoggedHandler(db, key);
					break;
				case getComplainsForReport:
					getComplainsForReportHandler(db,key);
					break;
				case getIncomeReport:
					getIncomeReportHandler(db,key);
					break;
				case CreateCustomProduct:
					createCustomProduct(db,key);
					break;
				case getOrderReport:
					getOrderReportHandler(db,key);
					break;
				case getSatisfactionReport:
					getSatisfactionReportHandler(db,key);
					break;
				case deleteUser:
					deleteUserHandler(db,key);
					break;
				case getBranchesIncludeService:
					getBranchesIncludeServiceHandler(db,key);
					break;
				case addMemberShipAccount:
					addMemberShipAccountHandler(db,key);
					break;
				case updateMemberShipAccountByAcNum:
					updateMemberShipAccountByAcNumHandler(db,key);
					break;
				case getMemberShipAccountByAcNum:
					getMemberShipAccountByAcNumHandler(db,key);
					break;
				case getAccountbycID:
					getAccountbycID(db,key);
					break;
				case getMemberShipAccount:
					getMembershipsBycID(db,key);
					break;
				case createOrder:
					createOrder(db,key);
					break;
				case getAccountByuId:
					getAccountByuIdHandler(db,key);
					break;
				case getOrdersByCIdandBrId:
					getOrderByCIdandBrId(db, key);
					break;
				case getPaymentDetails:
					getOrderPaymentsDetails(db,key);
					break;
				case getOrderInProductsDetails:
					getOrderProductsDetails(db,key);
					getProductsInOrder(db,key);
					getFlowerInProductInOrder(db,Command.getFlowersInProductInOrder);
					getAllFlowersFromOrder(db, Command.getFlowersInProducts);
					break;
				case deleteMemberShipAccountByacNum:
					deleteMemberShipAccountByacNumHandler(db,key);
					break;
				case getCustomers:
					getCustomersHandler(db, key);
					break;

					default:;
				}
				
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
