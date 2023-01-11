package com.topjava.lunchroom_vote.to;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @Alima-T 12/09/2022
 */
@NoArgsConstructor
@Data
public class DishTo extends BaseTo {

    private LocalDate voteDate;

    private String description;

    private BigDecimal price;

    private Integer restaurantId;

    @ConstructorProperties({"id", "voteDate", "description", "price", "restaurantId"})
    public DishTo(Integer id, LocalDate voteDate, String description, BigDecimal price, Integer restaurantId) {
        super(id);
        this.voteDate = voteDate;
        this.description = description;
        this.price = price;
        this.restaurantId = restaurantId;
    }
}
