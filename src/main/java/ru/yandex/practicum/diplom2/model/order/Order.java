package ru.yandex.practicum.diplom2.model.order;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private List<String> ingredients;
}