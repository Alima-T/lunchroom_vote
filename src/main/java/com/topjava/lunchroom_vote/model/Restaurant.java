package com.topjava.lunchroom_vote.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

/**
 * @Alima-T 11/27/2022
 */
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurant_unique_idx")})
@Getter
@Setter
@NoArgsConstructor
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @BatchSize(size = 7)
    @JsonManagedReference
    private List<Dish> menu;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("voteDate DESC")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @BatchSize(size = 200)
    private List<Vote> votes;

    public Restaurant(String name) {
        this(null, name);
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", menu=" + menu +
                ", votes=" + votes +
                ", name='" + name + '\'' +
                '}';
    }
}
