package by.epam.trainings.task6.listener;

import by.epam.trainings.task6.domain.Product;
import by.epam.trainings.task6.service.ProductService;
import by.epam.trainings.task6.service.ServiceException;
import by.epam.trainings.task6.service.impl.ProductServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Iterator;
import java.util.Map;

public class BasketSessionListener implements HttpSessionListener {//TODO
    private static final Logger LOGGER = Logger.getLogger(BasketSessionListener.class);
    private static final String MAP_ID_BASKET_AMOUNT = "map";

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        LOGGER.error("Session is created.");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        Map<Integer, Integer> map = (Map<Integer, Integer>) httpSessionEvent.getSession().getAttribute(MAP_ID_BASKET_AMOUNT);
        if (!map.isEmpty()){
            ProductService productService = ProductServiceImpl.getInstance();
            try {
                Iterator iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry pair = (Map.Entry) iterator.next();
                    Product product = productService.findById((Integer) pair.getKey());
                    int currentAmount = product.getAmount();
                    product.setAmount(currentAmount + (Integer) pair.getValue());
                    productService.update(product);
                }
            } catch (ServiceException e) {
                LOGGER.error(e);
            }
        }
    }
}
