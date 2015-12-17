package by.epam.trainings.task6.validator;

import by.epam.trainings.task6.domain.User;
import by.epam.trainings.task6.resource.MessageManager;

import java.util.regex.Pattern;

public class UserValidator implements Validator<User> {
    public static final Pattern LOGIN_PATTERN = Pattern.compile("^[a-z0-9_-]{3,16}$");
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9]{8,}$");
    public static final Pattern NAME_SURNAME_PATTERN = Pattern.compile("[А-ЯA-Zа-яa-z\\s-]{2,45}");
    public static final Pattern EMAIL_PATTERN = Pattern.compile("[\\w\\.]{3,25}@[a-z\\.]{3,10}\\.[a-z]{2,5}");

    public String isValid(User user) {
        if (!LOGIN_PATTERN.matcher(user.getLogin()).matches()) {
            return MessageManager.INCORRECT_LOGIN;
        }
        if (!PASSWORD_PATTERN.matcher(user.getPassword()).matches()) {
            return MessageManager.INCORRECT_PASSWORD;
        }
        if (!NAME_SURNAME_PATTERN.matcher(user.getFirstName()).find()
                && !NAME_SURNAME_PATTERN.matcher(user.getLastName()).find()) {
            return MessageManager.INCORRECT_NAME;
        }
        if (!EMAIL_PATTERN.matcher(user.getEmail()).find()) {
            return MessageManager.INCORRECT_EMAIL;
        }
        return null;
    }
}
