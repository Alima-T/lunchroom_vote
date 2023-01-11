package com.topjava.lunchroom_vote.to;

import com.topjava.lunchroom_vote.model.Dish;
import lombok.*;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Objects;

/**
 * @Alima-T 12/09/2022
 */
@NoArgsConstructor
@Data
public class RestaurantTo extends BaseTo {

    private String name;

    private List<Dish> menu;

    @ConstructorProperties({"id", "name"})
    public RestaurantTo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @ConstructorProperties({"id", "name", "menu"})
    public RestaurantTo(Integer id, String name, List<Dish> menu) {
        super(id);
        this.name = name;
        this.menu = menu;
    }
}
