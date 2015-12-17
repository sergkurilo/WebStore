package by.epam.trainings.task6.dao;

import by.epam.trainings.task6.domain.User;

public interface UserDAO extends GenericDAO<User, Integer> {
    public void addToBlacklist(Integer id) throws DAOException;
    public void removeFromBlacklist(Integer id) throws DAOException;
    public boolean checkUser(String login) throws DAOException;
    public User findUserByLoginAndPassword(String login, String password) throws DAOException;
}
