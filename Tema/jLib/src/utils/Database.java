package utils;

import java.sql.*;

public class Database {
	private static Connection connection = null;
	private final static String database = "jdbc:sqlite:src/storage/jlib.db";

	/**
	 * Connects to the database.
	 */
	public static Connection connect() {
		try {
			connection = DriverManager.getConnection(database);
			System.err.println("Database connected.");
		} catch (SQLException exception) {
			System.err.println(exception.getMessage());
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
				System.err.println("Database disconnected.");
			}
		} catch (SQLException exception) {
			System.err.println(exception.getMessage());
		}
	}

	/**
	 * Executes a query on the database.
	 *
	 * @param sql query
	 */
	public static void query(String sql) {
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}