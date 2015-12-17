package by.epam.trainings.task6.controller.command.impl;

import by.epam.trainings.task6.controller.command.ICommand;

import javax.servlet.http.HttpServletRequest;

public class ChangeLanguageCommand implements ICommand {
    private String page;
    private static final String LOCALE = "locale";
    private static final String URL = "url";

    @Override
    public String execute(HttpServletRequest request) {
        String locale = request.getParameter(LOCALE);
        request.getSession().setAttribute(LOCALE, locale);
        page = (String) request.getSession().getAttribute(URL);
        return page;
    }
}
