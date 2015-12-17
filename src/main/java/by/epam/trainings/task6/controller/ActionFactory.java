package by.epam.trainings.task6.controller;

import by.epam.trainings.task6.controller.command.ICommand;
import by.epam.trainings.task6.controller.command.enumeration.CommandEnum;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    private static final String COMMAND = "command";

    public ICommand defineCommand(HttpServletRequest request) throws IllegalArgumentException{
        ICommand command = null;

        String action = request.getParameter(COMMAND);

        if (action == null || action.isEmpty()) {
            return command;
        }

        try {
            CommandEnum commandEnum = CommandEnum.valueOf(action.toUpperCase());
            command = commandEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
        return command;
    }
}
