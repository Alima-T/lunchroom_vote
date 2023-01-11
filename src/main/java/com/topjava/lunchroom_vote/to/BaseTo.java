package com.topjava.lunchroom_vote.to;

import com.fasterxml.jackson.annotation.JsonView;
import com.topjava.lunchroom_vote.HasId;
import com.topjava.lunchroom_vote.View;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Alima-T 12/09/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseTo implements HasId {

    @JsonView(View.JsonREST.class)
    protected Integer id;

}
