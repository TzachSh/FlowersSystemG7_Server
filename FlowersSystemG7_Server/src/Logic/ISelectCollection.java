package Logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ISelectCollection<T, E>
{
	String getQuery();
	void setStatements(PreparedStatement stmt, T obj) throws SQLException;
	E createObject(ResultSet rs, T mainObj) throws SQLException;
	void addCollectionToObject(T obj, ArrayList<E> objList) throws SQLException;
}
