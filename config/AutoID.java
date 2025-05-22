package config;

import java.sql.Connection;

public class AutoID {
	static Connection con = null;
	static ClsDBConnection connect = new ClsDBConnection();

	public static String getAutoID(String field, String table, String prefix) throws ClassNotFoundException {
		if (table.equals("sale") || table.equals("purchase") || table.equals("medicine") || table.equals("order")) {
			return connect.getPrimaryKey(field, table, prefix);
		} else {
			return connect.getPrimaryKey2(field, table, prefix);
		}
	}

}
