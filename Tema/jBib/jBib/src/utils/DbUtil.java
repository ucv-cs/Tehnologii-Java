package utils;

import java.sql.*;

public class DbUtil {
	private static Connection connection = null;
	private final static String database = "jdbc:sqlite:src/resources/jbib.db";

	/**
	 * Connects to the database
	 */
	public static void Connect() {
		try {
			connection = DriverManager.getConnection(database);
			System.err.println("Database connected.");
		} catch (SQLException exception) {
			System.err.println(exception.getMessage());
		}
	}

	/**
	 * Disconnects the database
	 */
	public static void Disconnect() {
		try {
			if (connection != null) {
				connection.close();
				System.err.println("Database disconnected.");
			}
		} catch (SQLException exception) {
			System.err.println(exception.getMessage());
		}
	}

}