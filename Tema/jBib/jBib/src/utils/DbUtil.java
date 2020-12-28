package utils;

import java.sql.*;

public class DbUtil {
	public static void Connect() {
		Connection connection = null;
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:src/resources/jbib.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			statement.executeUpdate("drop table if exists librarians");
			statement.executeUpdate("CREATE TABLE 'librarians' (" + "'id'	INTEGER NOT NULL," + "'username'	TEXT,"
					+ "'password'	TEXT," + "'name'	TEXT," + "PRIMARY KEY('id' AUTOINCREMENT))");
			statement.executeUpdate("insert into librarians values(NULL, 'admin', 'admin', 'Alin')");
			ResultSet results = statement.executeQuery("select * from librarians");
			while (results.next()) {
				// read the result set
				System.out.println("username = " + results.getString("name"));
				System.out.println("id = " + results.getInt("id"));
				System.out.println("password = " + results.getString("password"));
			}
		} catch (SQLException exception) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(exception.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException exception) {
				// connection close failed.
				System.err.println(exception.getMessage());
			}
		}
	}

	public static void CreateDatabase(){

	}
}