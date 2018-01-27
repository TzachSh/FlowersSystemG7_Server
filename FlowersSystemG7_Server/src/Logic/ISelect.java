package Logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import PacketSender.Packet;

/**
 * Interface that server can use for implements their select requests to database when the result was arrived from the client
 *
 */
public interface ISelect
{
	/**
	 * Return the relevant query for the request
	 * @return sql query
	 */
	String getQuery();
	
	/**
	 * Set all the statements that used on the query
	 * 
	 * @param stmt The PreparedStatement object for insert all the values for each statement
	 * @param packet The Packet object, can used for get details from client; ex: filters, id etc..
	 * @throws SQLException Throws an exception when failed on statement
	 */
	void setStatements(PreparedStatement stmt, Packet packet) throws SQLException;
	
	/**
	 * Create the object for each result that returns from the database
	 * 
	 * @param rs ResultSet that contains all the result rows that returns from Database
	 * @return The object that created from the resultSet iteration 
	 * @throws SQLException Throws an exception when failed
	 */
	Object createObject(ResultSet rs) throws SQLException;
}
