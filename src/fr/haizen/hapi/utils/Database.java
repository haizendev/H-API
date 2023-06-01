package fr.haizen.hapi.utils;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

@Getter
public class Database {

	private Connection connection;
	private String host;
	private String database;
	private String username;
	private String password;
	private int port;

	public Database(String host, int port, String database, String username, String password) {
		this.host = host;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;
		this.connectDatabase();
	}

	public void connectDatabase() {
		try {
			this.connection = DriverManager.getConnection(
					"jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true",
					this.username, 
					this.password
			);
			System.out.println("[Base de donnée] Base de donnée ciblant " + this.host + " connectée avec succès!");
		} catch (SQLException exception) {
			System.out.println("[Base de donnée] Probleme pour se connecter " + exception.getMessage());
			exception.printStackTrace();
		}
	}

	public boolean isConnected() {
		if (this.connection == null) {
			return false;
		}
		return true;
	}

	public void execute(String request) {
		try {
			Statement statement = this.connection.createStatement();
			statement.execute(request);
			statement.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
	
	public ResultSet query(String query) {
		if (!this.isConnected()) {
			return null;
		}
		ResultSet rs = null;
		try {
			Statement st = getConnection().createStatement();
			rs = st.executeQuery(query);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return rs;
	}

	public void update(String query) {
		if (!this.isConnected()) {
			return;
		}
		try {
			Statement st = getConnection().createStatement();
			st.executeUpdate(query);
			st.close();
		} catch (SQLException e) {
			if (! (e instanceof MySQLSyntaxErrorException)) {
				e.printStackTrace();
			}
		}
	}

	public boolean tableExists(String table) {
		if (!this.isConnected()) {
			return false;
		}
		if (table == null) {
			return false;
		}
		try {
			DatabaseMetaData metadata = this.getConnection().getMetaData();
			if (metadata == null) {
				return false;
			}
			ResultSet rs = metadata.getTables(null, null, table, null);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public boolean exists(String table, String column, String data) {
		if (!this.isConnected()) {
			return false;
		}
		if (data != null) {
			data = "'" + data + "'";
		}
		try {
			ResultSet rs = query("SELECT * FROM " + table + " WHERE " + column + "=" + data);
			while (rs.next()) {
				if (rs.getString(column) != null) {
					return true;
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public int countRows(String table) {
		if (! this.isConnected()) {
			return -1;
		}
		try {
			ResultSet set = this.query("SELECT COUNT(*) FROM " + table);
			if (set.next()) {
				return set.getInt(1);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	
	public void createTable(String tablename, String... values) {
		String stmt = "CREATE TABLE IF NOT EXISTS " + tablename + "(";

		for (int i = 0; i < values.length; i++) {
			if (i == values.length - 1) {
				stmt = stmt.concat(values[i]);
			} else
				stmt = stmt.concat(values[i] + ", ");
		}
		stmt = stmt.concat(");");
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(stmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteTable(String tablename) {
		String sql = "DROP TABLE IF EXISTS " + tablename;
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void resetTable(String tablename, String[] values) {
		deleteTable(tablename);
		createTable(tablename, values);
	}
	
	public void updateAll(String table, String[] columns, Object[] values, String conditions) {
		String statement = "UPDATE " + table + " SET ";

		for(int i = 0; i < columns.length; i++) {
			String column = columns[i];
			Object value = values[i];
			
			statement += column + " = '" + value + "',";
		}
		
		statement = statement.substring(0, statement.length() - 1); // enlevé la virgule
		statement += " WHERE " + conditions + ";";
		
		this.update(statement);
	}
	
	public void insertIntoOrUpdate(String table, String[] columns, Object[] values, boolean update) {
		String statement = "INSERT INTO " + table;

		String c = "(";
		for (int i = 0; i < columns.length; i++) {
			if (i == columns.length - 1) {
				c = c + columns[i];
			} else
				c = c + columns[i] + ",";
		}
		c = c + ")";

		String v = "(";
		for (int i = 0; i < values.length; i++) {
			Object value = values[i];
			// - TODO: BETTER TYPE CONVERSION
			if (value instanceof Boolean) {
				Boolean booleanValue = (Boolean) value;
				value = booleanValue ? 1 : 0;
			}
			
			if (value instanceof String) {
				v += "'" + value + "',";
			} else {
				v += value + ",";
			}
		}
		v = v.substring(0, v.length() - 1);
		v += ")";

		statement = statement + c + " VALUES" + v;	
		
		if (update) {
			statement += " ON DUPLICATE KEY UPDATE ";
			for(int i = 0; i < columns.length; i++) {
				String column = columns[i];
				Object value = values[i];
				
				if (value instanceof Boolean) {
					Boolean booleanValue = (Boolean) value;
					value = booleanValue ? 1 : 0;
				}
				
				
				statement += column + " = '" + value + "',";
			}
			statement = statement.substring(0, statement.length() - 1); // enlevé la virgule
		}
		
		statement += ";";
		update(statement);
	}
}