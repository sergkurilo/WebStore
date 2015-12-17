package by.epam.trainings.task6.service.impl;

import by.epam.trainings.task6.dao.DAOException;
import by.epam.trainings.task6.dao.DAOFactory;
import by.epam.trainings.task6.dao.UserDAO;
import by.epam.trainings.task6.domain.User;
import by.epam.trainings.task6.service.ServiceException;
import by.epam.trainings.task6.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserService instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        return instance;
    }

    public User signIn(String login, String password) throws ServiceException {
        User user;
        UserDAO userDAO = DAOFactory.getUserDAO();
        try {
            user = userDAO.findUserByLoginAndPassword(login, password);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public List<User> allUsers() throws ServiceException {
        List<User> userList;
        UserDAO userDAO = DAOFactory.getUserDAO();
        try {
            userList = userDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userList;
    }

    public User create(User user) throws ServiceException {
        UserDAO userDAO = DAOFactory.getUserDAO();
        try {
            userDAO.create(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public void addToBlackList(Integer id) throws ServiceException {
        UserDAO userDAO = DAOFactory.getUserDAO();
        try {
            userDAO.addToBlacklist(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeFromBlackList(Integer id) throws ServiceException {
        UserDAO userDAO = DAOFactory.getUserDAO();
        try {
            userDAO.removeFromBlacklist(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        UserDAO userDAO = DAOFactory.getUserDAO();
        try {
            userDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public boolean isUserExist(User user) throws ServiceException {
        boolean result;
        UserDAO userDAO = DAOFactory.getUserDAO();
        try {
            result = userDAO.checkUser(user.getLogin());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }
}
