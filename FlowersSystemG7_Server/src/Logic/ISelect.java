package Logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import PacketSender.Packet;

public interface ISelect
{
	String getQuery();
	void setStatements(PreparedStatement stmt, Packet packet) throws SQLException;
	Object createObject(ResultSet rs) throws SQLException;
}
