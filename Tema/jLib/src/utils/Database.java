package utils;

import java.sql.*;

public class Database {
	public static Connection connection = null;
	private final static String database = "jdbc:sqlite:src/storage/jlib.db";

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
 * Executes a given SELECT query on the database.
 *
 * @param sql the query
 * @return the result set
 * @throws SQLException
 */
	public static ResultSet read(String sql) throws SQLException {
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
		return resultSet;
	}

	/**
	 * Executes a given INSERT, UPDATE or DELETE query on the database.
	 *
	 * @param sql the query
	 */
	public static void modify(String sql) {
		Statement statement;
		try {
			statement = connection.createStatement();
			// For INSERT, UPDATE or DELETE use the executeUpdate() method and for SELECT
			// use the executeQuery() method which returns the ResultSet.
			statement.executeUpdate(sql);
			statement.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}