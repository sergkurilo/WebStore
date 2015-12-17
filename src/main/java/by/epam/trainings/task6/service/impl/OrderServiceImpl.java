package by.epam.trainings.task6.service.impl;

import by.epam.trainings.task6.dao.DAOException;
import by.epam.trainings.task6.dao.DAOFactory;
import by.epam.trainings.task6.dao.OrderDAO;
import by.epam.trainings.task6.domain.Order;
import by.epam.trainings.task6.domain.Product;
import by.epam.trainings.task6.service.OrderService;
import by.epam.trainings.task6.service.ServiceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private static OrderService instance = new OrderServiceImpl();

    private OrderServiceImpl() {
    }

    public static OrderService getInstance() {
        return instance;
    }

    @Override
    public void create(Order order) throws ServiceException {
        OrderDAO orderDAO = DAOFactory.getOrderDAO();
        try {
            orderDAO.create(order);
            orderDAO.addProductToOrder(order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> findById(Integer id) throws ServiceException {
        List<Order> orderList;
        OrderDAO orderDAO = DAOFactory.getOrderDAO();
        try {
            orderList = orderDAO.findOrdersByUserId(id);
            for (Order order: orderList) {
                List<Product> listOfProducts = new ArrayList<>();
                Map<Integer, Integer> amountMap = new HashMap<>();
                order.setOrderList(listOfProducts);
                order.setAmountMap(amountMap);
                orderDAO.productsFromOrder(order);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return orderList;
    }

    @Override
    public void delete(Integer orderId) throws ServiceException {
        OrderDAO orderDAO = DAOFactory.getOrderDAO();
        try {
            orderDAO.delete(orderId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
