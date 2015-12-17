package by.epam.trainings.task6.validator;

import by.epam.trainings.task6.domain.Product;
import by.epam.trainings.task6.resource.MessageManager;

import java.util.regex.Pattern;

public class ProductValidator implements Validator<Product> {
    public static final Pattern NAME_PATTERN = Pattern.compile("^[A-Z]*[a-z0-9_-]{3,}$");
    public static final Pattern PRICE_PATTERN = Pattern.compile("[0-9]{1,13}(\\.[0-9]*)?");
    public static final Pattern CATEGORY_PATTERN = Pattern.compile("^[a-z]+$");

    @Override
    public String isValid(Product product) {
        String price = String.valueOf(product.getPrice());
        if (!NAME_PATTERN.matcher(product.getName()).matches()) {
            return MessageManager.INCORRECT_PRODUCT_NAME;
        }
        if (!PRICE_PATTERN.matcher(price).matches()) {
            return MessageManager.INCORRECT_PRODUCT_PRICE;
        }
        if (!CATEGORY_PATTERN.matcher(product.getCategory()).find()) {
            return MessageManager.INCORRECT_PRODUCT_CATEGORY;
        }
        return null;
    }
}
