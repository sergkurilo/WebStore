package by.epam.trainings.task6.controller.command.impl.navigation;

import by.epam.trainings.task6.controller.command.ICommand;
import by.epam.trainings.task6.domain.Product;
import by.epam.trainings.task6.resource.ConfigurationManager;
import by.epam.trainings.task6.resource.MessageManager;
import by.epam.trainings.task6.service.ProductService;
import by.epam.trainings.task6.service.ServiceException;
import by.epam.trainings.task6.service.impl.ProductServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ToEditCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(ToEditCommand.class);
    private static final String PRODUCT_ID = "id";
    private static final String URL = "url";
    private static final String PRODUCT = "product";
    private static final String MESSAGE = "message";
    private static final String ERROR_PAGE = "path.page.error";
    private static final String ADMIN_EDIT_PAGE = "path.page.admin.edit";

    @Override
    public String execute(HttpServletRequest request) {//TODO
        String page = null;
        int id = Integer.parseInt(request.getParameter(PRODUCT_ID));
        ProductService productService = ProductServiceImpl.getInstance();
        try {
            Product product = productService.findById(id);
            if (product != null) {
                request.getSession().setAttribute(PRODUCT, product);
                page = ConfigurationManager.getProperty(ADMIN_EDIT_PAGE);
                request.getSession().setAttribute(URL, page);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error during user removing user from blacklist: " + e);
            request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
            page = ConfigurationManager.getProperty(ERROR_PAGE);
        }
        return page;
    }
}
