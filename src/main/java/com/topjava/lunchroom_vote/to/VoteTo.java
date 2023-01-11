package com.topjava.lunchroom_vote.to;

import lombok.*;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @Alima-T 12/09/2022
 */
@NoArgsConstructor
@Data
public class VoteTo extends BaseTo {

    private LocalDate voteDate;

    private Integer userId;

    private Integer restaurantId;

    @ConstructorProperties({"id", "voteDate", "userId", "restaurantId"})
    public VoteTo(Integer id, LocalDate voteDate, Integer userId, Integer restaurantId) {
        super(id);
        this.voteDate = voteDate;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

}
