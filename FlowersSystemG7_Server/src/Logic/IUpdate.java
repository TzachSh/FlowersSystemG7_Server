package Logic;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IUpdate<T>
{
	String getQuery();
	void setStatements(PreparedStatement stmt, T obj) throws SQLException;
}
