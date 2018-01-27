package Logic;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Interface that server can use for implements their update requests to database when the result was arrived from the client
 *
 */
public interface IUpdate<T>
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
	 * @param obj The object that needed for updating
	 * @throws SQLException Throws an exception when failed on statement
	 */
	void setStatements(PreparedStatement stmt, T obj) throws SQLException;
}
