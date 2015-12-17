package by.epam.trainings.task6.controller.command.impl.navigation;

import by.epam.trainings.task6.controller.command.ICommand;
import by.epam.trainings.task6.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class ToSignupCommand implements ICommand {
    private static final String SIGN_UP_PAGE = "path.page.signup";

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(SIGN_UP_PAGE);
        return page;
    }
}
