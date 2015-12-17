package by.epam.trainings.task6.dao;

import by.epam.trainings.task6.domain.Order;

import java.util.List;

public interface OrderDAO extends GenericDAO<Order, Integer> {
    public void addProductToOrder(Order order) throws DAOException;
    public List<Order> findOrdersByUserId(Integer id) throws DAOException;
    public void deleteProductFromOrder(Integer id) throws DAOException;
    public void productsFromOrder(Order order) throws DAOException;
}
