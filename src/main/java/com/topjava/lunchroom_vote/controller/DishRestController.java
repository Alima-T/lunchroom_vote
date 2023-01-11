package com.topjava.lunchroom_vote.controller;

import com.topjava.lunchroom_vote.model.Dish;
import com.topjava.lunchroom_vote.service.DishService;
import com.topjava.lunchroom_vote.to.DishTo;
import com.topjava.lunchroom_vote.util.DishUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.topjava.lunchroom_vote.util.ValidationUtil.assureIdConsistent;
import static com.topjava.lunchroom_vote.util.ValidationUtil.checkNew;

/**
 * @Alima-T 12/09/2022
 */
@RestController
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class DishRestController {

    static final String REST_URL = "/api/admin/dishes";

    private final DishService service;

    public DishRestController(DishService service) {
        this.service = service;
    }

    @GetMapping()
    public List<DishTo> getAll() {
        log.info("getAll");
        return DishUtil.getTos(service.getAll());
    }

    @GetMapping("/{restaurantId}")
    public List<DishTo> getAllByRestaurant(@PathVariable int restaurantId) {
        log.info("getAll for restaurant {}", restaurantId);
        return DishUtil.getTos(service.getAllByRestaurant(restaurantId));
    }

    @GetMapping("/{restaurantId}/{id}")
    public DishTo get(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("get dish {} for restaurant {}", id, restaurantId);
        return DishUtil.createTo(service.get(id, restaurantId));
    }



    @GetMapping("/{restaurantId}/by-date")
    public List<DishTo> getAllByDate(@PathVariable int restaurantId,
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                     @RequestParam LocalDate voteDate) {
        log.info("getAllByDate {} for restaurant {}", voteDate, restaurantId);
        return DishUtil.getTos(service.getAllByDate(restaurantId, voteDate));
    }

    @PutMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        log.info("update dish {} for restaurant {}", dish, restaurantId);
        assureIdConsistent(dish, dish.id());
        service.update(dish, restaurantId);
    }

    @PostMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishTo> createWithLocation(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        log.info("create dish {} for restaurant {}", dish, restaurantId);
        checkNew(dish);
        DishTo created = DishUtil.createTo(service.create(dish, restaurantId));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{restaurantId}/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("delete dish {} for restaurant {}", id, restaurantId);
        service.delete(id, restaurantId);
    }


}
