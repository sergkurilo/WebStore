package by.epam.trainings.task6.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, K extends Serializable> {
    public List<T> findAll() throws DAOException;

    public void create(T t) throws DAOException;

    public void delete(K id) throws DAOException;

    public T find(K id) throws DAOException;

    public void update(T t) throws DAOException;
}
