package com.topjava.lunchroom_vote.service;

import com.topjava.lunchroom_vote.exception.OutOfTimeException;
import com.topjava.lunchroom_vote.model.Vote;
import com.topjava.lunchroom_vote.repository.RestaurantRepository;
import com.topjava.lunchroom_vote.repository.UserRepository;
import com.topjava.lunchroom_vote.repository.VoteRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import static com.topjava.lunchroom_vote.util.ValidationUtil.*;

/**
 * @Alima-T 11/30/2022
 */
@Service
public class VoteService {

    private static final Sort SORT_DATE = Sort.by(Sort.Direction.DESC, "voteDate", "id");
    private final VoteRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private Clock clock = Clock.system(ZoneId.systemDefault());

    public VoteService(VoteRepository repository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public Vote get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public List<Vote> getAll() {
        return repository.findAll(SORT_DATE);
    }

    public Vote getByUserAndDate(int userId, LocalDate voteDate) {
        Assert.notNull(voteDate, "Vote date can not be empty");
        return checkNotFoundWithId(repository.getByUserAndDate(userId, voteDate), userId);
    }

    public List<Vote> getByRestaurantAndDate(int restaurantId, LocalDate voteDate) {
        Assert.notNull(voteDate, "Vote date can not be empty");
        return checkNotFoundWithId(repository.getByRestaurantAndDate(restaurantId, voteDate), restaurantId);
    }

    public List<Vote> getAllByDate(LocalDate voteDate) {
        Assert.notNull(voteDate, "Vote date can not be empty");
        return checkNotFound(repository.getAllByDate(voteDate), "date=" + voteDate);
    }

    @Transactional
    public Vote create(int userId, int restaurantId) {
        Vote vote = new Vote(null, LocalDate.now(), userRepository.findById(userId).orElse(null),
                restaurantRepository.findById(restaurantId).orElse(null));
        Assert.notNull(vote, "Vote can not be empty");
        checkNew(vote);
        votingTimeVerification(clock);
        return repository.save(vote);
    }

    @Transactional
    public void update(int id, int restaurantId) {
        Vote vote = repository.findById(id).orElseThrow(() -> new OutOfTimeException("Voting time ended at 11.00 a.m., now is " + LocalTime.now()));
        Assert.notNull(vote, "Vote can not be empty");
        assureIdConsistent(vote, vote.id());
        votingTimeVerification(clock);
        vote.setRestaurant(restaurantRepository.findById(restaurantId).orElse(null));
        checkNotFoundWithId(repository.save(vote), vote.id());
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }
}
