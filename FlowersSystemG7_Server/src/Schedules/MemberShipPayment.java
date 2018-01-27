package Schedules;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import Logic.DbQuery;

/**
 * This class perform paying for all users in specify month
 */
public class MemberShipPayment {

	private int month;
	private int year;
	private DbQuery db;
	private ArrayList<String> accountsPays = new ArrayList<>();
	/**
	 * Constructor for initialize the month for payment
	 * @param db The database object details
	 * @param month The month for paying
	 * @param year The year for paying
	 */
	public MemberShipPayment(DbQuery db, int year, int month)
	{
		this.db = db;
		this.year = year;
		this.month = month;
	}
	
	/**
	 * Get The list of all accounts that system performed payment for them
	 * @throws Exception Exception when failed to get all accounts
	 * @return list of all accounts that system performed payment for them
	 */
	private ArrayList<String> getAllAccountThatPerformedPayment() throws Exception
	{
		ArrayList<String> accounts = new ArrayList<>();
		try
		{
			db.connectToDB();
			
			// create the connection to db
			Connection con = db.getConnection();
						
		
			String qry = "SELECT c.uId, u.user, sum(op.amount) 'TotalPayment' " + 
					"FROM `order` o INNER JOIN customer c ON c.cId = o.cId " + 
					"			   INNER JOIN user u ON u.uId = c.uId " + 
					"			   INNER JOIN orderpayment op ON op.oId = o.oId " + 
					"WHERE year(o.creationDate) = ? AND month(o.creationDate) = ? AND op.paymentDate IS NULL AND o.stId <> 3 " + 
					"GROUP BY c.uId";
					
			PreparedStatement stmt = con.prepareStatement(qry);
			stmt.setInt(1, year);
			stmt.setInt(2, month);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) 
			{
				int uId = rs.getInt(1);
				String user = rs.getString(2);
				double totalPayment = rs.getDouble(3);
				
				String format = String.format("Charged -> %d (%s) | Total: %.2f$", uId, user, totalPayment);
				accounts.add(format);
			}			
		}
		catch (Exception e) { throw e; }
		finally {
			db.connectionClose();
		}
		
		return accounts;
	}	
	
	/**
	 * Update database for charging each membership account that not payed yet for instance month
	 * @throws Exception Exception when failed on updating
	 */
	private void updateChargingOnDataBase() throws Exception
	{
		try
	    {
	    	db.connectToDB();
			Connection con = db.getConnection();
			

	    	String query =  "SET @creationYear = ?; " + 
			    			"SET @creationMonth = ?; " + 
			    			"UPDATE orderpayment op INNER JOIN `order` o ON op.oId = o.oId " + 
			    			"SET op.paymentDate = CURDATE() " + 
			    			"WHERE year(o.creationDate) = @creationYear AND month(o.creationDate) = @creationMonth AND op.paymentDate IS NULL AND o.stId <> 3 ;";
	    
	    	PreparedStatement stmt = con.prepareStatement(query);
	    	stmt.setInt(1, year);
	    	stmt.setInt(2, month);
	    	stmt.executeUpdate();
	    }
	    catch (SQLException e) 
	    {
	    	if (!e.getMessage().toLowerCase().contains("duplicate"))
	    		throw e;
	    }
	    catch (Exception e) { throw e; }
	    finally {
	    	db.connectionClose();
	    }
	}
	
	/**
	 * Performs the payments for all membership customers in the instance month
	 * @throws Exception Throws when there was an exception during the updating in database
	 */
	public void performPaying() throws Exception
	{
		// get all accounts that will be charged
		this.accountsPays = getAllAccountThatPerformedPayment();
		
		if (accountsPays.size() > 0)
		{
			// update charging on database
			updateChargingOnDataBase();
		}
	}
	
	/**
	 * Check if date is the first day of the month
	 * @param calendar The calendar date for checking
	 * @return true if it the first day of month else false
	 */
	public static boolean isFirstDayofMonth(Calendar calendar){
	    if(calendar == null)
	        return false;

	    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
	    return (dayOfMonth == 1);
	}
	
	public ArrayList<String> getChargedAccounts()
	{
		return accountsPays;
	}
	
}
