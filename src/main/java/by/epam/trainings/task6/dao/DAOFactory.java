package by.epam.trainings.task6.dao;

import by.epam.trainings.task6.dao.impl.OrderDAOImpl;
import by.epam.trainings.task6.dao.impl.ProductDAOImpl;
import by.epam.trainings.task6.dao.impl.UserDAOImpl;

public class DAOFactory {
    private static UserDAOImpl userDAO = new UserDAOImpl();
    private static ProductDAOImpl productDAO = new ProductDAOImpl();
    private static OrderDAOImpl orderDAO = new OrderDAOImpl();

    public static UserDAOImpl getUserDAO() {
        return userDAO;
    }

    public static ProductDAOImpl getProductDAO() {
        return productDAO;
    }

    public static OrderDAOImpl getOrderDAO() {return orderDAO;}
}
