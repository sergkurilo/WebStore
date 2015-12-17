import by.epam.trainings.task6.dao.DAOException;
import by.epam.trainings.task6.dao.DAOFactory;
import by.epam.trainings.task6.dao.ProductDAO;
import by.epam.trainings.task6.dao.pool.ConnectionPoolException;
import by.epam.trainings.task6.dao.pool.DBConnectionPool;
import by.epam.trainings.task6.domain.Product;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProductDAOTest {
    private static final Logger LOGGER = Logger.getLogger(ProductDAOTest.class);
    private static DBConnectionPool connectionPool;
    private ProductDAO productDAO;

    @BeforeClass
    public static void initConnectionPool() {
        try {
            connectionPool = DBConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindProductById() {
        productDAO = DAOFactory.getProductDAO();
        try {
            Product product = productDAO.find(4);
            Assert.assertEquals("Apple iPad mini 3 16GB Gold", product.getName());
            Assert.assertEquals(340, product.getPrice(), 0);
            Assert.assertEquals(16, product.getAmount());
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return;
    }

    @Test
    public void testCreateNewProduct() {
        productDAO = DAOFactory.getProductDAO();
        Product product = new Product();
        product.setName("New Telephone");
        product.setDescription("New, beautiful model");
        product.setAmount(20);
        product.setCategory("phone");
        product.setImage("image.jpg");
        product.setPrice(230);

        try {
            productDAO.create(product);
            int id = product.getProductId();
            Assert.assertNotNull(id);
            Product newProduct = productDAO.find(id);
            Assert.assertEquals("New Telephone", newProduct.getName());
            Assert.assertEquals("New, beautiful model", newProduct.getDescription());
            Assert.assertEquals("image.jpg", newProduct.getImage());
            Assert.assertEquals(230, newProduct.getPrice(), 0);
            Assert.assertEquals(20, newProduct.getAmount());
            productDAO.delete(id);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return;
    }

    @Test
    public void testDeleteProduct()
    {
        productDAO = DAOFactory.getProductDAO();
        Product product = new Product();
        product.setName("New PC");
        product.setDescription("New, shining pc");
        product.setAmount(30);
        product.setCategory("pc");
        product.setImage("pcimage.jpg");
        product.setPrice(400);

        try {
            productDAO.create(product);
            int id = product.getProductId();
            Assert.assertNotNull(id);
            productDAO.delete(id);
            Product newProduct = productDAO.find(id);
            Assert.assertEquals(null, newProduct);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return;
    }
}
