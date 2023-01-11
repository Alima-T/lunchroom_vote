package com.topjava.lunchroom_vote.repository;

import com.topjava.lunchroom_vote.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * @Alima-T 11/30/2022
 */
@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Query("SELECT f FROM Dish f WHERE f.restaurant.id=:restaurantId ORDER BY f.voteDate DESC")
    List<Dish> getAll(@Param("restaurantId") int restaurantId);

    @Query("SELECT f FROM Dish f WHERE f.restaurant.id=:restaurantId AND f.voteDate=:voteDate ORDER BY f.voteDate, f.id")
    List<Dish> getAllByDate(@Param("restaurantId") int restaurantId, @Param("voteDate") LocalDate voteDate);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish f WHERE f.id=:id AND f.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);
}
