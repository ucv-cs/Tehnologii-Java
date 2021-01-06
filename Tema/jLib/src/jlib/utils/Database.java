package jlib.utils;

import java.sql.*;

/**
 * Utility class for handling database operations.
 */
public class Database {
	public static Connection connection = null;
	private final static String database = "jdbc:sqlite:src/jlib/storage/jlib.db";

	/**
	 * Connects to the database.
	 */
	public static Connection connect() {
		try {
			connection = DriverManager.getConnection(database);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return connection;
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
			System.err.println(e.getMessage());
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
			System.err.println(e.getMessage());
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
			System.err.println(e.getMessage());
		}
	}

}