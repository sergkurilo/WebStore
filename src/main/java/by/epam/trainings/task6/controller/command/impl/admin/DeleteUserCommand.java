package by.epam.trainings.task6.controller.command.impl.admin;

import by.epam.trainings.task6.controller.command.ICommand;
import by.epam.trainings.task6.domain.User;
import by.epam.trainings.task6.resource.ConfigurationManager;
import by.epam.trainings.task6.resource.MessageManager;
import by.epam.trainings.task6.service.ServiceException;
import by.epam.trainings.task6.service.UserService;
import by.epam.trainings.task6.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DeleteUserCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(DeleteUserCommand.class);
    private static final String PARAM_NAME_USER_ID = "id";
    private static final String USERS = "users";
    private static final String USERS_ADMIN_PAGE = "path.page.admin.users";
    private static final String URL = "url";
    private static final String MESSAGE = "message";
    private static final String ERROR_PAGE = "path.page.error";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        int id = Integer.parseInt(request.getParameter(PARAM_NAME_USER_ID));
        UserService service = UserServiceImpl.getInstance();
        try {
            service.delete(id);
            List<User> users = service.allUsers();
            if (users != null) {
                request.getSession().setAttribute(USERS, users);
                page = ConfigurationManager.getProperty(USERS_ADMIN_PAGE);
                request.getSession().setAttribute(URL, page);
            } else {
                page = ConfigurationManager.getProperty(USERS_ADMIN_PAGE);
                request.setAttribute(MESSAGE, MessageManager.EMPTY_USER_LIST);
                request.getSession().setAttribute(URL, page);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error during deleting user: " + e);
            request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
            page = ConfigurationManager.getProperty(ERROR_PAGE);
        }
        return page;
    }
}
