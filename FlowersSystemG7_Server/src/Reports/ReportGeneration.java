package Reports;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Branches.Branch;
import Logic.DbQuery;

/**
 * This is an abstract class that uses for all reports types,
 * each report that extend this class, will need to implement the creationCsvData method,
 * and it can perform the report for each branch
 */
public abstract class ReportGeneration implements IReport
{	
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

	/**
	 * Get The collection of all branches in the database
	 * @throws Exception Exception when failed to get all branches
	 */
	private ArrayList<Branch> getAllBranches() throws Exception
	{
		ArrayList<Branch> branchesList = new ArrayList<>();
		
		try
		{
			db.connectToDB();
			
			// create the connection to db
			Connection con = db.getConnection();
						
			// query for get all branches
			String qry = "SELECT brId, brName FROM branch WHERE brName <> 'Service';";
					
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
		// pass over each branch and check if there is a quarter report for it on the database,
		// if report is not found, create for it a new report
		for (Branch branch : branchesList)
		{
			int branchId = branch.getbId();
			
			// generate report only for branch that there is no report for it
			if (!checkIfThereIsQuarterReportForBranch(branchId))
			{
				// creates the data report for current branch
				ArrayList<String[]> csvData = createCsvData(branchId);
				
			    // generate absolute path for the csv file
				Branch matchedBranch = getBranchByBranchId(branchId);

				String absolutePath = String.format("Reports\\%s\\%s\\%s\\%s.csv", 
				    matchedBranch.getName(), year, quarter, this);
				    
				// create and write the csv file based on the collection data
				writeCSVFile(csvData, absolutePath);
				    
				// save the line of relevant report on database
				saveReportPathInDb(branchId, absolutePath);
			}
		}
	}
	
	/**
	 * Saves the report line with it path in the database
	 * @param branchId The branch id for report
	 * @param csvFile The path for csv file report
	 * @throws Exception Throws when there was an exception during the saving in database
	 */
	private void saveReportPathInDb(int branchId, String csvFile) throws Exception
	{
	    try
	    {
	    	db.connectToDB();
			Connection con = db.getConnection();
			
	    	String query = "INSERT INTO report (year,quarter, branch, report, path) VALUES (?,?,?,?,?)";
	    
	    	PreparedStatement stmt = con.prepareStatement(query);
	    	stmt.setInt(1, year);
	    	stmt.setInt(2, quarter);
	    	stmt.setInt(3, branchId);
	    	stmt.setString(4, this.toString());
	    	stmt.setString(5, csvFile);
	    	
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
	 * Read and get the report from the csv file as String Array for specified branch id
	 * @param branchId Branch id to create for it the report
	 * @return Collection of Order report
	 * @throws Exception Exception when failed on reading the report
	 */
	protected ArrayList<String[]> getReportInString(int branchId) throws Exception
	{
		// get from db the absolute path of the ready report
		String path = getReportPathFromDb(branchId);
		
		// read the csv file, and set it to the attribute
		ArrayList<String[]> csvData = readCSVFile(path);
		
		return csvData;
	}

	/**
	 * Check if there is a quarter report for specified branch
	 * @param branchId The branch id to check for it
	 * @return true - quarter report exists, false - not exists
	 */
	private boolean checkIfThereIsQuarterReportForBranch(int branchId)
	{
		boolean result = false;
		try
		{
			String path = getReportPathFromDb(branchId);
			if (path.isEmpty())
				result = false;
			else
				result = true;
		}
		catch (Exception e)
		{
			result = false;
		}
		
		return result;
	}
	
	/**
	 * get the report path from the database
	 * @param branchId The branch id for report
	 * @throws Exception Throws when there was an exception during the saving in database
	 */
	private String getReportPathFromDb(int branchId) throws Exception
	{
		String path = "";
	    try
	    {
	    	db.connectToDB();
			Connection con = db.getConnection();
			
	    	String query = "SELECT path FROM report WHERE year=? AND quarter=? AND branch=? AND report=?";
		
	    
	    	PreparedStatement stmt = con.prepareStatement(query);
	    	stmt.setInt(1, year);
	    	stmt.setInt(2, quarter);
	    	stmt.setInt(3, branchId);
	    	stmt.setString(4, this.toString());
	    
	    	
	    	ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) 
			{
			   path = rs.getString(1);
			}
	    }
	    catch (Exception e) { throw e; }
	    finally {
	    	db.connectionClose();
	    }
	    
	    return path;
	}
	
	/**
	 * Read and get the report for specified branch id
	 * @param branchId Branch id to create for it the report
	 * @return Collection of relevant report
	 * @throws Exception Exception when failed on reading the report
	 */
	public ArrayList<Object> getReport(int branchId) throws Exception
	{
		ArrayList<String[]> csvData = getReportInString(branchId);
		
		// convert each column in string array to relevant report entity
		ArrayList<Object> report = new ArrayList<>();
		
		for (String[] row : csvData)
		{
			// call to implementation of create object on the concrete class
			Object obj = createObject(row);
			report.add(obj);
		}

		return report;
	}
	
	/** 
	 * Create all the data of the report for specified branch.
	 * Each column is shown as string array
	 * @param branchId The branch id to perform for it the csv data
	 * @return The csv data of all branches as ArrayList
	 */
	private ArrayList<String[]> createCsvData(int branchId) throws Exception {

		ArrayList<String[]> csvData = new ArrayList<>();
		try
		{
			db.connectToDB();
			// create the connection to db
			Connection con = db.getConnection();
						
			// query for get all orders in the quarter
			String qry = getQueryReport(branchId);
					
			PreparedStatement stmt = con.prepareStatement(qry);
			stmt.setInt(1,branchId);
			stmt.setInt(2,year);
			stmt.setInt(3,quarter);
			
			ResultSet rs = stmt.executeQuery();
			
			int nCol = rs.getMetaData().getColumnCount();
			
			while (rs.next()) 
			{
			   String[] row = new String[nCol];
			   for( int iCol = 1; iCol <= nCol; iCol++)
			   {
			      Object obj = rs.getObject(iCol);
				  row[iCol-1] = (obj == null) ? null : obj.toString();
			   }
			   csvData.add( row );
			}
		}
		catch (Exception e) { throw e; }
		finally {
			db.connectionClose();
		}
		
		return csvData;
	}
	
	/**
	 * Write the collection of csv data to absolute path 
	 * @param csvFile The csv file name and path
	 * @param csvData The csv data to write
	 * @throws IOException Exception when failed to write the csv file
	 */
	private void writeCSVFile(ArrayList<String[]> csvData, String csvFile) throws IOException {

		// create the directories if not exists
		Path pathToFile = Paths.get(csvFile);
		Files.createDirectories(pathToFile.getParent());
		
		PrintWriter pw = new PrintWriter(new File(csvFile));
		StringBuilder sb = new StringBuilder();

		for (String[] s : csvData)
		{
			// pass over all columns and append it to the string builder
			for (int i = 0; i < s.length; i++)
			{
				if (s[i] != null)
				{
					sb.append(s[i]);
				}
				else
				{
					sb.append(" ");
				}
				
				// append ',' for each column without the last column
				if (i != s.length - 1)
					sb.append(',');
			}

			sb.append('\n');
		}
		pw.write(sb.toString());
		pw.close();
	}

	/**
	 * Read and return the collection from csv file
	 * @param csvFile The csv file name and path
	 */
	private ArrayList<String[]> readCSVFile(String csvFile) {
		ArrayList<String[]> csvDataFile = new ArrayList<>();
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] lineArr = line.split(cvsSplitBy);
				csvDataFile.add(lineArr);
			}

		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return csvDataFile;
	}



}
