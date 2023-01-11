package com.topjava.lunchroom_vote.service;

import com.topjava.lunchroom_vote.model.Dish;
import com.topjava.lunchroom_vote.model.Restaurant;
import com.topjava.lunchroom_vote.repository.DishRepository;
import com.topjava.lunchroom_vote.repository.RestaurantRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.topjava.lunchroom_vote.util.ValidationUtil.checkNotFoundWithId;

/**
 * @Alima-T 11/30/2022
 */
@Service
public class RestaurantService {

    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final RestaurantRepository repository;
    private final DishRepository dishRepository;

    public RestaurantService(RestaurantRepository repository, DishRepository dishRepository) {
        this.repository = repository;
        this.dishRepository = dishRepository;
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Transactional
    public Restaurant getMenuOfDay(int id) {
        Restaurant restaurant = checkNotFoundWithId(repository.findById(id).orElse(null), id);
        Assert.notNull(restaurant, "Restaurant can not be empty");
        List<Dish> menuOfDay = dishRepository.getAll(id)
                .stream()
                .filter(all -> all.getVoteDate().isEqual(LocalDate.now()))
                .toList();
        restaurant.setMenu(menuOfDay);
        return restaurant;
    }
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return repository.findAll(SORT_NAME);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "Restaurant  can not be empty");
        return repository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "Restaurant can not be empty");
        checkNotFoundWithId(repository.save(restaurant), restaurant.id());
    }



}
