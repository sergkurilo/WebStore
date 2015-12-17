package by.epam.trainings.task6.dao;

import by.epam.trainings.task6.domain.Product;

public interface ProductDAO extends GenericDAO<Product, Integer> {
    public int findCategoryByName(String category) throws DAOException;
}
