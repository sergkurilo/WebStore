package by.epam.trainings.task6.service;

import by.epam.trainings.task6.domain.Order;

import java.util.List;

public interface OrderService {
    public void create(Order order)throws ServiceException;
    public List<Order> findById(Integer id) throws ServiceException;
    public void delete(Integer orderId) throws ServiceException;
}
