package by.epam.trainings.task6.dao.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public final class DBConnectionPool {
    private ArrayBlockingQueue<Connection> connections;
    private String driver;
    private String url;
    private String user;
    private String password;
    private int poolSize;
    private static AtomicBoolean isNull = new AtomicBoolean(true);
    private static ReentrantLock lock = new ReentrantLock();
    private static DBConnectionPool connectionPool;

    public static DBConnectionPool getInstance() throws ConnectionPoolException {
        if (isNull.get()) {
            lock.lock();
            try {
                if (isNull.get()) {
                    connectionPool = new DBConnectionPool();
                    isNull.set(false);
                }
            } finally {
                lock.unlock();
            }
        }
        return connectionPool;
    }

    private DBConnectionPool() throws ConnectionPoolException {
        try {
            this.driver = DBResourceManager.getValue(DBPropertyName.DB_DRIVER);
            this.url = DBResourceManager.getValue(DBPropertyName.DB_URL);
            this.user = DBResourceManager.getValue(DBPropertyName.DB_USER);
            this.password = DBResourceManager.getValue(DBPropertyName.DB_PASSWORD);
            this.poolSize = Integer.parseInt(DBResourceManager.getValue(DBPropertyName.DB_POOLSIZE));

            Class.forName(driver);
            connections = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                connections.put(DriverManager.getConnection(url, user, password));
            }
        } catch (SQLException | InterruptedException | ClassNotFoundException e) {
            throw new ConnectionPoolException(e);
        }
    }

    public Connection takeConnection() {
        Connection connection = null;
        try {
            connection = connections.take();
        } catch (InterruptedException e) {
            //TODO
        }
        return connection;
    }

    public void closeConnection(Connection connection, Statement statement) {
        try {
            statement.close();
            if (!connection.isClosed()) {
                connections.put(connection);
            }
        } catch (SQLException | InterruptedException e) {
            //TODO
        }
    }

    public void release() {
        Iterator<Connection> iterator = connections.iterator();
        while (iterator.hasNext()) {
            try {
                iterator.next().close();
            } catch (SQLException e) {
                //TODO
            }
        }
    }

    public static DBConnectionPool getPool() {
        return connectionPool;
    }

}