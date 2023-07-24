package ru.yandex.practicum.diplom2.model.order.ingredients;

import java.util.ArrayList;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ingredients {
    private Boolean success;
    private ArrayList<Datum> data;
}