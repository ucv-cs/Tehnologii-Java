package jlib.utils;

import java.sql.*;

/**
 * Utility class for handling database operations.
 */
public class Database {
	public static Connection connection = null;

	/**
	 * Connects to the databaseUrl.
	 *
	 * @param databaseUrl
	 */
	public static void connect(String databaseUrl) {
		try {
			connection = DriverManager.getConnection(databaseUrl);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Disconnects the database.
	 */
	public static void disconnect() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Executes a given SELECT query on the database, using executeQuery().
	 *
	 * @param sql the query
	 * @return the result set
	 */
	public static ResultSet read(String sql) {
		ResultSet resultSet = null;
		Statement statement;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * Executes a given INSERT, UPDATE or DELETE query on the database, using
	 * executeUpdate().
	 *
	 * @param sql the query
	 */
	public static void modify(String sql) {
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}