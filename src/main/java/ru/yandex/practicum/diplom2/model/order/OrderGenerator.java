package ru.yandex.practicum.diplom2.model.order;

import ru.yandex.practicum.diplom2.model.order.ingredients.Ingredients;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.yandex.practicum.diplom2.model.order.OrderClient.getIngredients;

public class OrderGenerator {

    private static final Order order = new Order();
    private static final List<String> listIngredients = new ArrayList<>();

    public static Order getListIngredients() {
        Ingredients ingredients = getIngredients();

        for (int i = 0; i < ingredients.getData().size(); i++) {
            listIngredients.add(ingredients.getData().get(i).get_id());
        }

        order.setIngredients(listIngredients);
        return order;
    }

    public static Order getEmptyListIngredients() {
        order.setIngredients(null);
        return order;
    }

    public static Order getListIngredientsInvalidHash() {
        order.setIngredients(new ArrayList<>(Arrays.asList("", "", "", "")));
        return order;
    }
}