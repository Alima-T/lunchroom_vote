package com.topjava.lunchroom_vote.util;

import com.topjava.lunchroom_vote.model.Dish;
import com.topjava.lunchroom_vote.to.DishTo;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

/**
 * @Alima-T 12/25/2022
 */
@NoArgsConstructor
public class DishUtil {

    public static List<DishTo> getTos(Collection<Dish> dishes) {
        return dishes.stream().map(DishUtil::createTo).toList();
    }

    public static DishTo createTo(Dish dish) {
        return new DishTo(dish.getId(), dish.getVoteDate(), dish.getDescription(),
                dish.getPrice(), dish.getRestaurant().getId());
    }
}
