package com.topjava.lunchroom_vote.controller;

import com.topjava.lunchroom_vote.model.Restaurant;
import com.topjava.lunchroom_vote.service.RestaurantService;
import com.topjava.lunchroom_vote.to.RestaurantTo;
import com.topjava.lunchroom_vote.util.RestaurantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.topjava.lunchroom_vote.util.ValidationUtil.assureIdConsistent;
import static com.topjava.lunchroom_vote.util.ValidationUtil.checkNew;

/**
 * @Alima-T 12/25/2022
 */
@RestController
@RequestMapping(value = RestaurantAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RestaurantAdminRestController {

    static final String REST_URL = "/api/admin/restaurants";

    private final RestaurantService service;

    public RestaurantAdminRestController(RestaurantService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantTo> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        RestaurantTo created = RestaurantUtil.createTo(service.create(restaurant));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant) {
        log.info("update {}", restaurant);
        assureIdConsistent(restaurant, restaurant.id());
        service.update(restaurant);
    }

    @GetMapping()
    public List<RestaurantTo> getAll() {
        log.info("getAll");
        return RestaurantUtil.getTos(service.getAll());
    }

    @GetMapping("/menu")
    public List<RestaurantTo> getAllWithMenu() {
        log.info("getAllWithMenu");
        return RestaurantUtil.getTosWithMenu(service.getAll());
    }

    @GetMapping("/{id}")
    public RestaurantTo get(@PathVariable int id) {
        log.info("get {}", id);
        return RestaurantUtil.createTo(service.get(id));
    }

    @GetMapping("/{id}/menu")
    public RestaurantTo getMenuOfDay(@PathVariable int id) {
        log.info("getMenuOfDay for restaurant {}", id);
        return RestaurantUtil.createToWithMenu(service.getMenuOfDay(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

}
