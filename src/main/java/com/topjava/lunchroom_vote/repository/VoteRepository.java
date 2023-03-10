package com.topjava.lunchroom_vote.repository;

import com.topjava.lunchroom_vote.model.Vote;
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
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.voteDate=:voteDate ORDER BY v.voteDate, v.id DESC")
    List<Vote> getAllByDate(@Param("voteDate") LocalDate voteDate);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.voteDate=:voteDate ORDER BY v.voteDate DESC")
    Vote getByUserAndDate(@Param("userId") int userId, @Param("voteDate") LocalDate voteDate);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId AND v.voteDate=:voteDate ORDER BY v.voteDate, v.id DESC")
    List<Vote> getByRestaurantAndDate(@Param("restaurantId") int restaurantId, @Param("voteDate") LocalDate voteDate);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id")
    int delete(@Param("id") int id);
}
