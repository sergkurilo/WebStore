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

public class DeleteOrderCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(DeleteOrderCommand.class);
    private static final String ORDER_ID = "order_id";
    private static final String MESSAGE = "message";
    private static final String USER = "user";
    private static final String USER_ORDERS = "userOrders";
    private static final String ERROR_PAGE = "path.page.error";
    private static final String ORDERS_PAGE = "path.page.orders";
    private static final String URL = "url";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        int orderId = Integer.parseInt(request.getParameter(ORDER_ID));
        User user = (User) request.getSession().getAttribute(USER);
        int userId = user.getUserId();

        if (orderId != 0) {
            OrderService orderService = OrderServiceImpl.getInstance();
            try {
                orderService.delete(orderId);
                List<Order> orders = orderService.findById(userId);
                request.getSession().setAttribute(USER_ORDERS, orders);
                page = ConfigurationManager.getProperty(ORDERS_PAGE);
                request.getSession().setAttribute(URL, page);
            } catch (ServiceException e) {
                LOGGER.error("Error during deleting order: " + e);
                request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
                page = ConfigurationManager.getProperty(ERROR_PAGE);
            }
        }
        return page;
    }
}
