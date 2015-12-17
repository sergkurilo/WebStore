package by.epam.trainings.task6.controller.command.impl.navigation;

import by.epam.trainings.task6.controller.command.ICommand;
import by.epam.trainings.task6.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class ToBasketCommand implements ICommand {
    private static String URL = "url";
    private static String BASKET_PAGE = "path.page.basket";

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(BASKET_PAGE);
        request.getSession().setAttribute(URL, page);
        return page;
    }
}
