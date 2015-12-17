package by.epam.trainings.task6.service;

import by.epam.trainings.task6.domain.User;

import java.util.List;

public interface UserService {
    public User signIn(String login, String password) throws ServiceException;
    public List<User> allUsers() throws ServiceException;
    public User create(User user) throws ServiceException;
    public void addToBlackList(Integer id) throws ServiceException;
    public void removeFromBlackList(Integer id) throws ServiceException;
    public void delete(Integer id) throws ServiceException;
    public boolean isUserExist(User user) throws ServiceException;
}
