package Connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashSet;
import java.util.Set;

import DB.DataBase;

public class ConnectionPool {

	public static ConnectionPool instance = new ConnectionPool();
	private Set<Connection> connections;

	private ConnectionPool() {
		connections = new HashSet<>();
	}

	public static ConnectionPool getInstance() {
		return instance;
	}

	public Connection getConnection() throws Exception {

		try {

			if (connections.isEmpty()) {
				// give the connection from the DBDAO
				Connection connection = DriverManager.getConnection(DataBase.getConnectionString());
				connections.add(connection);
				return connection;
			} else if (connections.size() > 10) {
				System.out.println("Please wait");
			}

		} catch (Exception e) {
			System.out.println("connectioPool error - failed to get connection");
		}
		return null;
	}
	
	
	
	
	
}
