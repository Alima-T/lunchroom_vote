package com.topjava.lunchroom_vote.util;

import com.topjava.lunchroom_vote.model.Restaurant;
import com.topjava.lunchroom_vote.to.RestaurantTo;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

/**
 * @Alima-T 12/25/2022
 */
@NoArgsConstructor
public class RestaurantUtil {

    public static List<RestaurantTo> getTos(Collection<Restaurant> restaurants) {
        return restaurants.stream().map(RestaurantUtil::createTo).toList();
    }

    public static List<RestaurantTo> getTosWithMenu(Collection<Restaurant> restaurants) {
        return restaurants.stream().map(RestaurantUtil::createToWithMenu).toList();
    }

    public static RestaurantTo createTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName());
    }

    public static RestaurantTo createToWithMenu(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getMenu());
    }
}
