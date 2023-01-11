package com.topjava.lunchroom_vote.service;

import com.topjava.lunchroom_vote.model.Dish;
import com.topjava.lunchroom_vote.repository.DishRepository;
import com.topjava.lunchroom_vote.repository.RestaurantRepository;
import com.topjava.lunchroom_vote.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.topjava.lunchroom_vote.util.ValidationUtil.checkNotFoundWithId;

/**
 * @Alima-T 11/30/2022
 */
@Service
public class DishService {

    private final DishRepository repository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public DishService(DishRepository repository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    public Dish get(int id, int restaurantId) {
        Dish dish = repository.findById(id).orElseThrow(() ->
                new NotFoundException("Dish " + id + " from Restaurant " + restaurantId + " not found"));
        return dish != null && Objects.requireNonNull(dish.getRestaurant().getId()) == restaurantId ? dish : null;
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(repository.delete(id, restaurantId) != 0, id);
    }

    @Cacheable("dishes")
    public List<Dish> getAll() {
        return repository.findAll();
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public List<Dish> getAllByRestaurant(int restaurantId) {
        return repository.getAll(restaurantId);
    }

    public List<Dish> getAllByDate(int restaurantId, LocalDate voteDate) {
        Assert.notNull(voteDate, "voteDate can not be empty");
        return repository.getAllByDate(restaurantId, voteDate);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Transactional
    public void update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "Dish can not be empty");
        dish.setRestaurant(restaurantRepository.findById(restaurantId).orElse(null));
        checkNotFoundWithId(repository.save(dish), dish.id());
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Transactional
    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "Dish can not be empty");
        dish.setRestaurant(restaurantRepository.findById(restaurantId).orElse(null));
        return repository.save(dish);
    }
}
