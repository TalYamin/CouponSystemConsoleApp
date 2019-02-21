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
	private BlockingQueue<Connection> conQ = new LinkedBlockingQueue<>(MAX_CON_NUM);

	
	//Private CTOR 
	private ConnectionPool() throws SQLException, Exception{
		
		try {
			Class.forName(DataBase.getDriverConnextion());
		}catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		Connection con1 = DriverManager.getConnection(DataBase.getConnectionString());
//		DatabaseMetaData  metas;
//		ResultSet tables;
//		Statement stat;
//		metas = con1.getMetaData();
//		stat = con1.createStatement();
//		tables = metas.getTables(con1.getCatalog(), null, "Company", null);
//		if (!tables.next()) {
//			DataBase.BuildDB();
//		}
		con1.close();
		while(conQ.size() < MAX_CON_NUM) {
			con1 = DriverManager.getConnection(DataBase.getConnectionString());
			conQ.offer(con1);
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
		while(conQ.peek()!=null) {
			c=conQ.poll();
			try {
				c.close();
			}catch (Exception e) {
				throw new ConnectionException("Unable to close all connections. ", this.conQ.size());
			}
		}
		
	}
	
	//return the number of the available connections
	
	public int availableConnections() {
		System.out.println("The num of available connections: " + this.conQ.size());
		return this.conQ.size();
	}
	
	
}
