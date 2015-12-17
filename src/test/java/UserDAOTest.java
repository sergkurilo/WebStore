import by.epam.trainings.task6.dao.DAOException;
import by.epam.trainings.task6.dao.DAOFactory;
import by.epam.trainings.task6.dao.UserDAO;
import by.epam.trainings.task6.dao.pool.ConnectionPoolException;
import by.epam.trainings.task6.dao.pool.DBConnectionPool;
import by.epam.trainings.task6.domain.User;
import by.epam.trainings.task6.util.Coder;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserDAOTest {
    private static final Logger LOGGER = Logger.getLogger(UserDAOTest.class);
    private static DBConnectionPool connectionPool;
    private UserDAO userDAO;

    @BeforeClass
    public static void initConnectionPool() {
        try {
            connectionPool = DBConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error(e);
        }
    }


    @Test
    public void testFindUserById() {
        userDAO = DAOFactory.getUserDAO();
        try {
            User user = userDAO.find(3);
            Assert.assertEquals("Sergey", user.getFirstName());
            Assert.assertEquals("Kurilo", user.getLastName());
            Assert.assertEquals("sergserg", user.getLogin());
            Assert.assertEquals("sergey.kurilo.96@mail.ru", user.getEmail());
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return;
    }

    @Test
    public void testFindUserByLoginAndPassword() {
        userDAO = DAOFactory.getUserDAO();
        try {
            User user = userDAO.findUserByLoginAndPassword("s", Coder.hashMD5("1"));
            Assert.assertEquals("Andy", user.getFirstName());
            Assert.assertEquals("Pitts", user.getLastName());
            Assert.assertEquals("Chicago", user.getAddress());
            Assert.assertEquals("mem.pitts@gmail.com", user.getEmail());
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return;
    }

    @Test
    public void testUserRegister() {
        userDAO = DAOFactory.getUserDAO();
        User user = new User();
        user.setLogin("kavlas");
        user.setEmail("jane.doe@mailinator.com");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword(Coder.hashMD5("hello12345"));
        user.setAddress("California");

        try {
            userDAO.create(user);
            int id = user.getUserId();
            Assert.assertNotNull(id);
            User newUser = userDAO.find(id);
            Assert.assertEquals("Jane", newUser.getFirstName());
            Assert.assertEquals("jane.doe@mailinator.com", newUser.getEmail());
            Assert.assertEquals("California", newUser.getAddress());
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return;
    }

    @Test
    public void testDeleteUser()
    {
        userDAO = DAOFactory.getUserDAO();
        User user = new User();
        user.setEmail("sam.mine@gmail.com");
        user.setLogin("kury");
        user.setFirstName("Sam");
        user.setLastName("Bali");
        user.setPassword(Coder.hashMD5("sambali12345"));
        user.setAddress("Texas");

        try {
            userDAO.create(user);
            int id = user.getUserId();
            Assert.assertNotNull(id);
            userDAO.delete(id);
            User newUser = userDAO.find(id);
            Assert.assertEquals(null, newUser);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return;
    }
}
