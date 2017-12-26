package Logic;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ISelect
{
	String getQuery();
	Object createObject(ResultSet rs) throws SQLException;
}
