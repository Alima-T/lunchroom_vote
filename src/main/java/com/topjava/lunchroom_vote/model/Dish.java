package com.topjava.lunchroom_vote.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @Alima-T 11/27/2022
 */
@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(
        columnNames = {"restaurant_id", "vote_date", "description"},
        name = "dish_unique_restaurant_vote_date_description_idx")})
@Getter
@Setter
@NoArgsConstructor
public class Dish extends AbstractBaseEntity {

    @Column(name = "vote_date", nullable = false)
    @NotNull
    private LocalDate voteDate;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;

    @Column(name = "price", nullable = false)
    @Range(min = 0, max = 1000)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @JsonBackReference
    private Restaurant restaurant;

    public Dish(LocalDate voteDate, String description, BigDecimal price, Restaurant restaurant) {
        this(null, voteDate, description, price, restaurant);
    }

    public Dish(Integer id, LocalDate voteDate, String description, BigDecimal price, Restaurant restaurant) {
        super(id);
        this.voteDate = voteDate;
        this.description = description;
        this.price = price;
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", voteDate=" + voteDate +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", restaurant=" + restaurant +
                '}';
    }
}
