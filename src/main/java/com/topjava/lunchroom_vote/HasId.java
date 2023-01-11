package com.topjava.lunchroom_vote;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.Assert;

/**
 * @Alima-T 11/27/2022
 */

public interface HasId {

    Integer getId();

    void setId(Integer id);

    @JsonIgnore
    default boolean isNew() {
        return getId() == null;
    }

    default int id() {
        Assert.notNull(getId(), "Entity can not be without id");
        return getId();
    }
}
