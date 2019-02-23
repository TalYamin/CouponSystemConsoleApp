package SystemUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import DB.DataBase;
import Exceptions.ConnectionException;

/**
 * @author Shay Ben Haroush and Tal Yamin
 *
 */

public class ConnectionPool {
	
	private static ConnectionPool instance;
	private final int MAX_CON_NUM = 10;
	private BlockingQueue<Connection> conQ  = new LinkedBlockingQueue<>();

	
	//Private CTOR 
	private ConnectionPool() throws SQLException, Exception{
		
		try {
			Class.forName(DataBase.getDriverConnextion());
		}catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		Connection con1 = DriverManager.getConnection(DataBase.getConnectionString());
		con1.close();
		while(this.conQ.size() < MAX_CON_NUM) {
			con1 = DriverManager.getConnection(DataBase.getConnectionString());
			this.conQ.offer(con1);
		}	
		
	}
	
	//call the instance of connectionPool
	
	public static ConnectionPool getInstance() throws Exception{
		if (instance == null) {
			try {
				instance = new ConnectionPool();
			}catch (SQLException e) {
				e.printStackTrace();
				throw new Exception("unable to get instance of connectioPool");
			}
		}
		
		return instance;
	}
	
	//send a connection to the requester from conQ. this method is synchronized so only one can get to the connection pool at a time.
    //before sending a connection it set the auto commit to true.
	
	public synchronized Connection getConnection() throws Exception {
		
		try {
			
			Connection c = conQ.poll();
			c.setAutoCommit(true);
			return c;
		}catch (Exception e) {
			throw new ConnectionException("failed to get connection. ", this.conQ.size());
		}
	}
	
	 //return a connection to conQ. 
	 //this method is synchronized so only one can get to the connection pool at a time

	public synchronized void returnConnection(Connection c)throws Exception{
		try {
		Connection c1 = DriverManager.getConnection(DataBase.getConnectionString());
		conQ.offer(c1);
		}catch (Exception e) {
			throw new ConnectionException("failed to return connection. ", this.conQ.size());
		}
	}
	
	// close all the connection available in conQ
	
	public void closeAllConnections() throws Exception {
		
		Connection c;
		while(this.conQ.peek()!=null) {
			c= this.conQ.poll();
			try {
				c.close();
				instance = null;
			}catch (Exception e) {
				throw new ConnectionException("Unable to close all connections. ", this.conQ.size());
			}
		}
		
		System.out.println("All connections have been closed in ConnectionPool");
	}
	
	//return the number of the available connections
	
	public int availableConnections() {
		System.out.println("The num of available connections: " + this.conQ.size());
		return this.conQ.size();
	}
	
	
}
