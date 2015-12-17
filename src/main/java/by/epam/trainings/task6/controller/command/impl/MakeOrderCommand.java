package by.epam.trainings.task6.controller.command.impl;

import by.epam.trainings.task6.controller.command.ICommand;
import by.epam.trainings.task6.domain.Order;
import by.epam.trainings.task6.domain.Product;
import by.epam.trainings.task6.domain.User;
import by.epam.trainings.task6.resource.ConfigurationManager;
import by.epam.trainings.task6.resource.MessageManager;
import by.epam.trainings.task6.service.OrderService;
import by.epam.trainings.task6.service.ServiceException;
import by.epam.trainings.task6.service.impl.OrderServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class MakeOrderCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(MakeOrderCommand.class);
    private static final String BASKET = "basket";
    private static final String MAP_ID_BASKET_AMOUNT = "amount_map";
    private static final String MESSAGE = "message";
    private static final String USER = "user";
    private static final String BASKET_PAGE = "path.page.basket";
    private static final String ERROR_PAGE = "path.page.error";
    private static final String URL = "url";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        List<Product> basket = (List<Product>) request.getSession().getAttribute(BASKET);
        Map<Integer, Integer> amountMap = (Map<Integer, Integer>) request.getSession().getAttribute(MAP_ID_BASKET_AMOUNT);
        User user = (User) request.getSession().getAttribute(USER);

        if (basket != null && user != null) {
            try {
                OrderService orderService = OrderServiceImpl.getInstance();
                Order order = new Order();
                order.setUserId(user.getUserId());
                order.setOrderList(basket);
                order.setAmountMap(amountMap);
                orderService.create(order);
                request.getSession().setAttribute(BASKET, null);
                request.getSession().setAttribute(MAP_ID_BASKET_AMOUNT, null);
                page = ConfigurationManager.getProperty(BASKET_PAGE);
                request.setAttribute(MESSAGE, MessageManager.MAKE_ORDER_SUCCESS);
                request.getSession().setAttribute(URL, page);
            } catch (ServiceException e) {
                LOGGER.error("Error. Can't make order. " + e);
                request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
                page = ConfigurationManager.getProperty(ERROR_PAGE);
            }
        } else {
            page = ConfigurationManager.getProperty(BASKET_PAGE);
            request.getSession().setAttribute(URL, page);
            request.setAttribute(MESSAGE, MessageManager.MAKE_ORDER_ERROR);
        }
        return page;
    }
}
