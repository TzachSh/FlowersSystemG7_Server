package Schedules;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Logic.DbQuery;

/**
 * This class uses for the schedule that check every night if there is memberships accounts<br>
 * That their date are passed
 */
public class MemberShipDeleting {

	private DbQuery db;
	private ArrayList<String> accountsDeleted = new ArrayList<>();
	
	/**
	 * Constructor for initialize the month for payment
	 * @param db The database object details
	 */
	public MemberShipDeleting(DbQuery db)
	{
		this.db = db;
	}
	
	/**
	 * Get The list of all accounts that system delete their membership account
	 * @throws Exception Exception when failed to get all accounts
	 */
	private ArrayList<String> getAllAccountThatDeleted() throws Exception
	{
		ArrayList<String> accounts = new ArrayList<>();
		try
		{
			db.connectToDB();
			
			// create the connection to db
			Connection con = db.getConnection();
						
		
			String qry = "SELECT u.uId, u.user, ms.memberShipType, msa.CreationDate " + 
					"FROM membershipaccount msa INNER JOIN membership ms ON msa.mId = ms.mId " + 
					"						   INNER JOIN account a ON a.acNum = msa.acNum " + 
					"                           INNER JOIN customer c ON a.cId = c.cId " + 
					"                           INNER JOIN user u ON u.uId = c.uId " + 
					"WHERE TIMESTAMPDIFF(MONTH, msa.CreationDate, CURDATE()) >= IF(msa.mId = 1, 1, 12);";
			//(select mId from membership where member='Monthly')
					
			PreparedStatement stmt = con.prepareStatement(qry);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) 
			{
				int uId = rs.getInt(1);
				String user = rs.getString(2);
				String type = rs.getString(3);
				String date = rs.getString(4);
				
				String format = String.format("Membership Deleted -> %d (%s) | %s | Created: %s", uId, user, type, date);
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
	 * Delete all memberships accounts that estimate date passed
	 * @throws Exception Exception when failed on deleting
	 */
	private void deleteMembershipsWhenPassedTheTime() throws Exception
	{
		try
	    {
	    	db.connectToDB();
			Connection con = db.getConnection();
			
	    	String query = "DELETE FROM membershipaccount\r\n" + 
	    				   "WHERE TIMESTAMPDIFF(MONTH, CreationDate, CURDATE()) >= IF(mId = 1, 1, 12);";
	    
	    	PreparedStatement ps = con.prepareStatement(query);
	    	ps.executeUpdate();
	    }
	    catch (Exception e) { throw e; }
	    finally {
	    	db.connectionClose();
	    }
	}
	
	/**
	 * Performs deleting all membership customers that passed
	 * @throws Exception Throws when there was an exception during the deleting
	 */
	public void performDeleteAllMembershipsThatPassed() throws Exception
	{
		this.accountsDeleted = getAllAccountThatDeleted();
		
		if (accountsDeleted.size() > 0)
		{
			deleteMembershipsWhenPassedTheTime();
		}
 	}
	
	public ArrayList<String> getMembershipDeleted()
	{
		return accountsDeleted;
	}
	
}
