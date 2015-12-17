package by.epam.trainings.task6.controller.command.impl.navigation;

import by.epam.trainings.task6.controller.command.ICommand;
import by.epam.trainings.task6.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class ToIndexCommand implements ICommand {
    private static String URL = "url";
    private static String INDEX_PAGE = "path.page.index";

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(INDEX_PAGE);
        request.getSession().setAttribute(URL, page);
        return page;
    }
}
