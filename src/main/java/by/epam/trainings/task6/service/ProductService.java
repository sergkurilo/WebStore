package by.epam.trainings.task6.service;

import by.epam.trainings.task6.domain.Product;

import java.util.List;

public interface ProductService {
    public Product findById(Integer id) throws ServiceException;
    public List<Product> allProducts() throws ServiceException;
    public void delete(Integer id) throws ServiceException;
    public void create(Product product)throws ServiceException;
    public void update(Product product) throws ServiceException;
}
