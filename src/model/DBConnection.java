package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static Connection conn;
	
	private DBConnection() {}
	
	public static Connection getConnection() {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlysanpham", "root", "123456789");
			} catch (SQLException e) {
				e.printStackTrace();
			} return conn;
		}
		return conn;
	}
	
	public static void main(String[] args) {
		Connection x = DBConnection.getConnection();
		Connection y = DBConnection.getConnection();
		System.out.println(x == y);
	}
}
