package by.epam.trainings.task6.service.impl;

import by.epam.trainings.task6.dao.DAOException;
import by.epam.trainings.task6.dao.DAOFactory;
import by.epam.trainings.task6.dao.ProductDAO;
import by.epam.trainings.task6.domain.Product;
import by.epam.trainings.task6.service.ProductService;
import by.epam.trainings.task6.service.ServiceException;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private static ProductService instance = new ProductServiceImpl();

    private ProductServiceImpl() {
    }

    public static ProductService getInstance() {
        return instance;
    }

    @Override
    public Product findById(Integer id) throws ServiceException {
        Product product;
        ProductDAO productDAO = DAOFactory.getProductDAO();
        try {
            product = productDAO.find(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return product;
    }

    @Override
    public List<Product> allProducts() throws ServiceException {
        List<Product> productList;
        ProductDAO productDAO = DAOFactory.getProductDAO();
        try {
            productList = productDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return productList;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        ProductDAO productDAO = DAOFactory.getProductDAO();
        try {
           productDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void create(Product product) throws ServiceException {
        ProductDAO productDAO = DAOFactory.getProductDAO();
        try {
            productDAO.create(product);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Product product) throws ServiceException {
        ProductDAO productDAO = DAOFactory.getProductDAO();
        try {
            productDAO.update(product);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
