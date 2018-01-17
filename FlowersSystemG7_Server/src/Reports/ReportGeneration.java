package Reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import Branches.Branch;
import Logic.DbQuery;

/**
 * This is an abstract class that uses for all reports types,
 * each report that extend this class, will need to implement the creationCsvData method,
 * and it can perform the report for each branch
 */
public abstract class ReportGeneration implements IReport
{	
	protected ArrayList<String[]> csvData = new ArrayList<>();
	protected ArrayList<Branch> branchesList = new ArrayList<>();
	protected int year;
	protected int quarter;
	protected DbQuery db;
	
	/**
	 * Constructor for initialize the year and the quarter of creation report
	 * @param db The database object details
	 * @param year The year of creation report
	 * @param quarter The quarter of creation report
	 */
	public ReportGeneration(DbQuery db, int year, int quarter)
	{
		this.db = db;
		this.year = year;
		this.quarter = quarter;
		
		try
		{
			this.branchesList = getAllBranches();
		}
		catch (Exception e)
		{
			this.branchesList = new ArrayList<>();
		}
	}
	
	public abstract LinkedHashMap<Integer, ArrayList<String[]>> createCsvDataForEachBranch();
	
	/**
	 * Get The collection of all branches in the database
	 * @throws Exception Exception when failed to get all branches
	 */
	private ArrayList<Branch> getAllBranches() throws Exception
	{
		ArrayList<Branch> branchesList = new ArrayList<>();
		
		db.connectToDB();
		
		try
		{
			// create the connection to db
			Connection con = db.getConnection();
						
			// query for get all branches
			String qry = "SELECT brId, brName FROM branch;";
					
			PreparedStatement stmt = con.prepareStatement(qry);
		
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) 
			{
				int brId = rs.getInt(1);
				String brName = rs.getString(2);
				branchesList.add(new Branch(brId, brName));
			}			
		}
		catch (Exception e) { throw e; }
		finally {
			db.connectionClose();
		}
		return branchesList;
	}
	
	/**
	 * Get the instance of branch by it's branch id
	 * @param branchId The branch id
	 * @return The Branch instance if founds, null if not founds
	 */
	private Branch getBranchByBranchId(int branchId)
	{
		for (Branch branch : branchesList)
		{
			if (branch.getbId() == branchId)
				return branch;
		}
		return null;
	}
	
	/**
	 * Perform and generate the report for each branch in the system,
	 * and save it as csv file in the relevant path and in the db
	 * @throws Exception The Exception when failed to generation the report
	 */
	public void performReport() throws Exception
	{
		LinkedHashMap<Integer, ArrayList<String[]>> csvDataBranch = createCsvDataForEachBranch();
		
		for (Map.Entry<Integer, ArrayList<String[]>> entry : csvDataBranch.entrySet())
		{
		    int branchId = entry.getKey();
		    ArrayList<String[]> csvData = entry.getValue();
		    
		    // generate absolute path for the csv file
		    Branch matchedBranch = getBranchByBranchId(branchId);
		    
		    String absolutePath = String.format("Reports/{0}/{1}/{2}/{3}.csv", 
		    		matchedBranch.getName(), year, quarter, this);
		    
		    // create and write the csv file based on the collection data
		    writeCSVFile(csvData, absolutePath);
		    
		    // save the line of relevant report on database
		    saveReportInDb(branchId, absolutePath);
		}
	}
	
	/**
	 * Saves the report line in the database
	 * @param branchId The branch id for report
	 * @param csvFile The path for csv file report
	 * @throws Exception Throws when there was an exception during the saving in database
	 */
	private void saveReportInDb(int branchId, String csvFile) throws Exception
	{
		db.connectToDB();
		Connection con = db.getConnection();
		
	    try
	    {
	    	String query = "INSERT INTO reports (year,quarter, branch, report, path) VALUES (?,?,?,?,?)";
		
	    
	    	PreparedStatement stmt = con.prepareStatement(query);
	    	stmt.setInt(1, year);
	    	stmt.setInt(2, quarter);
	    	stmt.setInt(3, branchId);
	    	stmt.setString(4, this.toString());
	    	stmt.setString(5, csvFile);
	    	
	    	stmt.executeUpdate();
	    }
	    catch (Exception e) { throw e; }
	    finally {
	    	db.connectionClose();
	    }
	}
	
	/**
	 * Write the collection of csv data to absolute path 
	 * @param csvData The csv data collection
	 * @param csvFile The csv file name and path
	 * @throws FileNotFoundException Exception when failed to write the csv file
	 */
	private void writeCSVFile(ArrayList<String[]> csvData, String csvFile) throws FileNotFoundException {

		PrintWriter pw = new PrintWriter(new File(csvFile));
		StringBuilder sb = new StringBuilder();

		for (String[] s : csvData)
		{
			// pass over all columns and append it to the string builder
			for (int i = 0; i < s.length; i++)
			{
				sb.append(s[i]);
				
				// append ',' for each column without the last column
				if (i != s.length - 1)
					sb.append(',');
			}

			sb.append('\n');
		}
		pw.write(sb.toString());
		pw.close();
	}


}
