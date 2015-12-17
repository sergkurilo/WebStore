package by.epam.trainings.task6.controller.command.impl;


import by.epam.trainings.task6.controller.command.ICommand;
import by.epam.trainings.task6.domain.Product;
import by.epam.trainings.task6.resource.ConfigurationManager;
import by.epam.trainings.task6.resource.MessageManager;
import by.epam.trainings.task6.service.ProductService;
import by.epam.trainings.task6.service.ServiceException;
import by.epam.trainings.task6.service.impl.ProductServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class LogoutCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(LogoutCommand.class);
    private static final String INDEX_PAGE = "path.page.index";
    private static final String ERROR_PAGE = "path.page.error";
    private static final String MESSAGE = "message";
    private static final String MAP_ID_BASKET_AMOUNT = "amount_map";

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(INDEX_PAGE);
        Map<Integer, Integer> map = (Map<Integer, Integer>) request.getSession().getAttribute(MAP_ID_BASKET_AMOUNT);
        if (map == null || map.isEmpty()) {
            request.getSession().invalidate();
        } else {
            ProductService productService = ProductServiceImpl.getInstance();
            try {
                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    Product product = productService.findById(entry.getKey());
                    int currentAmount = product.getAmount();
                    product.setAmount(currentAmount + entry.getValue());
                    productService.update(product);
                }
                request.getSession().invalidate();
            } catch (ServiceException e) {
                LOGGER.error("Log out error: " + e);
                request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
                page = ConfigurationManager.getProperty(ERROR_PAGE);
            }
        }
        return page;
    }
}
