package by.epam.trainings.task6.controller.command.impl;

import by.epam.trainings.task6.controller.command.ICommand;
import by.epam.trainings.task6.domain.Order;
import by.epam.trainings.task6.domain.User;
import by.epam.trainings.task6.resource.ConfigurationManager;
import by.epam.trainings.task6.resource.MessageManager;
import by.epam.trainings.task6.service.OrderService;
import by.epam.trainings.task6.service.ServiceException;
import by.epam.trainings.task6.service.impl.OrderServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AllUserOrdersCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(AllUserOrdersCommand.class);
    private static final String USER = "user";
    private static final String MESSAGE = "message";
    private static final String USER_ORDERS = "userOrders";
    private static final String ERROR_PAGE = "path.page.error";
    private static final String ORDERS_PAGE = "path.page.orders";
    private static final String URL = "url";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        User user = (User) request.getSession().getAttribute(USER);
        int id = user.getUserId();
        List<Order> orderList;

        if (id != 0) {
            OrderService orderService = OrderServiceImpl.getInstance();
            try {
                orderList = orderService.findById(id);
                if (!orderList.isEmpty()) {
                    request.getSession().setAttribute(USER_ORDERS, orderList);
                    page = ConfigurationManager.getProperty(ORDERS_PAGE);
                    request.getSession().setAttribute(URL, page);
                } else {
                    page = ConfigurationManager.getProperty(ORDERS_PAGE);
                    request.setAttribute(MESSAGE, MessageManager.HAVE_NOT_ORDERS);
                }
            } catch (ServiceException e) {
                LOGGER.error("Error during getting all user orders: " + e);
                request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
                page = ConfigurationManager.getProperty(ERROR_PAGE);
            }
        }
        return page;
    }
}
