package solvd.laba.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool implements AutoCloseable{

    // Concurrent-safe queue
    private final LinkedBlockingQueue<Connection> availableConnections;
    private boolean hasBeenInitialized;

    private final String url;
    private final String user;
    private final String password;



    public ConnectionPool(int size, String url, String user, String password) {
        this.availableConnections = new LinkedBlockingQueue<>(size);
        this.hasBeenInitialized=false;

        this.url = url;
        this.user = user;
        this.password = password;
    }


    // Method to borrow a connection, blocking thread if none available. Supports lazy init
    public Connection getConnection() {
        synchronized (this) {
            // Lazy initialization: Check whether I've initialized the connections once before.
            if(!this.hasBeenInitialized){
                System.out.println("Lazy initialization called by thread "
                        + Thread.currentThread().getName());

                int numberOfConnections = this.availableConnections.remainingCapacity();
                for (int i = 0; i < numberOfConnections; i++) {
                    try {
                        availableConnections.offer(DriverManager.getConnection(url, user, password));  // Create and add connection to the queue
                        System.out.println("Initialized connection: " + (i + 1));
                    }
                    catch(SQLException exc){
                        System.out.println("Conexión " + (i+1) + " no inicializada (SQLException).");
                    }
                }
                System.out.print("\n");
                this.hasBeenInitialized=true;
            }
        }

        try {
            return availableConnections.take();  // Blocks if no connections are available
        }
        catch(InterruptedException exc){
            return null;
        }
    }

    // Method to return a connection to the ConnectionPool queue
    public void releaseConnection(Connection conn) {
        try {
            availableConnections.put(conn);  // Adds the connection back to the pool
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void close() throws SQLException {
        for(Connection conn:availableConnections){
            conn.close();
        }
    }


}
