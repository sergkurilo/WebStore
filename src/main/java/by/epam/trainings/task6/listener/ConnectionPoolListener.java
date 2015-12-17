package by.epam.trainings.task6.listener;

import by.epam.trainings.task6.dao.pool.ConnectionPoolException;
import by.epam.trainings.task6.dao.pool.DBConnectionPool;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConnectionPoolListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPoolListener.class);
    private DBConnectionPool dbConnectionPool = null;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            LOGGER.error("Connection pool initialized");
        } catch (ConnectionPoolException e) {
            LOGGER.error("Exception int connection pool", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            dbConnectionPool.getInstance().release();
            LOGGER.error("Connection pool closed");
        } catch (ConnectionPoolException e) {
            throw new RuntimeException(e);
        }
    }
}
