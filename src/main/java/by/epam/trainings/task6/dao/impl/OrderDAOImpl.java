package by.epam.trainings.task6.dao.impl;

import by.epam.trainings.task6.dao.DAOException;
import by.epam.trainings.task6.dao.OrderDAO;
import by.epam.trainings.task6.dao.pool.DBConnectionPool;
import by.epam.trainings.task6.domain.Order;
import by.epam.trainings.task6.domain.Product;
import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private static final String SQL_CREATE_ORDER = "INSERT INTO store.orders(user_id, order_date) VALUES (?, ?)";
    private static final String SQL_ADD_PRODUCT_TO_ORDER = "INSERT INTO store.orders_products(order_id, product_id, amount) VALUES (?, ?, ?)";
    private static final String SQL_DELETE_ORDER = "DELETE w,e FROM store.orders w INNER JOIN store.orders_products e ON w.order_id=e.order_id WHERE e.order_id=?";
    private static final String SQL_ORDERS = "SELECT * FROM store.orders JOIN store.orders_products ON orders.order_id=orders_products.order_id JOIN store.products ON orders_products.product_id=products.product_id JOIN store.categories ON products.category=categories.category_id";
    private static final String SQL_PRODUCTS_FROM_ORDER = "SELECT * FROM store.orders_products JOIN store.products ON orders_products.product_id=products.product_id JOIN store.categories ON products.category=categories.category_id WHERE order_id=?";
    private static final String SQL_DELETE_PRODUCT = "DELETE FROM store.orders_product WHERE order_id=?";
    private static final String SQL_ORDER_BY_ID = "SELECT * FROM store.orders WHERE user_id=?";
    private static final String SQL_ORDER_ID = "order_id";
    private static final String SQL_USER_ID = "user_id";
    private static final String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
    private static final String SQL_PRODUCT_AMOUNT_IN_ORDER = "orders_products.amount";
    private static final String SQL_ORDER_DATE = "order_date";
    private static final String SQL_PRODUCT_ID = "product_id";
    private static final String SQL_PRODUCT_NAME = "product_name";
    private static final String SQL_PRICE = "price";
    private static final String SQL_DESCRIPTION = "description";
    private static final String SQL_CATEGORY_NAME = "name";
    private static final DBConnectionPool pool = DBConnectionPool.getPool();

    @Override
    public List<Order> findAll() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Order> listOfOrders = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_ORDERS);
            ResultSet resultSet = preparedStatement.executeQuery();
            listOfOrders = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product();
                Order order = null;
                product.setProductId(resultSet.getInt(SQL_PRODUCT_ID));
                product.setName(resultSet.getString(SQL_PRODUCT_NAME));
                product.setDescription(resultSet.getString(SQL_DESCRIPTION));
                product.setCategory(resultSet.getString(SQL_CATEGORY_NAME));
                product.setPrice(resultSet.getDouble(SQL_PRICE));
                if (!listOfOrders.isEmpty()) {
                    for (Order list : listOfOrders) {
                        if (list.getOrderId() == resultSet.getInt(SQL_ORDER_ID)) {
                            order = list;
                            order.getOrderList().add(product);
                            break;
                        }
                    }
                }
                if (order == null) {
                    order = new Order();
                    order.setOrderId(resultSet.getInt(SQL_ORDER_ID));
                    order.setUserId(resultSet.getInt(SQL_USER_ID));
                    order.setDate(resultSet.getString(SQL_ORDER_DATE));
                    order.getOrderList().add(product);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.closeConnection(connection, preparedStatement);
        }
        return listOfOrders;
    }

    @Override
    public void create(Order order) throws DAOException {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_CREATE_ORDER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setString(2, reportDate);
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setOrderId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public void delete(Integer id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_ORDER);
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Deleting order failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public Order find(Integer id) throws DAOException {
        throw new DAOException("Such operation is not supported");
    }

    @Override
    public void update(Order order) throws DAOException {
        throw new DAOException("Such operation is not supported");
    }


    @Override
    public void addProductToOrder(Order order) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_PRODUCT_TO_ORDER);
            for (Product product : order.getOrderList()) {
                preparedStatement.setInt(1, order.getOrderId());
                preparedStatement.setInt(2, product.getProductId());
                preparedStatement.setInt(3, order.getAmountMap().get(product.getProductId()));
                if (preparedStatement.executeUpdate() == 0) {
                    throw new SQLException("Adding product to order failed, no rows affected.");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public List<Order> findOrdersByUserId(Integer id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Order> listOfOrders = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_ORDER_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            listOfOrders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt(SQL_ORDER_ID));
                order.setUserId(resultSet.getInt(SQL_USER_ID));
                order.setDate(resultSet.getString(SQL_ORDER_DATE));
                listOfOrders.add(order);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.closeConnection(connection, preparedStatement);
        }
        return listOfOrders;
    }

    @Override
    public void deleteProductFromOrder(Integer id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_PRODUCT);
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Deleting product from order failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public void productsFromOrder(Order order) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = pool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_PRODUCTS_FROM_ORDER);
            preparedStatement.setInt(1, order.getOrderId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt(SQL_PRODUCT_ID));
                product.setName(resultSet.getString(SQL_PRODUCT_NAME));
                product.setDescription(resultSet.getString(SQL_DESCRIPTION));
                product.setCategory(resultSet.getString(SQL_CATEGORY_NAME));
                product.setPrice(resultSet.getDouble(SQL_PRICE));
                order.getOrderList().add(product);
                order.getAmountMap().put(product.getProductId(), resultSet.getInt(SQL_PRODUCT_AMOUNT_IN_ORDER));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }
}
